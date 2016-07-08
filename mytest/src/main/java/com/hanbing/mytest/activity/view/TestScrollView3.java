package com.hanbing.mytest.activity.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.hanbing.library.android.tool.ScrollableViewTransitionController;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.library.android.view.scroll.PullScaleScrollView;
import com.hanbing.library.android.view.scroll.StrengthScrollView;
import com.hanbing.mytest.R;

public class TestScrollView3 extends AppCompatActivity {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scroll_view3);

        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);

        final ImageView portrait = ViewUtils.findViewById(this, R.id.portrait);

        final View moveView = ViewUtils.findViewById(this, R.id.toolbar_move);
        final View staticView = ViewUtils.findViewById(this, R.id.toolbar_static);

        final View view = ViewUtils.findViewById(this, R.id.view);

        if (scrollView instanceof PullScaleScrollView)
        {
            ((PullScaleScrollView) scrollView).setScaleView(imageView);

        }

        ((StrengthScrollView) scrollView).setOnPullListener(new StrengthScrollView.OnPullListener() {
            @Override
            public void onPull(float dy, float y, float max) {
                LogUtils.e("onPull " + dy);
            }

            @Override
            public void onMoveBack(float dy, float y, float max) {
                LogUtils.e("onMoveBack " + dy);
            }
        });

        ScrollableViewTransitionController.setControlViews(scrollView, view, moveView, false, new ScrollableViewTransitionController.OnScrollListener() {
            @Override
            public void onScroll(float scrollY, float scrollScale) {

                LogUtils.e("onScaleMove moveScale = " + scrollScale);

                int color = getResources().getColor(R.color.blueviolet);

                int newColor = ScrollableViewTransitionController.scaleColorAlpha(color, scrollScale);

                view.setBackgroundColor(newColor);

                float scale = scrollScale;

                if (scrollScale >= 1.0) {

                    staticView.setVisibility(View.VISIBLE);
                } else {
                    staticView.setVisibility(View.GONE);
                }

                ViewUtils.setScale(view, scale, scale);
            }

        });



    }
}
