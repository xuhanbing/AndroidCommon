package com.hanbing.library.android.view.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.hanbing.library.android.view.plugin.DrawerItemWrapper;
import com.hanbing.library.android.view.plugin.RecyclerViewDrawerItemWrapper;

/**
 * Slide to delete RecyclerView
 * Created by hanbing
 */
public class DrawerRecyclerView extends HeaderRecyclerView implements DrawerItemWrapper.Actor {

    RecyclerViewDrawerItemWrapper mDrawerItemWrapper;

    public DrawerRecyclerView(Context context) {
        super(context);
        init();
    }

    public DrawerRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawerRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {
        mDrawerItemWrapper = new RecyclerViewDrawerItemWrapper(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mDrawerItemWrapper.interceptTouchEvent(ev))
            return true;

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof DrawerItemWrapper.Adapter) {
            mDrawerItemWrapper.setAdapter((DrawerItemWrapper.Adapter) adapter);
        }
    }

    @Override
    public boolean dispatchTouchEventSuper(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public void closeDrawer() {
        mDrawerItemWrapper.close();
    }
}
