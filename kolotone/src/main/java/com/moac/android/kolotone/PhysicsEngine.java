package com.moac.android.kolotone;

import java.util.List;

public class PhysicsEngine extends Thread {

	private volatile boolean running = true;

	private List<AbstractOrb> objects;
	private World world;
	
	public PhysicsEngine(World world, List<AbstractOrb> objects)
	{
		this.world = world;
		this.objects = objects;
	}

	public void manage(AbstractOrb obj)
	{
		synchronized(objects)
		{
			objects.add(obj);
		}
	}

	public boolean unmanage(AbstractOrb obj)
	{
		synchronized(objects)
		{
			return objects.remove(obj);
		}
	}

	public List<AbstractOrb> getAll()
	{
		return objects;
	}

	// This just updates the values, client-side 
	// then uses the updated values to draw the appropriate
	// representation.

	@Override
	public void run() {
		while (running) {
			// TODO Lock + sync on objects
			try {
				// TODO Parameterise this.
				// or should it just be drive as past as possible?
				Thread.sleep(5);
				
				synchronized (objects) {
				for (AbstractOrb orb : objects)
				{
					
						// Move through constant motion and/or
						// application of external/internal force
						orb.update(world.getDensity());
						
						// Reset external force 
						// TODO Correct?
						orb.setAcceleration(0, 0);
						
						// Check for rebound from world edges.
						orb.checkRebound(world.getxMax(), world.getyMax(),
								world.getReboundEnergyFactor());	

				}
				}
			} catch (InterruptedException ie) {
				running = false;
			}
		}
	}
    
    // Collision of external force.
//	public void doBounce(BOUNCE_TYPE bounceType, float vX, float vY) {
//		switch (bounceType) {
//		case TOPLEFT:
//			if (xVelocity > 0) {
//				xVelocity = -xVelocity * reboundEnergyFactor;
//			}
//			if (yVelocity > 0) {
//				yVelocity = -yVelocity * reboundEnergyFactor;
//			}
//			break;
//		case TOP:
//			if (yVelocity > 0) {
//				yVelocity = -yVelocity * reboundEnergyFactor;
//			}
//			break;
//		case TOPRIGHT:
//			if (xVelocity < 0) {
//				xVelocity = -xVelocity * reboundEnergyFactor;
//			}
//			if (yVelocity > 0) {
//				yVelocity = -yVelocity * reboundEnergyFactor;
//			}
//			break;
//		case LEFT:
//			if (xVelocity > 0) {
//				xVelocity = -xVelocity * reboundEnergyFactor;
//			}
//			break;
//		case RIGHT:
//			if (xVelocity < 0) {
//				xVelocity = -xVelocity * reboundEnergyFactor;
//			}
//			break;
//		case BOTTOMLEFT:
//			if (xVelocity > 0) {
//				xVelocity = -xVelocity * reboundEnergyFactor;
//			}
//			if (yVelocity < 0) {
//				yVelocity = -yVelocity * reboundEnergyFactor;
//			}
//			break;
//		case BOTTOM:
//			if (yVelocity < 0) {
//				yVelocity = -yVelocity * reboundEnergyFactor;
//			}
//			break;
//		case BOTTOMRIGHT:
//			if (xVelocity < 0) {
//				xVelocity = -xVelocity * reboundEnergyFactor;
//			}
//			if (yVelocity < 0) {
//				yVelocity = -yVelocity * reboundEnergyFactor;
//			}
//			break;
//		}
//		// TODO Magic numbers
//		xVelocity = xVelocity + (vX * 500);
//		yVelocity = yVelocity + (vY * 150);
//	}

	public void safeStop() {
		running = false;
		interrupt();
	}

	// TODO This is the external application of force.
//	private void handleCollision() {
//		// TODO: Handle multiball case
//		if (mBall == null) {
//			return;
//		}
//		if (mPaddleTimes.size() < 1) {
//			return;
//		}
//
//		Point ballCenter = getBallCenter();
//		Point paddleCenter = getPaddleCenter();
//
//		final int dy = ballCenter.y - paddleCenter.y;
//		final int dx = ballCenter.x - paddleCenter.x;
//
//		final float distance = dy * dy + dx * dx;
//
//		if (distance < ((2 * mBallRadius) * (2 * mPaddleRadius))) {
//			// Get paddle velocity
//			float vX = 0;
//			float vY = 0;
//			Point endPoint = new Point(-1, -1);
//			Point startPoint = new Point(-1, -1);
//			long timeDiff = 0;
//			try {
//				endPoint = mPaddlePoints.get(mPaddlePoints.size() - 1);
//				startPoint = mPaddlePoints.get(0);
//				timeDiff = mPaddleTimes.get(mPaddleTimes.size() - 1) - mPaddleTimes.get(0);
//			} catch (IndexOutOfBoundsException e) {
//				// Paddle points were removed at the last moment
//				return;
//			}
//			if (timeDiff > 0) {
//				vX = ((float) (endPoint.x - startPoint.x)) / timeDiff;
//				vY = ((float) (endPoint.y - startPoint.y)) / timeDiff;
//			}
//			// Determine the bounce type
//			BOUNCE_TYPE bounceType = BOUNCE_TYPE.TOP;
//			if ((ballCenter.x < (paddleCenter.x - mPaddleRadius / 2))
//					&& (ballCenter.y < (paddleCenter.y - mPaddleRadius / 2))) {
//				bounceType = BOUNCE_TYPE.TOPLEFT;
//			} else if ((ballCenter.x > (paddleCenter.x + mPaddleRadius / 2))
//					&& (ballCenter.y < (paddleCenter.y - mPaddleRadius / 2))) {
//				bounceType = BOUNCE_TYPE.TOPRIGHT;
//			} else if ((ballCenter.x < (paddleCenter.x - mPaddleRadius / 2))
//					&& (ballCenter.y > (paddleCenter.y + mPaddleRadius / 2))) {
//				bounceType = BOUNCE_TYPE.BOTTOMLEFT;
//			} else if ((ballCenter.x > (paddleCenter.x + mPaddleRadius / 2))
//					&& (ballCenter.y > (paddleCenter.y + mPaddleRadius / 2))) {
//				bounceType = BOUNCE_TYPE.BOTTOMRIGHT;
//			} else if ((ballCenter.x < paddleCenter.x)
//					&& (ballCenter.y > (paddleCenter.y - mPaddleRadius / 2))
//					&& (ballCenter.y < (paddleCenter.y + mPaddleRadius / 2))) {
//				bounceType = BOUNCE_TYPE.LEFT;
//			} else if ((ballCenter.x > paddleCenter.x)
//					&& (ballCenter.y > (paddleCenter.y - mPaddleRadius / 2))
//					&& (ballCenter.y < (paddleCenter.y + mPaddleRadius / 2))) {
//				bounceType = BOUNCE_TYPE.RIGHT;
//			} else if ((ballCenter.x > (paddleCenter.x - mPaddleRadius / 2))
//					&& (ballCenter.x < (paddleCenter.x + mPaddleRadius / 2))
//					&& (ballCenter.y > paddleCenter.y)) {
//				bounceType = BOUNCE_TYPE.RIGHT;
//			}
//
//			mBall.doBounce(bounceType, vX, vY);
//			if (!mPlayer.isPlaying()) {
//				mPlayer.release();
//				mPlayer = MediaPlayer.create(this, R.raw.collision);
//				mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//				mPlayer.start();
//			}
//		}
//	}

}
