package com.moac.android.kolotone.effects;

import java.util.HashMap;

import android.util.Log;

import com.moac.android.kolotone.instruments.PSND;
import com.moac.android.kolotone.instruments.Parameter;


public  class Volume extends Effect {	
	
	public static final String TAG = Volume.class.getSimpleName();

	public Parameter amp; 
	public Parameter ampglobal;
	public Parameter on;
	public Parameter off;
	
	public Volume() {
		this.name = "volume";

		params = new HashMap<String, Parameter>();
		ampglobal = new Parameter(PSND.AMP_GLOBAL, true);
		ampglobal.setMinMax(0f, 1f);
		ampglobal.setDefault(0.9f);
		//params.put(AMP_GLOBAL, ampglobal );
		amp = new Parameter(PSND.AMP, false);
		amp.setMinMax(0f, 1f);
		amp.setDefault(0.9f);
		params.put(PSND.AMP, amp );
		
		on = new Parameter(PSND.AMP_ON, false);
		on.setMinMax(0, 1);
		on.setDefault(0);
		
		off = new Parameter(PSND.AMP_OFF, false);
		off.setMinMax(0, 1);
		off.setDefault(0);

		
	    
		this.yenabledlist = new String[] {
				PSND.AMP,
		};
		this.yenabled = false;

	}

	
	public void setVolume(final float val) {
		Log.d(TAG, "Setting volume to: " + val);
		ampglobal.pushValue(val);
	}

}
