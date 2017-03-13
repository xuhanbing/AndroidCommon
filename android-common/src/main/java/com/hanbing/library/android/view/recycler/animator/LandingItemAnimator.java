package com.hanbing.library.android.view.recycler.animator;

import android.support.v7.widget.RecyclerView;

import com.hanbing.library.android.view.recycler.animator.bean.AlphaValuePair;
import com.hanbing.library.android.view.recycler.animator.bean.ScaleValuePair;
import com.hanbing.library.android.view.recycler.animator.bean.TranslationValuePair;
import com.hanbing.library.android.view.recycler.animator.bean.ValuePair;

/**
 * Created by hanbing on 2016/3/14.
 */
public class LandingItemAnimator extends BaseSimpleItemAnimator {
    @Override
    protected void initAnimation(RecyclerView.ViewHolder holder) {

        {
            ScaleValuePair add = new ScaleValuePair();
            ScaleValuePair remove = new ScaleValuePair();

            add.x = add.y =  new ValuePair(1.5f, 1);
            remove.x = remove.y = new ValuePair(1, 1.5f, 1);

            scale(add, remove);
        }

        {
            TranslationValuePair add = new TranslationValuePair();
            TranslationValuePair remove = new TranslationValuePair();


            add.z = new ValuePair(50, 0);
            remove.z = new ValuePair(0, 50, 0);

            translate(add, remove);

        }

        {
            AlphaValuePair add = new AlphaValuePair();
            AlphaValuePair remove = new AlphaValuePair();

            add.alpha = new ValuePair(0, 1);
            remove.alpha = new ValuePair(1, 0, 1);

            alpha(add, remove);
        }
    }
}
