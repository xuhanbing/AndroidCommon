package com.hanbing.library.android.view.recycler.animator;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hanbing on 2016/3/14.
 */
public class ScaleInTopItemAnimator extends ScaleInCenterItemAnimator {
    @Override
    protected void initAnimation(RecyclerView.ViewHolder holder) {


        View view = holder.itemView;

        ViewCompat.setPivotX(view, view.getWidth() / 2);
        ViewCompat.setPivotY(view , 0);

        super.initAnimation(holder);
    }
}
