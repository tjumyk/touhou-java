package org.tjuscs.bulletgame.model;

public enum ImageClass {

	arrow_big("arrow_big", 1, 4, 4, false), arrow_mid("arrow_mid", 2, 3, 3,
			false), gun_bullet("gun_bullet", 1, 4, 3, false), butterfly(
			"butterfly", 2, 4, 4, false), square("square", 1, 4, 4, false), ball_mid(
			"ball_mid", 2, 4, 4, false), ball_mid_b("ball_mid_b", 2, 5, 5,
			false), ball_mid_c("ball_mid_c", 2, 4, 4, false), ball_mid_d(
			"ball_mid_d", 2, 3, 3, false), money("money", 2, 3, 3, false), mildew(
			"mildew", 1, 3, 3, false), ellipse("ellipse", 2, 7, 5, false), star_small(
			"star_small", 1, 3, 3, false), star_big("star_big", 2, 6, 6, false), star_big_b(
			"star_big_b", 2, 6, 6, false), ball_huge("ball_huge", 4, 20, 20,
			false), ball_big("ball_big", 2, 8, 8, false), heart("heart", 2, 9,
			9, false), ball_small("ball_small", 1, 2, 2, false), grain_a(
			"grain_a", 1, 3, 3, false), grain_b("grain_b", 1, 3, 3, false), grain_c(
			"grain_c", 1, 3, 3, false), kite("kite", 1, 3, 3, false), knife(
			"knife", 2, 10, 3, false), knife_b("knife_b", 2, 10, 3, false), arrow_small(
			"arrow_small", 1, 3, 3, false), water_drop("water_drop", 4, 6, 6,
			false);

	private final String imgPrename;
	private final int interval;
	private final int a;
	private final int b;
	private final boolean rect;

	private ImageClass(String imgPrename, int interval, int a, int b,
			boolean rect) {
		this.imgPrename = imgPrename;
		this.interval = interval;
		this.rect = rect;
		if (this.rect) {
			this.a = a;
			this.b = b;
		} else {
			this.a = this.b = a;
		}
	}

	public void init(Bullet blt, int index) {
		blt.setImg(imgPrename + ((index + interval - 1) / interval));
		blt.setRect(rect);
		blt.setA(a);
		blt.setB(b);
	}

	public String getImgPrename() {
		return imgPrename;
	}

	public int getInterval() {
		return interval;
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}

	public boolean isRect() {
		return rect;
	}

}
