package com.common.widget.recyclerview.animator;

import android.support.v7.widget.RecyclerView;

import com.common.widget.recyclerview.animator.bean.AlphaValuePair;
import com.common.widget.recyclerview.animator.bean.ValuePair;

/**
 * Created by hanbing on 2016/3/14.
 */
public class FadeInItemAnimator extends BaseSimpleItemAnimator {

    @Override
    protected void initAnimation(RecyclerView.ViewHolder holder) {
        AlphaValuePair add = new AlphaValuePair();
        add.alpha = new ValuePair(0, 1);

        AlphaValuePair remove = new AlphaValuePair();
        remove.alpha = new ValuePair(1, 0, 1);

        alpha(add, remove);
    }
}
