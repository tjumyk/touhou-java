package org.tjuscs.bulletgame.model.item;

import org.tjuscs.bulletgame.model.GameModel;
import org.tjuscs.bulletgame.model.Item;

public class ItemPoint extends Item {
	public ItemPoint() {
	}

	public ItemPoint(double x, double y) {
		this.init(x, y, 2);
	}

	@Override
	public void collect() {
		if (this.getAttract() == 8) {
			GameModel.getInstance().setScore(
					GameModel.getInstance().getScore() + Item.pointRateFunc());
		} else {
			GameModel.getInstance().setScore(
					GameModel.getInstance().getScore() + Item.pointRateFunc()
							/ 2);
		}
	}
}
