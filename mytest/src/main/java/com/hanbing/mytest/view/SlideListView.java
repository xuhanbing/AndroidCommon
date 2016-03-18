package com.hanbing.mytest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;

public class SlideListView extends ListView {

    public static interface SlideListAdapter {
	public int getMaxScrollX(View child);

    }

    /**
     * 默认状态，没有打开
     */
    static final int STATUS_DEFAULT = 0;

    /**
     * 当前正在展示menu
     */
    static final int STATUS_OPEN = 1;

    /**
     * 开始展示一个新的menu
     */
    static final int STATUS_NEW = 2;

    /**
     * 继续上一次的menu
     */
    static final int STATUS_CONTINUE = 3;

    

    int mStatus = STATUS_DEFAULT;

    int VELOCITY_X = 100;
    int VELOCITY_Y = 1000;
    /**
     * 快速滑动速度
     */
    int VELOCITY_FLING = 1000;

    Scroller mScroller = null;
    VelocityTracker mVelocityTracker = null;
    int mPosition = -1;
    View mLastChild = null;

    int mDownX = 0;
    int mLastX = 0;
    int mLastY = 0;

    boolean mIsTouchInMenu = false;

    boolean mIsMove = false;

    boolean mIsClosed = true;
    SlideListAdapter mAdapter = null;

    int mTouchSlop;

    public SlideListView(Context context) {
	super(context);
	// TODO Auto-generated constructor stub
	init();
    }

    public SlideListView(Context context, AttributeSet attrs) {
	super(context, attrs);
	// TODO Auto-generated constructor stub
	init();
    }

    public SlideListView(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	// TODO Auto-generated constructor stub
	init();
    }

    
    int mMaxYOverscrollDistance ;
    private void init() {
	mScroller = new Scroller(getContext());

	ViewConfiguration configuration = ViewConfiguration.get(getContext());
	mTouchSlop = configuration.getScaledTouchSlop();
	
	
    }
    

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
	// TODO Auto-generated method stub
	
	if (null == mAdapter)
	    return super.dispatchTouchEvent(ev);
	
	int x = (int) ev.getRawX();
	int y = (int) ev.getRawY();
	
	Log.e("", "dispatch touch " + ev.getAction() + ", status=" + mStatus + ",x=" + x);
	/**
	 * 如果上一次点击关闭了，以后的非down事件都直接返回
	 */
	if (mStatus == STATUS_DEFAULT
		&& ev.getAction() != MotionEvent.ACTION_DOWN)
	    return true;
	
	if (ev.getAction() == MotionEvent.ACTION_DOWN) {
	    
	    mLastX = x;
	    mLastY = y;
	    
	    if (!mScroller.isFinished())
		mScroller.abortAnimation();
	    
	    mIsMove = false;
	    mIsTouchInMenu = false;
	    
	    int position = pointToPosition((int) ev.getX(), (int) ev.getY());
	    View child = getChildAt(position - getFirstVisiblePosition());
	    
	    /**
	     * 已经在展示
	     */
	    if (STATUS_OPEN == mStatus) {
		
		/**
		 * 不是同一个，关闭menu
		 */
		if (position != mPosition) {
		    
		    Log.e("", "not same position cur=" + position + ",last="
			    + mPosition);
		    close();
		    mStatus = STATUS_DEFAULT;
		    
		    
		    return true;
		} else {
		    
		    
		    
		    int scrollX = child.getScrollX();
		    mIsTouchInMenu = x > getWidth() - scrollX;
		    /**
		     * 继续操作当前展示menu
		     */
		    mStatus = STATUS_CONTINUE;
		    if (!mIsTouchInMenu)
			return true;
		}
	    } else
		mStatus = STATUS_NEW;
	    
	    mPosition = position;
	    mLastChild = child;
	    
	    mDownX = x;
	    
	} else if (ev.getAction() == MotionEvent.ACTION_UP) {
	    if (mStatus == STATUS_CONTINUE || mStatus == STATUS_NEW) {
		
//		/**
//		 * 如果产生移动，或者没有点击在menu上，返回true
//		 */
//		if (mIsMove) {
//		    onTouchEvent(ev);
//		    return true;
//		} else if (!mIsTouchInMenu) {
//		    onTouchEvent(ev);
//		    return true;
//		}
	    }
	    
	}
	
	return  super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
	// TODO Auto-generated method stub
	
	if (null == mAdapter)
	    return super.onTouchEvent(ev);

	if (null == mVelocityTracker)
	    mVelocityTracker = VelocityTracker.obtain();

	mVelocityTracker.addMovement(ev);

