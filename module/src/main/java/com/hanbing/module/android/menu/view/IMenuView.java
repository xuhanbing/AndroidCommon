package com.hanbing.module.android.menu.view;

import com.hanbing.module.android.menu.bean.MenuItem;

import java.util.List;

/**
 * Created by hanbing on 2016/7/7
 */
public interface IMenuView {

    /**
     * Open menu
     * @param menuItem  menu item
     * @param selectedMenuItem selected menu item
     */
    public void open(MenuItem menuItem, MenuItem selectedMenuItem);

    /**
     * Close menu
     * @param selectedMenuItem current selected menu
     */
    public void close(MenuItem selectedMenuItem);

    /**
     * Select menu item
     * @param selectedMenuItem
     */
    public void select(MenuItem selectedMenuItem);

}
