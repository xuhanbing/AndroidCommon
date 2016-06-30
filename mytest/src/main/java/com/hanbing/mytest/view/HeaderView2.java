package com.hanbing.mytest.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.common.util.LogUtils;
import com.common.util.ViewUtils;
import com.common.widget.ptr.IPtrHandler;
import com.common.widget.ptr.PtrLayout;
import com.hanbing.mytest.R;

/**
 * Created by hanbing
 */
public  class HeaderView2 extends android.widget.LinearLayout implements IPtrHandler {


    ImageView mImageView;
    Animation mLoadingAnimation;

    boolean mHoldAnimation = false;

    int[] mDrawableIds = {
            R.drawable.dropdown_anim_00, R.drawable.dropdown_anim_01, R.drawable.dropdown_anim_02,
            R.drawable.dropdown_anim_03, R.drawable.dropdown_anim_04, R.drawable.dropdown_anim_05,
            R.drawable.dropdown_anim_06, R.drawable.dropdown_anim_07, R.drawable.dropdown_anim_08,
            R.drawable.dropdown_anim_09, R.drawable.dropdown_anim_10};

    AnimationDrawable mAnimationDrawable;

    public HeaderView2(Context context) {
        super(context);
        init();
    }

    public HeaderView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeaderView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    void init() {

        inflate(getContext(), R.layout.layout_ptr_header, this);
        mImageView = (ImageView) findViewById(R.id.image);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                LogUtils.e("HeaderView2 onTouch");
                return false;
            }
        });
    }

    @Override
    public void onPull(PtrLayout ptrLayout, int delta, float percent) {

        if (mHoldAnimation)
            return;

        int index = 0;


        float ratio = 0.5f;

        if (percent < ratio) {
            percent = 0.5f;
        } else {
            index = Math.round((percent - ratio) / (1 - ratio) * mDrawableIds.length);
            index = Math.min(mDrawableIds.length - 1, Math.max(0, index));
        }

        float scale = Math.min(1, Math.max(percent, 0));

        ViewUtils.setScale(mImageView, scale, scale);

        mImageView.setImageResource(mDrawableIds[index]);
    }

    @Override
    public void onReset() {
        LogUtils.e("HeaderView2 onReset");
        mHoldAnimation = false;
        if (null != mAnimationDrawable)
            mAnimationDrawable.stop();
        mImageView.setImageResource(mDrawableIds[0]);
        ViewUtils.setScale(mImageView, 1, 1);
    }

    @Override
    public void onPullToRefresh() {

    }

    @Override
    public void onReleaseToRefresh() {

    }

    @Override
    public void onRefreshPrepared() {

    }

    @Override
    public void onRefreshStarted() {
        LogUtils.e("HeaderView2 onRefreshStarted");
        mHoldAnimation = true;
        ViewUtils.setScale(mImageView, 1, 1);
        mImageView.setImageResource(R.drawable.ptr_header);
        mAnimationDrawable = (AnimationDrawable) mImageView.getDrawable();
        mAnimationDrawable.start();
    }

    @Override
    public void onRefreshCompleted() {
        LogUtils.e("HeaderView2 onRefreshCompleted");

    }
}
