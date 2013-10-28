package org.tjuscs.bulletgame.model;

import java.util.List;

import org.tjuscs.bulletgame.util.GameUtil;
import org.tjuscs.bulletgame.util.MathUtils;
import org.tjuscs.bulletgame.view.component.AudioPlayer;
import org.tjuscs.bulletgame.view.component.Renderer;
import org.tjuscs.bulletgame.view.component.Resources;

/***********************************************************************
 * Module:  PlayerClass.java
 * Author:  Administrator
 * Purpose: Defines the Class PlayerClass
 ***********************************************************************/

/** @pdOid 1b20780a-658e-49b6-b413-f97f81006265 */
public class PlayerClass extends BaseObject {
	private List<String> imgs;
	private double supportx;
	private double supporty;
	private double hspeed;
	private double lspeed;
	private double dx;
	private double dy;
	private double collectLine;
	private int slow;
	private int lr;
	private double lh;
	private double fire;
	private boolean lock;
	private boolean dialog;
	private int nextshoot;
	private int nextspell;
	private int death;
	private int protect;
	private Grazer grazer;
	private int support;
	private boolean hasslist;
	protected double[][][] slist;
	protected double[][] sp = new double[4][3];
	private BaseObject target = null;

	public PlayerClass() {
	}

	public void init() {
		super.init();
		this.setGroup(GameUtil.GROUP_PLAYER);
		this.setLayer(GameUtil.LAYER_PLAYER);
		this.setVx(0);
		this.setVy(0);
		this.setX(240);
		this.setY(48);
		this.setSupportx(240);
		this.setSupporty(this.getY());
		this.setHspeed(4);
		this.setLspeed(2);
		this.setCollectLine(500);
		this.setSlow(0);
		this.setLr(1);
		this.setLh(0);
		this.setFire(0.0);
		this.setBound(false);
		this.setLock(false);
		this.setDialog(false);
		this.setNextshoot(0);
		this.setNextspell(0);
		this.setDeath(0);
		this.setProtect(120);
		this.setGrazer(new Grazer());
		this.setSupport(GameModel.getInstance().getPower() / 100);
		this.setHasslist(false);
	}

