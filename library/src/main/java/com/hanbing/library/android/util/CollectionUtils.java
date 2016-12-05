package com.hanbing.library.android.util;

import android.text.TextUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hanbing on 2016/9/30.
 */

public class CollectionUtils {


    public interface ToStringFactory<E> {
        public String toString(E e);
    }

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


    /**
     * 将集合转换成字符串序列
     * @param collection
     * @param toStringFactory
     * @param <E>
     * @return
     */
    public static <E> String toString(Collection<E> collection, ToStringFactory<E> toStringFactory) {
        return toString(collection, ",", toStringFactory);
    }

    /**
     * 将集合转换成字符串序列
     * @param collection
     * @param spilt
     * @param toStringFactory
     * @param <E>
     * @return
     */
    public static <E> String toString(Collection<E> collection, String spilt, ToStringFactory<E> toStringFactory) {



        if (null == toStringFactory) {
            toStringFactory = new ToStringFactory<E>() {
                @Override
                public String toString(E e) {
                    return null != e ? e.toString() : "";
                }
            };
        }

        if (null == spilt)
            spilt = ",";

        if (null == collection || collection.isEmpty())
            return null;

        Iterator<E> iterator = collection.iterator();

        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            String s = toStringFactory.toString(iterator.next());
            //如果为空不添加
            if (!TextUtils.isEmpty(s)) {
                stringBuilder.append(s);
                stringBuilder.append(spilt);
            }
        }

        String string = stringBuilder.toString();
        //去掉最后的","
        if (string.endsWith(spilt)) {
            string = string.substring(0, string.length() - spilt.length());
        }

        return string;
    }


    public static boolean equals(Object left, Object right) {
        if (null == left || null == right) {
            return  left == right;
        } else{
            if (left.getClass() == right.getClass()) {
                return left.equals(right);
            } else {
                return false;
            }
        }
    }

    public static int hashCode(Object o) {
        return null == o ? 0 : o.hashCode();
    }
}
