package com.sdjzu.xg14.glmisattendanceandroid.core;

import android.app.Application;

/**
 * Created on 22/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppStatusTracker.init(this);
    }
}
