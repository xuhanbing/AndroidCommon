package com.common.widget.recyclerview.animator;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hanbing on 2016/3/11.
 */
public class DefaultItemAnimator extends BaseItemAnimator {
    @Override
    protected void preAnimateRemove(RecyclerView.ViewHolder holder) {
        ViewCompat.setAlpha(holder.itemView, 1);
    }

    @Override
    protected void actAnimateRemove(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animation) {
        animation.alpha(0).start();
    }

    @Override
    protected void afterAnimateRemove(RecyclerView.ViewHolder holder) {
        ViewCompat.setAlpha(holder.itemView, 1);
    }

    @Override
    protected void preAnimateAdd(RecyclerView.ViewHolder holder) {
        ViewCompat.setAlpha(holder.itemView, 0);
    }

    @Override
    protected void actAnimateAdd(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animation) {
        animation.alpha(1).start();
    }

    @Override
    protected void afterAnimateAdd(RecyclerView.ViewHolder holder) {
        ViewCompat.setAlpha(holder.itemView, 1);
    }
}
