package com.hanbing.mytest.activity.action;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

public class TestAppWindow extends Activity {

    WindowManager wm = null;
    Button btn;
    LayoutParams params = null;

    public TestAppWindow() {
	// TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	
	setContentView(R.layout.activity_appwindow);

	wm = (WindowManager) getApplication().getSystemService(
		Context.WINDOW_SERVICE);

	open();
    }

    @Override
    protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();

	close();
    }

    private void open() {
	btn = new Button(this);
	btn.setBackgroundColor(0);
	btn.setText("click me");
	btn.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "you click me!",
			Toast.LENGTH_SHORT).show();
		
		finish();
	    }
	});

	btn.setOnTouchListener(new OnTouchListener() {

	    @Override
	    public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		Log.e("", "x=" + event.getX() + ",y=" + event.getY() + ",rx="
			+ event.getRawX() + ",ry=" + event.getRawY());

		params.x = (int) event.getRawX() - btn.getWidth() / 2;
		params.y = (int) event.getRawY() - btn.getHeight() / 2;
		wm.updateViewLayout(btn, params);
		return false;
	    }

	});

	params = new WindowManager.LayoutParams();
	params.type = LayoutParams.TYPE_SYSTEM_ALERT;

	params.format = PixelFormat.RGBA_8888; // ����ͼƬ��ʽ��Ч��Ϊ����͸��

	params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
		| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
	
	params.gravity = Gravity.LEFT | Gravity.TOP;

	params.x = 0;
	params.y = 0;
	params.width = WindowManager.LayoutParams.MATCH_PARENT;
	params.height = WindowManager.LayoutParams.MATCH_PARENT;

	wm.addView(btn, params);

    }

    private void close() {
	wm.removeView(btn);
    }
}