	int x = (int) ev.getRawX();
	int y = (int) ev.getRawY();
	switch (ev.getAction()) {
	case MotionEvent.ACTION_DOWN: {
	}
	    break;
	case MotionEvent.ACTION_MOVE: {
	    if (STATUS_DEFAULT != mStatus) {
		mVelocityTracker.computeCurrentVelocity(1000);

		int dx = mLastX - x;
		int dy = mLastY - y;

		Log.e("", "mTouchSlop=" + mTouchSlop + ",dx=" + dx + ",mLastX="
			+ mLastX + ",x=" + x);

		mLastX = x;
		mLastY = y;

//		if (Math.abs(dx) < mTouchSlop) {
//		    mLastX = x;
//		    mLastY = y;
//		    return true;
//		}

		float vx = mVelocityTracker.getXVelocity();
		float vy = mVelocityTracker.getYVelocity();

		Log.e("", "move vx=" + vx + ",vy=" + vy);
		if ((Math.abs(vx) > Math.abs(vy) && Math.abs(vx) > VELOCITY_X)
			|| mIsMove) {
		    mIsMove = true;

		    scrollCurrentChild(dx);

		    return true;
		}

		if (STATUS_CONTINUE == mStatus)
		    return true;
	    }

	    mLastX = x;
	    mLastY = y;
	}
	    break;
	case MotionEvent.ACTION_CANCEL:
	    mStatus = STATUS_DEFAULT;
	    reset();
	    break;
	case MotionEvent.ACTION_UP:

	    Log.e("", "up mStatus=" + mStatus);
	    if (STATUS_DEFAULT == mStatus) {

		/**
		 * 关闭了展示的menu，直接返回true
		 */
		return true;

	    } else if (STATUS_NEW == mStatus || STATUS_CONTINUE == mStatus) {

		/**
		 * 没有滑动，只是点击，关闭
		 */
		if (!mIsMove) {

		    /**
		     * 当前展示了menu，只是点击并没有滑动，关闭，返回true
		     */
		    if (STATUS_CONTINUE == mStatus) {

			close();
			mStatus = STATUS_DEFAULT;

			return true;

		    } else {
			/**
			 * 没有任何操作，传递到上层
			 */
			mStatus = STATUS_DEFAULT;
		    }

		} else {
		    if (null != mLastChild) {

			mVelocityTracker.computeCurrentVelocity(1000);

			int sx = mLastChild.getScrollX();
			int maxScrollX = 0;

			maxScrollX = mAdapter.getMaxScrollX(mLastChild);

			int startX = sx;
			int dx = 0;

			mVelocityTracker.computeCurrentVelocity(1000);

			float vx = mVelocityTracker.getXVelocity();

			if (vx < -VELOCITY_FLING) {
			    /**
			     * 快速向左滑，打开
			     */
			    dx = maxScrollX - sx;
			    mStatus = STATUS_OPEN;

			} else if (vx > VELOCITY_FLING) {
			    /**
			     * 快速向右滑，关闭
			     */
			    dx = 0 - sx;
			    mStatus = STATUS_DEFAULT;
			} else {
			    /**
			     * 如果超过一般，打开，否则关闭
			     */
			    if (sx > maxScrollX * 1 / 2) {
				dx = maxScrollX - startX;

				mStatus = STATUS_OPEN;
			    } else {
				dx = 0 - startX;
				mStatus = STATUS_DEFAULT;
			    }
			}

			if (Math.abs(dx) > 0) {
			    mScroller.startScroll(startX, 0, dx, 0);
			    postInvalidate();

			}

			return true;

		    }
		}
	    }

	    break;
	}
	
	
	return super.onTouchEvent(ev);
    }

    /**
     * @param dx
     */
    protected void scrollCurrentChild(int dx) {
	if (null != mLastChild) {

	dx = (int) (dx * 0.75);

	Log.e("", "dx1=" + dx);
	int sx = mLastChild.getScrollX() + dx;

	int maxScrollX = 0;

	maxScrollX = mAdapter.getMaxScrollX(mLastChild);

	sx = Math.max(0, Math.min(sx, maxScrollX));
	dx = sx - mLastChild.getScrollX();

	Log.e("", "dx2=" + dx + ",sx=" + (mLastChild.getScrollX() + dx));
	
	if (Math.abs(dx) > 0) {

	    mLastChild.scrollBy(dx, 0);
	    postInvalidate();
	}
	}
    }


    public void closeMenu() {
	close();
    }

    private void close() {
	if (null != mLastChild) {
	    mScroller.startScroll(mLastChild.getScrollX(), 0,
		    -mLastChild.getScrollX(), 0);
	    postInvalidate();
	}
    }

    @Override
    public void computeScroll() {
	// TODO Auto-generated method stub
	if (mScroller.computeScrollOffset()) {
	    if (null != mLastChild) {
		mLastChild.scrollTo(mScroller.getCurrX(), 0);

		postInvalidate();
	    }
	}
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
	// TODO Auto-generated method stub
	Log.e("", "onLayout");
	super.onLayout(changed, l, t, r, b);
    }

    public void reset() {
	for (int i = 0; i < getLastVisiblePosition()
		- getFirstVisiblePosition(); i++) {
	    View child = getChildAt(i);
	    child.scrollTo(0, 0);

	}
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
	// TODO Auto-generated method stub
	if (adapter instanceof SlideListAdapter) {
	    mAdapter = (SlideListAdapter) adapter;
	}
	super.setAdapter(adapter);
    }
}
