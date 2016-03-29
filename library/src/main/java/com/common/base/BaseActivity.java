/**
 * 
 */
package com.common.base;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import com.common.tool.SystemBarTintManager;

import org.xutils.x;

/**
 * @author hanbing
 * 
 */
public class BaseActivity extends FragmentActivity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub

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

		x.view().inject(this);

		initViews();
	}



	/**
	 * 
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
