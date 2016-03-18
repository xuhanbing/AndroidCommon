/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.pull2refresh;

import com.pull2refresh.PullToRefreshBase.Mode;
import com.pull2refresh.PullToRefreshBase.Orientation;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;


@SuppressLint("ViewConstructor")
public abstract class LoadingLayout extends FrameLayout implements ILoadingLayout {

    
	static final String LOG_TAG = "PullToRefresh-LoadingLayout";
	
	public LoadingLayout(Context context, final Mode mode, final Orientation scrollDirection, TypedArray attrs) {
		super(context);
		
	}
//
	public final void setHeight(int height) {
		ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) getLayoutParams();
		lp.height = height;
		requestLayout();
	}
//
	public final void setWidth(int width) {
		ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) getLayoutParams();
		lp.width = width;
		requestLayout();
	}
//
	
//
	public void hideAllViews() {
	    hideAllViewsImpl();
	}
	
	public void showInvisibleViews() {
	    showAllViewsImpl();
	}

	public void onPull(float scaleOfLayout) {
	    onPullImpl(scaleOfLayout);
	}

	public void pullToRefresh() {
//
		// Now call the callback
		pullToRefreshImpl();
	}
//
	public void refreshing() {
	 // Now call the callback
	 			refreshingImpl();
	}

	public void releaseToRefresh() {
//
		// Now call the callback
		releaseToRefreshImpl();
	}

	public void reset() {
	    resetImpl();
	}
//
//	/**
//	 * Callbacks for derivative Layouts
//	 */
	
	public abstract int getContentSize();
	
	protected abstract int getDefaultDrawableResId();

	protected abstract void onLoadingDrawableSet(Drawable imageDrawable);

	protected abstract void onPullImpl(float scaleOfLayout);

	protected abstract void pullToRefreshImpl();

	protected abstract void refreshingImpl();

	protected abstract void releaseToRefreshImpl();

	protected abstract void resetImpl();
	
	protected abstract void showAllViewsImpl();
	
	protected abstract void hideAllViewsImpl();
	
}
