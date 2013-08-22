package com.moac.android.kolotone.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.moac.android.kolotone.instruments.Instrument;
import com.moac.android.kolotone.model.ColorMap;
import com.moac.android.kolotone.view.InstrumentView;

public class InstrumentFragment extends Fragment {

    private Instrument mInstrument;

    @Override
    public void onAttach(Activity _activity) {
        super.onAttach(_activity);
        Log.v("InstrumentActivitySetup", "Starting instrument");
        //Make the Instrument
        mInstrument = new Instrument(mPdManager, new ColorMap());
        Log.v("InstrumentActivitySetup", "setting instrument patch");
        mInstrument.setPatch(PATCH_PATH);
        mInstrument.setMidiMin(70);
        mInstrument.setMidiMax(87);
        Log.v("InstrumentActivitySetup", "Done!");
    }

    @Override
    public View onCreateView(LayoutInflater _inflater, ViewGroup _container,
                             Bundle _savedInstanceState) {
        // Set our InstrumentView as the View - note View IS attached therefore non-null.
        View view = new InstrumentView(getActivity(), mInstrument);
        return view;
    }
}
