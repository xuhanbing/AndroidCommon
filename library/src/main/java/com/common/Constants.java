/**
 * 
 */
package com.common;

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
		DEFAULT(-1, "未知"), FEMALE(0,"女"), MALE(1, "男"),;

		int value = 0;
		String name = "";

		private Gender(int value, String name) {
			this.value = value;
			this.name = name;
		}

		public int getValue() {
			return this.value;
		}

		public String getName() {
			return this.name;
		}
		
		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return name;
		}

		/**
		 * 获取值
		 * 
		 * @param name
		 * @return
		 */
		public static Gender toGender(String name) {

			for (Gender gender : Gender.values()) {
				if (gender.getName().equals(name)) {
					return  gender;
				}
			}
			return DEFAULT;
		}

		/**
		 * 获取名称
		 * 
		 * @param context
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
