package com.dciets.androidutils.application;

import android.support.v4.app.FragmentActivity;

/**
 * Created by marc_ on 2016-03-07.
 */
public class MyFragmentActivity extends FragmentActivity {

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
