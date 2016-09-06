package com.hanbing.mytest.activity.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.hanbing.library.android.adapter.BaseFragmentPagerAdapter;
import com.hanbing.library.android.view.SwipeBackLayout;
import com.hanbing.mytest.R;
import com.hanbing.mytest.adapter.DefaultAdapter;
import com.hanbing.mytest.fragment.NumFragment;

import java.util.ArrayList;
import java.util.List;

public class TestSwipeBack extends AppCompatActivity implements SwipeFollowActivityBase{

    public static Intent newIntent(Context context, String action) {
        Intent intent = new Intent(context, TestSwipeBack.class);
        intent.putExtra("action", action);
        return intent;
    }

    String action;
    SwipeBackLayout swipeBackLayout;

    final String ACTION = this+"";


    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ACTION.equals(intent.getAction())) {
                int x = intent.getIntExtra("x", 0);
                int y = intent.getIntExtra("y", 0);

                followScroll(x, y);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
if (null != getIntent()) {
    action =getIntent().getStringExtra("action");
}
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_swipe_back);
        View view = getLayoutInflater().inflate(R.layout.activity_test_swipe_back, null);

        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(new DefaultAdapter(20));


        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new NumFragment());
        fragments.add(new NumFragment());
        fragments.add(new NumFragment());

        viewPager.setAdapter(new BaseFragmentPagerAdapter(getSupportFragmentManager(), fragments, null));

        setContentView(view);

        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.attachToActivity(this);
        swipeBackLayout.setOnlyScrollIfTouchEdge(false);
        swipeBackLayout.setOnScrollChangedListener(new SwipeBackLayout.OnScrollChangedListener() {
            @Override
            public void onScroll(int x, int y) {
//                followScroll(x, y);

                if (!TextUtils.isEmpty(action))
                {
                    Intent intent = new Intent();
                    intent.setAction(action);
                    intent.putExtra("x", x);
                    intent.putExtra("y", y);

                    sendBroadcast(intent);
                }


            }

            @Override
            public void onFinishActivity() {

            }
        });

        IntentFilter intentFilter = new IntentFilter(ACTION);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public void scrollWhenNextOpened() {
        swipeBackLayout.scrollToPositionWhenNesting();
    }

    @Override
    public void followScroll(int x, int y) {
        swipeBackLayout.followScrollWithNext(x, y);
    }

    public void onClick(View view) {
        startActivity(TestSwipeBack.newIntent(this, ACTION));
        scrollWhenNextOpened();

    }

    @Override
    public void onBackPressed() {
//        swipeBackLayout.scrollToFinishActivity();
        super.onBackPressed();
    }
}
