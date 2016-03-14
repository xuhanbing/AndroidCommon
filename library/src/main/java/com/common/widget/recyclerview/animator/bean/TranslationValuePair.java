package com.common.widget.recyclerview.animator.bean;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.view.View;

/**
 * Created by hanbing on 2016/3/14.
 */
public class TranslationValuePair extends AnimationValuePair {
    public ValuePair x = new ValuePair();
    public ValuePair y = new ValuePair();
    public ValuePair z = new ValuePair();

    @Override
    public void before(View view) {
        if (null == view)
            return;

        if (null != x)
            ViewCompat.setTranslationX(view, x.before);

        if (null != y)
            ViewCompat.setTranslationY(view, y.before);

        if (null != z)
            ViewCompat.setTranslationZ(view, z.before);
    }

    @Override
    public void after(View view) {
        if (null == view)
            return;

        if (null != x)
            ViewCompat.setTranslationX(view, x.after);

        if (null != y)
            ViewCompat.setTranslationY(view, y.after);

        if (null != z)
            ViewCompat.setTranslationZ(view, z.after);
    }

    @Override
    public void animate(ViewPropertyAnimatorCompat animation) {
        if (null == animation)
            return;

        if (null != x)
            animation.translationX(x.to);

        if (null != y)
            animation.translationY(y.to);

        if (null != z)
            animation.translationZ(z.to);
    }
}
