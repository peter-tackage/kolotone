package com.moac.android.kolotone.test;

import android.test.ActivityInstrumentationTestCase2;
import com.moac.android.kolotone.activity.InstrumentActivity;

public class InstrumentActivityTest extends ActivityInstrumentationTestCase2<InstrumentActivity> {

    public InstrumentActivityTest() {
        super(InstrumentActivity.class);
    }

    public void testActivity() {
        InstrumentActivity activity = getActivity();
        assertNotNull(activity);
    }
}

