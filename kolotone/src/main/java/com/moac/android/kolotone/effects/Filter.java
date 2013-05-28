package com.moac.android.kolotone.effects;

import com.moac.android.kolotone.instruments.BinaryParameter;
import com.moac.android.kolotone.instruments.PSND;
import com.moac.android.kolotone.instruments.Parameter;

import java.util.HashMap;

public class Filter extends Effect {

    public Filter() {
        this.name = "filter";

        params = new HashMap<String, Parameter>();
        final Parameter filt = new Parameter(PSND.FILT, false);
        filt.setMinMax(0f, 20f);
        filt.setDefaultNaive(0.9f);
        params.put(PSND.FILT, filt);

        final Parameter enabled = new BinaryParameter(PSND.FILTER_ENABLED, true);
        enabled.setMinMax(0, 1);
        enabled.setDefaultNaive(1);
        params.put(PSND.FILTER_ENABLED, enabled);

        this.yenabledlist = new String[]{
          PSND.FILT,
        };
        this.yenabled = false;
    }
}
