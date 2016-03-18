/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2015楠烇拷4閺堬拷20閺冿拷
 * Time : 娑撳宕�6:02:06
 */
package com.hanbing.mytest.receiver;

import com.hanbing.mytest.activity.action.TestKeyguard;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

/**
 * ScreenLockReceiver
 * @author hanbing 
 * @date 2015楠烇拷4閺堬拷20閺冿拷
 * @time 娑撳宕�6:02:06
 */
public class ScreenLockReceiver extends BroadcastReceiver {

	
	Handler mHandler = new Handler();
	/**
	 * 
	 */
	public ScreenLockReceiver() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		
	    KeyguardManager mKeyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);  
	     KeyguardLock   mKeyguardLock = mKeyguardManager.newKeyguardLock("my_lockscreen");   
	     
	     mKeyguardLock.disableKeyguard(); 
		
		String action = intent.getAction();
		
		Log.e("", "action=" + action);
		if (Intent.ACTION_SCREEN_ON.equals(action))
		{
			Intent in = new Intent(context, TestKeyguard.class);
			in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(in);
		}

	}

}
