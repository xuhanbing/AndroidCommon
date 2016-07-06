package com.common.view.plugin;

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
import android.widget.TextView;

import com.common.util.DipUtils;
import com.common.util.ViewUtils;

import java.util.List;

/**
 * Created by hanbing on 2016/6/16.
 */
public class FastLocateLayout extends LinearLayout implements IPluginWrapper {

    public interface Adapter {

        /**
         * return tags
         *
         * @return
         */
        List<String> getTags();

        /**
         * return first item postion with tag
         *
         * @param tag
         * @return
         */
        int positionOfTag(String tag);
    }

    public interface OnSelectedListener {
        void onSelected(int position);
    }

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
            for (int i = 0; i < childCount; i++) {

                View child = getChildAt(i);

                height += child.getMeasuredHeight();
            }
        }

        if (mEqualChildHeight || height + getPaddingTop() + getPaddingBottom() > getMeasuredHeight()) {
            for (int i = 0; i < childCount; i++) {

                View child = getChildAt(i);

                LinearLayout.LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();

                layoutParams.weight = 1;

            }
            requestLayout();

        }
    }

    public View createFastLocateItemView(int position, ViewGroup parent) {
        return null;
    }

    /**
     *
     */
    Rect mRect = new Rect();

    /**
     * check is touch down in fast locate layout
     */
    boolean mTouchInside = false;

    /**
     * equal fast locate layout item height, weight = 1
     */
    boolean mEqualChildHeight = false;

    /**
     * set fast locate layout item text size accoring to item height
     */
    boolean mAutoTextSize = true;


    /**
     * default background color
     */
    int mDefaultBgColor = Color.TRANSPARENT;

    /**
     * press background color
     */
    int mPressBgColor = 0x80000000;


    /**
     * index tags
     */
    List<String> mTags;

    Adapter mAdapter;

    OnSelectedListener mOnSelectedListener;

    public boolean interceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchInside = isTouchInside(ev);
                if (mTouchInside) {
                    setBackgroundColor(mPressBgColor);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setBackgroundColor(mDefaultBgColor);
                break;
        }

        if (mTouchInside) {
            dealOnTouchEvent(ev);
            return true;
        }

        return false;
    }

    private boolean isTouchInside(MotionEvent event) {

        if (mRect.contains((int) event.getX(), (int) event.getY()))
            return true;

        return false;
    }

    private void dealOnTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();


        x -= mRect.left;
        y -= mRect.top;

        int childCount =  getChildCount();

        for (int i = 0; i < childCount; i++) {

            View child =  getChildAt(i);

            Rect rect = new Rect(child.getLeft(), child.getTop(), child.getRight(), child.getBottom());

            if (y > child.getTop() && y < child.getBottom()) {
                if (null != mAdapter && null != mOnSelectedListener)
                {
                    int position = mAdapter.positionOfTag(mTags.get(i));
                    mOnSelectedListener.onSelected(position);
                }
                break;
            }
        }

    }

    public void setEqualChildHeight(boolean equal) {

        if (equal != mEqualChildHeight) {
            for (int i = 0; i <  getChildCount(); i++) {

                View child = getChildAt(i);
                if (null != child) {
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) child.getLayoutParams();

                    if (equal) {
                        params.weight = 1;
                    } else {
                        params.weight = 0;
                    }
                }
            }
            requestLayout();
        }

        mEqualChildHeight = equal;
    }

   public void init(Adapter adapter) {

        List<String> tags = adapter.getTags();
        this.mTags = tags;
        this.mAdapter = adapter;

        removeAllViews();
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setOrientation(LinearLayout.VERTICAL);
        setBackgroundColor(mDefaultBgColor);
        setGravity(Gravity.CENTER);

        if (null == tags || tags.size() == 0)
            return;

        for (int i = 0; i < tags.size(); i++) {

            String tag = tags.get(i);
            View view = createFastLocateItemView(i, this);

            if (null == view) {
                final TextView textView = new TextView(getContext()) {
                    @Override
                    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

                        setTextSize(10);

                        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

                        if (mAutoTextSize) {
                            float textSize = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
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

            addView(view, params);

        }

    }

    public void measure(ViewGroup parent, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        if (null == parent)
            return;

        ViewUtils.measureChild(parent, this, parentWidthMeasureSpec, parentHeightMeasureSpec);

        int paddingLeft = parent.getPaddingLeft();
        int paddingRight = parent.getPaddingRight();
        int paddingTop = parent.getPaddingTop();
        int paddingBottom = parent.getPaddingBottom();

        int width =  getMeasuredWidth();
        int height = getMeasuredHeight();

        int right = parent.getMeasuredWidth() - paddingRight;
        int left = right - width;
        int top = paddingTop;
        int bottom = top + height;
        mRect.set(left, top, right, bottom);
    }

    public void layout(ViewGroup parent) {
        layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
    }

    public void draw(ViewGroup parent, Canvas canvas) {
        ViewUtils.drawChild(parent, this, canvas);
    }


    public void setOnSelectedListener(OnSelectedListener listener) {
        this.mOnSelectedListener = listener;
    }
}
