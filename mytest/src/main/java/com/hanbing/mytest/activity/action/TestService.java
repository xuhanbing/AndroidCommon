package com.hanbing.mytest.activity.action;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.mytest.service.SimpleService;
import com.hanbing.mytest.utils.MyServiceAidl;

public class TestService extends AppCompatActivity {


    ServiceConnection simpleServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            LogUtils.e("onServiceConnected : SimpleService");

//            ((SimpleServiceBinder)service).getService().print("-=-=-SimpleService");

            MyServiceAidl impl = MyServiceAidl.Stub.asInterface(service);



            try {
                impl.print("-=-=-SimpleService");
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_service);


        bindService(new Intent(this, SimpleService.class), simpleServiceConnection, Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(simpleServiceConnection);
    }
}
