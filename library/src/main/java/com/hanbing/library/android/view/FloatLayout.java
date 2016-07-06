package com.hanbing.library.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.androidcommon.R;

import java.util.ArrayList;

/**
 * Created by hanbing on 2016/3/30.
 */
public class FloatLayout extends RelativeLayout {


    static class Gravity {

        public static final int DIRECTION_MASK = 0x0f;

        public static final int LEFT = 1;
        public static final int RIGHT = 2;
        public static final int TOP = 4;
        public static final int BOTTOM = 8;

        public static final int CENTER_HORIZONTAL = 0x10;
        public static final int CENTER_VERTICAL = 0x20;
        public static final int CENTER = CENTER_HORIZONTAL|CENTER_VERTICAL;
    }

    private int mGravity = Gravity.LEFT;

    private int mDirection = Gravity.LEFT;

    private boolean mAlignCenterHorizontal = false;
    private boolean mAlignCenterVertical = false;

    /**
     * float from left or right
     */
    private  boolean mIsHorizontal = true;

//    private int mContentPaddingLeft = 0;
//    private int mContentPaddingTop = 0;
//    private int mContentPaddingRight = 0;
//    private int mContentPaddingBottom = 0;
    private int mContentPaddingHorizontal = 0;
    private int mContentPaddingVertical = 0;


    public FloatLayout(Context context) {
        super(context);
        init(context, null);
    }

    public FloatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FloatLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs)
    {
        if (null == attrs)
            return;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatLayout);

        mGravity = a.getInteger(R.styleable.FloatLayout_floatGravity, Gravity.LEFT);

          mIsHorizontal = Gravity.LEFT == (mGravity& Gravity.LEFT)
                    || Gravity.RIGHT == (mGravity & Gravity.RIGHT);

        mDirection = Gravity.DIRECTION_MASK & mGravity;

        mAlignCenterHorizontal = Gravity.CENTER_HORIZONTAL  == (mGravity & Gravity.CENTER_HORIZONTAL);
        mAlignCenterVertical = Gravity.CENTER_VERTICAL  == (mGravity & Gravity.CENTER_VERTICAL);

        int contentPadding = a.getDimensionPixelSize(R.styleable.FloatLayout_floatPadding, 0);
        mContentPaddingHorizontal = a.getDimensionPixelOffset(R.styleable.FloatLayout_floatPaddingHorizontal, 0);
        mContentPaddingVertical = a.getDimensionPixelSize(R.styleable.FloatLayout_floatPaddingVertical, 0);

//        mContentPaddingLeft = a.getDimensionPixelOffset(R.styleable.FloatLayout_contentPaddingLeft, 0);
//        mContentPaddingRight = a.getDimensionPixelOffset(R.styleable.FloatLayout_contentPaddingRight, 0);
//        mContentPaddingTop = a.getDimensionPixelOffset(R.styleable.FloatLayout_contentPaddingTop, 0);
//        mContentPaddingBottom = a.getDimensionPixelOffset(R.styleable.FloatLayout_contentPaddingBottom, 0);

        if (contentPadding > 0)
        {
            mContentPaddingHorizontal = mContentPaddingVertical = contentPadding;
        }

