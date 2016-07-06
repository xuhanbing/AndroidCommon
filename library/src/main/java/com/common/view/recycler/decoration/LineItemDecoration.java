package com.common.view.recycler.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.view.recycler.BaseRecyclerView;

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

            LineItemDecoration lineItemDecoration = new LineItemDecoration(this);

            return lineItemDecoration;
        }
    }

    public LineItemDecoration(BaseItemDecoration.Builder builder) {
        super(builder);
    }

    @Override
    public Rect getDecorationRect(RecyclerView parent, View child) {

        int position = parent.getChildAdapterPosition(child);

        //last one has no divider
        if (null == parent.getAdapter()
                || position == parent.getAdapter().getItemCount() - 1)
            return null;

        if (parent instanceof BaseRecyclerView)
        {
            if (!((BaseRecyclerView) parent).drawItemDecoration(position))
            {
                return null;
            }
        }

        Rect rect = new Rect();

        if (isVertical())
        {
            rect.bottom = mMarginRect.bottom;
        } else {
            rect.right = mMarginRect.right;
        }

        return  rect;
    }

}
