/**
 * 
 */
package com.hanbing.library.android.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

/**
 * @author hanbing
 * @date 2015-6-18
 */
public class JSONUtils {

	
	/**
	 * 自动解析json格式数据到指定的类
	 * 类的属性支持普通类型和list，类的属性名称需要与json数据的key对应
	 * @param json
	 * @param clz
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> clz)
	{
		try {
			return fromJson(new JSONObject(json), clz);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	/**
	 * 自动解析json格式数据到指定的类
	 * 类的属性支持普通类型和list，类的属性名称需要与json数据的key对应
	 * @param json
	 * @param clz
	 * @return
	 */
	public static <T> T fromJson(JSONObject json, Class<T> clz) {

		if (null != json && null != clz) {

			T object = null;
			try {
				object = clz.newInstance();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (null == object)
				return null;

			Field[] fields = clz.getDeclaredFields();

			for (Field field : fields) {

				String name = field.getName();
				Object value = null;
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				try {
					{

						if (json.has(name)) {
							value = json.get(name);

							Class<?> type = field.getType();
							if (type == byte.class) {
								field.setByte(object, Byte.valueOf(value + ""));
							} else if (type == short.class) {
								field.setShort(object, Short.valueOf(value + ""));
							} else if (type == int.class) {
								field.setInt(object, Integer.valueOf(value + ""));
							} else if (type == float.class) {
								field.setFloat(object, Float.valueOf(value + ""));
							} else if (type == double.class) {
								field.setDouble(object, Double.valueOf(value + ""));
							} else if (type == long.class) {
								field.setLong(object, Long.valueOf(value + ""));
							} else if (type == String.class) {
								field.set(object, value);
							} else if (type == List.class) {
								ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();

								Class<?> clazz = (Class<?>) parameterizedType.getActualTypeArguments()[0];

								List<Object> list = new ArrayList<Object>();
								JSONArray jsonArray = json.getJSONArray(name);

								if (null != jsonArray && jsonArray.length() > 0) {
									for (int i = 0; i < jsonArray.length(); i++) {

										Object object2 = jsonArray.get(i);

										if (object2 instanceof JSONObject) {
											list.add(fromJson(jsonArray.getJSONObject(i), clazz));
										} else {
											list.add(object2);
										}

									}
								}

								field.set(object, list);
							} else {
								value = fromJson(json.getJSONObject(name), type);
								field.set(object, value);
							}
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					field.setAccessible(accessible);
				}

			}

			return object;
		}
		return null;
	}

	/**
	 * 获取json值
	 * 
	 * @param json
	 *            json数据
	 * @param path
	 *            需要的值在json中的路径，如data/user/name, name
	 * @param defaultValue
	 *            默认值
	 * @return
	 * @throws JSONException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getValue(JSONObject json, String path, T defaultValue)
			throws JSONException {
		T value = defaultValue;

		if (null != json || !TextUtils.isEmpty(path)) {
			String[] keys = path.split("/");

			String key = keys[0];

			/** 如果没有该key的值，返回 */
			if (!json.isNull(key)) {
				if (1 == keys.length) {
					try {
						value = (T) json.get(key);
					} catch (ClassCastException e) {
						// TODO: handle exception
					}
				} else {
					/** 递归找到最后一层的 */
					path = path.replaceFirst(keys[0] + "/", "");
					return getValue(json.getJSONObject(keys[0]), path,
							defaultValue);
				}
			}

		}

		return value;
	}

	/**
	 * 获取boolean数据
	 * 
	 * @param json
	 *            json数据
	 * @param path
	 *            需要的值在json中的路径，如data/user/name, name
	 * @return
	 * @throws JSONException
	 */
	public static boolean getBoolean(JSONObject json, String path)
			throws JSONException {
		return getBoolean(json, path, false);
	}

	/**
	 * 获取boolean数据
	 * 
	 * @param json
	 *            json数据
	 * @param path
	 *            需要的值在json中的路径，如data/user/name, name
	 * @param defaultValue
	 *            默认值
	 * @return
	 * @throws JSONException
	 */
	public static boolean getBoolean(JSONObject json, String path,
			boolean defaultValue) throws JSONException {
		return getValue(json, path, defaultValue);
	}

	/**
	 * 获取int型数据
	 * 
	 * @param json
	 *            json数据
	 * @param path
	 *            需要的值在json中的路径，如data/user/name, name
	 * @return
	 * @throws JSONException
	 */
	public static int getInteger(JSONObject json, String path)
			throws JSONException {
		return getInteger(json, path, 0);
	}

	/**
	 * 获取int型数据
	 * 
	 * @param json
	 *            json数据
	 * @param path
	 *            需要的值在json中的路径，如data/user/name, name
	 * @param defaultValue
	 *            默认值
	 * @return
	 * @throws JSONException
	 */
	public static int getInteger(JSONObject json, String path, int defaultValue)
			throws JSONException {
		return getValue(json, path, defaultValue);
	}

	/**
	 * 获取long型数据
	 * 
	 * @param json
	 *            json数据
	 * @param path
	 *            需要的值在json中的路径，如data/user/name, name
	 * @return
	 * @throws JSONException
	 */
	public static long getLong(JSONObject json, String path)
			throws JSONException {
		return getLong(json, path, 0);
	}

	/**
	 * 获取long型数据
	 * 
	 * @param json
	 *            json数据
	 * @param path
	 *            需要的值在json中的路径，如data/user/name, name
	 * @param defaultValue
	 *            默认值
	 * @return
	 * @throws JSONException
	 */
	public static long getLong(JSONObject json, String path, long defaultValue)
			throws JSONException {
		return getValue(json, path, defaultValue);
	}

	/**
	 * 获取double型数据
	 * 
	 * @param json
	 *            json数据
	 * @param path
	 *            需要的值在json中的路径，如data/user/name, name
	 * @return
	 * @throws JSONException
	 */
	public static double getDouble(JSONObject json, String path)
			throws JSONException {
		return getDouble(json, path, 0);
	}

	/**
	 * 获取double型数据
	 * 
	 * @param json
	 *            json数据
	 * @param path
	 *            需要的值在json中的路径，如data/user/name, name
	 * @param defaultValue
	 *            默认值
	 * @return
	 * @throws JSONException
	 */
	public static double getDouble(JSONObject json, String path,
			double defaultValue) throws JSONException {
		return getValue(json, path, defaultValue);
	}

	/**
	 * 获取string型数据
	 * 
	 * @param json
	 *            json数据
	 * @param path
	 *            需要的值在json中的路径，如data/user/name, name
	 * @return
	 * @throws JSONException
	 */
	public static String getString(JSONObject json, String path)
			throws JSONException {
		return getString(json, path, null);
	}

	/**
	 * 获取string数据
	 * 
	 * @param json
	 *            json数据
	 * @param path
	 *            需要的值在json中的路径，如data/user/name, name
	 * @param defaultValue
	 *            默认值
	 * @return
	 * @throws JSONException
	 */
	public static String getString(JSONObject json, String path,
			String defaultValue) throws JSONException {
		return getValue(json, path, defaultValue);
	}

	/**
	 * 获取jsonobject
	 * 
	 * @param json
	 *            json数据
	 * @param path
	 *            需要的值在json中的路径，如data/user/name, name
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject getJsonObject(JSONObject json, String path)
			throws JSONException {
		return getJsonObject(json, path, new JSONObject());
	}

	/**
	 * 获取jsonobject
	 * 
	 * @param json
	 *            json数据
	 * @param path
	 *            需要的值在json中的路径，如data/user/name, name
	 * @param defaultValue
	 *            默认值
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject getJsonObject(JSONObject json, String path,
			JSONObject defalutValue) throws JSONException {
		return getValue(json, path, defalutValue);
	}

	/**
	 * 获取jsonarray
	 * 
	 * @param json
	 *            json数据
	 * @param path
	 *            需要的值在json中的路径，如data/user/name, name
	 * @return
	 * @throws JSONException
	 */
	public static JSONArray getJsonArray(JSONObject json, String path)
			throws JSONException {
		return getJsonArray(json, path, new JSONArray());
	}

	/**
	 * 获取jsonarray
	 * 
	 * @param json
	 *            json数据
	 * @param path
	 *            需要的值在json中的路径，如data/user/name, name
	 * @param defaultValue
	 *            默认值
	 * @return
	 * @throws JSONException
	 */
	public static JSONArray getJsonArray(JSONObject json, String path,
			JSONArray defaultValue) throws JSONException {
		return getValue(json, path, defaultValue);
	}
}
