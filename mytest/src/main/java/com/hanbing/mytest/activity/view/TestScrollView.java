/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��3��19�� 
 * Time : ����2:40:12
 */
package com.hanbing.mytest.activity.view;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.mytest.view.MyScrollView;
import com.hanbing.mytest.view.MyScrollView.RefreshListener;
import com.hanbing.mytest.view.StrengthScrollView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;



/**
 * TestScrollView.java 
 * @author hanbing 
 * @date 2014��3��19�� 
 * @time ����2:40:12
 */
public class TestScrollView extends Activity {

    Handler handler;
    ImageView image1;
    ImageView image2;
    MyScrollView myScrollView;
    ListView listView;
    
    StrengthScrollView mStrengthScrollView;
    View footer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        handler = new Handler();
        myScrollView = new MyScrollView(this);
        myScrollView.handler = handler;
        myScrollView.setRefreshListener(new RefreshListener() {
            
            @Override
            public void onReflesh() {
                // TODO Auto-generated method stub
                reflesh();
            }
        });
//        ScrollView myScrollView = new ScrollView(this);
        
        final int padding = 0;
        
        LinearLayout parent = new LinearLayout(this);
        final StrengthScrollView sv = new StrengthScrollView(this);
        mStrengthScrollView = sv;
//        mStrengthScrollView.setOnTouchListener(new TouchListenerImpl());
        
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        LogUtils.e("screen height=" + dm.heightPixels);
        final LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dm.heightPixels / 3);
        LayoutParams params1 = new LayoutParams(LayoutParams.MATCH_PARENT, dm.heightPixels / 3);
        image1 = new ImageView(this);
        image1.setBackgroundResource(R.drawable.a);
        image2 = new ImageView(this);
        image2.setBackgroundResource(R.drawable.a00);
        
        final int margin = -200;
        params0.topMargin = margin;
        image1.setLayoutParams(params0);
        image2.setLayoutParams(params1);
        
        final TextView footer = new TextView(this);
        footer.setGravity(Gravity.CENTER);
        footer.setText("加载中...");
        footer.setTextSize(25);
        footer.setBackgroundColor(Color.YELLOW);
        this.footer = footer;
        footer.setVisibility(View.GONE);
        
        
        
        listView = new MyListView(this);
          
        listView.setAdapter(new MyAdapter());
        
        layout.addView(image1);
//        layout.addView(image2);
//        layout.addView(listView);
//        layout.addView(footer);
        
//        calcListViewHeight(listView);
        
        layout.setBackgroundColor(0x4400ffff);
        sv.setBackgroundColor(Color.YELLOW);
        
        
//        layout.setPadding(0, padding, 0, 0);
//        
//        sv.setOnPullListener(new OnPullListener() {
//	    
//	    @Override
//	    public void onPullUp(float y, float max) {
//		// TODO Auto-generated method stub
//	    }
//	    
//	    @Override
//	    public void onPullDown(float y, float max) {
//		// TODO Auto-generated method stub
//		layout.setPadding(0, (int) (padding * (1- y / max)), 0, 0);
//		
//	    }
//	});
        
//        sv.setOnPullListener(new OnPullListener() {
//	    
//	    @Override
//	    public void onPullUp(float dy, float y, float max) {
//		// TODO Auto-generated method stub
//		LogUtils.e("pull up y=" + y);
//		setPull(y, max);
//	    }
//	    
//	    @Override
//	    public void onPullDown(float dy, float y, float max) {
//		// TODO Auto-generated method stub
//		LogUtils.e("pull down y=" + y);
//		setPull(y, max);
//	    }
//	    
//	    @Override
//	    public void onMoveBack(float dy, float y, float max) {
//	        // TODO Auto-generated method stub
//		LogUtils.e("move back y=" + y);
//		setPull(y, max);
//	    }
//	    public void setPull(float y, float max)
//	    {
//		float scale = 1 + y / max;
//		params0.topMargin = (int) (margin + y);
//		sv.setAddHeight((int) y);
//		image1.requestLayout();
//		image1.setScaleX(scale);
//		image1.setScaleY(scale);
//	    }
//	});
        
        FrameLayout.LayoutParams params2 =  new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
