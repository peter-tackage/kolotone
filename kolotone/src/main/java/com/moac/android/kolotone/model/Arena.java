package com.moac.android.kolotone.model;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.util.Log;

public class Arena {

	private static String TAG = Arena.class.getSimpleName();

	// Defines the dimensions and nature of the arena that the
	// Physics engine uses.

	protected float density = 2; // px/m
	protected float reboundEnergyFactor = 0.6f; // Amount of energy returned

	private List<AbstractShape> objects = new ArrayList<AbstractShape>();
	private float xMax;
	private float yMax;


	public Arena(float reboundEnergyFactor, float density, float xMax,
			float yMax) {
	
		this.reboundEnergyFactor = reboundEnergyFactor;
		this.density = density;
		this.xMax = xMax;
		this.yMax = yMax;
			
		Log.d(TAG, "Creating Arena: " + 
			" reboundEnergyFactor: " + reboundEnergyFactor +
			" density: " + density +
			" xMax: " + xMax +
			" yMax: " + yMax);
	
;
	}

	public float getReboundEnergyFactor() {
		return reboundEnergyFactor;
	}

	public float getDensity() {
		return density;
	}

	public float getxMax() {
		return xMax;
	}
	public float getyMax() {
		return yMax;
	}

	public List<AbstractShape> getObjects()
	{
		return this.objects;
	}

	public void manage(AbstractShape obj)
	{
		Log.i(TAG, "Managing new shape: " + obj.getType() + " total: " + objects.size());
		this.objects.add(obj);
	}

	public void unmanage(AbstractShape obj)
	{
		Log.i(TAG, "Unmanaging shape: " + obj.getType() + " total: " + objects.size());
		this.objects.remove(obj);
	}

	// TODO Use wall dampening
	public void update() {
		
	//	Log.i(TAG, "Updating Arena: total objects: "  + objects.size());


		for (AbstractShape obj: objects)
		{
			// check collision with right wall if heading right
			if (obj.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
					&& obj.getXPos() + obj.getWidth() / 2 >= xMax) {
				obj.getSpeed().toggleXDirection();
			}
			// check collision with left wall if heading left
			if (obj.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
					&& obj.getXPos() - obj.getWidth() / 2 <= 0) {
				obj.getSpeed().toggleXDirection();
			}
			// check collision with bottom wall if heading down
			if (obj.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
					&& obj.getYPos() + obj.getHeight() / 2 >= yMax) {
				obj.getSpeed().toggleYDirection();
			}
			// check collision with top wall if heading up
			if (obj.getSpeed().getyDirection() == Speed.DIRECTION_UP
					&& obj.getYPos() - obj.getHeight() / 2 <= 0) {
				obj.getSpeed().toggleYDirection();
			}
			// Update position of the obj
			obj.update();
		}
	}
	//	public void update(float delta)
	//	{
	//		for (AbstractShape obj: objects)
	//		{
	//			// TODO update
	//	
	//			float newXVelocity = obj.getxVelocity() + (delta * obj.getxAcceleration() / 1000) * density;	
	//			float newYVelocity = obj.getyVelocity() + (delta * obj.getyAcceleration() / 1000) * density;
	//
	//			obj.setxVelocity(newXVelocity);
	//			obj.setyVelocity(newYVelocity);
	//			
	//			float newxPos = obj.getXPos() + ((obj.getxVelocity() * delta) / 1000) * density;
	//			float newyPos = obj.getYPos() + ((obj.getyVelocity() * delta) / 1000) * density;
	//			
	//			obj.setXPos(newxPos);
	//			obj.setYPos(newyPos);
	//			
	//			// Reset external force 
	//			obj.setxAcceleration(0f);
	//			obj.setyAcceleration(0f);
	//			
	//			// Check for rebound from world edges.
	//			obj.checkRebound(xMax, yMax,
	//					reboundEnergyFactor);	
	//			
	//			
	//		}
	//	}


	
	public void draw(Canvas canvas) {
	//	Log.i(TAG, "Drawing Arena: total objects: "  + objects.size());

		
		for (AbstractShape shape: objects)
		{
			shape.draw(canvas);
		}
		
	}
}
