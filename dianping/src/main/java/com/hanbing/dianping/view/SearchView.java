package com.hanbing.dianping.view;

import android.content.Context;
import android.util.AttributeSet;

import com.hanbing.library.android.view.SearchEditText;
import com.hanbing.dianping.R;


/**
 * Created by hanbing on 2016/6/14.
 */
public class SearchView extends SearchEditText {

    static final int RES_SEARCH = R.drawable.main_ic_home_search;
    static final int RES_CLOSE = R.drawable.edit_close;
    static final int RES_BG = R.drawable.search_title_bar;

    public SearchView(Context context) {
        super(context);
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    void init() {

    }
}
