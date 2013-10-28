package org.tjuscs.bulletgame.view.component;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Image extends TextureRegion {
	public int a, b;
	public double centerX, centerY;
	public boolean rect;
	public Color color = Color.WHITE;

	// String blendmode;

	public Image(Texture t, int x, int y, int width, int height) {
		super(t, x, y, width, height);
		setCenter(width / 2.0, height / 2.0);
	}

	public Image(TextureRegion tr, int x, int y, int width, int height) {
		super(tr, x, y, width, height);
		setCenter(width / 2.0, height / 2.0);
	}

	public void setCenter(double cx, double cy) {
		centerX = cx;
		centerY = cy;
	}

	public void setCollideGeometry(int a, int b, boolean rect) {
		this.a = a;
		this.b = b;
		this.rect = rect;
	}
}
