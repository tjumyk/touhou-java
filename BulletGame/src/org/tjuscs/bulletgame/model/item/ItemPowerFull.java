package org.tjuscs.bulletgame.model.item;

import org.tjuscs.bulletgame.model.Item;
import org.tjuscs.bulletgame.util.GameUtil;

public class ItemPowerFull extends Item {
	public ItemPowerFull() {
	}

	public ItemPowerFull(double x, double y) {
		this.init(x, y, 4);
	}

	@Override
	public void collect() {
		GameUtil.GetPower(500);
	}
}
