package com.hanbing.mytest.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

/**
 * Created by hanbing on 2016/7/25.
 */
public class PinnedScrollView extends ScrollView {
    public PinnedScrollView(Context context) {
        super(context);
    }

    public PinnedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PinnedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PinnedScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mOnScrollChangeListener.onScrollChanged();
    }

    ViewTreeObserver.OnScrollChangedListener mOnScrollChangeListener;
    public void addOnScrollChangedListener(ViewTreeObserver.OnScrollChangedListener onScrollChangeListener) {
        this.mOnScrollChangeListener = onScrollChangeListener;
    }
}
