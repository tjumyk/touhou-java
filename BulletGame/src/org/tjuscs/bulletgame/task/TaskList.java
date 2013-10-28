package org.tjuscs.bulletgame.task;

import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class TaskList {
	private Map<String,Task> list = new HashMap<String, Task>();
	
	public boolean act(String name){
		return list.get(name).act();
	}
	
	public void addTask(String name, Task task){
		list.put(name, task);
	}
	
	public void setBinding(String name, Object obj){
		for(Task task : list.values()){
			task.setBinding(name, obj);
		}
	}
	
	public void removeTask(String name){
		list.remove(name);
	}
	
	public Task get(String name){
		return list.get(name);
	}
	
	public static TaskList readScript(String fileName){
		try {
			FileHandle file = Gdx.files.internal(fileName);
			String content = file.readString("utf-8");
			Document doc = DocumentHelper.parseText(content);
			Element root = doc.getRootElement();
			if(root.getName().equalsIgnoreCase("tasklist")){
				TaskList list = new TaskList();
				for(Object obj : root.elements()){
					Element e = (Element) obj;
					if(e.getName().equalsIgnoreCase("task")){
						String name = e.attributeValue("name");
						Task task = Tasks.parse(e.elements().get(0));
						Tasks.setDefaultBindings(task);
						list.addTask(name, task);
					}
				}
				list.setBinding("tasklist", list);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
