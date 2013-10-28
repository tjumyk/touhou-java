package org.tjuscs.bulletgame.model;

/***********************************************************************
 * Module:  Boss.java
 * Author:  Administrator
 * Purpose: Defines the Class Boss
 ***********************************************************************/

import java.util.List;

import org.tjuscs.bulletgame.util.GameUtil;
import org.tjuscs.bulletgame.util.MathUtils;
import org.tjuscs.bulletgame.view.component.Renderer;
import org.tjuscs.bulletgame.view.component.Resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;

public class Boss extends Enemybase {

	private int aniIntv;
	private int auraAlpha;
	private int auraAlphaD;
	private int cardIndex;
	private List<Bosscard> cards;
	private Bosscard currentcard;
	private double dmgFactor;
	private int lr;
	private String scName;
	private String bossName;

	private int cardNameTxtPosition;

	public Boss() {
	}

	public Boss(double x, double y, String name, List<Bosscard> cards) {
		this.init(x, y, name, cards);
	}

	/**
	 * @param x
	 * @param y
	 * @param name
	 * @param cards
	 */
	public void init(double x, double y, String name, List<Bosscard> cards) {
		super.init(0);
		this.setX(x);
		this.setY(y);
		this.setA(32);
		this.setB(32);
		this.setImg("undefined");
		this.setAniIntv(8);
		this.setLr(1);
		this.setAuraAlpha(128);
		this.setAuraAlphaD(4);
		this.setDmgFactor(0);
		this.setBossName(name);
		this.setCards(cards);
		this.setCardIndex(0);
		if (this.getCardIndex() >= this.getCardNum()) {
			GameUtil.Del(this);
		} else {
			this.setCurrentcard(this.getCards().get(this.getCardIndex()));
			this.setTask(this.getCurrentcard().getTask());
			this.setTimer(0);
			this.setHp(this.getCurrentcard().getHp());
			this.setMaxhp(this.getCurrentcard().getHp());
			this.setDmgFactor(1);
			this.setCardIndex(this.getCardIndex() + 1);
		}
	}

	public void takeDamage(double dmg) {
		if (!this.isProtect())
			this.setHp(this.getHp() - dmg * this.getDmgFactor());
	}

	public void kill() {
		killServants(this);
		Item.dropItem(this.getX(), this.getY(), this.getCurrentcard().getDrop());
		GameUtil.BulletKiller(this.getX(), this.getY(), true);
		if (this.getCardIndex() >= this.getCardNum()) {
			// 真实死亡
			this.setHide(true);
			this.setColli(false);
			GameModel.getInstance().getObjList()
					.add(new EnemyDeath(1, this.getX(), this.getY()));
			// Task task = Tasks.sequence(
			// Tasks.wait(200),
			// Tasks.execute("game.nextStage();"));
			// task.setBinding("game", GameModel.getInstance());
			// GameModel.getInstance().getPlayer().setTask(task);
			GameModel.getInstance().nextStage();
		} else {
			// 下一符卡
			GameUtil.PerserveUnit(this);
			this.setCurrentcard(this.getCards().get(this.getCardIndex()));
			this.setTask(this.getCurrentcard().getTask());
			this.setTimer(0);
			this.setHp(this.getCurrentcard().getHp());
			this.setMaxhp(this.getCurrentcard().getHp());
			this.setDmgFactor(1);
			this.setCardIndex(this.getCardIndex() + 1);
		}
	}

