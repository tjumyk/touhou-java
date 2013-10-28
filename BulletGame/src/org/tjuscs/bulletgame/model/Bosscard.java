package org.tjuscs.bulletgame.model;

import org.tjuscs.bulletgame.task.Task;
import org.tjuscs.bulletgame.task.Tasks;

/***********************************************************************
 * Module: Bosscard.java Author: Administrator Purpose: Defines the Class
 * Bosscard
 ***********************************************************************/

public class Bosscard {

	private String name;
	private int t1;
	private int t2;
	private int t3;
	private double hp;
	private int[] drop = { 0, 0, 0 };
	private Task task;

	public Bosscard() {
	}

	public Bosscard(String name, int t1, int t2, int t3, double hp, int dpower,
			int dfaith, int dpoint) {
		init(name, t1, t2, t3, hp, dpower, dfaith, dpoint);
	}

	/**
	 * @param name
	 * @param t1
	 * @param t2
	 * @param t3
	 * @param hp
	 * @param drop
	 */
	public void init(String name, int t1, int t2, int t3, double hp,
			int dpower, int dfaith, int dpoint) {
		this.setName(name);
		this.setT1(t1);
		this.setT2(t2);
		this.setT3(t3);
		this.setHp(hp);
		int[] tDrop = { dpower, dfaith, dfaith };
		this.setDrop(tDrop);
		this.task = Tasks.empty();
	}

	public int[] getDrop() {
		return drop;
	}

	public void setDrop(int[] drop) {
		this.drop = drop;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getT1() {
		return t1;
	}

	public void setT1(int t1) {
		this.t1 = t1;
	}

	public int getT2() {
		return t2;
	}

	public void setT2(int t2) {
		this.t2 = t2;
	}

	public int getT3() {
		return t3;
	}

	public void setT3(int t3) {
		this.t3 = t3;
	}

	public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}