/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2015年4月27日
 * Time : 下午3:37:47
 */
package com.hanbing.mytest.activity.view;

import com.hanbing.mytest.view.SideBarLayout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

/**
 * TestSideBar
 * @author hanbing 
 * @date 2015年4月27日
 * @time 下午3:37:47
 */
public class TestSideBar extends Activity {

	/**
	 * 
	 */
	public TestSideBar() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		SideBarLayout layout = new SideBarLayout(this);
		TextView left = new TextView(this);
		left.setText("Left123456789");
		left.setBackgroundColor(Color.RED);
		left.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
		left.setTextSize(45);
		left.setGravity(Gravity.CENTER);
		
		TextView center = new TextView(this);
		center.setText("Center");
		center.setBackgroundColor(Color.GREEN);
		center.setTextSize(45);
		center.setGravity(Gravity.CENTER);
		
		TextView right = new TextView(this);
		right.setText("Right123456789");
		right.setBackgroundColor(Color.BLUE);
		right.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
		right.setTextSize(45);
		right.setGravity(Gravity.CENTER);
		
		layout.addView(left, center, right);
		layout.setBackgroundColor(Color.YELLOW);
		
		setContentView(layout);
	}
}
