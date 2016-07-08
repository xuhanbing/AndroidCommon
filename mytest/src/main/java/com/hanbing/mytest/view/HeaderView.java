package com.hanbing.mytest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.*;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.TimeUtils;
import com.hanbing.library.android.view.ptr.IPtrHandler;
import com.hanbing.library.android.view.ptr.PtrLayout;

/**
 * Created by hanbing
 */
public class HeaderView extends TextView implements IPtrHandler {

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
        setText("This is header");
        setBackgroundColor(0x80000000);

        setGravity(Gravity.CENTER);

        setMinHeight(200);
    }

    @Override
    public void onPull(PtrLayout ptrLayout, int delta, float percent) {

    }

    @Override
    public void onReset() {

    }

    @Override
    public void onPullToRefresh() {
        setText("下拉刷新");
    }

    @Override
    public void onReleaseToRefresh() {

        setText("释放刷新");
    }

    @Override
    public void onRefreshPrepared() {

    }

    @Override
    public void onRefreshStarted() {
        LogUtils.e("onRefreshStart");
        setText("刷新中");

    }

    @Override
    public void onRefreshCompleted() {
        setText("更新于" + TimeUtils.getTime());
    }
}





