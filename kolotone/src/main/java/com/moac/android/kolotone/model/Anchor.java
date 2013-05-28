package com.moac.android.kolotone.model;

public abstract class Anchor {

    private double xPos = 0;
    private double yPos = 0;

    public Anchor(double xPos, double yPos) {
        super();
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }
}
