package com.moac.android.kolotone.view;


import com.moac.android.kolotone.InstrumentThread;
import com.moac.android.kolotone.model.AbstractShape;
import com.moac.android.kolotone.model.Arena;
import com.moac.android.kolotone.model.Ball;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class InstrumentPanel extends SurfaceView implements
SurfaceHolder.Callback {

	private static String TAG = InstrumentPanel.class.getSimpleName();

	private InstrumentThread thread;
	private Arena arena;

	public InstrumentPanel(Context context) {
		super(context);
		
		Log.d(TAG, "Creating InstrumentPanel");

		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create the game loop thread
		// TODO use the actual values
        arena = new Arena(0.6f, 2, 500, 800);
		thread = new InstrumentThread(null, this.getHolder(), this);
		initialiseArena();
		

		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}
	
	
	
	
	private void initialiseArena(){
		Log.d(TAG, "Initialising Arena");

		Ball b1 = new Ball(null, 100,Color.RED, arena.getxMax() / 2, arena.getyMax() /2);
		Ball b2 = new Ball(null, 100,Color.GREEN, arena.getxMax() / 4, arena.getyMax() /2);
		b2.getSpeed().toggleYDirection();
		Ball b3 = new Ball(null, 100,Color.BLUE, arena.getxMax() * 0.75f, arena.getyMax() /2);
		b3.getSpeed().toggleXDirection();
		b3.getSpeed().toggleYDirection();
		
		arena.manage(b1);
		arena.manage(b2);
		arena.manage(b3);


	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "InstrumentPanel surfaceCreated()");

		thread.setRunning(true);
		thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "InstrumentPanel surfaceDestroyed()");

		boolean retry = true;
		while (retry) {
			try {
				thread.setRunning(false);
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
	}


	public void update() {
	//	Log.d(TAG, "InstrumentPanel: update() called");
		arena.update();
	}


	public void render(Canvas canvas) {

	//	Log.d(TAG, "InstrumentPanel: render() called");

		// fills the canvas with black
		canvas.drawColor(Color.BLACK);

		// TODO Iterate thru the elements
		arena.draw(canvas);
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		Log.d(TAG, "InstrumentPanel: onTouchEvent() called");

		for (AbstractShape obj : arena.getObjects())
		{
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// delegating event handling to the obj
			obj.handleActionDown((int)event.getX(), (int)event.getY());

			// check if in the lower part of the screen we exit
			if (event.getY() > getHeight() - 50) {
				thread.setRunning(false);
				((Activity)getContext()).finish();
			} else {
				Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
			}
		} if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// the gestures
			if (obj.isTouched()) {
				// the obj was picked up and is being dragged
				
				// TODO Ignore small movements ie taps
				// TODO Need some sort of layering so we can pick up
				// the "top" one and move only it when they overlap.
				obj.setXPos((int)event.getX());
				obj.setYPos((int)event.getY());
			}
		} if (event.getAction() == MotionEvent.ACTION_UP) {
			// touch was released
			if (obj.isTouched()) {
				obj.setTouched(false);
			}
		}
		
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO is this supposed to do something?
		Log.d(TAG, "InstrumentPanel: empty onDraw() called");

	}
}