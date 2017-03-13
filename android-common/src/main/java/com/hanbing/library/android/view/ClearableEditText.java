package com.hanbing.library.android.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;


/**
 * A EditText with clear icon.
 */
public class ClearableEditText extends DrawableEditText implements View.OnFocusChangeListener{



    public static interface OnClearListener {

        /**
         * Clear
         * @param content Current text
         * @deprecated
         */
        public void onClear(String content);

        /***
         * Before clear action.
         */
        public void beforeClear(EditText editText);

        /**
         * After clear action.
         */
        public void afterClear(EditText editText);
    }

    boolean mAlwaysShow = false;

    //Should place holder or not when it is empty
    boolean mPlaceHolder = false;

    Drawable mClearDrawable;

    Drawable mPlaceHolderDrawable;
    
    int mDftClearDrawableResId = android.R.drawable.ic_menu_close_clear_cancel;

    OnClearListener mOnClearListener;

    OnFocusChangeListener mOnFocusChangeListener;
    /**
     * @param context
     */
    public ClearableEditText(Context context) {
        super(context);
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * init
     */
    private void init()
    {
        mPlaceHolderDrawable = new ColorDrawable(Color.TRANSPARENT);
        mClearDrawable = this.getCompoundDrawables()[2];

        //set clear icon default
        if (null == mClearDrawable)
        {
            setClearImageResource(mDftClearDrawableResId);
        }

        setOnFocusChangeListener(null);

        this.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

                showClear();
            }

        });

        showClear(false);
    }


    boolean mIsTouchClear = false;
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //check if touch clear icon
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isTouchClear(event)) {
                    mIsTouchClear = true;
                    onTouchClear();
                    return true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (mIsTouchClear)
                    return true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mIsTouchClear)
                {
                    mIsTouchClear = false;
                    return true;
                }

                break;
        }

        return super.dispatchTouchEvent(event);
    }

    private void onTouchClear() {
        if (!isEnabled())
            return;

        if (isInEditMode()) {

        }

        if (null != mOnClearListener){
            mOnClearListener.beforeClear(this);
            mOnClearListener.onClear(getText().toString());
        }
        this.setText("");

        if (null != mOnClearListener)
            mOnClearListener.afterClear(this);
    }

    /**
     * check if touch clear icon
     * @param event
     * @return
     */
    protected boolean isTouchClear(MotionEvent event)
    {
        //No focus, return false
        if (!hasFocus())
            return false;
        
        int x = (int) event.getX();
        int y = (int) event.getY();

        //Set minimun size as 40px
        int left = this.getWidth() - Math.max(getTotalPaddingRight(), 40);
        int right = this.getWidth();
        if (x >= left
                && x <= right)
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * set clear icon
     * @param resId
     */
    public void setClearImageResource(int resId)
    {
        setClearImageDrawable(getResources().getDrawable(resId));
    }
    
    /**
     *  set clear icon
     * @param d
     */
    public void setClearImageDrawable(Drawable d)
    {
        mClearDrawable = d;
        showClear(mAlwaysShow);
    }

    public void setAlwaysShow(boolean alwaysShow)
    {
        this.mAlwaysShow = alwaysShow;


    }

    private void showClear() {
        showClear(isFocused() && getText().toString().length() > 0);
    }
    /**
     *  set clear icon
     * @param show
     */
    protected void showClear(boolean show)
    {
        Drawable right = null;

        //If enabled = false, clear icon will not show
        if ((mAlwaysShow || show) && isEnabled()) {
            right = mClearDrawable;
        } else if (mPlaceHolder) {
            right = mPlaceHolderDrawable;
        } else {
            right = null;
        }

        this.setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1],
                right,
                getCompoundDrawables()[3]);

    }


    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        mOnFocusChangeListener = onFocusChangeListener;
        super.setOnFocusChangeListener(this);
    }

    public void setOnClearListener(OnClearListener listener)
    {
        this.mOnClearListener = listener;
    }

    public void onClear() {

    }

    public boolean isAlwaysShow() {
        return mAlwaysShow;
    }

    public boolean isPlaceHolder() {
        return mPlaceHolder;
    }

    public void setPlaceHolder(boolean placeHolder) {
        mPlaceHolder = placeHolder;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        showClear();
        if (null != mOnFocusChangeListener) mOnFocusChangeListener.onFocusChange(v, hasFocus);
    }

}
