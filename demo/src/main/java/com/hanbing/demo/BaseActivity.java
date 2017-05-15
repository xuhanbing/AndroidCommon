package com.hanbing.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hanbing.library.android.view.SwipeBackLayout;

/**
 * Created by hanbing on 2016/9/23
 */
public class BaseActivity extends AppCompatActivity {


    public SwipeBackLayout swipeBackLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.attachToActivity(this);
        swipeBackLayout.setSwipeOnlyIfTouchEdge(true);
    }



}
