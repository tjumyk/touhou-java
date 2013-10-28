package org.tjuscs.bulletgame.model.player;

import org.tjuscs.bulletgame.model.BaseObject;
import org.tjuscs.bulletgame.model.PlayerBulletTrail;
import org.tjuscs.bulletgame.view.component.Renderer;

public class ReimuBulletBlue extends PlayerBulletTrail {

	public ReimuBulletBlue(String img, double x, double y, double v,
			double angle, BaseObject target, double trail, double dmg) {
		this.init(img, x, y, v, angle, target, trail, dmg);
	}

	@Override
	public void render() {
		Renderer.getInstance().Render(this.getImg(), this.getX(), this.getY(),
				this.getRot());
	}

	@Override
	public void init(String img, double x, double y, double v, double angle,
			BaseObject target, double trail, double dmg) {
		super.init(img, x, y, v, angle, target, trail, dmg);
		this.setA(16);
		this.setB(16);
	}

}
