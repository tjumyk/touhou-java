package org.tjuscs.bulletgame.model;

import org.tjuscs.bulletgame.model.item.ItemChip;
import org.tjuscs.bulletgame.model.item.ItemFaith;
import org.tjuscs.bulletgame.model.item.ItemPoint;
import org.tjuscs.bulletgame.model.item.ItemPower;
import org.tjuscs.bulletgame.model.item.ItemPowerFull;
import org.tjuscs.bulletgame.model.item.ItemPowerLarge;
import org.tjuscs.bulletgame.util.GameUtil;
import org.tjuscs.bulletgame.util.MathUtils;
import org.tjuscs.bulletgame.util.RandomUtil;
import org.tjuscs.bulletgame.view.component.Renderer;

/***********************************************************************
 * Module: Item.java Author: Administrator Purpose: Defines the Class Item
 ***********************************************************************/

public class Item extends BaseObject {
	private String imgup;
	private int attract;
	private static double scBonusMax = 2000000;
	private static double scBonusBase = 1000000;

	public Item() {
	}

	public Item(double x, double y, int t) {
		this.init(x, y, t);
	}

	/**
	 * @param x
	 * @param y
	 * @param t
	 */
	public void init(double x, double y, int t) {
		super.init();
		this.setX(x);
		this.setY(y);
		this.setA(8);
		this.setB(8);
		this.setRect(false);
		double angle = 90.0;
		double v = 1.5;
		GameUtil.SetV(this, v, angle, false);
		this.setGroup(GameUtil.GROUP_ITEM);
		this.setLayer(GameUtil.LAYER_ITEM);
		this.setBound(false);
		this.setImg("item" + t);
		this.setImgup("item_up" + t);
		this.setAttract(0);
	}

	public void render() {
		double worldheight = GameModel.getInstance().getWorldHeight();
		if (this.getY() > worldheight)
			Renderer.getInstance().Render(this.getImgup(), this.getX(),
					worldheight - 8);
		else
			Renderer.getInstance().Render(this.getImg(), this.getX(),
					this.getY());
	}

	public void frame() {
		if (this.getTimer() < 24) {
			this.setRot(this.getRot() + 45);
			this.setHscale((this.getTimer() + 25) / 48);
			this.setVscale(this.getHscale());
			if (this.getTimer() == 22) {
				this.setVy(RandomUtil.Float(1.5, 2.0));
				this.setVx(0.0);
			}
		} else if (this.getAttract() > 0) {
			PlayerClass player = GameModel.getInstance().getPlayer();
			double a = GameUtil.Angle(this, player);
			this.setVx(this.getAttract() * MathUtils.cosDeg(a) + player.getDx()
					* 0.5);
			this.setVy(this.getAttract() * MathUtils.sinDeg(a) + player.getDy()
					* 0.5);
		} else {
			this.setVy(Math.max(this.getVy() - 0.03, -1.7));
		}
		if (this.getY() < 0)
			GameUtil.Del(this);
	}

	/**
	 * @param other
	 */
	public void colli(BaseObject other) {
		if (other == GameModel.getInstance().getPlayer()) {
			this.collect();
			GameUtil.Kill(this);
		}
	}

	public void collect() {
	}

	public static double getScBonusMax() {
		return scBonusMax;
	}

	public static void setScBonusMax(double scBonusMax) {
		Item.scBonusMax = scBonusMax;
	}

	public static double getScBonusBase() {
		return scBonusBase;
	}

	public static void setScBonusBase(double scBonusBase) {
		Item.scBonusBase = scBonusBase;
	}

