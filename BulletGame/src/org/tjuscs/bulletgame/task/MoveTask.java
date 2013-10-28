package org.tjuscs.bulletgame.task;

import org.apache.commons.jexl2.Expression;
import org.tjuscs.bulletgame.model.BaseObject;

public class MoveTask extends Task {
	private int frames,currentFrame;
	private int x, y;
	private String mode;
	private BaseObject target;
	
	private Expression xExp, yExp, framesExp, targetExp;
	
	private double initx,inity,stepx,stepy,accx,accy;
	private boolean prepared;
	
	public MoveTask(String xExp, String yExp, String framesExp, String mode, String targetExp){
		this.xExp = scriptEngine.createExpression(xExp);
		this.yExp = scriptEngine.createExpression(yExp);
		this.framesExp = scriptEngine.createExpression(framesExp);
		this.targetExp = scriptEngine.createExpression(targetExp);
		
		this.mode = mode;
		prepared = false;
	}

	private void prepare() {
		x = ((Number)xExp.evaluate(context)).intValue();
		y = ((Number)yExp.evaluate(context)).intValue();
		frames = ((Number)framesExp.evaluate(context)).intValue();
		target = (BaseObject)targetExp.evaluate(context);
		
		initx = target.getX();
		inity = target.getY();
		if(mode.equalsIgnoreCase("acc")){
			stepx = accx = 2*(x -  initx)/(frames+1)/frames;
			stepy = accy = 2*(y -  inity)/(frames+1)/frames;
		}else if(mode.equalsIgnoreCase("dec")){
			accx = -2*(x -  initx)/(frames+1)/frames;
			accy = -2*(y -  inity)/(frames+1)/frames;
			stepx = frames * -accx;
			stepy = frames * -accy;
		}else {//normal
			stepx = (x -  initx)/frames;
			stepy = (y -  inity)/frames;
			accx = accy = 0;
		}
		prepared = true;
	}
	
	@Override
	public void reset() {
		super.reset();
		this.currentFrame = 0;
		prepared = false;
	}
	
	@Override
	public boolean work() {
		if(!prepared)
			prepare();
		if(currentFrame < frames){
			initx += stepx;
			inity += stepy;
			stepx += accx;
			stepy += accy;
			target.setX(initx);
			target.setY(inity);
			currentFrame ++;
			return false;
		}
		return true;
	}

}
