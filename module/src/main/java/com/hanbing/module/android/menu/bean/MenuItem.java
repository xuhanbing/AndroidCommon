package com.hanbing.module.android.menu.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanbing on 2016/7/7
 */
public abstract class MenuItem<T extends MenuItem> implements Serializable{


    public abstract String getId();

    public abstract List getSubMenuItems();

    public abstract  T getParent();

    public abstract String getTitle();


    public boolean isRootMenuItem() {
        return null == getParent();
    }
    public boolean hasNoSubMenuItems() {
        return null == getSubMenuItems() || 0 == getSubMenuItems().size();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MenuItem) {
            MenuItem menuItem = (MenuItem) o;

            if (getId() .equals(menuItem.getId()))
                return false;

            if (null == getTitle()) {
                return null == menuItem.getTitle();
            }

            return getTitle().equals(menuItem.getTitle());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (null == getId() ? 0 :getId().hashCode())
                + (null == getTitle() ? 0 : getTitle().hashCode());
    }


}
