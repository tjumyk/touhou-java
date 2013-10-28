package org.tjuscs.bulletgame.collide;


public class GeometryRec extends GeometryBase{
	private double width;
	private double height;
	private double rotation;
	double axisx[] = new double[2];
	double axisy[] = new double[2];	
	double centerP[] = new double[2];
	double fourCor[][] = new double[4][2];
	
	public GeometryRec(double x,double y,double width,double height,double r){
		this.setLocx(x);
		this.setLocy(y);
		this.setWidth(width);
		this.setRotation(r);
		this.setHeight(height);		
	}

	public void setData() {
		this.axisx[0] = Math.cos(this.rotation / 180.0 * Math.PI);
		this.axisx[1] = Math.sin(this.rotation / 180.0 * Math.PI);
		this.axisy[0] = -this.axisx[1];
		this.axisy[1] = this.axisx[0];
		this.centerP[0] = this.getLocx() + this.width;
		this.centerP[1] = this.getLocy() + this.height;
	}
	
	public void getFourCor(){
		setData();
		double t1[] = new double[2];
		double t2[] = new double[2];
		t1[0] = this.width / 2.0 * this.axisx[0];
		t1[1] = this.height / 2.0 * this.axisx[1];
		t2[0] = this.width / 2.0 * this.axisy[0];
		t2[1] = this.height / 2.0 * this.axisy[1];
		this.fourCor[0][0] = t1[0] + t2[0];
		this.fourCor[0][1] = t1[1] + t2[1];
		this.fourCor[1][0] = t1[0] - t2[0];
		this.fourCor[1][1] = t1[1] - t2[1];
		this.fourCor[2][0] = -t1[0] - t2[0];
		this.fourCor[2][1] = -t1[1] - t2[1];
		this.fourCor[3][0] = -t1[0] + t2[0];
		this.fourCor[3][1] = -t1[1] + t2[1];		
	}

    public double getProjectionRadius(double[] axis) {
    	double projectionAxisX = this.dot(axis, axisx);
    	double projectionAxisY = this.dot(axis, this.axisy); 
        return this.width / 2.0 * projectionAxisX + this.height / 2.0 * projectionAxisY;
    }
	
    public double dot(double[] axisA, double[] axisB) {
        return Math.abs(axisA[0] * axisB[0] + axisA[1] * axisB[1]);
    }

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
}
