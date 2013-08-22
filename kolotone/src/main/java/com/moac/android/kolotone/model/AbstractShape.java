package com.moac.android.kolotone.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class AbstractShape {

    protected int mColor = Color.WHITE;
    protected Paint mPaint;

    protected boolean mIsCollidable = false;
    protected Anchor mAnchor = null;
    protected int mMass = 1;

    // Movement
    protected float mXPos = 0;
    protected float mYPos = 0;

    protected Velocity mVelocity;

    protected double mRotation = 0d;
    protected long mLastUpdatedAt;
    protected boolean mIsTouched = false;

    public AbstractShape(int _color, float _xpos, float _ypos) {
        mLastUpdatedAt = System.currentTimeMillis();
        mColor = _color;
        mPaint = new Paint();
        mPaint.setColor(_color);
        mXPos = _xpos;
        mYPos = _ypos;
        mVelocity = new Velocity();
    }

    abstract public double getArea();
    abstract public String getType();
    abstract public void draw(Canvas canvas);
    abstract public boolean handleActionDown(int eventX, int eventY);
    abstract public double getWidth();
    abstract public double getHeight();
    abstract public void update();
    abstract public void scale(double factor);

    abstract public boolean isInside(double x, double y);

    public int getMass() { return mMass; }
    public void setMass(int mass) { mMass = mass; }

    public double getXPos() { return mXPos; }
    public void setXPos(float xPos) { mXPos = xPos; }

    public double getYPos() { return mYPos; }
    public void setYPos(float yPos) { mYPos = yPos; }

    public Velocity getVelocity() { return mVelocity; }
    public void setVelocity(Velocity v) { mVelocity = v; }

    public void setColor(int color) { mColor = color; }
    public int getColor() {return mColor; }

    public boolean isCollidable() {return mIsCollidable;}
    public void setIsCollidable(boolean isCollidable) { mIsCollidable = isCollidable; }

    public double getRotation() { return mRotation; }
    public void setRotation(double rotation) { mRotation = rotation; }

    public Anchor getAnchor() { return mAnchor; }
    public void setAnchor(Anchor anchor) { mAnchor = anchor; }

    public boolean isTouched() { return mIsTouched; }
    public void setIsTouched(boolean isTouched) {mIsTouched = isTouched;}
}
