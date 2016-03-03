/**
 * 
 */
package com.common.util;

import java.io.File;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;

/**
 * 其他一下常用
 * @author hanbing
 * @date 2015-7-7
 */
public class OtherUtils {

	/**
	 * 拨打电话
	 * 
	 * @param context
	 * @param phoneNumber
	 */
	public static void dial(Context context, String phoneNumber) {
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:" + phoneNumber));
		context.startActivity(intent);
	}

	/**
	 * 打开地图
	 * 
	 * @param context
	 * @param lat
	 * @param lng
	 */
	public static void openMap(Context context, double lat, double lng) {
		// Uri uri = Uri.parse("geo:" + lat + ", " + lng);
		// Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// context.startActivity(intent);

		openMap(context, lat, lng, null);
	}

	/**
	 * 打开地图
	 * 
	 * @param context
	 * @param lat
	 * @param lng
	 * @param address
	 */
	public static void openMap(Context context, double lat, double lng,
			String address) {
		Uri uri = Uri.parse("geo:" + lat + ", " + lng + "?q=" + address);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 浏览器中打开url
	 * 
	 * @param context
	 * @param url
	 */
	public static void openUrl(Context context, String url) {

		if (TextUtils.isEmpty(url))
			return;

		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			url = "http://" + url;
		}

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	/**
	 * 拍照
	 * 
	 * @param context
	 * @param localPath
	 *            图片保存路径
	 * @param requestCode
	 */
	public static void takePicture(Activity context, String localPath,
			int requestCode) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(localPath)));
		context.startActivityForResult(intent, requestCode);
	}

	/**
	 * 选取图片 4.4以上采用新的方式选取图片
	 * 
	 * @param context
	 * @param requestCode
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static void choosePicture(Activity context, int requestCode) {
		Intent intent = new Intent();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
		} else {
			intent.setAction(Intent.ACTION_PICK);
		}

		intent.setType("image/*");

		context.startActivityForResult(Intent.createChooser(intent, null),
				requestCode);
	}

	/**
	 * 安装app
	 * 
	 * @param context
	 * @param appLocalPath
	 */
	public static void installApp(Context context, String appLocalPath) {

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(new File(appLocalPath)),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}
	
	/**
	     * 退格键
	     */
	    public static void backspace() {
		// TODO Auto-generated method stub

		new Thread(new Runnable() {

		    @Override
		    public void run() {
			// TODO Auto-generated method stub
			Instrumentation ins = new Instrumentation();
			ins.sendKeyDownUpSync(KeyEvent.KEYCODE_DEL);
		    }

		}).start();

	    }

}
