/**
 * 
 */
package com.common.util;

import java.lang.reflect.Method;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * @author hanbing
 * @date 2015-7-21
 */
public class SystemUtils {

	/**
	 * 获取显示信息
	 * 
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getDisplayMetrics(Context context) {
		return context.getResources().getDisplayMetrics();
	}

	/**
	 * 获取显示信息
	 * 
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getDisplayMetrics2(Activity context) {
		Display display = context.getWindowManager().getDefaultDisplay();
		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		return dm;
	}

	/**
	 * 全屏显示 隐藏虚拟按键
	 * 
	 * @param activity
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static void hideSystemUi(Activity activity) {
		int versionCode = Build.VERSION.SDK_INT;
		if (versionCode >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			int flag = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

			if (versionCode >= Build.VERSION_CODES.KITKAT) {
				flag |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			}

			activity.getWindow().getDecorView().setSystemUiVisibility(flag);
		}
	}

	/**
	 * 是否禁用系统锁屏
	 */
	static boolean isDisableSystemLock = false;
	static final String TAG = "Lockscreen";

	/**
	 * 是否锁定
	 * 
	 * @param context
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static boolean isKeyguardLocked(Context context) {
		KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN)
			return km.isKeyguardLocked();
		return false;
	}

	/**
	 * 启用系统锁屏
	 * 
	 * @param context
	 */
	public static void enableSystemLock(Context context) {
		Log.e("", "enableSystemLock");
		enableOrDisableSystemLock(context, true);
	}

	/**
	 * 禁用系统锁屏
	 * 
	 * @param context
	 */
	public static void disableSystemLock(Context context) {
		Log.e("", "disableSystemLock");
		enableOrDisableSystemLock(context, false);
	}

	/**
	 * 启用或禁用系统锁屏
	 * 
	 * @param context
	 * @param enable
	 */
	private static void enableOrDisableSystemLock(Context context, boolean enable) {
		KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);

		KeyguardLock lock = km.newKeyguardLock(TAG);
		if (!enable) {
			if (!isDisableSystemLock) {
				LogUtils.e("disable system lock");
				lock.disableKeyguard();
				isDisableSystemLock = true;
			}
		} else {
			if (isDisableSystemLock) {
				LogUtils.e("enable system lock");
				lock.reenableKeyguard();
				isDisableSystemLock = false;
			}
		}
	}

	/**
	 * 判断Miui悬浮窗权限
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkMiuiOverlayPermission(Context context) {
		int OP_SYSTEM_ALERT_WINDOW = 24;
		if (Build.VERSION.SDK_INT >= 19) {

			AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
			try {

				Method method = manager.getClass().getMethod("checkOp",
						new Class[] { int.class, int.class, String.class });

				int value = (Integer) method.invoke(manager, OP_SYSTEM_ALERT_WINDOW, android.os.Binder.getCallingUid(),
						context.getPackageName());
				if (AppOpsManager.MODE_ALLOWED == value) { // 这儿反射就自己写吧
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				LogUtils.e(e.getMessage());
			}
			return false;
		} else {
			if ((context.getApplicationInfo().flags & 1 << 27) == 1) {
				return true;
			} else {
				return false;
			}
		}

	}

	/**
	 * 显示输入法键盘
	 * 
	 * @param context
	 * @param view
	 */
	public static void showKeyboard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 隐藏输入法键盘
	 * 
	 * @param context
	 * @param view
	 */
	public static void hideKeyboard(Context context, View view) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}
