package com.moac.android.kolotone.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class AbstractShape {

	public enum BOUNCE_TYPE {
		TOPLEFT, TOP, TOPRIGHT, LEFT, RIGHT, BOTTOMLEFT, BOTTOM, BOTTOMRIGHT
	}

	protected int color = Color.WHITE;
	protected Paint paint;

	protected boolean isCollidable = false;
	protected Anchor anchor = null;
	protected int mass = 1;

	// Movement
	protected float xPos = 160;
	protected float yPos = 240;
	//protected float xAcceleration = 0;
	//protected float yAcceleration = 0;
	
	protected Speed speed;

	protected float rotation = 0f;
	protected long lastUpdate;
	protected boolean touched = false;


	public AbstractShape(int color,float xpos, float ypos)
	{
		this.lastUpdate = System.currentTimeMillis();
		this.color = color;
		this.paint = new Paint();
		paint.setColor(color);
		this.xPos = xpos;
		this.yPos = ypos;
		this.speed = new Speed();
	}
	
	abstract public double getArea();
	abstract public String getType();

	public int getMass() {
		return mass;
	}
	public void setMass(int mass) {
		this.mass = mass;
	}
	public float getXPos() {
		return xPos;
	}
	public void setXPos(float xPos) {
		this.xPos = xPos;
	}
	public float getYPos() {
		return yPos;
	}
	public void setYPos(float yPos) {
		this.yPos = yPos;
	}
//	public float getxAcceleration() {
//		return xAcceleration;
//	}
//	public void setxAcceleration(float xAcceleration) {
//		this.xAcceleration = xAcceleration;
//	}
//	public float getyAcceleration() {
//		return yAcceleration;
//	}
//	public void setyAcceleration(float yAcceleration) {
//		this.yAcceleration = yAcceleration;
//	}
//	
	public Speed getSpeed()
	{
		return speed;
	}
	
	public void setSpeed(Speed speed)
	{
		this.speed = speed;
	}
	
	
	public void setColor(int color) {
		this.color = color;
	}
	public void setCollidable(boolean isCollidable) {
		this.isCollidable = isCollidable;
	}

	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	//abstract public void checkRebound(float reboundEnergyFactor, float xMax, float yMax);

	/*
	 * 1 is initial size. Range is 0 - infinite.
	 */
	abstract public void scale(float factor);

	public int getColor()
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

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	abstract public void draw(Canvas canvas);

	abstract public void handleActionDown(int eventX, int eventY);
	
	abstract public float getWidth();
	abstract public float getHeight();
	abstract public void update();

}
