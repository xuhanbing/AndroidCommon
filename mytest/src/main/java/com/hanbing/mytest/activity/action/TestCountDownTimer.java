package com.hanbing.mytest.activity.action;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.mytest.R;

public class TestCountDownTimer extends AppCompatActivity {


     Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_count_down_timer);

        button = ViewUtils.findViewById(this, R.id.button);
    }

    CountDownTimer mCountDownTimer;
    public void onClick(View view) {
        if (null != mCountDownTimer) {
            mCountDownTimer.cancel();
        }


        mCountDownTimer = new CountDownTimer(10000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                button.setText("Left " + Math.round(millisUntilFinished * 1.0f / 1000 + 0.5)+ " s");
            }

            @Override
            public void onFinish() {
                button.setText("Start");

            }
        }.start();

    }
}
