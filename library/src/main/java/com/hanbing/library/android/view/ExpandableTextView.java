package com.hanbing.library.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hanbing.library.android.R;
import com.hanbing.library.android.util.LogUtils;

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

    boolean mIsExpanded;

    int mExpandedHeight;
    int mCollapsedHeight;

    int mTextMaxLinesHeight;


    int mTextViewMarginBottom;

    boolean mNeedLayout = true;

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

        a.recycle();

        init();
    }

    private void init() {
        setOrientation(VERTICAL);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        LogUtils.e("onMeasure measureHeight = " + getMeasuredHeight());

        if (!mNeedLayout)
            return;

        mNeedLayout = false;


        //hide arrow first
        mExpandArrow.setVisibility(View.GONE);


        TextView textView = mTextView;

        //save current max lines
        int maxLines = TextViewCompat.getMaxLines(textView);

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

        mExpandArrow.setVisibility(View.VISIBLE);

        if (!mIsExpanded) {
            //if it is collapsed, set original max lines, and measure size
            textView.setMaxLines(mMaxLines);
        }

        //measure again
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //calculate margin bottom
        mTextViewMarginBottom = getMeasuredHeight() - mTextView.getMeasuredHeight();

        //set collapsed height
        mCollapsedHeight = getMeasuredHeight();
//
//// Setup with optimistic case
//        // i.e. Everything fits. No button needed
//        View mButton = mExpandArrow;
//        final TextView mTv = mTextView;
//
//        mButton.setVisibility(View.GONE);
//        mTv.setMaxLines(Integer.MAX_VALUE);
//
//        // Measure
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        // If the text fits in collapsed mode, we are done.
//        int mMaxCollapsedLines = mMaxLines;
//        if (mTv.getLineCount() <= mMaxCollapsedLines) {
//            return;
//        }
//
//        // Saves the text height w/ max lines
//        mTextMaxLinesHeight = getRealTextViewHeight(mTv);
//
//        boolean mCollapsed = !mIsExpanded;
//        // Doesn't fit in collapsed mode. Collapse text view as needed. Show
//        // button.
//        if (mCollapsed) {
//            mTv.setMaxLines(mMaxCollapsedLines);
//        }
//        mButton.setVisibility(View.VISIBLE);
//
//        // Re-measure with new setup
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        if (mCollapsed) {
//            // Gets the margin between the TextView's bottom and the ViewGroup's bottom
//            mTv.post(new Runnable() {
//                @Override
//                public void run() {
//                    mMarginBetweenTxtAndBottom = getHeight() - mTv.getHeight();
//                }
//            });
//            // Saves the collapsed height of this ViewGroup
//            mCollapsedHeight = getMeasuredHeight();
//        }


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
        if (0 != mTextViewId)
            mTextView = (TextView) findViewById(mTextViewId);

        if (0 != mExpandArrowId)
            mExpandArrow = findViewById(mExpandArrowId);

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
            mMaxLines = TextViewCompat.getMaxLines(mTextView);
            mTextView.removeTextChangedListener(this);
            mTextView.addTextChangedListener(this);
        }

        //set onclick listener
        if (null != mExpandArrow) {
            mExpandArrow.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mIsExpanded) {
                        collapse();
                    } else {
                        expand();
                    }
                }
            });
            //gone default
            mExpandArrow.setVisibility(GONE);
        }
    }

    private void expand() {
        mIsExpanded = true;
//        if (null != mTextView)
//        {
//            mTextView.setMaxLines(Integer.MAX_VALUE);
//        }

        startAnimation();
        postOnExpandStateChanged();
    }

    private void collapse() {
        mIsExpanded = false;
//        if (null != mTextView)
//        {
//            mTextView.setMaxLines(mMaxLines);
//        }

        startAnimation();

        postOnExpandStateChanged();
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


        if (mIsExpanded) {
            fromY = mCollapsedHeight;
            toY = mExpandedHeight;

            toY = getHeight() - mTextView.getHeight() + mTextMaxLinesHeight;

        } else {
            fromY = mExpandedHeight;
            toY = mCollapsedHeight;

            toY = mCollapsedHeight;
        }

        fromY = getHeight();


//        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
//                Animation.RELATIVE_TO_SELF, fromY, Animation.RELATIVE_TO_SELF, toY);
//        translateAnimation.setDuration(500);
//
//        startAnimation(translateAnimation);


//        ObjectAnimator objectAnimator = new ObjectAnimator();
//        objectAnimator = ObjectAnimator.ofInt(this, "height", (int)fromY, (int)toY);
//        objectAnimator.setDuration(500);
//
//        objectAnimator.start();

//        ExpandCollapseAnimation animation = new ExpandCollapseAnimation(this, fromY, toY);
//        animation.setFillAfter(true);
//        startAnimation(animation);
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
            mTextView.setMaxHeight(newHeight - mTextViewMarginBottom);
//            if (Float.compare(mAnimAlphaStart, 1.0f) != 0) {
//                applyAlphaAnimation(mTv, mAnimAlphaStart + interpolatedTime * (1.0f - mAnimAlphaStart));
//            }
            mTargetView.getLayoutParams().height = newHeight;
            mTargetView.requestLayout();
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


    private void registerObserver() {
        if (null != mTextView && null != mExpandArrow) {
            mMaxLines = TextViewCompat.getMaxLines(mTextView);

            mTextView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    int lineCount = mTextView.getLineCount();

                    mExpandArrow.setVisibility(lineCount > mMaxLines ? View.VISIBLE : View.GONE);
                    mTextView.getViewTreeObserver().removeOnPreDrawListener(this);
                    return false;
                }
            });
        }
    }

    public void setText(int resId) {
        setText(getResources().getString(resId));
    }

    public void setText(CharSequence text) {
        if (null != mTextView) mTextView.setText(text);
    }

    public TextView getTextView() {
        return mTextView;
    }

    public View getExpandArrow() {
        return mExpandArrow;
    }


    public void setOnExpandStateChangedListener(OnExpandStateChangedListener onExpandStateChangedListener) {
        mOnExpandStateChangedListener = onExpandStateChangedListener;
    }

    private void postOnExpandStateChanged() {

        if (null != mOnExpandStateChangedListener) {
            mOnExpandStateChangedListener.onChanged(mTextView, mIsExpanded);
        }
    }

    String mOldText = "";

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mTextView.setMaxLines(mMaxLines);
        mIsExpanded = false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

//        String newText = s.toString();
//
//        mNeedLayout = !mOldText.equals(newText);
//
//        mOldText = newText;

        mNeedLayout = true;
    }
}
