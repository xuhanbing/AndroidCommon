package com.hanbing.mytest.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by hanbing on 2016/7/25.
 */
public class CallbackScrollView extends com.hanbing.library.android.view.scroll.CallbackScrollView {


    public CallbackScrollView(Context context) {
        super(context);
    }

    public CallbackScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CallbackScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CallbackScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
