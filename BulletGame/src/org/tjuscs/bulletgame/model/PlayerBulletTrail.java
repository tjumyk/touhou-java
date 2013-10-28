package org.tjuscs.bulletgame.model;

import org.tjuscs.bulletgame.util.GameUtil;
import org.tjuscs.bulletgame.util.MathUtils;

/***********************************************************************
 * Module: PlayerBulletTrail.java Author: 宇锴 Purpose: Defines the Class
 * PlayerBulletTrail
 ***********************************************************************/

public class PlayerBulletTrail extends PlayerBullet {
	protected BaseObject target;
	private double trail;
	private double v;

	public PlayerBulletTrail() {
	}

	public PlayerBulletTrail(String img, double x, double y, double v,
			double angle, BaseObject target, double trail, double dmg) {
		this.init(img, x, y, v, angle, target, trail, dmg);
	}

	/**
	 * @param img
	 * @param x
	 * @param y
	 * @param v
	 * @param angle
	 * @param target
	 * @param trail
	 * @param dmg
	 */
	public void init(String img, double x, double y, double v, double angle,
			BaseObject target, double trail, double dmg) {
		super.init(img, x, y, v, angle, dmg);
		this.setTarget(target);
		this.setTrail(trail);
		this.setV(v);
	}

	public void frame() {
		if (target != null && GameUtil.IsValid(target) && target.isColli()) {
			double atarget = GameUtil.Angle(this, target);
			double a = (atarget - this.getRot() + 720) % 360;
			if (a > 180)
				a -= 360;
			double da = this.getTrail() / (GameUtil.Dist(this, target) + 1);
			if (da >= Math.abs(a)) {
				this.setRot(atarget);
			} else {
				this.setRot(this.getRot() + Math.signum(a) * da);
			}
		}
		this.setVx(this.getV() * MathUtils.cosDeg(this.getRot()));
		this.setVy(this.getV() * MathUtils.sinDeg(this.getRot()));
	}

	public BaseObject getTarget() {
		return target;
	}

	public void setTarget(BaseObject target) {
		this.target = target;
	}

	public double getTrail() {
		return trail;
	}

	public void setTrail(double trail) {
		this.trail = trail;
	}

	public double getV() {
		return v;
	}

	public void setV(double v) {
		this.v = v;
	}

}