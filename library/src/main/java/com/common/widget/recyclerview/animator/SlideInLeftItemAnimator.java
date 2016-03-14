package com.common.widget.recyclerview.animator;

import android.support.v7.widget.RecyclerView;

import com.common.widget.recyclerview.animator.bean.TranslationValuePair;
import com.common.widget.recyclerview.animator.bean.ValuePair;

/**
 * Created by hanbing on 2016/3/11.
 */
public class SlideInLeftItemAnimator extends BaseSimpleItemAnimator {


    @Override
    protected void initAnimation(RecyclerView.ViewHolder holder) {

        int width = holder.itemView.getWidth();
        int height = holder.itemView.getHeight();

        TranslationValuePair add = new TranslationValuePair();
        TranslationValuePair remove = new TranslationValuePair();

        add.x = new ValuePair(-width, 0);
        remove.x = new ValuePair(0, -width, 0);

        translate(add, remove);
    }
}
