package org.tjuscs.bulletgame.model.item;

import org.tjuscs.bulletgame.model.GameModel;
import org.tjuscs.bulletgame.model.Item;

public class ItemExtend extends Item {
	public ItemExtend() {
	}

	public ItemExtend(double x, double y) {
		this.init(x, y, 7);
	}

	@Override
	public void collect() {
		GameModel.getInstance().setLife(GameModel.getInstance().getLife() + 1);
	}
}
