package com.common.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.CompoundButton;
import android.widget.Scroller;

import com.androidcommon.R;
import com.common.util.LogUtils;


/**
 * Switch
 * 滑块在左边表示关闭，在右边表示开启
 * Created by hanbing on 2016/4/15.
 */
public class SwitchButton extends CompoundButton {

    private static final int STATE_CLOSED = -1;
    private static final int STATE_MOVING = 0;
    private static final int STATE_OPENED = 1;

    /**
     *
     */
    Drawable mThumb;

    Drawable mBackground;

    Drawable mOpenBackground;

    Drawable mCloseBackground;

    /**
     * 默认的padding
     */
    int mDefaultPadding = 2;

    /**
     *
     */
    Rect mThumbRect;
    Rect mThumbRectOpened;
    Rect mThumbRectClosed;

    boolean mIsFirstLayout = true;

    /**
     * 默认关闭
     */
    int mState = STATE_CLOSED;
    int mDownState = STATE_CLOSED;

    Scroller mScroller;

    int mDownX = 0;
    int mDownY = 0;
    int mMotionX = 0;
    int mMotionY = 0;
    boolean mIsBeingDragged = false;

    public SwitchButton(Context context) {
        super(context);
        init(context, null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        mThumbRect = new Rect();
        mState = isChecked() ? STATE_OPENED : STATE_CLOSED;

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        if (paddingLeft <= 0)
            paddingLeft = mDefaultPadding;

        if (paddingTop <= 0)
            paddingTop = mDefaultPadding;

        if (paddingRight <= 0)
            paddingRight = mDefaultPadding;

        if (paddingBottom <= 0)
            paddingBottom = mDefaultPadding;

        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

        if (null != attrs)
        {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);



            mThumb = a.getDrawable(R.styleable.SwitchButton_thumbDrawable);

            mOpenBackground = a.getDrawable(R.styleable.SwitchButton_openBackground);
            mCloseBackground = a.getDrawable(R.styleable.SwitchButton_closeBackground);

            a.recycle();
        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mIsFirstLayout || changed)
        {
            mIsFirstLayout = false;

            initOpenedRect();
            initClosedRect();
        }

        if (STATE_OPENED == mState)
        {
            mThumbRect.set(mThumbRectOpened);
        } else {
            mThumbRect.set(mThumbRectClosed);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if (onDownEvent(event))
                    return  true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (onMoveEvent(event))
                    return  true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (onUpEvent(event))
                    return  true;
                break;
        }
        return super.onTouchEvent(event);
    }

    boolean onDownEvent(MotionEvent event) {

        if (null != mScroller
                && !mScroller.isFinished())
            mScroller.forceFinished(true);

        if (isTouchInThumb(event))
        {
            mDownX = mMotionX = (int) event.getRawX();
            mDownY = mMotionY = (int) event.getRawY();


            mIsBeingDragged = true;


        } else {
            mIsBeingDragged = false;
        }

        mDownState = mState;

        return true;
    }

    boolean onMoveEvent(MotionEvent event) {

        if (mIsBeingDragged)
        {

            mState = STATE_MOVING;

            int width = mThumbRect.width();

            int x = (int) event.getRawX();

            int dx = x - mMotionX;

            mMotionX = x;

            x = mThumbRect.left + dx;

            if (x < getPaddingLeft() )
            {
                x = getPaddingLeft();
            } else if (x > getWidth() - getPaddingRight() - width)
            {
                x = getWidth() - getPaddingRight() - width;
            }

            mThumbRect.left = x;
            mThumbRect.right = x + width;

            postInvalidate();

            return true;
        }
        return false;
    }

