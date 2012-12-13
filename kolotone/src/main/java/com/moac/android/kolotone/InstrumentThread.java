package com.moac.android.kolotone;


import com.moac.android.kolotone.model.Arena;
import com.moac.android.kolotone.view.InstrumentPanel;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class InstrumentThread extends Thread {

	private static String TAG = InstrumentThread.class.getSimpleName();

	// desired fps
	private final static int    MAX_FPS = 50;

	// maximum number of frames to be skipped
	private final static int    MAX_FRAME_SKIPS = 5;
	// the frame period
	private final static int    FRAME_PERIOD = 1000 / MAX_FPS; 

	private volatile boolean running = true;

	private SurfaceHolder surfaceHolder;
	private InstrumentPanel instrumentPanel;

	public InstrumentThread(Arena world,
			SurfaceHolder surfaceHolder, InstrumentPanel instrumentPanel)
	{
		
        Log.i(TAG, "InstrumentThread created");

		this.surfaceHolder = surfaceHolder;
		this.instrumentPanel = instrumentPanel;
	}

	// This just updates the values, client-side 
	// then uses the updated values to draw the appropriate
	// representation.

	@Override
	public void run() {

		Canvas canvas;
		Log.d(TAG, "Starting instrument loop");

		long beginTime;     // the time when the cycle begun
		long timeDiff = 0;      // the time it took for the cycle to execute
		int sleepTime;      // ms to sleep (<0 if we're behind)
		int framesSkipped;  // number of frames being skipped

		sleepTime = 0;

		while (running) {
			canvas = null;
			// try locking the canvas for exclusive pixel editing
			// in the surface
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					beginTime = System.currentTimeMillis();
					framesSkipped = 0;  // resetting the frames skipped

					// update game state
					this.instrumentPanel.update();
					// render state to the screen
					// draws the canvas on the panel
					this.instrumentPanel.render(canvas);
					
					// calculate how long did the cycle take
					timeDiff = System.currentTimeMillis() - beginTime;
					// calculate sleep time
					sleepTime = (int)(FRAME_PERIOD - timeDiff);

					if (sleepTime > 0) {
						// if sleepTime > 0 we're OK
						try {
							// send the thread to sleep for a short period
							// very useful for battery saving
							Thread.sleep(sleepTime);
						} catch (InterruptedException e) {}
					}

					while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
						
						Log.d(TAG, "Catching up on updates");
						
						// we need to catch up
						// update without rendering
						this.instrumentPanel.update();
						// add frame period to check if in next frame
						sleepTime += FRAME_PERIOD;
						framesSkipped++;
					}

				}
			} finally {
				// in case of an exception the surface is not left in
				// an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}   // end finally
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
		Log.d(TAG, "safeStop() called");
		running = false;
		interrupt();
	}

	public void setRunning(boolean running)
	{
		this.running = running;
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
