package org.tjuscs.bulletgame.model;

/***********************************************************************
 * Module: PlayerBulletHide.java Author: 宇锴 Purpose: Defines the Class
 * PlayerBulletHide
 ***********************************************************************/

public class PlayerBulletHide extends PlayerBullet {

	private int delay;

	public PlayerBulletHide() {
	}

	public PlayerBulletHide(double a, double b, double x, double y, double v,
			double angle, double dmg, int delay) {
		this.init(a, b, x, y, v, angle, dmg, delay);
	}

	/**
	 * @param a
	 * @param b
	 * @param x
	 * @param y
	 * @param v
	 * @param angle
	 * @param dmg
	 * @param delay
	 */
	public void init(double a, double b, double x, double y, double v,
			double angle, double dmg, int delay) {
		super.init("", x, y, v, angle, dmg);
		this.setHide(true);
		this.setColli(false);
		this.setA(a);
		this.setB(b);
		this.setDelay(delay);
	}

	public void frame() {
		if (this.getTimer() == this.getDelay())
			this.setColli(true);
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

}