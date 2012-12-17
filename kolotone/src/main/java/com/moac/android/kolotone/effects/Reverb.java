package com.moac.android.kolotone.effects;

import java.util.HashMap;

import com.moac.android.kolotone.instruments.BinaryParameter;
import com.moac.android.kolotone.instruments.PSND;
import com.moac.android.kolotone.instruments.Parameter;


public  class Reverb extends Effect {	
	
	public Reverb() {
		this.name = "reverb";

		params = new HashMap<String, Parameter>();
		final Parameter REVERBtime = new Parameter(PSND.REVERB_TIME, true);
		REVERBtime.setMinMax(0f, 100f);
		REVERBtime.setDefaultNaive(0.2f);
		params.put(PSND.REVERB_TIME, REVERBtime );
		final Parameter feedback = new Parameter(PSND.REVERB_FEEDBACK, true);
		feedback.setMinMax(0f, 100f);
		feedback.setDefaultNaive(0.7f);
		params.put(PSND.REVERB_FEEDBACK, feedback );
		final Parameter enabled = new BinaryParameter(PSND.REVERB_ENABLED, true);
		enabled.setMinMax(0, 1);
		enabled.setDefaultNaive(1);
		params.put(PSND.REVERB_ENABLED, enabled );
		
		this.yenabled = false;

	}
		


}
