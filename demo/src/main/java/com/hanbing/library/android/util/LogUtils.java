/**
 *
 */
package com.hanbing.library.android.util;

import android.util.Log;


/**
 * Created by hanbing
 */
public class LogUtils {
    static  boolean DEBUG = true;
    static final String TAG = "Log";

    public static void setDebug(boolean isDebug){
        DEBUG = isDebug;
    }

    /**
     * 打印日志
     *
     * @param msg
     */
    public static void e(String msg) {
        e(TAG, msg);
    }

    /**
     * 打印日志
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (DEBUG)
            Log.e(tag, msg);
    }


    /**
     * 打印日志
     *
     * @param msg
     */
    public static void d(String msg) {
        d(TAG, msg);
    }

    /**
     * 打印日志
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (DEBUG)
            Log.d(tag, msg);
    }


    /**
     * 打印日志
     *
     * @param msg
     */
    public static void i(String msg) {
        i(TAG, msg);
    }

    /**
     * 打印日志
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (DEBUG)
            Log.i(tag, msg);
    }


    /**
     * 打印日志
     *
     * @param msg
     */
    public static void w(String msg) {
        w(TAG, msg);
    }

    /**
     * 打印日志
     *
     * @param tag
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (DEBUG)
            Log.w(tag, msg);
    }


    /**
     * 打印日志
     *
     * @param msg
     */
    public static void v(String msg) {
        v(TAG, msg);
    }

    /**
     * 打印日志
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (DEBUG)
            Log.v(tag, msg);
    }

    /**
     * 打印日志
     *
     * @param msg
     */
    public static void wtf(String msg) {
        wtf(TAG, msg);
    }

    /**
     * 打印日志
     *
     * @param tag
     * @param msg
     */
    public static void wtf(String tag, String msg) {
        if (DEBUG)
            Log.wtf(tag, msg);
    }
}
