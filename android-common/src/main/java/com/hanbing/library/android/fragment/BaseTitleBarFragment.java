/**
 * 
 */
package com.hanbing.library.android.fragment;

import android.view.View;
import android.widget.TextView;

/**
 * @author hanbing
 */
public abstract class BaseTitleBarFragment extends BaseFragment implements View.OnClickListener{

	protected TextView mCenterTitle;

	protected TextView mLeftTitle;

	protected TextView mRightTitle;

	protected View mBackView;

	@Override
	protected void initViews(View view) {
		// TODO Auto-generated method stub
		super.initViews(view);
		initTitleBar(view);
	}

	private void initTitleBar(View view) {

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

		if (null != mBackView)
			mBackView.setVisibility(isShowBack() ? View.VISIBLE : View.GONE);

		if (null != mLeftTitle)
			mLeftTitle.setVisibility(isShowLeft() ? View.VISIBLE : View.GONE);

		if (null != mRightTitle)
			mRightTitle.setVisibility(isShowRight() ? View.VISIBLE : View.GONE);
	}

	protected abstract void findTitleBarViews();

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
		return false;
	}

	protected boolean isShowLeft() {
		return false;
	}

	protected boolean isShowRight() {
		return false;
	}

	protected void goBack() {
		getActivity().finish();
	}

	protected void onLeftClick() {

	}

	protected void onRightClick() {

	}

	public void onClick(View view) {

	}
}
