package com.common.view.recycler.animator;

import android.support.v7.widget.RecyclerView;

import com.common.view.recycler.animator.bean.TranslationValuePair;
import com.common.view.recycler.animator.bean.ValuePair;

/**
 * Created by hanbing on 2016/3/11.
 */
public class SlideInBottomItemAnimator extends BaseSimpleItemAnimator {

    @Override
    protected void initAnimation(RecyclerView.ViewHolder holder) {

        int width = holder.itemView.getWidth();
        int height = holder.itemView.getHeight();

        TranslationValuePair add = new TranslationValuePair();
        TranslationValuePair remove = new TranslationValuePair();

        add.y = new ValuePair(height, 0);
        remove.y = new ValuePair(0, height, 0);

        translate(add, remove);
    }
}
