package com.hanbing.library.android.tool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by hanbing on 2016/9/9
 */
public abstract class ListenerManager<T> {

    byte[] mLock = new byte[1];

    List<T> mListeners;

    /**
     * Add listeners
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
    public void removeListener(T ... listeners) {
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

            //cached method
            Method method = null;

            for (T listener : mListeners) {
                method = callback(listener, null, args);
            }

        }
    }

    /**
     * Callback
     * @param t listener
     * @param method last hit method, invoke it if not null
     * @param args arguments
     * @return
     */
    private Method callback(T t, Method method, Object... args) {
        if (null == t)
            return null;

        if (null == args)
            args = new Object[0];

        Class<?> aClass = t.getClass();

        Method[] methods = aClass.getMethods();

        if (null == methods || 0 == methods.length)
            return null;


        if (null == method) {

            boolean hit = false;

            for (Method method1 : methods) {
                Class<?>[] parameterTypes = method1.getParameterTypes();

                //only support public method
                if (method1.getModifiers() != Modifier.PUBLIC)
                    continue;

                // first check param length, and then check param types
                if (parameterTypes.length == args.length) {

                    hit = true;
                    for (int i = 0, length = parameterTypes.length; i < length; i++) {
                        Class<?> parameterType = parameterTypes[i];
                        Object arg = args[i];

                        Class<?> aClass1 = arg.getClass();
                        if (!check(parameterType, aClass1)) {
                            // deferent type, check next method
                            hit = false;
                            break;
                        }
                    }

                    if (hit) {
                        //hit method1, invoke it and return
                        method = method1;
                        break;
                    }
                }

            }
        }

        if (null != method) {
            try {
                method.invoke(t, args);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        //return method if hit, so that next callback can invoke immediately.
        return method;
    }


    private boolean check(Class left, Class right) {
        return check(left, right, true);
    }

    private boolean check(Class left, Class right, boolean base) {
        boolean ret = left.equals(right);

        if (!ret && base) {
            ret = checkBase(left, right) || checkBase(right, left);
        }
        return ret;

    }


    private boolean checkBase(Class left, Class right) {
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
