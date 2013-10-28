package org.tjuscs.bulletgame.collide;


public class GeometryEllipse extends GeometryBase{
	private double diameter;
	private double rotation;
	
	public GeometryEllipse(double x,double y,double d,double r){
		this.setLocx(x);
		this.setLocy(y);
		this.setDiameter(d);
		this.setRotation(r);
	}

	public double getDiameter() {
		return diameter;
	}

	public void setDiameter(double diameter) {
		this.diameter = diameter;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

}
