package org.tjuscs.bulletgame.task;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.tjuscs.bulletgame.util.MathUtils;
import org.tjuscs.bulletgame.util.RandomUtil;

public abstract class Task {
	public static JexlEngine scriptEngine = new JexlEngine();
	static{
		Map<String, Object> funcs = new HashMap<String, Object>();
		funcs.put("MathUtils", MathUtils.class);
		funcs.put("Math", Math.class);
		funcs.put("ran", RandomUtil.class);
		funcs.put("TaskList", TaskList.class);
		funcs.put("Tasks", Tasks.class);
		scriptEngine.setFunctions(funcs);
	}
	private boolean canceled;

	private boolean completed;

	protected MapContext context;

	public Task() {
		completed = false;
		canceled = false;
		context = new MapContext();
		context.setLocal("context", context);
	}

	public boolean act() {
		if (completed || canceled)
			return true;
		completed = work();
		return completed;
	}

	public void cancel() {
		canceled = true;
	}

	public void reset() {
		completed = false;
		canceled = false;
	}

	public void setParentContext(JexlContext context) {
		this.context.setParentContext((MapContext) context);
	}
	
	public Task bind(String name, Object value){
		setBinding(name, value);
		return this;
	}
	
	public void setBinding(String name, Object value){
		this.context.set(name, value);
	}
	
	public Object getBinding(String name){
		return this.context.get(name);
	}

	public abstract boolean work();
}
