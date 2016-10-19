package com.hanbing.demo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hanbing.library.android.util.LogUtils;

/**
 * Created by hanbing on 2016/10/14
 */

public class SlidingMenu extends ViewGroup {

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
     * 判断打开还是关闭的比例，默认0.5
     */
    float mJudgeRatio = 0.5f;

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

    int mState = STATE_IDLE;

    Point mContentViewOriginalPos = new Point();
    Point mLeftMenuOriginalPos = new Point();
    Point mLeftMenuOpenedPos = new Point();
    Point mRightMenuOriginalPos = new Point();
    Point mRightMenuOpenedPos = new Point();


    ViewDragHelper mViewDragHelper;

    ViewDragHelper.Callback mViewDragHelperCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mContentView;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            LogUtils.e("clampViewPositionHorizontal " + left);
            if (child != mContentView)
                return 0;

            if (STATE_IDLE == mState) {
                if (dx > 0 && !isLeftMenuEnabled()) {
                    //左菜单不可用
                    return 0;
                } else if (dx < 0 && !isRightMenuEnabled()) {
                    //右菜单不可用
                    return 0;
                }
            } else {
                if (isLeftMenuActive()) {
                    left = Math.max(mContentViewOriginalPos.x, Math.min(mContentViewOriginalPos.x + mLeftMenu.getMeasuredWidth(), left));
                } else if (isRightMenuActive()) {
                    left = Math.max(mContentViewOriginalPos.x - mRightMenu.getMeasuredWidth(), Math.min(mContentViewOriginalPos.x, left));
                }
            }

            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return getPaddingTop();
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            int range = getMeasuredWidth() - child.getMeasuredWidth() + 1;
            if (isLeftMenuActive()) {
                range = getMeasuredWidth() - mLeftMenu.getMeasuredWidth();
            } else if (isRightMenuActive()) {
                range = getMeasuredWidth() - mRightMenu.getMeasuredWidth();
            }

