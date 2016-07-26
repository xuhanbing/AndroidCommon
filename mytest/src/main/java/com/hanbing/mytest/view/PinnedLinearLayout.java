package com.hanbing.mytest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * Created by hanbing on 2016/7/25.
 */
public class PinnedLinearLayout extends com.hanbing.library.android.view.PinnedLinearLayout {

    public PinnedLinearLayout(Context context) {
        super(context);
    }

    public PinnedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PinnedLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
