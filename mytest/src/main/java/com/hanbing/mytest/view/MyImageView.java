package com.hanbing.mytest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public  class MyImageView extends ImageView {

        public MyImageView(Context context) {
            super(context);
        }

        public MyImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

            int width = getMeasuredWidth();
            int height = getMeasuredHeight();
        }
    }