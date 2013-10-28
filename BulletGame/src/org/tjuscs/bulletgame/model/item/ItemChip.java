package org.tjuscs.bulletgame.model.item;

import org.tjuscs.bulletgame.model.GameModel;
import org.tjuscs.bulletgame.model.Item;

public class ItemChip extends Item {
	public ItemChip() {
	}

	public ItemChip(double x, double y) {
		this.init(x, y, 3);
	}

	@Override
	public void collect() {
		int chip = GameModel.getInstance().getChip();
		chip += 1;
		if (chip >= 5) {
			GameModel.getInstance().setLife(
					GameModel.getInstance().getLife() + 1);
			chip = 0;
		}
		GameModel.getInstance().setChip(chip);
	}
}
