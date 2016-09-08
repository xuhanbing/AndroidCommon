package com.hanbing.library.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanbing.library.android.R;

/**
 * Created by hanbing on 2016/9/7
 */
public class ExpandableTextView extends LinearLayout implements TextWatcher {


    public interface OnExpandStateChangedListener {
        public void onChanged(TextView textView, boolean isExpanded);
    }


    int mTextViewId;

    int mExpandArrowId;

    TextView mTextView;

    View mExpandArrow;

    //original max lines
    int mMaxLines;

    boolean mExpanded;

    int mExpandedHeight;
    int mCollapsedHeight;

    int mTextMaxLinesHeight;


    boolean mAnimationEnabled = false;

    int mTextViewMarginBottom;

    boolean mRelayout = true;

    OnExpandStateChangedListener mOnExpandStateChangedListener;

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView, defStyleAttr, 0);

        mTextViewId = a.getResourceId(R.styleable.ExpandableTextView_expandTextViewId, 0);
        mExpandArrowId = a.getResourceId(R.styleable.ExpandableTextView_expandArrowId, 0);
        mAnimationEnabled = a.getBoolean(R.styleable.ExpandableTextView_expandAnimationEnabled, false);
        a.recycle();

        init();
    }

    private void init() {
        setOrientation(VERTICAL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!mRelayout || getVisibility() == View.GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        mRelayout = false;

        final TextView textView = mTextView;

        //hide arrow first
        mExpandArrow.setVisibility(View.GONE);

        //set max lines
        textView.setMaxLines(Integer.MAX_VALUE);

        //measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // lines is less than max lines, return
        if (textView.getLineCount() <= mMaxLines) {
            return;
        }

        //get textview height
        mTextMaxLinesHeight = getRealTextViewHeight(textView);


        if (!mExpanded) {
            //if it is collapsed, set original max lines, and measure size
            textView.setMaxLines(mMaxLines);
        }

        mExpandArrow.setVisibility(View.VISIBLE);

        //measure again
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //calculate margin bottom
        mTextViewMarginBottom = getMeasuredHeight() - mTextView.getMeasuredHeight();

        //set collapsed height
        mCollapsedHeight = getMeasuredHeight();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        findViews();
    }

//    @Override
//    public void addView(View child, int index, ViewGroup.LayoutParams params) {
//        super.addView(child, index, params);
//
//        findViews();
//    }

    @Override
    public void setOrientation(int orientation) {
        if (VERTICAL != orientation)
            throw new IllegalArgumentException("ExpandableTextView only support vertical orientation.");
        super.setOrientation(orientation);
    }

    protected void findViews() {

        //clear views
        mTextView = null;
        mExpandArrow = null;

        // find views by id
        {
            if (0 != mTextViewId)
                mTextView = (TextView) findViewById(mTextViewId);

            if (0 != mExpandArrowId)
                mExpandArrow = findViewById(mExpandArrowId);
        }

        int childCount = getChildCount();

        //do not find TextView by id, we find first TextView as mTextView
        if (null == mTextView) {
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);

                if (child instanceof TextView) {
                    mTextView = (TextView) child;
                    break;
                }
            }
        }

        //do not find arrow view by id, we find first view but not mTextView as mExpandArrow
        if (null == mExpandArrow) {
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);

                if (child instanceof View && child != mTextView) {
                    mExpandArrow = child;
                    break;
                }
            }
        }

        //register text watcher
        if (null != mTextView) {
            //save max lines
            mMaxLines = TextViewCompat.getMaxLines(mTextView);
            //add text changed listener
            mTextView.removeTextChangedListener(this);
            mTextView.addTextChangedListener(this);
        }

        //set onclick listener
        if (null != mExpandArrow) {
            mExpandArrow.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    mExpanded = !mExpanded;
                    startAnimation();
                    postOnExpandStateChanged();

                }
            });
            //gone default
            mExpandArrow.setVisibility(GONE);
        }
    }

    private static int getRealTextViewHeight(@NonNull TextView textView) {
        int textHeight = textView.getLayout().getLineTop(textView.getLineCount());
        int padding = textView.getCompoundPaddingTop() + textView.getCompoundPaddingBottom();
        return textHeight + padding;
    }

    private void startAnimation() {

        clearAnimation();

        int fromY = 0;
        int toY = 0;


        if (mExpanded) {
            toY = getHeight() - mTextView.getHeight() + mTextMaxLinesHeight;
        } else {
            toY = mCollapsedHeight;
        }

        fromY = getHeight();

        if (mAnimationEnabled) {

            ExpandCollapseAnimation animation = new ExpandCollapseAnimation(this, fromY, toY);
            animation.setFillAfter(true);
            startAnimation(animation);
        } else {
            setViewHeight(toY);
        }

    }

    /**
     * change view height
     * @param newHeight
     */
    private void setViewHeight(int newHeight)
    {
        mTextView.setMaxHeight(newHeight - mTextViewMarginBottom);
        getLayoutParams().height = newHeight;
        requestLayout();
    }

    class ExpandCollapseAnimation extends Animation {
        private final View mTargetView;
        private final int mStartHeight;
        private final int mEndHeight;

        public ExpandCollapseAnimation(View view, int startHeight, int endHeight) {
            mTargetView = view;
            mStartHeight = startHeight;
            mEndHeight = endHeight;
            setDuration(500);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final int newHeight = (int) ((mEndHeight - mStartHeight) * interpolatedTime + mStartHeight);
            setViewHeight(newHeight);
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }


    public void setText(int resId) {
        setText(getResources().getString(resId));
    }

    public void setText(CharSequence text) {
        if (null != mTextView) mTextView.setText(text);
    }

    public CharSequence getText(){
        if (null != mTextView) return mTextView.getText();
        return "";
    }

    public TextView getTextView() {
        return mTextView;
    }

    public View getExpandArrow() {
        return mExpandArrow;
    }

    public void setAnimationEnabled(boolean animationEnabled) {
        if (mAnimationEnabled == animationEnabled)
            return;
        if (!animationEnabled)
            clearAnimation();

        mAnimationEnabled = animationEnabled;
    }

    public void setOnExpandStateChangedListener(OnExpandStateChangedListener onExpandStateChangedListener) {
        mOnExpandStateChangedListener = onExpandStateChangedListener;
    }

    private void postOnExpandStateChanged() {

        if (null != mOnExpandStateChangedListener) {
            mOnExpandStateChangedListener.onChanged(mTextView, mExpanded);
        }
    }

    String mOldText = "";

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mOldText = mTextView.getText().toString();

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String newText = s.toString();

        if (!mOldText.equals(newText)) {
            clearAnimation();
            mExpanded = false;
            mRelayout = true;
            requestLayout();
        }

    }
}