	/**
	 * @param x
	 * @param y
	 * @param drop
	 */
	public static void dropItem(double x, double y, int[] drop) {
		int n = drop[0] + drop[1] + drop[2];
		if (n < 1)
			return;
		double r = Math.sqrt((double) (n - 1)) * 5;
		if (GameModel.getInstance().getPower() >= 500) {
			drop[1] += drop[0];
			drop[0] = 0;
		}
		for (int i = 0; i < drop[0]; i++) {
			double r2 = Math.sqrt(RandomUtil.Float(1.0, 4.0)) * r;
			double a = RandomUtil.Float(0, 360);
			GameModel
					.getInstance()
					.getObjList()
					.add(new ItemPower(x + r2 * MathUtils.cosDeg(a), y + r2
							* MathUtils.sinDeg(a)));
		}
		for (int i = 0; i < drop[1]; i++) {
			double r2 = Math.sqrt(RandomUtil.Float(1.0, 4.0)) * r;
			double a = RandomUtil.Float(0, 360);
			GameModel
					.getInstance()
					.getObjList()
					.add(new ItemFaith(x + r2 * MathUtils.cosDeg(a), y + r2
							* MathUtils.sinDeg(a)));
		}
		for (int i = 0; i < drop[2]; i++) {
			double r2 = Math.sqrt(RandomUtil.Float(1.0, 4.0)) * r;
			double a = RandomUtil.Float(0, 360);
			GameModel
					.getInstance()
					.getObjList()
					.add(new ItemPoint(x + r2 * MathUtils.cosDeg(a), y + r2
							* MathUtils.sinDeg(a)));
		}
	}

	public static void startChipBonus() {
		GameModel.getInstance().setChipBonus(true);
	}

	/**
	 * @param x
	 * @param y
	 */
	public static void endChipBonus(double x, double y) {
		if (GameModel.getInstance().isChipBonus()) {
			GameModel.getInstance().getObjList().add(new ItemChip(x, y));
			GameModel.getInstance().setChipBonus(false);
		}
	}

	public static void playerInit() {
		GameModel.getInstance().setPower(0);
		GameModel.getInstance().setLife(2);
		GameModel.getInstance().setChip(0);
		GameModel.getInstance().setFaith(50000);
		GameModel.getInstance().setGraze(0);
		GameModel.getInstance().setScore(0);
		GameModel.getInstance().setBlockSpell(false);
		GameModel.getInstance().setChipBonus(false);
	}

	public static void playerMiss() {
		GameModel.getInstance().setChipBonus(false);
		PlayerClass player = GameModel.getInstance().getPlayer();
		player.setProtect(360);
		GameModel.getInstance().setLife(GameModel.getInstance().getLife() - 1);
		GameModel.getInstance().setPower(
				Math.max(0, GameModel.getInstance().getPower() - 320));
		if (GameModel.getInstance().getLife() > 0) {
			for (int i = 1; i <= 7; i++) {
				double a = 90 + (i - 4) * 18;
				if (i % 2 == 1) {
					Item itemp = new ItemPower(player.getX(),
							player.getY() + 10);
					GameUtil.SetV(itemp, 3, a, false);
					GameModel.getInstance().getObjList().add(itemp);
				} else {
					Item itemp = new ItemPowerLarge(player.getX(),
							player.getY() + 10);
					GameUtil.SetV(itemp, 3, a, false);
					GameModel.getInstance().getObjList().add(itemp);
				}
			}
		} else {
			Item itemp = new ItemPowerFull(player.getX(), player.getY() + 10);
			GameModel.getInstance().getObjList().add(itemp);
		}
	}

	public static void playerGraze() {
		GameModel.getInstance()
				.setGraze(GameModel.getInstance().getGraze() + 1);
		GameModel.getInstance().setScore(
				GameModel.getInstance().getScore() + 50);
	}

	/**
	 * @param var
	 */
	public static int pointRateFunc() {
		int faith = GameModel.getInstance().getFaith();
		int graze = GameModel.getInstance().getGraze();
		int res = faith + faith * graze / 10000;
		return res - res % 20;
	}

	public String getImgup() {
		return imgup;
	}

	public void setImgup(String imgup) {
		this.imgup = imgup;
	}

	public int getAttract() {
		return attract;
	}

	public void setAttract(int attract) {
		this.attract = attract;
	}

}