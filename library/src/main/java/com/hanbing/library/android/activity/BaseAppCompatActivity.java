/**
 * 
 */
package com.hanbing.library.android.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.hanbing.library.android.tool.SystemBarTintManager;

/**
 * @author hanbing
 * 
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {
	protected Activity mContext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		mContext = this;
		if (enableStatusBarTint())
		{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

				SystemBarTintManager systemBarTintManager = new SystemBarTintManager(this);
				systemBarTintManager.setStatusBarTintEnabled(true);
				systemBarTintManager.setNavigationBarTintEnabled(true);
				systemBarTintManager.setTintColor(getStatusBarTintColor());

				configSystemBarTintManager(systemBarTintManager);

			}
		}
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

	/**
	 * enable or not statusbar tint
	 * @return
	 */
	protected boolean enableStatusBarTint()
	{
		return false;
	}


	/**
	 * config systemBarTintManager if need
	 * @param systemBarTintManager
	 */
	protected void configSystemBarTintManager(SystemBarTintManager systemBarTintManager)
	{

	}

	/**
	 * get statusbar tint color
	 * @return
	 */
	protected int getStatusBarTintColor()
	{
		return 0;
	}
}
