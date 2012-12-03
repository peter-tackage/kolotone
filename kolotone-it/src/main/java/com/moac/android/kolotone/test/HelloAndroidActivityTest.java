package com.moac.android.kolotone.test;

import android.test.ActivityInstrumentationTestCase2;
import com.moac.android.kolotone.*;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<HelloAndroidActivity> {

    public HelloAndroidActivityTest() {
        super(HelloAndroidActivity.class); 
    }

    public void testActivity() {
        HelloAndroidActivity activity = getActivity();
        assertNotNull(activity);
    }
}

