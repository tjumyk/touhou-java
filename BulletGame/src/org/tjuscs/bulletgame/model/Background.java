package org.tjuscs.bulletgame.model;

import java.util.HashMap;
import java.util.Map;

import org.tjuscs.bulletgame.task.Task;
import org.tjuscs.bulletgame.task.TaskList;
import org.tjuscs.bulletgame.util.GameUtil;
import org.tjuscs.bulletgame.view.component.Renderer;

/***********************************************************************
 * Module:  Background.java
 * Author:  Administrator
 * Purpose: Defines the Class Background
 ***********************************************************************/

/**
 * 背景
 * 
 * @pdOid 7c0da75d-2f5b-45ea-b580-0859944e9f1a
 */
public class Background extends BaseObject {
	private double alpha;
	private Task initTask, frameTask, renderTask;
	private Map<String, Object> data = new HashMap<String, Object>();

	public Task getFrameTask() {
		return frameTask;
	}

	public void setFrameTask(Task frameTask) {
		this.frameTask = frameTask;
	}

	public Background(boolean isScBg, String scriptPath) {
		super();
		TaskList tl = TaskList.readScript(scriptPath);
		tl.setBinding("data", data);
		tl.setBinding("bg", this);
		initTask = tl.get("init");
		frameTask = tl.get("frame");
		renderTask = tl.get("render");
		this.init(isScBg);

		initTask.act();
		initTask.reset();
	}

	/**
	 * 符卡背景初始透明
	 * 
	 * @param isScBg
	 */
	public void init(boolean isScBg) {
		this.setGroup(GameUtil.GROUP_GHOST);
		if (isScBg) {
			setLayer(GameUtil.LAYER_BG);
			setAlpha(0);
		} else {
			setLayer(GameUtil.LAYER_BG - 0.1);
			setAlpha(1);
		}
	}

	@Override
	public void frame() {
		setTimer(getTimer() + 1);
		frameTask.act();
		frameTask.reset();
	}

	@Override
	public void render() {
		Renderer.getInstance().setMode(Renderer.MODE_3D);
		renderTask.act();
		renderTask.reset();
		Renderer.getInstance().setMode(Renderer.MODE_2D);
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
}