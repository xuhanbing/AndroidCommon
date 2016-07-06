package com.common.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidcommon.R;
import com.common.util.DipUtils;
import com.common.util.SystemUtils;
import com.common.util.ViewUtils;

import com.common.view.ClearableEditText;

/**
 * Created by hanbing on 2016/3/24.
 */
public class SearchView extends LinearLayout  implements TextView.OnEditorActionListener, TextWatcher, ClearableEditText.OnClearListener{

    TextView mSearchText;
    ImageView mSearchIcon;

    View mView;
    View mDefaultView;
    View mExpandView;



    TextView.OnEditorActionListener mOnEditorActionListener;
    android.widget.SearchView.OnQueryTextListener mOnQueryTextListener;

    int mClearIconResId;
    int mSearchIconResId;
    int mSearchTextSize = 0;
    int mSearchTextColor;
    int mSearchTextColorHint;
    int mSearchTextBackgroundResId;
    CharSequence mHint;
    CharSequence mText;


    Transition mTransition;
    TransitionManager mTrasitionManager;
    Scene mSceneDefault;
    Scene mSceneExpand;
    public SearchView(Context context) {
        super(context);
        init(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    void init(Context context, AttributeSet attrs)
    {
        Resources resources = context.getResources();
        mDefaultView =  ViewUtils.inflate(getContext(), R.layout.layout_searchview, this, false);
        mExpandView =  ViewUtils.inflate(getContext(), R.layout.layout_searchview_expand, this, false);

        if (null != attrs)
        {
            final TypedArray a = context.obtainStyledAttributes(
                    attrs, R.styleable.SearchView, 0, 0);

            mSearchIconResId = a.getResourceId(R.styleable.SearchView_searchIcon, android.R.drawable.ic_menu_search);
            mClearIconResId = a.getResourceId(R.styleable.SearchView_clearIcon, android.R.drawable.ic_menu_close_clear_cancel);
            mSearchTextBackgroundResId = a.getResourceId(R.styleable.SearchView_searchTextBackground, 0);

            mText = a.getText(R.styleable.SearchView_searchText);
            mHint = a.getText(R.styleable.SearchView_searchHint);

            mSearchTextColor = a.getColor(R.styleable.SearchView_searchTextColor, Color.BLACK);
            mSearchTextColorHint = a.getColor(R.styleable.SearchView_searchTextColorHint, Color.LTGRAY);
            mSearchTextSize = a.getDimensionPixelSize(R.styleable.SearchView_searchTextSize, DipUtils.dip2px(context, 15));

            a.recycle();

        }

        initTransition();



        initViews(mDefaultView);


    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
//            mTrasitionManager = new TransitionManager();
//            mTransition = TransitionInflater.from(getContext()).inflateTransition(R.transition.changebounds);
            mTrasitionManager = TransitionInflater.from(getContext()).inflateTransitionManager(R.transition.transition_searchview, this);
            mSceneDefault = Scene.getSceneForLayout(this, R.layout.layout_searchview, getContext());
            mSceneExpand = Scene.getSceneForLayout(this, R.layout.layout_searchview_expand, getContext());
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void changeScene()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {

            if (isExpand())
            {
                mTrasitionManager.transitionTo(mSceneExpand);
            } else {
                mTrasitionManager.transitionTo(mSceneDefault);
            }


        }

    }

    private void configEditText(TextView editText)
    {

    }



    void initViews(View view)
    {
        mView = view;

        changeScene();

        removeAllViews();
        addView(view);

        mSearchIcon = ViewUtils.findViewById(view, R.id.iv_search_icon);
        mSearchIcon.setImageResource(mSearchIconResId);

        mSearchText = ViewUtils.findViewById(view, R.id.et_search_text);

        mSearchText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSearchTextSize);
        mSearchText.setText(mText);
        mSearchText.setHint(mHint);
        mSearchText.setTextColor(mSearchTextColor);
        mSearchText.setHintTextColor(mSearchTextColorHint);

        if (isExpand())
        {
            TextView editText = (TextView) mSearchText;

            editText.setBackgroundResource(mSearchTextBackgroundResId);

            editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
            editText.setOnEditorActionListener(this);
            editText.requestFocus();
            editText.setOnClickListener(null);
            SystemUtils.showKeyboard(getContext(), editText);
            setOnClickListener(null);

            if (mSearchText instanceof ClearableEditText)
            {
                ClearableEditText clearableEditText = ((ClearableEditText)editText);
                clearableEditText.setOnClearListener(this);
                clearableEditText.setAlwaysShow(true);
                clearableEditText.setClearImageResource(mClearIconResId);
            }
        } else {
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onViewExpanded();
                }
            });


        }

        configEditText(mSearchText);

    }

    public void onViewExpanded() {
        initViews(mExpandView);
    }

    public void onViewCollapsed() {
        SystemUtils.hideKeyboard(getContext(), mSearchText);
        initViews(mDefaultView);
    }

    private boolean isExpand()
    {
        return mView != null && mView == mExpandView;
    }


    /**
     * Returns the query string currently in the text field.
     *
     * @return the query string
     */
    public CharSequence getQuery() {
        return mSearchText.getText();
    }

    public void setQuery(CharSequence query, boolean submit) {
        if (!isExpand())
            return;

        mSearchText.setText(query);
        if (query != null) {
            ((EditText)mSearchText).setSelection(mSearchText.length());
        }

        // If the query is not empty and submit is requested, submit the query
        if (submit && !TextUtils.isEmpty(query)) {
            onSubmitQuery();
        }
    }


    public void setQueryHint(CharSequence hint) {

        if (null != mSearchText)
        {
            mSearchText.setText(hint);
        }
    }

    public void setSearchIcon(int resId) {

        if (null != mSearchIcon)
        {
            mSearchIcon.setImageResource(resId);
        }
    }

    public void setClearIcon(int resId){

        if (null != mSearchText
                && mSearchText instanceof ClearableEditText)
        {
            ((ClearableEditText)mSearchText).setClearImageResource(resId);
        }
    }

    private void onSubmitQuery()
    {
        if (null != mOnQueryTextListener)
        {
            mOnQueryTextListener.onQueryTextSubmit(mSearchText.getText().toString());
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        onSubmitQuery();
        return true;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (null != mOnQueryTextListener) {
            mOnQueryTextListener.onQueryTextChange(mSearchText.getText().toString());
        }
    }

    @Override
    public void onClear(String content) {
        if (isExpand())
        {
            if (TextUtils.isEmpty(content))
            {
                onViewCollapsed();
            }
        }
    }
}
