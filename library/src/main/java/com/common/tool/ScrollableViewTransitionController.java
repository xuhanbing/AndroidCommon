/**
 *
 */
package com.common.tool;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.AbsListView;

import com.common.util.LogUtils;
import com.common.util.ViewUtils;
import com.common.view.StrengthScrollView;

import java.lang.reflect.Field;


/**
 * @author hanbing
 * @date 2015-12-23
 */
public class ScrollableViewTransitionController {

    /**
     * @author hanbing
     * @date 2015-12-23
     */
    public interface OnScrollListener {
        /**
         * scrollView滚动的距离缩放 位置针对于屏幕坐标
         *
         * @param scrollY     距离最小位置（y坐标）的距离
         * @param scrollScale 0 ~1
         *                    当前距离（最大位置-当前位置）/最大距离（最大位置-最小位置）
         *                    当为0时表示超过了最大的位置，当为1时表示小于最小位置
         */
        void onScroll(float scrollY, float scrollScale);
    }

    public interface OnPullListener {
        /**
         * 如果是可以回弹的scrollView，且只有下拉时才会调用该方法，
         *
         * @param pullY     下拉的距离
         * @param pullScale 与最大距离的比值
         */
        void onPull(float pullY, float pullScale);
    }


    /**
     *
     */
    public ScrollableViewTransitionController() {
        // TODO Auto-generated constructor stub
    }

    int startY;
    int endY;

    /**
     * @param scrollableView scrollableView
     * @param moveBaseView   基准的view
     * @param moveView       scrollableView中移动的view，当moveView的顶部到达moveBaseView的底部(当moveBaseView=null时为0)时，此时的移动比例为1，此view的高度需要比较稳定
     * @param lsner          回调
     */

    public static void setControlViews(final View scrollableView,
                                       final View moveBaseView,
                                       final View moveView,
                                       final OnScrollListener lsner) {

        scrollableView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
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


                final int endY = loc1[1] + (null == moveBaseView ? 0 : moveBaseView.getMeasuredHeight());
                final int startY = loc2[1];

                if (null != lsner)
                    lsner.onScroll(0, 0);

                scrollableView.getViewTreeObserver().addOnScrollChangedListener(
                        new OnScrollChangedListener() {

                            @Override
                            public void onScrollChanged() {
                                // TODO Auto-generated method stub



                                int[] location1 = new int[2];
                                int[] location2 = new int[2];


                                moveView.getLocationOnScreen(location2);

                                int moveY = location2[1];

                                float scale;
                                if (moveY >= startY) {
                                    /**
                                     * 向下拉
                                     */
                                    scale = 0;
                                } else if (moveY <= endY) {
                                    /**
                                     * 向上缩,已经被moveBaseView覆盖
                                     */
                                    scale = 1;
                                } else {
                                    /**
                                     * 在最大和最小之间
                                     */
                                    scale = 1.0f * (startY - moveY) / (startY - endY);
                                }

                                if (null != lsner)
                                    lsner.onScroll(startY - moveY, scale);
                            }
                        });

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    scrollableView.getViewTreeObserver()
                            .removeOnGlobalLayoutListener(this);
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
