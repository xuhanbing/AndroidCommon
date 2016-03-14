package com.common.widget.recyclerview.animator;

import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.common.util.ViewUtils;
import com.common.widget.recyclerview.animator.bean.AlphaValuePair;
import com.common.widget.recyclerview.animator.bean.RotationValuePair;
import com.common.widget.recyclerview.animator.bean.ScaleValuePair;
import com.common.widget.recyclerview.animator.bean.TranslationValuePair;

/**
 * Created by hanbing on 2016/3/11.
 */
public abstract class BaseSimpleItemAnimator extends BaseItemAnimator {

    ScaleValuePair scaleAdd;
    ScaleValuePair scaleRemove;

    TranslationValuePair translationAdd;
    TranslationValuePair translationRemove;

    RotationValuePair rotationAdd;
    RotationValuePair rotationRemove;

    AlphaValuePair alphaAdd;
    AlphaValuePair alphaRemove;

    long duration = 250;

    Interpolator interpolator = new LinearInterpolator();

    boolean isInitValues = false;

    public BaseSimpleItemAnimator(){

    }

    @Override
    protected void preAnimateRemove(RecyclerView.ViewHolder holder) {
//        ViewCompat.setAlpha(holder.itemView, 1);

        ViewUtils.clear(holder.itemView);

        initAnimation(holder);

        if (null != scaleRemove)
            scaleRemove.before(holder.itemView);

        if (null != translationRemove)
            translationRemove.before(holder.itemView);

        if (null != rotationRemove)
            rotationRemove.before(holder.itemView);

        if (null != alphaRemove)
            alphaRemove.before(holder.itemView);


    }

    @Override
    protected void actAnimateRemove(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animation) {
        initAnimation(holder);
//        animation.alpha(0).start();
        if (null != scaleRemove)
            scaleRemove.animate(animation);

        if (null != translationRemove)
            translationRemove.animate(animation);

        if (null != rotationRemove)
            rotationRemove.animate(animation);

        if (null != alphaRemove)
            alphaRemove.animate(animation);


        start(animation);
    }

    @Override
    protected void afterAnimateRemove(RecyclerView.ViewHolder holder) {
//        ViewCompat.setAlpha(holder.itemView, 1);
        initAnimation(holder);

        if (null != scaleRemove)
            scaleRemove.after(holder.itemView);

        if (null != translationRemove)
            translationRemove.after(holder.itemView);

        if (null != rotationRemove)
            rotationRemove.after(holder.itemView);

        if (null != alphaRemove)
            alphaRemove.after(holder.itemView);

    }

    @Override
    protected void preAnimateAdd(RecyclerView.ViewHolder holder) {
//        ViewCompat.setAlpha(holder.itemView, 0);
        ViewUtils.clear(holder.itemView);

        initAnimation(holder);

        if (null != scaleAdd)
            scaleAdd.before(holder.itemView);

        if (null != translationAdd)
            translationAdd.before(holder.itemView);

        if (null != rotationAdd)
            rotationAdd.before(holder.itemView);

        if (null != alphaAdd)
            alphaAdd.before(holder.itemView);
    }

    @Override
    protected void actAnimateAdd(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animation) {
//        animation.alpha(1).start();
        initAnimation(holder);

        if (null != scaleAdd)
            scaleAdd.animate(animation);

        if (null != translationAdd)
            translationAdd.animate(animation);

        if (null != rotationAdd)
            rotationAdd.animate(animation);

        if (null != alphaAdd)
            alphaAdd.animate(animation);

        start(animation);
    }


    @Override
    protected void afterAnimateAdd(RecyclerView.ViewHolder holder) {
//        ViewCompat.setAlpha(holder.itemView, 1);
        initAnimation(holder);

        if (null != scaleAdd)
            scaleAdd.after(holder.itemView);

        if (null != translationAdd)
            translationAdd.after(holder.itemView);

        if (null != rotationAdd)
            rotationAdd.after(holder.itemView);

        if (null != alphaAdd)
            alphaAdd.after(holder.itemView);


    }

    private void start(ViewPropertyAnimatorCompat animation)
    {
        animation.setInterpolator(interpolator).setDuration(duration).start();
    }


    public BaseSimpleItemAnimator scale(ScaleValuePair add, ScaleValuePair remove) {
        this.scaleAdd = add;
        this.scaleRemove = remove;

        return this;
    }

    public BaseSimpleItemAnimator translate(TranslationValuePair add, TranslationValuePair remove)
    {
        this.translationAdd = add;
        this.translationRemove = remove;

        return this;
    }

    public BaseSimpleItemAnimator rotate(RotationValuePair add, RotationValuePair remove)
    {
        this.rotationAdd = add;
        this.rotationRemove = remove;

        return this;
    }

    public BaseSimpleItemAnimator alpha(AlphaValuePair add, AlphaValuePair remove)
    {
        this.alphaAdd = add;
        this.alphaRemove = remove;

        return this;
    }

    public BaseSimpleItemAnimator setDuration(long duration)
    {
        this.duration = duration;

        return this;
    }

    public BaseSimpleItemAnimator setInterpolater(Interpolator interpolater)
    {
        this.interpolator = interpolater;

        return this;
    }

    private final void initValues(RecyclerView.ViewHolder holder)
    {
        if (isInitValues)
            return;

        isInitValues = true;

        initAnimation(holder);

    }

    protected abstract void initAnimation(RecyclerView.ViewHolder holder);
}
