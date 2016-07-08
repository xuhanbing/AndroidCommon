package com.hanbing.module.android.menu.model;

import com.hanbing.module.android.menu.bean.MenuItem;

/**
 * Created by hanbing on 2016/7/7
 */
public interface IMenuModel {


    /**
     * Index of menu items except root
     * The root menu item is -1 and subs are 0
     * @param menuItem
     * @return
     */
    public int indexOfMenuItem(MenuItem menuItem);

    /**
     * Count of sub menu levels.
     * @param menuItem
     * @return
     */
    public int countOfSubMenuLevels(MenuItem menuItem);

    /**
     * Check if is root menu item
     * @param menuItem
     * @return
     */
    public boolean isRootMenuItem(MenuItem menuItem);

    /**
     * Check if is last menu item(No sub items).
     * @param menuItem
     * @return
     */
    public boolean isLastMenuItem(MenuItem menuItem);

    /**
     * Find root menu item
     * @param menuItem
     * @return
     */
    public MenuItem findRootMenuItem(MenuItem menuItem);
}
