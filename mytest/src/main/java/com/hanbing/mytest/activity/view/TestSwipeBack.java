package com.hanbing.mytest.activity.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.hanbing.library.android.view.SwipeBackLayout;
import com.hanbing.mytest.R;
import com.hanbing.mytest.adapter.DefaultAdapter;

public class TestSwipeBack extends AppCompatActivity implements SwipeFollowActivityBase{

    SwipeBackLayout swipeBackLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_swipe_back);
        View view = getLayoutInflater().inflate(R.layout.activity_test_swipe_back, null);

        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(new DefaultAdapter(20));


        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().getDecorView().setBackgroundDrawable(null);

        setContentView(view);

        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.attachToActivity(this);
    }

    @Override
    public void scrollWhenNextOpened() {
        swipeBackLayout.scrollWhenNextOpened();
    }

    @Override
    public void followScroll(int x, int y) {
        swipeBackLayout.scrollContentBy(x, y);
    }

    public void onClick(View view) {
        startActivity(new Intent(this, TestSwipeBack.class));
        overridePendingTransition(R.anim.in_from_right, 0);
        scrollWhenNextOpened();
    }
}
