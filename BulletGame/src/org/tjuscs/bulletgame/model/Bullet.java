package org.tjuscs.bulletgame.model;

import org.tjuscs.bulletgame.model.item.ItemFaithMinor;
import org.tjuscs.bulletgame.util.GameUtil;
import org.tjuscs.bulletgame.view.component.Renderer;

import com.badlogic.gdx.utils.Pool.Poolable;

/***********************************************************************
 * Module:  Bullet.java
 * Author:  Administrator
 * Purpose: Defines the Class Bullet
 ***********************************************************************/

/**
 * 子弹
 * 
 */
public class Bullet extends BaseObject implements Poolable {
	private ImageClass imgclass;
	private int index;
	private boolean stay;
	private boolean destroyable;
	private String img1, img2;
	private boolean hasgrazed;

	public Bullet() {
	}

	public Bullet(String imgclassName, int index, double x, double y, double v,
			double angle, boolean aim, double omiga, boolean stay,
			boolean destroyable) {
		this.init(imgclassName, index, x, y, v, angle, aim, omiga, stay,
				destroyable);
	}

	/**
	 * @param imgclass
	 * @param index
	 * @param stay
	 * @param destroyable
	 */
	public void init(String imgclassName, int index, double x, double y,
			double v, double angle, boolean aim, double omiga, boolean stay,
			boolean destroyable) {
		super.init();
		this.setX(x);
		this.setY(y);
		this.setHasgrazed(false);
		GameUtil.SetV2(this, v, angle, true, aim);
		this.setOmiga(omiga);

		ImageClass imgclass = ImageClass.valueOf(imgclassName);
		this.setImgclass(imgclass);
		if (destroyable)
			this.setGroup(GameUtil.GROUP_ENEMY_BULLET);
		else
			this.setGroup(GameUtil.GROUP_INDES);
		this.setStay(stay);
		if (this.isStay())
			this.setColli(false);
		else
			this.setColli(true);
		index = Math.min(Math.max(1, index), 16);
		this.setLayer(GameUtil.LAYER_ENEMY_BULLET_EF);
		this.setIndex(index);
		imgclass.init(this, index);
		this.img1 = this.getImg();
		this.img2 = "preimg" + ((index + 1) / 2);
	}

	public void frame() {
		if (this.stay) {
			this.setX(this.getX() - this.getVx());
			this.setY(this.getY() - this.getVy());
			this.setRot(this.getRot() - this.getOmiga());
			this.setImg(img2);
			if (this.getTimer() == 11) {
				this.setLayer(GameUtil.LAYER_ENEMY_BULLET);
				this.setColli(true);
				if (this.stay) {
					this.setTimer(-1);
					this.stay = false;
					this.setImg(img1);
				}
			}
		}
		this.getTask().act();
	}

	public void kill() {
		GameModel.getInstance().getObjList()
				.add(new ItemFaithMinor(this.getX(), this.getY()));
	}

	@Override
	public void render() {
		Renderer.getInstance().Render(this.getImg(), this.getX(), this.getY(),
				this.getRot());
	}

	public void del() {
	}

	public ImageClass getImgclass() {
		return imgclass;
	}

	public void setImgclass(ImageClass imgclass) {
		this.imgclass = imgclass;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isStay() {
		return stay;
	}

	public void setStay(boolean stay) {
		this.stay = stay;
	}

	public boolean isDestroyable() {
		return destroyable;
	}

	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}

	@Override
	public void reset() {
		super.reset();
		imgclass = null;
		index = 0;
		stay = false;
		destroyable = false;
		img1 = null;
		img2 = null;
	}

	public boolean isHasgrazed() {
		return hasgrazed;
	}

	public void setHasgrazed(boolean hasgrazed) {
		this.hasgrazed = hasgrazed;
	}

}