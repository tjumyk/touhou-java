package org.tjuscs.bulletgame.model;

import org.tjuscs.bulletgame.util.GameUtil;
import org.tjuscs.bulletgame.view.component.AudioPlayer;
import org.tjuscs.bulletgame.view.component.Renderer;
import org.tjuscs.bulletgame.view.component.Resources;

public class EnemyDeath extends BaseObject {

	public EnemyDeath() {
	}

	public EnemyDeath(int index, double x, double y) {
		this.init(index, x, y);
	}

	public void init(int index, double x, double y) {
		super.init();
		this.setImg("bubble" + index);
		this.setLayer(GameUtil.LAYER_ENEMY + 50);
		this.setGroup(GameUtil.GROUP_GHOST);
		this.setX(x);
		this.setY(y);
		this.setRot(45);
		AudioPlayer.getInstance().PlaySound("enep00", 0.3);
	}

	@Override
	public void frame() {
		if (this.getTimer() == 30)
			GameUtil.Kill(this);
	}

	@Override
	public void render() {
		Resources resources = Resources.getInstance();
		Renderer render = Renderer.getInstance();
		double alpha = 1 - this.getTimer() / 30;
		alpha = 255 * alpha * alpha;
		resources.SetImageState(this.getImg(), "", 255, 255, 255, alpha);
		double scale = 0.4 - this.getTimer() * 0.01;
		render.Render(this.getImg(), this.getX(), this.getY(), 15, scale);
		render.Render(this.getImg(), this.getX(), this.getY(), 75, scale);
		render.Render(this.getImg(), this.getX(), this.getY(), 135, scale);
	}

}
