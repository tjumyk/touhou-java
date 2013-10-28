package org.tjuscs.bulletgame.model;

import java.util.ArrayList;

import org.tjuscs.bulletgame.BulletGame;
import org.tjuscs.bulletgame.collide.CollisionCheck;
import org.tjuscs.bulletgame.collide.GeometryBase;
import org.tjuscs.bulletgame.collide.GeometryEllipse;
import org.tjuscs.bulletgame.collide.GeometryRec;
import org.tjuscs.bulletgame.model.player.PlayerReimu;
import org.tjuscs.bulletgame.task.Tasks;
import org.tjuscs.bulletgame.util.GameUtil;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Pool;

public class GameModel {

	private static GameModel Instance = null;

	private GameModel() {
	}

	public static GameModel getInstance() {
		if (Instance == null) {
			Instance = new GameModel();
		}
		return Instance;
	}

	private int screenWidth, screenHeight;
	private int worldWidth, worldHeight;
	private ArrayList<StageGroup> gameStage = new ArrayList<StageGroup>();
	private Stage currentStage = null;
	private boolean isPratice, pauseFlag, quitFlag, bossWait;
	private PlayerClass player = null;
	private int diffcultid, stageid;
	private ArrayList<BaseObject> objList = new ArrayList<BaseObject>();
	private int life = 0, power = 0, faith = 0, chip = 0, graze = 0, score = 0;
	private boolean blockSpell = false, chipBonus = false;
	private Background gameBG = null;
	// 下面4个boolean变量每帧有相应自机操作时设为true，否则为false
	private boolean keyShoot, keySpell, keySlow, keyMove;
	// 当前帧自机移动的角度
	private double playerMoveAngel = 0;
	private Pool<Bullet> bulletPool = new Pool<Bullet>(500) {

		@Override
		protected Bullet newObject() {
			return new Bullet();
		}

	};

	public Pool<Bullet> getBulletPool() {
		return bulletPool;
	}

	public void init() {
		// 初始化stagegroup 及 stage
		Tasks.readScript("script/loadStage.xml").act();

		Item.playerInit();
		startStage(0, 0);
		this.player = new PlayerReimu();
		getObjList().add(player.getGrazer());
		getObjList().add(player);
	}

	public void frame() {
		if(life < 0){
			Gdx.input.getTextInput(new TextInputListener() {
				
				@Override
				public void input(String text) {
					Preferences pref = Gdx.app.getPreferences("highscore");
					pref.putInteger(text, GameModel.this.getScore());
					pref.flush();
				}
				
				@Override
				public void canceled() {
					
				}
			}, "大侠！请从头来过！请留下您的大名：", "玩家");
			BulletGame.setCurrentScreen(BulletGame.homeScreen);
			objList.clear();
			return;
		}
		currentStage.getTask().act();
		if(this.gameBG != null)
			this.gameBG.frame();
		for (int i = 0; i < getObjList().size(); i++) {
			BaseObject obj = objList.get(i);
			if (!GameUtil.IsValid(obj)) {
				if (obj instanceof Bullet) {
					getBulletPool().free((Bullet) obj);
				}
				getObjList().remove(i);
				i--;
			} else {
				obj.frame();
			}
		}

		for (BaseObject obj : getObjList()) {
			GameUtil.Movement(obj);
		}

		// 碰撞检测
		for (int i = 0; i < getObjList().size(); i++) {
			BaseObject obj1 = getObjList().get(i);
			if (!obj1.isColli())
				continue;

			for (int j = 0; j < getObjList().size(); j++) {
				BaseObject obj2 = getObjList().get(j);
				if (obj1 == obj2 || !obj2.isColli())
					continue;
				if ((obj1.getGroup() == GameUtil.GROUP_PLAYER && obj2
						.getGroup() == GameUtil.GROUP_ENEMY_BULLET)
						|| (obj1.getGroup() == GameUtil.GROUP_PLAYER && obj2
								.getGroup() == GameUtil.GROUP_ENEMY)
						|| (obj1.getGroup() == GameUtil.GROUP_PLAYER && obj2
								.getGroup() == GameUtil.GROUP_INDES)
						|| (obj1.getGroup() == GameUtil.GROUP_ENEMY && obj2
								.getGroup() == GameUtil.GROUP_PLAYER_BULLET)
						|| (obj1.getGroup() == GameUtil.GROUP_ITEM && obj2
								.getGroup() == GameUtil.GROUP_PLAYER)) {
					if (CollisionCheck.check(getGeometry(obj1),
							getGeometry(obj2))) {
						obj1.colli(obj2);
					}
				}
			}
		}
	}

	/**
	 * 根据{@link BaseObject}获取用于碰撞检测的几何外形对象
	 * 
	 * @param obj
	 * @return
	 */
	private GeometryBase getGeometry(BaseObject obj) {
		if (obj.isRect())
			return new GeometryRec(obj.getX(), obj.getY(), 2 * obj.getA(),
					2 * obj.getB(), obj.getRot());
		else
			return new GeometryEllipse(obj.getX(), obj.getY(), 2 * obj.getA(),
					obj.getRot());
	}

