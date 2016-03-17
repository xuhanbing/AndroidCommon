package com.common.widget.recyclerview.animator;

import android.support.v7.widget.RecyclerView;

import com.common.widget.recyclerview.animator.bean.AnimationValuePair;
import com.common.widget.recyclerview.animator.bean.RotationValuePair;
import com.common.widget.recyclerview.animator.bean.ScaleValuePair;
import com.common.widget.recyclerview.animator.bean.ValuePair;

/**
 * Created by hanbing on 2016/3/14.
 */
public class RotateItemAnimator extends BaseSimpleItemAnimator {
    @Override
    protected void initAnimation(RecyclerView.ViewHolder holder) {

        {
            RotationValuePair add = new RotationValuePair();
            add.z = new ValuePair(0, 360, 0);

            RotationValuePair remove = new RotationValuePair();
            remove.z = new ValuePair(add.z.to, add.z.from, 0);

            rotate(add, remove);
        }

        {
            ScaleValuePair add = new ScaleValuePair();
            add.x = add.y= new ValuePair(0, 1, 1);

            ScaleValuePair remove = new ScaleValuePair();
            remove.x = remove.y=new ValuePair(1, 0, 1);

            scale(add, remove);
        }
    }
}