    boolean onUpEvent(MotionEvent event) {

        if (null == mScroller)
            mScroller = new Scroller(getContext());

        int x = (int) event.getRawX();
        int startX = 0, endX = 0;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        int scaledTouchSlop = viewConfiguration.getScaledTouchSlop();

        int state ;
        int duration = 2500;
        if (mIsBeingDragged && Math.abs(x - mDownX) > scaledTouchSlop)
        {

            int width = mThumbRect.width();

            int centerX = (getWidth() - getPaddingLeft() - getPaddingRight())/ 2 - width / 2;


            x = mThumbRect.left + x - mMotionX;

            if (x < centerX)
            {
                initClosedRect();
                endX = mThumbRectClosed.left;

                state = STATE_CLOSED;
            } else {
                initOpenedRect();
                endX = mThumbRectOpened.right;

                state = STATE_OPENED;
            }

        } else {

            startX = mThumbRect.left;

            if (STATE_CLOSED == mState)
            {
                endX = mThumbRectClosed.left;
                state = STATE_OPENED;
            } else {
                endX = mThumbRectClosed.left;
                state = STATE_CLOSED;
            }

            duration *= 2;
        }

        mScroller.startScroll(startX, 0, endX - startX, 0, duration);

        mState = state;

        if (mDownState != mState)
        {
            postStateChanged();
        }

        postInvalidate();

        return true;
    }

    @Override
    public void computeScroll() {

        if (null != mScroller
                && mScroller.computeScrollOffset())
        {
            int width = mThumbRect.width();
            mThumbRect.left = mScroller.getCurrX();
            mThumbRect.right = mThumbRect.left + width;

            postInvalidate();
        }
    }

    private void initOpenedRect() {
        int contentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int contentHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        int thumbWidth = contentWidth / 2;
        int thumbHeight = contentHeight;

        if (null == mThumbRectOpened)
        {
            mThumbRectOpened = new Rect();
        }

        int t = getPaddingTop();
        int r = getWidth() - getPaddingRight();

        mThumbRectOpened.set(r - thumbWidth, t, r, t + thumbHeight);
    }

    private void initClosedRect() {
        int contentWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int contentHeight = getHeight() - getPaddingTop() - getPaddingBottom();

        int thumbWidth = contentWidth / 2;
        int thumbHeight = contentHeight;

        if (null == mThumbRectClosed)
        {

            mThumbRectClosed = new Rect();
        }
        int l = getPaddingLeft();
        int t = getPaddingTop();

        mThumbRectClosed.set(l, t, l + thumbWidth, t + thumbHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (null == mThumb)
            return;

        Drawable closeDrawable = mCloseBackground;
        Drawable openDrawable = mOpenBackground;

        Rect closeRect = new Rect();
        Rect openRect = new Rect();

        switch (mState)
        {
            case STATE_CLOSED:

                closeRect.set(0, 0, getWidth(), getHeight());
                if (null != closeDrawable)
                {
                    closeDrawable.setBounds(closeRect);
                    closeDrawable.draw(canvas);
                }

                mThumbRect.set(mThumbRectClosed);
                break;
            case STATE_MOVING:

            {
                openRect.set(0, 0, mThumbRect.right, getHeight());
                closeRect.set(mThumbRect.left, 0, getWidth(), getHeight());
            }
                break;
            case STATE_OPENED:
                openRect.set(0, 0, getWidth(), getHeight());

                mThumbRect.set(mThumbRectOpened);
                break;
        }


        if (null != openDrawable)
        {
            openDrawable.setBounds(openRect);
            openDrawable.draw(canvas);
        }

        if (null != closeDrawable)
        {
            closeDrawable.setBounds(closeRect);
            closeDrawable.draw(canvas);
        }



        mThumb.setBounds(mThumbRect);
        mThumb.draw(canvas);


    }

    private boolean isTouchInThumb(MotionEvent event) {
        return mThumbRect.contains((int)event.getX(), (int)event.getY());
    }

    private boolean isOpened() {
        return STATE_OPENED == mState;
    }


    private void postStateChanged() {
        setChecked(isOpened());
    }

    @Override
    public void setChecked(boolean checked) {

        if (isOpened() != checked)
        {
            mState = checked ? STATE_OPENED : STATE_CLOSED;
            postInvalidate();
        }


        super.setChecked(checked);


    }
}
