/**
 *
 */
package com.hanbing.library.android.util;

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
 *
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

    public static void share(Context context, String title, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, title));

    }

    /**
     * 分享图片
     *
     * @param context
     * @param title
     * @param subject
     * @param imagePath
     */
    public static void shareImage(Context context, String title, String subject, String imagePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(imagePath)));
        context.startActivity(Intent.createChooser(intent, title));

    }

    /**
     * 分享文件
     *
     * @param context
     * @param title
     * @param subject
     * @param filePath
     */
    public static void shareFile(Context context, String title, String subject, String filePath) {
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
        context.startActivity(Intent.createChooser(intent, title));

    }

    public static void mail(Context context, String to, String title, String subject, String content) {
//		Intent intent = new Intent(Intent.ACTION_SENDTO);
//
//		intent.setData(Uri.parse("mailto:" + to));
//		intent.putExtra(Intent.EXTRA_TEXT, content);
//		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
//
//		context.startActivity(intent);

        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:qq10000@qq.com"));
        data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
        data.putExtra(Intent.EXTRA_TEXT, "这是内容");
        context.startActivity(data);
    }

    /**
     * 拍照
     *
     * @param context
     * @param localPath   图片保存路径
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

        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");

        context.startActivityForResult(Intent.createChooser(intent, null),
                requestCode);
    }

    /**
     *
     * @param context
     * @param requestCode
     */
    public static void choosePictureNew(Activity context, int requestCode) {
        Intent intent = new Intent();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");

            context.startActivityForResult(Intent.createChooser(intent, null),
                    requestCode);
        } else {
            choosePicture(context, requestCode);
        }


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
     * 卸载app
     *
     * @param context
     * @param packageName app的包名
     */
    public static void uninstallApp(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
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
            phoneNumber = phoneNumber.replace("+86", "");
        } else if (phoneNumber.startsWith("86")) {
            phoneNumber = phoneNumber.replaceFirst("86", "");
        }
        String telRegex = "[1][3578]\\d{9}";
        if (TextUtils.isEmpty(phoneNumber))
            return false;
        else
            return phoneNumber.matches(telRegex);
    }

}
