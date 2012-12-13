package com.moac.android.kolotone.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.util.Log;

public class Ball extends AbstractShape {

    private static String TAG = Ball.class.getSimpleName();

	public static final String TYPE = "Ball";
	
	private float radius = 20;
	private Paint textPaint;

	public Ball(Anchor anchor, float radius, int color, float xpos, float ypos) {
		super(color, xpos, ypos);
		this.radius = radius;

		Log.d(TAG, "Creating Ball: " + "Anchor: " + anchor +
				" | radius: " + radius +
				" | color: " + color +
				" | xpos: " + xpos +
				" | ypos: " + ypos);
		
		textPaint = new Paint();
		textPaint.setColor(Color.BLACK);
		paint.setXfermode(new PorterDuffXfermode(Mode.SCREEN));
	}

	public float getRadius() {
		return radius;
	}

//	@Override
//	public void checkRebound(float reboundEnergyFactor, float xMax, float yMax) {
//		// Handle rebounding on both sides simultaneously
//		if (yPos - radius < 0) {
//			reboundXPos = xPos;
//			reboundYPos = radius;
//			reboundXVelocity = xVelocity;
//			reboundYVelocity = -yVelocity * reboundEnergyFactor;
//		} else if (yPos + radius > yMax) {
//			reboundXPos = xPos;
//			reboundYPos = yMax - radius;
//			reboundXVelocity = xVelocity;
//			reboundYVelocity = -yVelocity * reboundEnergyFactor;
//		}
//
//		if (xPos - radius < 0) {
//			reboundXPos = radius;
//			reboundYPos = yPos;
//			reboundXVelocity = -xVelocity * reboundEnergyFactor;
//			reboundYVelocity = yVelocity;
//		} else if (xPos + radius > xMax) {
//			reboundXPos = xMax - radius;
//			reboundYPos = yPos;
//			reboundXVelocity = -xVelocity * reboundEnergyFactor;
//			reboundYVelocity = yVelocity;
//		}
//
//		// Moved this into here.
//		// Perhaps it should be deferred, for smoothness??
//        xPos = reboundXPos;
//        yPos = reboundYPos;
//        xVelocity = reboundXVelocity;
//        yVelocity = reboundYVelocity;
//	}

	public String getState() {
		String state = "";
		state = xPos + "|" + yPos + "|"
				+ "|" + speed;
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


	public void update() {
//		Log.d(TAG, "updating Ball: " + "" +
//				"touched: " + touched);

    if (!touched) {
        xPos += (speed.getXv() * speed.getxDirection());
        yPos += (speed.getYv() * speed.getyDirection());
    }
}

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
	
	@Override
	public  void draw(Canvas canvas) {		
		 canvas.drawCircle(xPos, yPos, radius, this.paint);
		 canvas.drawText(xPos + "|" + yPos + "|" + speed.getxDirection()
				 + "|" + speed.getyDirection() + "|" + touched, xPos - radius, yPos, textPaint);
	}

	@Override
	public void handleActionDown(int eventX, int eventY) {
		Log.d(TAG, "handleActionDown: " 
				+ " eventX: " + eventX
				+ " eventY: " + eventY);
		setTouched(inCircle(eventX, eventY, xPos, yPos, radius));
	}
	
	private boolean inCircle(float x, float y, float circleCenterX, float circleCenterY, float circleRadius) {
	    double dx = Math.pow(x - circleCenterX, 2);
	    double dy = Math.pow(y - circleCenterY, 2);

	    if ((dx + dy) < Math.pow(circleRadius, 2)) {
	        return true;
	    } else {
	        return false;
	    }
	}

	@Override
	public float getWidth() {
		return radius * 2; // is diameter
 	}

	@Override
	public float getHeight() {
		return radius * 2; // is diameter
	}
}
