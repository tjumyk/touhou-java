package org.tjuscs.bulletgame.model;

import org.tjuscs.bulletgame.util.GameUtil;

/***********************************************************************
 * Module: PlayerBulletStraight.java Author: 宇锴 Purpose: Defines the Class
 * PlayerBulletStraight
 ***********************************************************************/

public class PlayerBullet extends BaseObject {
	private double dmg;

	public PlayerBullet() {
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
		this.setGroup(GameUtil.GROUP_PLAYER_BULLET);
		this.setLayer(GameUtil.LAYER_PLAYER_BULLET);
		this.setImg(img);
		this.setX(x);
		this.setY(y);
		GameUtil.SetV(this, v, angle, true);
		this.setDmg(dmg);
	}

	public double getDmg() {
		return dmg;
	}

	public void setDmg(double dmg) {
		this.dmg = dmg;
	}
}