package com.hanbing.library.android.view.scroll;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by hanbing on 2016/7/25.
 */
public class CallbackScrollView extends ScrollView {

    public interface OnScrollChangedListener {
        public void onScrollChanged(int l, int t, int oldl, int oldt);
    }

    public CallbackScrollView(Context context) {
        super(context);
    }

    public CallbackScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CallbackScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CallbackScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (null != mOnScrollChangedListener)
        mOnScrollChangedListener.onScrollChanged(l, t, oldl, oldt);
    }

    OnScrollChangedListener mOnScrollChangedListener;
    public void setOnScrollChangedListener(OnScrollChangedListener onScrollChangeListener) {
        this.mOnScrollChangedListener = onScrollChangeListener;
    }
}
