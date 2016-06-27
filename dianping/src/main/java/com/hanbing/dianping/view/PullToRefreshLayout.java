package com.hanbing.dianping.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.common.util.ViewUtils;
import com.hanbing.dianping.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by hanbing on 2016/6/8.
 */
public class PullToRefreshLayout extends PtrFrameLayout {


    class HeaderView extends LinearLayout implements PtrUIHandler {


        ImageView mImageView;
        Animation mLoadingAnimation;

        boolean mHoldAnimation = false;

        int[] mDrawableIds = {
                R.drawable.dropdown_anim_00, R.drawable.dropdown_anim_01, R.drawable.dropdown_anim_02,
                R.drawable.dropdown_anim_03, R.drawable.dropdown_anim_04, R.drawable.dropdown_anim_05,
                R.drawable.dropdown_anim_06, R.drawable.dropdown_anim_07, R.drawable.dropdown_anim_08,
                R.drawable.dropdown_anim_09, R.drawable.dropdown_anim_10};

        AnimationDrawable mAnimationDrawable;

        public HeaderView(Context context) {
            super(context);
            init();
        }

        public HeaderView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);

            init();
        }

        void init() {

            inflate(getContext(), R.layout.layout_ptr_header, this);
            mImageView = (ImageView) findViewById(R.id.image);
        }

        @Override
        public void onUIReset(PtrFrameLayout frame) {
            mHoldAnimation = false;
            if (null != mAnimationDrawable)
            mAnimationDrawable.stop();
            mImageView.setImageResource(mDrawableIds[0]);
            ViewUtils.setScale(mImageView, 1, 1);
        }

        @Override
        public void onUIRefreshPrepare(PtrFrameLayout frame) {
        }

        @Override
        public void onUIRefreshBegin(PtrFrameLayout frame) {
            mHoldAnimation = true;
            ViewUtils.setScale(mImageView, 1, 1);
            mImageView.setImageResource(R.drawable.ptr_header);
            mAnimationDrawable = (AnimationDrawable) mImageView.getDrawable();
            mAnimationDrawable.start();
        }

        @Override
        public void onUIRefreshComplete(PtrFrameLayout frame) {

        }

        @Override
        public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

            if (mHoldAnimation)
                return;

            float percent = ptrIndicator.getCurrentPercent();


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
    }


    public PullToRefreshLayout(Context context) {
        super(context);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {

        disableWhenHorizontalMove(true);

        View header = new HeaderView(getContext());

        if (null != header) {
            this.setHeaderView(header);
            if (header instanceof PtrUIHandler) {
                this.addPtrUIHandler((PtrUIHandler) header);
            }
        }


    }



    VelocityTracker mVelocityTracker ;
    boolean mIsScrollHorizontal = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {

        if (null == mVelocityTracker)
        {
            mVelocityTracker = VelocityTracker.obtain();
        }

        mVelocityTracker.addMovement(e);
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsScrollHorizontal = false;
                break;
            case MotionEvent.ACTION_MOVE:

                mVelocityTracker.computeCurrentVelocity(1000);
                if (Math.abs(mVelocityTracker.getXVelocity()) > Math.abs(mVelocityTracker.getYVelocity())) {
                    mIsScrollHorizontal = true;
                }
                break;
                case MotionEvent.ACTION_UP:
                    mVelocityTracker.clear();
                    break;
        }

        if (isRefreshing() && mIsScrollHorizontal) {
            return dispatchTouchEventSupper(e);
        }

        return super.dispatchTouchEvent(e);
    }


}
