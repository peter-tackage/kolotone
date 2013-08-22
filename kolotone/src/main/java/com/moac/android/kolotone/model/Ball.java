package com.moac.android.kolotone.model;

import android.graphics.Canvas;
import android.util.Log;

public class Ball extends AbstractShape {

    private static String TAG = Ball.class.getSimpleName();

    public static final String TYPE = "Ball";

    private float mRadius = 20f;

    public Ball(Anchor anchor, float radius, int color, float xpos, float ypos) {
        super(color, xpos, ypos);
        this.mRadius = radius;

        Log.d(TAG, "Creating Ball: " + "Anchor: " + anchor +
          " | mRadius: " + radius +
          " | mColor: " + color +
          " | xpos: " + xpos +
          " | ypos: " + ypos);

    }

    public double getRadius() {
        return mRadius;
    }

    @Override
    public void update() {
        if(!mIsTouched) {
            mXPos += (mVelocity.getXv() * mVelocity.getxDirection());
            mYPos += (mVelocity.getYv() * mVelocity.getyDirection());
        }
    }

    @Override
    public double getArea() {
        return Math.PI * (Math.pow(mRadius, 2));
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void scale(double factor) {
        // TODO Not implemented yet.
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(mXPos, mYPos, mRadius, this.mPaint);
    }

    @Override
    public boolean handleActionDown(int eventX, int eventY) {
        Log.d(TAG, "handleActionDown: "
          + " eventX: " + eventX
          + " eventY: " + eventY);
        setIsTouched(inCircle(eventX, eventY, mXPos, mYPos, mRadius));
        return mIsTouched;
    }

    @Override
    public boolean isInside(double x, double y) {
        return inCircle(x, y, mXPos, mYPos, mRadius);
    }

    private boolean inCircle(double x, double y, double circleCenterX, double circleCenterY, double circleRadius) {
        double dx = Math.pow(x - circleCenterX, 2);
        double dy = Math.pow(y - circleCenterY, 2);

        if((dx + dy) < Math.pow(circleRadius, 2)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double getWidth() {
        return mRadius * 2; // is diameter
    }

    @Override
    public double getHeight() {
        return mRadius * 2; // is diameter
    }
}
