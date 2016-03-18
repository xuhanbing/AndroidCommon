package com.common.util;

import android.content.Context;

import com.common.tool.PinyinParser;
import com.common.tool.py.ChineseToPinyinHelper;

/**
 * Created by hanbing on 2016/3/18.
 */
public class PinyinUtils {


    public static String getPinyin(String chs) {
        return PinyinParser.getInstance().getPinyin(chs);
    }

    public static String getPinyinFirstCharUpperCase(String chs)
    {
        return PinyinParser.getInstance().getPinyinFirstCharUpperCase(chs, false);
    }

    public static String getFirstChars(String chs)
    {
        return  PinyinParser.getInstance().getFirstChars(chs);
    }
}
