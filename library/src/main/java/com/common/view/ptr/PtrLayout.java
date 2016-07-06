package com.common.view.ptr;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.androidcommon.R;
import com.common.util.LogUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hanbing
 */
public class PtrLayout extends ViewGroup {


    interface State {
        /**
         * Default
         */
        int DEFAULT = 0;

        /**
         * Pull to refresh.
         */
        int PULL_TO_REFRESH = 1;

        /**
         * Release to refresh.
         */
        int RELEASE_TO_REFRESH = 2;

        /**
         * Prepare to refresh.
         */
        int PREPARE_TO_REFRESH = 3;

        /**
         * Refreshing.
         */
        int REFRESHING = 4;

        /**
         * Pull from start.
         * Pull down or pull right
         */
        int FROM_START = 0x1;

        /**
         * Pull from end.
         * Pull up or pull left
         */
        int FROM_END = 0x2;


        int PULL_TO_REFRESH_FROM_START = (FROM_START << 4) | PULL_TO_REFRESH;
        int RELEASE_TO_REFRESH_FROM_START = (FROM_START << 4) | RELEASE_TO_REFRESH;
        int PREPARE_TO_REFRESH_FROM_START = (FROM_START << 4) | PREPARE_TO_REFRESH;
        int REFRESHING_FROM_START = (FROM_START << 4) | REFRESHING;

        int PULL_TO_REFRESH_FROM_END = (FROM_END << 4) | PULL_TO_REFRESH;
        int RELEASE_TO_REFRESH_FROM_END = (FROM_END << 4) | RELEASE_TO_REFRESH;
        int PREPARE_TO_REFRESH_FROM_END = (FROM_END << 4) | PREPARE_TO_REFRESH;
        int REFRESHING_FROM_END = (FROM_END << 4) | REFRESHING;

    }

    public static final int VERTICAL = LinearLayout.VERTICAL;
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;


    /**
     * Orientation.
     * Default is {@link #VERTICAL}
     */
    int mOrientation = VERTICAL;

    /**
     * If pull from start is enabled, default is true
     * You can also invoke {@link #setHeaderView(View)}  and pass null to disable pull from start
     */
    boolean mPullFromStartEnabled = true;

    /**
     * If pull from end is enabled, default is true
     * You can also invoke {@link #setFooterView(View)} and pass null to disable pull from end
     */
    boolean mPullFromEndEnabled = true;


    /**
     * If dispatch touch event to children when refreshing
     * Default is true.You'd better do not set false.
     */
    boolean mDispatchTouchEventWhenRefreshing = true;

    /**
     * If hold header or footer when  refreshing.
     * Default is true.
     */
    boolean mHoldHeaderWhenRefreshing = true;

    /**
     * If refresh immediately or after scroller finished which may has some delay
     * Default is true;
     */
    boolean mRefreshImmediately = true;

    /**
     * view at top or left of content, you can  invoke {@link #setHeaderView(View)} and pass null to remove this view
     */
    View mHeaderView;

    /**
     * view at bottom or right of content, you can  invoke {@link #setFooterView(View)} and pass null to remove this view
     */
    View mFooterView;

    /**
     * content, this ViewGroup  can only host one content
     */
    View mContentView;

    /**
     * if there is being dragged
     */
    boolean mIsBeingDragged = false;

    /**
     *
     */
    VelocityTracker mVelocityTracker;

    /**
     *
     */
    int mTouchSlop;

    /**
     *
     */
    int mMaximumFlingVelocity;

    /**
     *
     */
    int mMinimumFlingVelocity;

    /**
     * touch down x, y
     */
    int mDownMotionX, mDownMotionY;

    /**
     * last motion x, y
     */
    int mLastMotionX, mLastMotionY;

    /**
     * current motion x, y
     */
    int mCurMotionX, mCurMotionY;

    /**
     * scroller
     */
    MoveScroller mScroller;

    /**
     *
     */
    Handler mHandler = new Handler();



    /**
     * current state
     */
    int mState = State.DEFAULT;

    /**
     * max move distance
     */
    int mMaxMoveDistance = 1000;

    /**
     * each move max distance
     */
    int mMaxMoveDelta = 100;

    /**
     *  ratio base on header or footer size
     *  if move distance reach mRatioToRefresh * (header or footer size), then you can release to refresh
     */
    float mRatioToRefresh = 1.2f;


    /**
     * scroll duration
     */
    int mScrollDuration = 750;

    /**
     * current move distance x , y
     */
    int mCurMoveX, mCurMoveY;

    /**
     * pull checker
     * check if you can pull from start or end
     */
    IPtrPullChecker mPullChecker;

    /**
     *
     */
    IPtrOnRefreshListener mOnRefreshListener;

