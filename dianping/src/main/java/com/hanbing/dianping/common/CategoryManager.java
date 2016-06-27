package com.hanbing.dianping.common;

import android.content.Context;

import com.hanbing.dianping.model.Category;

import org.json.JSONArray;

/**
 * Created by hanbing on 2016/6/8.
 */
public class CategoryManager {


    static String JSON = null;
    static {

        JSONArray jsonArray = null;

        JSON = "";
    }

    private static CategoryManager ourInstance ;

    public static CategoryManager getInstance(Context context) {

        if (null == ourInstance) {
            ourInstance = new CategoryManager(context);
        }
        return ourInstance;
    }


    Category mRootCategory;

    private CategoryManager(Context context) {
    }

    public Category getAllCategory() {
        return mRootCategory;
    }

    public Category getParentCategory(Category category) {
        if (null == category)
            return null;

        return category.getParent();
    }
}
