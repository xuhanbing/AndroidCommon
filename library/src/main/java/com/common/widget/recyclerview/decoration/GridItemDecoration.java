package com.common.widget.recyclerview.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.common.widget.recyclerview.BaseRecyclerView;

/**
 * Created by hanbing on 2016/3/11.
 */
public class GridItemDecoration extends BaseItemDecoration {

    public static class Builder extends BaseItemDecoration.Builder {

        public Builder(Context context) {
            super(context);
        }

        @Override
        public BaseItemDecoration create() {

            GridItemDecoration gridItemDecoration = new GridItemDecoration(this);

            return gridItemDecoration;
        }
    }

    public GridItemDecoration(BaseItemDecoration.Builder builder) {
        super(builder);
    }

    @Override
    public Rect getDecorationRect(RecyclerView parent, View child) {

        int position = parent.getChildAdapterPosition(child);

        if (null == parent.getAdapter()
                || parent.getAdapter().getItemCount() == 0)
            return null;

        if (parent instanceof BaseRecyclerView)
        {
            if (!((BaseRecyclerView)parent).drawItemDecoration(position))
                return null;
        }

        return super.getDecorationRect(parent, child);
    }
}
