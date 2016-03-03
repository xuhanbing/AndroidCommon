/**
 * 
 */
package com.common.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author hanbing
 * @date 2015-12-23
 */
public class ViewUtils {

	/**
	 * 
	 */
	public ViewUtils() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * inflate view
	 * 
	 * @param context
	 * @param resource
	 * @param root
	 * @param attachToRoot
	 * @return
	 */
	public static View inflate(Context context, int resource, ViewGroup root, boolean attachToRoot) {
		if (null == context)
			return null;

		return inflate(LayoutInflater.from(context), resource, root, attachToRoot);
	}

	/**
	 * 
	 * @param inflater
	 * @param resource
	 * @param root
	 * @param attachToRoot
	 * @return
	 */
	public static View inflate(LayoutInflater inflater, int resource, ViewGroup root, boolean attachToRoot) {
		return inflater.inflate(resource, root, attachToRoot);
	}

	/**
	 * find view by id
	 * 
	 * @param activity
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T findViewById(Activity activity, int id) {
		if (null == activity)
			return null;

		return (T) activity.findViewById(id);
	}

	/**
	 * find view by id
	 * 
	 * @param parent
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T findViewById(View parent, int id) {
		if (null == parent)
			return null;

		return (T) parent.findViewById(id);
	}

	/**
	 * find view with tag
	 * 
	 * @param parent
	 * @param tag
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T findViewWidthTag(View parent, Object tag) {
		if (null == parent)
			return null;

		return (T) parent.findViewWithTag(tag);
	}

	/**
	 * bind view with onCickListener
	 * 
	 * @param activity
	 * @param id
	 * @param listener
	 * @return view
	 */
	public static <T extends View> T findViewAndBindOnClick(Activity activity, int id, View.OnClickListener listener) {
		T view = findViewById(activity, id);

		if (null != view)
			view.setOnClickListener(listener);

		return view;
	}

	/**
	 * bind view with onCickListener
	 * 
	 * @param parent
	 * @param id
	 * @param listener
	 * @return view
	 */
	public static <T extends View> T findViewAndBindOnClick(View parent, int id, View.OnClickListener listener) {
		T view = findViewById(parent, id);

		if (null != view)
			view.setOnClickListener(listener);

		return view;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void setScale(View view, float scaleX, float scaleY) {
		if (null == view)
			return;

		if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
			view.setScaleX(scaleX);
			view.setScaleY(scaleY);
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static void setAlpha(View view, float alpha) {
		if (null == view)
			return;

		if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
			view.setAlpha(alpha);
		}
	}

}
