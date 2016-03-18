package com.hanbing.mytest.activity.view;

import com.hanbing.mytest.fragment.NumFragment;
import com.hanbing.mytest.view.SlideMenuLayout;
import com.hanbing.mytest.view.SlidingMenu;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TestSlideLayout extends FragmentActivity {

	private SlideMenuLayout mSlidingMenu;
	private NumFragment leftFragment;	// ��fragment
	private NumFragment rightFragment;	// ��fragment
	private NumFragment viewPageFragment;	// �м临��fragment
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		LinearLayout ll = new LinearLayout(this);
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0);
		lp.weight = 1;
		
		LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 20);
		
		View line = new View(this);
		
		line.setBackgroundColor(Color.WHITE);
		
		View v1 = getLayout1();
		View v2 = getLayout2();
		
		ll.addView(v1, lp);
		ll.addView(line, lpp);
		ll.addView(v2, lp);
		
		ll.setOrientation(LinearLayout.VERTICAL);
		
		setContentView(ll);
	}
	
	private View getLayout1() {
		// TODO Auto-generated method stub
		SlideMenuLayout layout = new SlideMenuLayout(this);
//		SlidingMenu layout = new SlidingMenu(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
				LayoutParams.MATCH_PARENT));
		
//		View left = getText("left", Color.RED);
//		View right = getText("right", Color.YELLOW);
//		View content = getText("content", Color.WHITE);
		
		View left = getView("left menu", Color.RED);
		View right = getView("right menu", Color.YELLOW);
		View content = getView("This is content", Color.GREEN);
		
		
		layout.setLeftView(left);
		layout.setRightView(right);
		layout.setCenterView(content);
		
		return layout;
	}
	
	private View getLayout2() {
		// TODO Auto-generated method stub
//		SlideMenuLayout layout = new SlideMenuLayout(this);
		SlidingMenu layout = new SlidingMenu(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
				LayoutParams.MATCH_PARENT));
		
//		View left = getText("left", Color.RED);
//		View right = getText("right", Color.YELLOW);
//		View content = getText("content", Color.WHITE);
		
		View left = getView("left menu", Color.RED);
		View right = getView("right menu", Color.YELLOW);
		View content = getView("This is content", Color.GREEN);
		
		
		layout.setLeftView(left);
		layout.setRightView(right);
		layout.setCenterView(content);
		
		return layout;
	}

	private TextView getText(final String text, int bgColor)
	{
		TextView textView = new TextView(this);
		
		textView.setGravity(Gravity.CENTER);
		textView.setText(text);
		textView.setBackgroundColor(bgColor);
		textView.setTextSize(50);
		
		textView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		
		textView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
			}
		});
		
		
		return textView;
	}
	
	private View getView(String text, int bgColor)
	{
		View view = new View(this);
		
		LinearLayout layout = new LinearLayout(this);
		
		layout.addView(getText(text, bgColor));
		
		
		return layout;
	}
}
