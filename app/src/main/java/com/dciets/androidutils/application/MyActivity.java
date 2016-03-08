package com.dciets.androidutils.application;

import android.app.Activity;

/**
 * Created by marc_ on 2016-03-07.
 */
public class MyActivity extends Activity {

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setCurrentActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MyApplication.setCurrentActivity(null);
    }
}
