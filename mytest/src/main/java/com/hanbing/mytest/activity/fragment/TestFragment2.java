package com.hanbing.mytest.activity.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.mytest.R;
import com.hanbing.mytest.activity.BaseActivity;
import com.hanbing.mytest.activity.MainActivity;
import com.hanbing.mytest.fragment.SimpleFragment;

import java.util.ArrayList;
import java.util.List;

public class TestFragment2 extends BaseActivity {

    List<Fragment> mFragments = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragment2);


        for (int i = 0; i < 4; i++) {

            SimpleFragment simpleFragment = new SimpleFragment();

            String tag = "Fragment" + i  + "-"+ System.currentTimeMillis();

            mFragments.add(simpleFragment);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.add(simpleFragment, tag).hide(simpleFragment);
            fragmentTransaction.replace(R.id.layout_content, simpleFragment, tag).hide(simpleFragment);
            fragmentTransaction.commit();
        }

//        if (null == savedInstanceState) {
//            SimpleFragment simpleFragment = new SimpleFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, simpleFragment).commit();
//        }
//        else {
//            SimpleFragment simpleFragment = (SimpleFragment) getSupportFragmentManager().findFragmentById(R.id.layout_content);
//
//            if (null == simpleFragment) {
//                simpleFragment = new SimpleFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.layout_content, simpleFragment).commit();
//
//            } else {
//
//            }
//        }



        ViewUtils.findViewAndBindOnClick(this, R.id.textView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
