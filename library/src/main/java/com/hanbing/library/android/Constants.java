/**
 * 
 */
package com.hanbing.library.android;

import android.text.TextUtils;

/**
 * @author hanbing
 * @date 2015-9-1
 */
public class Constants {

	
	public static final int TRUE = 1;
	
	public static final int FALSE = 0;
	
	/**
	 * 性别
	 * 
	 * @author hanbing
	 * @date 2015-8-4
	 */
	public static enum Gender {
		DEFAULT(-1, "未知", "Unknown"), FEMALE(0,"女", "Female"), MALE(1, "男", "Male"),;

		int value = 0;
		String cnName = "";
		String enName;

		private Gender(int value, String cnName, String enName) {
			this.value = value;
			this.cnName = cnName;
			this.enName = enName;
		}

		public int getValue() {
			return this.value;
		}

		public String getCnName() {
			return this.cnName;
		}

		public String getEnName()
		{
			return this.enName;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return cnName;
		}

		/**
		 * 获取值
		 * 
		 * @param name
		 * @return
		 */
		public static Gender toGender(String name) {

			if (TextUtils.isEmpty(name))
				return DEFAULT;

			for (Gender gender : Gender.values()) {
				if (gender.getCnName().equals(name)
						|| gender.getEnName().toUpperCase().equals(name.toUpperCase())) {
					return  gender;
				}
			}
			return DEFAULT;
		}

		/**
		 * 获取名称
		 * 
		 * @param value
		 * @return
		 */
		public static Gender toGender(int value) {

			for (Gender gender : Gender.values()) {

				if (value == gender.getValue()) {
					return gender;
				}
			}
			return DEFAULT;
		}
	}

}
