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
     * 获取当前时间
     *
     * @return
     */
    public static String getTime() {
        return getTime(FORMAT_FULL);
    }

    /**
     * 获取当前时间
     *
     * @param format
     * @return
     */
    public static String getTime(String format) {
        return getTime(format, System.currentTimeMillis());
    }

    /**
     * 获取指定时间
     *
     * @param time
     * @return
     */
    public static String getTime(long time) {
        return getTime(FORMAT_FULL, time);
    }

    /**
     * 获取指定时间
     *
     * @param format
     * @param time
     * @return
     */
    public static String getTime(String format, long time) {
        return new SimpleDateFormat(format).format(new Date(time));
    }

    /**
     * 获取毫秒
     *
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
     * 改变时间格式
     *
     * @param time
     * @param oldFormat
     * @param newFormat
     * @return
     */
    public static String changeFormat(String time, String oldFormat, String newFormat) {
        return getTime(newFormat, getTimeInMillis(oldFormat, time));
    }

    /**
     * 计算周岁年龄
     *
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
     * 计算周岁年龄
     *
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

        /**
         * 计算年龄，按照周岁的算法
         */
        int yearAge = (nowYear - year);
        if (monthOfYear > nowMonth || (monthOfYear == nowMonth && dayOfMonth > nowDay)) {
            yearAge--;
        }

        yearAge = Math.max(yearAge, 0);

        return yearAge;
    }


    /**
     * 判断是否是闰年
     *
     * @param year
     * @return
     */
    public static boolean isLoopYear(int year) {
        return (0 == year % 400) || ((0 != year % 100) && (0 == year % 4));
    }


    /**
     * 计算第二年同一日的前一天 2016-10-1 -> 2017-9.30
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
     * 计算第二年同一日的前一天 2016-10-1 -> 2017-9.30
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

        //年 +1
        year += 1;

        //日期 -1
        day -= 1;

        //
        if (0 == day) {
            //进到前面的月份
            month -= 1;

            if (0 == month) {
                //进到前一年
                year -= 1;
                month = 12;
            }

            //获取日期
            day = DAY_OF_MONTH[month - 1];

            //如果是闰年且是二月，day + 1
            if (isLoopYear(year) && 2 == month) {
                day += 1;
            }
        }

        return String.format("%4d-%02d-%02d", year, month, day);
    }


}
