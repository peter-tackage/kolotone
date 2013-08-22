package com.moac.android.kolotone.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.moac.android.kolotone.instruments.Instrument;
import com.moac.android.kolotone.model.AbstractShape;
import com.moac.android.kolotone.model.Arena;
import com.moac.android.kolotone.model.Ball;
import com.moac.android.kolotone.model.OverlapListener;

public class InstrumentView extends SurfaceView implements
  SurfaceHolder.Callback {

    private static String TAG = InstrumentView.class.getSimpleName();

    private AnimateLoop mLoop;
    private Arena mArena;
    private Instrument mInstrument;

    public InstrumentView(Context _context, Instrument _instrument) {
        super(_context);

        Log.d(TAG, "Creating InstrumentView");
        mInstrument = _instrument;

        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);

        // create the game loop mLoop
        Display display = ((Activity) _context).getWindowManager().getDefaultDisplay();

        mArena = initArena(display, mInstrument);

        mLoop = new AnimateLoop(this.getHolder(), this);

        // make the GamePanel focusable so it can handle events
        setFocusable(true);
    }

    private Arena initArena(Display _display, OverlapListener _overlapListener) {
        Log.d(TAG, "Initialising Arena");

        float xMax = _display.getWidth();
        float yMax = _display.getHeight();

        Arena arena = new Arena(0.6, 2, xMax, yMax);

        Ball b1 = new Ball(null, 100f, Color.RED, xMax / 2, yMax / 2);
        Ball b2 = new Ball(null, 100f, Color.GREEN, xMax / 4, yMax / 2);
        b2.getVelocity().toggleYDirection();
        Ball b3 = new Ball(null, 100f, Color.BLUE, xMax * 0.75f, yMax / 2);
        b3.getVelocity().toggleXDirection();
        b3.getVelocity().toggleYDirection();

        arena.addShape(b1);
        arena.addShape(b2);
        arena.addShape(b3);

        arena.addOverlapListener(_overlapListener);

        return arena;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "InstrumentView surfaceCreated()");
        mLoop.setRunning(true);
        mLoop.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "InstrumentView surfaceDestroyed()");
        boolean retry = true;
        while(retry) {
            try {
                mLoop.setRunning(false);
                mLoop.join();
                retry = false;
            } catch(InterruptedException e) {
                // try again shutting down the loop?
            }
        }
    }

    public void update() {
        mArena.update();
    }

    public void render(Canvas canvas) {
        // fills the canvas with black
        canvas.drawColor(Color.BLACK);

        // TODO Iterate thru the elements
        mArena.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "InstrumentView: onTouchEvent() called");
        for(AbstractShape shape : mArena.getShapes()) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                // delegating event handling to the obj
                boolean handled = shape.handleActionDown((int) event.getX(), (int) event.getY());
                if(handled) {
                    mInstrument.touchDown(event, shape);
                    return true;
                    // TODO Define some sort of ordering that is clear!.
                }
            } else if(event.getAction() == MotionEvent.ACTION_MOVE) {
                if(shape.isTouched()) {
                    // the obj was picked up and is being dragged

                    // TODO Ignore small movements ie taps
                    // TODO Need some sort of layering so we can pick up
                    // the "top" one and move only it when they overlap.
                    // TODO lots of behaviour possibilities here
                    //if (!obj.isInside(event.getX(), event.getY()))
                    //{
                    shape.setXPos((int) event.getX());
                    shape.setYPos((int) event.getY());
                    mInstrument.touchMove(event, shape);
                    // TODO Interesting, the other "incircle" one still stops. cos it has a "mIsTouched" register thru onDown
                    return true;
                    //}
                }
            } else if(event.getAction() == MotionEvent.ACTION_UP) {
                // touch was released
                if(shape.isTouched()) {
                    shape.setIsTouched(false);
                }
                mInstrument.touchUp(event, shape);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO is this supposed to do something?
        Log.d(TAG, "InstrumentView: empty onDraw() called");
    }

}