package com.hanbing.demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hanbing.demo.BaseActivity;
import com.hanbing.demo.R;
import com.hanbing.library.android.tool.ListenerManager;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.TimeUtils;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;
import com.squareup.otto.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OttoActivity extends BaseActivity {


    class TestBase {
        public void onGet(int msg) {
            LogUtils.e("get int: " + msg);
        }

//        public void onGet(Integer msg) {
//            LogUtils.e("get Integer: " + msg);
//        }

    }
     class Test extends TestBase{
        public  void onGet(String msg) {
            LogUtils.e("Test : " + msg);

        }

        public  void onGet(String msg, String msg2) {
            LogUtils.e("Test : " + msg + "," + msg2);
        }

    }
    @BindView(R.id.textView)
    TextView mTextView;


    Bus mBus = new Bus();


    ListenerManager<Test> mListenerManager = new ListenerManager<Test>(){};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_otto);
        ButterKnife.bind(this);


        Test test1 = new Test() {
            @Override
            public void onGet(String msg) {
                LogUtils.e("Test1 : " + msg);
            }

//            @Override
//            public void onGet(String msg, String msg2) {
//                LogUtils.e("Test1 : " + msg + "," + msg2);
//
//            }
        };
        Test test2 = new Test() /*{
            @Override
            public void onGet(String msg) {
                LogUtils.e("Test2 : " + msg);
            }

            @Override
            public void onGet(String msg, String msg2) {
                LogUtils.e("Test2 : " + msg + "," + msg2);

            }
        }*/;
        mListenerManager.addListener(test1, test2);

//
//        mListenerManager.callback("mm");
//        mListenerManager.callback("mm", "mm2");
        mListenerManager.callback(1234);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mBus.register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        mBus.unregister(this);
    }

    public void send(View view) {
        mBus.post(new MyEvent("发送一个消息:" + TimeUtils.getTime()));
    }


    @Produce
    public MyEvent getMessage() {
        return new MyEvent("meassage event:" + TimeUtils.getTime());
    }

    @Produce
    public String getMessageString() {
        return "message string :" + TimeUtils.getTime();
    }

//    @Produce
//    public MyEvent getMessage2() {
//        return new MyEvent("getMessage -> 发送一个消息2:" + TimeUtils.getTime());
//    }

    @Subscribe
    public void onGetMessage(MyEvent myEvent) {
        LogUtils.e("onGetMessage:" + myEvent.msg);
    }

    @Subscribe
    public void onGetMessage2(MyEvent myEvent) {
        LogUtils.e("onGetMessage2:" + myEvent.msg);
    }

    @Subscribe
    public void onGetMessage3(String msg) {
        LogUtils.e("onGetMessage3:" + msg);
    }


    class MyEvent {
        public String msg;

        public MyEvent(String msg) {
            this.msg = msg;
        }
    }
}
