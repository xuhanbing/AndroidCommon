package com.hanbing.demo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hanbing.demo.BaseActivity;
import com.hanbing.demo.R;
import com.hanbing.library.android.rxbus.RxBus;
import com.hanbing.library.android.rxbus.Subscribe;
import com.hanbing.library.android.util.LogUtils;

public class RxBusActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_bus);
    }

    @Override
    protected void onStart() {
        super.onStart();

        RxBus.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        RxBus.getInstance().unregister(this);

    }

    @Subscribe
    public void onGetMessage(String msg) {
        LogUtils.e(this + "onGetMessage : " + msg);
    }


    @Subscribe
    public void onGetMessage2(String msg) {
        LogUtils.e("onGetMessage2 : " + msg);
    }

    @Subscribe
    public void onGetMessage3(String msg) {
        LogUtils.e("onGetMessage3 : " + msg);
    }

    @Subscribe
    public void onGetMessage4(Message msg) {
        LogUtils.e("onGetMessage4 : " + msg);
    }

    @Subscribe
    public void onGetMessage5(Message msg) {
        LogUtils.e("onGetMessage5 : " + msg);
    }


    public void send(View view) {

        RxBus.getInstance().post("simple msg");

    }

    public void sendObject(View view) {
        RxBus.getInstance().post(new Message("object message"));
    }

    public void register(View view) {
        RxBus.getInstance().register(this);
    }

    public void unregister(View view) {
        RxBus.getInstance().unregister(this);
    }

    class Message {
        String msg;

        public Message(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return msg;
        }
    }
}
