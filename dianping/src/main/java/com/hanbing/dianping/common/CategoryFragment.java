package com.hanbing.dianping.common;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hanbing.dianping.R;
import com.hanbing.dianping.model.Category;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.BindView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@ContentView(R.layout.fragment_category)
public class CategoryFragment extends CategoryFragmentBase {


    private static final String KEY_SELECTED_CATEGORY = "key_selected_category";

    @BindView(R.id.lv_category_parent)
    ListView mParentListView;

    @BindView(R.id.lv_category_child)
    ListView mChildListView;


    ParentCategoryAdapter mParentCategoryAdpater;
    ChildCategoryAdapter mChildCategoryAdapter;


    Category mSelectedCategory;

    public static CategoryFragment newInstance(Category category) {

        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_SELECTED_CATEGORY, category);

        categoryFragment.setArguments(args);

        return categoryFragment;
    }

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initViews(View view) {
        super.initViews(view);

        if (null != getArguments()) {

            mSelectedCategory = (Category) getArguments().getSerializable(KEY_SELECTED_CATEGORY);
        }

        if (null != mSelectedCategory) {
            Category parent = mSelectedCategory.getParent();
            mParentCategoryAdpater = new ParentCategoryAdapter(mCategory, parent);
            mChildCategoryAdapter = new ChildCategoryAdapter(parent, mSelectedCategory);
        } else {
            mParentCategoryAdpater = new ParentCategoryAdapter(mCategory);
            mChildCategoryAdapter = new ChildCategoryAdapter(null);
        }


        mParentListView.setAdapter(mParentCategoryAdpater);
        mChildListView.setAdapter(mChildCategoryAdapter);

        mParentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Category category = (Category) parent.getItemAtPosition(position);

                mParentCategoryAdpater.select(category);

                if (null != category) {
                    List<Category> childs = category.getChilds();
                    if (null != childs && childs.size() > 0) {
                        mChildCategoryAdapter.update(category, null);
                    } else {
                        onSelected(category);
                    }


                }
            }
        });

        mChildListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) parent.getItemAtPosition(position);
                mChildCategoryAdapter.update(category.getParent(), category);

                if (null != category) {
                    onSelected(category);

                }
            }
        });
    }


    class ParentCategoryAdapter extends CategoryAdapter {

        public ParentCategoryAdapter(Category category) {
            super(category);
        }

        public ParentCategoryAdapter(Category category, Category selectedCategory) {
            super(category, selectedCategory);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }

    class ChildCategoryAdapter extends CategoryAdapter {


        public ChildCategoryAdapter(Category category) {
            super(category);
        }

        public ChildCategoryAdapter(Category category, Category selectedCategory) {
            super(category, selectedCategory);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