	public void frame() {
		GameModel model = GameModel.getInstance();
		this.setSupport(model.getPower() / 100);
		int dx = 0;
		// find target
		if (this.getTarget() == null || !GameUtil.IsValid(this.getTarget())
				|| !this.getTarget().isColli()) {
			findtarget();
		}
		if ((this.getDeath() == 0) && !this.isLock()) {
			// slow
			if (model.isKeySlow())
				this.setSlow(1);
			else
				this.setSlow(0);
			// shoot and spell
			if (!this.isDialog()) {
				if (model.isKeyShoot() && this.getNextshoot() <= 0) {
					this.shoot();
					AudioPlayer.getInstance().PlaySound("plst00", 0.15);
				}
				if (model.isKeySpell() && this.getNextspell() <= 0
						&& model.getPower() >= 100 && !model.isBlockSpell()) {
					model.setPower(model.getPower() - 100);
					this.spell();
				}
			}
			// move
			if (model.isKeyMove()) {
				double v = this.getHspeed();
				double a = model.getPlayerMoveAngel();// 移动角度
				a = a % 360.0;
				if (this.getSlow() == 1)
					v = this.getLspeed();
				this.setDx(v * MathUtils.cosDeg(a));
				this.setDy(v * MathUtils.sinDeg(a));
				if (a < 60 || a > 300)
					dx = 1;
				if (a > 120 && a < 240)
					dx = -1;
			} else {
				this.setDx(0);
				this.setDy(0);
			}
			this.setX(Math.max(
					Math.min(this.getX() + this.getDx(), model.getWorldWidth()),
					0));
			this.setY(Math.max(Math.min(this.getY() + this.getDy(),
					model.getWorldHeight()), 0));
			// fire
			if (model.isKeyShoot() && !this.isDialog()) {
				this.setFire(this.getFire() + 0.16);
			} else {
				this.setFire(this.getFire() - 0.16);
			}
			if (this.getFire() < 0)
				this.setFire(0.0);
			if (this.getFire() > 1)
				this.setFire(1.0);
			// item
			if (this.getY() > this.getCollectLine()) {
				List<BaseObject> objList = model.getObjList();
				for (BaseObject obj : objList) {
					if (obj.getGroup() == GameUtil.GROUP_ITEM) {
						Item o = (Item) obj;
						o.setAttract(8);
					}
				}
			} else {
				if (this.getSlow() == 1) {
					List<BaseObject> objList = model.getObjList();
					for (BaseObject obj : objList) {
						if (obj.getGroup() == GameUtil.GROUP_ITEM
								&& GameUtil.Dist(this, obj) < 48) {
							Item o = (Item) obj;
							o.setAttract(Math.max(o.getAttract(), 3));
						}
					}
				} else {
					List<BaseObject> objList = model.getObjList();
					for (BaseObject obj : objList) {
						if (obj.getGroup() == GameUtil.GROUP_ITEM
								&& GameUtil.Dist(this, obj) < 24) {
							Item o = (Item) obj;
							o.setAttract(Math.max(o.getAttract(), 3));
						}
					}
				}
			}
		} else if (this.getDeath() == 90) {
			Item.playerMiss();
			this.setHide(true);
			this.setColli(false);
			model.getObjList().add(new PlayerDeath(this.getX(), this.getY()));
		} else if (this.getDeath() == 50) {
			this.setX(240);
			this.setSupportx(240);
			this.setY(10);
			this.setSupporty(10);
			this.setHide(false);
			this.setColli(true);
			GameUtil.BulletKiller(this.getX(), this.getY(), false);
		} else if (this.getDeath() < 50) {
			this.setY(48 - this.getDeath() * 0.76);
		}
		// img
		if (Math.abs(this.getLr()) == 1) {
			this.setImg(this.getImgs().get((this.getAni() / 8) % 8));
		} else if (this.getLr() == -6) {
			this.setImg(this.getImgs().get((this.getAni() / 8) % 4 + 12));
		} else if (this.getLr() == 6) {
			this.setImg(this.getImgs().get((this.getAni() / 8) % 4 + 20));
		} else if (this.getLr() < 0) {
			this.setImg(this.getImgs().get(6 - this.getLr()));
		} else if (this.getLr() > 0) {
			this.setImg(this.getImgs().get(14 + this.getLr()));
		}
		// some status
		this.setLr(this.getLr() + dx);
		if (this.getLr() > 6)
			this.setLr(6);
		if (this.getLr() < -6)
			this.setLr(-6);
		if (this.getLr() == 0)
			this.setLr(this.getLr() + dx);
		if (dx == 0) {
			if (this.getLr() > 1)
				this.setLr(this.getLr() - 1);
			if (this.getLr() < -1)
				this.setLr(this.getLr() + 1);
		}
		//
		this.setLh(this.getLh() + (this.getSlow() - 0.5) * 0.3);
		if (this.getLh() < 0)
			this.setLh(0);
		if (this.getLh() > 1)
			this.setLh(1);

		if (this.getNextshoot() > 0)
			this.setNextshoot(this.getNextshoot() - 1);
		if (this.getNextspell() > 0)
			this.setNextspell(this.getNextspell() - 1);

		this.setSupportx(this.getX() + (this.getSupportx() - this.getX())
				* 0.6785);
		this.setSupporty(this.getY() + (this.getSupporty() - this.getY())
				* 0.6785);

		if (this.getDeath() > 0)
			this.setDeath(this.getDeath() - 1);
		if (this.getProtect() > 0)
			this.setProtect(this.getProtect() - 1);

		// update supports
		if (this.isHasslist()) {
			if (this.getSupport() == 5) {
				for (int i = 0; i <= 3; i++) {
					this.sp[i] = mixTable(this.getLh(), this.slist[5][i]);
					this.sp[i][2] = 1;
				}
			} else {
				int s = this.getSupport();
				for (int i = 0; i <= 3; i++) {
					if (this.slist[s][i][4] > 0) {
						this.sp[i] = mixTable(this.getLh(), this.slist[s][i]);
						this.sp[i][2] = 1;
					} else {
						this.sp[i][2] = 0;
					}
				}
			}
		}
		this.getTask().act();
	}

	public void shoot() {
	}

	public void spell() {

	}

	public double[] mixTable(double x, double[] t1) {
		double[] r = new double[3];
		double y = 1 - x;
		for (int i = 0; i < 2; i++)
			r[i] = y * t1[i] + x * t1[i + 2];
		return r;
	}

	public double[] mixTable(double x, double[] t1, double[] t2) {
		double[] r = new double[3];
		double y = 1 - x;
		for (int i = 0; i < 2; i++)
			r[i] = y * t1[i] + x * t2[i];
		return r;
	}

