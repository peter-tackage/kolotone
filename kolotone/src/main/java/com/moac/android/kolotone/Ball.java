package com.moac.android.kolotone;

public class Ball extends AbstractOrb {

	public static final String TYPE = "Ball";

	private float radius = 20;

	public Ball() {
		lastUpdate = System.currentTimeMillis();
	}

	public Ball(Anchor anchor) {
		this();
		this.anchor = anchor;
	}

	public float getRadius() {
		return radius;
	}

	@Override
	public void update(float pxPerM) {

		long currentTime = System.currentTimeMillis();

		long elapsed = currentTime - lastUpdate;
		lastUpdate = currentTime;

		xVelocity += ((elapsed * xAcceleration) / 1000) * pxPerM;
		yVelocity += ((elapsed * yAcceleration) / 1000) * pxPerM;

		xPos += ((xVelocity * elapsed) / 1000) * pxPerM;
		yPos += ((yVelocity * elapsed) / 1000) * pxPerM;

	}

	@Override
	public void checkRebound(float reboundEnergyFactor, float xMax, float yMax) {
		// Handle rebounding on both sides simultaneously
		if (yPos - radius < 0) {
			reboundXPos = xPos;
			reboundYPos = radius;
			reboundXVelocity = xVelocity;
			reboundYVelocity = -yVelocity * reboundEnergyFactor;
		} else if (yPos + radius > yMax) {
			reboundXPos = xPos;
			reboundYPos = yMax - radius;
			reboundXVelocity = xVelocity;
			reboundYVelocity = -yVelocity * reboundEnergyFactor;
		}

		if (xPos - radius < 0) {
			reboundXPos = radius;
			reboundYPos = yPos;
			reboundXVelocity = -xVelocity * reboundEnergyFactor;
			reboundYVelocity = yVelocity;
		} else if (xPos + radius > xMax) {
			reboundXPos = xMax - radius;
			reboundYPos = yPos;
			reboundXVelocity = -xVelocity * reboundEnergyFactor;
			reboundYVelocity = yVelocity;
		}

		// Moved this into here.
		// Perhaps it should be deferred, for smoothness??
        xPos = reboundXPos;
        yPos = reboundYPos;
        xVelocity = reboundXVelocity;
        yVelocity = reboundYVelocity;
	}

	public String getState() {
		String state = "";
		state = xPos + "|" + yPos + "|" + xAcceleration + "|" + yAcceleration
				+ "|" + xVelocity + "|" + yVelocity;
		return state;
	}

	// public void restoreState(String state) {
	// List<String> stateInfo = Arrays.asList(state.split("\\|"));
	// putOnScreen(Float.parseFloat(stateInfo.get(0)),
	// Float.parseFloat(stateInfo.get(1)), Float
	// .parseFloat(stateInfo.get(2)), Float.parseFloat(stateInfo.get(3)), Float
	// .parseFloat(stateInfo.get(4)), Float.parseFloat(stateInfo.get(5)),
	// Integer
	// .parseInt(stateInfo.get(6)));
	// }



	@Override
	public double getArea() {
		return Math.PI * (Math.pow(radius, 2));
	}

	@Override
	public String getType() {
		return TYPE;
	}

	@Override
	public void scale(float factor) {
		// Modifies the radius.
		this.radius = radius * factor;
	}
}
