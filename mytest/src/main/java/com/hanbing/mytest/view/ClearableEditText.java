package com.hanbing.mytest.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;


/**
 * �������ť�ı༭��
 * @author Administrator 2014��2��19�� ����2:39:57
 */
public class ClearableEditText extends EditText {

    Drawable mClearDrawable; //��հ�ť��ͼƬ
    
    //��հ�ť��Ĭ��ͼƬ
    int mDftClearDrawableResId = android.R.drawable.ic_delete;
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
     * ��ʼ��
     */
    private void init()
    {
        mClearDrawable = this.getCompoundDrawables()[2];
        
        //û�����ã�ʹ��Ĭ�ϵ�ͼƬ
        if (null == mClearDrawable)
        {
            setClearImageResource(mDftClearDrawableResId);
            
        }
        this.addTextChangedListener(new TextWatcher()
        {

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
        
        //����������ť�������������
        if (isTouchClear(event))
        {
            this.setText("");
            return true;
        }
        
        return super.dispatchTouchEvent(event);
    }
    
    /**
     * �ж��Ƿ����������ť
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
     * ������հ�ť��ͼƬ
     * @param resId
     */
    public void setClearImageResource(int resId)
    {
        setClearImageDrawable(getResources().getDrawable(resId));
    }
    
    /**
     * ������հ�ťͼƬ
     * @param d
     */
    public void setClearImageDrawable(Drawable d)
    {
        mClearDrawable = d;
        mClearDrawable.setBounds(0, 0, 
                                 mClearDrawable.getIntrinsicWidth(), 
                                 mClearDrawable.getIntrinsicHeight());
        
    }
    
    /**
     * �ж��Ƿ���ʾ��հ�ť
     * @param show
     */
    private void showClear(boolean show)
    {
        Drawable right = show ? mClearDrawable : null;
        this.setCompoundDrawables(getCompoundDrawables()[0], 
                                  getCompoundDrawables()[1],
                                  right, 
                                  getCompoundDrawables()[3]);

    }
}
