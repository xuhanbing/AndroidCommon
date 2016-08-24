package com.hanbing.library.android.view.scroll;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ReflectUtils;

/**
 * Created by hanbing on 2016/7/25.
 */
public class CallbackScrollView extends ScrollView {

    public interface OnScrollChangedListener {
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public interface OnFlingChangedListener {
        public void onFlingStarted();
        public void onFlingFinished();
    }

    OverScroller mSuperScroller;
    OnScrollChangedListener mOnScrollChangedListener;
    OnFlingChangedListener mOnFlingChangedListener;

    boolean mIsFling = false;

    public CallbackScrollView(Context context) {
        super(context);
        init();
    }

    public CallbackScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CallbackScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CallbackScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        mSuperScroller = ReflectUtils.getValue(this, "mScroller", null);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (null != mSuperScroller && !mSuperScroller.computeScrollOffset()) {
            if (mIsFling) {
                mIsFling = false;
                if (null != mOnFlingChangedListener) mOnFlingChangedListener.onFlingFinished();
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (null != mOnScrollChangedListener)
        mOnScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        if (getChildCount() > 0 && null != mSuperScroller) {
            mIsFling = true;
            if (null != mOnFlingChangedListener) mOnFlingChangedListener.onFlingStarted();
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangeListener) {
        this.mOnScrollChangedListener = onScrollChangeListener;
    }

    public void setOnFlingChangedListener(OnFlingChangedListener onFlingChangedListener) {
        mOnFlingChangedListener = onFlingChangedListener;
    }
}
