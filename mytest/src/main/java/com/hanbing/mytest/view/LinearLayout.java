package com.hanbing.mytest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.hanbing.mytest.R;

/**
 * Created by hanbing
 */
public  class LinearLayout extends android.widget.LinearLayout {

    public LinearLayout(Context context) {
        super(context);
    }

    public LinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

         RotatePlate rotate = (RotatePlate) findViewById(R.id.rotatePlate);

        if (null != rotate) {
            if (rotate.dispatchTouchEventLocal(ev))
                return true;
        }


        return super.dispatchTouchEvent(ev);
    }




}
