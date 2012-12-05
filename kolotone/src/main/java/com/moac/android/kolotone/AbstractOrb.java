package com.moac.android.kolotone;

import android.graphics.Color;

public abstract class AbstractOrb {

    public enum BOUNCE_TYPE {
        TOPLEFT, TOP, TOPRIGHT, LEFT, RIGHT, BOTTOMLEFT, BOTTOM, BOTTOMRIGHT
    }
    
	protected Color color;
	protected boolean isCollidable = false;
	protected Anchor anchor = null;
	protected int mass = 1;

	// TODO Should this be here or canvas?

	// Movement
	protected float xPos = 160;
	protected float yPos = 240;
	protected float xAcceleration = 0;
	protected float yAcceleration = 0;
	protected float xVelocity = 0;
	protected float yVelocity = 0;
	protected float reboundXPos = 0;
	protected float reboundYPos = 0;
	protected float reboundXVelocity = 0;
	protected float reboundYVelocity = 0;

	protected long lastUpdate;
    
	abstract public double getArea();
	abstract public String getType();
	
    public void setAcceleration(float ax, float ay) {
        xAcceleration = ax;
        yAcceleration = ay;
    }
       
    public float getXVelocity() {
        return xVelocity;
    }

    // Called to update position etc
	abstract public void update(float pxPerM);
    
    abstract public void checkRebound(float reboundEnergyFactor, float xMax, float yMax);
      
	/*
	 * 1 is initial size. Range is 0 - infinite.
	 */
	abstract public void scale(float factor);

	public Color getColor()
	{
		return this.color;
	}
	
	 public boolean isCollidable()
	{
		return this.isCollidable;
	}
	 
	 public Anchor getAnchor()
	 {
		 return this.anchor;
	 }
	 
	 public void setAnchor(Anchor anchor)
	 {
		 this.anchor = anchor;
	 }
	 	 
}
