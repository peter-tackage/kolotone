package com.moac.android.kolotone.model;

public abstract class Anchor {

	private float xPos = 0;
    private float yPos = 0;
        
	public Anchor(float xPos, float yPos) {
		super();
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public float getxPos() {
		return xPos;
	}
	public void setxPos(float xPos) {
		this.xPos = xPos;
	}
	public float getyPos() {
		return yPos;
	}
	public void setyPos(float yPos) {
		this.yPos = yPos;
	}
        
    
}
