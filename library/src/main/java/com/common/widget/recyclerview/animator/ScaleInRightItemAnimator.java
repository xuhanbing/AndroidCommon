package com.common.widget.recyclerview.animator;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.widget.recyclerview.animator.bean.ScaleValuePair;
import com.common.widget.recyclerview.animator.bean.ValuePair;

/**
 * Created by hanbing on 2016/3/14.
 */
public class ScaleInRightItemAnimator extends ScaleInCenterItemAnimator {
    @Override
    protected void initAnimation(RecyclerView.ViewHolder holder) {


        View view = holder.itemView;

        ViewCompat.setPivotX(view, view.getWidth());
        ViewCompat.setPivotY(view , view.getHeight() / 2);

        super.initAnimation(holder);
    }
}
