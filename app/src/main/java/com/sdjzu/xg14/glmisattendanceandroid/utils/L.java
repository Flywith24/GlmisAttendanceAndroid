package com.sdjzu.xg14.glmisattendanceandroid.utils;

import android.util.Log;

/**
 * Created on 22/05/2017.
 *
 * 自定义日志打印，app发布时我们要取消日志打印。
 * @author YYZ
 * @version 1.0.0
 */

public class L {
    /**
     * 定义6个静态常量，用来表示日志信息的打印等级
     * 由1到5打印等级依次升高
     */
    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;

    private static final int NOTHING = 6;

    /**
     * 该静态常量的值用来控制你想打印的日志等级；
     * 比如当前LEVEL的值为常量1（VERBOSE），那么你以上5个日志等级都是可以打印的；
     * 假如当前LEVEL的值为常量2（DEBUG），那么你只能打印从DEBUG（2）到ERROR（5）之间的日志信息；
     * 假如你要是不想让日志信息打印出现，那么将LEVEL的值置为NOTHING即可。
     */
    private static final int LEVEL = DEBUG;

    // 调用Log.v()方法打印日志
    public static void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    // 调用Log.d()方法打印日志
    public static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            Log.d(tag, msg);
        }
    }
    // 调用Log.d()方法打印日志
    public static void d(String msg) {
        if (LEVEL <= DEBUG) {
            Log.d("yyz", msg);
        }
    }

    // 调用Log.i()方法打印日志
    public static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
            Log.i(tag, msg);
        }
    }

    // 调用Log.w()方法打印日志
    public static void w(String tag, String msg) {
        if (LEVEL <= WARN) {
            Log.w(tag, msg);
        }
    }

    // 调用Log.e()方法打印日志
    public static void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
            Log.e(tag, msg);
        }
    }
}