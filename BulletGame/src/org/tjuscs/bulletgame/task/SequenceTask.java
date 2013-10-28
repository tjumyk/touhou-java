package org.tjuscs.bulletgame.task;

import java.util.ArrayList;
import java.util.List;

public class SequenceTask extends Task {
	private int taskIndex;
	private List<Task> taskList = new ArrayList<Task>();

	public SequenceTask(Task... tasks) {
		for (Task t : tasks) {
			t.setParentContext(context);
			taskList.add(t);
		}
		taskIndex = 0;
	}

	@Override
	public void reset() {
		super.reset();
		taskIndex = 0;
		for (Task t : taskList) {
			t.reset();
		}
	}

	@Override
	public boolean work() {
		if (taskList.size() <= 0)
			return true;
		while (true) {
			if (taskList.get(taskIndex).act()) {
				taskList.get(taskIndex).reset();
				taskIndex++;
			}else
				return false;
			if (taskIndex >= taskList.size())
				return true;
		}
	}

}
