/**
 * 
 */
package com.hanbing.mytest.activity.action;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.mytest.activity.BaseActivity;

/**
 * @author hanbing
 * @date 2015-11-27
 */
public class TestTouch extends BaseActivity {

    class TestViewGroup extends LinearLayout {

	String tag = "";
	/**
	 * @param context
	 */
	public TestViewGroup(Context context) {
	    super(context);
	    // TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
	    // TODO Auto-generated method stub
	    boolean ret = super.onInterceptTouchEvent(ev);
	    LogUtils.e(tag, " onInterceptTouchEvent action=" + ev.getAction() + ",ret="+ret);
	    return ret;
	}
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
	    // TODO Auto-generated method stub
	    boolean ret = true;//super.dispatchTouchEvent(ev);
	    LogUtils.e(tag, " dispatchTouchEvent action=" + ev.getAction() + ",ret="+ret);
	    return ret;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    // TODO Auto-generated method stub
	    boolean ret = super.onTouchEvent(event);
	    LogUtils.e(tag, " onTouchEvent action=" + event.getAction() + ",ret="+ret);
	    return ret;
	}
    }
    
    class TestView extends ImageView {

	String tag = "";
	
	/**
	 * @param context
	 */
	public TestView(Context context) {
	    super(context);
	    // TODO Auto-generated constructor stub
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
	    // TODO Auto-generated method stub
	    boolean ret = super.dispatchTouchEvent(ev);
	    LogUtils.e(tag, " dispatchTouchEvent action=" + ev.getAction() + ",ret="+ret);
	    return ret;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    // TODO Auto-generated method stub
	    boolean ret = true;//super.onTouchEvent(event);
	    LogUtils.e(tag, " onTouchEvent action=" + event.getAction() + ",ret="+ret);
	    return ret;
	}
    }
    
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
	super.onCreate(arg0);
	
	DisplayMetrics dm = getResources().getDisplayMetrics();
	int height = dm.heightPixels;
	
	TestViewGroup base = new TestViewGroup(this);
	base.tag = "base";
	base.setBackgroundColor(0x80ff0000);
	base.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height));
	
	TestViewGroup parent = new TestViewGroup(this);
	parent.tag = "parent";
	parent.setBackgroundColor(0x8000ff00);
	parent.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height * 3 / 4));
	
	TestView child = new TestView(this);
	child.tag = "child";
	child.setBackgroundColor(0x800000ff);
	child.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, height * 2 / 4));
	
	base.addView(parent);
	parent.addView(child);
	
	setContentView(base);
        
    }

}
