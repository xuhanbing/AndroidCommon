package com.hanbing.mytest.activity.action;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.widget.TextView;

import com.common.util.LogUtils;
import com.common.util.SystemUtils;
import com.common.util.ViewUtils;
import com.hanbing.mytest.R;

public class TestAnimation extends AppCompatActivity {


    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_animation);

        textView = ViewUtils.findViewById(this, R.id.text);
    }

    public void start(View view) {


        LogUtils.e("start");



        ObjectAnimator scaleX = ObjectAnimator.ofFloat(textView, "scaleX", 1.0f, 0.00f);
        scaleX.setDuration(1000);
        scaleX.setRepeatCount(ValueAnimator.INFINITE);
        scaleX.setRepeatMode(ValueAnimator.REVERSE);


       ViewGroup parent = (ViewGroup) textView.getParent();
        parent.setBackgroundColor(Color.RED);
        int[] location = new int[2];


        float x = parent.getWidth() - textView.getWidth() - textView.getX();
        float y = parent.getHeight() - textView.getHeight() - textView.getY();

        ObjectAnimator translateX = ObjectAnimator.ofFloat(textView, "translationX", 0, x);
        translateX .setDuration(1000);
//        translateX.setRepeatMode(ValueAnimator.REVERSE);
        translateX.setRepeatCount(ValueAnimator.INFINITE);
        translateX.setInterpolator(new BounceInterpolator());

        ObjectAnimator translateY = ObjectAnimator.ofFloat(textView, "translationY", 0, y);
        translateY .setDuration(1000);
        translateY.setRepeatMode(ValueAnimator.REVERSE);
        translateY.setRepeatCount(ValueAnimator.INFINITE);

        AnimatorSet animatorSet = new AnimatorSet();


        animatorSet.setDuration(1000);

        animatorSet.playTogether(scaleX, translateX);

        animatorSet.start();
    }

}
