package com.moac.android.kolotone.effects;

import com.moac.android.kolotone.instruments.PSND;
import com.moac.android.kolotone.instruments.Parameter;

import java.util.HashMap;

public class ASDR extends Effect {

    public Parameter attack;
    public Parameter sustain;
    public Parameter decay;
    public Parameter release;

    public ASDR() {
        this.name = "asdr";

        params = new HashMap<String, Parameter>();
        attack = new Parameter(PSND.ATTACK, false);
        attack.setMinMax(0f, 100);
        attack.setDefault(100);
        params.put(PSND.ATTACK, attack);

        sustain = new Parameter(PSND.SUSTAIN, false);
        sustain.setMinMax(0f, 100);
        sustain.setDefault(100);
        params.put(PSND.SUSTAIN, sustain);

        decay = new Parameter(PSND.DECAY, false);
        decay.setMinMax(0f, 100);
        decay.setDefault(100);
        params.put(PSND.DECAY, decay);

        release = new Parameter(PSND.RELEASE, false);
        release.setMinMax(0f, 100);
        release.setDefault(100);
        params.put(PSND.RELEASE, release);

        this.yenabledlist = new String[]{
//				ATTACK,
//				SUSTAIN,
//				DECAY,
//				RELEASE
        };
        this.yenabled = false;
    }
}
