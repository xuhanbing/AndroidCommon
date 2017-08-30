package com.hanbing.library.android.util;

/**
 * Created by hanbing on 2016/10/8.
 */

public class ValueUtils {


    public static Boolean toBoolean(String string) {
        return toBoolean(string, false);
    }

    public static Boolean toBoolean(String string, boolean defaultValue) {
        boolean value = defaultValue;

        if (null == string)
            return value;

        return Boolean.valueOf(string);
    }

    public static Byte toByte(String string) {
        return toByte(string, (byte) 0);
    }

    public static byte toByte(String string, byte defaultValue) {
        byte value = defaultValue;

        if (null == string)
            return value;

        try {
            value = Byte.valueOf(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Integer toInt(String string) {
        return toInt(string, 0);
    }

    public static Integer toInt(String string, int defaultValue) {
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

    public static Float toFloat(String string) {
        return toFloat(string, 0);
    }

    public static Float toFloat(String string, float defaultValue) {
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

    public static Double toDouble(String string) {
        return toDouble(string, 0);
    }

    public static Double toDouble(String string, double defaultValue) {
        double value = defaultValue;

        if (null == string)
            return value;

        try {
            value = Double.valueOf(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static Long toLong(String string) {
        return toLong(string, 0);
    }

    public static Long toLong(String string, long defaultValue) {
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
