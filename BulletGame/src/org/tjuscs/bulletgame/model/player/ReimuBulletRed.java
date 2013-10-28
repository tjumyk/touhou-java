package org.tjuscs.bulletgame.model.player;

import org.tjuscs.bulletgame.model.PlayerBulletStraight;
import org.tjuscs.bulletgame.view.component.Renderer;

public class ReimuBulletRed extends PlayerBulletStraight {

	public ReimuBulletRed(String img, double x, double y, double v,
			double angle, double dmg) {
		super.init(img, x, y, v, angle, dmg);
	}

	@Override
	public void render() {
		Renderer.getInstance().Render(this.getImg(), this.getX(), this.getY(),
				this.getRot());
	}

	@Override
	public void init(String img, double x, double y, double v, double angle,
			double dmg) {
		super.init(img, x, y, v, angle, dmg);
		this.setA(16);
		this.setB(16);
	}

}
