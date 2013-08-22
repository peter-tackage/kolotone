package com.moac.android.kolotone.model;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Arena {

    private static String TAG = Arena.class.getSimpleName();

    // Defines the dimensions and nature of the arena that the
    // Physics engine uses.

    protected double density = 2d; // px/m
    protected double mReboundEnergyFactor = 0.6d; // Amount of energy returned

    private List<AbstractShape> mShapes = new ArrayList<AbstractShape>();
    private double mXMax;
    private double yMax;

    private List<OverlapListener> mOverlapListeners = new ArrayList<OverlapListener>();

    public Arena(double _reboundEnergyFactor, double _density, double _xMax,
                 double _yMax) {

        this.mReboundEnergyFactor = _reboundEnergyFactor;
        this.density = _density;
        this.mXMax = _xMax;
        this.yMax = _yMax;

        Log.d(TAG, "Creating Arena: " +
          " reboundEnergyFactor: " + _reboundEnergyFactor +
          " density: " + _density +
          " mXMax: " + _xMax +
          " yMax: " + _yMax);
    }

    public double getReboundEnergyFactor() {
        return mReboundEnergyFactor;
    }

    public double getDensity() {
        return density;
    }

    public double getXMax() {
        return mXMax;
    }

    public double getyMax() {
        return yMax;
    }

    public List<AbstractShape> getShapes() {
        return mShapes;
    }

    public void addShape(AbstractShape _shape) {
        Log.i(TAG, "Adding shape: " + _shape.getType() + " total: " + mShapes.size());
        mShapes.add(_shape);
    }

    public void removeShape(AbstractShape _shape) {
        Log.i(TAG, "Removing shape: " + _shape.getType() + " total: " + mShapes.size());
        mShapes.remove(_shape);
    }

    // TODO Use rebound coefficient
    // TODO Use wall dampening
    public void update() {
        for(AbstractShape obj : mShapes) {
            // check collision with right wall if heading right
            if(obj.getVelocity().getxDirection() == Velocity.DIRECTION_RIGHT
              && obj.getXPos() + obj.getWidth() / 2 >= mXMax) {
                obj.getVelocity().toggleXDirection();
            }
            // check collision with left wall if heading left
            if(obj.getVelocity().getxDirection() == Velocity.DIRECTION_LEFT
              && obj.getXPos() - obj.getWidth() / 2 <= 0) {
                obj.getVelocity().toggleXDirection();
            }
            // check collision with bottom wall if heading down
            if(obj.getVelocity().getyDirection() == Velocity.DIRECTION_DOWN
              && obj.getYPos() + obj.getHeight() / 2 >= yMax) {
                obj.getVelocity().toggleYDirection();
            }
            // check collision with top wall if heading up
            if(obj.getVelocity().getyDirection() == Velocity.DIRECTION_UP
              && obj.getYPos() - obj.getHeight() / 2 <= 0) {
                obj.getVelocity().toggleYDirection();
            }
            // Update position of the obj
            obj.update();
        }
        reportOverlaps();
    }

    private void reportOverlaps() {

        // Iterate through the shapes
        // TODO This currently will assume that all shapes are circles/balls ... it's easier!
        List<Overlap> overlaps = new ArrayList<Overlap>();

        SIDE_A:
        for(AbstractShape s1 : mShapes) {
            Ball b1 = (Ball) s1;

            SIDE_B:
            for(AbstractShape s2 : mShapes) {

                // Now cast them to circles
                Ball b2 = (Ball) s2;

                // Don't compare to self
                if(b1 == b2) continue SIDE_B;

                // Check if a collision has already been reported from the other side.
                for(Overlap c : overlaps) {
                    if(c.involves(s1, s2)) continue SIDE_B;
                }

                // Distance between centrepoints.
                double d = Math.abs(Math.hypot(b1.getXPos() - b2.getXPos(), b1.getYPos() - b2.getYPos()));
                if(d >= b1.getRadius() + b2.getRadius()) {

                    // See http://mathworld.wolfram.com/Circle-CircleIntersection.html

                    // We have a collision!
                    // Check if we have registered it already
                    double area =
                      0.5 * Math.sqrt((-d +
                        b1.getRadius() + b2.getRadius())
                        * (d + b1.getRadius() - b2.getRadius())
                        * (d - b1.getRadius() + b2.getRadius())
                        * (d + b1.getRadius() + b2.getRadius()));

                    Overlap o = new Overlap(s1, s2, area);
                    overlaps.add(o);
                }
            }

            // Now iterate through the listeners and report overlaps to them.
            for(OverlapListener ol : mOverlapListeners) {
                // TODO On a different thread??
                ol.notifyOverlap(overlaps);
            }
        }
    }

    public void draw(Canvas canvas) {
        for(AbstractShape shape : mShapes) {
            shape.draw(canvas);
        }
    }

    public void addOverlapListener(OverlapListener ol) {
        if(ol != null)
            mOverlapListeners.add(ol);
    }

    public void removeOverlapListener(OverlapListener ol) {
        mOverlapListeners.remove(ol);
    }
}
