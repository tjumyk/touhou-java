package org.tjuscs.bulletgame.model;

import java.util.List;

import org.tjuscs.bulletgame.util.GameUtil;

public class BulletDeleter extends BaseObject {
	private boolean killIndes;

	public BulletDeleter(double x, double y, boolean killindes) {
		this.init(x, y, killindes);
	}

	public void init(double x, double y, boolean killindes) {
		super.init();
		this.setX(x);
		this.setY(y);
		this.setHide(true);
		this.setKillIndes(killindes);
	}

	@Override
	public void frame() {
		if (this.getTimer() == 40)
			GameUtil.Del(this);
		List<BaseObject> objList = GameModel.getInstance().getObjList();
		if (this.isKillIndes()) {
			for (BaseObject obj : objList) {
				if ((obj.getGroup() == GameUtil.GROUP_ENEMY_BULLET || obj
						.getGroup() == GameUtil.GROUP_INDES)
						&& GameUtil.Dist(this, obj) < this.getTimer() * 20) {
					GameUtil.Del(obj);
				}
			}
		} else {
			for (BaseObject obj : objList) {
				if (obj.getGroup() == GameUtil.GROUP_ENEMY_BULLET
						&& GameUtil.Dist(this, obj) < this.getTimer() * 20) {
					GameUtil.Del(obj);
				}
			}
		}
	}

	public boolean isKillIndes() {
		return killIndes;
	}

	public void setKillIndes(boolean killIndes) {
		this.killIndes = killIndes;
	}
}
