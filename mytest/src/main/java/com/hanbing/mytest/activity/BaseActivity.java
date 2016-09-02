package com.hanbing.mytest.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.view.SwipeBackLayout;

public class BaseActivity extends com.hanbing.library.android.activity.BaseActivity {


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
	}


	@Override
	protected void initViews() {
		super.initViews();
		new SwipeBackLayout(this).attachToActivity(this);
	}

	@Override
	protected void setContentView() {

	}
}
