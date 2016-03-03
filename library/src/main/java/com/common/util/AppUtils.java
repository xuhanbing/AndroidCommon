/**
 * 
 */
package com.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * @author hanbing
 * @date 2016年1月13日
 */
public class AppUtils {

	/**
	 * 获取应用版本号
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		int versionCode = 0;

		if (null != context) {
			PackageInfo pkgInfo = null;
			try {
				pkgInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
				versionCode = pkgInfo.versionCode;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return versionCode;
	}
	
	/**
	 * 获取应用版本名称
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context)
	{
		String name = "";
		
		if (null != context) {
			PackageInfo pkgInfo = null;
			try {
				pkgInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
				name = pkgInfo.versionName;
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		return name;
	}
	
	/**
	 * 获取应用名称
	 * @param context
	 * @return
	 */
	public static String getAppName(Context context)
	{
		if (null == context)
			return "";
		
		return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
	}
}
