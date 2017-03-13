package com.hanbing.library.android.util;

import android.content.Context;
import android.content.SharedPreferences;


public class PreferencesUtils {

    private PreferencesUtils() {
    }

    public static boolean putString(Context context, String spName, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }


    public static String getString(Context context, String spName, String key) {
        return getString(context, spName, key, null);
    }

    public static String getString(Context context, String spName, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }


    public static boolean putInt(Context context, String spName, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }


    public static int getInt(Context context, String spName, String key) {
        return getInt(context, spName, key, -1);
    }


    public static int getInt(Context context, String spName, String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }


    public static boolean putLong(Context context, String spName, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }


    public static long getLong(Context context, String spName, String key) {
        return getLong(context, spName, key, -1);
    }


    public static long getLong(Context context, String spName, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }


    public static boolean putFloat(Context context, String spName, String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static float getFloat(Context context, String spName, String key) {
        return getFloat(context, spName, key, -1);
    }

    public static float getFloat(Context context, String spName, String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }


    public static boolean putBoolean(Context context, String spName, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }


    public static boolean getBoolean(Context context, String spName, String key) {
        return getBoolean(context, spName, key, false);
    }


    public static boolean getBoolean(Context context, String spName, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }
}