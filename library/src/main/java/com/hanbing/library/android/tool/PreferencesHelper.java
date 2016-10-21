package com.hanbing.library.android.tool;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hanbing on 2016/10/21
 */

public class PreferencesHelper {

    public static final String SP_NAME = "AndroidCommon";

    Context mContext;
    String mSpName;

    public static PreferencesHelper create(Context context) {
        return create(context, SP_NAME);
    }

    public static PreferencesHelper create(Context context, String spName) {
        return new PreferencesHelper(context, spName);
    }

    private PreferencesHelper(Context context) {
        this(context, SP_NAME);
    }

    private PreferencesHelper(Context context, String spName) {
        this.mContext = context;
        this.mSpName = spName;
    }


    public boolean putString(String key, String value) {
        SharedPreferences settings = mContext.getSharedPreferences(mSpName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }


    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        SharedPreferences settings = mContext.getSharedPreferences(mSpName, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }


    public boolean putInt(String key, int value) {
        SharedPreferences settings = mContext.getSharedPreferences(mSpName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }


    public int getInt(String key) {
        return getInt(key, -1);
    }


    public int getInt(String key, int defaultValue) {
        SharedPreferences settings = mContext.getSharedPreferences(mSpName, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }


    public boolean putLong(String key, long value) {
        SharedPreferences settings = mContext.getSharedPreferences(mSpName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }


    public long getLong(String key) {
        return getLong(key, -1);
    }


    public long getLong(String key, long defaultValue) {
        SharedPreferences settings = mContext.getSharedPreferences(mSpName, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }


    public boolean putFloat(String key, float value) {
        SharedPreferences settings = mContext.getSharedPreferences(mSpName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public float getFloat(String key) {
        return getFloat(key, -1);
    }

    public float getFloat(String key, float defaultValue) {
        SharedPreferences settings = mContext.getSharedPreferences(mSpName, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }


    public boolean putBoolean(String key, boolean value) {
        SharedPreferences settings = mContext.getSharedPreferences(mSpName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }


    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }


    public boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences settings = mContext.getSharedPreferences(mSpName, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }
}
