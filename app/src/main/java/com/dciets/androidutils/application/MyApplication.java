package com.dciets.androidutils.application;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    private static Activity currentActivity = null;
    private static MyApplication instance;

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
    }

    public static void setCurrentActivity(Activity activity) {
        currentActivity = activity;
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static MyApplication getInstance() {
        return instance;
    }
}