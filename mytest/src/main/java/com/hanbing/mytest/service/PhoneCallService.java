/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��5��19�� 
 * Time : ����1:33:52
 */
package com.hanbing.mytest.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * PhoneCallService.java 
 * @author hanbing 
 * @date 2014��5��19�� 
 * @time ����1:33:52
 */
public class PhoneCallService extends Service {

    public static final String TAG = "PhoneCallService";
    
    BroadcastReceiver receiver = new BroadcastReceiver() {
        
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            
            String action = intent.getAction();
            
            if (action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED))
            {
                TelephonyManager tm = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
                
                String number = intent.getStringExtra("incoming_number");
                
                if (tm.getCallState() == TelephonyManager.CALL_STATE_RINGING)
                {
                }
                
                Log.d(TAG, "get phone call from : " + number);
            }
        }
    };

    /* (non-Javadoc)
     * @see android.app.Service#onBind(android.content.Intent)
     */
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        
        Log.d(TAG, "service onCreate");
        register();
    }

   

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        
        super.onDestroy();
        Log.d(TAG, "service onDestroy");
        unregister();
    }

    /**
     * 
     */
    private void register() {
        // TODO Auto-generated method stub
        IntentFilter filter = new IntentFilter();
        
        filter.addAction(TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        
        registerReceiver(receiver, filter);
    }
    
    /**
     * 
     */
    private void unregister() {
        // TODO Auto-generated method stub
        unregisterReceiver(receiver);
    }
    
    
    

}
