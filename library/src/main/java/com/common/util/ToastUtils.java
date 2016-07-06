/**
 *
 */
package com.common.util;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/**
 * @author hanbing
 * @date 2015-6-17
 */
public class ToastUtils {

    /**
     * 显示toast
     *
     * @param context
     * @param resId
     */
    public static void showToast(Context context, int resId) {
        showToast(context, context.getResources().getString(resId));
    }

    /**
     * 显示toast
     *
     * @param context
     * @param text
     */
    public static void showToast(Context context, String text) {
        if (null != context) {

            boolean isMainThread = isMainThread(context);

            if (!isMainThread)
                Looper.prepare();

            Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT).show();

            if (!isMainThread) {
                Looper.loop();
            }
            ;
        }
    }

    public static boolean isMainThread(Context context) {
        if (null == context)
            return false;

        return Thread.currentThread() == context.getMainLooper().getThread();

    }

}
