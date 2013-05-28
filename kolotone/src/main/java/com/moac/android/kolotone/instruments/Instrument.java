package com.moac.android.kolotone.instruments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import com.moac.android.kolotone.PDManager;
import com.moac.android.kolotone.effects.*;
import com.moac.android.kolotone.model.AbstractShape;
import com.moac.android.kolotone.model.ColorMap;
import com.moac.android.kolotone.model.Overlap;
import com.moac.android.kolotone.model.OverlapListener;
import org.json.JSONObject;
import org.puredata.core.PdBase;

import java.util.ArrayList;
import java.util.List;

public class Instrument implements OverlapListener {

    public String TAG = Instrument.class.getSimpleName();

    final PDManager p;

//	public static int MAX_INDEX;

    final private ArrayList<Effect> effects = new ArrayList<Effect>();
    final private Volume volume;

    private int patch;
    private String patchName;

    public float midiMin = 0;
    //	public int visualQuality;
    public float midiMax = 127;
    public float waveform = 1;
//	public static int NCONTINUOUS = 0;
//	public static int NQUANTIZE = 1;
//	public static int NSLIDE = 2;
//	public int quantize = NCONTINUOUS;
//	public String quantval;

    public boolean ready = false;
    public ColorMap colorMap;

//	TouchAbstraction touchabs;

    public Instrument(final PDManager p, ColorMap cm) {
//		MAX_INDEX = Launcher.getUIType() == Launcher.PHONE ? 4 : 8; //phones support 4 touches, tablets support 8;
//		touchabs = new TouchAbstraction(MAX_INDEX);
//		visualQuality  = Launcher.getUIType() == Launcher.PHONE ? 1 : 2;
        this.p = p;
        this.colorMap = cm;
        initColorMap();
        volume = new Volume();
        effects.add(new ASDR());
        effects.add(new Vibrato());
        effects.add(new Tremolo());
        effects.add(new Delay());
        effects.add(new Reverb());
        effects.add(new Filter());
        effects.add(volume);
    }

    public void setPatch(final String patch) {
        patchName = patch;
        initInstrument();
        ready = true;
    }

    public void initInstrument() {
        patch = p.openPatch(patchName);
    }

    private void initColorMap() {
        // Em
        colorMap.setNote(Color.RED, 329.628f);
        colorMap.setNote(Color.GREEN, 493.883f);
        colorMap.setNote(Color.BLUE, 783.991f);
    }

    public void touchUp(final MotionEvent me, AbstractShape s) {
        Log.d(TAG, "touchUp: " + s.getColor() + " ready: " + ready);

        if(ready) {
            for(final Effect e : effects) {
                e.touchUp(me, s);
            }
        }
    }

    public void touchMove(final MotionEvent me, AbstractShape s) {
        Log.d(TAG, "touchMove: " + s.getColor() + " ready: " + ready);

        if(ready) {
            setVolume(1);
            setPitch(colorMap.getNote(s.getColor()));
            for(final Effect e : effects) {
                e.touchMove(me, s);
            }
        }
    }

