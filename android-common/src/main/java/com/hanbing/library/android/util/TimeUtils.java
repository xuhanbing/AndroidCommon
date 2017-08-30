/**
 *
 */
package com.hanbing.library.android.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.TextUtils;

/**
 * Created by hanbing
 */
public class TimeUtils {

    static int[] DAY_OF_MONTH = {
            31, 28, 31, 30, 31, 30,
            31, 31, 30, 31, 30, 31
    };

    public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_FULL2 = "yyyy/MM/dd HH:mm:ss";
    public static final String FORMAT_DATE_TIME = "MM-dd  HH:mm";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATE_MD = "MM-dd";
    public static final String FORMAT_TIME_FULL = "HH:mm:ss";
    public static final String FORMAT_TIME_HM = "HH:mm";
    public static final String FORMAT_BIRTHDAY = FORMAT_DATE;

    /**
     * Get current time
     *
     * @return
     */
    public static String getTime() {
        return getTime(FORMAT_FULL);
    }

    /**
     * Get current time
     *
     * @param format
     * @return
     */
    public static String getTime(String format) {
        return getTime(format, System.currentTimeMillis());
    }

    /**
     * @param time
     * @return
     */
    public static String getTime(long time) {
        return getTime(FORMAT_FULL, time);
    }

    /**
     * @param format
     * @param time
     * @return
     */
    public static String getTime(String format, long time) {
        return new SimpleDateFormat(format).format(new Date(time));
    }

    /**
     * @param format
     * @param time
     * @return
     */
    public static long getTimeInMillis(String format, String time) {
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(new SimpleDateFormat(format).parse(time));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return c.getTimeInMillis();
    }

    /**
     * 当前时间添加指定毫秒，默认时间格式
     * @param srcTime 原始时间
     * @param timeInMillis 增加的毫秒
     * @return
     */
    public static String addTime(String srcTime, long timeInMillis) {
        return addTime(srcTime, timeInMillis, FORMAT_FULL);
    }

    /**
     * 当前时间添加指定毫秒
     * @param srcTime 原始时间
     * @param timeInMillis 增加的毫秒
     * @param format 时间格式
     * @return
     */
    public static String addTime(String srcTime, long timeInMillis, String format) {
        return addTime(srcTime, timeInMillis, format, format);
    }

    /**
     * 当前时间添加指定毫秒
     * @param srcTime 原始时间
     * @param timeInMillis 增加的毫秒
     * @param srcFormat 原时间格式
     * @param dstFormat 目标时间格式
     * @return
     */
    public static String addTime(String srcTime, long timeInMillis, String srcFormat, String dstFormat) {
        return getTime(dstFormat, TimeUtils.getTimeInMillis(srcFormat , srcTime) + timeInMillis);
    }

    /**
     * @param time
     * @param oldFormat
     * @param newFormat
     * @return
     */
    public static String changeFormat(String time, String oldFormat, String newFormat) {
        return getTime(newFormat, getTimeInMillis(oldFormat, time));
    }

    /**
     * @param birthday
     * @return
     */
    public static int calcAge(String birthday) {
        if (!TextUtils.isEmpty(birthday)) {
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(new SimpleDateFormat(FORMAT_BIRTHDAY).parse(birthday));
                return calcAge(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return 0;
    }

    /**
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    public static int calcAge(int year, int monthOfYear, int dayOfMonth) {
        final Calendar c = Calendar.getInstance();
        final int nowYear = c.get(Calendar.YEAR);
        final int nowMonth = c.get(Calendar.MONTH);
        final int nowDay = c.get(Calendar.DAY_OF_MONTH);

        int yearAge = (nowYear - year);
        if (monthOfYear > nowMonth || (monthOfYear == nowMonth && dayOfMonth > nowDay)) {
            yearAge--;
        }

        yearAge = Math.max(yearAge, 0);

        return yearAge;
    }


    /**
     * @param year
     * @return
     */
    public static boolean isLoopYear(int year) {
        return (0 == year % 400) || ((0 != year % 100) && (0 == year % 4));
    }


    /**
     * Calculate the day before next year.For example, from 2016-10-1 to 2017-9-30
     *
     * @param date
     * @return
     */
    public static String calcSecondYearDateBefore(String date) {
        if (TextUtils.isEmpty(date))
            return null;
        long timeInMillis = TimeUtils.getTimeInMillis("yyyy-MM-dd", date);

        return calcSecondYearDateBefore(timeInMillis);
    }

    /**
     * Calculate the day before next year.For example, from 2016-10-1 to 2017-9-30
     *
     * @param timeInMillis
     * @return
     */
    public static String calcSecondYearDateBefore(long timeInMillis) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);

        year += 1;

        day -= 1;

        //
        if (0 == day) {
            //prev month
            month -= 1;

            if (0 == month) {
                //prev year
                year -= 1;
                month = 12;
            }

            //date
            day = DAY_OF_MONTH[month - 1];

            //day +1 if is looper year
            if (isLoopYear(year) && 2 == month) {
                day += 1;
            }
        }

        return String.format("%4d-%02d-%02d", year, month, day);
    }


}
