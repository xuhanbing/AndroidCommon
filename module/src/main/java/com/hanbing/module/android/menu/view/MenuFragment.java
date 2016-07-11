package com.hanbing.module.android.menu.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hanbing.module.android.menu.bean.MenuItem;
import com.hanbing.module.android.menu.presenter.IMenuPresenter;
import com.hanbing.module.android.menu.presenter.MenuPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanbing on 2016/7/7
 */
public class MenuFragment extends Fragment implements IMenuView, AdapterView.OnItemClickListener {

    public static final String MENU_ITEM = "menuItem";
    public static final String SELECTED_MENU_ITEM = "selectedMenuItem";

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MenuItem menuItem1 = (MenuItem) parent.getAdapter().getItem(position);
        mMenuPresenter.selectMenu(menuItem1);

    }

    public interface OnSelectedListener {
        public void onSelected(MenuItem menuItem);
    }

    protected IMenuPresenter mMenuPresenter;
    protected LinearLayout mLinearLayout;
    protected List<ListView> mListViews;
    OnSelectedListener mOnSelectedListener;

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        mOnSelectedListener = onSelectedListener;
    }

    public static MenuFragment newInstance(MenuItem menuItem, MenuItem selectedMenuItem) {
        Bundle args = new Bundle();
        args.putSerializable(MENU_ITEM, menuItem);
        args.putSerializable(SELECTED_MENU_ITEM, selectedMenuItem);

        MenuFragment menuFragment = new MenuFragment();
        menuFragment.setArguments(args);

        return menuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        MenuItem menuItem = null;
        MenuItem selectedMenuItem = null;
        Bundle arguments = getArguments();
        if (null != arguments) {
            menuItem = (MenuItem) arguments.getSerializable(MENU_ITEM);
            selectedMenuItem = (MenuItem) arguments.getSerializable(SELECTED_MENU_ITEM);
        }

        mMenuPresenter = new MenuPresenter(this);
        mLinearLayout = new LinearLayout(getContext());
        mListViews = new ArrayList<>();

        initLayout(menuItem);
        open(menuItem, selectedMenuItem);

        mLinearLayout.setBackgroundColor(0x80000000);
        return mLinearLayout;
    }

   protected void initLayout(MenuItem menuItem) {
        if (null == menuItem || menuItem.hasNoSubMenuItems())
            return;

        int count = mMenuPresenter.countOfSubMenuLevels(menuItem);

        for (int i = 0; i < count; i++) {
            ListView listView = new ListView(getContext());
            listView.setOnItemClickListener(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            mLinearLayout.addView(listView, params);
            mListViews.add(listView);
        }
    }

    @Override
    public void open(MenuItem menuItem, MenuItem selectedMenuItem) {
        if (null == menuItem || menuItem.hasNoSubMenuItems())
            return;

        if (null == selectedMenuItem) {

            List<MenuItem> subMenuItems = menuItem.getSubMenuItems();
            if (null != subMenuItems && subMenuItems.size() > 0) {
                ListView listView = mListViews.get(0);

                MenuItemAdapter adapter = new MenuItemAdapter(subMenuItems, subMenuItems.get(0));
                listView.setAdapter(adapter);
            }

        } else {
            int index = mMenuPresenter.indexOfMenuItem(selectedMenuItem);
            while (index >= 0) {

                ListView listView = mListViews.get(index);

                MenuItem parent = selectedMenuItem.getParent();
                listView.setAdapter(new MenuItemAdapter(parent.getSubMenuItems(), selectedMenuItem));

                index--;

                selectedMenuItem = parent;
            }

        }


    }

    @Override
    public void close(MenuItem selectedMenuItem) {

        select(selectedMenuItem);

        if (null != mOnSelectedListener) {
            mOnSelectedListener.onSelected(selectedMenuItem);
        }

    }

    @Override
    public void select(MenuItem selectedMenuItem) {
        int index = mMenuPresenter.indexOfMenuItem(selectedMenuItem);
        if (index < 0)
            return;

        //Update selected menu item
        MenuItemAdapter adapter = (MenuItemAdapter) mListViews.get(index).getAdapter();
        adapter.setSelectedMenuItem(selectedMenuItem);

        int levels = mMenuPresenter.countOfSubMenuLevels(mMenuPresenter.findRootMenuItem(selectedMenuItem));

        //Show sub menu items if have
        if (index + 1 < levels) {
            List<MenuItem> subMenuItems = selectedMenuItem.getSubMenuItems();
            if (null != subMenuItems && subMenuItems.size() > 0)
                mListViews.get(index + 1).setAdapter(new MenuItemAdapter(subMenuItems));
            else
                mListViews.get(index + 1).setAdapter(null);
        }


        //Clear next next menu items
        for (int i = index + 2; i < mListViews.size(); i++) {
            mListViews.get(i).setAdapter(null);
        }
    }

    class MenuItemAdapter extends BaseAdapter {

        List<MenuItem> mMenuItems;
        MenuItem mSelectedMenuItem;

        public MenuItemAdapter(List<MenuItem> menuItems) {
            mMenuItems = menuItems;
        }

        public MenuItemAdapter(List<MenuItem> menuItems, MenuItem selectedMenuItem) {
            mMenuItems = menuItems;
            mSelectedMenuItem = selectedMenuItem;
        }

        @Override
        public int getCount() {
            return null == mMenuItems ? 0 : mMenuItems.size();
        }

        @Override
        public Object getItem(int position) {
            return null == mMenuItems ? null : mMenuItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (null == convertView) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            TextView textView = (TextView) convertView.findViewById(android.R.id.text1);

            MenuItem menuItem = mMenuItems.get(position);
            textView.setText(menuItem.getTitle());

            if (menuItem.equals(mSelectedMenuItem)) {
                convertView.setBackgroundColor(Color.LTGRAY);
            } else {
                convertView.setBackgroundColor(Color.TRANSPARENT);
            }

            return convertView;
        }

        public void setSelectedMenuItem(MenuItem selectedMenuItem) {
            mSelectedMenuItem = selectedMenuItem;
            notifyDataSetChanged();
        }
    }
}
