package org.tjuscs.bulletgame.model.player;

import java.util.ArrayList;

import org.tjuscs.bulletgame.model.BaseObject;
import org.tjuscs.bulletgame.model.GameModel;
import org.tjuscs.bulletgame.model.PlayerClass;
import org.tjuscs.bulletgame.task.Task;
import org.tjuscs.bulletgame.task.Tasks;
import org.tjuscs.bulletgame.view.component.AudioPlayer;
import org.tjuscs.bulletgame.view.component.Renderer;
import org.tjuscs.bulletgame.view.component.Resources;

public class PlayerReimu extends PlayerClass {

	public PlayerReimu() {
	}

	@Override
	public void init() {
		super.init();
		this.setImgs(new ArrayList<String>());
		for (int i = 1; i <= 24; i++) {
			this.getImgs().add("reimu_player" + i);
		}
		double[][][] splist = {
				{ { 0, 0, 0, 0, -1 }, { 0, 0, 0, 0, -1 }, { 0, 0, 0, 0, -1 },
						{ 0, 0, 0, 0, -1 } },
				{ { 0, -32, 0, -32, 1 }, { 0, 0, 0, 0, -1 },
						{ 0, 0, 0, 0, -1 }, { 0, 0, 0, 0, -1 } },
				{ { -30, -10, -15, -20, 1 }, { 30, -10, 15, -20, 1 },
						{ 0, 0, 0, 0, -1 }, { 0, 0, 0, 0, -1 } },
				{ { -30, -10, -15, -20, 1 }, { 30, -10, 15, -20, 1 },
						{ 0, -32, 0, -32, 1 }, { 0, 0, 0, 0, -1 } },
				{ { -30, -10, -15, -20, 1 }, { 30, -10, 15, -20, 1 },
						{ -15, -32, -7.5, -32, 1 }, { 15, -32, 7.5, -32, 1 } },
				{ { -30, -10, -15, -20, 1 }, { 30, -10, 15, -20, 1 },
						{ -15, -32, -7.5, -32, 1 }, { 15, -32, 7.5, -32, 1 } } };
		this.setSlist(splist);
		this.setA(2);
		this.setB(2);
		this.setRect(false);
		this.setHasslist(true);
		Task task = Tasks.readScript("script/spell/reimu_sp.xml");
		task.setBinding("this", this);
		this.setTask(task);
		this.getTask().cancel();
	}

	@Override
	public void shoot() {
		ArrayList<BaseObject> objList = GameModel.getInstance().getObjList();
		this.setNextshoot(4);
		objList.add(new ReimuBulletRed("reimu_bullet_red", this.getX() + 6,
				this.getY(), 24, 90, 1));
		objList.add(new ReimuBulletRed("reimu_bullet_red", this.getX() - 6,
				this.getY(), 24, 90, 1));
		if (this.getTimer() % 8 < 4) {
			for (int i = 0; i <= 3; i++) {
				if (this.sp[i][2] > 0.5) {
					objList.add(new ReimuBulletBlue("reimu_bullet_blue", this
							.getSupportx() + this.sp[i][0], this.getSupporty()
							+ this.sp[i][1], 12, 90, this.getTarget(), 3600,
							1.25));
				}
			}
		}
	}

	@Override
	public void spell() {
		AudioPlayer.getInstance().PlaySound("gun00", 1.0);
		this.getTask().reset();
		this.setNextspell(240);
		this.setProtect(360);
	}

	@Override
	public void render() {
		Resources resources = Resources.getInstance();
		Renderer render = Renderer.getInstance();
		double rot = this.getAni() * 5;
		if (this.getSupport() == 5) {
			resources.SetImageState("reimu_support", "", 255, 255, 255, 128);
			for (int i = 0; i <= 3; i++) {
				render.Render("reimu_support", this.getSupportx()
						+ this.sp[i][0], this.getSupporty() + this.sp[i][1],
						-rot, 1.3);
			}
			resources.SetImageState("reimu_support", "", 255, 255, 255, 255);
		}
		for (int i = 0; i <= 3; i++) {
			if (this.sp[i][2] > 0.5)
				render.Render("reimu_support", this.getSupportx()
						+ this.sp[i][0], this.getSupporty() + this.sp[i][1],
						rot, 1);
		}
		super.render();
	}

}
