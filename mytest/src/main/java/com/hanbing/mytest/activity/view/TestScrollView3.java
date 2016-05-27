package com.hanbing.mytest.activity.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.common.tool.ScrollViewTransitionController;
import com.common.util.LogUtils;
import com.common.util.ViewUtils;
import com.common.view.PullScaleScrollView;
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

        ScrollViewTransitionController.setControlViews(scrollView, view, moveView, new ScrollViewTransitionController.OnTransitionListener() {
            @Override
            public void onScaleMove(float moveY, float moveScale) {

                LogUtils.e("onScaleMove moveScale = " + moveScale);

                int color = getResources().getColor(R.color.blueviolet);

                int newColor = ScrollViewTransitionController.scaleColorAlpha(color, moveScale);

                view.setBackgroundColor(newColor);

                float scale = moveScale;

                if (moveScale >= 1.0) {

                    staticView.setVisibility(View.VISIBLE);
                } else {
                    staticView.setVisibility(View.GONE);
                }

                ViewUtils.setScale(view, scale, scale);
            }

            @Override
            public void onScalePull(float pullY, float pullScale) {

            }
        });
    }
}
