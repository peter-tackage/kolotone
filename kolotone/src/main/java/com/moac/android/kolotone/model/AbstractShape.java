package com.moac.android.kolotone.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class AbstractShape {

    protected int color = Color.WHITE;
    protected Paint paint;

    protected boolean isCollidable = false;
    protected Anchor anchor = null;
    protected int mass = 1;

    // Movement
    protected float xPos = 0;
    protected float yPos = 0;

    protected Velocity velocity;

    protected double rotation = 0d;
    protected long lastUpdate;
    protected boolean touched = false;

    public AbstractShape(int color, float xpos, float ypos) {
        this.lastUpdate = System.currentTimeMillis();
        this.color = color;
        this.paint = new Paint();
        paint.setColor(color);
        this.xPos = xpos;
        this.yPos = ypos;
        this.velocity = new Velocity();
    }

    abstract public double getArea();

    abstract public String getType();

    abstract public void draw(Canvas canvas);

    abstract public boolean handleActionDown(int eventX, int eventY);

    abstract public double getWidth();

    abstract public double getHeight();

    abstract public void update();

    abstract public boolean isInside(double x, double y);

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public double getXPos() {
        return xPos;
    }

    public void setXPos(float xPos) {
        this.xPos = xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void setYPos(float yPos) {
        this.yPos = yPos;
    }

    public Velocity getVelocity() {
        return velocity;
    }

    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setCollidable(boolean isCollidable) {
        this.isCollidable = isCollidable;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    /*
     * 1 is initial size. Range is 0 - infinite.
     */
    abstract public void scale(double factor);

    public int getColor() {
        return this.color;
    }

    public boolean isCollidable() {
        return this.isCollidable;
    }

    public Anchor getAnchor() {
        return this.anchor;
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
    }

    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }
}
