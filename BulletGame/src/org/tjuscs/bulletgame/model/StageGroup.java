package org.tjuscs.bulletgame.model;

import java.util.ArrayList;

public class StageGroup {

	/** 关卡组名 */
	private String title;
	/** 关卡组下关卡列表 */
	private ArrayList<Stage> stage_group;

	public StageGroup(String title) {
		super();
		this.title = title;
		stage_group = new ArrayList<Stage>();
	}

	public void addStage(Stage stage) {
		stage_group.add(stage);
	}
	
	public void addStage(String stagename, int life, int power, int faith) {
		stage_group.add(new Stage(stagename, life, power, faith));
	}

	public Stage getStagebyId(int id) {
		return stage_group.get(id);
	}

	public ArrayList<Stage> getStage_group() {
		return stage_group;
	}

	public void setStage_group(ArrayList<Stage> stage_group) {
		this.stage_group = stage_group;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
