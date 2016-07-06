package com.hanbing.library.android.util;

import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by hanbing
 */
public class ReflectUtils {

    public static Field getField(Class cls, String fieldName) {

        if (null == cls)
            return null;

        try {
            return  cls.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            // find from super class
            return getField(cls.getSuperclass(), fieldName);
        }

    }

    public static <T> T getValue(Object object, String fieldName, T defaultValue) {

        if (null == object
                || TextUtils.isEmpty(fieldName))
            return defaultValue;


        try {
            Field field = getField(object.getClass(), fieldName);

            if (null != field) {
                boolean accessible = field.isAccessible();

                field.setAccessible(true);

                T value = (T) field.get(object);

                field.setAccessible(accessible);

                return value;
            }

        }  catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    public static void setValue(Object object, String fieldName,  Object newValue) {

        if (null == object
                || TextUtils.isEmpty(fieldName))
            return ;

        try {
            Field field = getField(object.getClass(), fieldName);

           if (null != field) {
               boolean accessible = field.isAccessible();

               field.setAccessible(true);

               field.set(object, newValue);

               field.setAccessible(accessible);
           }

        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * Check if it is static method
     * @param method
     * @return
     */
    public static boolean isStaticMethod(Method method) {

        if (null == method)
            return false;

        return Modifier.isStatic(method.getModifiers());
    }

    public static Method getMethod(Class clazz, String methodName, Class... classes) {

        if (null == clazz)
            return null;

        try {
            return  clazz.getDeclaredMethod(methodName, classes);
        } catch (NoSuchMethodException e) {
            // find from super class
            return getMethod(clazz.getSuperclass(), methodName, classes);
        }

    }

    public static Method getMethod(Object object, String methodName, Class... classes) {

        if (null == object
                || TextUtils.isEmpty(methodName))
            return null;

        return getMethod(object.getClass(), methodName, classes);
    }

    public static <T> T invokeMethod(Object object, String methodName, Class[] classes,  Object ... args) {

        if (null == object || TextUtils.isEmpty(methodName))
            return null;

        Method method = getMethod(object, methodName, classes);

        return invokeMethod(object, method, args);
    }

    public static <T> T invokeMethod(Object object, Method method, Object ... args) {

        if (null == object && !isStaticMethod(method))
            return null;

        if (null != method)
        {
            try {
                boolean accessible = method.isAccessible();
                method.setAccessible(true);
                T value = (T) method.invoke(object, args);
                method.setAccessible(accessible);

                return value;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public static void invokeVoidMethod(Object object, String methodName, Class[] classes,  Object ...args) {
        if (null == object || TextUtils.isEmpty(methodName))
            return ;

        Method method = getMethod(object, methodName, classes);

        invokeVoidMethod(object, method, args);

    }

    public static void invokeVoidMethod(Object object, Method method, Object ...args) {
        if (null != method)
        {
            try {
                boolean accessible = method.isAccessible();
                method.setAccessible(true);
                method.invoke(object, args);
                method.setAccessible(accessible);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }
}
