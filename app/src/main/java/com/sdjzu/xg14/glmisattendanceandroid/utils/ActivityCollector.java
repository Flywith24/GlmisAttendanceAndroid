package com.sdjzu.xg14.glmisattendanceandroid.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 25/06/2017.
 * Activity 管理器
 * @author YYZ
 * @version 1.0.0
 */

public class ActivityCollector {
    private static List<Activity> sActivities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        sActivities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        sActivities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : sActivities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
