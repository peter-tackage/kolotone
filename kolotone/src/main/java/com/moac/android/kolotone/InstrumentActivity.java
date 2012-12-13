package com.moac.android.kolotone;

import com.moac.android.kolotone.view.InstrumentPanel;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.ShapeDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class InstrumentActivity extends Activity implements SensorEventListener {

    private static String TAG = InstrumentActivity.class.getSimpleName();
    
    ShapeDrawable mDrawable = new ShapeDrawable();

    private SensorManager sensorManager = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {

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
	    setContentView(new InstrumentPanel(this));

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
//
//    private void updateBall() {
//
//
//        //Calculate new speed
//        xVelocity += (xAcceleration * frameTime);
//        yVelocity += (yAcceleration * frameTime);
//
//        //Calc distance travelled in that time
//        float xS = (xVelocity/2)*frameTime;
//        float yS = (yVelocity/2)*frameTime;
//
//        //Add to position negative due to sensor 
//        //readings being opposite to what we want!
//        xPosition -= xS; 
//        yPosition -= yS;
//
//        if (xPosition > xmax) {
//            xPosition = xmax;
//        } else if (xPosition < 0) {
//            xPosition = 0;
//        }
//        if (yPosition > ymax) { 
//            yPosition = ymax;
//        } else if (yPosition < 0) {
//            yPosition = 0;
//        }
//    }

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
    }

    @Override
    protected void onStop()
    {
        Log.i(TAG, "onStop() called");
        // Unregister the listener
        sensorManager.unregisterListener(this);
        super.onStop();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i(TAG, "onConfigurationChanged() called");
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

}


