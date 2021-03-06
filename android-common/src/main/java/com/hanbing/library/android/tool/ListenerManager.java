package com.hanbing.library.android.tool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hanbing on 2016/9/9
 */
public abstract class ListenerManager<T> {

    byte[] mLock = new byte[1];

    List<T> mListeners;

    ConcurrentHashMap<Class, Method> mMethodMap;

    /**
     * Add listeners
     *
     * @param listeners
     */
    public void addListener(T... listeners) {
        if (null == listeners || 0 == listeners.length)
            return;

        synchronized (mLock) {
            if (null == mListeners)
                mListeners = new ArrayList<>();
            mListeners.addAll(Arrays.asList(listeners));

        }
    }


    /**
     * Remote listeners.
     *
     * @param listeners
     */
    public void removeListener(T... listeners) {
        if (null == listeners || 0 == listeners.length)
            return;

        synchronized (mLock) {
            if (null != mListeners && !mListeners.isEmpty())
                mListeners.removeAll(Arrays.asList(listeners));
        }
    }

    /**
     * Return listeners.
     *
     * @return
     */
    public List<T> getListeners() {
        synchronized (mLock) {
            return mListeners;
        }
    }

    /**
     * Return listeners.
     *
     * @param copy if true, return a copy list, otherwise simple list.
     * @return
     */
    public List<T> getListeners(boolean copy) {
        synchronized (mLock) {

            if (copy) {

                List<T> list = new ArrayList<>();

                if (null != mListeners && !mListeners.isEmpty())
                    list.addAll(mListeners);

                return list;
            } else {
                return mListeners;
            }
        }
    }

    /**
     * Callback all listeners.
     *
     * @param args arguments
     */
    public void callback(Object... args) {
        synchronized (mLock) {

            if (null == mListeners || mListeners.isEmpty())
                return;
            for (T listener : mListeners) {
                //use strict mode first, then
                if (!callback(listener, true, args))
                    callback(listener, false, args);
            }

        }
    }



    /**
     * Callback
     *
     * @param t      listener
     * @param strict if false method param such as int.class equals Integer.class if there is no method match
     * @param args   arguments
     * @return
     */
    private boolean callback(T t, boolean strict, Object... args) {
        if (null == t)
            return false;

        if (null == args)
            args = new Object[0];

        Class<?> aClass = t.getClass();

        Method[] methods = aClass.getMethods();

        if (null == methods || 0 == methods.length)
            return false;


        Method hitMethod = null;
       {

            boolean hit = false;

            for (Method method : methods) {
                Class<?>[] parameterTypes = method.getParameterTypes();

                //only support public hitMethod
                if (method.getModifiers() != Modifier.PUBLIC)
                    continue;

                // first check param length, and then check param types
                if (parameterTypes.length == args.length) {

                    hit = true;
                    for (int i = 0, length = parameterTypes.length; i < length; i++) {
                        Class<?> parameterType = parameterTypes[i];
                        Object arg = args[i];

                        Class<?> aClass1 = arg.getClass();
                        if (!check(parameterType, aClass1, !strict)) {
                            // deferent type, check next hitMethod
                            hit = false;
                            break;
                        }
                    }

                    if (hit) {
                        //hit method, invoke it and return
                        hitMethod = method;

                        break;
                    }
                }

            }
        }

        if (null != hitMethod) {
            try {
                hitMethod.invoke(t, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        //return hitMethod if hit
        return null != hitMethod;
    }


    private boolean check(Class left, Class right) {
        return check(left, right, true);
    }

    private boolean check(Class left, Class right, boolean base) {
        boolean ret = left.equals(right);

        if (!ret && base) {
            ret = checkBase(left, right);
        }
        return ret;

    }


    private boolean checkBase(Class left, Class right) {
        return checkBaseInner(left, right) || checkBaseInner(right, left);
    }

    private boolean checkBaseInner(Class left, Class right) {
        return (left == boolean.class && right == Boolean.class)
                || (left == byte.class && right == Byte.class)
                || (left == char.class && right == Character.class)
                || (left == short.class && right == Short.class)
                || (left == int.class && right == Integer.class)
                || (left == long.class && right == Long.class)
                || (left == float.class && right == Float.class)
                || (left == double.class && right == Double.class);
    }

}
