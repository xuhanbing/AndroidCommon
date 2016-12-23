/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2015年5月11日
 * Time : 下午2:45:58
 */
package com.hanbing.mytest.activity.action;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * TestKeyboard
 * @author hanbing 
 * @date 2015年5月11日
 * @time 下午2:45:58
 */
public class TestKeyboardDialog extends Activity {

	
	EditText input;
	/**
	 * 
	 */
	public TestKeyboardDialog() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setTitle("Keyboard Dialog");
		
		int mode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
		getWindow().setSoftInputMode(mode);
		
		setContentView(R.layout.activity_keyboard_dialog);
		
		input = (EditText) findViewById(R.id.et_input);
	}
	
	private void hideKeyboard()
	{
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
		imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
	}
	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		
		return super.onKeyDown(keyCode, event);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyUp(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode
				== KeyEvent.KEYCODE_BACK)
		{
			hideKeyboard();
			finish();
			overridePendingTransition(R.anim.anim_in_from_down, R.anim.anim_out_to_down);
			
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	/**
	 * 
	 */
	protected void startNewActivity() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this, TestKeyboardDialog.class);
		intent.putExtra("isNew", true);
		startActivity(intent);
	}
	
	
	
}
