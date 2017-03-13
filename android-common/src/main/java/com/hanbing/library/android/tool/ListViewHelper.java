package com.hanbing.library.android.tool;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * Created by hanbing on 2016/11/14
 */

public abstract class ListViewHelper<DataAdapter extends BaseAdapter> extends AbsListViewHelper<ListView, DataAdapter> {


    ListView mListView;
    public ListViewHelper(Context context) {
        super(context);
    }

    @Override
    public final void initDataView(ListView listView) {
        if (null != listView)
        {
            initHeadersAndFooters(listView);
            addLoadMoreIfNeed();
            setEmptyViewIfNeed();

            initListView(listView);
        }
    }

    @Override
    public ListView createDataView() {
        return createListView();
    }

    public ListView createListView() {
        return mListView = new ListView(mContext);
    }

    public  void initHeadersAndFooters(ListView listView) {

    }

    public  void initListView(ListView listView) {
        listView.setAdapter(mDataAdapter);
    }

}
