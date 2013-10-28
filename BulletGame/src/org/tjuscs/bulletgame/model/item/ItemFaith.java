package org.tjuscs.bulletgame.model.item;

import org.tjuscs.bulletgame.model.GameModel;
import org.tjuscs.bulletgame.model.Item;

public class ItemFaith extends Item {
	public ItemFaith() {
	}

	public ItemFaith(double x, double y) {
		this.init(x, y, 5);
	}

	@Override
	public void collect() {
		GameModel.getInstance().setFaith(
				GameModel.getInstance().getFaith() + 100);
		GameModel.getInstance().setScore(
				GameModel.getInstance().getScore() + 10000);
	}
}
