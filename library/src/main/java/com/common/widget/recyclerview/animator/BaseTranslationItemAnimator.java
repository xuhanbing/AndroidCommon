package com.common.widget.recyclerview.animator;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hanbing on 2016/3/11.
 */
public class BaseTranslationItemAnimator extends BaseItemAnimator {


     long mAddDuration;
     long mRemoveDuration;

     int mAddFromX;
     int mAddFromY;
     int mAddToX;
     int mAddToY;

    int mRemoveFromX;
    int mRemoveFromY;
    int mRemoveToX;
    int mRemoveToY;

    boolean mValuesInited = false;

    public BaseTranslationItemAnimator(){
        mAddDuration = mRemoveDuration = 500;
    }

    @Override
    protected void preAnimateRemove(RecyclerView.ViewHolder holder) {
        initValues(holder);

        ViewCompat.setTranslationX(holder.itemView, mRemoveFromX);
        ViewCompat.setTranslationY(holder.itemView, mRemoveFromY);
    }

    @Override
    protected void actAnimateRemove(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animation) {
        initValues(holder);

        animation.translationX(mRemoveToX)
                .translationY(mRemoveToY)
                .setDuration(mRemoveDuration)
                .start();
    }

    @Override
    protected void afterAnimateRemove(RecyclerView.ViewHolder holder) {
        initValues(holder);

        ViewCompat.setTranslationX(holder.itemView, mAddToX);
        ViewCompat.setTranslationY(holder.itemView, mAddToY);
    }

    @Override
    protected void preAnimateAdd(RecyclerView.ViewHolder holder) {
        initValues(holder);

        ViewCompat.setTranslationX(holder.itemView, mAddFromX);
        ViewCompat.setTranslationY(holder.itemView, mAddFromY);
    }

    @Override
    protected void actAnimateAdd(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animation) {
        initValues(holder);

        animation.translationX(mAddToX)
                .translationY(mAddToY)
                .setDuration(mAddDuration)
                .start();
    }

    @Override
    protected void afterAnimateAdd(RecyclerView.ViewHolder holder) {
        initValues(holder);

        ViewCompat.setTranslationX(holder.itemView, mAddToX);
        ViewCompat.setTranslationY(holder.itemView, mAddToY);
    }

    protected void initValues(RecyclerView.ViewHolder holder)
    {
        if (mValuesInited)
            return;

        mAddDuration = mRemoveDuration = 500;

    }
}
