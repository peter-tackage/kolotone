package com.moac.android.kolotone.instruments;

import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import com.moac.android.kolotone.libpd.PdManager;
import com.moac.android.kolotone.effects.*;
import com.moac.android.kolotone.model.AbstractShape;
import com.moac.android.kolotone.model.ColorMap;
import com.moac.android.kolotone.model.Overlap;
import com.moac.android.kolotone.model.OverlapListener;
import org.puredata.core.PdBase;

import java.util.ArrayList;
import java.util.List;

public class Instrument implements OverlapListener {

    public String TAG = Instrument.class.getSimpleName();

    final PdManager mPdManager;

    final private ArrayList<Effect> mEffects = new ArrayList<Effect>();
    final private Volume mVolume;

    private int patch;
    private String patchName;

    public float midiMin = 0;
    public float midiMax = 127;
    public float waveform = 1;

    public boolean mIsReady = false;
    public ColorMap mColorMap;

    public Instrument(final PdManager _pdManager, ColorMap _colorMap) {
        mPdManager = _pdManager;
        mColorMap = initColorMap(_colorMap);
        mVolume = new Volume();
        mEffects.add(new ASDR());
        mEffects.add(new Vibrato());
        mEffects.add(new Tremolo());
        mEffects.add(new Delay());
        mEffects.add(new Reverb());
        mEffects.add(new Filter());
        mEffects.add(mVolume);
    }

    public void setPatch(final String patch) {
        patchName = patch;
        initInstrument();
        mIsReady = true;
    }

    public void initInstrument() {
        patch = mPdManager.openPatch(patchName);
    }

    private ColorMap initColorMap(ColorMap _cm) {
        // Em
        _cm.setNote(Color.RED, 329.628f);
        _cm.setNote(Color.GREEN, 493.883f);
        _cm.setNote(Color.BLUE, 783.991f);
        return _cm;
    }

    public void touchUp(final MotionEvent me, AbstractShape s) {
        Log.d(TAG, "touchUp: " + s.getColor() + " mIsReady: " + mIsReady);
        if(mIsReady) {
            for(final Effect e : mEffects) {
                e.touchUp(me, s);
            }
        }
    }

    public void touchMove(final MotionEvent me, AbstractShape s) {
        Log.d(TAG, "touchMove: " + s.getColor() + " mIsReady: " + mIsReady);
        if(mIsReady) {
            setVolume(1);
            setPitch(mColorMap.getNote(s.getColor()));
            for(final Effect e : mEffects) {
                e.touchMove(me, s);
            }
        }
    }

    public void touchDown(final MotionEvent me, AbstractShape s) {
        Log.d(TAG, "touchDown: " + s.getColor() + " mIsReady: " + mIsReady);
        if(mIsReady) {
            setVolume(1);
            setPitch(mColorMap.getNote(s.getColor()));
            for(final Effect e : mEffects) {
                e.touchDown(me, s);
            }
        }
    }

    public void setMidiMin(final float val) {
        this.midiMin = val;
    }

    public void setMidiMax(final float val) {
        this.midiMax = val;
    }

    private void sendMessage(final String s, final float val) {
        PdBase.sendFloat(s, val);
    }

    private void sendMessage(final String s, final float val, final int index) {
        PdBase.sendFloat(s + index, val);
    }

    public void setPitch(final float val) {
        float pitch = midiMin + ((val + (1 / (2 * midiMax - 2 * midiMin))) * (midiMax - midiMin));
        Log.v(TAG, "Setting pitch: " + val + " to normalised value: " + pitch);
        pitch = (float) Math.floor(pitch);
        sendMessage("pitch", pitch);
    }

    public void setVolume(final float amp) {
        mVolume.setVolume(amp);
    }

    public void setWaveform(final float waveform) {
        this.waveform = waveform;
        sendMessage("inssel", waveform);
    }

    @Override
    public String toString() {
        return "Instrument is - patch: " + patch
          + " patchName: " + patchName
          + " isReady: " + mIsReady
          + " mVolume: " + mVolume
          + " waveform: " + waveform
          + " midiMin: " + midiMin
          + " midiMax: " + midiMax;
    }

    public void cleanup() {
        PdBase.closePatch(patch);
    }

    @Override
    public void notifyOverlap(List<Overlap> overlaps) {
        // TODO Interpret and generate tones.
    }
}
