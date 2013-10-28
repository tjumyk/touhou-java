package org.tjuscs.bulletgame.model;

import org.tjuscs.bulletgame.util.GameUtil;
import org.tjuscs.bulletgame.util.MathUtils;
import org.tjuscs.bulletgame.view.component.AudioPlayer;
import org.tjuscs.bulletgame.view.component.Renderer;
import org.tjuscs.bulletgame.view.component.Resources;

/***********************************************************************
 * Module:  Laser.java
 * Author:  宇锴
 * Purpose: Defines the Class Laser
 ***********************************************************************/

/** @pdOid ba69b7dd-402d-4fc7-8ca8-ef1a4e4bba32 */
public class Laser extends BaseObject {
	private int index;
	private String img1;
	private String img2;
	private String img3;
	private String img4;
	private double l1;
	private double l2;
	private double l3;
	private double w0;
	private double w;
	private double alpha;
	private double node;
	private double dw;
	private double da;
	private int counter;

	public Laser() {
	}

	public Laser(int index, double x, double y, double rot, double l1,
			double l2, double l3, double w, double node) {
		this.init(index, x, y, rot, l1, l2, l3, w, node);
	}

	/**
	 * @param index
	 * @param x
	 * @param y
	 * @param rot
	 * @param l1
	 * @param l2
	 * @param l3
	 * @param w
	 * @param node
	 */
	public void init(int index, double x, double y, double rot, double l1,
			double l2, double l3, double w, double node) {
		super.init();
		this.setIndex(Math.max(1, Math.min((index + 1) / 2, 8)));
		this.setImg1("laser_1" + this.getIndex());
		this.setImg2("laser_2" + this.getIndex());
		this.setImg3("laser_3" + this.getIndex());
		this.setImg4("laser_node" + this.getIndex());
		this.setX(x);
		this.setY(y);
		this.setRot(rot);
		this.setL1(l1);
		this.setL2(l2);
		this.setL3(l3);
		this.setW0(w);
		this.setW(0.0);
		this.setAlpha(0);
		this.setNode(node);
		this.setGroup(GameUtil.GROUP_INDES);
		this.setLayer(GameUtil.LAYER_ENEMY_BULLET);
		this.setColli(false);
		this.setA(0);
		this.setB(0);
		this.setDw(0);
		this.setDa(0);
		this.setCounter(0);
	}

	// public void frame() {
	// this.getTask().act();
	// if (this.getCounter() > 0) {
	// this.setCounter(this.getCounter() - 1);
	// this.setW(this.getW() + this.getDw());
	// this.setAlpha(this.getAlpha() + this.getDa());
	// }
	// if (this.getAlpha() > 0.999) {
	// PlayerClass player = GameModel.getInstance().getPlayer();
	// double x = player.getX() - this.getX();
	// double y = player.getY() - this.getY();
	// double rot = this.getRot();
	// x = x * MathUtils.cosDeg(rot) + y * MathUtils.sinDeg(rot);
	// y = Math.abs(y * MathUtils.cosDeg(rot) - x * MathUtils.sinDeg(rot));
	// if (x > 0) {
	// if (x < this.getL1()) {
	// if (y < x / this.getL1() * this.getW() / 2)
	// player.colli(this);
	// } else if (x < this.getL1() + this.getL2()) {
	// if (y < this.getW() / 2)
	// player.colli(this);
	// } else if (x < this.getL1() + this.getL2() + this.getL3()) {
	// if (y < (this.getL1() + this.getL2() + this.getL3() - x)
	// / this.getL3() * this.getW() / 2)
	// player.colli(this);
	// }
	// if (this.getTimer() % 6 == 0) {
	// if (x < this.getL1()) {
	// if (y < x / this.getL1() * this.getW() / 2 + 16) {
	// Item.playerGraze();
	// player.getGrazer().setGrazed(true);
	// }
	// } else if (x < this.getL1() + this.getL2()) {
	// if (y < this.getW() / 2 + 16) {
	// Item.playerGraze();
	// player.getGrazer().setGrazed(true);
	// }
	// } else if (x < this.getL1() + this.getL2() + this.getL3()) {
	// if (y < (this.getL1() + this.getL2() + this.getL3() - x)
	// / this.getL3() * this.getW() / 2 + 16) {
	// Item.playerGraze();
	// player.getGrazer().setGrazed(true);
	// }
	// }
	// }
	// }
	// }
	// }

