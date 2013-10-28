package org.tjuscs.bulletgame.task;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.tjuscs.bulletgame.model.GameModel;
import org.tjuscs.bulletgame.util.GameUtil;
import org.tjuscs.bulletgame.view.component.AudioPlayer;
import org.tjuscs.bulletgame.view.component.Renderer;
import org.tjuscs.bulletgame.view.component.Resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Tasks {
	//private static Map<String, Task> taskCache = new HashMap<String, Task>();

	public static EmptyTask empty() {
		return new EmptyTask();
	}

	public static WaitTask wait(int frames) {
		return new WaitTask(frames);
	}

	public static SequenceTask sequence(Task... tasks) {
		return new SequenceTask(tasks);
	}
	
	public static ParallelTask parallel(Task... tasks) {
		return new ParallelTask(tasks);
	}

	public static IfElseTask ifelse(String condition, Task ifTask, Task elseTask) {
		return new IfElseTask(condition, ifTask, elseTask);
	}

	public static LoopTask loop(String loops, Task innerTask) {
		return loop(loops, innerTask, null, null, null);
	}

	public static LoopTask loop(String loops, Task innerTask, String varName,
			String varValue, String updateScript) {
		return new LoopTask(loops, innerTask, varName, varValue, updateScript);
	}

	public static ScriptTask execute(String script) {
		return new ScriptTask(script);
	}

	public static MoveTask move(String x, String y, String frames, String mode,
			String target) {
		return new MoveTask(x, y, frames, mode, target);
	}

	public static Task readScript(String fileName) {
		try {
//			if (useCache) {
//				Task task = taskCache.get(fileName);
//				if (task != null)
//					return task;
//			}
			FileHandle file = Gdx.files.internal(fileName);
			String content = file.readString("utf-8");
			Document doc = DocumentHelper.parseText(content);
			Element root = doc.getRootElement();
			Task task = parse(root);
			setDefaultBindings(task);

//			if (useCache) {
//				taskCache.put(fileName, task);
//			}
			return task;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setDefaultBindings(Task task) {
		task.setBinding("game", GameModel.getInstance());
		task.setBinding("gameUtil", GameUtil.getInstance());
		task.setBinding("res", Resources.getInstance());
		task.setBinding("render", Renderer.getInstance());
		task.setBinding("audio", AudioPlayer.getInstance());
	}

	@SuppressWarnings("rawtypes")
	static Task parse(Object obj) {
		Element e = (Element) obj;
		if (e.getName().equalsIgnoreCase("seq")) {
			List list = e.elements();
			Task[] tasks = new Task[list.size()];
			for (int i = 0; i < tasks.length; i++) {
				tasks[i] = parse((Element) list.get(i));
			}
			return Tasks.sequence(tasks);
		} else if (e.getName().equalsIgnoreCase("para")) {
			List list = e.elements();
			Task[] tasks = new Task[list.size()];
			for (int i = 0; i < tasks.length; i++) {
				tasks[i] = parse((Element) list.get(i));
			}
			return Tasks.parallel(tasks);
		} else if (e.getName().equalsIgnoreCase("if")) {
			List list = e.elements();
			String cond = e.attributeValue("cond");
			if (list.size() <= 1)
				return Tasks.ifelse(cond, parse(list.get(0)), null);
			return Tasks.ifelse(cond, parse(list.get(0)), parse(list.get(1)));
		} else if (e.getName().equalsIgnoreCase("loop")) {
			List list = e.elements();
			String count = e.attributeValue("count");
			String var = e.attributeValue("var");
			String init = e.attributeValue("init");
			String update = e.attributeValue("update");
			return Tasks.loop(count, parse(list.get(0)), var,
					init, update);
		} else if (e.getName().equalsIgnoreCase("wait")) {
			return Tasks.wait(Integer.parseInt(e.attributeValue("count")));
		} else if (e.getName().equalsIgnoreCase("cmd")) {
			return Tasks.execute(e.getText());
		} else if (e.getName().equalsIgnoreCase("script")) {
//			String cache = e.attributeValue("cache");
//			boolean useCache = false;
//			if(cache != null && cache.equalsIgnoreCase("true"))
//				useCache = true;
			return Tasks.readScript(e.attributeValue("path"));
		} else if (e.getName().equalsIgnoreCase("var")) {
			String name = e.attributeValue("name");
			String value = e.attributeValue("value");
			return Tasks.execute("context.setParent('" + name + "'," + value
					+ ");");
		} else if (e.getName().equalsIgnoreCase("move")) {
			String x = e.attributeValue("x");
			String y = e.attributeValue("y");
			String frames = e.attributeValue("frames");
			String mode = e.attributeValue("mode");
			String target = e.attributeValue("target");
			return Tasks.move(x, y, frames, mode, target);
		} else if(e.getName().equalsIgnoreCase("empty")){
			return Tasks.empty();
		}
		return Tasks.empty();
	}
}
