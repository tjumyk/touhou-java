package org.tjuscs.bulletgame.task;

import org.apache.commons.jexl2.Script;

public class ScriptTask extends Task {
	private Script script;

	public ScriptTask(String script) {
		this.script = scriptEngine.createScript(script);
	}

	@Override
	public boolean work() {
		if (context != null && script != null ) {
			try {
				script.execute(context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