//        if (mContentPaddingHorizontal > 0)
//        {
//            mContentPaddingLeft = mContentPaddingRight = mContentPaddingHorizontal;
//        }
//
//        if (mContentPaddingVertical > 0)
//        {
//            mContentPaddingTop = mContentPaddingBottom = mContentPaddingVertical;
//        }


        a.recycle();

    }

    public static class LayoutParams extends RelativeLayout.LayoutParams {

        public int left = 0;
        public int right = 0;
        public int top = 0;
        public int bottom = 0;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public int getNeedWidth()
        {
            return width + leftMargin + rightMargin;
        }

        public int getNeedHeight()
        {
            return height + topMargin + bottomMargin;
        }

    }

    private int getContentMaxWidth()
    {
        return getMeasuredWidth()  - getPaddingLeft() - getPaddingRight();
    }

    private int getContentMaxHeight() {
        return getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measureWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight();

        int childCount = getChildCount();
        int contentMaxWidth = getContentMaxWidth();
        int contentMaxHeight = getContentMaxHeight();
        int contentWidth = 0;
        int contentHeight = 0;
        int maxHeight = 0;
        int maxWidth = 0;



        int originLeft = getPaddingLeft();
        int originRight = measureWidth - getPaddingRight();
        int originTop = getPaddingTop();
        int originBottom = measureHeight - getPaddingBottom();

        int curLeft = originLeft;
        int curTop = originTop;
        int curRight = originRight;
        int curBottom = originBottom;


        ArrayList<View> list = new ArrayList<>();

        int direction = mDirection;

        for (int i = 0; i < childCount; i++)
        {
            View child = getChildAt(i);

            if (View.GONE == child.getVisibility())
                continue;

            MarginLayoutParams originParams = (MarginLayoutParams) child.getLayoutParams();

            int leftMargin = 0;
            int rightMargin = 0;
            int topMargin = 0;
            int bottomMargin = 0;

            int w = 0;
            int h = 0;

            leftMargin = originParams.leftMargin;
            rightMargin = originParams.rightMargin;
            topMargin = originParams.topMargin;
            bottomMargin = originParams.bottomMargin;

            w = originParams.width;
            h = originParams.height;

            LayoutParams params = new LayoutParams(originParams);

            int childWidthMeasureSpec = createChildMeasureSpec(widthMeasureSpec, w);
            int childHeightMeasureSpec = createChildMeasureSpec(heightMeasureSpec, h);

            measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

            int childMeasureWidth = child.getMeasuredWidth();
            int childMeasureHeight = child.getMeasuredHeight();

            params.width = childMeasureWidth;
            params.height = childMeasureHeight;

            int needHeight = childMeasureHeight + topMargin + bottomMargin + mContentPaddingVertical;
            int needWidth = childMeasureWidth + leftMargin + rightMargin + mContentPaddingHorizontal;

            switch (direction) {
                case Gravity.LEFT: {

                    if (contentWidth + childMeasureWidth + leftMargin  > contentMaxWidth)
                    {
                        contentHeight += maxHeight;
                        contentWidth = 0;
                        maxHeight = 0;

                        align(list, curLeft, curTop);
                        list.clear();

                        curLeft = originLeft;
                        curTop = contentHeight + getPaddingTop();
                    }

                    params.left = curLeft + mContentPaddingHorizontal + leftMargin;
                    params.right = params.left + childMeasureWidth;
                    params.top = curTop + mContentPaddingVertical + topMargin;
                    params.bottom = params.top + childMeasureHeight;

                    curLeft += needWidth ;

                }
                break;
                case Gravity.RIGHT: {
                    if (contentWidth + childMeasureWidth + rightMargin > contentMaxWidth)
                    {
                        contentHeight += maxHeight;
                        contentWidth = 0;
                        maxHeight = 0;

                        align(list, curLeft, curTop);
                        list.clear();

                        curRight = originRight;
                        curTop = contentHeight + getPaddingTop();

                    }

                    params.right = curRight  - mContentPaddingHorizontal - rightMargin;
                    params.left = params.right - childMeasureWidth;
                    params.top = curTop + mContentPaddingVertical + topMargin;
                    params.bottom = params.top + childMeasureHeight;

                    curRight -= needWidth;
                }
                break;

                case Gravity.TOP: {
                    if (contentHeight + childMeasureHeight + topMargin  > contentMaxHeight)
                    {
                        contentWidth += maxWidth;
                        contentHeight = 0;
                        maxWidth = 0;

                        align(list, curLeft, curTop);
                        list.clear();

                        curLeft = contentWidth + getPaddingLeft();
                        curTop = originTop;
                    }


                    params.left = curLeft+ mContentPaddingHorizontal + leftMargin ;
                    params.right = params.left + childMeasureWidth;
                    params.top = curTop + mContentPaddingVertical + topMargin ;
                    params.bottom = params.top + childMeasureHeight;

                    curTop += needHeight;
                }
                break;
                case Gravity.BOTTOM: {
                    if (contentHeight + childMeasureHeight + bottomMargin > contentMaxHeight)
                    {

                        contentWidth += maxWidth;
                        contentHeight = 0;
                        maxWidth = 0;

                        align(list, curLeft, curTop);
                        list.clear();

                        curBottom = originBottom;
                        curLeft = contentWidth + getPaddingLeft();
                    }


                    params.left = curLeft  + mContentPaddingHorizontal+ leftMargin;
                    params.right = params.left + childMeasureWidth;
                    params.bottom = curBottom - mContentPaddingVertical - bottomMargin ;
                    params.top = params.bottom - childMeasureHeight;

                    curBottom -= needHeight;
                }
                break;


            }


            child.setLayoutParams(params);
            list.add(child);



            if (mIsHorizontal)
            {
                contentWidth += needWidth;

                if (maxHeight < needHeight)
                {
                    maxHeight = needHeight;
                }


            } else {
                contentHeight += needHeight;

                if (maxWidth < needWidth)
                {
                    maxWidth = needWidth;
                }


            }

        }

        if (mIsHorizontal)
        {
            contentHeight += maxHeight + mContentPaddingVertical + getPaddingTop() + getPaddingBottom();
            align(list, curLeft, curTop);
            setMeasuredDimension(measureWidth, contentHeight);
        } else {
            contentWidth += maxWidth + mContentPaddingHorizontal + getPaddingLeft() + getPaddingRight();
            align(list, curLeft, curTop);
            setMeasuredDimension(contentWidth, measureHeight);
        }



    }

    private void align(ArrayList<View> list, int l, int t) {



        int direction = mDirection;
        boolean alignCenterHorizontal = mAlignCenterHorizontal;
        boolean alignCenterVertical = mAlignCenterVertical;

        if (!alignCenterHorizontal && !alignCenterVertical)
            return;

        int contentMaxWidth = getContentMaxWidth();
        int contentMaxHeight = getContentMaxHeight();

        int count = list.size();
        int contentWidth = 0;
        int contentHeight = 0;

        for (int i = 0; i < count; i++)
        {
            View child = list.get(i);


            LayoutParams params = (LayoutParams) child.getLayoutParams();

            if (mIsHorizontal)
            {
                contentWidth += params.getNeedWidth();
                contentHeight = Math.max(params.getNeedHeight(), contentHeight);
            } else {
                contentHeight += params.getNeedHeight();
                contentWidth = Math.max(params.getNeedWidth(), contentWidth);
            }

        }

        int xOffset = 0;
        int yOffset = 0;
        int centerX = 0;
        int centerY = 0;
        if (mIsHorizontal)
        {

            contentWidth += (count + 1) * mContentPaddingHorizontal;
            contentHeight += 2  * mContentPaddingVertical;

            xOffset = (contentMaxWidth - contentWidth) / 2;

            if (Gravity.RIGHT == direction)
            {
                xOffset = -xOffset;
            }

            centerY = (t + contentHeight  / 2);
        } else {

            contentHeight += (count + 1) * mContentPaddingVertical;
            contentWidth += 2  * mContentPaddingHorizontal;

            yOffset = (contentMaxHeight - contentHeight) / 2;
            if (Gravity.BOTTOM == direction)
            {
                yOffset = -yOffset;
            }
            centerX = (l + contentWidth  / 2);
        }

        for (int i = 0; i < count; i++)
        {
            View child = list.get(i);
            int measureWidth  = child.getMeasuredWidth();
            int measureHeight = child.getMeasuredHeight();

            LayoutParams params = (LayoutParams) child.getLayoutParams();


            if (mIsHorizontal)
            {
                if (alignCenterVertical)
                {
                    params.top = centerY - measureHeight / 2;
                    params.bottom = centerY + measureHeight / 2;
                }

                if (alignCenterHorizontal)
                {
                    params.left += xOffset;
                    params.right += xOffset;
                }
            }
            else {
                if (alignCenterVertical)
                {
                    params.top += yOffset;
                    params.bottom += yOffset;
                }

                if (alignCenterHorizontal)
                {
                    params.left = centerX - measureWidth / 2;
                    params.right = centerX + measureWidth / 2;
                }
            }


        }

    }



    private int createChildMeasureSpec(int parentMeasureSpec, int size)
    {
        int childMeasureSpec = 0;

        if (LayoutParams.MATCH_PARENT == size)
        {
            childMeasureSpec = parentMeasureSpec;
        } else if (LayoutParams.WRAP_CONTENT == size
                || 0 == size)
        {
            childMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }  else {
            childMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        }

        return childMeasureSpec;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                LayoutParams params = (LayoutParams) child.getLayoutParams();
                child.layout(params.left, params.top, params.right, params.bottom);
            }

        }

    }

}
