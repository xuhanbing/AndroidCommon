/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��6��26�� 
 * Time : ����11:17:33
 */
package com.hanbing.mytest.activity.view;

import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.mytest.R;
import com.hanbing.mytest.activity.BaseActivity;
import com.hanbing.mytest.fragment.NumFragment;
import com.hanbing.mytest.view.RotatePlate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * TestDraw.java
 * @author hanbing
 * @date 2014��6��26��
 * @time ����11:17:33
 */
public class TestDraw extends BaseActivity {


    ViewPager viewPager;


    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

//        RotatePlate tp = new RotatePlate(this);
////        tp.setBackgroundColor(Color.LTGRAY);
//        tp.setBackgroundResource(R.drawable.circle);
//        tp.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//        setContentView(tp);


        final List<Fragment> fragments = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            NumFragment fragment = NumFragment.newInstance(i);
            fragments.add(fragment);
        }

        setContentView(R.layout.activity_test_draw);

        viewPager = ViewUtils.findViewById(this, R.id.viewPager);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "fragment " + position;
            }
        });

        RotatePlate rotatePlate = ViewUtils.findViewById(this, R.id.rotatePlate);

        rotatePlate.setupWithViewPager(viewPager);

        viewPager.setCurrentItem(0);

        viewPager.addOnPageChangeListener(rotatePlate);
    }


}
