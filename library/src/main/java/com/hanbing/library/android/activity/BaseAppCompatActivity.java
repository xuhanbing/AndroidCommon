/**
 * 
 */
package com.hanbing.library.android.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.xutils.x;

/**
 * @author hanbing
 * 
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);

		setContentView();
		bindViews(arg0);
		initViews();
	}

	/**
	 * You should call @{@link #setContentView} here
	 */
	protected abstract void setContentView();

	/**
	 * Bind views
	 * @param savedInstanceState
	 */
	protected  void bindViews(Bundle savedInstanceState) {

	}

	/**
	 * Initialize views
	 */
	protected void initViews() {
		// TODO Auto-generated method stub
	}
}
