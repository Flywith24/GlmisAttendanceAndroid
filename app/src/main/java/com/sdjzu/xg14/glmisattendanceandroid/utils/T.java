package com.sdjzu.xg14.glmisattendanceandroid.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created on 06/06/2017.
 * 显示Toast的工具类
 *
 * @author YYZ
 * @version 1.0.0
 */

public class T {
    private static Toast toast;

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public static void showToast(Context context,
                                 int rsId) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    rsId,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(context.getResources().getString(rsId));
        }
        toast.show();
    }
}
