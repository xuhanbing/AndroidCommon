package com.hanbing.library.android.util;

import java.util.List;

/**
 * Created by hanbing on 2016/9/30.
 */

public class CollectionUtils {


    /**
     * add and filter same data
     * @param list
     * @param newList
     * @param <T>
     */
    public static <T> void addAll(List<T> list, List<? extends T> newList) {

        if (null == list || null == newList)
            return;

        for (T t : newList) {
            if (!list.contains(t))
            list.add(t);
        }
    }
}
