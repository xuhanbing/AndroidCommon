/**
 * 
 */
package com.hanbing.library.android.image;

import android.graphics.Bitmap;
import android.view.View;

/**
 * 图片加载回调
 * 
 * @author hanbing
 * @date 2015-7-10
 */
public interface ImageLoaderListener {

	/**
	 * 开始加载
	 * 
	 * @param view
	 * @param uri
	 */
	public void onLoadStarted(View view, String uri);

	/**
	 * 加载进度
	 * 
	 * @param view
	 * @param uri
	 * @param total
	 * @param current
	 */
	public void onLoading(View view, String uri, long total, long current);

	/**
	 * 加载完成
	 * 
	 * @param view
	 * @param uri
	 * @param bm
	 */
	public void onLoadCompleted(View view, String uri, Bitmap bm);

	/**
	 * 加载失败
	 * 
	 * @param view
	 * @param uri
	 */
	public void onLoadFailed(View view, String uri);

	/**
	 * 加载取消
	 * 
	 * @param view
	 * @param uri
	 */
	public void onLoadCancelled(View view, String uri);
}
