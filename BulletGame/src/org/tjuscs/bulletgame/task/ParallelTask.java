package org.tjuscs.bulletgame.task;

import java.util.ArrayList;
import java.util.List;

public class ParallelTask extends Task {
	private List<Task> taskList = new ArrayList<Task>();

	public ParallelTask(Task... tasks) {
		for (Task t : tasks) {
			t.setParentContext(context);
			taskList.add(t);
		}
	}

	@Override
	public void reset() {
		super.reset();
		for (Task t : taskList) {
			t.reset();
		}
	}

	@Override
	public boolean work() {
		if (taskList.size() <= 0)
			return true;
		boolean allFinish = true;
		for (Task t : taskList) {
			if (!t.act())
				allFinish = false;
		}
		return allFinish;
	}

}
