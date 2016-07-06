package com.common.view.recycler.animator;

import android.support.v7.widget.RecyclerView;

import com.common.view.recycler.animator.bean.RotationValuePair;
import com.common.view.recycler.animator.bean.ValuePair;

/**
 * Created by hanbing on 2016/3/14.
 */
public class FlipInBottomItemAnimator extends BaseSimpleItemAnimator {
    @Override
    protected void initAnimation(RecyclerView.ViewHolder holder) {

        RotationValuePair add = new RotationValuePair();
        add.x = new ValuePair(90, 0, 0);

        RotationValuePair remove = new RotationValuePair();
        remove.x = new ValuePair(add.y.to, add.y.from, 0);

        rotate(add, remove);
    }
}
