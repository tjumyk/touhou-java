package org.tjuscs.bulletgame.model;

import org.tjuscs.bulletgame.util.GameUtil;
import org.tjuscs.bulletgame.view.component.Renderer;

/***********************************************************************
 * Module: Bubble.java Author: 宇锴 Purpose: Defines the Class Bubble
 ***********************************************************************/

public class Bubble extends BaseObject {
	private int lifeTime;
	private double size1;
	private double size2;

	public Bubble() {
	}

	public Bubble(String img, double x, double y, int lifeTime, double size1,
			double size2, double layer) {
		this.init(img, x, y, lifeTime, size1, size2, layer);
	}

	/**
	 * @param img
	 * @param x
	 * @param y
	 * @param lifeTime
	 * @param size1
	 * @param size2
	 * @param color1
	 * @param color2
	 * @param layer
	 * @param blend
	 */
	public void init(String img, double x, double y, int lifeTime,
			double size1, double size2, double layer) {
		super.init();
		this.setImg(img);
		this.setX(x);
		this.setY(y);
		this.setGroup(GameUtil.GROUP_GHOST);
		this.setLayer(layer);
		this.setLifeTime(lifeTime);
		this.setSize1(size1);
		this.setSize2(size2);
	}

	public void render() {
		double t = (this.getLifeTime() - this.getTimer() + 0.0)
				/ (this.getLifeTime() + 0.0);
		double size = this.getSize1() * t + this.getSize2() * (1 - t);
		Renderer.getInstance().Render(this.getImg(), this.getX(), this.getY(),
				0, size);
	}

	public void frame() {
		if (this.getTimer() == this.getLifeTime() - 1)
			GameUtil.Del(this);
	}

	public int getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(int lifeTime) {
		this.lifeTime = lifeTime;
	}

	public double getSize1() {
		return size1;
	}

	public void setSize1(double size1) {
		this.size1 = size1;
	}

	public double getSize2() {
		return size2;
	}

	public void setSize2(double size2) {
		this.size2 = size2;
	}

}