	public void frame() {
		this.getTask().act();
		if (this.getCounter() > 0) {
			this.setCounter(this.getCounter() - 1);
			this.setW(this.getW() + this.getDw());
			this.setAlpha(this.getAlpha() + this.getDa());
		}
		if (this.getAlpha() > 0.999) {
			PlayerClass player = GameModel.getInstance().getPlayer();
			boolean isplayerColli = false, isplayerGraze = false;
			double rot = this.getRot();
			double laserT = this.getL1() + this.getL2() + this.getL3();
			double dx = MathUtils.cosDeg(rot);
			double dy = MathUtils.sinDeg(rot);
			double ex = player.getX() - this.getX();
			double ey = player.getY() - this.getY();
			double laserA = ex * dx + ey * dy;
			double laserA2 = laserA * laserA;
			double laserE2 = ex * ex + ey * ey;

			double playerR2 = player.getA() * player.getA();
			if (playerR2 - laserE2 + laserA2 >= 0) {
				double playerF = Math.sqrt(playerR2 - laserE2 + laserA2);
				double playerT = laserA - playerF;
				if (playerT > 0.0 && playerT < laserT)
					isplayerColli = true;
				playerT = laserA + playerF;
				if (playerT > 0.0 && playerT < laserT)
					isplayerColli = true;
			}
			if (isplayerColli)
				player.colli(this);

			if (this.getTimer() % 6 == 0) {
				double grazeR2 = player.getGrazer().getA()
						* player.getGrazer().getA();
				if (grazeR2 - laserE2 + laserA2 >= 0) {
					double grazeF = Math.sqrt(grazeR2 - laserE2 + laserA2);
					double grazeT = laserA - grazeF;
					if (grazeT > 0.0 && grazeT < laserT)
						isplayerGraze = true;
					grazeT = laserA + grazeF;
					if (grazeT > 0.0 && grazeT < laserT)
						isplayerGraze = true;
				}
				if (isplayerGraze) {
					Item.playerGraze();
					player.getGrazer().setGrazed(true);
				}
			}
		}
	}

	public void render() {
		if (this.getW() > 0) {
			Resources resources = Resources.getInstance();
			Renderer render = Renderer.getInstance();
			resources.SetImageState(this.getImg1(), "", 255, 255, 255,
					255 * this.getAlpha());
			resources.SetImageState(this.getImg2(), "", 255, 255, 255,
					255 * this.getAlpha());
			resources.SetImageState(this.getImg3(), "", 255, 255, 255,
					255 * this.getAlpha());
			render.Render(this.getImg1(), this.getX(), this.getY(),
					this.getRot(), this.getL1() / 64, this.getW() / 16);
			render.Render(this.getImg2(),
					this.getX() + (this.getL1() / 2 + this.getL2() / 2)
							* MathUtils.cosDeg(this.getRot()),
					this.getY() + (this.getL1() / 2 + this.getL2() / 2)
							* MathUtils.sinDeg(this.getRot()), this.getRot(),
					this.getL2() / 128, this.getW() / 16);
			render.Render(
					this.getImg3(),
					this.getX()
							+ (this.getL1() / 2 + this.getL2() + this.getL3() / 2)
							* MathUtils.cosDeg(this.getRot()),
					this.getY()
							+ (this.getL1() / 2 + this.getL2() + this.getL3() / 2)
							* MathUtils.sinDeg(this.getRot()), this.getRot(),
					this.getL3() / 64, this.getW() / 16);
			if (this.getNode() > 0) {
				resources.SetImageState(this.getImg4(), "", 255, 255, 255, 255
						* this.getW() / this.getW0());
				render.Render(this.getImg4(), this.getX(), this.getY(),
						this.getTimer() * 0.02, this.getNode() / 8);
				render.Render(this.getImg4(), this.getX(), this.getY(),
						-this.getTimer() * 0.02, this.getNode() / 8);
			}
		}
	}

	/**
	 * @param t
	 * @param mute
	 */
	public void turnOn(int t, boolean mute) {
		t = Math.max(1, t);
		if (!mute) {
			AudioPlayer.getInstance().PlaySound("lazer00", 0.25);
		}
		this.setCounter(t);
		this.setDa((1 - this.getAlpha()) / (t + 0.0));
		this.setDw((this.getW0() - this.getW()) / (t + 0.0));
	}

	/**
	 * @param t
	 */
	public void turnHalfOn(int t) {
		t = Math.max(1, t);
		this.setCounter(t);
		this.setDa((0.5 - this.getAlpha()) / (t + 0.0));
		this.setDw((0.5 * this.getW0() - this.getW()) / (t + 0.0));
	}

	/**
	 * @param t
	 */
	public void turnOff(int t) {
		t = Math.max(1, t);
		this.setCounter(t);
		this.setDa(-this.getAlpha() / (t + 0.0));
		this.setDw(-this.getW() / (t + 0.0));
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getImg3() {
		return img3;
	}

	public void setImg3(String img3) {
		this.img3 = img3;
	}

	public String getImg4() {
		return img4;
	}

	public void setImg4(String img4) {
		this.img4 = img4;
	}

	public double getL1() {
		return l1;
	}

	public void setL1(double l1) {
		this.l1 = l1;
	}

	public double getL2() {
		return l2;
	}

	public void setL2(double l2) {
		this.l2 = l2;
	}

	public double getL3() {
		return l3;
	}

	public void setL3(double l3) {
		this.l3 = l3;
	}

	public double getW0() {
		return w0;
	}

	public void setW0(double w0) {
		this.w0 = w0;
	}

	public double getW() {
		return w;
	}

	public void setW(double w) {
		this.w = w;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getNode() {
		return node;
	}

	public void setNode(double node) {
		this.node = node;
	}

	public double getDw() {
		return dw;
	}

	public void setDw(double dw) {
		this.dw = dw;
	}

	public double getDa() {
		return da;
	}

	public void setDa(double da) {
		this.da = da;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

}