package org.tjuscs.bulletgame.model;

import org.tjuscs.bulletgame.util.GameUtil;
import org.tjuscs.bulletgame.view.component.Renderer;

import com.badlogic.gdx.Gdx;

public class PlayerDeath extends ParticleObject {

	public PlayerDeath() {
	}

	public PlayerDeath(double x, double y) {
		this.init(x, y);
	}

	@Override
	public void frame() {
		if (this.getTimer() == 4) {
			this.setFireParticle(false);
		}
		if (this.getTimer() == 60) {
			GameUtil.Del(this);
		}
	}

	public void init(double x, double y) {
		super.init();
		this.setX(x);
		this.setY(y);
		this.setImg("player_death_ef");
		this.setLayer(GameUtil.LAYER_PLAYER + 50);
		this.setGroup(GameUtil.GROUP_GHOST);
	}

	@Override
	public void render() {
		if (this.isFireParticle()) {
			Renderer.getInstance().RenderParticle(this.getImg(),
					Gdx.graphics.getDeltaTime(), this.getX(), this.getY(), 0);
		}
	}
}
