/**
 * 
 */
package com.common.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * @author hanbing
 * @date 2015-7-6
 */
public class NetworkUtils {

	/**
	 * 判断网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkOk(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = cm.getActiveNetworkInfo();

		if (null != info && info.isConnected()) {
			return true;
		}

		return false;
	}

	/**
	 * 判断wifi当前网络是否是wifi
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiOn(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = cm.getActiveNetworkInfo();

		if (null != info && info.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}

		return false;
	}

	/**
	 * 判断wifi是否打开
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiEnabled(Context context) {
		WifiManager wm = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);

		return wm.isWifiEnabled();
	}
}
