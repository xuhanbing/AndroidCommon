package com.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.common.util.DipUtils;
import com.common.util.LogUtils;

import java.util.List;

/**
 * Created by hanbing on 2016/6/15.
 */
public class FastLocateListView extends ListView {

    public interface Adapter {

        /**
         * return tags
         * @return
         */
        List<String> getTags();

        /**
         * return first item postion with tag
         * @param tag
         * @return
         */
        int positionWithTag(String tag);

    }

    class FastLocateLayout extends LinearLayout {

        public FastLocateLayout(Context context) {
            super(context);
        }

        public FastLocateLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public FastLocateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            int childCount = getChildCount();

            int height = 0;

            if (!mEqualChildHeight) {
                for (int i = 0 ;i < childCount; i++) {

                    View child = getChildAt(i);

                    height += child.getMeasuredHeight();
                }
            }

            if (mEqualChildHeight || height + getPaddingTop() + getPaddingBottom() > getMeasuredHeight()) {
                for (int i = 0 ;i < childCount; i++) {

                    View child = getChildAt(i);

                    LinearLayout.LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();

                    layoutParams.weight = 1;

                }
                requestLayout();

            }

            LogUtils.e("w = " + getMeasuredWidth() + ", h = " + getMeasuredHeight()
            + ", parent w = " + FastLocateListView.this.getMeasuredWidth() + ", h = " + FastLocateListView.this.getMeasuredHeight()
            + ", hh = " + FastLocateListView.this.getHeight());
        }
    }

    /**
     *
     */
    FastLocateLayout mFastLocateLayout;

    /**
     *
     */
    Rect mFastLocateLayoutRect;

    /**
     * check is touch down in fast locate layout
     */
    boolean mTouchDownInFastLocateLayout = false;

    /**
     * equal fast locate layout item height, weight = 1
     */
    boolean mEqualChildHeight = false;

    /**
     * set fast locate layout item text size accoring to item height
     */
    boolean mAutoTextSize = true;


    int mDefaultBgColor = Color.TRANSPARENT;
    int mPressBgColor = 0x80000000;

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

        mFastLocateLayoutRect = new Rect();
    }

    void initFastLocateLayout() {
        if (null == mFastLocateLayout) {
            mFastLocateLayout = new FastLocateLayout(getContext());
        } else {
            mFastLocateLayout.removeAllViews();
        }

        mFastLocateLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFastLocateLayout.setOrientation(LinearLayout.VERTICAL);
        mFastLocateLayout.setBackgroundColor(mDefaultBgColor);
        mFastLocateLayout.setGravity(Gravity.CENTER);

        List<String> tags = getTags();

        if (null == tags || tags.size() == 0)
            return;

        for (int i = 0; i < tags.size(); i++) {

            String tag = tags.get(i);
            View view = createFastLocateItemView(i, mFastLocateLayout);

            if (null == view) {
                final TextView textView = new TextView(getContext()) {
                    @Override
                    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

                        setTextSize(10);

                        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

                        if (mAutoTextSize) {
                            float textSize = getMeasuredHeight() - getPaddingTop()- getPaddingBottom();
                            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize * 0.75f);
                        }


                    }
                };

                textView.setText(tag);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.BLACK);


                int padding = DipUtils.dip2px(getContext(), 4);
                textView.setPadding(padding, padding, padding, padding);

                view = textView;
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            if (mEqualChildHeight) {
                params.weight = 1;
            }

            mFastLocateLayout.addView(view, params);

        }
    }

    void measureFastLocateLayout(int widthMeasureSpec, int heightMeasureSpec) {
        if (null == mFastLocateLayout) {
            return;
        }

        measureChild(mFastLocateLayout, widthMeasureSpec, heightMeasureSpec);

        int width = mFastLocateLayout.getMeasuredWidth();
        int height = mFastLocateLayout.getMeasuredHeight();

        int right = getMeasuredWidth() - getPaddingRight();
        int left = right - width;
        int top = getPaddingTop();
        int bottom = top + height;
        mFastLocateLayoutRect.set(left, top, right, bottom);

    }

    void layoutFastLocateLayout() {

        if (null == mFastLocateLayout || mFastLocateLayout.getChildCount() == 0) {
            return;
        }

        mFastLocateLayout.layout(mFastLocateLayoutRect.left, mFastLocateLayoutRect.top, mFastLocateLayoutRect.right, mFastLocateLayoutRect.bottom);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureFastLocateLayout(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        layoutFastLocateLayout();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (null != mFastLocateLayout) {
            drawChild(canvas, mFastLocateLayout, getDrawingTime());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchDownInFastLocateLayout = isTouchInFastLocateLayout(ev);
                if (mTouchDownInFastLocateLayout) {
                    mFastLocateLayout.setBackgroundColor(mPressBgColor);
                }
                break;
            case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                if (null != mFastLocateLayout) {
                    mFastLocateLayout.setBackgroundColor(mDefaultBgColor);
                }
                break;
        }

        if (mTouchDownInFastLocateLayout) {
            dealTouchEventInFastLocateLayout(ev);
            return true;
        }

        return super.dispatchTouchEvent(ev);
    }

    boolean isTouchInFastLocateLayout(MotionEvent event) {

        if (null != mFastLocateLayout
        && mFastLocateLayoutRect.contains((int) event.getX(), (int) event.getY()))
            return true;

        return false;
    }

    void dealTouchEventInFastLocateLayout(MotionEvent event) {
        if (null == mFastLocateLayout)
            return;

        int x = (int) event.getX();
        int y = (int) event.getY();


        x -= mFastLocateLayoutRect.left;
        y -= mFastLocateLayoutRect.top;


        int childCount = mFastLocateLayout.getChildCount();

        for (int i = 0; i < childCount; i++) {

            View child = mFastLocateLayout.getChildAt(i);

            Rect rect = new Rect(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());

            if (y > child.getTop() && y < child.getBottom()) {
                onSelect(i);
                break;
            }
        }

    }

    public void setFastLocateLayoutChildEqualHeight(boolean equal) {


        if (equal != mEqualChildHeight) {
            if (null != mFastLocateLayout) {
                for (int i = 0; i < mFastLocateLayout.getChildCount(); i++) {

                    View child = mFastLocateLayout.getChildAt(i);
                    if (null != child) {
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();

                        if (equal) {
                            params.weight = 1;
                        } else {
                            params.weight = 0;
                        }
                    }
                }
            }
            mFastLocateLayout.requestLayout();
        }


        mEqualChildHeight = equal;
    }


    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter instanceof Adapter) {
            initFastLocateLayout();
            requestLayout();
        }
    }

    public List<String> getTags() {

        ListAdapter adapter = getAdapter();

        if (adapter instanceof Adapter) {
            return ((Adapter) adapter).getTags();
        }

        return null;
    }

    public void onSelect(int tagIndex) {
        ListAdapter adapter = getAdapter();

        if (adapter instanceof Adapter) {

            List<String> tags = ((Adapter) adapter).getTags();

            int position = ((Adapter) adapter).positionWithTag(tags.get(tagIndex));

            setSelection(position);
        }
    }

    public View createFastLocateItemView(int position, ViewGroup parent) {
        return null;
    }

}
