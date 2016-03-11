package com.common.widget.recyclerview.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hanbing on 2016/3/11.
 */
public class LineItemDecoration extends BaseItemDecoration {

    public static class Builder extends BaseItemDecoration.Builder {

        public Builder(Context context) {
            super(context);
        }

        @Override
        public BaseItemDecoration create() {

            System.out.println("creat LineItemDecoration");
            LineItemDecoration lineItemDecoration = new LineItemDecoration(this);

            return lineItemDecoration;
        }
    }

    public LineItemDecoration(BaseItemDecoration.Builder builder) {
        super(builder);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        int position = parent.getChildAdapterPosition(view);
        if (position == parent.getAdapter().getItemCount() - 1)
            return;

        if (isVertical())
        {
            mMarginRect.bottom = mSize;
        } else {
            mMarginRect.right = mSize;
        }

        outRect.set(mMarginRect);
    }

//    @Override
//    protected void drawDecoration(Canvas c, View child) {
//
//        int size = mSize;
//
//        if (isVertical())
//        {
//            c.drawRect(child.getLeft(), child.getBottom(), child.getRight(), child.getBottom() + size, mPaint);
//        } else {
//            c.drawRect(child.getRight(), child.getTop(), child.getRight() + size, child.getBottom(), mPaint);
//        }
//
//    }
}
