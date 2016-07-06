package com.hanbing.library.android.view.plugin;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by hanbing
 */
public interface IPluginWrapper {

    /**
     * measure size
     * @param parent
     * @param parentWidthMeasureSpec
     * @param parentHeightMeasureSpec
     */
    public void measure(ViewGroup parent, int parentWidthMeasureSpec, int parentHeightMeasureSpec);

    /**
     * layout position
     * @param parent
     */
    public void layout(ViewGroup parent);

    /**
     * draw in parent
     * @param parent
     * @param canvas
     */
    public void draw(ViewGroup parent, Canvas canvas);

    /**
     * if intercept touch event
     * @param ev
     * @return
     */
    public boolean interceptTouchEvent(MotionEvent ev);

}
