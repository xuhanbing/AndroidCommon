package com.hanbing.module.android.menu.presenter;

import android.view.Menu;

import com.hanbing.module.android.menu.bean.MenuItem;
import com.hanbing.module.android.menu.model.IMenuModel;

/**
 * Created by hanbing on 2016/7/7
 */
public interface IMenuPresenter extends IMenuModel {

    /**
     * Select menu item
     * @param menuItem
     */
    public void selectMenu(MenuItem menuItem);
}
