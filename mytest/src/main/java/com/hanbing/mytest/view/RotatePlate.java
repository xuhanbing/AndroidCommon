package com.hanbing.mytest.view;

import android.content.Context;
import android.database.DatabaseUtils;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.common.util.LogUtils;
import com.common.util.ViewUtils;
import com.hanbing.mytest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanbing
 */
public class RotatePlate extends ViewGroup implements ViewPager.OnPageChangeListener{




    public interface OnRotateChangeListener {
        public void onSelected(int position);

        /**
         * 顶点的位置即选中的位置，中间的上部
         * @param leftPosition   顶点左侧的item的索引
         * @param rightPosition  顶点右侧的item的索引
         * @param rotateAngle    转过的角度
         * @param percent  左侧的item的当前百分比，0:left选中，1：right选中（类似ViewPager）
         */
        public void onRotate(int leftPosition, int rightPosition, int rotateAngle, float percent);
    }




    private static final int MAX_ANGLE_EVERY_ROTATE = 20;

    /**
     */
    int mAngle = 0;

    int mAngleAverage = 360;

    int mItemCount = 0;

    /**
     */
    int mRadius;

    /**
     */
    PointF mCenter;

    PointF mLastPoint = new PointF();
    PointF mMotionPoint = new PointF();
    PointF mDownPoint = new PointF();

    boolean mDownInsideItem = false;

    RotateScroller mScroller;


    VelocityTracker mVelocityTracker;

    int mMaxFlingVelocity;
    int mMinFlingVelocity;

    int mTouchSlop;

    float mOriginalScale = 0.5f;


    ViewPager mViewPager;

    OnRotateChangeListener mOnRotateChangeListener;


    OnRotateChangeListener mDefaultOnRotateChangeListener = new OnRotateChangeListener() {
        @Override
        public void onSelected(int position) {

        }

        @Override
        public void onRotate(int leftPosition, int rightPosition, int rotateAngle, float percent) {
            float scaleLeft = mOriginalScale + (1 - percent) * (1 - mOriginalScale);
            float scaleRight = mOriginalScale + percent * (1 - mOriginalScale);

            View childLeft = getChildAt(leftPosition);
            View childRight = getChildAt(rightPosition);


            ViewUtils.setScale(childLeft, scaleLeft, scaleLeft);
            ViewUtils.setScale(childRight, scaleRight, scaleRight);
        }
    };

    public void setOnRotateChangeListener(OnRotateChangeListener onRotateChangeListener) {
        this.mOnRotateChangeListener = onRotateChangeListener;
    }

    public RotatePlate(Context context) {
        super(context);

        init();
    }

    public RotatePlate(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RotatePlate(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    void init() {

        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        mMinFlingVelocity = configuration.getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = configuration.getScaledMaximumDrawingCacheSize();
        mTouchSlop = configuration.getScaledTouchSlop();

        setOnRotateChangeListener(mDefaultOnRotateChangeListener);
    }

    public void setupWithViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        if (null != viewPager) setupWithPagerAdapter(viewPager.getAdapter());
    }
    private void setupWithPagerAdapter(PagerAdapter adapter)
    {

        if (null == adapter || adapter.getCount() == 0)
            return;

        for (int i = 0; i < adapter.getCount(); i++) {

            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_turnplate, this, false);
            TextView text = (TextView) view.findViewById(R.id.tv_turnplate_info);
            text.setText(adapter.getPageTitle(i));

            ImageView image = (ImageView) view.findViewById(R.id.tv_turnplate_icon);
            image.setImageResource(R.drawable.p1);

            final  int index = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    scrollTo(index);
                }
            });

            int[] colors = {Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE};
            view.setBackgroundColor(colors[i % colors.length]);

            if (0 == i)
                ViewUtils.setScale(view, 1, 1);
            else
                ViewUtils.setScale(view, mOriginalScale, mOriginalScale);

            addView(view);
        }


        mItemCount = adapter.getCount();
        mAngleAverage = 360 / mItemCount;
    }

    int getItemCount() {
        return mItemCount;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int measureHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

        int size = Math.min(measureWidth, measureHeight);
        setMeasuredDimension(size, size);
        widthMeasureSpec = heightMeasureSpec = MeasureSpec.makeMeasureSpec(size / 3, MeasureSpec.EXACTLY);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        LogUtils.e("dispatchTouchEvent " + event);

        if (null != mScroller && !mScroller.isFinished()) {
            mScroller.forceFinished(true);
        }

        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }

        float x = event.getX();
        float y = event.getY();

        mVelocityTracker.addMovement(event);

        mMotionPoint.set(x, y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            {
                mLastPoint.set(x, y);
                mDownPoint.set(x, y);

                mDownInsideItem = false;

                int index = touchChildIndex(event);
                if (index >= 0) {
                    mDownInsideItem = true;
                }
            }

                break;
            case MotionEvent.ACTION_MOVE:
            {

                if (calcDistance(mLastPoint, mMotionPoint) > mTouchSlop
                        || calcDistance(mDownPoint, mMotionPoint) > mTouchSlop) {
                    mDownInsideItem = false;
                }

                int angle = calcAngle();
                mLastPoint.set(x, y);

                if (Math.abs(angle) > MAX_ANGLE_EVERY_ROTATE) {
                    angle = MAX_ANGLE_EVERY_ROTATE * angle / Math.abs(angle);
                }



                rotateBy(angle);
                postInvalidate();
            }

                break;
            case MotionEvent.ACTION_UP:

            {
                mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);

                float xVelocity = mVelocityTracker.getXVelocity();
                float yVelocity = mVelocityTracker.getYVelocity();

                int angle = mAngle % mAngleAverage;

                if (Math.abs(xVelocity) > mMinFlingVelocity || Math.abs(yVelocity) > mMinFlingVelocity) {

                    boolean isClockwise = isClockwise(mDownPoint, mMotionPoint);

                    if (isClockwise) {
                        angle = mAngleAverage - angle;
                    } else {
                        angle = -angle;
                    }
                } else {
                    if (angle > mAngleAverage / 2) {
                        angle = mAngleAverage - angle;
                    } else {
                        angle = -angle;
                    }
                }

                if (mDownInsideItem) {
                    int index = touchChildIndex(event);
                    if (index >= 0) {
                        getChildAt(index).performClick();
                    }
                } else
                {
                    LogUtils.e("auto rotate start from up");
                    autoRotate(angle);
                }
            }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

