package com.hanbing.mytest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.common.util.LogUtils;
import com.hanbing.mytest.utils.MyServiceAidl;
import com.hanbing.mytest.utils.MyServiceAidl.Stub;

/**
 * Created by hanbing on 2016/6/1.
 */
public  class SimpleService extends Service {


    SimpleServiceBinder mBinder;


    MyServiceAidl.Stub mStub = new MyServiceAidl.Stub() {

        @Override
        public void print(String msg) throws RemoteException {
            LogUtils.e("AIDL stub print : " + msg);
        }
    };


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return mStub;
    }

    @Override
    public void onCreate() {
        super.onCreate();


    }

//    @Override
//    public void print(String msg) throws RemoteException {
//        LogUtils.e("AIDL print : " + msg);
//    }
//
//    @Override
//    public IBinder asBinder() {
//        return mStub;
//    }
}