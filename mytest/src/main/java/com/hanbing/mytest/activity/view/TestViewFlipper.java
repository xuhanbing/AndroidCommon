package com.hanbing.mytest.activity.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class TestViewFlipper extends Activity {

	ViewFlipper viewFlipper;
	
	int [] resIds = new int[]
			{
			R.drawable.a,
			R.drawable.b,
			R.drawable.a,
			R.drawable.b,
			};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		viewFlipper = new ViewFlipper(this);
		
		for (int i = 0; i < resIds.length; i++ )
		{
			ImageView image = new ImageView(this);
			image.setImageResource(resIds[i]);
			
			viewFlipper.addView(image);
		}
		setContentView(viewFlipper);
		
		viewFlipper.startFlipping();
	}

}
