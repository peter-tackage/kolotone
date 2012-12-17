package com.moac.android.kolotone.model;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.util.Log;

public class Arena {

	private static String TAG = Arena.class.getSimpleName();

	// Defines the dimensions and nature of the arena that the
	// Physics engine uses.

	protected double density = 2d; // px/m
	protected double reboundEnergyFactor = 0.6d; // Amount of energy returned

	private List<AbstractShape> objects = new ArrayList<AbstractShape>();
	private double xMax;
	private double yMax;

	private List<OverlapListener> olListeners = new ArrayList<OverlapListener>();

	public Arena(double reboundEnergyFactor, double density, double xMax,
			double yMax) {

		this.reboundEnergyFactor = reboundEnergyFactor;
		this.density = density;
		this.xMax = xMax;
		this.yMax = yMax;

		Log.d(TAG, "Creating Arena: " + 
				" reboundEnergyFactor: " + reboundEnergyFactor +
				" density: " + density +
				" xMax: " + xMax +
				" yMax: " + yMax);

	}

	public double getReboundEnergyFactor() {
		return reboundEnergyFactor;
	}

	public double getDensity() {
		return density;
	}

	public double getxMax() {
		return xMax;
	}
	public double getyMax() {
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

		// TODO Use rebound coefficient
		for (AbstractShape obj: objects)
		{
			// check collision with right wall if heading right
			if (obj.getVelocity().getxDirection() == Velocity.DIRECTION_RIGHT
					&& obj.getXPos() + obj.getWidth() / 2 >= xMax) {
				obj.getVelocity().toggleXDirection();
			}
			// check collision with left wall if heading left
			if (obj.getVelocity().getxDirection() == Velocity.DIRECTION_LEFT
					&& obj.getXPos() - obj.getWidth() / 2 <= 0) {
				obj.getVelocity().toggleXDirection();
			}
			// check collision with bottom wall if heading down
			if (obj.getVelocity().getyDirection() == Velocity.DIRECTION_DOWN
					&& obj.getYPos() + obj.getHeight() / 2 >= yMax) {
				obj.getVelocity().toggleYDirection();
			}
			// check collision with top wall if heading up
			if (obj.getVelocity().getyDirection() == Velocity.DIRECTION_UP
					&& obj.getYPos() - obj.getHeight() / 2 <= 0) {
				obj.getVelocity().toggleYDirection();
			}
			// Update position of the obj
			obj.update();
		}
		reportOverlaps();
	}
	private void reportOverlaps() {

		// Iterate through the shapes
		// TODO This currently will assume that all shapes are circles/balls ... it's easier!
		List<Overlap> overlaps = new ArrayList<Overlap>();

		SIDE_A:
			for (AbstractShape s1: objects)
			{
				Ball b1 = (Ball)s1;

				SIDE_B:
					for (AbstractShape s2: objects)
					{

						// Now cast them to circles
						Ball b2 = (Ball)s2;

						// Don't compare to self
						if (b1 == b2 ) continue SIDE_B;

						// Check if a collision has already been reported from the other side.
						for (Overlap c: overlaps)
						{
							if (c.involves(s1, s2)) continue SIDE_B;
						}

						// Distance between centrepoints.
						double d = Math.abs(Math.hypot(b1.getXPos() - b2.getXPos(), b1.getYPos() - b2.getYPos()));
						if (d >= b1.getRadius() + b2.getRadius())
						{

							// See http://mathworld.wolfram.com/Circle-CircleIntersection.html

							// We have a collision!
							// Check if we have registered it already 
							double area =
									0.5 * Math.sqrt((-d + 
											b1.getRadius() + b2.getRadius())
											* (d + b1.getRadius() - b2.getRadius())
											* (d - b1.getRadius() + b2.getRadius())
											* (d + b1.getRadius() + b2.getRadius()));

							Overlap o = new Overlap(s1, s2, area);
							overlaps.add(o);

						}

					}
				
				// Now iterate through the listeners and report overlaps to them.
				for (OverlapListener ol : olListeners)
				{
					// TODO On a different thread??
					ol.notifyOverlap(overlaps);
				}
			}

	}

	/*
	 * A = 1/2 
	 */
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



	private void reportOverlap() {



	}

	public void draw(Canvas canvas) {
		//	Log.i(TAG, "Drawing Arena: total objects: "  + objects.size());


		for (AbstractShape shape: objects)
		{
			shape.draw(canvas);
		}

	}

	public void addOverlapListener(OverlapListener ol)
	{
		if (ol != null)
			olListeners.add(ol);
	}

	public void removeOverlapListener(OverlapListener ol)
	{
		olListeners.remove(ol);
	}
	
//	public Overlap getOverlap(AbstractShape s1, AbstractShape s2)
//	{
//		for (Overlap o: overlaps)
//		{
//			if (o.involves(s1, s2))
//			{
//				return o;
//			}
//		}
//		return null;
//	}
//	
//	public List<Overlap> getOverlaps(AbstractShape s)
//	{
//		List<Overlap> result = new ArrayList<Overlap>();
//		
//		for (Overlap o: overlaps)
//		{
//			if (o.involves(s)) 
//			{
//				result.add(o);
//			}
//		}
//		return result;
//	}
}
