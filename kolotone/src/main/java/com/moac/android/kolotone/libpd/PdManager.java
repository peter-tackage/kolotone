package com.moac.android.kolotone.libpd;

public interface PdManager {

    public void setPatch(String name);

//    private static final String TAG = PdManager.class.getSimpleName();
//
//    private static final int SAMPLE_RATE = 44100;
//
//    private boolean mIsRecording = false;
//    private String mRecordingFilename = "";
//
//    PdReceiver mReceiver;
//
//    private final Context mContext;
//
//    /* synchronize on this lock whenever you access pdService */
//    private final Object lock = new Object();
//
//    /* the reference to the actual launched PdService */
//    PdService pdService = null;
//
//    public PdManager(final Context _context, PdReceiver _receiver) {
//        mContext = _context;
//        mReceiver = _receiver;
//    }
//
//    private final ServiceConnection serviceConnection = new ServiceConnection() {
//        /* This gets called when our service is bound and sets up */
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            synchronized(lock) {
//                pdService = ((PdService.PdBinder)service).getService();
//                initPd();   /* see below */
//            }
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            /* this method will never be called */
//        }
//    };
//
//    /* actually bind the service, which triggers the code above;
//       this is the method you should call to launch Pd */
//    private void initPdService() {
//  /* a separate thread is not strictly necessary,
//     but it improves responsiveness */
//        new Thread() {
//            @Override
//            public void run() {
//                bindService(new Intent(MyPdDemo.this, PdService.class),
//                  serviceConnection, BIND_AUTO_CREATE);
//            }
//        }.start();
//    }
//
//    /* this is how we initialize Pd */
//    private void initPd() {
//  /* here is where we bind the print statement catcher defined below */
//        PdBase.setReceiver(myDispatcher);
//  /* here we are adding the listener for various messages
//     from Pd sent to "GUI", i.e., anything that goes into the object
//     [s GUI] will send to the listener defined below */
//        dispatcher.addListener("GUI", myListener);
//        startAudio();  /* see below */
//    }
//
//    public void onResume() {
//        if(AudioParameters.suggestSampleRate() < SAMPLE_RATE) {
//            Log.e(TAG, "Required sample rate not available; exiting");
//            finish();
//            return;
//        }
//        final int nOut = Math.min(AudioParameters.suggestOutputChannels(), 2);
//        if(nOut == 0) {
//            Log.e(TAG, "Audio output not available; exiting");
//            finish();
//            return;
//        }
//        try {
//            PdAudio.initAudio(SAMPLE_RATE, 0, nOut, 1, true);
//            PdAudio.startAudio(mContext);
//            PdBase.setReceiver(mReceiver);
//            PdBase.subscribe("mainlevel");
//            PdBase.subscribe("pitch");
//        } catch(final IOException e) {
//            Log.e(TAG, "Failed to initialise PDAudio" + e.getMessage());
//        }
//    }
//
//    public void onPause() {
//        PdAudio.stopAudio();
//    }
//
//    public void onDestroy() {
//        finish();
//    }
//
//    private void finish() {
//        Log.d(TAG, "Finishing for some reason");
//        // make sure to release all resources
//        PdAudio.stopAudio();
//        PdBase.release();
//    }
//
//    public int openPatch(final String patch) {
//        final File dir = mContext.getFilesDir();
//        final File patchFile = new File(dir, patch);
//        int out = -1;
//        try {
//            IoUtils.extractZipResource(mContext.getResources().openRawResource(R.raw.patch), dir, true);
//            out = PdBase.openPatch(patchFile.getAbsolutePath());
//        } catch(final IOException e) {
//            e.printStackTrace();
//            Log.e(TAG, e.toString() + "; exiting now");
//            finish();
//        }
//        return out;
//    }

//    private static final char[] ILLEGAL_CHARACTERS = { '/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':' };
//
//    public String recordOnOff(File parentdir, String name, boolean addTimestamp) {
//        if(mIsRecording) {
//            String filename = mRecordingFilename;
//            endRecording();
//            return filename;
//        } else {
//            String filename = name;
//            if(addTimestamp) {
//                Date d = new Date();
//                filename += "-" + d.toString();
//            }
//            filename = filename.replace(' ', '_');
//            filename = filename.replace('/', '_');
//            for(char c : ILLEGAL_CHARACTERS) {
//                filename = filename.replace(c, '.');
//            }
//            filename += ".wav";
//            filename = parentdir.getAbsolutePath() + "/" + filename;
//            startRecording(filename);
//            return null;
//        }
//    }
//
//    public void startRecording(String filename) {
//        Log.d("PdManager", "Starting recording at : " + filename);
//        mIsRecording = true;
//        mRecordingFilename = filename;
//        PdBase.sendSymbol("record_filename", filename);
//        PdBase.sendBang("record_start");
//    }
//
//    public void endRecording() {
//        Log.d("PdManager", "Ending recording at : " + mRecordingFilename);
//        PdBase.sendBang("record_stop");
//        mIsRecording = false;
//        mRecordingFilename = null;
//    }
}