    /**
     * Refresh start time {@link SystemClock}
     */
    long mRefreshStartedTime = 0;

    /**
     * Refresh minimum duration
     */
    long mMinimumRefreshDuration = 2500;

    /**
     *
     */
    boolean mInsureMinimumRefreshDuration = true;

    /**
     * Header view layout id
     */
    int mHeaderViewId = -1;

    /**
     * Footer view layout id.
     */
    int mFooterViewId = -1;

    /**
     * Content view layout id.
     */
    int mContentViewId = -1;

    /**
     * animation move scroller
     */
    class MoveScroller implements Runnable {

        Scroller mScroller = new Scroller(getContext());

        Handler mHandler = new Handler();

        int mDelay = 10;

        public void abort() {
            if (!mScroller.isFinished()) {
                mHandler.removeCallbacks(this);
                mScroller.forceFinished(true);
            }

        }

        public void scrollTo(int x, int y) {
            mScroller.startScroll(mCurMoveX, mCurMoveY, x - mCurMoveX, y - mCurMoveY, mScrollDuration);
            mHandler.postDelayed(this, mDelay);
        }

        @Override
        public void run() {

            if (mScroller.computeScrollOffset()) {

                LogUtils.e("cx = " + mScroller.getCurrX() + ", cy = " + mScroller.getCurrX()
                + ", fx = " + mScroller.getFinalX() + ", fy = " + mScroller.getFinalY());
                if (mCurMoveX != mScroller.getCurrX()
                        || mCurMoveY != mScroller.getCurrY()) {
                    mCurMoveX = mScroller.getCurrX();
                    mCurMoveY = mScroller.getCurrY();

                    layoutChildren();
                }

                mHandler.postDelayed(this, mDelay);
            } else {

                postOnScrollFinished();

                mHandler.removeCallbacks(this);
            }

        }
    }


    public PtrLayout(Context context) {
        this(context, null);
    }

