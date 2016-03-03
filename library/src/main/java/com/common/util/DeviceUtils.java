/**
 * 
 */
package com.common.util;

import java.util.UUID;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * @author hanbing
 * @date 2015-8-12
 */
public class DeviceUtils {

	/**
	 * 获取一个唯一的设备id
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context) {

		String deviceId = null;

		if (TextUtils.isEmpty(deviceId)) {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);

			String imei = tm.getDeviceId();

			String simSerialNumber = tm.getSimSerialNumber();

			String androidId = android.provider.Settings.Secure.getString(

			context.getContentResolver(),
					android.provider.Settings.Secure.ANDROID_ID);

			UUID deviceUuid = new UUID(androidId.hashCode(), imei.hashCode());

			deviceId = deviceUuid.toString();
		}

		return deviceId;

	}

}
