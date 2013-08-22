package com.moac.android.kolotone.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.moac.android.kolotone.PdEventListener;
import com.moac.android.kolotone.libpd.PdManager;
import org.puredata.android.service.PdService;
import org.puredata.core.PdBase;
import org.puredata.core.PdReceiver;
import org.puredata.core.utils.IoUtils;

import java.io.File;
import java.io.IOException;

public class InstrumentActivity extends Activity implements PdManager{

    private static String TAG = InstrumentActivity.class.getSimpleName();
    private static final int SAMPLE_RATE = 44100;
    private static String PATCH_PATH = "simplesine.small.4.2.pd";
    private static String LOCK_TAG = "Kolotone";

    PowerManager.WakeLock mWakeLock = null;

    /* synchronize on this lock whenever you access pdService */
    private final Object lock = new Object();

    /* the reference to the actual launched PdService */
    PdService pdService = null;

    /* this is where we'll save the handle of the Pd patch */
    int patch = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate() called");
        super.onCreate(savedInstanceState);

        //Set FullScreen & portrait
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initPdService();
    }

    @Override
    public void setPatch(String name) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume() called");
        super.onResume();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, LOCK_TAG);
        mWakeLock.acquire();
        // TODO If PD is initialised - restart.
    }

    @Override
    protected void onPause() {
        stopAudio();
        if(mWakeLock != null && mWakeLock.isHeld()) mWakeLock.release();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        cleanup();
        super.onDestroy();
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        /* This gets called when our service is bound and sets up */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            synchronized(lock) {
                pdService = ((PdService.PdBinder)service).getService();
                PdReceiver receiver = new PdEventListener();
                initPd(receiver);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
                /* this method will never be called */
        }
    };

    /**
     * Actually bind the service, which triggers the code above;
     * this is the method you should call to launch Pd
     */
    private void initPdService() {
        // A separate thread is not strictly necessary but it improves responsiveness
        new Thread() {
            @Override
            public void run() {
                bindService(new Intent(InstrumentActivity.this, PdService.class),
                  serviceConnection, BIND_AUTO_CREATE);
            }
        }.start();
    }


    // This is how we initialize Pd
    private void initPd(PdReceiver _receiver) {
        PdBase.setReceiver(_receiver);
  /* here we are adding the listener for various messages
     from Pd sent to "GUI", i.e., anything that goes into the object
     [s GUI] will send to the listener defined below */
        startAudio();
    }

    private void startAudio() {
        synchronized (lock) {
            if (pdService == null) return;
            if (!initAudio(2, 2) && !initAudio(1, 2)) {  /* see below */
                if (!initAudio(0, 2)) {
                    Log.e(TAG, "Unable to initialize audio interface");
                    finish();
                    return;
                } else {
                    Log.w(TAG, "No audio input available");
                }
            }
            if (patch == 0) {
                try {
                    // assuming here that the patch zipfile contained a single folder "patch/" that contains an _main.pd */
                    String path = "/sdcard/mypatches/patch";
                    // open Pd patch and save its handle for future reference */
                    patch = PdBase.openPatch(new File(path, "_main.pd"));
                } catch (IOException e) {
                    Log.e(TAG, e.toString());
                    finish();
                    return;
                }
                try {
        /* sleep for one second to give Pd a chance to load samples and such;
           this is not always necessary, but not doing this may give rise to
           obscure glitches when the patch contains audio files */
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // do nothing
                }
            }
            // Non-foreground priority PT.
            pdService.startAudio();
        }
    }

    /* helper method for startAudio();
       try to initialize Pd audio for the given number of input/output channels,
       return true on success */
    private boolean initAudio(int nIn, int nOut) {
        try {
            pdService.initAudio(SAMPLE_RATE, nIn, nOut, -1);
        // negative values default to PdService preferences
        } catch (IOException e) {
            Log.e("PdTag", e.toString());
            return false;
        }
        return true;
    }

    private void stopAudio() {
        synchronized (lock) {
            if (pdService == null) return;
            // Consider ramping down the volume here to avoid clicks
            pdService.stopAudio();
        }
    }


    private void cleanup() {
        synchronized(lock) {
            // make sure to release all resources
            stopAudio();
            if (patch != 0) {
                PdBase.closePatch(patch);
                patch = 0;
            }
            PdBase.release();
            try {
                unbindService(serviceConnection);
            } catch (IllegalArgumentException e) {
                // already unbound
                pdService = null;
            }
        }
    }
}


