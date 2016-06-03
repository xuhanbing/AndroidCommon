package com.hanbing.mytest.activity.view;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.SimpleAdapter;

import com.common.util.LogUtils;
import com.hanbing.mytest.R;
import com.hanbing.mytest.activity.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestContinueScrollListView extends BaseActivity {



    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_continue_scroll_list_view);

        listView = new ContinueScrollListView(this);

        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 0; i < 100; i ++) {
            Map<String, String> map = new HashMap<>();
            map.put("value", "Item " + i);

            data.add(map);
        }


        SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_1, new String[]{"value"}, new int[]{android.R.id.text1});

        listView.setAdapter(adapter);


//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            boolean isFling = false;
//            int lastMotionY;
//
//            Scroller scroller;
//
//            @Override
//
//            public void onScrollStateChanged(final AbsListView view, int scrollState) {
//                LogUtils.e("onScrollStateChanged : " + scrollState + ", " + view.getFirstVisiblePosition());
//
//                if (SCROLL_STATE_FLING == scrollState) {
//                    isFling = true;
//                    lastMotionY = view.getFirstVisiblePosition();
//
//                } else if (SCROLL_STATE_TOUCH_SCROLL == scrollState) {
//                    if (null != scroller
//                            && !scroller.isFinished()) {
//                        scroller.forceFinished(true);
//                    }
//                    isFling = false;
//                } else {
//                    if (isFling) {
//
//                        view.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                view.smoothScrollBy(2000, 5000);
//                            }
//                        });
//
//                    }
//                    isFling = false;
//                }
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                LogUtils.e("onScroll");
//            }
//        });

//        listView.post(new Runnable() {
//            @Override
//            public void run() {
//                listView.smoothScrollBy(2000, 5000);
//            }
//        });


        setContentView(listView);
    }


    class ContinueScrollListView extends ListView {

        private int mMinimumVelocity;
        private int mMaximumVelocity;
        private int mTouchSlop;

        public ContinueScrollListView(Context context) {
            super(context);
            init();
        }

        public ContinueScrollListView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public ContinueScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private void init(){
            final ViewConfiguration configuration = ViewConfiguration.get(getApplicationContext());
            mTouchSlop = configuration.getScaledTouchSlop();
            mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
            mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        }

        @Override
        protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
            maxOverScrollY = 400;
            return super.overScrollBy(deltaX, deltaY , scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
        }

        class MyScroller extends Scroller {
            public MyScroller(Context context) {
                super(context);
            }

            public MyScroller(Context context, Interpolator interpolator) {
                super(context, interpolator);
            }

            public MyScroller(Context context, Interpolator interpolator, boolean flywheel) {
                super(context, interpolator, flywheel);
            }
        }
    }
}
