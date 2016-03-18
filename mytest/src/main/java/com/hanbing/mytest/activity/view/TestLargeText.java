package com.hanbing.mytest.activity.view;

import java.io.IOException;
import java.io.InputStream;

import com.hanbing.mytest.view.MyScrollView2;
import com.hanbing.mytest.view.MyScrollView2.OnPositionChangedListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class TestLargeText extends Activity {

	ScrollView scrollView;
	TextView textView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		init();
		
		loadData();
	}

	private void init() {
		// TODO Auto-generated method stub
		
		textView = new TextView(this);
		
		LinearLayout layout = new LinearLayout(this);
		layout.addView(textView);
		
		MyScrollView2 sv = new MyScrollView2(this);
		
		
		sv.setOnPositionChangedListener(new OnPositionChangedListener() {
			
			@Override
			public void onTop() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onBottom() {
				// TODO Auto-generated method stub
				loadData();
			}
		});
		
		scrollView = sv;
		
		scrollView.addView(layout);
		
		setContentView(scrollView);
	}
	
	
	private static final int PAGE_LENGTH = 4*1024;
	static final int LINES = 50;
	int lastPostion = 0;
	StringBuilder stringBuilder = new StringBuilder();
	
	InputStream is = null;
	private void loadData()
	{
		try {
			
			if (null == is)
			{
				is = getAssets().open("novel.txt");
			}
			
			int totalLength = is.available();
			int readLength = PAGE_LENGTH;
			
			if (totalLength < lastPostion + readLength)
			{
				readLength = totalLength - lastPostion;
			}
			
			if (readLength <= 0)
			{
				return;
			}
			
			byte[] buffer = new byte[readLength];
			
			is.read(buffer);
			
			String string = new String(buffer);
			
			stringBuilder.append(string);
			
//			int count = 0;
//			
//			String string = null;
//			while (count < LINES && (string = bufferedReader.readLine()) != null)
//			{
//				stringBuilder.append(string);
//				count++;
//			}
			
//			textView.setText(string);
			
			textView.setText(stringBuilder.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if (null != is)
		{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			is = null;
		}
	}
}
