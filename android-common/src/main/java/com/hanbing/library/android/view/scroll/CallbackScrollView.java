package com.hanbing.library.android.view.scroll;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.hanbing.library.android.util.ReflectUtils;

import java.util.ArrayList;
import java.util.List;

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
    List<OnScrollChangedListener> mOnScrollChangedListeners;
    OnFlingChangedListener mOnFlingChangedListener;
    List<OnFlingChangedListener> mOnFlingChangedListeners;

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
        /**
         * bug#meizu
         * java.lang.ClassCastException: com.meizu.widget.MzOverScroller cannot be cast to android.widget.OverScroller
         */
        try {
            mSuperScroller = ReflectUtils.getValue(this, "mScroller", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (null != mSuperScroller && !mSuperScroller.computeScrollOffset()) {
            if (mIsFling) {
                mIsFling = false;
                if (null != mOnFlingChangedListener) mOnFlingChangedListener.onFlingFinished();
                postOnFlingFinished();
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (null != mOnScrollChangedListener)
        mOnScrollChangedListener.onScrollChanged(l, t, oldl, oldt);

        postOnScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY);
        if (getChildCount() > 0 && null != mSuperScroller) {
            mIsFling = true;
            if (null != mOnFlingChangedListener) mOnFlingChangedListener.onFlingStarted();
            postOnFlingStarted();
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangeListener) {
        this.mOnScrollChangedListener = onScrollChangeListener;
    }

    public void addOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        if (null == mOnScrollChangedListeners)
            mOnScrollChangedListeners = new ArrayList<>();
        if (null != onScrollChangedListener)
            mOnScrollChangedListeners.add(onScrollChangedListener);
    }

    public void removeOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener){
        if (null == mOnScrollChangedListeners || null == onScrollChangedListener)
            return;

        mOnScrollChangedListeners.remove(onScrollChangedListener);
    }

    private void postOnScrollChanged(int l, int t, int oldl, int oldt) {
        if (null == mOnScrollChangedListeners || mOnScrollChangedListeners.size() == 0)
            return;

        for (OnScrollChangedListener listener :
                mOnScrollChangedListeners) {
            listener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public void setOnFlingChangedListener(OnFlingChangedListener onFlingChangedListener) {
        mOnFlingChangedListener = onFlingChangedListener;
    }

    public void addOnFlingChangedListener(OnFlingChangedListener onFlingChangedListener) {
        if (null == mOnFlingChangedListeners)
            mOnFlingChangedListeners = new ArrayList<>();
        if (null != onFlingChangedListener)
            mOnFlingChangedListeners.add(onFlingChangedListener);
    }

    public void removeOnFlingChangedListener(OnFlingChangedListener onFlingChangedListener){
        if (null == mOnFlingChangedListeners || null == onFlingChangedListener)
            return;

        mOnFlingChangedListeners.remove(onFlingChangedListener);
    }

    private void postOnFlingStarted() {
        if (null == mOnFlingChangedListeners || mOnFlingChangedListeners.size() == 0)
            return;

        for (OnFlingChangedListener listener :
                mOnFlingChangedListeners) {
            listener.onFlingStarted();
        }
    }

    private void postOnFlingFinished() {
        if (null == mOnFlingChangedListeners || mOnFlingChangedListeners.size() == 0)
            return;

        for (OnFlingChangedListener listener :
                mOnFlingChangedListeners) {
            listener.onFlingFinished();
        }
    }
}
