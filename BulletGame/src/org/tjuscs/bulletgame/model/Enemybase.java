package org.tjuscs.bulletgame.model;

/***********************************************************************
 * Module:  Enemybase.java
 * Author:  Administrator
 * Purpose: Defines the Class Enemybase
 ***********************************************************************/

import java.util.ArrayList;
import java.util.List;

import org.tjuscs.bulletgame.util.GameUtil;

public class Enemybase extends BaseObject {
	private boolean protect = false;
	private double hp;
	private List<String> imgs;
	private double maxhp;
	private List<BaseObject> servants;
	private BaseObject master;
	private int[] drop = { 0, 0, 0 };

	public Enemybase() {
	}

	public Enemybase(double hp) {
		this.init(hp);
	}

	/**
	 * @param other
	 */
	public void colli(BaseObject other) {
		PlayerBullet otherblt = (PlayerBullet) other;
		if (otherblt.getDmg() != 0) {
			this.takeDamage(otherblt.getDmg());
		}
		GameUtil.Kill(other);
	}

	public void takeDamage(double dmg) {
		if (!this.isProtect())
			this.setHp(this.getHp() - dmg);
	}

	public void del() {
		delServants(this);
	}

	public void frame() {
		if (this.hp <= 0)
			GameUtil.Kill(this);
		this.getTask().act();
	}

	public void killServants(Enemybase master) {
		for (BaseObject servant : this.servants) {
			if (GameUtil.IsValid(servant))
				GameUtil.Kill(servant);
		}
		master.setServants(new ArrayList<BaseObject>());
	}

	public void delServants(Enemybase master) {
		for (BaseObject servant : this.servants) {
			if (GameUtil.IsValid(servant))
				GameUtil.Del(servant);
		}
		master.setServants(new ArrayList<BaseObject>());
	}

	public double getHp() {
		return hp;
	}

	public List<String> getImgs() {
		return imgs;
	}

	public BaseObject getMaster() {
		return master;
	}

	public double getMaxhp() {
		return maxhp;
	}

	public List<BaseObject> getServants() {
		return servants;
	}

	/**
	 * @param hp
	 */
	public void init(double hp) {
		super.init();
		this.setLayer(GameUtil.LAYER_ENEMY);
		this.setGroup(GameUtil.GROUP_ENEMY);
		this.setBound(false);
		this.setColli(true);
		this.setMaxhp(hp);
		this.setHp(hp);
		this.servants = new ArrayList<BaseObject>();
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}

	public void setMaster(BaseObject master) {
		this.master = master;
	}

	public void setMaxhp(double maxhp) {
		this.maxhp = maxhp;
	}

	public void setServants(List<BaseObject> servants) {
		this.servants = servants;
	}

	public boolean isProtect() {
		return protect;
	}

	public void setProtect(boolean protect) {
		this.protect = protect;
	}

	public int[] getDrop() {
		return drop;
	}

	public void setDrop(int[] drop) {
		this.drop = drop;
	}
}