package com.common.widget.recyclerview.animator;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hanbing on 2016/3/11.
 */
public class SlideInRightItemAnimator extends BaseTranslationItemAnimator {

    @Override
    protected void initValues(RecyclerView.ViewHolder holder) {
        super.initValues(holder);

        View view = holder.itemView;

        mAddFromX = mRemoveToX = view.getWidth();
        mAddToX = mRemoveFromX = 0;

        mAddFromY = mRemoveToY = 0;
        mAddToY = mRemoveToY = 0;
    }
}
