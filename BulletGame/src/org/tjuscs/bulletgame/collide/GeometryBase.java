package org.tjuscs.bulletgame.collide;


public class GeometryBase {
	private double locx;
	private double locy;
	
	public GeometryBase(){
		locx = 0.0;
		locy = 0.0;		
	}
	
	public GeometryBase(double x,double y){
		locx = x;
		locy = y;		
	}
	
	public double getLocx() {
		return locx;
	}
	public void setLocx(double locx) {
		this.locx = locx;
	}
	public double getLocy() {
		return locy;
	}
	public void setLocy(double locy) {
		this.locy = locy;
	}
}
