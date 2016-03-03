/**
 * 
 */
package com.common.util;

import android.text.TextUtils;

/**
 * 电话号码
 * 
 * @author hanbing
 * @date 2015-6-18
 */
public class PhoneNumberUtils {

	/**
	 * 判断电话号码是否合法
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public static boolean isPhoneNumber(String phoneNumber) {
		/**
		 * 去除前后空格
		 */
		phoneNumber = phoneNumber.trim();

		/**
		 * 去掉+86或86
		 */
		if (phoneNumber.startsWith("+86")) {
			phoneNumber.replaceFirst("+86", "");
		} else if (phoneNumber.startsWith("86")) {
			phoneNumber.replaceFirst("86", "");
		}

		String telRegex = "[1][3578]\\d{9}";
		if (TextUtils.isEmpty(phoneNumber))
			return false;
		else
			return phoneNumber.matches(telRegex);
	}
}
