package org.tjuscs.bulletgame.model;

import org.tjuscs.bulletgame.util.GameUtil;
import org.tjuscs.bulletgame.view.component.Renderer;

import com.badlogic.gdx.Gdx;

/***********************************************************************
 * Module: Grazer.java Author: Administrator Purpose: Defines the Class Grazer
 ***********************************************************************/

public class Grazer extends ParticleObject {
	private boolean grazed;

	public Grazer() {
	}

	public void init() {
		super.init();
		this.setGroup(GameUtil.GROUP_PLAYER);
		this.setLayer(GameUtil.LAYER_ENEMY_BULLET_EF);
		this.setGrazed(false);
		this.setImg("graze");
		this.setFireParticle(false);
		this.setRect(false);
		this.setA(24);
		this.setB(24);
	}

	public void frame() {
		PlayerClass player = GameModel.getInstance().getPlayer();
		this.setX(player.getX());
		this.setY(player.getY());
		this.setHide(player.isHide());
		if (this.isGrazed()) {
			this.setGrazed(false);
			this.setFireParticle(true);
		} else {
			this.setFireParticle(false);
		}
	}

	public void render() {
		if (this.isFireParticle()) {
			Renderer.getInstance().RenderParticle(this.getImg(),
					Gdx.graphics.getDeltaTime(), this.getX(), this.getY(), 0);
		}
	}

	/**
	 * @param other
	 */
	public void colli(BaseObject other) {
		if (other.getGroup() != GameUtil.GROUP_ENEMY) {
			Bullet otherBullet = (Bullet) other;
			if (!otherBullet.isHasgrazed()) {
				Item.playerGraze();
				this.setGrazed(true);
				otherBullet.setHasgrazed(true);
			}
		}
	}

	public boolean isGrazed() {
		return grazed;
	}

	public void setGrazed(boolean grazed) {
		this.grazed = grazed;
	}
}