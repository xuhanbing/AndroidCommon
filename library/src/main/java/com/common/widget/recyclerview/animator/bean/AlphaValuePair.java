package com.common.widget.recyclerview.animator.bean;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;

/**
 * Created by hanbing on 2016/3/14.
 */
public class AlphaValuePair extends AnimationValuePair{
    public ValuePair alpha = new ValuePair(1, 1);

    @Override
    public void before(View view)
    {
        if (null == view || null == alpha)
            return;

        ViewCompat.setAlpha(view, alpha.before);
    }

    @Override
    public void after(View view)
    {
        if (null == view || null == alpha)
            return;

        ViewCompat.setAlpha(view, alpha.after);
    }

    @Override
    public void animate(ViewPropertyAnimatorCompat animation) {

        if (null == animation)
            return;

        if (null != alpha)
            animation.alpha(alpha.to);
    }
}
