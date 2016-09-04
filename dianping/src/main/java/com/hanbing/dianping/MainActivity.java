package com.hanbing.dianping;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanbing.dianping.common.base.BaseActivity;
import com.hanbing.library.android.bind.annotation.BindContentView;
import com.hanbing.library.android.bind.annotation.BindView;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.library.android.view.tab.TabWidget;
import com.hanbing.dianping.core.FindFragment;
import com.hanbing.dianping.core.HomeFragment;
import com.hanbing.dianping.core.MyFragment;
import com.hanbing.dianping.core.TuanFragment;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@BindContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {


    @BindView(R.id.tab_main)
    TabWidget mTab;

    List<String> mTitles;
    int[] mTitleResIds = {R.string.title_home, R.string.title_tuan, R.string.title_find, R.string.title_my};
    int[] mIconResIds = {R.drawable.main_index_home, R.drawable.main_index_tuan, R.drawable.main_index_search, R.drawable.main_index_my};

    FragmentManager mFragmentManager;

    List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle arg0) {
        mTitles = new ArrayList<>();
        for (int id : mTitleResIds) {
            mTitles.add(getString(id));
        }

        super.onCreate(arg0);


        mFragmentManager = getSupportFragmentManager();

        if (null == arg0) {

            mFragments = new ArrayList<>();

            mFragments.add(new HomeFragment());
            mFragments.add(new TuanFragment());
            mFragments.add(new FindFragment());
            mFragments.add(new MyFragment());

        }

        showFragment(0);

        InputStream inputStream = getResources().openRawResource(R.raw.category);


        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, "gb2312"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String string = null;

        StringBuffer stringBuffer = new StringBuffer();

        try {
            while ((string = reader.readLine()) != null) {

                stringBuffer.append(string + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LogUtils.e("" + stringBuffer.toString());

    }

    @Override
    protected void initViews() {
        super.initViews();


        mTab.setTabViewFactory(new TabWidget.TabViewFactory() {
            @Override
            public View createView(TabWidget.TabSpec tabSpec) {

                View view = ViewUtils.inflate(getApplicationContext(), R.layout.item_main_tab);

                ImageView icon = ViewUtils.findViewById(view, R.id.iv_main_tab_icon);
                TextView title = ViewUtils.findViewById(view, R.id.tv_main_tab_title);

                int index = mTitles.indexOf(tabSpec.getLabel());
                icon.setImageResource(mIconResIds[index]);
                title.setText(tabSpec.getLabel());

                return view;
            }
        });

        mTab.setOnTabClickListener(new TabWidget.OnTabClickListener() {
            @Override
            public void onClick(int position) {

                showFragment(position);

            }
        });

        mTab.addTabs(mTitles);


    }

    private void showFragment(int position) {
        mFragmentManager.beginTransaction().replace(R.id.layout_main_content, mFragments.get(position)).commit();
    }
}
