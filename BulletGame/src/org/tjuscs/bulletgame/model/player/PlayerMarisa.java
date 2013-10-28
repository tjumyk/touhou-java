package org.tjuscs.bulletgame.model.player;

import java.util.ArrayList;

import org.tjuscs.bulletgame.model.PlayerClass;
import org.tjuscs.bulletgame.view.component.Resources;

public class PlayerMarisa extends PlayerClass {
	public PlayerMarisa() {
	}

	@Override
	public void init() {
		Resources resource = Resources.getInstance();
		resource.LoadTexture("marisa_player",
				"THlib\\player\\marisa\\marisa.png");
		resource.LoadImageGroup("marisa_player", "marisa_player", 0, 0, 32, 48,
				8, 3, 0, 0);
		resource.LoadImage("marisa_bullet", "marisa_player", 0, 144, 32, 16,
				16, 16);
		resource.LoadAnimation("marisa_bullet_ef", "marisa_player", 0, 144, 32,
				16, 4, 1, 4);
		resource.SetImageState("marisa_bullet", "", 255, 255, 255, 128);
		resource.LoadImage("marisa_support", "marisa_player", 144, 144, 16, 16);
		resource.LoadImage("marisa_laser_light", "marisa_player", 224, 224, 32,
				32);
		resource.SetImageState("marisa_laser_light", "mul+add", 255, 255, 255,
				128);
		resource.LoadPSPool("marisa_sp_ef",
				"THlib\\player\\marisa\\marisa_sp_ef.p", "particles6");
		super.init();
		this.setImgs(new ArrayList<String>());
		for (int i = 1; i <= 24; i++) {
			this.getImgs().add("marisa_player" + i);
		}
		this.setHspeed(5.0);
		this.setLspeed(2.5);
		double[][][] splist = {
				{ { 0, 0, 0, 0, -1 }, { 0, 0, 0, 0, -1 }, { 0, 0, 0, 0, -1 },
						{ 0, 0, 0, 0, -1 } },
				{ { 0, 32, 0, 29, 1 }, { 0, 0, 0, 0, -1 }, { 0, 0, 0, 0, -1 },
						{ 0, 0, 0, 0, -1 } },
				{ { -30, 10, -8, 23, 1 }, { 30, 10, 8, 23, 1 },
						{ 0, 0, 0, 0, -1 }, { 0, 0, 0, 0, -1 } },
				{ { -30, 10, -15, 20, 1 }, { 30, 10, 15, 20, 1 },
						{ 0, 32, 0, 29, 1 }, { 0, 0, 0, 0, -1 } },
				{ { -30, 10, -15, 20, 1 }, { 30, 10, 15, 20, 1 },
						{ -7.5, 32, -7.5, 29, 1 }, { 7.5, 32, 7.5, 29, 1 } },
				{ { -30, 10, -15, 20, 1 }, { 30, 10, 15, 20, 1 },
						{ -7.5, 32, -7.5, 29, 1 }, { 7.5, 32, 7.5, 29, 1 } } };
		this.setSlist(splist);
	}

	@Override
	public void shoot() {
		// TODO Auto-generated method stub
	}

	@Override
	public void spell() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
	}

}
