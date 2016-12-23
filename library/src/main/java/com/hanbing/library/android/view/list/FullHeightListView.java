/**
 *
 */
package com.hanbing.library.android.view.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;

/**
 * Created by hanbing
 */
public class FullHeightListView extends ListView {

    boolean mFullHeight = true;


    public FullHeightListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
    }

    public FullHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public FullHeightListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub

        super.onMeasure(widthMeasureSpec, mFullHeight ? View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE / 2, MeasureSpec.AT_MOST)
                : heightMeasureSpec);
    }

    public void setFullHeight(boolean fullHeight) {
        mFullHeight = fullHeight;
    }
}
