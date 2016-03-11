package com.common.widget.recyclerview.decoration;

import android.content.Context;

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

}
