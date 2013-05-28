package com.moac.android.kolotone.effects;

import com.moac.android.kolotone.instruments.BinaryParameter;
import com.moac.android.kolotone.instruments.PSND;
import com.moac.android.kolotone.instruments.Parameter;

import java.util.HashMap;

public class Vibrato extends Effect {

    public Vibrato() {
        this.name = "vibrato";

        params = new HashMap<String, Parameter>();
        final Parameter vibspeed = new Parameter(PSND.VIBRATO_SPEED, false);
        vibspeed.setMinMax(0f, 50);
        vibspeed.setDefaultNaive(0.4f);
        params.put(PSND.VIBRATO_SPEED, vibspeed);
        final Parameter vibdepth = new Parameter(PSND.VIBRATO_DEPTH, false);
        vibdepth.setMinMax(0f, 100f);
        vibdepth.setDefault(0f);
        params.put(PSND.VIBRATO_DEPTH, vibdepth);

        final Parameter enabled = new BinaryParameter(PSND.VIBRATO_ENABLED, true);
        enabled.setMinMax(0, 1);
        enabled.setDefault(1);
        params.put(PSND.VIBRATO_ENABLED, enabled);

        final Parameter vibwaveform = new Parameter(PSND.VIBRATO_WAVEFORM, false);
        vibwaveform.setMinMax(0f, 100f);
        vibwaveform.setDefault(1f);
        params.put(PSND.VIBRATO_WAVEFORM, vibwaveform);

        this.yenabledlist = new String[]{
          PSND.VIBRATO_SPEED,
          PSND.VIBRATO_DEPTH,
        };
        this.yenabled = false;
    }
}
