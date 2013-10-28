package org.tjuscs.bulletgame.model;

import org.tjuscs.bulletgame.task.Task;

public class Stage {

	/** 关卡名*/
	private String title;
	/** 以该关卡为开始游戏的第一关时初始残机，火力，信仰*/
	private int life, power, faith;

	private Task task;

	public Stage(String title, int life, int power, int faith) {
		super();
		this.title = title;
		this.life = life;
		this.power = power;
		this.faith = faith;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getFaith() {
		return faith;
	}

	public void setFaith(int faith) {
		this.faith = faith;
	}

}