	public void startStage(int diffcultid, int stageid) {
		this.diffcultid = diffcultid;
		this.stageid = stageid;
		this.currentStage = getStageGroupbyId(diffcultid).getStagebyId(stageid);
		this.life = getCurrentStage().getLife();
		this.power = getCurrentStage().getPower();
		this.faith = getCurrentStage().getFaith();
		// TODO 开始当前关卡游戏
		currentStage.getTask().reset();
	}

	public void addStageGroup(String sgname) {
		gameStage.add(new StageGroup(sgname));
	}

	public void nextStage() {
		this.stageid++;
		if (stageid >= getStageGroupbyId(this.diffcultid).getStage_group()
				.size()) {
			// TODO 游戏通关
			Gdx.input.getTextInput(new TextInputListener() {
				
				@Override
				public void input(String text) {
					Preferences pref = Gdx.app.getPreferences("highscore");
					pref.putInteger(text, GameModel.this.getScore());
					pref.flush();
				}
				
				@Override
				public void canceled() {
					
				}
			}, "恭喜通关！请留下您的大名：", "玩家");
			BulletGame.setCurrentScreen(BulletGame.homeScreen);
			objList.clear();
		} else {
			// TODO 开始下一关
			this.currentStage = getStageGroupbyId(diffcultid).getStagebyId(stageid);
			currentStage.getTask().reset();
			objList.clear();
			getObjList().add(player.getGrazer());
			getObjList().add(player);
		}
	}

	public void pauseGame() {
		// TODO
	}

	public void resumeGame() {
		// TODO
	}

	public void quitGame() {
		// TODO
	}

	public StageGroup getStageGroupbyId(int id) {
		return gameStage.get(id);
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getFaith() {
		return faith;
	}

	public void setFaith(int faith) {
		this.faith = faith;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public void setWorldWidth(int worldWidth) {
		this.worldWidth = worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

	public void setWorldHeight(int worldHeight) {
		this.worldHeight = worldHeight;
	}

	public ArrayList<StageGroup> getGameStage() {
		return gameStage;
	}

	public void setGameStage(ArrayList<StageGroup> gameStage) {
		this.gameStage = gameStage;
	}

	public Stage getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

	public boolean isPratice() {
		return isPratice;
	}

	public void setPratice(boolean isPratice) {
		this.isPratice = isPratice;
	}

	public boolean isPauseFlag() {
		return pauseFlag;
	}

	public void setPauseFlag(boolean pauseFlag) {
		this.pauseFlag = pauseFlag;
	}

	public boolean isQuitFlag() {
		return quitFlag;
	}

	public void setQuitFlag(boolean quitFlag) {
		this.quitFlag = quitFlag;
	}

	public boolean isBossWait() {
		return bossWait;
	}

	public void setBossWait(boolean bossWait) {
		this.bossWait = bossWait;
	}

	public PlayerClass getPlayer() {
		return player;
	}

	public void setPlayer(PlayerClass player) {
		this.player = player;
	}

	public int getDiffcultid() {
		return diffcultid;
	}

	public void setDiffcultid(int diffcultid) {
		this.diffcultid = diffcultid;
	}

	public int getStageid() {
		return stageid;
	}

	public void setStageid(int stageid) {
		this.stageid = stageid;
	}

	public ArrayList<BaseObject> getObjList() {
		return objList;
	}

	public void setObjList(ArrayList<BaseObject> objList) {
		this.objList = objList;
	}

	public Background getGameBG() {
		return gameBG;
	}

	public void setGameBG(Background gameBG) {
		this.gameBG = gameBG;
	}

	public int getChip() {
		return chip;
	}

	public void setChip(int chip) {
		this.chip = chip;
	}

	public int getGraze() {
		return graze;
	}

	public void setGraze(int graze) {
		this.graze = graze;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isBlockSpell() {
		return blockSpell;
	}

	public void setBlockSpell(boolean blockSpell) {
		this.blockSpell = blockSpell;
	}

	public boolean isChipBonus() {
		return chipBonus;
	}

	public void setChipBonus(boolean chipBonus) {
		this.chipBonus = chipBonus;
	}

	public boolean isKeyShoot() {
		return keyShoot;
	}

	public void setKeyShoot(boolean keyShoot) {
		this.keyShoot = keyShoot;
	}

	public boolean isKeySlow() {
		return keySlow;
	}

	public void setKeySlow(boolean keySlow) {
		this.keySlow = keySlow;
	}

	public boolean isKeySpell() {
		return keySpell;
	}

	public void setKeySpell(boolean keySpell) {
		this.keySpell = keySpell;
	}

	public boolean isKeyMove() {
		return keyMove;
	}

	public void setKeyMove(boolean keyMove) {
		this.keyMove = keyMove;
	}

	public double getPlayerMoveAngel() {
		return playerMoveAngel;
	}

	public void setPlayerMoveAngel(double playerMoveAngel) {
		this.playerMoveAngel = playerMoveAngel;
	}

}
