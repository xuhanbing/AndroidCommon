/**
 *
 */
package com.hanbing.library.android.view.list;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hanbing.library.android.view.plugin.AbsListViewPinnedSectionWrapper;
import com.hanbing.library.android.view.plugin.PinnedSectionWrapper;

/**
 * pinned list view
 *
 * Created by hanbing
 */
public class PinnedSectionListView extends ListView {


    AbsListViewPinnedSectionWrapper mPinnedSectionWrapper;


    /**
     * @param context
     */
    public PinnedSectionListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public PinnedSectionListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public PinnedSectionListView(Context context, AttributeSet attrs,
                                 int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * init
     */
    void init() {
        mPinnedSectionWrapper = new AbsListViewPinnedSectionWrapper(this);
        setOnScrollListener(mPinnedSectionWrapper);
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
    public void setOnScrollListener(OnScrollListener l) {
        // TODO Auto-generated method stub
        if (null != mPinnedSectionWrapper)
            super.setOnScrollListener(mPinnedSectionWrapper.wrapOnScrollListener(l));
        else
            super.setOnScrollListener(l);
    }


    @Override
    public void setAdapter(ListAdapter adapter) {
        // TODO Auto-generated method stub
        super.setAdapter(adapter);

        if (null != mPinnedSectionWrapper) {
            if (adapter instanceof PinnedSectionWrapper.Adapter) {
                mPinnedSectionWrapper.setAdapter((PinnedSectionWrapper.Adapter) adapter);
            }
        }
    }

    /**
     * set pinned view
     *
     * @param view
     */
    public void setPinnedView(View view) {
        if (null != mPinnedSectionWrapper) {
            mPinnedSectionWrapper.setPinnedView(view);
            requestLayout();
        }

    }


}
