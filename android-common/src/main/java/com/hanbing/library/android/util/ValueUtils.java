package com.hanbing.library.android.util;

/**
 * Created by hanbing on 2016/10/8.
 */

public class ValueUtils {

    public static Integer intValueOf(String string) {
        return intValueOf(string, 0);
    }

    public static Integer intValueOf(String string, int defaultValue) {
        int value = defaultValue;

        if (null == string)
            return value;

        try {
            value = Integer.valueOf(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Float floatValueOf(String string) {
        return floatValueOf(string, 0);
    }

    public static Float floatValueOf(String string, float defaultValue) {
        float value = defaultValue;

        if (null == string)
            return value;

        try {
            value = Float.valueOf(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Long longValueOf(String string) {
        return longValueOf(string, 0);
    }

    public static Long longValueOf(String string, long defaultValue) {
        long value = defaultValue;

        if (null == string)
            return value;
        try {
            value = Long.valueOf(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }
}
