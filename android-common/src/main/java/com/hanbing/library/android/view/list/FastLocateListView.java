package com.hanbing.library.android.view.list;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hanbing.library.android.view.plugin.FastLocateLayout;

/**
 * Created by hanbing on 2016/6/15.
 */
public class FastLocateListView extends ListView implements FastLocateLayout.OnSelectedListener {


    FastLocateLayout mFastLocateLayout;

    public FastLocateListView(Context context) {
        super(context);
        init();
    }

    public FastLocateListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public FastLocateListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    void init() {

        setFadingEdgeLength(0);

        mFastLocateLayout = new FastLocateLayout(getContext());
        mFastLocateLayout.setOnSelectedListener(this);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (null != mFastLocateLayout) mFastLocateLayout.measure(this, widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (null != mFastLocateLayout) mFastLocateLayout.layout(this);

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (null != mFastLocateLayout)  mFastLocateLayout.draw(this, canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (null != mFastLocateLayout && mFastLocateLayout.interceptTouchEvent(ev))
            return true;

        return super.dispatchTouchEvent(ev);
    }




    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);

        if (null != mFastLocateLayout)
        {
            if (adapter instanceof FastLocateLayout.Adapter) {
                mFastLocateLayout.init((FastLocateLayout.Adapter) adapter);
            } else {
                mFastLocateLayout.init(null);
            }

        }

    }


    @Override
    public void onSelected(int position) {
        setSelection(position);
    }
}
