package org.tjuscs.bulletgame.model;

import java.util.ArrayList;

import org.tjuscs.bulletgame.util.GameUtil;
import org.tjuscs.bulletgame.util.MathUtils;
import org.tjuscs.bulletgame.view.component.Renderer;

/***********************************************************************
 * Module: Enemy.java Author: Administrator Purpose: Defines the Class Enemy
 ***********************************************************************/

public class Enemy extends Enemybase {

	// 对象的光环图id，0表示没有光环
	public final static int[] Enemy_aura_tb = { 1, 2, 3, 4, 3, 1, 0, 0, 0, 3,
			1, 4, 1, 0, 3, 1, 2, 4, 3, 1, 2, 4, 1, 2, 3, 4 };
	// 对象死亡时Bubble的id
	public final static int[] Death_ef_tb = { 1, 2, 3, 4, 3, 1, 1, 2, 1, 3, 1,
			4, 1, 1, 3, 1, 2, 4, 3, 1, 2, 4, 1, 2, 3, 4 };
	public final static int[] Enemy_a_tb = { 16, 16, 16, 16, 16, 16, 32, 32,
			32, 16, 16, 16, 16, 32, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
			16 };
	public final static int[] Enemy_b_tb = { 16, 16, 16, 16, 16, 16, 32, 32,
			32, 16, 16, 16, 16, 32, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,
			16 };;

	private int style;
	private boolean clearBullet;
	private boolean autoDelete;
	private int aura;
	private int deathEf;
	private int aniIntv;
	private int lr;
	private int dx;

	public int getDx() {
		return dx;
	}

	public void setDx(int dx) {
		this.dx = dx;
	}

	public Enemy() {
	}

	public Enemy(int style, double hp, boolean clear_bullet, boolean auto_delete) {
		this.init(style, hp, clear_bullet, auto_delete);
	}

	/**
	 * @param style
	 * @param hp
	 * @param clearBullet
	 * @param autoDelete
	 */
	public void init(int style, double hp, boolean clearBullet,
			boolean autoDelete) {
		super.init(hp);
		this.setHp(hp);
		this.setClearBullet(clearBullet);
		this.setAutoDelete(autoDelete);
		this.setStyle(style);
		this.setAura(Enemy_aura_tb[style - 1]);
		this.setDeathEf(Death_ef_tb[style - 1]);
		this.setRect(false);
		this.setA(Enemy_a_tb[style - 1]);
		this.setB(Enemy_b_tb[style - 1]);
		if (style <= 18) {
			this.setImgs(new ArrayList<String>());
			for (int i = 1; i <= 12; i++) {
				this.getImgs().add("enemy" + style + "_" + i);
			}
			this.setAniIntv(8);
			this.setLr(1);
		} else if (style <= 22) {
			this.setImg("kedama" + (style - 18));
			this.setOmiga(12);
		} else if (style <= 26) {
			this.setImg("enemy_orb" + (style - 22));
		}
	}

	public void frame() {
		super.frame();
		if (this.getStyle() <= 18) {
			if (this.getVx() > 0.5)
				dx = 1;
			else if (this.getVx() < -0.5)
				dx = -1;
			else
				dx = 0;
			this.setLr(Math.min(Math.max((this.getLr() + dx), -18), 18));
			if (this.getLr() == 0)
				this.setLr(this.getLr() + dx);
			if (dx == 0) {
				if (this.getLr() > 1)
					this.setLr(this.getLr() - 1);
				if (this.getLr() < -1)
					this.setLr(this.getLr() + 1);
			}
			if (Math.abs(this.getLr()) == 1) {
				this.setImg(this.getImgs().get(
						(this.getAni() / this.getAniIntv()) % 4));
			} else if (Math.abs(this.getLr()) == 18) {
				this.setImg(this.getImgs().get(
						(this.getAni() / this.getAniIntv()) % 4 + 8));
			} else {
				this.setImg(this.getImgs()
						.get((Math.abs(this.getLr()) - 2) / 4) + 4);
			}
			if (this.getLr() >= 0)
				this.setHscale(1);
			else
				this.setHscale(-1);
		}
		if (this.isAutoDelete()
				&& GameUtil.BoxCheck(this, 0, GameModel.getInstance()
						.getWorldWidth(), 0, GameModel.getInstance()
						.getWorldHeight())) {
			this.setBound(true);
		}
	}

	public void render() {
		Renderer renderer = Renderer.getInstance();
		if (this.getAura() != 0) {
			renderer.Render("enemy_aura" + this.getAura(), this.getX(),
					this.getY(), this.getTimer() * 3,
					1.25 + 0.15 * MathUtils.sinDeg(this.getTimer() * 6));
		}
		renderer.Render(this.getImg(), this.getX(), this.getY(), this.getRot());
		if (this.getStyle() > 22) {
			renderer.Render("enemy_orb_ring" + this.getAura(), this.getX(),
					this.getY(), -this.getTimer() * 6);
			renderer.Render("enemy_orb_ring" + this.getAura(), this.getX(),
					this.getY(), this.getTimer() * 4, 1.4);
		}
	}

	public void takeDamage(double dmg) {
		if (!this.isProtect())
			this.setHp(this.getHp() - dmg);
	}

	public void kill() {
		Item.dropItem(this.getX(), this.getY(), this.getDrop());
		if (this.isClearBullet())
			GameUtil.BulletKiller(this.getX(), this.getY(), false);
		killServants(this);
		GameModel
				.getInstance()
				.getObjList()
				.add(new EnemyDeath(this.getDeathEf(), this.getX(), this.getY()));
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public boolean isClearBullet() {
		return clearBullet;
	}

	public void setClearBullet(boolean clearBullet) {
		this.clearBullet = clearBullet;
	}

	public boolean isAutoDelete() {
		return autoDelete;
	}

	public void setAutoDelete(boolean autoDelete) {
		this.autoDelete = autoDelete;
	}

	public int getAura() {
		return aura;
	}

	public void setAura(int aura) {
		this.aura = aura;
	}

	public int getDeathEf() {
		return deathEf;
	}

	public void setDeathEf(int deathEf) {
		this.deathEf = deathEf;
	}

	public int getAniIntv() {
		return aniIntv;
	}

	public void setAniIntv(int aniIntv) {
		this.aniIntv = aniIntv;
	}

	public int getLr() {
		return lr;
	}

	public void setLr(int lr) {
		this.lr = lr;
	}

}