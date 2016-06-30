/**
 * 
 */
package com.common.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author hanbing
 */
public class BaseTitleBarActivity extends BaseActivity implements View.OnClickListener{

	TextView mCenterTitle;

	TextView mLeftTitle;

	TextView mRightTitle;

	ImageView mBack;

	/*
	 * (non-Javadoc)
	 *
	 */
	@Override
	protected void initViews() {
		// TODO Auto-generated method stub
		super.initViews();
		
		initTitleBar();
	}

	private void initTitleBar() {
		
		findTitleBarViews();

		if (getCenterTitleResId() > 0)
			setCenterTitle(getCenterTitleResId());
		else
			setCenterTitle(getCenterTitle());

		if (getLeftTitleResId() > 0)
			setLeftTitle(getLeftTitleResId());
		else
			setLeftTitle(getLeftTitle());

		if (getRightTitleResId() > 0)
			setRightTitle(getRightTitleResId());
		else
			setRightTitle(getRightTitle());

		if (null != mBack)
			mBack.setVisibility(isShowBack() ? View.VISIBLE : View.GONE);

		if (null != mLeftTitle)
			mLeftTitle.setVisibility(isShowLeft() ? View.VISIBLE : View.GONE);

		if (null != mRightTitle)
			mRightTitle.setVisibility(isShowRight() ? View.VISIBLE : View.GONE);
	}

	private void findTitleBarViews() {

	}


	protected void setCenterTitle(CharSequence text) {
		if (null != mCenterTitle)
			mCenterTitle.setText(text);
	}

	protected void setCenterTitle(int resid) {
		if (null != mCenterTitle)
			mCenterTitle.setText(resid);
	}

	protected void setLeftTitle(CharSequence text) {
		if (null != mLeftTitle)
			mLeftTitle.setText(text);
	}

	protected void setLeftTitle(int resid) {
		if (null != mLeftTitle)
			mLeftTitle.setText(resid);
	}

	protected void setRightTitle(CharSequence text) {
		if (null != mRightTitle)
			mRightTitle.setText(text);
	}

	protected void setRightTitle(int resid) {
		if (null != mRightTitle)
			mRightTitle.setText(resid);
	}

	protected CharSequence getCenterTitle() {
		return null;
	}

	protected int getCenterTitleResId() {
		return 0;
	}

	protected CharSequence getLeftTitle() {
		return null;
	}

	protected int getLeftTitleResId() {
		return 0;
	}

	protected CharSequence getRightTitle() {
		return null;
	}

	protected int getRightTitleResId() {
		return 0;
	}

	protected boolean isShowBack() {
		return true;
	}

	protected boolean isShowLeft() {
		return false;
	}

	protected boolean isShowRight() {
		return false;
	}

	protected void goBack() {
		finish();
	}

	protected void onLeftClick() {

	}

	protected void onRightClick() {

	}

	public void onClick(View view) {

	}
}
