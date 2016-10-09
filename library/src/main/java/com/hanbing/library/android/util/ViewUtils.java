/**
 *
 */
package com.hanbing.library.android.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.text.method.TextKeyListener;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TabHost;
import android.widget.TextView;

import com.hanbing.library.android.view.recycler.OnItemClickListener;
import com.hanbing.library.android.view.recycler.OnItemLongClickListener;
import com.hanbing.library.android.tool.FragmentTabHostAndViewPagerInitHelper;
import com.hanbing.library.android.view.recycler.SimpleOnItemTouchListener;
import com.hanbing.library.android.view.tab.TabWidget;

/**
 * @author hanbing
 * @date 2015-12-23
 */
public class ViewUtils {

    /**
     *
     */
    public ViewUtils() {
        // TODO Auto-generated constructor stub
    }

    /**
     * inflate view
     *
     * @param context
     * @param resource
     * @return
     */
    public static View inflate(Context context, int resource) {
        if (null == context)
            return null;

        return inflate(LayoutInflater.from(context), resource, null, false);
    }

    /**
     * inflate view
     *
     * @param context
     * @param resource
     * @param root
     * @param attachToRoot
     * @return
     */
    public static View inflate(Context context, int resource, ViewGroup root, boolean attachToRoot) {
        if (null == context)
            return null;

        return inflate(LayoutInflater.from(context), resource, root, attachToRoot);
    }

    /**
     * @param inflater
     * @param resource
     * @param root
     * @param attachToRoot
     * @return
     */
    public static View inflate(LayoutInflater inflater, int resource, ViewGroup root, boolean attachToRoot) {
        return inflater.inflate(resource, root, attachToRoot);
    }

    /**
     * find view by id
     *
     * @param activity
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T findViewById(Activity activity, int id) {
        if (null == activity)
            return null;

        return (T) activity.findViewById(id);
    }

    /**
     * find view by id
     *
     * @param parent
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T findViewById(View parent, int id) {
        if (null == parent)
            return null;

        return (T) parent.findViewById(id);
    }

    /**
     * find view with tag
     *
     * @param parent
     * @param tag
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T findViewWidthTag(View parent, Object tag) {
        if (null == parent)
            return null;

        return (T) parent.findViewWithTag(tag);
    }

    /**
     * bind view with onCickListener
     *
     * @param activity
     * @param id
     * @param listener
     * @return view
     */
    public static <T extends View> T findViewAndBindOnClick(Activity activity, int id, View.OnClickListener listener) {
        T view = findViewById(activity, id);

        if (null != view)
            view.setOnClickListener(listener);

        return view;
    }

    /**
     * bind view with onCickListener
     *
     * @param parent
     * @param id
     * @param listener
     * @return view
     */
    public static <T extends View> T findViewAndBindOnClick(View parent, int id, View.OnClickListener listener) {
        T view = findViewById(parent, id);

        if (null != view)
            view.setOnClickListener(listener);

        return view;
    }

    public static  void setScale(View view, float scale) {
        setScale(view, scale, scale);
    }

