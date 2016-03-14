package com.common.widget.recyclerview.animator;

import android.support.v7.widget.RecyclerView;

import com.common.widget.recyclerview.animator.bean.RotationValuePair;
import com.common.widget.recyclerview.animator.bean.ValuePair;

/**
 * Created by hanbing on 2016/3/14.
 */
public class FlipInRightItemAnimator extends BaseSimpleItemAnimator {
    @Override
    protected void initAnimation(RecyclerView.ViewHolder holder) {

        RotationValuePair add = new RotationValuePair();
        add.y = new ValuePair(-90, 0, 0);

        RotationValuePair remove = new RotationValuePair();
        remove.y = new ValuePair(add.y.to, add.y.from, 0);

        rotate(add, remove);
    }
}
