package com.hanbing.library.android.adapter;

import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanbing on 2016/8/3
 */
public abstract class BaseEditableAdapter<VH extends ViewHolder, Bean> extends BaseAdapter<VH> implements EditableAdapter<Bean> {

    protected List<Bean> mDataList;
    protected List<Bean> mSelectedItems = new ArrayList<>();
    protected boolean mEditMode;
    protected AdapterView mAdapterView;

    public BaseEditableAdapter() {
    }

    public BaseEditableAdapter(List<Bean> dataList) {
        mDataList = dataList;
    }

    @Override
    public void beginEdit() {
        mEditMode = true;
        notifyDataSetChanged();
    }

    @Override
    public void delete(List<Bean> list) {

        if (null == mDataList)
            return;

        if (null != list && list.size() > 0) {

            mDataList.removeAll(list);
            mSelectedItems.removeAll(list);

            notifyDataSetChanged();
        }
    }

    @Override
    public void deleteSelectedItems() {
        if (!mEditMode)
            return;

        if (mSelectedItems.size() <= 0)
            return;

        if (null != mDataList)
            mDataList.removeAll(mSelectedItems);

        mSelectedItems.clear();

        notifyDataSetChanged();
    }

    @Override
    public void endEdit() {
        mEditMode = false;
        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public List<Bean> getSelectedItems() {
        return mSelectedItems;
    }

    @Override
    public boolean isEditMode() {
        return mEditMode;
    }

    public void select(int position) {
        if (null == mDataList || mDataList.isEmpty())
            return;

        int size = mDataList.size();
        if (position >= size || position < 0)
            return;

        select(mDataList.get(position));
    }

    @Override
    public void select(Bean object) {
        if (!mEditMode)
            return;

        if (mSelectedItems.contains(object)) {
            mSelectedItems.remove(object);
        } else {
            mSelectedItems.add(object);
        }

        notifyDataSetChanged();
    }

    @Override
    public void selectAll() {
        if (!mEditMode)
            return;
        if (null == mDataList || mDataList.size() <= 0)
            return;

        mSelectedItems.clear();
        mSelectedItems.addAll(mDataList);

        notifyDataSetChanged();

    }
    @Override
    public void inverseSelect() {
        if (!mEditMode)
            return;
        if (null == mDataList || mDataList.size() <= 0)
            return;

        List<Bean> tmpList = new ArrayList<>();
        tmpList.addAll(mDataList);

        for (Bean bean : mSelectedItems) {
            tmpList.remove(bean);
        }

        mSelectedItems.clear();
        mSelectedItems.addAll(tmpList);

        notifyDataSetChanged();
    }

    @Override
    public void unselectAll() {
        if (!mEditMode)
            return;

        mSelectedItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public boolean isSelected(Bean object) {
        return mSelectedItems.contains(object);
    }

    public void setAdapterView(AdapterView adapterView) {
       setAdapterView(adapterView, null);
    }

    public void setAdapterView(AdapterView adapterView, AdapterView.OnItemClickListener onItemClickListener) {
        if (null == adapterView)
            return;
        this.mAdapterView = adapterView;
        adapterView.setOnItemClickListener(wrapOnItemClickListener(onItemClickListener));
    }

    public AdapterView.OnItemClickListener wrapOnItemClickListener(final AdapterView.OnItemClickListener onItemClickListener) {
        AdapterView.OnItemClickListener wrapOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                select(position);
                if (null != onItemClickListener) onItemClickListener.onItemClick(parent, view, position, id);
            }
        };

        return wrapOnItemClickListener;
    }
}
