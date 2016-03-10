/**
 * 
 */
package com.common.base;

import org.xutils.x;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

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
}
