package com.moac.android.kolotone;

public class World {

	// Defines the dimensions and nature of the world that the
	// Physics engine uses.
	
	protected float density = 2; // px/m
	protected float reboundEnergyFactor = 0.6f; // Amount of energy returned
	protected float xMax = 0;    
	protected float yMax = 0;
	
	public World(float reboundEnergyFactor, float density, float xMax,
			float yMax) {
		super();
		this.reboundEnergyFactor = reboundEnergyFactor;
		this.density = density;
		this.xMax = xMax;
		this.yMax = yMax;
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
	

}
