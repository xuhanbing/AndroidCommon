package com.common.widget.plugin;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.common.util.LogUtils;

/**
 * Created by hanbing
 */
public class ListViewDrawerItemWrapper extends DrawerItemWrapper<ListView> {
    public ListViewDrawerItemWrapper(ListView parent) {
        super(parent);
    }

    @Override
    public int findPosition(MotionEvent ev) {
        return mParent.pointToPosition((int)ev.getX(), (int)ev.getY());
    }

    @Override
    public View getChildAt(int position) {
        return mParent.getChildAt(position - mParent.getFirstVisiblePosition());
    }
}
