package org.tjuscs.bulletgame.model;

/***********************************************************************
 * Module: PlayerBulletStraight.java Author: 宇锴 Purpose: Defines the Class
 * PlayerBulletStraight
 ***********************************************************************/

public class PlayerBulletStraight extends PlayerBullet {

	public PlayerBulletStraight() {
	}

	public PlayerBulletStraight(String img, double x, double y, double v,
			double angle, double dmg) {
		this.init(img, x, y, v, angle, dmg);
	}

	/**
	 * @param img
	 * @param x
	 * @param y
	 * @param v
	 * @param angle
	 * @param dmg
	 */
	public void init(String img, double x, double y, double v, double angle,
			double dmg) {
		super.init(img, x, y, v, angle, dmg);
	}

}