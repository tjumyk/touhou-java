package org.tjuscs.bulletgame.task;

public class WaitTask extends Task {
	private int currentFrame, totalFrames;

	public WaitTask(int frames) {
		this.totalFrames = frames;
		this.currentFrame = 0;
	}

	@Override
	public void reset() {
		super.reset();
		currentFrame = 0;
	}

	@Override
	public boolean work() {
		currentFrame++;
		if (currentFrame > totalFrames)
			return true;
		return false;
	}
}