    public PtrLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PtrLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (null != attrs) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PtrLayout, defStyleAttr, 0);

            mOrientation = a.getInt(R.styleable.PtrLayout_ptrOrientation, mOrientation);
            mPullFromStartEnabled = a.getBoolean(R.styleable.PtrLayout_ptrPullFromStartEnabled, mPullFromEndEnabled);
            mPullFromEndEnabled = a.getBoolean(R.styleable.PtrLayout_ptrPullFromEndEnabled, mPullFromEndEnabled);
            mDispatchTouchEventWhenRefreshing = a.getBoolean(R.styleable.PtrLayout_ptrDispatchTouchEventWhenRefreshing, mDispatchTouchEventWhenRefreshing);
            mHoldHeaderWhenRefreshing = a.getBoolean(R.styleable.PtrLayout_ptrHoldHeaderWhenRefreshing, mHoldHeaderWhenRefreshing);
            mRefreshImmediately = a.getBoolean(R.styleable.PtrLayout_ptrRefreshImmediately, mRefreshImmediately);
            mMaxMoveDistance = a.getDimensionPixelSize(R.styleable.PtrLayout_ptrMaxMoveDistance, mMaxMoveDistance);
            mMaxMoveDelta = a.getDimensionPixelSize(R.styleable.PtrLayout_ptrMaxMoveDelta, mMaxMoveDelta);
            mRatioToRefresh = a.getFloat(R.styleable.PtrLayout_ptrRatioToRefresh, mRatioToRefresh);
            mScrollDuration = a.getInt(R.styleable.PtrLayout_ptrScrollDuration, mScrollDuration);
            mInsureMinimumRefreshDuration = a.getBoolean(R.styleable.PtrLayout_ptrInsureMinimumRefreshDuration, mInsureMinimumRefreshDuration);
            mMinimumRefreshDuration = a.getInt(R.styleable.PtrLayout_ptrMinimumRefreshDuration, (int) mMinimumRefreshDuration);
            mHeaderViewId = a.getResourceId(R.styleable.PtrLayout_ptrHeaderViewId, -1);
            mFooterViewId = a.getResourceId(R.styleable.PtrLayout_ptrFooterViewId, -1);
            mContentViewId = a.getResourceId(R.styleable.PtrLayout_ptrContentViewId, -1);

            a.recycle();
        }
        init(attrs);
    }


    void init(AttributeSet attrs) {


        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());

        mTouchSlop = viewConfiguration.getScaledTouchSlop();
        mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();

        mPullChecker = PtrPullChecker.defaultPtrPullChecker();

        mScroller = new MoveScroller();

    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();

        mHeaderView = null;
        mFooterView = null;
        mContentView = null;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();
        if (childCount > 3) {
            throw new IllegalStateException("PtrLayout can host only max three child.");
        }


        if (0 == childCount)
            return;

        int leftCount = childCount;

        if (mHeaderViewId > 0 && null == mHeaderView) {
            mHeaderView = findViewById(mHeaderViewId);

            if (null != mHeaderView) leftCount--;
        }

        if (mContentViewId > 0 && null == mContentView) {
            mContentView = findViewById(mContentViewId);

            if (null != mContentView) leftCount--;
        }

        if (mFooterViewId > 0 && null == mFooterView) {
            mFooterView = findViewById(mFooterViewId);

            if (null != mFooterView) leftCount--;
        }

        if (leftCount == 3) {
            //Views are not signed, find views one by one

            View child1 = getChildAt(0);
            View child2 = getChildAt(1);
            View child3 = getChildAt(2);

            if (child1 instanceof IPtrHandler) {
                mHeaderView = child1;
                if (child2 instanceof IPtrHandler && !(child3 instanceof IPtrHandler)) {
                    mFooterView = child2;
                    mContentView = child3;
                } else {
                    mContentView = child2;
                    mFooterView = child3;
                }
            } else if (child2 instanceof IPtrHandler) {
                mContentView = child1;
                mHeaderView = child2;
                mFooterView = child3;
            } else  {
                mHeaderView = child1;
                mContentView = child2;
                mFooterView = child3;
            }

        } else if (2 == leftCount) {
            if (childCount == leftCount) {
                //Only two children and not signed
                View child1 = getChildAt(0);
                View child2 = getChildAt(1);

                if (child1 instanceof IPtrHandler && child2 instanceof IPtrHandler) {
                    //Both are header or footer
                    mHeaderView = child1;
                    mFooterView = child2;
                } else if(child1 instanceof IPtrHandler){
                    //First is header
                    mHeaderView = child1;
                    mContentView = child2;
                } else if (child2 instanceof IPtrHandler) {
                    //Second is footer
                    mContentView = child1;
                    mFooterView = child2;
                } else {
                    //Both are simple view
                    mHeaderView = child1;
                    mContentView = child2;
                }

            } else {
                //One view is signed
                if (null != mHeaderView) {
                    View[] childs = findLeftViews(mHeaderView);
                    View child1 = childs[0];
                    View child2 = childs[1];

                    if (child1 instanceof IPtrHandler && !(child2 instanceof IPtrHandler)) {
                        mFooterView = child1;
                        mContentView = child2;
                    } else{
                        mContentView = child1;
                        mFooterView = child2;
                    }

                } else if (null != mFooterView) {
                    View[] childs = findLeftViews(mFooterView);
                    View child1 = childs[0];
                    View child2 = childs[1];

                    if (!(child1 instanceof IPtrHandler) && child2 instanceof IPtrHandler) {
                        mHeaderView = child2;
                        mContentView = child1;
                    } else {
                        mHeaderView = child1;
                        mContentView = child2;
                    }
                } else if (null != mContentView) {
                    View[] childs = findLeftViews(mContentView);
                    mHeaderView = childs[0];
                    mFooterView = childs[1];
                }
            }
        } else if (1 == leftCount) {
            if (leftCount == childCount) {
                //Only one child
                mContentView = getChildAt(0);
            } else {
                //Only one view is not signed
                if (null == mHeaderView) {
                    View[] childs = findLeftViews(mContentView, mFooterView);
                    mHeaderView = childs[0];
                } else if (null == mFooterView) {
                    View[] childs = findLeftViews(mContentView, mHeaderView);
                    mFooterView = childs[0];
                } else if (null == mContentView) {
                    View[] childs = findLeftViews(mHeaderView, mFooterView);
                    mContentView = childs[0];
                }
            }
        }

    }

    View[] findLeftViews(View ... views) {

        int childCount = getChildCount();
        if (0 == childCount)
            return null;

        List<View> list = null;
        if (null != views && 0 != views.length) {
            list = Arrays.asList(views);
        }

        View[] arr = new View[childCount];

        int index = 0;

        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);

            if (null == list || !list.contains(child)) {
                arr[index++] = child;
            }
        }

        return arr;
    }

    protected LayoutParams generateDefaultHeaderLayoutParams() {
        if (isVertical()) {
            return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        } else {
            return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        measureHeaderView(mHeaderView, widthMeasureSpec, heightMeasureSpec);
        measureHeaderView(mFooterView, widthMeasureSpec, heightMeasureSpec);

        if (null != mContentView && mContentView.getVisibility() != View.GONE) {
            measureChild(mContentView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    void measureHeaderView(View view, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {

        if (null != view && view.getVisibility() != View.GONE) {
            LayoutParams layoutParams = view.getLayoutParams();

            if (null == layoutParams) {
                layoutParams = generateDefaultHeaderLayoutParams();
                view.setLayoutParams(layoutParams);
            }

            measureChild(view, parentWidthMeasureSpec, parentHeightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChildren();
    }

    void layoutChildren() {
        if (null == mContentView)
            return;

        mContentView.layout(0, 0, getWidth(), getHeight());

        if (isVertical()) {
            layoutChildrenVertical();
        } else {
            layoutChildrenHorizontal();
        }

    }


    void layoutChildrenVertical() {
        if (mCurMoveY >= 0 && isStateIn(State.DEFAULT, State.PULL_TO_REFRESH_FROM_START, State.RELEASE_TO_REFRESH_FROM_START, State.REFRESHING_FROM_START, State.PREPARE_TO_REFRESH_FROM_START)) {
            layoutChildren(true);
        } else if (mCurMoveY >= 0 && isStateIn(State.REFRESHING_FROM_END)) {
            layoutChildren(false);
        } else if (mCurMoveY <= 0 && isStateIn(State.DEFAULT, State.PULL_TO_REFRESH_FROM_END, State.RELEASE_TO_REFRESH_FROM_END, State.REFRESHING_FROM_END, State.PREPARE_TO_REFRESH_FROM_END)) {
            layoutChildren(false);
        } else if (mCurMoveY <= 0 && isStateIn(State.REFRESHING_FROM_START)) {
            layoutChildren(true);
        }
    }

    void layoutChildrenHorizontal() {
        if (mCurMoveX >= 0 && isStateIn(State.DEFAULT, State.PULL_TO_REFRESH_FROM_START, State.RELEASE_TO_REFRESH_FROM_START, State.REFRESHING_FROM_START, State.PREPARE_TO_REFRESH_FROM_START)) {
            layoutChildren(true);
        } else if (mCurMoveX >= 0 && isStateIn(State.REFRESHING_FROM_END)) {
            layoutChildren(false);
        } else if (mCurMoveX <= 0 && isStateIn(State.DEFAULT, State.PULL_TO_REFRESH_FROM_END, State.RELEASE_TO_REFRESH_FROM_END, State.REFRESHING_FROM_END, State.PREPARE_TO_REFRESH_FROM_END)) {
            layoutChildren(false);
        } else if (mCurMoveX <= 0 && isStateIn(State.REFRESHING_FROM_START)) {
            layoutChildren(true);
        }
    }

    void layoutChildren(boolean fromStart) {
        int left = 0;
        int right = getWidth();
        int top = 0;
        int bottom = getHeight();

        if (fromStart) {
            if (null != mHeaderView) {

                if (isVertical()) {
                    bottom = mCurMoveY;
                    top = bottom - getHeaderSize();

                    mHeaderView.layout(left, top, right, bottom);

                    top = bottom;
                    bottom = getHeight();

                } else {

                    right = mCurMoveX;
                    left = right - getHeaderSize();

                    mHeaderView.layout(left, top, right, bottom);

                    left = right;
                    right = getWidth();
                }

                mContentView.layout(left, top, right, bottom);
            }

            if (null != mFooterView) {
                mFooterView.layout(0, 0, 0, 0);
            }

        } else {

            if (null != mHeaderView) {
                mHeaderView.layout(0, 0, 0, 0);
            }


            if (null != mFooterView) {

                if (isVertical()) {
                    top = getHeight() + mCurMoveY;
                    top = Math.min(getHeight(), top);
                    bottom = top + getFooterSize();

                    mFooterView.layout(left, top, right, bottom);
                    bottom = top;
                    top = bottom - getContentSize();
                } else {
                    left = getWidth() + mCurMoveX;
                    left = Math.min(getWidth(), left);
                    right = left + getFooterSize();

                    mFooterView.layout(left, top, right, bottom);

                    right = left;
                    left = right - getContentSize();
                }

                mContentView.layout(left, top, right, bottom);
            }

        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        mVelocityTracker.addMovement(ev);

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (State.REFRESHING_FROM_START != mState && State.REFRESHING_FROM_END != mState) {
                    mState = State.DEFAULT;
                }

                mIsBeingDragged = false;
                mDownMotionX = mLastMotionX = mCurMotionX = x;
                mDownMotionY = mLastMotionY = mCurMotionY = y;

                if (null == mVelocityTracker) {
                    mVelocityTracker = VelocityTracker.obtain();
                }
                mVelocityTracker.clear();
                mVelocityTracker.addMovement(ev);
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (null == mContentView)
            return false;

        if (!mPullFromStartEnabled
                && !mPullFromEndEnabled)
            return false;

        if (null == mHeaderView
                && null == mFooterView)
            return false;


        if (isRefreshing()
                && !mDispatchTouchEventWhenRefreshing)
            return true;

        mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);

        float vx = mVelocityTracker.getXVelocity();
        float vy = mVelocityTracker.getYVelocity();

        /**
         * this not move , and is not being dragged
         */
        if (!mIsBeingDragged && Math.abs(vx) < mMinimumFlingVelocity && Math.abs(vy) < mMinimumFlingVelocity)
            return false;

        if (isVertical()) {
            /**
             * move horizontal, dispatch to child
             */
            if (Math.abs(vx) > Math.abs(vy)) {
                return false;
            }
        } else {

            /**
             * move vertical, dispatch to child
             */
            if (Math.abs(vx) < Math.abs(vy)) {
                return false;
            }
        }

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE: {

                int deltaX = x - mLastMotionX;
                int deltaY = y - mLastMotionY;

                if (VERTICAL == mOrientation) {
                    int moveY = mCurMoveY + deltaY;

                    if (interceptTouchEvent(moveY, deltaY))
                        return true;

                } else {
                    int moveX = mCurMoveX + deltaX;

                    if (interceptTouchEvent(moveX, deltaX))
                        return true;
                }
            }
            break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {

            }
            break;

        }

        return false;
    }

    boolean interceptTouchEvent(int move, int delta) {

        switch (mState) {
            case State.DEFAULT:
                if (interceptTouchEventFromStart(move, delta)) {
                    return true;
                } else if (interceptTouchEventFromEnd(move, delta)) {
                    return true;
                }
                break;
            case State.PULL_TO_REFRESH_FROM_START: {
                if (interceptTouchEventFromStart(move, delta)) {
                    return true;
                }
            }
            break;
            case State.RELEASE_TO_REFRESH_FROM_START:
                if (interceptTouchEventFromStart(move, delta)) {
                    return true;
                }
                break;
            case State.REFRESHING_FROM_START:
                if (interceptTouchEventFromStart(move, delta)) {

                    if (delta < 0 && mHoldHeaderWhenRefreshing) {
                        return false;
                    }

                    return true;
                }
                break;
            case State.REFRESHING_FROM_END:
                if (interceptTouchEventFromEnd(move, delta)) {
                    if (delta > 0 && mHoldHeaderWhenRefreshing) {
                        return false;
                    }
                    return true;
                }

                break;
            case State.PULL_TO_REFRESH_FROM_END:
                if (interceptTouchEventFromEnd(move, delta)) {
                    return true;
                }
                break;

            case State.RELEASE_TO_REFRESH_FROM_END:
                if (interceptTouchEventFromEnd(move, delta)) {
                    return true;
                }
                break;
        }

        return false;

    }

    private boolean interceptTouchEventFromStart(int move, int delta) {
        return mPullFromStartEnabled && move > 0 && (mPullChecker.canPullFromStart(this));
    }

    private boolean interceptTouchEventFromEnd(int move, int delta) {
        return mPullFromEndEnabled && move < 0 && (mPullChecker.canPullFromEnd(this));
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL: {
                onTouchUp(event);
            }
            break;
        }

        return super.onTouchEvent(event);
    }

    private void onTouchMove(MotionEvent event) {

        mIsBeingDragged = true;

        abortScroller();

        int x = (int) event.getX();
        int y = (int) event.getY();

        int deltaX = x - mLastMotionX;
        int deltaY = y - mLastMotionY;

        mLastMotionX = x;
        mLastMotionY = y;

        int move;
        int delta = 0;
        if (isVertical()) {

            move = mCurMoveY;
            delta = deltaY;
        } else {
            move = mCurMoveX;
            delta = deltaX;
        }

        int limit = limitMove(move, delta);

        if (move == limit)
            return;

        move = limit;

        switch (mState) {
            case State.DEFAULT:
                if (move > 0) {
                    if (mPullFromStartEnabled) {
                        mState = State.PULL_TO_REFRESH_FROM_START;
                        postOnPullToRefresh();
                        updateMove(move);

                    }
                } else if (move < 0) {
                    if (mPullFromEndEnabled) {
                        mState = State.PULL_TO_REFRESH_FROM_END;
                        postOnPullToRefresh();
                        updateMove(move);
                    }
                }
                break;
            case State.PULL_TO_REFRESH_FROM_START: {
                if (move > getRefreshThresholdSize()) {
                    mState = State.RELEASE_TO_REFRESH_FROM_START;
                    postOnReleaseToRefresh();
                } else if (move < 0) {
                    move = 0;
                }

                updateMove(move);

            }
            break;
            case State.RELEASE_TO_REFRESH_FROM_START: {
                if (move > 0 && move < getRefreshThresholdSize()) {
                    mState = State.PULL_TO_REFRESH_FROM_START;
                    postOnPullToRefresh();
                } else if (move > getMaxMoveDistance()) {
                    move = getMaxMoveDistance();
                }
                updateMove(move);
            }
            break;
            case State.REFRESHING_FROM_START: {
                updateMove(move);

            }
            break;
            case State.PULL_TO_REFRESH_FROM_END: {
                if (move < -getRefreshThresholdSize()) {
                    mState = State.RELEASE_TO_REFRESH_FROM_END;
                    postOnReleaseToRefresh();
                } else if (move > 0) {
                    move = 0;
                }

                updateMove(move);
            }
            break;
            case State.RELEASE_TO_REFRESH_FROM_END: {
                if (move < 0 && move > -getRefreshThresholdSize()) {
                    mState = State.PULL_TO_REFRESH_FROM_END;
                    postOnPullToRefresh();
                } else if (move < -getMaxMoveDistance()) {
                    move = -getMaxMoveDistance();
                }
                updateMove(move);
            }
            break;
            case State.REFRESHING_FROM_END: {
                updateMove(move);
            }
            break;
        }

        if (!isRefreshing()) {

            int size = (State.PULL_TO_REFRESH_FROM_START == mState || State.RELEASE_TO_REFRESH_FROM_START == mState)
                    ? getHeaderSize() : getFooterSize();

            if (isVertical()) {
                postOnPull(this, deltaY, Math.abs(mCurMoveY) * 1.0f / size);
            } else {
                postOnPull(this, deltaX, Math.abs(mCurMoveX) * 1.0f / size);
            }
        }


    }

    int limitMove(int move, int delta) {
        float ratio = 1 - Math.abs(move * 1.0f / getMaxMoveDistance());

        delta = (int) (delta * ratio);

        if (0 == delta)
            return move;

        delta = Math.abs(delta) * Math.min(Math.abs(delta), mMaxMoveDelta) / delta;

        move += delta;

        return move;
    }

    void updateMove(int move) {
        if (isVertical()) {
            mCurMoveX = 0;
            mCurMoveY = move;
        } else {
            mCurMoveX = move;
            mCurMoveY = 0;
        }

        layoutChildren();
    }

    void onTouchUp(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        int deltaX = x - mLastMotionX;
        int deltaY = y - mLastMotionY;

        mLastMotionX = x;
        mLastMotionY = y;

        int moveX = x - mDownMotionX;
        int moveY = y - mDownMotionY;

        mCurMoveX += deltaX;
        mCurMoveY += deltaY;


        switch (mState) {
            case State.DEFAULT:
            case State.PULL_TO_REFRESH_FROM_START:
            case State.PULL_TO_REFRESH_FROM_END: {
                smoothScrollTo(0, 0);
            }
            break;
            case State.RELEASE_TO_REFRESH_FROM_START: {
                prepareRefreshFromStart();
            }
            break;
            case State.REFRESHING_FROM_START: {
                if (mHoldHeaderWhenRefreshing) {
                    smoothScrollToHeader();
                } else {

                    if (isVertical() && (mCurMoveY > getHeaderSize())) {
                        smoothScrollToHeader();
                    } else if (!isVertical() && (mCurMoveX > getHeaderSize())) {
                        smoothScrollToHeader();
                    } else {
                        smoothScrollTo(0, 0);
                    }

                }
            }
            break;
            case State.RELEASE_TO_REFRESH_FROM_END: {
                prepareRefreshFromEnd();
            }
            break;
            case State.REFRESHING_FROM_END: {
                if (mHoldHeaderWhenRefreshing) {
                    smoothScrollToFooter();
                } else {
                    if (isVertical() && (mCurMoveY < -getFooterSize())) {
                        smoothScrollToFooter();
                    } else if (!isVertical() && (mCurMoveX < -getFooterSize())) {
                        smoothScrollToFooter();
                    } else {
                        smoothScrollTo(0, 0);
                    }

                }
            }
            break;
        }
    }

    private void prepareRefreshFromStart() {
        mState = State.PREPARE_TO_REFRESH_FROM_START;
        postOnRefreshPrepared();

        if (mRefreshImmediately) {
            mState = State.REFRESHING_FROM_START;
            postOnRefreshStarted();
        }
        smoothScrollToHeader();
    }

    private void prepareRefreshFromEnd() {
        mState = State.PREPARE_TO_REFRESH_FROM_END;
        postOnRefreshPrepared();
        if (mRefreshImmediately) {
            mState = State.REFRESHING_FROM_END;
            postOnRefreshStarted();
        }
        smoothScrollToFooter();
    }

    private void smoothScrollTo(int x, int y) {
        abortScroller();

        if (x != mCurMoveX || y != mCurMoveY)
            mScroller.scrollTo(x, y);
    }

    void smoothScrollToHeader() {
        if (isVertical())
            smoothScrollTo(0, getHeaderSize());
        else
            smoothScrollTo(getHeaderSize(), 0);
    }

    void smoothScrollToFooter() {
        if (isVertical())
            smoothScrollTo(0, -getFooterSize());
        else
            smoothScrollTo(-getFooterSize(), 0);
    }

    void abortScroller() {
        mScroller.abort();
    }

    int getHeaderSize() {
        return getSize(mHeaderView);
    }

    int getRefreshHeaderSize() {
        return (int) (getHeaderSize() * mRatioToRefresh);
    }

    int getFooterSize() {
        return getSize(mFooterView);
    }

    int getRefreshFooterSize() {
        return (int) (getFooterSize() * mRatioToRefresh);
    }

    int getRefreshThresholdSize() {

        return isPullFromStart() ? getRefreshHeaderSize() : getRefreshFooterSize();
    }

    int getContentSize() {
        return getSize(mContentView);
    }

    int getSize(View view) {
        if (null == view)
            return 0;

        return isVertical() ? view.getMeasuredHeight() : view.getMeasuredWidth();
    }

    /**
     * min is 2 times of header or footer size
     *
     * @return
     */
    int getMaxMoveDistance() {
        return Math.max(0, Math.max(mMaxMoveDistance, 2 * (isPullFromStart() ? getHeaderSize() : getFooterSize())));
    }

    public boolean isVertical() {
        return VERTICAL == mOrientation;
    }

    public boolean isPullFromEnd() {
        return isStateIn(State.PULL_TO_REFRESH_FROM_END, State.RELEASE_TO_REFRESH_FROM_END, State.PREPARE_TO_REFRESH_FROM_END, State.REFRESHING_FROM_END);
    }

    public boolean isPullFromStart() {
        return isStateIn(State.PULL_TO_REFRESH_FROM_START, State.RELEASE_TO_REFRESH_FROM_START, State.PREPARE_TO_REFRESH_FROM_START, State.REFRESHING_FROM_START);
    }

    public boolean isRefreshing() {
        return State.REFRESHING_FROM_START == mState
                || State.REFRESHING_FROM_END == mState;
    }

    public void autoRefresh() {

        autoPullFromStart();
    }

    public void autoPullFromStart() {
        if (!isRefreshing())
            prepareRefreshFromStart();
    }

    public void autoPullFromEnd() {
        if (!isRefreshing())
            prepareRefreshFromEnd();
    }

    public int getOrientation() {
        return mOrientation;
    }

    public void setOrientation(int orientation) {
        mOrientation = orientation;
    }

    public IPtrPullChecker getPullChecker() {
        return mPullChecker;
    }

    public void setPullChecker(IPtrPullChecker pullChecker) {
        mPullChecker = pullChecker;
    }

    public IPtrOnRefreshListener getOnRefreshListener() {
        return mOnRefreshListener;
    }

    public void setOnRefreshListener(IPtrOnRefreshListener onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
    }

    public boolean isPullFromEndEnabled() {
        return mPullFromEndEnabled;
    }

    public void setPullFromEndEnabled(boolean pullFromEndEnabled) {
        mPullFromEndEnabled = pullFromEndEnabled;
    }

    public boolean isPullFromStartEnabled() {
        return mPullFromStartEnabled;
    }

    public void setPullFromStartEnabled(boolean pullFromStartEnabled) {
        mPullFromStartEnabled = pullFromStartEnabled;
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    public void setHeaderView(View headerView) {
        if (null != mHeaderView) {
            removeView(mHeaderView);
            mHeaderView = null;
        }

        if (null == headerView)
            return;

        addView(headerView, generateDefaultHeaderLayoutParams());
        mHeaderView = headerView;

    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View footerView) {

        if (null != mFooterView) {
            removeView(mFooterView);
            mFooterView = null;
        }

        if (null == footerView)
            return;


        addView(footerView, generateDefaultHeaderLayoutParams());
        mFooterView = footerView;

    }

    public View getContentView() {
        return mContentView;
    }

    public void setContentView(View content) {
        if (null != mContentView) {
            removeView(mContentView);
            mContentView = null;
        }
        if (null == content)
            return;
        addView(content);
        mContentView = content;
    }

    public boolean isHoldHeaderWhenRefreshing() {
        return mHoldHeaderWhenRefreshing;
    }

    public void setHoldHeaderWhenRefreshing(boolean holdHeaderWhenRefreshing) {
        mHoldHeaderWhenRefreshing = holdHeaderWhenRefreshing;
    }

    public boolean isDispatchTouchEventWhenRefreshing() {
        return mDispatchTouchEventWhenRefreshing;
    }

    public void setDispatchTouchEventWhenRefreshing(boolean dispatchTouchEventWhenRefreshing) {
        mDispatchTouchEventWhenRefreshing = dispatchTouchEventWhenRefreshing;
    }

    boolean isStateIn(int... states) {
        if (null == states || states.length == 0)
            return false;

        for (int state : states) {
            if (state == mState) {
                return true;
            }
        }

        return false;
    }


     void onPull(PtrLayout ptrLayout, int delta, float percent) {
        if (isPullFromStart() && mHeaderView instanceof IPtrHandler)
            ((IPtrHandler) mHeaderView).onPull(this, delta, percent);
        else if (isPullFromEnd() && mFooterView instanceof IPtrHandler)
            ((IPtrHandler) mFooterView).onPull(this, delta, percent);
    }

    void onReset() {
        if (mHeaderView instanceof IPtrHandler)
            ((IPtrHandler) mHeaderView).onReset();
         if ( mFooterView instanceof IPtrHandler)
            ((IPtrHandler) mFooterView).onReset();
    }

    void onPullToRefresh() {
        if (isPullFromStart() && mHeaderView instanceof IPtrHandler)
            ((IPtrHandler) mHeaderView).onPullToRefresh();
        else if (isPullFromEnd() && mFooterView instanceof IPtrHandler)
            ((IPtrHandler) mFooterView).onPullToRefresh();

    }

     void onReleaseToRefresh() {
        if (isPullFromStart() && mHeaderView instanceof IPtrHandler)
            ((IPtrHandler) mHeaderView).onReleaseToRefresh();
        else if (isPullFromEnd() && mFooterView instanceof IPtrHandler)
            ((IPtrHandler) mFooterView).onReleaseToRefresh();

    }

     void onRefreshPrepared() {
        if (isPullFromStart() && mHeaderView instanceof IPtrHandler)
            ((IPtrHandler) mHeaderView).onRefreshPrepared();
        else if (isPullFromEnd() && mFooterView instanceof IPtrHandler)
            ((IPtrHandler) mFooterView).onRefreshPrepared();
    }

    void onRefreshStarted() {

        recordRefreshTime();

        if (isPullFromStart() && mHeaderView instanceof IPtrHandler)
            ((IPtrHandler) mHeaderView).onRefreshStarted();
        else if (isPullFromEnd() && mFooterView instanceof IPtrHandler)
            ((IPtrHandler) mFooterView).onRefreshStarted();


        if (null != mOnRefreshListener) {
            if (isPullFromStart())
                mOnRefreshListener.onRefreshFromStart(this);
            else if (isPullFromEnd())
                mOnRefreshListener.onRefreshFromEnd(this);

        } else {
            postOnRefreshCompleted();
        }


    }

    void onRefreshCompleted() {
        if (isPullFromStart() && mHeaderView instanceof IPtrHandler)
            ((IPtrHandler) mHeaderView).onRefreshCompleted();
        else if (isPullFromEnd() && mFooterView instanceof IPtrHandler)
            ((IPtrHandler) mFooterView).onRefreshCompleted();

        smoothScrollTo(0, 0);
        mState = State.DEFAULT;
    }

    void postOnPull(final PtrLayout ptrLayout, final int delta, final float percent) {
        onPull(ptrLayout, delta, percent);
    }

    void postOnReset() {
        onReset();
    }

    void postOnPullToRefresh() {
        onPullToRefresh();
    }

    void postOnReleaseToRefresh() {
        onReleaseToRefresh();
    }

    void postOnRefreshPrepared() {
        onRefreshPrepared();
    }

    void postOnRefreshStarted() {
        onRefreshStarted();
    }

    void recordRefreshTime() {
        mRefreshStartedTime = SystemClock.elapsedRealtime();
    }

    public void postOnRefreshCompleted() {
        if (State.DEFAULT == mState)
            return;

        long delay = 0;
        if (mInsureMinimumRefreshDuration) {

            long duration = SystemClock.elapsedRealtime() - mRefreshStartedTime;
            if (duration < mMinimumRefreshDuration) {
                delay = mMinimumRefreshDuration - duration;
            }
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onRefreshCompleted();
            }
        }, delay);
    }



    void postOnScrollFinished() {
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                onScrollFinished();
//            }
//        });
        onScrollFinished();
    }

    void onScrollFinished() {
        LogUtils.e("onScrollFinished " + mState);
        if (isRefreshing()) {

        } else if (isStateIn(State.PREPARE_TO_REFRESH_FROM_START)) {
            mState = State.REFRESHING_FROM_START;
            postOnRefreshStarted();

        } else if (isStateIn(State.PREPARE_TO_REFRESH_FROM_END)) {
            mState = State.REFRESHING_FROM_END;
            postOnRefreshStarted();
        } else {
            mState = State.DEFAULT;
            postOnReset();
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        mScroller.abort();

        if (null != mVelocityTracker)
            mVelocityTracker.recycle();

        super.onDetachedFromWindow();
    }
}
