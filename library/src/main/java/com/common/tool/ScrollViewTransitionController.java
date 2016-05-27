/**
 *
 */
package com.common.tool;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.ScrollView;

import com.common.util.LogUtils;
import com.common.util.ViewUtils;
import com.common.view.StrengthScrollView;


/**
 * @author hanbing
 * @date 2015-12-23
 */
public class ScrollViewTransitionController {

	/**
	 * @author hanbing
	 * @date 2015-12-23
	 */
	public interface OnTransitionListener {
		/**
		 * scrollView滚动的距离缩放 位置针对于屏幕坐标
		 *
		 * @param moveY
		 *            距离最小位置（y坐标）的距离
		 * @param moveScale
		 *            当前距离（最大位置-当前位置）/最大距离（最大位置-最小位置）
		 *            当为0时表示超过了最大的位置，当为1时表示小于最小位置
		 *
		 */
		public void onScaleMove(float moveY, float moveScale);

		/**
		 * 如果是可以回弹的scrollView，且只有下拉时才会调用该方法，
		 *
		 * @param pullY
		 *            下拉的距离
		 * @param pullScale
		 *            与最大距离的比值
		 */
		public void onScalePull(float pullY, float pullScale);
	}

	/**
	 *
	 */
	public ScrollViewTransitionController() {
		// TODO Auto-generated constructor stub
	}



	/**
	 *
	 * @param scrollView
	 *            scrollView
	 * @param moveBaseView
	 *            基准的view
	 * @param moveView
	 *            scrollView中移动的view，当moveView的顶部到达moveBaseView的底部(当moveBaseView=null时为0)时，此时的移动比例为1
	 * @param lsner
	 *            回调
	 */

	public static void setControlViews(final ScrollView scrollView,
									   final View moveBaseView,
									   final View moveView,
									   final OnTransitionListener lsner) {



		scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {

				int[] loc1 = new int[2];
				int[] loc2 = new int[2];

				if (null == moveBaseView) {
					loc1[1] = 0;
				} else {
					moveBaseView.getLocationOnScreen(loc1);
				}

				moveView.getLocationOnScreen(loc2);


				final int minY = loc1[1] + ( null == moveBaseView ? 0 : moveBaseView.getMeasuredHeight());
				final int maxY = loc2[1];


//				final boolean baseOnSelf = (null == moveUpView || moveUpView == moveView);
//				final int minY = baseOnSelf ? originMoveY : originBaseY
//						+ moveBaseView.getHeight();
//				final int maxY = baseOnSelf ? originMoveY
//						+ moveView.getMeasuredHeight() : originBaseY
//						+ moveUpView.getMeasuredHeight();


				if (null != lsner)
					lsner.onScaleMove(0, 0);

				scrollView.getViewTreeObserver().addOnScrollChangedListener(
						new OnScrollChangedListener() {

							@Override
							public void onScrollChanged() {
								// TODO Auto-generated method stub


								int[] location1 = new int[2];
								int[] location2 = new int[2];


								moveView.getLocationOnScreen(location2);

								int moveY = location2[1];

								float scale ;
								if (moveY >= maxY) {
									/**
									 * 向下拉
									 */
									scale = 0;
								} else if (moveY <= minY) {
									/**
									 * 向上缩,已经被moveBaseView覆盖
									 */
									scale = 1;
								} else {
									/**
									 * 在最大和最小之间
									 */
									scale = 1.0f * (maxY - moveY) / (maxY - minY);
								}

								if (null != lsner)
									lsner.onScaleMove(maxY - moveY, scale);
							}
						});

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
					scrollView.getViewTreeObserver()
							.removeOnGlobalLayoutListener(this);
			}
		});



		/**
		 * 如果是下拉回弹的scrollView
		 */
		if (scrollView instanceof StrengthScrollView)
			((StrengthScrollView) scrollView)
					.setOnPullListener(new StrengthScrollView.OnPullListener() {

						@Override
						public void onPull(float dy, float y, float max) {
							// TODO Auto-generated method stub
							animate(y, max);
						}

						@Override
						public void onMoveBack(float dy, float y, float max) {
							// TODO Auto-generated method stub
							animate(y, max);
						}

						private void animate(float y, float max) {
							float scale = y / max;
							/**
							 * 只有下拉才变化
							 */
							if (y > 0) {

								if (null != lsner)
									lsner.onScalePull(y, scale);
							}
						}
					});
	}

	/**
	 * 缩放透明度
	 *
	 * @param color
	 * @param scale
	 * @return
	 */
	public static int scaleColorAlpha(int color, float scale) {

		return ViewUtils.scaleColorAlpha(color, scale);

	}

}
