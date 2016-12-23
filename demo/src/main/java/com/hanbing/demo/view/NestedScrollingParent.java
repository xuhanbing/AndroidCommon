package com.hanbing.demo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.hanbing.demo.R;
import com.hanbing.library.android.util.LogUtils;

/**
 * Created by hanbing on 2016/11/22
 */

public class NestedScrollingParent extends RelativeLayout implements android.support.v4.view.NestedScrollingParent {


    NestedScrollingParentHelper mNestedScrollingParentHelper;


    View mFixedView;
    View mStickView;
    View mTopView;

    public NestedScrollingParent(Context context) {
        super(context);
        init();
    }

    public NestedScrollingParent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NestedScrollingParent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NestedScrollingParent(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    protected void onFinishInflate() {

        mFixedView = findViewById(R.id.fixedLayout);
        mStickView = findViewById(R.id.tabLayout);
        mTopView = findViewById(R.id.topLayout);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return true;
    }


    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return nestedScrollAxes == SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

        int[] pos1 = new int[2];
        int[] pos2 = new int[2];

        mFixedView.getLocationInWindow(pos1);
        mStickView.getLocationInWindow(pos2);


        int y1 = pos1[1] + mFixedView.getHeight();
        int y2 = pos2[1];

        LogUtils.e("onNestedPreScroll dx = " + dx + ", dy = " + dy + ", fix bottom y1 = " + y1 + ", stick top y2 = " + y2);


        if (dy > 0) {
            //向上滑动
            if (y2 > y1) {
                consumed[1] = Math.min(y2 - y1, dy);
                mTopView.scrollBy(0, consumed[1]);

            }
        } else if (dy < 0) {
            //向下滑动
            int scrollY = mTopView.getScrollY();
            if (scrollY > 0)
            {
                consumed[1] = Math.max(-scrollY, dy);
                mTopView.scrollBy(0, consumed[1]);
            }
        }


    }



    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
    }


    @Override
    public void onStopNestedScroll(View child) {
        mNestedScrollingParentHelper.onStopNestedScroll(child);
    }
}
