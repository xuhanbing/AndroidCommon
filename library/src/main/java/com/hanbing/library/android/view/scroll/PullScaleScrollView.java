/**
 * www.douyang.com
 */
package com.hanbing.library.android.view.scroll;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


/**
 * @author hanbing
 * @Date 2015-7-8
 */
public class PullScaleScrollView extends StrengthScrollView implements StrengthScrollView.OnPullListener {


    /**
     * View的marginTop随着下拉的距离变化
     */
    View mScaleView;

    int mMaxMarginTop = 0;

    OnPullListener mOnPullListener;

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public PullScaleScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public PullScaleScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     */
    public PullScaleScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     *
     */
    protected void init() {
        // TODO Auto-generated method stub
        super.init();

        mCanMoveWhenPull = false;
        mMaxMarginTop = mMaxPullY;

        setOnPullListener(null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void fixPullY() {
        //do nothing
    }

    @Override
    public void setOnPullListener(final OnPullListener lsner) {
        // TODO Auto-generated method stub
        mOnPullListener = lsner;
        super.setOnPullListener(this);
    }

    /**
     *
     */
    public void setScaleView(View scaleView) {
        this.mScaleView = scaleView;

        post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                animate(0, 1);
            }
        });

    }


    /**
     * 是否是第一次动画（第一次动画在ScrollView的layout后）
     */
    boolean mIsFirstAnimate = true;
    /**
     * 内部变化的view的原始高度
     */
    int mInnerViewHeight = 0;


    /**
     * 移动
     *
     * @param y   相对于原始位置移动的距离
     * @param max
     */
    protected void animate(float y, float max) {


        if (null != mScaleView) {
            {
                if (mIsFirstAnimate) {
                    mInnerViewHeight = mScaleView.getMeasuredHeight();
                    mIsFirstAnimate = false;
                }


                float scale = y / max;
                MarginLayoutParams params = getDefaultLayoutParams(mScaleView);

                params.height = (int) (mInnerViewHeight + max * scale);
                mScaleView.setLayoutParams(params);

            }
        }
    }

    private MarginLayoutParams getDefaultLayoutParams(View view) {
        if (null == view)
            return null;

        MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
        ViewGroup parent = (ViewGroup) view.getParent();
        if (null == params) {

            params = new MarginLayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            if (null != parent) {
                if (parent instanceof LinearLayout) {
                    params = new LinearLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
                } else if (parent instanceof RelativeLayout) {
                    params = new RelativeLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
                } else if (parent instanceof FrameLayout) {
                    params = new FrameLayout.LayoutParams(
                            LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
                } else {

                }
            }
        }

        return params;
    }


    @Override
    public void onPull(float dy, float y, float max) {
        /**
         * 向上移动时不回调
         */
        if (y < 0)
            return;

        animate(y, max);

        if (null != mOnPullListener) {
            mOnPullListener.onPull(dy, y, max);
        }
    }

    @Override
    public void onMoveBack(float dy, float y, float max) {
        if (y < 0)
            return;

        animate(y, max);

        if (null != mOnPullListener) {
            mOnPullListener.onMoveBack(dy, y, max);
        }
    }
}
