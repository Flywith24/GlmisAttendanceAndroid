package com.sdjzu.xg14.glmisattendanceandroid.core;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.sdjzu.xg14.glmisattendanceandroid.constants.ConstantValues;
import com.sdjzu.xg14.glmisattendanceandroid.utils.L;

/**
 * Created on 22/05/2017.
 *
 * @author YYZ
 * @version 1.0.0
 */

public class AppStatusTracker implements Application.ActivityLifecycleCallbacks {
    private static final long MAX_INTERVAL = 5 * 60 * 1000;
    private static AppStatusTracker tracker;
    private Application application;
    private int mAppStatus = ConstantValues.STATUS_OFFLINE;
    private boolean isForground;
    private int activeCount;
    private long timestamp;
    private boolean isScreenOff;
    private DaemonReceiver receiver;

    private AppStatusTracker(Application application) {
        this.application = application;
        application.registerActivityLifecycleCallbacks(this);
    }

    public static void init(Application application) {
        tracker = new AppStatusTracker(application);
    }

    public static AppStatusTracker getInstance() {
        return tracker;
    }

    public void setAppStatus(int status) {
        this.mAppStatus = status;
        if (status == ConstantValues.STATUS_ONLINE) {
            if (receiver == null) {
                IntentFilter filter = new IntentFilter();
                filter.addAction(Intent.ACTION_SCREEN_OFF);
                receiver = new DaemonReceiver();
                application.registerReceiver(receiver, filter);
            }
        } else if (receiver != null) {
            application.unregisterReceiver(receiver);
            receiver = null;
        }
    }

    public int getAppStatus() {
        return this.mAppStatus;
    }

    public boolean isForground() {
        return isForground;
    }

    private void onScreenOff(boolean isScreenOff) {
        this.isScreenOff = isScreenOff;
    }

    public boolean checkIfShowGesture() {
        if (mAppStatus == ConstantValues.STATUS_ONLINE) {
            if (isScreenOff) {
                return true;
            }
            if (timestamp != 0l && System.currentTimeMillis() - timestamp > MAX_INTERVAL) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        activeCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        isForground = true;
        timestamp = 0l;
        isScreenOff = false;
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        activeCount--;
        if (activeCount == 0) {
            isForground = false;
            timestamp = System.currentTimeMillis();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    private class DaemonReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            L.d("onReceive:", action);
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                AppStatusTracker.getInstance().onScreenOff(true);
            }
        }
    }
}
