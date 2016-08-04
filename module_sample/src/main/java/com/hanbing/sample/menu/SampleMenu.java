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

            @Override
            public void onClose() {

            }
        });


        getSupportFragmentManager().beginTransaction().add(menuFragment, "Menu").commit();

    }

    public static MyMenuItem createMenuItem(MyMenuItem parent, int countOfLevels, String title) {

        if (null == parent)
        {
            parent = new MyMenuItem();
        }

        List<MyMenuItem> childs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {

            MyMenuItem sub = new MyMenuItem();
            if (0 == i) {
                sub.setId(""+0);
                sub.setTitle("全部");
            } else {
                sub.setId(""+i);
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
