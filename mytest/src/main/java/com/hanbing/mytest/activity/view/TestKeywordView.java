/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2015年5月5日
 * Time : 上午11:19:35
 */
package com.hanbing.mytest.activity.view;

import com.hanbing.mytest.view.KeywordView;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;

/**
 * TestKeywordView
 * @author hanbing 
 * @date 2015年5月5日
 * @time 上午11:19:35
 */
public class TestKeywordView extends Activity {

	/**
	 * 
	 */
	public TestKeywordView() {
		// TODO Auto-generated constructor stub
	}

	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		KeywordView view = new KeywordView(this);
		
		view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		setContentView(view);
	}
}
