package com.hanbing.sample.menu;

import com.hanbing.module.android.menu.bean.MenuItem;

import java.util.List;

/**
 * Created by hanbing on 2016/8/4
 */
public class MyMenuItem extends MenuItem {

    String id;

    String title;

    MyMenuItem parent;

    List<MyMenuItem> subMenuItems;

    @Override
    public MyMenuItem getParent() {
        return parent;
    }

    public void setParent(MyMenuItem parent) {
        this.parent = parent;
    }

    @Override
    public List<MyMenuItem> getSubMenuItems() {
        return subMenuItems;
    }

    public void setSubMenuItems(List<MyMenuItem> subMenuItems) {
        this.subMenuItems = subMenuItems;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
