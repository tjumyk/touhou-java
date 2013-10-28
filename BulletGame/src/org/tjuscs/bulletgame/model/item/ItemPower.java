package org.tjuscs.bulletgame.model.item;

import org.tjuscs.bulletgame.model.Item;
import org.tjuscs.bulletgame.util.GameUtil;

public class ItemPower extends Item {
	public ItemPower() {
	}

	public ItemPower(double x, double y) {
		this.init(x, y, 1);
	}

	@Override
	public void collect() {
		GameUtil.GetPower(5);
	}

}
