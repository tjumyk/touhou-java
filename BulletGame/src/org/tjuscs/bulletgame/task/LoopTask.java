package org.tjuscs.bulletgame.task;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.Script;

public class LoopTask extends Task {
	private Task innerTask;
	private int currentLoop;
	public static final int LOOP_INFINITE = -1;
	private String varName, varValue;
	private Script update;
	private Expression loops;
	private Integer loopsValue = null;

	public LoopTask(String loops, Task innerTask) {
		this(loops, innerTask, null, null, null);
	}

	public LoopTask(String loops, Task innerTask, String varName, String varValue,
			String updateScript) {
		this.innerTask = innerTask;
		innerTask.setParentContext(context);
		this.loops = scriptEngine.createExpression(loops);
		this.currentLoop = 0;
		this.varName = varName;
		this.varValue = varValue;

		if (varName != null && varName.length() > 0) {
			this.context.set(varName, scriptEngine.createExpression(varValue)
					.evaluate(context));
		}
		if (updateScript != null && updateScript.length() > 0)
			this.update = scriptEngine.createScript(updateScript);
	}

	@Override
	public void reset() {
		super.reset();
		this.currentLoop = 0;
		this.loopsValue = null;
		if (varName != null && varName.length() > 0) {
			this.context.setLocal(varName,
					scriptEngine.createExpression(varValue).evaluate(context));
		}
		innerTask.reset();
	}

	@Override
	public boolean work() {
		if (innerTask == null)
			return true;
		if(loopsValue == null){
			loopsValue = ((Number)loops.evaluate(context)).intValue();
		}
		while (true) {
			if (innerTask.act()) {
				currentLoop++;
				if (update != null)
					update.execute(context);
				innerTask.reset();
			} else
				return false;
			if (loopsValue != LOOP_INFINITE && currentLoop >= loopsValue)
				return true;
		}
	}

}
