package org.tjuscs.bulletgame.task;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.jexl2.JexlContext;

public class MapContext implements JexlContext {
	private MapContext parentContext;
	private Map<String, Object> map;

	public MapContext() {
		this(null);
	}

	public MapContext(MapContext parentContext) {
		super();
		this.parentContext = parentContext;
		map = new HashMap<String, Object>();
	}

	public boolean has(String name) {
		if (map.containsKey(name))
			return true;
		if (parentContext != null)
			return parentContext.has(name);
		return false;
	}

	public Object get(String name) {
		Object obj = map.get(name);
		if (obj != null) {
			return obj;
		}
		if (parentContext != null)
			return parentContext.get(name);
		return null;
	}

	public MapContext getParentContext() {
		return parentContext;
	}

	public void setParentContext(MapContext parentContext) {
		this.parentContext = parentContext;
	}

	public void set(String name, Object value) {
		if (map.containsKey(name) || parentContext == null)
			map.put(name, value);
		else if (!parentContext.has(name))
			map.put(name, value);
		else
			parentContext.set(name, value);
	}

	public void setLocal(String name, Object value) {
		map.put(name, value);
	}

	public void setParent(String name, Object value) {
		parentContext.setLocal(name, value);
	}
}
