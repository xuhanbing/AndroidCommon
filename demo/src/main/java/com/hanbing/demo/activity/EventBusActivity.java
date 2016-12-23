package com.hanbing.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hanbing.demo.BaseActivity;
import com.hanbing.demo.R;
import com.hanbing.library.android.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusActivity extends BaseActivity {


    EventBus mEventBus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);


        mEventBus = EventBus.builder().eventInheritance(false).build();

        mEventBus.postSticky(new Message(" Message "));
        mEventBus.postSticky(new MessageChild(" MessageChild "));

    }

    @Override
    protected void onStart() {
        super.onStart();
        mEventBus.register(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
//        mEventBus.unregister(this);

    }



    @Subscribe
    public void onGetMessage(Message msg) {
        LogUtils.e("onGetMessage:" + msg);

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, sticky = true)
    public void onGetMessage2(Message msg) {
        LogUtils.e("onGetMessage2:" + msg);
    }

    @Subscribe
    public void onGetMessage3(Message msg) {
        LogUtils.e("onGetMessage3:" + msg);
    }

    public void sendSimpleMsg(View view) {
//        mEventBus.post(" Simple string msg. ");
        mEventBus.post(new Message(" Message "));
    }

    public void open(View view) {
        startActivity(new Intent(this, EventBusActivity.class));
    }

    class Message  {
        String msg;

        public Message(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return msg;
        }
    }

    class MessageChild extends Message {

        public MessageChild(String msg) {
            super(msg);
        }
    }
}
