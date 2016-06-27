package com.hanbing.dianping.common;

import android.view.View;
import android.widget.BaseAdapter;


import com.hanbing.dianping.common.base.BaseFragment;
import com.hanbing.dianping.model.Category;

/**
 * Created by hanbing on 2016/6/8.
 */
public abstract class CategoryFragmentBase extends BaseFragment {

    public interface OnSelectedListener {
        void onSelected(Category category);
    }


    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.mOnSelectedListener = onSelectedListener;
    }

    OnSelectedListener mOnSelectedListener;

    protected  Category mCategory;

    @Override
    protected void initViews(View view) {
        super.initViews(view);

        mCategory = CategoryManager.getInstance(getActivity()).getAllCategory();
    }

    protected void onSelected(Category category) {
        if (null != mOnSelectedListener) {
            mOnSelectedListener.onSelected(category);
        }

    }

   protected  abstract class CategoryAdapter extends BaseAdapter {

        Category mCategory;
       Category mSelectedCategory;

        public CategoryAdapter(Category category) {
            this.mCategory = category;
        }

       public CategoryAdapter(Category category, Category selectedCategory) {
           this.mCategory = category;
           this.mSelectedCategory = selectedCategory;
       }

        public void update(Category category, Category selectedCategory) {
            this.mCategory = category;
            this.mSelectedCategory = selectedCategory;
            notifyDataSetChanged();
        }

       public void select(Category selectedCategory) {
           this.mSelectedCategory = selectedCategory;
           notifyDataSetChanged();
       }



        @Override
        public int getCount() {
            return (null == mCategory || null == mCategory.getChilds()) ? 0 : mCategory.getChilds().size();
        }

        @Override
        public Object getItem(int position) {

            if (null != mCategory && null != mCategory.getChilds()) {
                return mCategory.getChilds().get(position);
            }

            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }
}
