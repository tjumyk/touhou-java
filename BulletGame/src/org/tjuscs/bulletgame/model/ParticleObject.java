package org.tjuscs.bulletgame.model;

public class ParticleObject extends BaseObject {
	private boolean fireParticle = true;

	public ParticleObject() {
	}

	@Override
	public void init() {
		super.init();
		this.setFireParticle(true);
	}

	public boolean isFireParticle() {
		return fireParticle;
	}

	public void setFireParticle(boolean fireParticle) {
		this.fireParticle = fireParticle;
	}
}
