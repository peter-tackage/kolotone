package com.moac.android.kolotone;

import android.util.Log;
import org.puredata.core.PdReceiver;

public class PdEventListener implements PdReceiver {
    public float audiolevel = 0f;

    @Override
    public void print(final String s) {
        Log.d("PdManager", "received print! " + s);
    }

    @Override
    public void receiveBang(final String source) {
        Log.d("PdManager", "received bang! " + source);
    }

    @Override
    public void receiveFloat(final String source, final float x) {
        //	Log.d("PdManager", "recieved float! "+source+" : "+x);
        if(source.equalsIgnoreCase("mainlevel")) {
            audiolevel = x;
        }
    }

    @Override
    public void receiveList(final String source, final Object... args) {
        Log.d("PdManager", "received list! " + source);
    }

    @Override
    public void receiveMessage(final String source, final String symbol,
                               final Object... args) {
        Log.d("PdManager", "received message! " + source);
    }

    @Override
    public void receiveSymbol(final String source, final String symbol) {
        Log.d("PdManager", "received symbol! " + source);
    }
};

