package com.hanbing.mytest.activity.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ToastUtils;

public class TestBroadcast extends AppCompatActivity {


    static final String ACTION_BROADCAST = "This is broadcast";
    static final String ACTION_LOCAL_BROADCAST = "This is local broadcast";

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.e("receive broadcast : " + intent.getAction());
            ToastUtils.showToast(context, intent.getAction());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_broadcast);
    }

    public void registerLocalBroadcast(View view) {

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_LOCAL_BROADCAST);
        manager.registerReceiver(broadcastReceiver, filter);
    }

    public void unregisterLocalBroadcast(View view) {

        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    public void unregisterBroadcast(View view) {

        unregisterReceiver(broadcastReceiver);
    }

    public void registerBroadcast(View view) {


        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_BROADCAST);

        registerReceiver(broadcastReceiver, filter);
    }

    public void sendBroadcast(View view) {

        sendBroadcast(new Intent(ACTION_BROADCAST));
    }

    public void sendLocalBroadcast(View view) {

        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(ACTION_LOCAL_BROADCAST));
    }
}
