package com.common.view.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.androidcommon.R;

/**
 * Created by hanbing on 2016/3/25.
 */
public class TabStripWidget extends TabWidget {
    /**
     * strip left
     */
    int mStripLeft = 0;

    /**
     * width of strip
     */
    int mStripWidth = 0;

    /**
     * show strip or not
     */
    boolean mStripEnabled = true;

    /**
     * whether scroll strip when conten scroll
     */
    boolean mScrollStripEnabled = true;

    /**
     * whethere scroll strip follow content such as viewpager
     */
    boolean mScrollStripFollowContent = true;

    /**
     * scroll duration
     */
    int mScrollDuration = 1000;

    /**
     *
     */
    Handler mHandler = new Handler();

    /**
     * scroll
     */
    MyScroller mScroller = null;


    class MyScroller extends Scroller {
        int fromLeft;
        int fromWidth;
        int toLeft;
        int toWidth;


        public MyScroller(Context context) {
            super(context);
        }

        public MyScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public MyScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }
    }

    public TabStripWidget(Context context) {
        super(context);
        init(context, null);
    }

    public TabStripWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TabStripWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            setStrip(mSelectedTab);
    }

    private void init(Context context, AttributeSet attrs) {

        if (null != attrs)
        {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabStripWidget);

            mStripEnabled = a.getBoolean(R.styleable.TabStripWidget_showStrip, true);
            mScrollStripEnabled = a.getBoolean(R.styleable.TabStripWidget_enableStripScroll, true);
            mScrollStripFollowContent = a.getBoolean(R.styleable.TabStripWidget_enableStripScrollFollowContent, true);
            mScrollDuration = a.getInt(R.styleable.TabStripWidget_scrollDuration, mScrollDuration);
        }
    }

    /**
     * invoke when select item without content such as viewpager
     * @param fromIndex from tab index
     * @param toIndex to tab index
     */
    protected void scrollStripInSampleMode(int fromIndex, int toIndex)
    {
        if (!mStripEnabled)
            return;

        if (fromIndex < 0 || fromIndex >= getTabCount())
            return;

        if (toIndex < 0 || toIndex >= getTabCount())
            return;

        if (fromIndex == toIndex)
            return;


        View fromChild = getTab(fromIndex);
        View toChild = getTab(toIndex);

        final int fromLeft = fromChild.getLeft();
        final int fromWidth = fromChild.getMeasuredWidth();

        final int toLeft = toChild.getLeft();
        final int toWidth = toChild.getMeasuredWidth();

        final int delta = Math.abs(fromLeft - toLeft);

        if (mScrollStripEnabled)
        {
            if (null == mScroller)
            {
                mScroller = new MyScroller(getContext());
            } else if (!mScroller.isFinished()){
                mScroller.forceFinished(true);
            }
            mScroller.fromLeft = fromLeft;
            mScroller.fromWidth = fromWidth;
            mScroller.toLeft = toLeft;
            mScroller.toWidth = toWidth;

            mScroller.startScroll(fromLeft, 0, toLeft - fromLeft, 0, mScrollDuration);

            mStripLeft = fromLeft;
            mStripWidth = fromWidth;

            onUpdateStrip(mStripLeft,mStripWidth);

//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    if (null != mScroller && mScroller.computeScrollOffset()) {
//                        mStripLeft = mScroller.getCurrX();
//                        float ratio = Math.abs(mStripLeft - fromLeft) * 1.0f / delta;
//                        mStripWidth = (int) (fromWidth + ratio * (toWidth - fromWidth));
////                    requestLayout();
//                        LogUtils.e("mStripLeft=" + mStripLeft + ", mStripWidth=" + mStripWidth);
//                        onUpdateStrip(mStripLeft, mStripWidth);
//
//                        if (!mScroller.isFinished()) {
//                            mHandler.postDelayed(this, 10);
//                        }
//                    }
//
//                }
//            }, 10);
        } else {

            setStrip(toIndex);

        }

    }

    @Override
    public void computeScroll() {
        if (null != mScroller && mScroller.computeScrollOffset()) {
            mStripLeft = mScroller.getCurrX();
            int fromLeft = mScroller.fromLeft;
            int fromWidth = mScroller.fromWidth;
            int toLeft = mScroller.toLeft;
            int toWidth = mScroller.toWidth;

            float ratio = Math.abs(mStripLeft - mScroller.fromLeft) * 1.0f /  Math.abs(fromLeft - toLeft);
            mStripWidth = (int) (fromWidth + ratio * (toWidth - fromWidth));
//                    requestLayout();
            onUpdateStrip(mStripLeft, mStripWidth);
        }
    }

    /**
     * scroll trip when item scroll calculate strip left and width (top and
     * height may change as style is diffrent)
     *
     * @param position
     * @param positionOffset
     * @param positionOffsetPixels
     */
    public void scrollStripFollowViewPager(int position, float positionOffset,
                                           int positionOffsetPixels) {

        if (!mStripEnabled
                || !mScrollStripEnabled
                || !mScrollStripFollowContent)
            return;

        int tabCount = getTabCount();

        if (tabCount <= 1)
            return;

         {
            if (position >= getTabCount())
                return;

            mStripLeft = getTab(position).getLeft();
            mStripWidth = getTab(position).getMeasuredWidth();

            int nextTabWidth = 0;
            View child = null;
            if (0 == position) {
                child = getTab(position + 1);


            } else if (getTabCount() - 1 == position) {
                child = getTab(position - 1);

            } else {
                if (positionOffset >= 0) {
                    child = getTab(position + 1);
                } else {
                    child = getTab(position - 1);
                }
            }

            if (null != child) {
                nextTabWidth = child.getMeasuredWidth();

                mStripLeft += (int) (mStripWidth * positionOffset);

                positionOffset = Math.abs(positionOffset);
                int dx = nextTabWidth - mStripWidth;

                mStripWidth = (int) (mStripWidth + dx * positionOffset);

                onUpdateStrip(mStripLeft, mStripWidth);
            }

        }

    }

    private boolean isScrolling()
    {
        return null != mScroller && !mScroller.isFinished();
    }

    protected void onUpdateStrip(int left, int width)
    {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        super.onPageScrolled(arg0, arg1, arg2);

        if (mScrollStripFollowContent)
        scrollStripFollowViewPager(arg0, arg1, arg2);
    }


    @Override
    protected void onTabSelected(int last, int current) {
        //if in simple mode or scroll not follow content
        if (isSimpleMode() || !mScrollStripFollowContent)
        scrollStripInSampleMode(last, current);


    }

    /**
     * set strip position with child index
     * @param index
     */
    protected void setStrip(int index)
    {
        //if it is scrolling ,we do not set strip
        if (isScrolling())
            return;

        View child = getTab(index);
        if (null != child)
        {
            mStripLeft = child.getLeft();
            mStripWidth = child.getMeasuredWidth();
            onUpdateStrip(mStripLeft, mStripWidth);
        }
    }

    /**
     * set
     *
     * @param enable
     */
    public void setStripEnabled(boolean enable) {
        mStripEnabled = enable;
    }

    /**
     * set whether scroll when content scroll
     *
     * @param enable
     */
    public void setStripScrollEnabled(boolean enable) {
        mScrollStripEnabled = enable;
    }
}