//        params2.topMargin = -200;
        params2.gravity = Gravity.TOP;
        
        sv.addView(layout);
        
        
//        myScrollView.addView(sv);
//        this.setContentView(myScrollView);
        setContentView(sv);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
	menu.add(0, 0, 0, "change");
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
	if (0 == item.getItemId())
	{
	    change();
	}
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * 
     */
    private void change() {
	// TODO Auto-generated method stub
	
	ViewGroup vg = (ViewGroup) mStrengthScrollView.getChildAt(0);
	
	if (vg.indexOfChild(image2) >= 0)
	{
	    vg.removeView(image2);
	    vg.removeView(listView);
	} else {
	    vg.removeAllViews();
	    vg.addView(image1);
	    vg.addView(image2);
	    vg.addView(listView);
	}
    }

    private class TouchListenerImpl implements OnTouchListener { 
	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
	    switch (motionEvent.getAction()) {
	    case MotionEvent.ACTION_DOWN:

		break;
	    case MotionEvent.ACTION_MOVE:
		int scrollY = view.getScrollY();
		int height = view.getHeight();
		int scrollViewMeasuredHeight = mStrengthScrollView
			.getChildAt(0).getMeasuredHeight();
		if (scrollY == 0) {
		    // 刷新数据，在响应成功后隐藏proressBar
		}
		if ((scrollY + height) == scrollViewMeasuredHeight) {
		    footer.setVisibility(View.VISIBLE);
		    
		    handler.postDelayed(new Runnable() {
		        
		        @Override
		        public void run() {
		    	// TODO Auto-generated method stub
		            footer.setVisibility(View.GONE);
		        }
		    }, 1000);
		}
		break;

	    }
	    return false;
	}

    };
    
    
    public void calcListViewHeight(ListView listView)
    {
	// 获取ListView对应的Adapter

	  ListAdapter listAdapter = listView.getAdapter();

	  if (listAdapter == null) {

	   return;

	  }

	  int totalHeight = 0;

	  for (int i = 0; i < listAdapter.getCount(); i++) { // listAdapter.getCount()返回数据项的数目

	   View listItem = listAdapter.getView(i, null, listView);

	   listItem.measure(0, 0); // 计算子项View 的宽高

	   totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度

	  }

	  ViewGroup.LayoutParams params = listView.getLayoutParams();
	  
	  params.height = totalHeight
	    + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

	  // listView.getDividerHeight()获取子项间分隔符占用的高度

	  // params.height最后得到整个ListView完整显示需要的高度

	  listView.setLayoutParams(params);
    }
    
    class MyListView extends ListView
    {

	/**
	 * @param context
	 */
	public MyListView(Context context) {
	    super(context);
	    // TODO Auto-generated constructor stub
	}
	
	@Override 
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
	    int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, 
	            MeasureSpec.AT_MOST); 
	    super.onMeasure(widthMeasureSpec, expandSpec); 
	}  
    }
    
    class MyAdapter extends BaseAdapter {

	@Override
	public int getCount() {
	    // TODO Auto-generated method stub
	    return 20;
	}

	@Override
	public Object getItem(int position) {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public long getItemId(int position) {
	    // TODO Auto-generated method stub
	    return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    // TODO Auto-generated method stub
	    TextView textView = new TextView(getApplicationContext());
	    
	    textView.setText("item " + position);
	    textView.setPadding(20, 20, 20, 20);
	    textView.setTextSize(25);
	    
	    return textView;
	}
	
    }
    
    /**
     * 
     */
    int count = 0;
    protected void reflesh() {
        // TODO Auto-generated method stub
        
        
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                count++;
                
                handler.post(new Runnable() {
                    
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        
                        int id = R.drawable.a;
                        
                        if (count % 2 == 0)
                        {
                            id = R.drawable.b;
                        }
                        
                        image1.setBackgroundResource(id);
                        image2.setBackgroundResource(id);
                        
                        myScrollView.goOrig();
                    }
                });
                
                
            }
        }).start();;
        
        
        
    }

    
}
