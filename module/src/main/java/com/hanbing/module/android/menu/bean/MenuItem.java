package com.hanbing.module.android.menu.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanbing on 2016/7/7
 */
public class MenuItem <T extends MenuItem>implements Serializable{

    /**
     * index
     */
    int index;

    /**
     * title
     */
    String title;

    /**
     * root
     */
    T mParent;

    /**
     * subs
     */
    List<T> mSubMenuItems;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<T> getSubMenuItems() {
        return mSubMenuItems;
    }

    public void setSubMenuItems(List<T> subMenuItems) {
        mSubMenuItems = subMenuItems;
    }

    public T getParent() {
        return mParent;
    }

    public void setParent(T parent) {
        mParent = parent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isRootMenuItem() {
        return null == mParent;
    }
    public boolean hasNoSubMenuItems() {
        return null == mSubMenuItems || 0 == mSubMenuItems.size();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MenuItem) {
            MenuItem menuItem = (MenuItem) o;

            if (index != menuItem.index)
                return false;

            if (null == title) {
                return null == menuItem.title;
            }

            return title.equals(menuItem.title);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return ((Integer)index).hashCode()
                + (null == title ? 0 : title.hashCode());
    }


}
