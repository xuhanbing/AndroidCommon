package com.common.widget.recyclerview.animator;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by hanbing on 2016/3/11.
 */
public class SlideInBottomItemAnimator extends BaseTranslationItemAnimator {


    @Override
    protected void initValues(RecyclerView.ViewHolder holder) {
        super.initValues(holder);

        View view = holder.itemView;

        mAddFromX = mRemoveToX = 0;
        mAddToX = mRemoveFromX = 0;

        mAddFromY = mRemoveToY = view.getHeight();
        mAddToY = mRemoveToY = 0;
    }
}
