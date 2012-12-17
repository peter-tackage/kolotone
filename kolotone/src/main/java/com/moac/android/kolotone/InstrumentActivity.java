package com.moac.android.kolotone;

import com.moac.android.kolotone.instruments.Instrument;
import com.moac.android.kolotone.instruments.JSONPresets;
import com.moac.android.kolotone.model.ColorMap;
import com.moac.android.kolotone.view.InstrumentPanel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.ShapeDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class InstrumentActivity extends Activity implements SensorEventListener {

    private static String TAG = InstrumentActivity.class.getSimpleName();
    
	public static final String SHARED_PREFERENCES_AUDIO = "shared_prefs_audio";
	public static final String SHARED_PREFERENCES_APPSTUFF = "appstufffz";
	
	public static String PATCH_PATH;

    ShapeDrawable mDrawable = new ShapeDrawable();

    private SensorManager sensorManager = null;
    PowerManager.WakeLock wl = null;
    
	public PDManager pdman;
	public Instrument instrument;
	public InstrumentPanel vis;
	
	boolean pdready = false;
	boolean startingup = true;
	Runnable readyrunnable = new Runnable() {
		public void run() {
			if (startingup == false) {
				pdready = false;
				if (pdman != null) {
					pdman.onResume();
				
				
					pdready = true;
					Log.v("InstrumentActivityReadyRunnable", "Destroying popup!");
				}
				// TODO This is the splash screen
		//		runOnUiThread(new Runnable() { public void run() {loadingview.setVisibility(View.GONE);}});
			}
		}
	};
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

		initStatics();

        super.onCreate(savedInstanceState);
        
        Log.i(TAG, "onCreate() called");

        //Set FullScreen & portrait
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Get a reference to a SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
                
        // set our InstrumentPanel as the View
        vis = new InstrumentPanel(this);
	    setContentView(vis);

	    asyncSetup.execute(new Void[0]);
    }
    
	public void initStatics() {
	//	PATCH_PATH = Launcher.getUIType() == Launcher.PHONE ? "simplesine.small.4.2.pd"  : "simplesine4.2.pd";

		PATCH_PATH = "simplesine.small.4.2.pd";
	}
    
    // This method will update the UI on new sensor events
    public void onSensorChanged(SensorEvent sensorEvent)
    {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {
           //     Log.i(TAG, "Sensor detected change in orientation");

                //Set sensor values as acceleration
              //  panel.yAcceleration = sensorEvent.values[1]; 
              ///  xAcceleration = sensorEvent.values[2];
            //    updateBall();
            }
    }


    // I've chosen to not implement this method
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        // TODO Auto-generated method stub
    }

    @Override
    protected void onResume()
    {
        Log.i(TAG, "onResume() called");
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_GAME);
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "PlasmaSoundHDLock");
		wl.acquire();

		if (pdready == true) {
		    pdready = false;
			if (pdman != null) pdman.onResume(readyrunnable);
		}
		readSettings();
		
	}
    
    @Override
    protected void onPause()
    {
		super.onPause();
		if (pdman != null) pdman.onPause();
		if (wl != null && wl.isHeld()) wl.release();
    }

    @Override
    protected void onStop()
    {
        Log.i(TAG, "onStop() called");
    	super.onStop();

        // Unregister the listener
        sensorManager.unregisterListener(this);
        // TODO Don't care for now - fix this later PT.
  //  	JSONPresets.getPresets().removeListener(this);

    }
    
    @Override
    protected void onDestroy()
    {
		if (pdman != null) pdman.cleanup();
    	super.onDestroy();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i(TAG, "onConfigurationChanged() called");
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    
	AsyncTask<Void,Void,Void> asyncSetup = new AsyncTask<Void,Void,Void>() {
		@Override
		protected Void doInBackground(final Void... params) {
			startingup = true;
			Log.v("InstrumentActivitySetup", "creating pd");
		    //PD Stuff
		    pdman = new PDManager(InstrumentActivity.this);
			Log.v("InstrumentActivitySetup", "launching pd");
		    pdready = false;
		    pdman.onResume();
		    
			Log.v("InstrumentActivitySetup", "Starting instrument");
		    //Make the Instrument
		    instrument = new Instrument(pdman, new ColorMap());		   
			Log.v("InstrumentActivitySetup", "setting instrument patch");
			instrument.setPatch(PATCH_PATH);
			instrument.setMidiMin(70);
			instrument.setMidiMax(87);
					    
			Log.v("InstrumentActivitySetup", "Reading settings");
			loadPresets();
			if (loadPresets()) {
				if (JSONPresets.getPresets().loadDefault(InstrumentActivity.this, instrument) == null) {}//if there is no defaults
			}
			readSettings();	    
			
			vis.setInstrument(instrument);
			Log.v("InstrumentActivitySetup", "Done!");
			return null;
		}
		
		@Override
		protected void onPostExecute(final Void params) {
			Log.v("InstrumentActivitySetup", "Destroying popup!");
			pdready = true;
			startingup = false;
			// This ends the splash screen
		//	loadingview.setVisibility(View.GONE);
			
			// TODO Need to have check if PDManager init fails.
		}
	};

	public boolean loadPresets() { return true; }

	
    public void readSettings() {
    	if (instrument == null) {
    		return;
    	}
        final SharedPreferences mPrefs = this.getSharedPreferences(SHARED_PREFERENCES_AUDIO, 0);
    	instrument.updateSettings(this, mPrefs);
    }

}


