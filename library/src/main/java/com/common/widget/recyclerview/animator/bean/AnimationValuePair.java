package com.common.widget.recyclerview.animator.bean;

import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;
import android.view.animation.Animation;

/**
 * Created by hanbing on 2016/3/14.
 */
public abstract class AnimationValuePair {
    /**
     * before animation
     * @param view
     */
    public abstract void before(View view);

    /**
     * after animation
     * @param view
     */
    public abstract void after(View view);

    /**
     * animate
     * @param animation ViewPropertyAnimatorCompat
     */
    public abstract void animate(ViewPropertyAnimatorCompat animation);
}
