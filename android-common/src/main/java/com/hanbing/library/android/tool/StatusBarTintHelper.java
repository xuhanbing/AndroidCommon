package com.hanbing.library.android.tool;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hanbing on 2016/10/26
 */

public class StatusBarTintHelper {

    /**
     * 设置状态栏高度和颜色
     *
     * @param activity
     * @param statusBar
     * @param color
     * @return 返回实际高度
     */
    public static int config(Activity activity, View statusBar, int color) {

        if (null == activity || null == statusBar)
            return 0;
        //设置高度，刚好等于statusbar的高度
        SystemBarTintManager systemBarTintManager = new SystemBarTintManager(activity);

        ViewGroup.LayoutParams params = statusBar.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;

        //如果sdk < 19，设置高度为0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT )
            params.height = 0;
        else
            params.height = systemBarTintManager.getConfig().getStatusBarHeight();

        statusBar.setLayoutParams(params);
        statusBar.setBackgroundColor(color);

        return params.height;
    }
}
