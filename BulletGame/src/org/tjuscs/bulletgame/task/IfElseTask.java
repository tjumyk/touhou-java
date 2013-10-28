package org.tjuscs.bulletgame.task;

import org.apache.commons.jexl2.Expression;

public class IfElseTask extends Task {
	private Task ifTask;
	private Task elseTask;
	private Expression condition;

	public IfElseTask(String condition, Task ifTask, Task elseTask) {
		this.condition = scriptEngine.createExpression(condition);
		this.ifTask = ifTask;
		this.elseTask = elseTask;
		if (ifTask != null)
			ifTask.setParentContext(context);
		if (elseTask != null)
			elseTask.setParentContext(context);
	}

	@Override
	public void reset() {
		super.reset();
		if(ifTask != null)
			ifTask.reset();
		if(elseTask != null)
			elseTask.reset();
	}
	
	@Override
	public boolean work() {
		if (condition == null || ifTask == null)
			return true;
		boolean complete = true;
		if ((Boolean) condition.evaluate(context))
			complete = ifTask.act();
		else if (elseTask != null)
			complete = elseTask.act();
		if (complete) {
			if (ifTask != null)
				ifTask.reset();
			if (elseTask != null)
				elseTask.reset();
		}
		return complete;
	}
}