    public void touchDown(final MotionEvent me, AbstractShape s) {
        Log.d(TAG, "touchDown: " + s.getColor() + " ready: " + ready);

        if(ready) {
            setVolume(1);
            setPitch(colorMap.getNote(s.getColor()));
            for(final Effect e : effects) {
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

//	public void setVisualQuality(final int val) {
//		this.visualQuality = val;
//	}

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

//	public boolean isCursorSnapped(final Cursor c, final float width) {
//		if (c == null) return false;
//		final float spacing = (midiMax-midiMin)/width;
//		final int firstClosestX = Math.round((c.firstPoint.x) * spacing);
//		final int lastClosestX = Math.round((c.currentPoint.x) * spacing);
//		if (firstClosestX == lastClosestX) {
//			return true;
//		}
//		else {
//			return false;
//		}
//	}

    public void setVolume(final float amp) {
        volume.setVolume(amp);
    }

    public void setWaveform(final float waveform) {
        this.waveform = waveform;
        sendMessage("inssel", waveform);
    }

    public void updateSettings(Context context, final SharedPreferences prefs) {
        updateSettings(context, prefs, "");
    }

    public void updateSettings(Context context, final SharedPreferences prefs, final String preset) {
        try {
            final float prefMidiMin = prefs.getInt(preset + PSND.MIDI_MIN, 70);
            final float prefMidiMax = prefs.getInt(preset + PSND.MIDI_MAX, 86);
            setMidiMin(prefMidiMin);
            setMidiMax(prefMidiMax);

            // Commented out - not doing yet PT.
//			String defaultQuality = "1";//Launcher.getPhoneCPUPower(context) > Launcher.PRETTY_CRAP ? "1" : "0";
//			defaultQuality = Launcher.getUIType() == Launcher.PHONE ? defaultQuality : "2";
//			String qual = defaultQuality;
//			if (prefs.contains(preset+PSND.VISUAL_QUALITY)) {
//				qual = prefs.getString(preset+PSND.VISUAL_QUALITY, defaultQuality);
//			} else {
//				Editor edit = prefs.edit();
//				edit.putString(preset+PSND.VISUAL_QUALITY, qual);
//				edit.commit(); //so the preference box sees our change.
//			}
//			final int prefsQual = Integer.parseInt(qual);
//			setVisualQuality(prefsQual);

            final String s_waveform = prefs.getString(preset + PSND.WAVEFORM, "1.0");
            final Float waveform = Float.parseFloat(s_waveform);
            setWaveform(waveform);

            for(final Effect e : effects) {
                e.updateSettings(prefs, preset);
            }
        } catch(final Exception e) { e.printStackTrace(); }

        Log.d(TAG, "Update setting (non-JSON) " + this.toString());
    }

    public void updateSettingsFromJSON(JSONObject prefs) {
        updateSettingsFromJSON(prefs, false, null);
    }

    public void updateSettingsFromJSON(JSONObject prefs, boolean savetoshared, SharedPreferences sprefs) {
        try {
            Log.d("INSTRUMENT", "Settings changed from JSON!!!!!!!!!!!");
            Editor edit = sprefs.edit();
            final float prefMidiMin = prefs.has(PSND.MIDI_MIN) ? prefs.getInt(PSND.MIDI_MIN) : 70;
            if(savetoshared) edit.putInt(PSND.MIDI_MIN, (int) prefMidiMin);
            final float prefMidiMax = prefs.has(PSND.MIDI_MAX) ? prefs.getInt(PSND.MIDI_MAX) : 86;
            if(savetoshared) edit.putInt(PSND.MIDI_MAX, (int) prefMidiMax);
            setMidiMin(prefMidiMin);
            setMidiMax(prefMidiMax);

            final String s_waveform = prefs.has(PSND.WAVEFORM) ? prefs.getString(PSND.WAVEFORM) : "1.0";
            final Float waveform = Float.parseFloat(s_waveform);
            if(savetoshared) edit.putString(PSND.WAVEFORM, s_waveform);
            setWaveform(waveform);

            for(final Effect e : effects) {
                e.updateSettingsFromJSON(prefs, savetoshared, edit);
            }

            if(savetoshared) edit.commit();
        } catch(final Exception e) { e.printStackTrace(); }

        Log.d(TAG, this.toString());
    }

    @Override
    public String toString() {
        return "Instrument is - patch: " + patch
          + " patchName: " + patchName
          + " isReady: " + ready
          + " volume: " + volume
          + " waveform: " + waveform
          + " midiMin: " + midiMin
          + " midiMax: " + midiMax;
    }

    public JSONObject saveSettingsToJSON(JSONObject prefs) {
        try {
            prefs.put(PSND.MIDI_MIN, this.midiMin);
            prefs.put(PSND.MIDI_MAX, this.midiMax);
            prefs.put(PSND.WAVEFORM, this.waveform);

            for(final Effect e : effects) {
                prefs = e.saveSettingsToJSON(prefs);
            }

            return prefs;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cleanup() {
        PdBase.closePatch(patch);
    }

    @Override
    public void notifyOverlap(List<Overlap> overlaps) {
        // TODO Auto-generated method stub
        // Interpret and generate tones.
    }
}
