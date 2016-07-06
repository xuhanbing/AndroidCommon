package com.hanbing.library.android.view.plugin;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hanbing
 */
public class RecyclerViewDrawerItemWrapper extends DrawerItemWrapper<RecyclerView> {
    public RecyclerViewDrawerItemWrapper(RecyclerView parent) {
        super(parent);
    }

    @Override
    public int findPosition(MotionEvent ev) {

        RecyclerView.LayoutManager layoutManager = mParent.getLayoutManager();

        View childViewUnder = mParent.findChildViewUnder(ev.getX(), ev.getY());

        return layoutManager.getPosition(childViewUnder);
    }

    @Override
    public View getChildAt(int position) {

        RecyclerView.LayoutManager layoutManager = mParent.getLayoutManager();

        return layoutManager.findViewByPosition(position);
    }
}
