package com.hanbing.library.android.view.recycler.animator.bean;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;

/**
 * Created by hanbing on 2016/3/14.
 */
public class ScaleValuePair extends AnimationValuePair{
    public ValuePair x = new ValuePair(1, 1);
    public ValuePair y = new ValuePair(1, 1);



    @Override
    public void before(View view) {
        if (null == view)
            return;

        if (null != x)
            ViewCompat.setScaleX(view, x.before);

        if (null != y)
            ViewCompat.setScaleY(view, y.before);
    }

    @Override
    public void after(View view) {
        if (null == view)
            return;

        if (null != x)
            ViewCompat.setScaleX(view, x.after);

        if (null != y)
            ViewCompat.setScaleY(view, y.after);
    }

    @Override
    public void animate(ViewPropertyAnimatorCompat animation) {

        if (null == animation)
            return;

        if (null != x)
            animation.scaleX(x.to);

        if (null != y)
            animation.scaleY(y.to);
    }
}
