package com.common.view.recycler.animator.bean;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;

/**
 * Created by hanbing on 2016/3/14.
 */
public class RotationValuePair extends AnimationValuePair{
    public ValuePair x = new ValuePair();
    public ValuePair y = new ValuePair();
    public ValuePair z = new ValuePair();

    @Override
    public void before(View view) {
        if (null == view)
            return;

        if (null != x)
            ViewCompat.setRotationX(view, x.before);

        if (null != y)
            ViewCompat.setRotationY(view, y.before);

        if (null != z)
            ViewCompat.setRotation(view, z.before);
    }

    @Override
    public void after(View view) {
        if (null == view)
            return;

        if (null != x)
            ViewCompat.setRotationX(view, x.after);

        if (null != y)
            ViewCompat.setRotationY(view, y.after);

        if (null != z)
            ViewCompat.setRotation(view, z.after);
    }

    @Override
    public void animate(ViewPropertyAnimatorCompat animation) {

        if (null == animation)
            return;

        if (null != x)
            animation.rotationX(x.to);

        if (null != y)
            animation.rotationY(y.to);

        if (null != z)
            animation.rotation(z.to);
    }
}
