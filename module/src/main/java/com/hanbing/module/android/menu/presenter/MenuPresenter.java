package com.hanbing.module.android.menu.presenter;

import com.hanbing.module.android.menu.bean.MenuItem;
import com.hanbing.module.android.menu.model.IMenuModel;
import com.hanbing.module.android.menu.model.MenuModel;
import com.hanbing.module.android.menu.view.IMenuView;

/**
 * Created by hanbing on 2016/7/7
 */
public class MenuPresenter implements IMenuPresenter{

    IMenuModel mMenuModel;
    IMenuView mMenuView;

    public MenuPresenter(IMenuView menuView) {
        this.mMenuModel = new MenuModel();
        this.mMenuView = menuView;
    }

    @Override
    public void selectMenu(MenuItem menuItem) {
        if (null == mMenuModel || null == mMenuView)
            return;

        if (isLastMenuItem(menuItem)) {
            mMenuView.close(menuItem);
        } else {
            mMenuView.select(menuItem);
        }
    }

    @Override
    public int indexOfMenuItem(MenuItem menuItem) {
        return mMenuModel.indexOfMenuItem(menuItem);
    }

    @Override
    public int countOfSubMenuLevels(MenuItem menuItem) {
        return mMenuModel.countOfSubMenuLevels(menuItem);
    }

    @Override
    public boolean isRootMenuItem(MenuItem menuItem) {
        return mMenuModel.isRootMenuItem(menuItem);
    }

    @Override
    public boolean isLastMenuItem(MenuItem menuItem) {
        return mMenuModel.isLastMenuItem(menuItem);
    }

    @Override
    public MenuItem findRootMenuItem(MenuItem menuItem) {
        return mMenuModel.findRootMenuItem(menuItem);
    }
}
