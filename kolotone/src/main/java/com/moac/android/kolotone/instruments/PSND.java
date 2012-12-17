package com.moac.android.kolotone.instruments;

import com.moac.android.kolotone.R;

import android.content.Context;
import android.content.res.Resources;

public class PSND {
	public static String MIDI_MIN = "midimin";
	public static String MIDI_MAX = "midimax";
	public static String VISUAL_QUALITY = "visualqual";
	public static String WAVEFORM = "waveform";
	public static String QUANTIZE = "quantize_note_list";
	public static String QUAT_CONTINUOUS = "continuous";
	public static String QUAT_QUANTIZE = "quantize";
	public static String QUAT_SLIDE = "slide";

	
	
	public static String AMP_GLOBAL = "ampglob";
	public static String AMP = "amp";
	public static String AMP_ON = "noteon";
	public static String AMP_OFF = "noteoff";

	
	
	public static String VIBRATO_SPEED = "vibspeed";
	public static String VIBRATO_DEPTH = "vibdepth";
	public static String VIBRATO_ENABLED = "vibrato_onoff";
	public static String VIBRATO_WAVEFORM = "vibwaveform";

	
	public static String TREMOLO_SPEED = "tremolospeed";
	public static String TREMOLO_DEPTH = "tremolodepth";
	public static String TREMOLO_ENABLED = "tremolo_onoff";
	public static String TREMOLO_WAVEFORM = "tremolowaveform";

	
	public static String REVERB_TIME = "revebrtime";
	public static String REVERB_FEEDBACK = "reverbfeedback";
	public static String REVERB_ENABLED = "reverb_onoff";

	
	public static String FILT = "filt";
	public static String FILTER_ENABLED = "filter_onoff";

	
	public static String DELAY_TIME = "delaytime";
	public static String DELAY_FEEDBACK = "delayfeedback";
	public static String DELAY_ENABLED = "delay_onoff";

	
	public static String ATTACK = "attack";
	public static String SUSTAIN = "sustain";
	public static String DECAY = "decay";
	public static String RELEASE = "release";
	
	public static void readFromResources(Context context) {
		Resources r = context.getResources();
		
		 MIDI_MIN = r.getString(R.string.midimin);
		 MIDI_MAX = r.getString(R.string.midimax);
		 VISUAL_QUALITY = r.getString(R.string.visualqual);
		 WAVEFORM = r.getString(R.string.waveform);
		 QUANTIZE = r.getString(R.string.quantize_note_list);
		 QUAT_CONTINUOUS = r.getString(R.string.continuous);
		 QUAT_QUANTIZE = r.getString(R.string.quantize);
		 QUAT_SLIDE = r.getString(R.string.slide);

		
		
		 AMP_GLOBAL = r.getString(R.string.ampglob);
		 AMP = r.getString(R.string.amp);
		 AMP_ON = r.getString(R.string.noteon);
		 AMP_OFF = r.getString(R.string.noteoff);

		
		
		 VIBRATO_SPEED = r.getString(R.string.vibspeed);
		 VIBRATO_DEPTH = r.getString(R.string.vibdepth);
		 VIBRATO_ENABLED = r.getString(R.string.vibrato_onoff);
		 VIBRATO_WAVEFORM = r.getString(R.string.vibrato_waveform);

		
		 TREMOLO_SPEED = r.getString(R.string.tremolospeed);
		 TREMOLO_DEPTH = r.getString(R.string.tremolodepth);
		 TREMOLO_ENABLED = r.getString(R.string.tremolo_onoff);
		 TREMOLO_WAVEFORM = r.getString(R.string.tremolo_waveform);

		
		 REVERB_TIME = r.getString(R.string.revebrtime);
		 REVERB_FEEDBACK = r.getString(R.string.reverbfeedback);
		 REVERB_ENABLED = r.getString(R.string.reverb_onoff);

		
		 FILT = r.getString(R.string.filt);
		 FILTER_ENABLED = r.getString(R.string.filter_onoff);

		
		 DELAY_TIME = r.getString(R.string.delaytime);
		 DELAY_FEEDBACK = r.getString(R.string.delayfeedback);
		 DELAY_ENABLED = r.getString(R.string.delay_onoff);

		
		 ATTACK = r.getString(R.string.attack);
		 SUSTAIN = r.getString(R.string.sustain);
		 DECAY = r.getString(R.string.decay);
		 RELEASE = r.getString(R.string.release);		
		
	}

}
