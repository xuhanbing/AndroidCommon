package com.hanbing.module.android.menu.model;

import com.hanbing.module.android.menu.bean.MenuItem;

import java.util.List;

/**
 * Created by hanbing on 2016/7/7
 */
public class MenuModel implements IMenuModel {

    public MenuModel() {
    }

    @Override
    public int indexOfMenuItem(MenuItem menuItem) {

        if (null == menuItem)
            return  -1;

        int count = -1;
        while (true) {
            MenuItem parent = menuItem.getParent();

            if (null == parent) {
                break;
            }

            menuItem = parent;
            count++;
        }

        return count;
    }

    @Override
    public int countOfSubMenuLevels(MenuItem menuItem) {

        int count = countLevels(menuItem);

        return count - 1;

    }

    private int countLevels(MenuItem menuItem) {
        if (null == menuItem)
            return 0;

        int count = 1;

        if (menuItem.hasNoSubMenuItems()) {

        } else {

            List<MenuItem> subMenuItems = menuItem.getSubMenuItems();

            int deep = 0;
            for (MenuItem item : subMenuItems) {
                int tmp = countLevels(item);

                if (tmp > deep) {
                    deep = tmp;
                }
            }

            count += deep;
        }
        return count;
    }


    @Override
    public boolean isRootMenuItem(MenuItem menuItem) {
        if (null == menuItem)
            return false;
        return menuItem.getParent() == null;
    }

    @Override
    public boolean isLastMenuItem(MenuItem menuItem) {
        if (null == menuItem)
            return false;
        return menuItem.hasNoSubMenuItems();
    }

    @Override
    public MenuItem findRootMenuItem(MenuItem menuItem) {
        if (null == menuItem)
            return null;
        while (menuItem.getParent() != null)
            menuItem = menuItem.getParent();

        return menuItem;
    }
}
