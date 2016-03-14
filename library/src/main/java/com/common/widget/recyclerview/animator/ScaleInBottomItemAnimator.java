package com.common.widget.recyclerview.animator;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.widget.recyclerview.animator.bean.ScaleValuePair;
import com.common.widget.recyclerview.animator.bean.ValuePair;

/**
 * Created by hanbing on 2016/3/14.
 */
public class ScaleInBottomItemAnimator extends ScaleInCenterItemAnimator {
    @Override
    protected void initAnimation(RecyclerView.ViewHolder holder) {


        View view = holder.itemView;

        ViewCompat.setPivotX(view, view.getWidth() / 2);
        ViewCompat.setPivotY(view, view.getHeight());

        super.initAnimation(holder);
    }
}
