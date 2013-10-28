package org.tjuscs.bulletgame.model.item;

import org.tjuscs.bulletgame.model.Item;
import org.tjuscs.bulletgame.util.GameUtil;

public class ItemPowerLarge extends Item {
	public ItemPowerLarge() {
	}

	public ItemPowerLarge(double x, double y) {
		this.init(x, y, 6);
	}

	@Override
	public void collect() {
		GameUtil.GetPower(100);
	}
}
