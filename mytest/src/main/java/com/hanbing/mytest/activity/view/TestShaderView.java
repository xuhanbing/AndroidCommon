/**
 * 
 */
package com.hanbing.mytest.activity.view;

import android.os.Bundle;

import com.hanbing.mytest.activity.BaseActivity;
import com.hanbing.mytest.view.ShaderView;

/**
 * @author hanbing
 * @date 2015-9-11
 */
public class TestShaderView extends BaseActivity {

	/**
	 * 
	 */
	public TestShaderView() {
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		ShaderView view = new ShaderView(this);
		
		setContentView(view);
	}
}
