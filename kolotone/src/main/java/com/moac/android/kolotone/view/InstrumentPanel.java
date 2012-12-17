package com.moac.android.kolotone.view;

import com.moac.android.kolotone.InstrumentThread;
import com.moac.android.kolotone.PDManager;
import com.moac.android.kolotone.instruments.Instrument;
import com.moac.android.kolotone.model.AbstractShape;
import com.moac.android.kolotone.model.Arena;
import com.moac.android.kolotone.model.Ball;
import com.moac.android.kolotone.model.ColorMap;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class InstrumentPanel extends SurfaceView implements
SurfaceHolder.Callback {

	private static String TAG = InstrumentPanel.class.getSimpleName();

	private InstrumentThread thread;
	private Arena arena;
	private Instrument instrument;

	public InstrumentPanel(Context context) {
		super(context);

		Log.d(TAG, "Creating InstrumentPanel without parameter");

		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);

		// create the game loop thread
		Display display = ((Activity)context).getWindowManager().getDefaultDisplay(); 
		float xMax = display.getWidth();
		float yMax = display.getHeight();

		arena = new Arena(0.6, 2, xMax, yMax);
		thread = new InstrumentThread(null, this.getHolder(), this);
		initArena();

		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}
	
	public InstrumentPanel(Context context, Instrument instrument) {
		this(context);
		
		Log.d(TAG, "Creating InstrumentPanel with instrument parameter");
		
		this.instrument = instrument;
	}


	private void initArena(){
		Log.d(TAG, "Initialising Arena");

		float xMax = (float)arena.getxMax();
		float yMax = (float)arena.getyMax();
		Ball b1 = new Ball(null, 100f,Color.RED, xMax / 2, yMax /2);
		Ball b2 = new Ball(null, 100f,Color.GREEN, xMax / 4, yMax /2);
		b2.getVelocity().toggleYDirection();
		Ball b3 = new Ball(null, 100f,Color.BLUE, xMax * 0.75f, yMax /2);
		b3.getVelocity().toggleXDirection();
		b3.getVelocity().toggleYDirection();

		arena.manage(b1);
		arena.manage(b2);
		arena.manage(b3);

		arena.addOverlapListener(instrument);

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
				boolean handled = obj.handleActionDown((int)event.getX(), (int)event.getY());
				if (handled)
				{
					
					instrument.touchDown(event, obj);
					break; // only do one
					// TODO Define some sort of ordering that is clear!.
				}

			} if (event.getAction() == MotionEvent.ACTION_MOVE) {
				// the gestures
				if (obj.isTouched()) {
					// the obj was picked up and is being dragged

					// TODO Ignore small movements ie taps
					// TODO Need some sort of layering so we can pick up
					// the "top" one and move only it when they overlap.
					// TODO lots of behaviour possibilities here 
					//if (!obj.isInside(event.getX(), event.getY()))
					//{
						obj.setXPos((int)event.getX());
						obj.setYPos((int)event.getY());
						instrument.touchMove(event, obj);
						break; // TODO Interesting, the other "incircle" one still stops. cos it has a "touched" register thru onDown
					//}
				}
			} if (event.getAction() == MotionEvent.ACTION_UP) {
				// touch was released
				if (obj.isTouched()) {
					obj.setTouched(false);
				}
				instrument.touchUp(event, obj);

			}

		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO is this supposed to do something?
		Log.d(TAG, "InstrumentPanel: empty onDraw() called");

	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;	
	}
}