/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��5��26�� 
 * Time : ����12:50:24
 */
package com.hanbing.mytest.view;

import com.hanbing.mytest.listener.MulitPointTouchListener;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * �������϶���imageview
 * @author hanbing 
 * @date 2014��5��26�� 
 * @time ����12:50:24
 */
public class CustomImageView extends ImageView {
    
    MulitPointTouchListener onTouchLsner;
    
    OnClickListener onClickLsner;
    OnLongClickListener onLongClickLsner;

    /**
     * @param context
     */
    public CustomImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init();
    }
    
    private void init()
    {
        onTouchLsner = new MulitPointTouchListener(getContext());
        this.setOnTouchListener(onTouchLsner);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        // TODO Auto-generated method stub
        onTouchLsner.setOnClickLsner(l);
        super.setOnClickListener(l);
        
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        // TODO Auto-generated method stub
        onTouchLsner.setOnLongClickLsner(l);
        super.setOnLongClickListener(l);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        // TODO Auto-generated method stub
        super.setOnTouchListener(onTouchLsner);
    }


    @Override
    protected void onDetachedFromWindow() {
        // TODO Auto-generated method stub
        super.onDetachedFromWindow();
        onTouchLsner.release();
        
    }

}