            return range;
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            LogUtils.e("onEdgeDragStarted " +edgeFlags);
            //如果不是关闭的状态，返回
            if (STATE_IDLE != mState)
                return;
            if (isLeftMenuEnabled() && ViewDragHelper.EDGE_LEFT == edgeFlags)
            {
                //左侧抽屉

                mViewDragHelper.captureChildView(mContentView, pointerId);
                mState = STATE_LEFT_OPENING;

            } else if (isRightMenuEnabled() && ViewDragHelper.EDGE_RIGHT == edgeFlags) {
                //右侧抽屉
                mViewDragHelper.captureChildView(mContentView, pointerId);
                mState = STATE_RIGHT_OPENING;
            }

        }
        @Override
        public void onViewDragStateChanged(int state) {
            if (ViewDragHelper.STATE_IDLE == state) {

                switch (mState) {
                    case STATE_LEFT_OPENED:
                    case STATE_LEFT_OPENING:
                    case STATE_LEFT_CLOSING:
                        if (isLeftMenuOpened()) {
                            mState = STATE_LEFT_OPENED;
                        }else {
                            mState = STATE_IDLE;
                        }
                        break;
                    case STATE_RIGHT_OPENED:
                    case STATE_RIGHT_OPENING:
                    case STATE_RIGHT_CLOSING:
                        if (isRightMenuOpened()) {
                            mState = STATE_RIGHT_OPENED;
                        } else {
                            mState = STATE_IDLE;
                        }
                        break;
                    default:
                        mState = STATE_IDLE;
                        break;

                }

            }
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (left >= mContentViewOriginalPos.x) {
                if (isLeftMenuEnabled() && STATE_IDLE == mState)
                    updateState(STATE_LEFT_OPENING);
            } else if (left < mContentViewOriginalPos.x) {
                if (isRightMenuEnabled() && STATE_IDLE == mState)
                    updateState(STATE_RIGHT_CLOSING);
            }

            if (isLeftMenuActive()) {
                //左侧菜单跟随
                moveLeftMenuBy(dx, dy);
            } else if (isRightMenuActive()) {
                //右侧菜单跟随
                moveRightMenuBy(dx, dy);
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {

            if (isLeftMenuActive()) {
                //左侧打开中
                onLeftMenuReleased();

            } else if (isRightMenuActive()) {
                //右侧打开中
                onRightMenuReleased();
            }

        }

    };


    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlideMenuLayout);

        mLeftMenuWidthPercent = a.getFloat(R.styleable.SlideMenuLayout_leftMenuWidthPercent, WIDTH_SIZE_PERCENT);
        mRightMenuWidthPercent = a.getFloat(R.styleable.SlideMenuLayout_rightMenuWidthPercent, WIDTH_SIZE_PERCENT);
        mJudgeRatio = a.getFloat(R.styleable.SlideMenuLayout_judgeRatio, 0.5f);
        mContentViewId = a.getResourceId(R.styleable.SlideMenuLayout_contentViewId, 0);
        mLeftMenuId = a.getResourceId(R.styleable.SlideMenuLayout_leftMenuId, 0);
        mRightMenuId = a.getResourceId(R.styleable.SlideMenuLayout_rightMenuId, 0);

        a.recycle();

        init();
    }


    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, 0.3f, mViewDragHelperCallback);
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT|ViewDragHelper.EDGE_RIGHT);
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

        int count = 0;
        if (null != mContentView)
            count++;
        if (null != mLeftMenu)
            count++;
        if (null != mRightMenu)
            count++;

        int childCount = getChildCount();

        //都已指定，直接返回
        if (count == childCount)
            return;

        if (0 == childCount)
            return;

        if (1 == childCount) {
            //不管有没有通过id指定，都默认为content view
            mContentView = getChildAt(0);
        } else {
            for (int i = 0; i < childCount; i++) {
                View view =  getChildAt(i);
                if (isViewObtain(view))
                    continue;

                if (null == mLeftMenu) mLeftMenu = view;
                else if (null == mContentView) mContentView = view;
                else if (null == mRightMenu) mRightMenu = view;
            }
        }

    }

    private boolean isViewObtain(View view) {
        if (null == view) return false;

        if (view == mLeftMenu || view == mContentView || view == mRightMenu)
            return true;

        return false;
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

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        layoutChild(mContentView, paddingLeft, paddingTop);
        saveOriginalPos(mContentViewOriginalPos, mContentView);
        if (null != mLeftMenu) {
            layoutChild(mLeftMenu, paddingLeft - mLeftMenu.getMeasuredWidth(), paddingTop);
            saveOriginalPos(mLeftMenuOriginalPos, mLeftMenu);
            mLeftMenuOpenedPos.set(mLeftMenuOriginalPos.x + mLeftMenu.getMeasuredWidth(), mLeftMenuOriginalPos.y);
        }
        if (null != mRightMenu) {
            layoutChild(mRightMenu, width - paddingRight, paddingTop);
            saveOriginalPos(mRightMenuOriginalPos, mRightMenu);
            mRightMenuOpenedPos.set(mRightMenuOriginalPos.x - mRightMenu.getMeasuredWidth(), mRightMenuOriginalPos.y);
        }



    }


    protected void layoutChild(View child, int left, int top) {
        if (null == child) return;
        child.layout(left, top, left + child.getMeasuredWidth(), top + child.getMeasuredHeight());
    }

    private void saveOriginalPos(Point point, View view) {
        if (null != view && null != point) point.set(view.getLeft(), view.getTop());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (null == mContentView)
            throw new IllegalArgumentException("Must have a content view.");

        int contentWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();

        View view = mLeftMenu;
        if (null != view) {

            ViewGroup.LayoutParams lp = view.getLayoutParams();
            int w = lp.width;

            if (w < 0) {
                //按比例计算
                w = (int) (contentWidth * mLeftMenuWidthPercent);
            }

            int parentWidthMeasureSpec = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
            view.measure(parentWidthMeasureSpec, heightMeasureSpec);
        }

        view = mRightMenu;
        if (null != view) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();

            int w = lp.width;
            if (w < 0) {
                //按比例计算
                w = (int) (contentWidth * mRightMenuWidthPercent);
            }

            int parentWidthMeasureSpec = MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY);
            view.measure(parentWidthMeasureSpec, heightMeasureSpec);

        }

        view = mContentView;
        if (null != view) {
            //中间的view必须填满
            int parentWidthMeasureSpec = MeasureSpec.makeMeasureSpec(contentWidth, MeasureSpec.EXACTLY);
            view.measure(parentWidthMeasureSpec, heightMeasureSpec);
        }

    }

    private boolean isLeftMenuActive() {
        return isLeftMenuEnabled() && stateIn(STATE_LEFT_OPENED, STATE_LEFT_OPENING, STATE_LEFT_CLOSING);
    }

    private boolean isRightMenuActive() {
        return isRightMenuEnabled() && stateIn(STATE_RIGHT_OPENED, STATE_RIGHT_OPENING, STATE_RIGHT_CLOSING);
    }

    private boolean stateIn(int ... states) {
        if (null == states)
            return false;

        for (int i = 0; i < states.length; i++) {
            if (states[i] == mState) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean ret = mViewDragHelper.shouldInterceptTouchEvent(ev);

        if (isLeftMenuActive() || isRightMenuActive()) {
            //如果在content中，屏蔽上下滑动
            float x = ev.getX();
            float y = ev.getY();
            if (!ret && x >= mContentView.getLeft() && x <= mContentView.getRight()
                    && y >= mContentView.getTop() && y <= mContentView.getBottom()) {
                ret = true;
            }
        }

        return ret;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }


    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }



    private void onLeftMenuReleased() {

        int left = 0;
        int halfWidth = 0;
        left = mContentView.getLeft();
        halfWidth = (int) (mContentViewOriginalPos.x + mLeftMenu.getMeasuredWidth() * mJudgeRatio);

        if (left > halfWidth) openLeftMenu();
        else closeLeftMenu();
    }

    private void onRightMenuReleased() {

        int left = 0;
        int halfWidth = 0;
        left = mContentView.getLeft();
        halfWidth = (int) (mContentViewOriginalPos.x - mRightMenu.getMeasuredWidth() * mJudgeRatio);

        if (left < halfWidth) openRightMenu();
        else closeRightMenu();
    }

    private boolean isLeftMenuEnabled() {
        return null != mLeftMenu;
    }

    private boolean isRightMenuEnabled() {
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
        postInvalidate();
    }

    private void moveLeftMenuBy(float x, float y) {

        int left = (int) (mLeftMenu.getLeft() + x);

        int minLeft = mLeftMenuOriginalPos.x;
        int maxLeft = minLeft + mLeftMenu.getMeasuredWidth();

        if (left < minLeft) left = minLeft;
        if (left > maxLeft) left = maxLeft;

        x = left - mLeftMenu.getLeft();
        moveViewBy(mLeftMenu, x, y);

    }

    private void moveRightMenuBy(float x, float y) {

        int left = (int) (mRightMenu.getLeft() + x);

        int minLeft = mRightMenuOpenedPos.x;
        int maxLeft = minLeft + mRightMenu.getMeasuredWidth();

        if (left < minLeft) left = minLeft;
        if (left > maxLeft) left = maxLeft;

        x = left - mRightMenu.getLeft();

        moveViewBy(mRightMenu, x, y);
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
        if (!isLeftMenuEnabled())
            return;
        updateState(STATE_LEFT_OPENING);
        mViewDragHelper.smoothSlideViewTo(mContentView, mContentViewOriginalPos.x + mLeftMenu.getMeasuredWidth(), mContentViewOriginalPos.y);
        invalidate();

    }

    public void closeLeftMenu() {
        if (!isLeftMenuEnabled())
            return;
        updateState(STATE_LEFT_CLOSING);
        mViewDragHelper.smoothSlideViewTo(mContentView, mContentViewOriginalPos.x, mContentViewOriginalPos.y);
        invalidate();

    }

    public void openRightMenu() {
        if (!isRightMenuEnabled())
            return;
        updateState(STATE_RIGHT_OPENING);
        mViewDragHelper.smoothSlideViewTo(mContentView, mContentViewOriginalPos.x - mRightMenu.getMeasuredWidth(), mContentViewOriginalPos.y);
        invalidate();

    }

    public void closeRightMenu() {
        if (!isRightMenuEnabled())
            return;
        updateState(STATE_RIGHT_CLOSING);
        mViewDragHelper.smoothSlideViewTo(mContentView, mContentViewOriginalPos.x, mContentViewOriginalPos.y);
        invalidate();

    }

    public boolean isLeftMenuOpened() {
        return isLeftMenuEnabled() && mLeftMenu.getLeft() == mLeftMenuOpenedPos.x;
    }

    public boolean isRightMenuOpened() {
        return isRightMenuEnabled() && mRightMenu.getLeft() ==  mRightMenuOpenedPos.x;
    }


    private void updateState(int state) {
        mState = state;
    }
}
