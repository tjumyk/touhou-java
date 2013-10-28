package org.tjuscs.bulletgame.model.item;

import org.tjuscs.bulletgame.model.GameModel;
import org.tjuscs.bulletgame.model.Item;
import org.tjuscs.bulletgame.util.GameUtil;

public class ItemFaithMinor extends Item {
	public ItemFaithMinor() {
	}

	public ItemFaithMinor(double x, double y) {
		this.init(x, y);
	}

	public void init(double x, double y) {
		this.setX(x);
		this.setY(y);
		super.init(x, y, 8);
		this.setVx(0);
		this.setVy(0);
		if (!GameUtil.BoxCheck(this, 0,
				GameModel.getInstance().getWorldWidth(), 0, GameModel
						.getInstance().getWorldHeight()))
			GameUtil.RawDel(this);
	}

	@Override
	public void frame() {
		if (this.getTimer() >= 24)
			GameUtil.SetV(this, 8,
					GameUtil.Angle(this, GameModel.getInstance().getPlayer()),
					false);
	}

	@Override
	public void collect() {
		GameModel.getInstance()
				.setFaith(GameModel.getInstance().getFaith() + 5);
		GameModel.getInstance().setScore(
				GameModel.getInstance().getScore() + 500);
	}
}
