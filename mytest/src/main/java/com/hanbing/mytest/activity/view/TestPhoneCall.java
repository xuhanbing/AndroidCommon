/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��5��19�� 
 * Time : ����1:31:42
 */
package com.hanbing.mytest.activity.view;

import com.hanbing.mytest.service.PhoneCallService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;


/**
 * TestPhoneCall.java 
 * @author hanbing 
 * @date 2014��5��19�� 
 * @time ����1:31:42
 */
public class TestPhoneCall extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        TextView text = new TextView(this);
        
        String string = String.format("Welcome ", "Big big big");
        
        text.setText(string);
        
        setContentView(text);
        
        startService();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        stopService();
    }

    
    
    private void startService()
    {
        Intent intent = new Intent(this, PhoneCallService.class);
        
        startService(intent);
    }
    
    private void stopService()
    {
        Intent intent = new Intent(this, PhoneCallService.class);
        
        stopService(intent);
    }
    

}
