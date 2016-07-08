package com.hanbing.sample.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hanbing.module.android.menu.bean.MenuItem;
import com.hanbing.module.android.menu.view.MenuFragment;
import com.hanbing.sample.R;

import java.util.ArrayList;
import java.util.List;

public class SampleMenu extends AppCompatActivity {


    MenuItem mMenuItem;
    MenuItem mSelectedMenuItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_menu);

        mMenuItem = createMenuItem(null, 4, "menu");
    }

    public void onClick(final View view) {

        MenuFragment menuFragment = MenuFragment.newInstance(mMenuItem, mSelectedMenuItem);

        menuFragment.setOnSelectedListener(new MenuFragment.OnSelectedListener() {
            @Override
            public void onSelected(MenuItem menuItem) {
                mSelectedMenuItem = menuItem;


                String title = menuItem.getTitle();

                if (menuItem.hasNoSubMenuItems() && null != menuItem.getParent()) {
                    title = title + menuItem.getParent().getTitle();
                }
                ((Button) view).setText(title);
            }
        });


        getSupportFragmentManager().beginTransaction().add(menuFragment, "Menu").commit();

    }

    public static MenuItem createMenuItem(MenuItem parent, int countOfLevels, String title) {

        if (null == parent)
        {
            parent = new MenuItem();
        }

        List<MenuItem> childs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            MenuItem sub = new MenuItem();
            if (0 == i) {
                sub.setIndex(0);
                sub.setTitle("全部");
            } else {
                sub.setIndex(i);
                sub.setTitle(title + "-" + i);

                if (countOfLevels > 1) {
                    sub = createMenuItem(sub, countOfLevels - 1, sub.getTitle());
                }
            }

            sub.setParent(parent);
            childs.add(sub);
        }

        parent.setSubMenuItems(childs);
        return parent;
    }
}
