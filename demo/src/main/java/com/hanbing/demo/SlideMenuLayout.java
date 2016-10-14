package com.hanbing.demo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import com.hanbing.library.android.util.LogUtils;

/**
 * Created by hanbing on 2016/10/14
 */

public class SlideMenuLayout extends RelativeLayout {


    /**
     * 抽屉样式，从上面覆盖
     */
    public static final int MODE_DRAWER = 0;
    /**
     * 抽屉样式，从上面揭开
     */
    public static final int MODE_DRAWER_R = 1;

    /**
     * 展开样式，水平移动
     */
    public static final int MODE_EXPAND = 2;


    /**
     * 空闲
     */
    public static final int STATE_IDLE = 0;
    /**
     * 左侧菜单打开
     */
    public static final int STATE_LEFT_OPENED = 1;

    /**
     * 左侧菜单正在打开
     */
    public static final int STATE_LEFT_OPENING = 2;
    /**
     * 左侧菜单正在关闭
     */
    public static final int STATE_LEFT_CLOSING = 3;

    /**
     * 右侧菜单打开
     */
    public static final int STATE_RIGHT_OPENED = 4;
    /**
     * 右侧菜单正在打开
     */
    public static final int STATE_RIGHT_OPENING = 5;
    /**
     * 右侧菜单正在关闭
     */
    public static final int STATE_RIGHT_CLOSING = 6;

    /**
     * 中心的view
     */
    View mContentView;

    /**
     * 左侧菜单
     */
    View mLeftMenu;

    /**
     * 右侧的view
     */
    View mRightMenu;

    /**
     * 样式
     */
    int mMode = MODE_DRAWER;

    /**
     * 左侧和右侧的view可以指定百分比
     * 如果layout_width或layout_height没有指定大小，将按照百分比计算
     * 默认都是0.8
     */
    float WIDTH_SIZE_PERCENT = 0.8f;
    float mLeftMenuWidthPercent = WIDTH_SIZE_PERCENT;
    float mRightMenuWidthPercent = WIDTH_SIZE_PERCENT;


    int mLeftMenuId;
    int mRightMenuId;
    int mContentViewId;


    float mLastMotionX;
    float mLastMotionY;
    float mLastDownX;
    float mLastDownY;

    boolean mIsBeingDragged;
    Scroller mScroller;
    VelocityTracker mVelocityTracker;

    int mState = STATE_IDLE;

    int mTouchSlop;
    int mMaximumFlingVelocity;
    int mMinimumFlingVelocity;

    public SlideMenuLayout(Context context) {
        this(context, null);
    }

