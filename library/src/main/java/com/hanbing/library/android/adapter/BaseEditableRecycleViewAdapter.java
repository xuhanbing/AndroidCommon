package com.hanbing.library.android.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanbing on 2016/3/10.
 */
public abstract class BaseEditableRecycleViewAdapter<VH extends  RecyclerView.ViewHolder, Bean> extends RecyclerView.Adapter<VH> implements EditableAdapter<Bean> {

    protected List<Bean> mDataList;
    protected List<Bean> mSelectedItems = new ArrayList<>();
    protected boolean mEditMode;

    public BaseEditableRecycleViewAdapter() {
    }

    public BaseEditableRecycleViewAdapter(List<Bean> dataList) {
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
}
