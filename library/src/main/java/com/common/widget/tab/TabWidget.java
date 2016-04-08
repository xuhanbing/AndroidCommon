/**
 *
 */
package com.common.widget.tab;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author hanbing
 * @date 2015-9-9
 */
public class TabWidget extends HorizontalScrollView implements OnClickListener,
        OnPageChangeListener {

    /**
     * use for create tab view
     *
     * @author hanbing
     * @date 2015-9-15
     */
    public static abstract class TabViewFactory {
        /**
         * create view
         *
         * @param tabSpec
         * @return
         */
        public abstract View createView(TabSpec tabSpec);
    }

    /**
     * use to create tab view
     *
     * @author hanbing
     * @date 2015-9-14
     */
    public static class DefaultTabViewFactory extends TabViewFactory {
        Context mContext = null;

        public DefaultTabViewFactory(Context context) {
            this.mContext = context;
        }

        public View createView(TabSpec tabSpec) {
            View view = tabSpec.view;
            if (null == view) {
                TextView text = new TextView(mContext);
                text.setText(tabSpec.label);

                int padding = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 10, mContext
                                .getResources().getDisplayMetrics());
                text.setPadding(padding, padding, padding, padding);
                text.setGravity(Gravity.CENTER);
                view = text;
            }

            view.setTag(tabSpec.tag);

            return view;
        }
    }

    /**
     * callback when tab is clicked
     *
     * @author hanbing
     * @date 2015-9-9
     */
    public static interface OnTabClickListener {
        public void onClick(int position);
    }

    public static class TabSpec {
        String tag;
        CharSequence label;
        View view;

        public TabSpec() {

        }

        public TabSpec(String label) {
            this.label = label;
            this.tag = label;
        }

        /**
         * @return the tag
         */
        public String getTag() {
            return tag;
        }

        /**
         * @param tag the tag to set
         */
        public void setTag(String tag) {
            this.tag = tag;
        }

        /**
         * @return the label
         */
        public CharSequence getLabel() {
            return label;
        }

        /**
         * @param label the label to set
         */
        public void setLabel(CharSequence label) {
            this.label = label;
        }

        /**
         * @return the view
         */
        public View getView() {
            return view;
        }

        /**
         * @param view the view to set
         */
        public void setView(View view) {
            this.view = view;
        }

        /**
         * set indicator with default, textview with label
         *
         * @param label
         */
        public TabSpec setIndicator(CharSequence label) {
            this.label = label;

            return this;
        }

        /**
         * set indicator with custom view
         *
         * @param view
         * @return
         */
        public TabSpec setIndicator(View view) {
            this.view = view;

            return this;
        }
    }

    /**
     * content layout
     */
    LinearLayout mInnerLayout;

    /**
     * content width
     */
    int mInnerLayoutContentWidth = 0;

    /**
     * tab click callback
     */
    OnTabClickListener mOnTabClickListener;

    /**
     * select tab, default is 0
     */
    int mSelectedTab = 0;


    /**
     * view factory
     */
    TabViewFactory mTabViewFactory = null;

    /**
     * if innerlayout can not scroll, we set layout width the same as scrollview
     */
    boolean mInnerLayoutCanScroll = true;

    HashMap<Object, TabSpec> mTabSpecMap = new HashMap<Object, TabSpec>();

    /**
     * viewpager
     */
    ViewPager mViewPager;

    /**
     * page change listener
     */
    ViewPager.OnPageChangeListener mOnPageChangeListener;



    /**
     * @param context
     */
    public TabWidget(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context, null);
    }

    /**
     * @param context
     * @param attrs
     */
    public TabWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TabWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        init(context, attrs);
    }

    /**
     * init
     */
    private void init(Context context, AttributeSet attrs) {
        // TODO Auto-generated method stub

//        if (null != attrs) {
//            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabWidget);
//
//        }

        mTabViewFactory = new DefaultTabViewFactory(getContext());

        setHorizontalFadingEdgeEnabled(false);
        setHorizontalScrollBarEnabled(false);

        mInnerLayout = new LinearLayout(getContext());

        addInnerLayout();

        setOnTabClickListener(new OnTabClickListener() {

            @Override
            public void onClick(int position) {
                // TODO Auto-generated method stub
                setCurrentItem(position);
            }
        });
    }

    /**
     * add inner layout to this view
     */
    private void addInnerLayout() {
        mInnerLayout.setDrawingCacheEnabled(true);
        mInnerLayout.setOrientation(LinearLayout.HORIZONTAL);

        android.view.ViewGroup.LayoutParams params = new android.view.ViewGroup.LayoutParams(
                mInnerLayoutCanScroll ? LayoutParams.WRAP_CONTENT
                        : LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        addView(mInnerLayout, params);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int innerChildCount = mInnerLayout.getChildCount();
//        /**
//         * if can not scroll, we set layout width the same as the scrollview's
//         */
//        if (!mInnerLayoutCanScroll) {
//            mInnerLayout
//                    .measure(MeasureSpec.makeMeasureSpec(getMeasuredWidth(),
//                            MeasureSpec.EXACTLY), heightMeasureSpec);
//
//
//        } else {
//        }

        /**
         * check if innelayout's content width is less than measure width if
         * true, then we set child view 's layoutparam with equal width
         */
        mInnerLayoutContentWidth = mInnerLayout.getMeasuredWidth();

        if (innerChildCount > 0) {
            int contentWidth = 0;
//            for (int i = 0; i < innerChildCount; i++) {
//                View child = mInnerLayout.getChildAt(i);
//
//                contentWidth += child.getMeasuredWidth();
//            }

            /**
             * can not fill
             */
            if (mInnerLayoutContentWidth < getMeasuredWidth()) {

                int width = getMeasuredWidth() / innerChildCount;


                mInnerLayoutCanScroll = false;

                for (int i = 0; i < innerChildCount; i++) {
                    View child = mInnerLayout.getChildAt(i);

                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();


                    params.width =  Math.max(width, child.getMeasuredWidth());
                    contentWidth += params.width;

                    child.setLayoutParams(params);
                }

                mInnerLayoutCanScroll = contentWidth > getMeasuredWidth();
                mInnerLayoutContentWidth = contentWidth;
            }

        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub

        super.onLayout(changed, l, t, r, b);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
    }

    /**
     * add default tab into inner layout
     *
     * @param title
     */
    private TabWidget addTab(String title) {
        TextView text = new TextView(getContext());
        text.setText(title);

        addTab(text);

        return this;
    }

    /**
     * add tab into inner layout
     *
     * @param child
     */
    private TabWidget addTab(View child) {
        // TODO Auto-generated method stub
        child.setOnClickListener(this);

        setTabLayoutParams(child);

        mInnerLayout.addView(child);

        // set default select
        child.setSelected(mInnerLayout.getChildCount() == mSelectedTab + 1);

        return this;
    }

    /**
     * add tab use tabspec
     *
     * @param tabSpec
     */
    public TabWidget addTab(TabSpec tabSpec) {
        if (null == mTabViewFactory)
            mTabViewFactory = new DefaultTabViewFactory(getContext());
        View view = mTabViewFactory.createView(tabSpec);

        view.setTag(tabSpec.tag);
        mTabSpecMap.put(tabSpec.tag, tabSpec);
        addTab(view);

        return this;
    }

    /**
     * set viewpager
     *
     * @param viewPager
     */
    public void setViewPager(ViewPager viewPager) {
        if (null == viewPager) {
            throw new IllegalArgumentException("viewPager must not be null");
        }

        if (null == viewPager.getAdapter()) {
            throw new IllegalArgumentException(
                    "viewPager'getAdapter must not be null");
        }

        mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(this);

        initTabs(viewPager.getAdapter());
    }

    /**
     * initialize tabs base on pagerAdapter
     *
     * @param pagerAdapter
     */
    public void initTabs(PagerAdapter pagerAdapter) {

        if (null != mInnerLayout)
            mInnerLayout.removeAllViews();
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            CharSequence title = pagerAdapter.getPageTitle(i);

            TabSpec tabSpec = newTabSpec(title + "").setIndicator(title);

            addTab(tabSpec);
        }
    }


    /**
     * create tabspec with tag
     *
     * @param tag
     * @return
     */
    public TabSpec newTabSpec(String tag) {
        TabSpec tabSpec = new TabSpec();
        tabSpec.tag = tag;

        return tabSpec;
    }

    /**
     * @param child
     */
    private void setTabLayoutParams(View child) {
        // TODO Auto-generated method stub

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if (null != child.getLayoutParams()) {
            params = new LinearLayout.LayoutParams(child.getLayoutParams());
        }

        if (!mInnerLayoutCanScroll) {

            params.width = 0;
            params.weight = 1;
        }
        child.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int index = mInnerLayout.indexOfChild(v);
        setSelectedItem(index);

        if (null != mOnTabClickListener) {
            mOnTabClickListener.onClick(index);
        }

    }

    /**
     * set whether it can scroll when content width may bigger than measure
     * width
     *
     * @param canScroll
     */
    public void setCanScroll(boolean canScroll) {
        mInnerLayoutCanScroll = canScroll;


//        android.view.ViewGroup.LayoutParams params = new android.view.ViewGroup.LayoutParams(
//                mInnerLayoutCanScroll ? LayoutParams.WRAP_CONTENT
//                        : LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        if (null != mInnerLayout) {
            android.view.ViewGroup.LayoutParams params = mInnerLayout.getLayoutParams();
            params.width = mInnerLayoutCanScroll ? LayoutParams.WRAP_CONTENT : ViewGroup.LayoutParams.MATCH_PARENT;
            mInnerLayout.setLayoutParams(params);
        }
    }

    /**
     * set callback when tab click
     *
     * @param onTabClickListener
     */
    public void setOnTabClickListener(OnTabClickListener onTabClickListener) {
        this.mOnTabClickListener = onTabClickListener;
    }

    /**
     * select tab item
     *
     * @param position
     */
    public void setSelectedItem(int position) {

        if (mSelectedTab == position)
            return;

        if (mInnerLayout.getChildCount() > mSelectedTab) {
            mInnerLayout.getChildAt(mSelectedTab).setSelected(false);
        }

        View child = mInnerLayout.getChildAt(position);
        if (mInnerLayout.getChildCount() > position) {
            child.setSelected(true);
        }

        onTabSelected(mSelectedTab, position);


        scrollIfNeed(position);


        mSelectedTab = position;

    }

    protected void onTabSelected(int last, int current) {

    }

    /**
     * select tab with tag
     *
     * @param tag
     */
    public void setSelectedItem(String tag) {
        setSelectedItem(indexOfTab(tag));
    }

    /**
     * find tab index with tag
     *
     * @param tag tag of tab
     * @return
     */
    public int indexOfTab(String tag) {
        View child = mInnerLayout.findViewWithTag(tag);

        return mInnerLayout.indexOfChild(child);
    }

    /**
     * if tab which is selected do not show fully , than we scroll
     *
     * @param position
     */
    protected void scrollIfNeed(int position) {
        if (!mInnerLayoutCanScroll)
            return;

        if (position >= getTabCount())
            return;
        View tab = getTab(position);

        int width = tab.getWidth();

        int[] location = new int[2];
        tab.getLocationInWindow(location);

        int x = location[0];
        int y = location[1];

        getLocationInWindow(location);

        /**
         * get tab widget left and right if tab's left is small than pl or right
         * is bigger, then scroll
         */
        int pl = location[0];
        int pr = pl + getWidth();

        int dx = 0;
        if (x < pl) {
            dx = x - pl;
        } else if (x > pr - width) {
            dx = x - (pr - width);
        }

//        smoothScrollBy(dx, 0);
        scrollBy(dx, 0);
    }

    /**
     * set inner layout , current must be linearlayout
     *
     * @param layout
     */
    public void setInnerLayout(LinearLayout layout) {
        if (null == layout)
            throw new IllegalArgumentException("layout must not be null");

        removeView(mInnerLayout);

        mInnerLayout = layout;
        addInnerLayout();
    }

    /**
     * get inner layout
     *
     * @return
     */
    public LinearLayout getInnerLayout() {
        return mInnerLayout;
    }

    /**
     * get tab view
     *
     * @param index
     * @return
     */
    public View getTab(int index) {
        return mInnerLayout.getChildAt(index);
    }

    /**
     * get tab item count
     *
     * @return
     */
    public int getTabCount() {
        return mInnerLayout.getChildCount();
    }


    /**
     * set tab view factory
     *
     * @param tabViewFactory
     */
    public void setTabViewFactory(TabViewFactory tabViewFactory) {
        this.mTabViewFactory = tabViewFactory;
    }


    /**
     * get viewpager
     *
     * @return
     */
    public ViewPager getViewPager() {
        return mViewPager;
    }

    /**
     * set custom {@link OnPageChangeListener}, must set use this function
     *
     * @param onPageChangeListener
     */
    public void setOnPageChangeListener(
            OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
        if (null != mOnPageChangeListener) {
            mOnPageChangeListener.onPageScrollStateChanged(arg0);
        }

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub
        if (null != mOnPageChangeListener) {
            mOnPageChangeListener.onPageScrolled(arg0, arg1, arg2);
        }


    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        if (null != mOnPageChangeListener) {
            mOnPageChangeListener.onPageSelected(arg0);
        }

        setSelectedItem(arg0);
    }

    /**
     * set current item
     *
     * @param position
     */
    public void setCurrentItem(int position) {
        setCurrentItem(position, true);
    }

    /**
     * set current item with tab tag
     *
     * @param tag
     */
    public void setCurrentItem(String tag) {
        setCurrentItem(tag, true);
    }

    /**
     * set current item
     *
     * @param position
     * @param smoothScroll
     */
    public void setCurrentItem(int position, boolean smoothScroll) {
        if (null != mViewPager)
            mViewPager.setCurrentItem(position, smoothScroll);

    }

    /**
     * set current item with tab tag
     *
     * @param tag
     * @param smoothScroll
     */
    public void setCurrentItem(String tag, boolean smoothScroll) {
        setCurrentItem(indexOfTab(tag), smoothScroll);
    }

    /**
     * simple mode without viewpager
     *
     * @return
     */
    protected boolean isSimpleMode() {
        return null == mViewPager;
    }

}

