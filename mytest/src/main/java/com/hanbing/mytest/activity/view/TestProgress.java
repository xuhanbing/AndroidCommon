package com.hanbing.mytest.activity.view;

import com.hanbing.mytest.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class TestProgress extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ScrollView sv = new ScrollView(this);
		
		final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
//        LayoutParams lp = new LayoutParams(
//              LayoutParams.MATCH_PARENT,
//              200);
//        {
//        	ProgressView v1 = new ProgressView(this, ProgressView.STYLE_CIRCLE);
//            ProgressView v2 = new ProgressView(this, ProgressView.STYLE_SWEEP);
//            ProgressView v3 = new ProgressView(this, ProgressView.STYLE_WATER);
//            
//            layout.addView(v1, lp);
//            layout.addView(v2, lp);
//            layout.addView(v3, lp);
//        }
//        
//        {
//        	ProgressView v1 = new ProgressView(this, ProgressView.STYLE_CIRCLE);
//            ProgressView v2 = new ProgressView(this, ProgressView.STYLE_SWEEP);
//            ProgressView v3 = new ProgressView(this, ProgressView.STYLE_WATER);
//            
//            v1.mUseRing = false;
//            v2.mUseRing = false;
//            v3.mUseRing = false;
//            
//            layout.addView(v1, lp);
//            layout.addView(v2, lp);
//            layout.addView(v3, lp);
//        }
//        
//        {
//        	
//        	LayoutParams lp = new LayoutParams(
//                    LayoutParams.MATCH_PARENT,
//                    100);
//        	
//        	
//        	ProgressView v1 = new ProgressView(this, ProgressView.STYLE_RECT_PROGRESS_IN);
//            ProgressView v2 = new ProgressView(this, ProgressView.STYLE_RECT_PROGRESS_OUT_RIGHT);
//            ProgressView v3 = new ProgressView(this, ProgressView.STYLE_RECT_PROGRESS_OUT_LEFT);
//            ProgressView v4 = new ProgressView(this, ProgressView.STYLE_RECT_PROGRESS_OUT_TOP);
//            ProgressView v5 = new ProgressView(this, ProgressView.STYLE_RECT_PROGRESS_OUT_BOTTOM);
//            
//            v1.mUseRing = false;
//            v2.mUseRing = false;
//            v3.mUseRing = false;
//            v4.mUseRing = false;
//            v5.mUseRing = false;
//            
//            List<View> list = new ArrayList<View>();
//            list.add(v1);
//            list.add(v2);
//            list.add(v3);
//            list.add(v4);
//            list.add(v5);
//            
//            for (int i = 0; i < list.size(); i ++)
//            {
//            	layout.addView(list.get(i), lp);
//            	layout.addView(new View(this), lp);
//            }
//            
//        }
//        
//        
//        
//        
//        layout.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				for (int i = 0; i < layout.getChildCount(); i++)
//				{
//					View view = layout.getChildAt(i);
//					if (view instanceof ProgressView)
//					{
//						ProgressView pv = (ProgressView)view ;
//						pv.test();
//					}
//					
//				}
//			}
//		});
//        
//        sv.addView(layout);
//        setContentView(sv);
        
        setContentView(R.layout.layout_progress_test);
	}

}
