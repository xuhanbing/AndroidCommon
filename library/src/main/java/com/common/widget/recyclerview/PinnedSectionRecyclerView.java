package com.common.widget.recyclerview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.common.widget.plugin.PinnedSectionWrapper;
import com.common.widget.plugin.RecyclerViewPinnedSectionWrapper;

/**
 * Created by hanbing
 */
public class PinnedSectionRecyclerView extends RecyclerView {

    RecyclerViewPinnedSectionWrapper mPinnedSectionWrapper;

    public PinnedSectionRecyclerView(Context context) {
        super(context);
        init();
    }

    public PinnedSectionRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PinnedSectionRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {
        mPinnedSectionWrapper = new RecyclerViewPinnedSectionWrapper(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (null != mPinnedSectionWrapper) {
            mPinnedSectionWrapper.measure(this, widthMeasureSpec, heightMeasureSpec);

        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        super.onLayout(changed, l, t, r, b);

        if (null != mPinnedSectionWrapper)
            mPinnedSectionWrapper.layout(this);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.dispatchDraw(canvas);

        if (null != mPinnedSectionWrapper)
            mPinnedSectionWrapper.draw(this, canvas);

    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (null != mPinnedSectionWrapper) {
            if (adapter instanceof PinnedSectionWrapper.Adapter) {
                mPinnedSectionWrapper.setAdapter((PinnedSectionWrapper.Adapter) adapter);
            }
        }
    }


    public void setPinnedView(View pinnedView) {
        if (null != mPinnedSectionWrapper) mPinnedSectionWrapper.setPinnedView(pinnedView);
    }
}