    public static void setScale(View view, float scaleX, float scaleY) {
        if (null == view)
            return;

//        if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
//            view.setScaleX(scaleX);
//            view.setScaleY(scaleY);
//        }

        ViewCompat.setScaleX(view, scaleX);
        ViewCompat.setScaleY(view, scaleY);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void setAlpha(View view, float alpha) {
        if (null == view)
            return;

//        if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
//            view.setAlpha(alpha);
//        }
        ViewCompat.setAlpha(view, alpha);
    }

    public static void clear(View view)
    {
        if (null == view)
            return;

        ViewCompat.setAlpha(view, 1);
        ViewCompat.setScaleY(view, 1);
        ViewCompat.setScaleX(view, 1);
        ViewCompat.setTranslationY(view, 0);
        ViewCompat.setTranslationX(view, 0);
        ViewCompat.setRotation(view, 0);
        ViewCompat.setRotationY(view, 0);
        ViewCompat.setRotationX(view, 0);
        // @TODO https://code.google.com/p/android/issues/detail?id=80863
//        ViewCompat.setPivotY(v, v.getMeasuredHeight() / 2);
        view.setPivotY(view.getMeasuredHeight() / 2);
        ViewCompat.setPivotX(view, view.getMeasuredWidth() / 2);
        AnimatorCompatHelper.clearInterpolator(view);
    }


    public static View removeFromParent(View view)
    {
        if (null == view)
            return null;

        ViewParent parent = view.getParent();

        if (parent instanceof ViewGroup)
        {
            ((ViewGroup) parent).removeView(view);
        }

        return view;
    }

    /**
     *
     *
     * @param viewPager
     * @param tabWidget
     * @param tabViewFactory
     * @param onPageChangeListener
     */
    public static void bindTabWidgetAndViewPager(ViewPager viewPager, TabWidget tabWidget, TabWidget.TabViewFactory tabViewFactory
            , ViewPager.OnPageChangeListener onPageChangeListener) {

        if (null == viewPager || null == tabWidget)
            return;

        tabWidget.setTabViewFactory(tabViewFactory);
        tabWidget.setViewPager(viewPager);
        tabWidget.setOnPageChangeListener(onPageChangeListener);
    }

    /**
     * @param context
     * @param viewPager
     * @param fragmentTabHost
     * @param fragmentManager
     * @param viewFactory
     * @param tags
     * @param onPageChangeListener
     * @param onTabChangeListener
     */
    public static void bindFragmentTabHostAndViewPager(
            Context context,
            ViewPager viewPager,
            FragmentTabHost fragmentTabHost,
            FragmentManager fragmentManager,
            FragmentTabHostAndViewPagerInitHelper.ViewFactory viewFactory,
            String[] tags,
            ViewPager.OnPageChangeListener onPageChangeListener,
            TabHost.OnTabChangeListener onTabChangeListener) {

        FragmentTabHostAndViewPagerInitHelper helper = new FragmentTabHostAndViewPagerInitHelper(context, viewPager, fragmentTabHost, fragmentManager, viewFactory, tags, onPageChangeListener, onTabChangeListener);

        helper.init();
    }


    public static void bindOnItemClickListener(RecyclerView recyclerView, OnItemClickListener onItemClickListener) {
        bindOnItemClickListener(recyclerView, onItemClickListener, null);
    }

    public static void bindOnItemLongClickListener(RecyclerView recyclerView, OnItemLongClickListener onItemLongClickListener) {
        bindOnItemClickListener(recyclerView, null, onItemLongClickListener);
    }

    /**
     * bind recycleView with onItemClick and onItemLongClick
     * @param recyclerView
     * @param onItemClickListener
     */
    public static void  bindOnItemClickListener(final RecyclerView recyclerView, final OnItemClickListener onItemClickListener, final OnItemLongClickListener onItemLongClickListener)
    {
        if (null == recyclerView)
            return;

        if (null == onItemClickListener && null == onItemLongClickListener)
            return;

        //多次添加后面的会接收不到事件
        recyclerView.addOnItemTouchListener(new SimpleOnItemTouchListener(recyclerView, onItemClickListener, onItemLongClickListener));

    }

    /**
     * 缩放透明度
     *
     * @param color
     * @param scale
     * @return
     */
    public static int scaleColorAlpha(int color, float scale) {

        /**
         * 取颜色值的alpha
         */
        int alpha = (color >> 24) & 0xff;
        /**
         * 取颜色值的rgb值
         */
        int rgb = color & 0x00ffffff;

        if (scale < 0)
            scale = 0;
        else if (scale > 1)
            scale = 1;

        alpha *= scale;
        color = ((alpha & 0xff) << 24) | rgb;

        return color;

    }


    /**
     * measure view
     * @param parent
     * @param child
     * @param parentWidthMeasureSpec
     * @param parentHeightMeasureSpec
     */
    public static void measureChild(ViewGroup parent, View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        if (null == parent || null == child)
            return;
        ReflectUtils.invokeVoidMethod(parent, "measureChild", new Class[]{View.class, int.class, int.class}, new Object[]{child, parentWidthMeasureSpec, parentHeightMeasureSpec});
    }

    /**
     * draw view
     * @param parent
     * @param child
     * @param canvas
     */
    public static void drawChild(ViewGroup parent, View child, Canvas canvas) {
        if (null == parent || null == child || null == canvas)
            return;
        ReflectUtils.invokeVoidMethod(parent, "drawChild", new Class[]{Canvas.class, View.class, long.class}, canvas, child, parent.getDrawingTime());

    }


    public static final String NUMBER_LETTERS = "0123456789";
    public static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 只能输入数字
     * @param textView
     */
    public static void setInputTypeNumber(TextView textView) {
        setInputType(textView, NUMBER_LETTERS);

    }

    /**
     * 只能输入字母
     * @param textView
     */
    public static void setInputTypeAlphabet(TextView textView) {
        setInputType(textView, UPPERCASE_LETTERS + LOWERCASE_LETTERS);

    }

    /**
     * 只能输入大写字母
     * @param textView
     */
    public static void setInputTypeAlpahbetUppercase(TextView textView) {
        setInputType(textView, UPPERCASE_LETTERS);

    }

    /**
     * 只能输入小写字母
     * @param textView
     */
    public static void setInputTypeAlpahbetLowercase(TextView textView) {
        setInputType(textView, LOWERCASE_LETTERS);

    }

    /**
     *  数字字母
     * @param textView
     */
    public static void setInputTypeNumberAndAlpahbet(TextView textView) {
        setInputType(textView, NUMBER_LETTERS + UPPERCASE_LETTERS + LOWERCASE_LETTERS);
    }

    /**
     * 接受所有字符
     * @param textView
     */
    public static void setInputTypeAll(TextView textView) {
        setInputType(textView, null);
    }
    /**
     * 自定义接受的输入字符
     *
     * @param textView
     * @param letters 接受的字符，如果为空，则接受所有
     */
    public static void setInputType(TextView textView, final String letters) {
        if (null == textView)
            return;

        if (TextUtils.isEmpty(letters))
        {
            textView.setKeyListener(TextKeyListener.getInstance());
            return;
        }

        textView.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                return letters.toCharArray();
            }

            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_TEXT;
            }
        });
    }
}
