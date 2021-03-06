package com.hanbing.library.android.view.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hanbing.library.android.view.plugin.DrawerItemWrapper;
import com.hanbing.library.android.view.plugin.ListViewDrawerItemWrapper;

/**
 * Slide left to delete ListView
 * Created by hanbing
 */
public class DrawerListView extends ListView implements DrawerItemWrapper.Actor {

    ListViewDrawerItemWrapper mDrawerItemWrapper;

    public DrawerListView(Context context) {
        super(context);
        init();
    }

    public DrawerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawerListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    void init() {
        mDrawerItemWrapper = new ListViewDrawerItemWrapper(this);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);

        if (adapter instanceof DrawerItemWrapper.Adapter) {
            mDrawerItemWrapper.setAdapter((DrawerItemWrapper.Adapter) adapter);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (mDrawerItemWrapper.interceptTouchEvent(ev)) {
           return true;
        }

        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean dispatchTouchEventSuper(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    public void closeDrawer() {
        mDrawerItemWrapper.close();
    }
}
