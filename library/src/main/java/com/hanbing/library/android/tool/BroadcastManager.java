package com.hanbing.library.android.tool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by hanbing on 2016/9/25.
 */
public class BroadcastManager {
    static BroadcastManager mBroadcastManager;
    public static BroadcastManager getInstance(Context context) {
        if (null == mBroadcastManager) mBroadcastManager = new BroadcastManager(context);
        return mBroadcastManager;
    }

    LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastManager(Context context) {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(context.getApplicationContext());
    }


    public void register(BroadcastReceiver receiver, String ... actions) {
        IntentFilter filter = new IntentFilter();
        if (null != actions && actions.length > 0) {
            for (String action :
                    actions) {
                filter.addAction(action);

            }
        }
        mLocalBroadcastManager.registerReceiver(receiver, filter);
    }

    public void unreigster(BroadcastReceiver receiver) {
        mLocalBroadcastManager.unregisterReceiver(receiver);
    }

    public void sendBroadcast(Intent intent) {
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    public void sendBroadcast( String action) {
        sendBroadcast(new Intent(action));
    }
}
