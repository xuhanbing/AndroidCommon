package com.hanbing.mytest.service;

import com.hanbing.mytest.MyTestApplication;
import com.hanbing.mytest.receiver.ScreenLockReceiver;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class TestService extends Service {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		System.out.println("service oncreate");
		super.onCreate();
		
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
//				restart();
			}
		}).start();
	}
	
	protected void restart() {
		// TODO Auto-generated method stub
		MyTestApplication app = (MyTestApplication) getApplication();
		app.restart();
	}

	ScreenLockReceiver mReceiver = new ScreenLockReceiver();
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("service onstart");
		
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_ON);
		
		registerReceiver(mReceiver, filter);
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	

	/* (non-Javadoc)
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		System.out.println("service ondestory");
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}
}
