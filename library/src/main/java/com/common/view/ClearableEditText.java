package com.common.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;


/**
 * 带清空的edit
 */
public class ClearableEditText extends EditText{


    public static interface OnClearListener {
        public void onClear(String content);
    }


    boolean mAlwaysShow = false;

    Drawable mClearDrawable;
    
    int mDftClearDrawableResId = android.R.drawable.ic_delete;

    OnClearListener mOnClearListener;
    /**
     * @param context
     */
    public ClearableEditText(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * init
     */
    private void init()
    {
        mClearDrawable = this.getCompoundDrawables()[2];
        
        //set clear icon default
        if (null == mClearDrawable)
        {
            setClearImageResource(mDftClearDrawableResId);
        }

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

                showClear(null != s && s.length() > 0);
            }

        });
    }
    
    
    





    
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        //check if touch clear icon
        if (MotionEvent.ACTION_DOWN == event.getAction()
            && isTouchClear(event))
        {
            if (null != mOnClearListener)
            {
                mOnClearListener.onClear(getText().toString());
            }
            this.setText("");

            return true;
        }
        
        return super.dispatchTouchEvent(event);
    }
    
    /**
     * check if touch clear icon
     * @param event
     * @return
     */
    private boolean isTouchClear(MotionEvent event)
    {
        
        int x = (int) event.getX();
        int y = (int) event.getY();
        
        if (x >= (this.getWidth() - this.getTotalPaddingRight()) 
                && x <= (this.getWidth() - this.getPaddingRight()))
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
        mClearDrawable.setBounds(0, 0,
                mClearDrawable.getIntrinsicWidth(),
                mClearDrawable.getIntrinsicHeight());


        showClear(mAlwaysShow);
    }

    public void setAlwaysShow(boolean alwaysShow)
    {
        this.mAlwaysShow = alwaysShow;

        showClear(mAlwaysShow);
    }
    
    /**
     *  set clear icon
     * @param show
     */
    private void showClear(boolean show)
    {
        Drawable right = (mAlwaysShow || show) ? mClearDrawable : null;
        this.setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1],
                right,
                getCompoundDrawables()[3]);

    }

    public void setOnClearListener(OnClearListener listener)
    {
        this.mOnClearListener = listener;
    }

    public void onClear() {

    }
}