//        return super.dispatchTouchEvent(event);

        return true;
    }

    public boolean dispatchTouchEventLocal(MotionEvent event) {

        event = MotionEvent.obtain(event);

        int[] location = new int[2];

        getLocationInWindow(location);

        float x = event.getRawX();
        float y = event.getRawY();

        PointF touch = new PointF(x, y);
        PointF center = new PointF(location[0] + getMeasuredWidth() / 2, location[1] + getMeasuredHeight() / 2);

        int radius = Math.min(getMeasuredWidth(), getMeasuredHeight()) / 2;

        if (calcDistance(touch, center) > radius)
            return false;

        x -= location[0];
        y -= location[1];

        event.setLocation(x, y);

        return dispatchTouchEvent(event);

    }


    private int touchChildIndex(MotionEvent ev) {
        int childCount = getChildCount();
        if (childCount > 0)
        {
            int angle = mAngleAverage;

            for (int i = 0; i < childCount; i++) {

                View child =getChildAt(i);

                PointF center = getCenterPoint(mAngle + angle * i);

                PointF touch = new PointF(ev.getX(), ev.getY());

                if (calcDistance(center, touch) < Math.min(child.getMeasuredWidth(), child.getMeasuredHeight()) / 2) {
                    return i;
                }

            }
        }

        return -1;
    }

    void autoRotate(final int angle) {
        autoRotate(angle, true);
    }
    void autoRotate(final int angle, boolean fromInner) {

        if (null == mScroller) {
            mScroller = new RotateScroller(getContext());
        }

        if (!mScroller.isFinished())
        {
            mScroller.forceFinished(true);
        }

        onSelected(getSelectedItem(mAngle + angle), fromInner);

        mScroller.startScroll(mAngle, 0, angle, 0, 1000 * (1 + Math.abs(angle) / mAngleAverage));

    }

    private int getSelectedItem(int angle) {
        return checkAngle(360 - angle) / mAngleAverage;
    }

    void scrollTo(int position) {
        scrollTo(position, true);
    }

    void scrollTo(int position, boolean fromInner) {

        int angle = position * mAngleAverage;

        angle = (mAngle + angle) % 360;

        if (angle < 180) {
            angle = - angle;
        } else {
            angle = 360 - angle;
        }
        autoRotate(angle, fromInner);
    }

    /**
     * @param deltaAngle
     */
    void rotateBy(final int deltaAngle) {
        rotateBy(deltaAngle, true);
    }

    private void rotateBy(final int deltaAngle, boolean fromInner)
    {
        if (0 == checkAngle(deltaAngle))
            return;

        mAngle = checkAngle(mAngle + deltaAngle);

        onRotate(deltaAngle, fromInner);

        update();
    }

    void rotateTo(final int angle) {
        rotateTo(angle, true);
    }
    void rotateTo(final int angle, boolean fromInner) {

        if (checkAngle(angle) == checkAngle(mAngle))
            return;

        int deltaAngle = angle - mAngle;

        mAngle = checkAngle(angle);

        onRotate(deltaAngle, fromInner);

        update();
    }

    void update() {
        layoutItems();
    }


    /**
     * 利用余弦定理计算出需要旋转的角度
     * @return
     */
    private int calcAngle()
    {
        double angle = 0.0f;

        double sideA = calcDistance(mLastPoint, mCenter);
        double sideB = calcDistance(mMotionPoint, mCenter);
        double sideC = calcDistance(mLastPoint, mMotionPoint);


        angle =  Math.acos((sideA * sideA + sideB * sideB - sideC * sideC)
                / (2 * sideA * sideB));

        angle = Math.toDegrees(angle);


        angle *= isClockwise(mLastPoint, mMotionPoint) ? 1.0 : -1.0;

        return (int)angle;
    }

    /**
     * 判断是否是顺时针
     * @return
     */
    private boolean isClockwise(PointF lastPoint, PointF curPoint)
    {
        boolean isCw = true;

        PointF last = transform2Zero(lastPoint);
        PointF cur = transform2Zero(curPoint);

        //y = kx + b
        float k = (last.y - cur.y) / (last.x - cur.x);
        float b = last.y - k * last.x;

        if (k > 0) {

            if (lastPoint.y > curPoint.y) {
                isCw = b > 0;
            } else {
                isCw = b < 0;
            }

        } else {

            if (lastPoint.y < curPoint.y) {
                isCw = b > 0;
            } else {
                isCw = b < 0;
            }

        }

        return isCw;
    }

    /**
     * 计算两点距离
     * @param p0
     * @param p1
     * @return
     */
    private double calcDistance(PointF p0, PointF p1)
    {
        double disX = p0.x - p1.x;
        double disY = p0.y - p1.y;

        return Math.sqrt(disX * disX + disY * disY);
    }


    /**
     * 转换以center为原点的坐标系
     * @param point
     * @return
     */
    private PointF transform2Zero(PointF point)
    {
        PointF p = new PointF(point.x - mCenter.x,
                -(point.y - mCenter.y));

        return p;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        layoutItems();
    }

    void layoutItems() {
        int childCount = getChildCount();

        mCenter = new PointF(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        mRadius = Math.min(getMeasuredWidth() - getPaddingLeft() - getPaddingRight(), getMeasuredHeight()  - getPaddingTop() - getPaddingBottom()) / 2 - getChildAt(0).getMeasuredHeight() / 2;

        float angle = mAngleAverage;
        for (int i = 0; i < childCount; i++) {

            PointF center = getCenterPoint(mAngle + i * angle);

            View child = getChildAt(i);

            int left = (int) (center.x - child.getMeasuredWidth() / 2);
            int right = (int) (center.x + child.getMeasuredWidth() / 2);
            int top = (int) (center.y - child.getMeasuredHeight() / 2);
            int bottom = (int) (center.y + child.getMeasuredHeight() / 2);

            ViewCompat.setRotation(child, mAngle + i * angle);

            child.layout(left, top, right, bottom);

        }
    }


    /**
     * 计算出item的中心位置
     * @param angle
     * @return
     */
    private PointF getCenterPoint(double angle)
    {
        PointF point = new PointF();

        double redian = Math.toRadians(angle);
        point.x = (int) ((mRadius * Math.sin(redian)) + mCenter.x);
        point.y = (int) (- (mRadius * Math.cos(redian)) + mCenter.y);

        return point;
    }

    /**
     * [0, 360)
     * @param angle
     * @return
     */
    int checkAngle(int angle) {
        return (angle + 360) % 360;
    }

    void onRotate(int deltaAngle) {
        onRotate(deltaAngle, true);
    }
    void onRotate(int deltaAngle, boolean fromInner) {

        deltaAngle = checkAngle(deltaAngle);
        if (0 == deltaAngle)
            return;

        int left = 0;
        int right = 0;

        int angle = checkAngle(mAngle);

        angle = 360 - angle;

        int angleLeft = angle % mAngleAverage;

        float scale = angleLeft * 1.0f / mAngleAverage;

        left = angle / mAngleAverage;
        left %= getItemCount();

        if (0 == angleLeft) {
            right = left;
            scale = 1;
        } else {
            right = (left + 1) % getItemCount();
        }




//        if (null != mViewPager) mViewPager.scrollBy((int) (mViewPager.getWidth() * percent), 0);

        if (null != mOnRotateChangeListener) mOnRotateChangeListener.onRotate(left, right, deltaAngle, scale);
    }

    void onSelected(int position) {
        onSelected(position, true);
    }
    void onSelected(int position, boolean fromInner) {

        if (fromInner)
        {
            if (null != mViewPager) mViewPager.setCurrentItem(position);
        }

        if (null != mOnRotateChangeListener) mOnRotateChangeListener.onSelected(position);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//        if (true) {
//            float percent = positionOffset;
//            int angle = -(int) (mAngleAverage * position + mAngleAverage * percent);
//            rotateTo(angle, false);
//        }
    }

    @Override
    public void onPageSelected(int position) {
        scrollTo(position, false);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class RotateScroller extends Scroller {

        Handler mHandler = new Handler();

        int mUpdateDelay = 10;

        Runnable mUpdateRunnable = new Runnable() {
            @Override
            public void run() {

                if (isFinished() || !computeScrollOffset()) {
                    mHandler.removeCallbacks(this);
                } else {
                    int angle = getCurrX();
                    rotateTo(angle);

                    mHandler.postDelayed(this, mUpdateDelay);
                }
            }
        };
        public RotateScroller(Context context) {
            super(context);
        }

        public RotateScroller(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        public RotateScroller(Context context, Interpolator interpolator, boolean flywheel) {
            super(context, interpolator, flywheel);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, duration);
            mHandler.postDelayed(mUpdateRunnable, mUpdateDelay);
        }


    }
}
