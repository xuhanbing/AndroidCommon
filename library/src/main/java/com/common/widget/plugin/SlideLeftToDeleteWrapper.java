package com.common.widget.plugin;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by hanbing
 */
public class SlideLeftToDeleteWrapper implements IPluginWrapper {
    @Override
    public void measure(ViewGroup parent, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {

    }

    @Override
    public void layout(ViewGroup parent) {

    }

    @Override
    public void draw(ViewGroup parent, Canvas canvas) {

    }

    @Override
    public boolean interceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
