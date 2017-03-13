/**
 *
 */
package com.hanbing.library.android.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * Created by hanbing
 */
public class ToastUtils {
    static Toast mToast;
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
    public static void showToast(final Context context, final String text) {
        if(null != context) {
            boolean isMainThread = isMainThread(context);
            if(isMainThread) {
                show(context, text);
            } else {

                Handler handler = new Handler(context.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        show(context, text);
                    }
                });

            }

        }
    }
    private static void show(Context context, String text) {
        cancel();

        Toast toast = createToast(context, text);
        toast.show();
        mToast = toast;
    }

    private static Toast createToast(Context context, String text) {
        return Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
    }

    private static void cancel() {
        if (null != mToast) mToast.cancel();
    }

    public static boolean isMainThread(Context context) {
        if (null == context)
            return false;

        return Thread.currentThread() == context.getMainLooper().getThread();

    }

}
