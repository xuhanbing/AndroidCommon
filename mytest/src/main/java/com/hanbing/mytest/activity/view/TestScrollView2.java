/**
 * 
 */
package com.hanbing.mytest.activity.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hanbing.mytest.R;
import com.hanbing.mytest.activity.BaseActivity;

/**
 * @author hanbing
 * @date 2015-12-24
 */
public class TestScrollView2 extends BaseActivity {

    /**
     * 
     */
    public TestScrollView2() {
	// TODO Auto-generated constructor stub
    }
    MyScrollView scrollView;
    LinearLayout layout;
    
    View swipeView;
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        
        scrollView = new MyScrollView(this);
        
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        LinearLayout innerLayout = new LinearLayout(this);
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        
        Drawable drawable0 = getResources().getDrawable(R.drawable.a);
        Drawable drawable1 = getResources().getDrawable(R.drawable.b);
        Drawable drawable2 = new ColorDrawable(Color.BLUE);
        Drawable drawable3 = new ColorDrawable(Color.YELLOW);
        
        Drawable[] layers = {drawable0, drawable1};
	TransitionDrawable drawable = new TransitionDrawable(layers );
	
	
	
        for (int i = 0; i < 1; i++)
        {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LayoutParams(-1, 200));
//            imageView.setBackgroundResource(R.drawable.a);
//            imageView.setBackgroundDrawable(drawable);
            imageView.setImageDrawable(drawable);
            drawable.startTransition(2000);
            innerLayout.addView(imageView);
        }
        
        ListView listView = new ListView(this);
        listView.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				TextView text = new TextView(getApplication());
				
				text.setText("item " + position);
				text.setTextSize(25);
				text.setPadding(20, 20, 20, 20);
				
				return text;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 50;
			}
		});
        
        
        
//        layout.setLayoutParams(new LayoutParams(-1, 2000));
        
//        getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        
        
        scrollView.addView(innerLayout);
        
        layout.addView(scrollView);
        layout.addView(listView);
        setContentView(layout);
        
    }
    
	
    
    
    
    
    class MyScrollView extends ScrollView {


	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public MyScrollView(Context context, AttributeSet attrs,
		int defStyleAttr) {
	    super(context, attrs, defStyleAttr);
	    // TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public MyScrollView(Context context, AttributeSet attrs) {
	    super(context, attrs);
	    // TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public MyScrollView(Context context) {
	    super(context);
	    // TODO Auto-generated constructor stub
	}
	
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
		int scrollY, int scrollRangeX, int scrollRangeY,
		int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
	    // TODO Auto-generated method stub
	    Log.e("overScrollBy", "deltaX=" + deltaX + ", deltaY=" + deltaY + ",scrollY=" + scrollY + ",scrollRangeX=" + scrollRangeX);
	    return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
	    	scrollRangeY, maxOverScrollX, 200, isTouchEvent);
	}
	
    }

}
