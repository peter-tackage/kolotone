package com.moac.android.kolotone.model;

public abstract class Anchor {

    private double mXPos = 0;
    private double mYPos = 0;

    public Anchor(double xPos, double yPos) {
        super();
        this.mXPos = xPos;
        this.mYPos = yPos;
    }

    public double getXPos() { return mXPos; }
    public void setXPos(double _xPos) { mXPos = _xPos; }

    public double getYPos() { return mYPos; }
    public void setYPos(double _yPos) { mYPos = _yPos; }
}
