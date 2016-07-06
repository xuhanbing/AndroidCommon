package com.hanbing.library.android.view.recycler.animator;

import android.support.v7.widget.RecyclerView;

import com.hanbing.library.android.view.recycler.animator.bean.ScaleValuePair;
import com.hanbing.library.android.view.recycler.animator.bean.ValuePair;

/**
 * Created by hanbing on 2016/3/14.
 */
public class ScaleInCenterItemAnimator extends BaseSimpleItemAnimator {
    @Override
    protected void initAnimation(RecyclerView.ViewHolder holder) {


        ScaleValuePair add = new ScaleValuePair();
        ScaleValuePair remove = new ScaleValuePair();

        add.x = add.y = new ValuePair(0f, 1, 1);
        remove.x = remove.y = new ValuePair(1, 0f, 1);

        scale(add, remove);
    }
}
