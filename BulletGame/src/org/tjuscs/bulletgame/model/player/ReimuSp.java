package org.tjuscs.bulletgame.model.player;

import java.util.List;

import org.tjuscs.bulletgame.model.BaseObject;
import org.tjuscs.bulletgame.model.Bubble;
import org.tjuscs.bulletgame.model.GameModel;
import org.tjuscs.bulletgame.model.PlayerBulletTrail;
import org.tjuscs.bulletgame.util.GameUtil;
import org.tjuscs.bulletgame.view.component.AudioPlayer;
import org.tjuscs.bulletgame.view.component.Renderer;

import com.badlogic.gdx.Gdx;

public class ReimuSp extends PlayerBulletTrail {
	public ReimuSp(String img, double x, double y, double v, double angle,
			BaseObject target, double trail, double dmg) {
		this.init(img, x, y, v, angle, target, trail, dmg);
	}

	@Override
	public void init(String img, double x, double y, double v, double angle,
			BaseObject target, double trail, double dmg) {
		super.init(img, x, y, v, angle, target, trail, dmg);
		this.setA(32);
		this.setB(32);
	}

	@Override
	public void kill() {
		AudioPlayer.getInstance().PlaySound("explode", 0.3);
		List<BaseObject> objList = GameModel.getInstance().getObjList();
		objList.add(new Bubble("parimg12", this.getX(), this.getY(), 30, 4, 6,
				GameUtil.LAYER_ENEMY_BULLET_EF));
		for (int i = 1; i <= 16; i++) {
			objList.add(new ReimuSpEffect(16, 16, this.getX(), this.getY(), 3,
					360 / 16.0 * i, 1, 2, 300));
		}
	}

	@Override
	public void frame() {
		if (target == null || !GameUtil.IsValid(target) || !target.isColli()) {
			target = GameModel.getInstance().getPlayer().getTarget();
		}
		super.frame();
	}

	@Override
	public void render() {
		Renderer.getInstance().RenderParticle(this.getImg(),
				Gdx.graphics.getDeltaTime(), this.getX(), this.getY(), 0);
	}

}
