package org.tjuscs.bulletgame.model.player;

import org.tjuscs.bulletgame.model.PlayerBulletHide;
import org.tjuscs.bulletgame.util.GameUtil;

public class ReimuSpEffect extends PlayerBulletHide {

	private int life;

	public ReimuSpEffect(double a, double b, double x, double y, double v,
			double angle, double dmg, int delay, int life) {
		this.init(a, b, x, y, v, angle, dmg, delay, life);
	}

	public void init(double a, double b, double x, double y, double v,
			double angle, double dmg, int delay, int life) {
		super.init(a, b, x, y, v, angle, dmg, delay);
		this.setLife(life);
	}

	public void frame() {
		if (this.getTimer() == this.getDelay())
			this.setColli(true);
		if (this.getTimer() == this.getLife())
			GameUtil.Del(this);
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
}
