package com.hanbing.library.android.bind;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hanbing.library.android.bind.annotation.BindView;
import com.hanbing.library.android.bind.annotation.BindContentView;
import com.hanbing.library.android.bind.annotation.BindFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * bind views and fragments
 * Created by hanbing on 2016/7/7
 */
public class ObjectBinder {


    public static void bind(Activity activity) {

        BindContentView contentView = findContentView(activity.getClass());

        if (null != contentView) {
            int layoutId = contentView.value();

            if (layoutId > 0) {
                activity.setContentView(layoutId);
            }
        }


        bind(activity, activity.getClass(), new ObjectFinder(activity));
    }

    public static View bind(Object object,  LayoutInflater inflater, ViewGroup container) {

        BindContentView contentView = findContentView(object.getClass());

        View view = null;
        if (null != contentView) {
            int layoutId = contentView.value();

            if (layoutId > 0) {
                view = inflater.inflate(layoutId, container, false);
            }
        }

        if (null != view) {
            bind(object, object.getClass(), new ObjectFinder(object, view));
        }


        return view;
    }

    public static void bind(Object object, View view) {
        bind(object, object.getClass(), new ObjectFinder(object, view));
    }

    public static void bind(Object object, Class clazz,  ObjectFinder objectFinder) {

        if (!checkClass(clazz))
            return;

        bind(object, clazz.getSuperclass(), objectFinder);

        Field[] fields = clazz.getDeclaredFields();

        if (null != fields && fields.length > 0) {

            for (Field field : fields) {

                if (Modifier.isStatic(field.getModifiers())
                        || Modifier.isFinal(field.getModifiers())
                        || field.getType().isArray()
                        || field.getType().isPrimitive())
                    continue;



                BindFragment bindFragment = field.getAnnotation(BindFragment.class);

                if (null != bindFragment) {
                    int id = bindFragment.value();
                    String tag = bindFragment.tag();

                    android.support.v4.app.Fragment f = objectFinder.findFragment(id, tag);

                    if (null != f) {
                        try {
                            boolean accessible = field.isAccessible();
                            field.setAccessible(true);
                            field.set(object, f);
                            field.setAccessible(accessible);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                }

                BindView bindView = field.getAnnotation((BindView.class));

                if (null != bindView) {
                    int id = bindView.value();
                    String tag = bindView.tag();

                    View v = objectFinder.findView(id, tag);
                    if (null != v) {
                        try {
                            boolean accessible = field.isAccessible();
                            field.setAccessible(true);
                            field.set(object, v);
                            field.setAccessible(accessible);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

        } else {

        }



    }


    private static BindContentView findContentView(Class clazz) {

        if (!checkClass(clazz))
            return null;

        BindContentView annotation = (BindContentView) clazz.getAnnotation(BindContentView.class);

        if (null == annotation) {
            return findContentView(clazz.getSuperclass());
        }
        return annotation;
    }



    private static boolean checkClass(Class clazz) {
        if (null == clazz
                || Activity.class == clazz
                || FragmentActivity.class == clazz
                || Fragment.class == clazz
                || android.support.v4.app.Fragment.class == clazz)
            return false;

        return true;

    }


}
