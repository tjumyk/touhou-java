package org.tjuscs.bulletgame.view.component;

import java.util.ArrayList;
import java.util.List;

public class Animation{
	public int a,b;
	public boolean rect;
	
	public List<Image> keyFrames = new ArrayList<Image>();
	public int intv;
	private int current;
	
	public void setCollideGeometry(int a, int b, boolean rect){
		this.a = a;
		this.b = b;
		this.rect = rect;
	}
	
	public Image getCurrentImage(){
		return keyFrames.get(current/(intv+1));
	}
	
	public void update(){
		current ++;
		current %= keyFrames.size()*(intv+1);
	}
}
