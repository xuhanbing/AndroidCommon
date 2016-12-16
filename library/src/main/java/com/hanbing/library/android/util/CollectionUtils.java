package com.hanbing.library.android.util;

import android.text.TextUtils;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by hanbing on 2016/9/30.
 */

public class CollectionUtils {


    public static final String SPILT = ",";

    public interface ToStringFactory<E> {
        public String toString(E e);
    }


    /**
     * add and filter same data
     *
     * @param list
     * @param t
     * @param <T>
     */
    public static <T> boolean add(List<T> list, T t) {

        if (null == list || null == t)
            return false;

        if (!list.contains(t)) {
            return list.add(t);
        }

        return false;
    }

    /**
     * add and filter same data
     *
     * @param list
     * @param subList
     * @param <T>
     */
    public static <T> boolean addAll(List<T> list, List<? extends T> subList) {

        if (null == list || null == subList)
            return false;

        boolean modified = false;
        for (T t : subList) {
            if (!list.contains(t)) {
                modified |= list.add(t);
            }
        }

        return modified;
    }


    /**
     * remove
     *
     * @param list
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean remove(List<T> list, T t) {
        if (null == list || null == t)
            return false;

        return list.remove(t);
    }

    /**
     * remove all
     *
     * @param list
     * @param otherList
     * @param <T>
     * @return
     */
    public static <T> boolean removeAll(List<T> list, List<T> otherList) {
        if (null == list || null == otherList)
            return false;
        return list.removeAll(otherList);
    }

    /**
     * 将集合转换成字符串序列
     *
     * @param collection
     * @param toStringFactory
     * @param <E>
     * @return
     */
    public static <E> String toString(Collection<E> collection, ToStringFactory<E> toStringFactory) {
        return toString(collection, SPILT, toStringFactory);
    }

    /**
     * 将集合转换成字符串序列
     *
     * @param collection
     * @param spilt
     * @param toStringFactory
     * @param <E>
     * @return
     */
    public static <E> String toString(Collection<E> collection, String spilt, ToStringFactory<E> toStringFactory) {

        if (null == collection || collection.isEmpty())
            return null;

        if (null == spilt)
            spilt = SPILT;

        if (null == toStringFactory) {
            toStringFactory = new ToStringFactory<E>() {
                @Override
                public String toString(E e) {
                    return null != e ? e.toString() : "";
                }
            };
        }

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


    /**
     * 比较两个值是否相等
     *
     * @param left
     * @param right
     * @return
     */
    public static boolean equals(Object left, Object right) {
        return (null == left) ? (null == right) : left.equals(right);
    }

    /**
     * 获取hashcode
     *
     * @param o
     * @return
     */
    public static int hashCode(Object o) {
        return null == o ? 0 : o.hashCode();
    }

    /**
     * 值是否在序列中
     *
     * @param value  值
     * @param values 序列
     * @return
     */
    public static boolean isValueIn(Object value, Object... values) {
        if (null == values)
            return false;

        for (Object o : values) {
            if (equals(value, o))
                return true;
        }

        return false;
    }


    /**
     * 是否为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * 包含
     *
     * @param collection
     * @param o
     * @return
     */
    public static boolean contains(Collection collection, Object o) {
        if (null == collection)
            return false;
        return collection.contains(o);
    }

    /**
     * @param map
     * @return
     */
    public static boolean isEmpty(Map map) {
        return null == map || map.isEmpty();
    }

    public static boolean containsKey(Map map, Object key) {
        if (null == map)
            return false;

        return map.containsKey(key);
    }

    public static boolean containsValue(Map map, Object value) {
        if (null == map)
            return false;

        return map.containsValue(value);
    }

    /**
     * 返回集合已经存在的值
     * @param collection
     * @param e
     * @param <E>
     * @return
     */
    public static <E> E getExist(Collection<? extends E> collection, E e) {
        if (null == collection || null == e)
            return null;

        if (!collection.contains(e))
            return null;

        Iterator<? extends E> iterator = collection.iterator();

        while (iterator.hasNext()) {
            E next = iterator.next();

            if (equals(next, e))
                return next;
        }

        return null;
    }



}