	public void frame() {
		super.frame();
		int dx;
		if (this.getVx() > 0.5)
			dx = 1;
		else if (this.getVx() < -0.5)
			dx = -1;
		else
			dx = 0;
		this.setLr(Math.min(Math.max((this.getLr() + dx), -18), 18));
		if (this.getLr() == 0)
			this.setLr(this.getLr() + dx);
		if (dx == 0) {
			if (this.getLr() > 1)
				this.setLr(this.getLr() - 1);
			if (this.getLr() < -1)
				this.setLr(this.getLr() + 1);
		}
		if (this.getLr() >= 0)
			this.setHscale(1);
		else
			this.setHscale(-1);
		this.setAuraAlpha(this.getAuraAlpha() + this.getAuraAlphaD());
		this.setAuraAlpha(Math.min(Math.max(0, this.getAuraAlpha()), 128));
		if (this.getTimer() < this.getCurrentcard().getT1()) {
			this.setDmgFactor(0.0);
		} else if (this.getTimer() < this.getCurrentcard().getT2()) {
			this.setDmgFactor((this.getTimer() - this.getCurrentcard().getT1() + 0.0)
					/ (this.getCurrentcard().getT2()
							- this.getCurrentcard().getT1() + 0.0));
		} else if (this.getTimer() < this.getCurrentcard().getT3()) {
			this.setDmgFactor(1.0);
		} else {
			GameUtil.Kill(this);
		}

		if (cardNameTxtPosition < Gdx.graphics.getHeight() - 30) {
			cardNameTxtPosition += 5;
		} else {
			cardNameTxtPosition = Gdx.graphics.getHeight() - 25;
		}
	}

	public void render() {
		Resources resources = Resources.getInstance();
		Renderer render = Renderer.getInstance();
		resources.SetImageState("boss_aura", "", 255, 255, 255,
				this.getAuraAlpha());
		render.Render("boss_aura", this.getX(), this.getY(), this.getAni() * 5,
				1.8 + 0.4 * MathUtils.sinDeg(this.getAni() * 2.5));
		render.Render(this.getImg(), this.getX(),
				this.getY() + MathUtils.sinDeg(this.getAni() * 4) * 4,
				this.getRot(), this.getHscale(), this.getVscale());
		String txt = currentcard.getName();
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		TextBounds bounds = render.getTextBounds("lishu", txt);
		render.RenderText("lishu", txt, (int) (width - bounds.width),
				cardNameTxtPosition);
		txt = this.bossName;
		bounds = render.getTextBounds("yahei", txt);
		render.RenderText("yahei", txt, (int) (width - bounds.width),
				height - 53);

		render.RenderRect("black", 30, width, height - 50, height - 53);
		render.RenderRect("white", 30, (int) (30 + (width - 30) * this.getHp()
				/ this.getMaxhp()), height - 50, height - 53);
	}

	public String getScName() {
		return scName;
	}

	public void setScName(String scName) {
		this.scName = scName;
	}

	public int getAniIntv() {
		return aniIntv;
	}

	public int getAuraAlpha() {
		return auraAlpha;
	}

	public int getAuraAlphaD() {
		return auraAlphaD;
	}

	public List<Bosscard> getCards() {
		return cards;
	}

	public double getDmgFactor() {
		return dmgFactor;
	}

	public int getLr() {
		return lr;
	}

	public void setAniIntv(int aniIntv) {
		this.aniIntv = aniIntv;
	}

	public void setAuraAlpha(int auraAlpha) {
		this.auraAlpha = auraAlpha;
	}

	public void setAuraAlphaD(int auraAlphaD) {
		this.auraAlphaD = auraAlphaD;
	}

	public void setCards(List<Bosscard> cards) {
		this.cards = cards;
	}

	public void setDmgFactor(double dmgFactor) {
		this.dmgFactor = dmgFactor;
	}

	public void setLr(int lr) {
		this.lr = lr;
	}

	public void showAura(boolean show) {
		if (show)
			this.setAuraAlphaD(4);
		else
			this.setAuraAlphaD(-4);
	}

	public String getBossName() {
		return bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}

	public int getCardIndex() {
		return cardIndex;
	}

	public void setCardIndex(int cardIndex) {
		this.cardIndex = cardIndex;
	}

	public int getCardNum() {
		return cards.size();
	}

	public Bosscard getCurrentcard() {
		return currentcard;
	}

	public void setCurrentcard(Bosscard currentcard) {
		this.currentcard = currentcard;
		this.cardNameTxtPosition = 0;
	}

}