    public SlideMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }


    private void init() {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    @Override
    protected void onFinishInflate() {
        checkChildCount();
        findViews();
    }

    private void findViews() {
        if (0 != mContentViewId) {
            mContentView = findViewById(mContentViewId);
        }
        if (0 != mLeftMenuId) {
            mLeftMenu = findViewById(mLeftMenuId);
        }
        if (0 != mRightMenuId) {
            mRightMenu = findViewById(mRightMenuId);
        }


        int childCount = getChildCount();

        if (0 == childCount)
            return;

        if (1 == childCount) {
            //不管有没有通过id指定，都默认为content view
            mContentView = getChildAt(0);
        } else if (2 == childCount) {
            if (null == mContentView) {
                //如果没有指定content view
                mLeftMenu = getChildAt(0);
                mContentView = getChildAt(1);
            } else {
                //优先左菜单
                int index = indexOfChild(mContentView);
                for (int i = 0; i < childCount; i++) {
                    if (index == i)
                        continue;
                    if (null == mLeftMenu) mLeftMenu = getChildAt(i);
                }
            }
        } else {
            if (null == mContentView) {
                mLeftMenu = getChildAt(0);
                mContentView = getChildAt(1);
                mRightMenu = getChildAt(2);
            } else {
                int index = indexOfChild(mContentView);
                for (int i = 0; i < childCount; i++) {
                    if (index == i)
                        continue;
                    if (null == mLeftMenu) mLeftMenu = getChildAt(i);
                    else mRightMenu = getChildAt(i);
                }
            }

        }

    }

    private void checkChildCount() {
        int childCount = getChildCount();

        if (childCount > 3) {
            throw new IllegalArgumentException("Only need 3 children, current is " + childCount + ".");
        }
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);

        checkChildCount();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (null != mContentView) {
            mContentView.layout(0, 0, mContentView.getMeasuredWidth(), mContentView.getMeasuredHeight());
        }

        if (null != mLeftMenu) {
            mLeftMenu.layout(-mLeftMenu.getMeasuredWidth(), 0, 0, mLeftMenu.getMeasuredHeight());
        }

        if (null != mRightMenu) {
            mRightMenu.layout(getMeasuredWidth(), 0, getMeasuredWidth() + mRightMenu.getMeasuredWidth(), mRightMenu.getMeasuredHeight());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (null == mContentView)
            throw new IllegalArgumentException("Must have a content view.");

        View view = mLeftMenu;
        if (null != view) {

            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp.width < 0) {
                //按比例计算
                int parentWidthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (getMeasuredWidth() * mLeftMenuWidthPercent), MeasureSpec.EXACTLY);
                measureChild(view, parentWidthMeasureSpec, heightMeasureSpec);
            }
        }

        view = mRightMenu;
        if (null != view) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp.width < 0) {
                //按比例计算
                int parentWidthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (getMeasuredWidth() * mRightMenuWidthPercent), MeasureSpec.EXACTLY);
                measureChild(view, parentWidthMeasureSpec, heightMeasureSpec);
            }
        }

        view = mContentView;
        if (null != view) {
            //中间的view必须填满
            int parentWidthMeasureSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY);
            measureChild(view, parentWidthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        LogUtils.e("dispatchTouchEvent " + ev.getAction());
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mLastDownX = mLastMotionX = ev.getRawX();
            mLastDownY = mLastMotionY = ev.getRawX();
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        LogUtils.e("onInterceptTouchEvent " + ev.getAction());

        //滑动中，截断事件
        if (mIsBeingDragged)
            return true;

        if (null == mVelocityTracker)
            mVelocityTracker = VelocityTracker.obtain();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mVelocityTracker.addMovement(ev);
                return true;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocityTracker.addMovement(ev);
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
                if (Math.abs(mVelocityTracker.getXVelocity()) > Math.abs(mVelocityTracker.getYVelocity())) {
                    //横向滑动
                    mIsBeingDragged = true;
                    return true;
                }
                break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.e("onTouchEvent " + event.getAction());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return onTouchDown(event);
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                return onTouchUp(event);
            case MotionEvent.ACTION_MOVE:
                return onTouchMove(event);
        }

        return super.onTouchEvent(event);
    }

    private boolean onTouchDown(MotionEvent event) {
        return true;
    }

    private boolean onTouchMove(MotionEvent event) {

        if (null != mScroller && !mScroller.isFinished())
            mScroller.forceFinished(true);


        float x = event.getRawX();
        float y = event.getRawY();

        float dx = x - mLastMotionX;
        float dy = y - mLastMotionY;

        switch (mState) {
            case STATE_IDLE:
                if (dx > 0 && canOpenLeftMenu()) {
                    //向右滑动，打开左菜单
                    mState = STATE_LEFT_OPENING;
                    moveLeftMenuBy(dx, dy);
                } else if (dx < 0 && canOpenRightMenu()) {
                    mState = STATE_RIGHT_OPENING;
                    moveRightMenuBy(dx, dy);
                }
                break;
            case STATE_LEFT_OPENING:
            case STATE_LEFT_CLOSING:
                if (dx > 0) {
                    //向右
                    mState = STATE_LEFT_OPENING;
                } else {
                    //向左
                    mState = STATE_LEFT_CLOSING;
                }
                moveLeftMenuBy(dx, dy);
                break;
            case STATE_RIGHT_OPENING:
            case STATE_RIGHT_CLOSING:
                if (dx > 0) {
                    //向右
                    mState = STATE_LEFT_CLOSING;
                } else {
                    //向左
                    mState = STATE_RIGHT_OPENING;
                }
                moveLeftMenuBy(dx, dy);
                break;
        }

        mLastMotionX = x;
        mLastMotionY = y;

        return true;
    }

    private boolean onTouchUp(MotionEvent event) {

        if (mIsBeingDragged) {
            switch (mState) {
                case STATE_LEFT_OPENING:
                    openLeftMenu();
                    break;
                case STATE_LEFT_CLOSING:
                    closeLeftMenu();
                    break;
                case STATE_RIGHT_OPENING:
                    openRightMenu();
                    break;
                case STATE_RIGHT_CLOSING:
                    closeRightMenu();
                    break;
            }
        } else {
            mVelocityTracker.addMovement(event);
            mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);

            float xVelocity = mVelocityTracker.getXVelocity();
            float yVelocity = mVelocityTracker.getYVelocity();

            if (Math.abs(xVelocity) > Math.abs(yVelocity)) {
                //扫过去
                if (xVelocity > 0) {
                    if (STATE_IDLE == mState) {
                        openLeftMenu();
                    } else if (STATE_RIGHT_OPENED == mState
                            || STATE_RIGHT_OPENING == mState
                            || STATE_RIGHT_CLOSING == mState) {
                        closeRightMenu();
                    }

                } else {
                    if (STATE_IDLE == mState) {
                        openRightMenu();
                    } else if (STATE_LEFT_OPENED == mState
                            || STATE_LEFT_OPENING == mState
                            || STATE_LEFT_CLOSING == mState) {
                        closeLeftMenu();
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
    }

    private boolean canOpenLeftMenu() {
        return null != mLeftMenu;
    }

    private boolean canOpenRightMenu() {
        return null != mRightMenu;
    }


    private void moveViewBy(View view, float x, float y) {
        if (null == view)
            return;
        int w = view.getMeasuredWidth();
        int h = view.getMeasuredHeight();
        int l = (int) (view.getLeft() + x);
        int t = (int) (view.getTop() + y);

        view.layout(l, t, l + w, t + h);
    }

    private void moveViewTo(View view, float x, float y) {
        if (null == view)
            return;
        int w = view.getMeasuredWidth();
        int h = view.getMeasuredHeight();
        int l = (int) (x);
        int t = (int) (y);

        view.layout(l, t, l + w, t + h);

    }

    private void animateMoveViewTo(View view, float x, float y) {
        if (null == mScroller)
            mScroller = new Scroller(getContext());

        if (!mScroller.isFinished())
            mScroller.forceFinished(true);

        int dx = (int) (x - view.getLeft());
        int dy = (int) (y - view.getTop());
        mScroller.startScroll(view.getLeft(), view.getTop(), dx, dy);
    }

    private void moveLeftMenuBy(float x, float y) {
        moveViewBy(mLeftMenu, x, y);
    }

    private void moveLeftMenuTo(float x, float y) {
        moveViewTo(mLeftMenu, x, y);
    }

    private void moveRightMenuBy(float x, float y) {
        moveViewBy(mRightMenu, x, y);
    }

    private void moveRightMenuTo(float x, float y) {
        moveViewTo(mRightMenu, x, y);
    }


    public void setLeftMenu(View leftMenu) {
        if (null != mLeftMenu)
            removeView(mLeftMenu);

        mLeftMenu = leftMenu;
        addView(mLeftMenu);
    }

    public void setContentView(View contentView) {
        if (null != mContentView)
            removeView(mContentView);

        mContentView = contentView;
        addView(contentView);
    }

    public void setRightMenu(View rightMenu) {
        if (null != mRightMenu)
            removeView(mRightMenu);

        mRightMenu = rightMenu;
        addView(mRightMenu);
    }

    public void openLeftMenu() {
        animateMoveViewTo(mLeftMenu, 0, 0);
    }

    public void closeLeftMenu() {
        animateMoveViewTo(mLeftMenu, -mLeftMenu.getMeasuredWidth(), 0);
    }

    public void openRightMenu() {
        animateMoveViewTo(mRightMenu, getMeasuredWidth() - mRightMenu.getMeasuredWidth(), 0);
    }

    public void closeRightMenu() {
        animateMoveViewTo(mLeftMenu, getMeasuredWidth(), 0);
    }
}
