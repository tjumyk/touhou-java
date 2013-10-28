package org.tjuscs.bulletgame.model;

/***********************************************************************
 * Module:  MyObject.java
 * Author:  Administrator
 * Purpose: Defines the Class MyObject
 ***********************************************************************/

import org.tjuscs.bulletgame.task.Task;
import org.tjuscs.bulletgame.task.Tasks;
import org.tjuscs.bulletgame.util.GameUtil;

import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * 所有实体类的基类
 * 
 * @pdOid 50463fe5-5f86-4d86-8e0d-491b3c5d23d0
 */
public class BaseObject implements Poolable {

	private double a = 0;
	private int ani = 0;
	private double b = 0;
	private boolean bound = true;
	private boolean colli = true;
	private int group = GameUtil.GROUP_GHOST;
	private boolean hide = false;
	private double hscale = 1;
	private String img = null;
	private double layer = 0;
	private boolean navi = false;
	private double omiga = 0;
	private boolean rect = false;
	private double rot = 0;
	private int status = GameUtil.STATUS_NORMAL;
	private Task task;
	private int timer = 0;
	private double vscale = 1;
	private double vx = 0;
	private double vy = 0;
	private double x = 0;
	private double y = 0;

	public BaseObject() {
		this.init();
	}

	/**
	 * @param other
	 */
	public void colli(BaseObject other) {
	}

	public void del() {
	}

	public void frame() {
	}

	public double getA() {
		return a;
	}

	public int getAni() {
		return ani;
	}

	public double getB() {
		return b;
	}

	public int getGroup() {
		return group;
	}

	public double getHscale() {
		return hscale;
	}

	public String getImg() {
		return img;
	}

	public double getLayer() {
		return layer;
	}

	public double getOmiga() {
		return omiga;
	}

	public double getRot() {
		return rot;
	}

	public int getStatus() {
		return status;
	}

	public Task getTask() {
		return task;
	}

	public int getTimer() {
		return timer;
	}

	public double getVscale() {
		return vscale;
	}

	public double getVx() {
		return vx;
	}

	public double getVy() {
		return vy;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void init() {
		this.setTimer(0);
		setTask(Tasks.empty());
	}

	public boolean isBound() {
		return bound;
	}

	public boolean isColli() {
		return colli;
	}

	public boolean isHide() {
		return hide;
	}

	public boolean isNavi() {
		return navi;
	}

	public boolean isRect() {
		return rect;
	}

	public void kill() {
	}

	public void render() {
	}

	public void setA(double a) {
		this.a = a;
	}

	public void setAni(int ani) {
		this.ani = ani;
	}

	public void setB(double b) {
		this.b = b;
	}

	public void setBound(boolean bound) {
		this.bound = bound;
	}

	public void setColli(boolean colli) {
		this.colli = colli;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public void setHscale(double hscale) {
		this.hscale = hscale;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public void setLayer(double layer) {
		this.layer = layer;
	}

	public void setNavi(boolean navi) {
		this.navi = navi;
	}

	public void setOmiga(double omiga) {
		this.omiga = omiga;
	}

	public void setRect(boolean rect) {
		this.rect = rect;
	}

	public void setRot(double rot) {
		this.rot = rot;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public void setVscale(double vscale) {
		this.vscale = vscale;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public void reset() {
		a = 0;
		ani = 0;
		b = 0;
		bound = true;
		colli = true;
		group = GameUtil.GROUP_GHOST;
		hide = false;
		hscale = 1;
		img = null;
		layer = 0;
		navi = false;
		omiga = 0;
		rect = false;
		rot = 0;
		status = GameUtil.STATUS_NORMAL;
		task = null;
		timer = 0;
		vscale = 1;
		vx = 0;
		vy = 0;
		x = 0;
		y = 0;
	}

}