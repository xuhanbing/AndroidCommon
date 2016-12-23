package com.hanbing.mytest.activity;

import android.os.Bundle;

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