	public void render() {
		Resources resources = Resources.getInstance();
		Renderer render = Renderer.getInstance();
		if (this.getProtect() % 3 == 1)
			resources.SetImageState(this.getImg(), "", 0, 0, 255, 255);
		else
			resources.SetImageState(this.getImg(), "", 255, 255, 255, 255);
		render.Render(this.getImg(), this.getX(), this.getY());
	}

	/**
	 * @param other
	 */
	public void colli(BaseObject other) {
		if (this.getDeath() == 0 && this.getProtect() == 0 && !this.isDialog()) {
			this.setDeath(100);
			GameModel
					.getInstance()
					.getObjList()
					.add(new Bubble("bubble1", this.getX(), this.getY(), 20, 1,
							6, GameUtil.LAYER_PLAYER));
			AudioPlayer.getInstance().PlaySound("pldead00", 0.5);
			if (other.getGroup() == GameUtil.GROUP_ENEMY_BULLET)
				GameUtil.Del(other);
		}
	}

	public void findtarget() {
		this.setTarget(null);
		double maxpri = -1;
		List<BaseObject> objList = GameModel.getInstance().getObjList();
		for (BaseObject obj : objList) {
			if (obj.getGroup() == GameUtil.GROUP_ENEMY && obj.isColli()) {
				double dx = this.getX() - obj.getX();
				double dy = this.getY() - obj.getY();
				double pri = Math.abs(dy) / (Math.abs(dx) + 0.01);
				if (pri > maxpri) {
					maxpri = pri;
					this.setTarget(obj);
				}
			}
		}
	}

	public double getSupportx() {
		return supportx;
	}

	public void setSupportx(double supportx) {
		this.supportx = supportx;
	}

	public double getSupporty() {
		return supporty;
	}

	public void setSupporty(double supporty) {
		this.supporty = supporty;
	}

	public double getHspeed() {
		return hspeed;
	}

	public void setHspeed(double hspeed) {
		this.hspeed = hspeed;
	}

	public double getLspeed() {
		return lspeed;
	}

	public void setLspeed(double lspeed) {
		this.lspeed = lspeed;
	}

	public double getCollectLine() {
		return collectLine;
	}

	public void setCollectLine(double collectLine) {
		this.collectLine = collectLine;
	}

	public int getSlow() {
		return slow;
	}

	public void setSlow(int slow) {
		this.slow = slow;
	}

	public int getLr() {
		return lr;
	}

	public void setLr(int lr) {
		this.lr = lr;
	}

	public double getLh() {
		return lh;
	}

	public void setLh(double lh) {
		this.lh = lh;
	}

	public double getFire() {
		return fire;
	}

	public void setFire(double fire) {
		this.fire = fire;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public boolean isDialog() {
		return dialog;
	}

	public void setDialog(boolean dialog) {
		this.dialog = dialog;
	}

	public int getNextshoot() {
		return nextshoot;
	}

	public void setNextshoot(int nextshoot) {
		this.nextshoot = nextshoot;
	}

	public int getNextspell() {
		return nextspell;
	}

	public void setNextspell(int nextspell) {
		this.nextspell = nextspell;
	}

	public int getDeath() {
		return death;
	}

	public void setDeath(int death) {
		this.death = death;
	}

	public int getProtect() {
		return protect;
	}

	public void setProtect(int protect) {
		this.protect = protect;
	}

	public int getSupport() {
		return support;
	}

	public void setSupport(int support) {
		this.support = support;
	}

	public double[][][] getSlist() {
		return slist;
	}

	public void setSlist(double[][][] slist) {
		this.slist = slist;
	}

	public double[][] getSp() {
		return sp;
	}

	public void setSp(double[][] sp) {
		this.sp = sp;
	}

	public BaseObject getTarget() {
		return target;
	}

	public void setTarget(BaseObject target) {
		this.target = target;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public Grazer getGrazer() {
		return grazer;
	}

	public void setGrazer(Grazer grazer) {
		this.grazer = grazer;
	}

	public List<String> getImgs() {
		return imgs;
	}

	public void setImgs(List<String> imgs) {
		this.imgs = imgs;
	}

	public boolean isHasslist() {
		return hasslist;
	}

	public void setHasslist(boolean hasslist) {
		this.hasslist = hasslist;
	}

}