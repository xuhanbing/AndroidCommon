/**
 * 
 */
package com.hanbing.library.android.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

/**
 * Created by hanbing
 */
public class FileUtils {

	/**
	 * 文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isExist(String path) {
		return !TextUtils.isEmpty(path) && new File(path).exists();
	}

	/**
	 * 文件是否可用
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isAvaliable(String path) {
		return isExist(path) && new File(path).length() > 0;
	}

	// /**
	// * 从uri获取文件的绝对路径
	// * @param context
	// * @param uri
	// * @return
	// */
	// public static String getAbsPath(Context context, Uri uri) {
	//
	// if ("content".equalsIgnoreCase(uri.getScheme())) {
	// String[] projection = { "_data" };
	// Cursor cursor = null;
	//
	// try {
	// cursor = context.getContentResolver().query(uri, projection,null, null,
	// null);
	// int column_index = cursor.getColumnIndexOrThrow("_data");
	// if (cursor.moveToFirst()) {
	// return cursor.getString(column_index);
	// }
	// } catch (Exception e) {
	// // Eat it
	// }
	// }
	//
	// else if ("file".equalsIgnoreCase(uri.getScheme())) {
	// return uri.getPath();
	// }
	//
	// return null;
	// }

	/**
	 * 获取绝对路径 4.4以上采用新的方式解析
	 * 
	 * @param context
	 * @param uri
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static String getAbsPath(final Context context, final Uri uri) {
		if (null == uri)
			return null;

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		// DocumentProvider
		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			// ExternalStorageProvider
			if (isExternalStorageDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/"
							+ split[1];
				}

				// TODO handle non-primary volumes
			}
			// DownloadsProvider
			else if (isDownloadsDocument(uri)) {

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"),
						Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			}
			// MediaProvider
			else if (isMediaDocument(uri)) {
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[] { split[1] };

				return getDataColumn(context, contentUri, selection,
						selectionArgs);
			}
		}
		// MediaStore (and general)
		else if ("content".equalsIgnoreCase(uri.getScheme())) {

			// Return the remote address
			if (isGooglePhotosUri(uri))
				return uri.getLastPathSegment();

			return getDataColumn(context, uri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 * 
	 * @param context
	 *            The context.
	 * @param uri
	 *            The Uri to query.
	 * @param selection
	 *            (Optional) Filter used in the query.
	 * @param selectionArgs
	 *            (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri,
			String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = { column };

		try {
			cursor = context.getContentResolver().query(uri, projection,
					selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri
				.getAuthority());
	}

	/**
	 * @param uri
	 *            The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri
				.getAuthority());
	}

	/**
	 * 如果sd卡可用，使用sd中的路径 否则使用app默认路径
	 * 
	 * @param context
	 * @return
	 */
	public static String getRootPath(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ File.separator;
		} else {
			return context.getFilesDir().getParent() + File.separator;
		}
	}


	/**
	 * app路径
	 * @param context
	 * @return
	 */
	public static String getRootPathIn(Context context) {
		return context.getFilesDir().getParent() + File.separator;
	}

	/**
	 * 扩展sd卡中app路径
	 * @param context
	 * @return
	 */
	public static String getRootPathExt(Context context) {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().getAbsolutePath()
					+ File.separator;
		}

		return "";
	}

	/**
	 * 自动添加根路径
	 *  如果存在扩展sd卡，有限使用扩展sd卡中app的路径
	 *  否则使用内置sd卡中app路径
	 * @param context
	 * @param subPath 子路径
	 */
	public static String addRootIfNeed(Context context, String subPath) {
		String rootIn = getRootPathIn(context);
		String rootExt = getRootPathExt(context);


		/**
		 * 如果已经是内容部存储路径或者外部存储路径
		 */
		if (subPath.startsWith(rootIn)
				|| (!TextUtils.isEmpty(rootExt) && subPath.startsWith(rootExt))) {
			return subPath;
		}

		String root = getRootPath(context);
		/**
		 * 添加默认的根路径
		 */
		if (!subPath.startsWith(root)) {
			if (!root.endsWith(File.separator)) {
				root += File.separator;
			}
			subPath = root + subPath;
		}

		return subPath;
	}

	/**
	 * 生成文件路径
	 * 
	 * @param context
	 * @param dir
	 *            目录
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public static String createPath(Context context, String dir, String fileName) {
		return addRootIfNeed(context, dir + File.separator + fileName);
	}

	/**
	 * 创建目录
	 * 
	 * @param dir
	 */
	public static String createDir(Context context, String dir) {
		dir = addRootIfNeed(context, dir);
		return createDir(dir);
	}


	/**
	 * 创建文件
	 *
	 * @param context
	 * @param path
	 * @return
	 */
	public static String createFile(Context context, String path)
	{
		path = addRootIfNeed(context, path);

		return createFile(path);
	}

	/**
	 * 创建文件，并返回路径
	 * 
	 * @param dir
	 *            目录
	 * @param fileName
	 *            文件名
	 */
	public static String createFile(Context context, String dir, String fileName) {
		dir = addRootIfNeed(context, dir);

		return createFile(dir, fileName);
	}

	/**
	 * 创建目录
	 * @param dir
	 * @return
	 */
	public static String createDir(String dir)
	{
		File file = new File(dir);

		if (!file.exists())
			file.mkdirs();
		return dir;
	}

	/**
	 * 创建文件
	 * @param path
	 * @return
	 */
	public static String createFile(String path)
	{

		File file = new File(path);

		createDir(file.getParent());

		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}

	public  static String createFile(String dir, String fileName)
	{
		String path = dir + "/" + fileName;

		return createFile(path);
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(String path) {
		if (null == path)
			return false;

		File file = new File(path);

		return file.exists() && file.delete();
	}

	/**
	 * 删除目录及目录下所有文件
	 * 
	 * @param dir
	 */
	public static void deleteDir(String dir) {
		File file = new File(dir);

		if (file.isDirectory()) {
			File[] files = file.listFiles();

			for (File f : files) {
				if (f.isDirectory()) {
					deleteDir(f.getAbsolutePath());
				} else {
					f.delete();
				}
			}
			file.delete();
		} else {
			file.delete();
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param srcPath
	 * @param dstPath
	 * @return
	 */
	public static boolean copyFile(String srcPath, String dstPath) {

		if (!isExist(srcPath))
			return false;

		InputStream in = null;
		FileOutputStream fs = null;
		try {
			int readLength = 0;
			File srcFile = new File(srcPath);

			if (srcFile.exists()) { // 文件存在时
				in = new FileInputStream(srcPath); // 读入原文件
				fs = new FileOutputStream(dstPath);
				byte[] buffer = new byte[4096];
				while ((readLength = in.read(buffer)) != -1) {
					fs.write(buffer, 0, readLength);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			IOUtils.close(in);
			IOUtils.close(fs);
		}

		return false;

	}

	/**
	 * 重命名，或移动
	 * 
	 * @param oldPath
	 * @param newPath
	 */
	public static boolean rename(String oldPath, String newPath) {
		if (TextUtils.isEmpty(oldPath) || TextUtils.isEmpty(newPath))
			return false;

		if (!isExist(oldPath))
			return false;

		File oldFile = new File(oldPath);
		File newFile = new File(newPath);

		return oldFile.renameTo(newFile);
	}

	/**
	 * 写入文件
	 * @param path
	 * @param data
	 * @return
	 */
	public static boolean writeToFile(String path, byte[] data)
	{

		boolean success = false;
		if (!TextUtils.isEmpty(path) && null != data)
		{
			File file = new File(path);

			file.getParentFile().mkdirs();

			try {
				FileOutputStream os = new FileOutputStream(file);

				os.write(data);

				os.flush();
				os.close();

				success = true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return success;
	}

	/**
	 *
	 * @param path
	 * @param inputStream
	 * @return
	 */
	public static boolean writeToFile(String path, InputStream inputStream) {
		boolean success = false;
		if (!TextUtils.isEmpty(path) && null != inputStream)
		{
			File file = new File(path);

			file.getParentFile().mkdirs();

			byte[] data = new byte[4096];
			try {
				FileOutputStream os = new FileOutputStream(file);

				int readLen;

				while ((readLen = inputStream.read(data)) != -1) {
					os.write(data, 0, readLen);
				}

				os.flush();
				os.close();

				success = true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return success;
	}

	/**
	 * 读取文件倒流
	 * @param path
	 * @return
	 */
	public static byte[] readFromFile(String path)
	{
		if (FileUtils.isExist(path))
		{
			try {
				FileInputStream is = new FileInputStream(path);

				int totalLen = is.available();


				byte[] buffer = new byte[totalLen];

				int curLength = 0;
				int readLength = 0;

				while ((readLength = is.read(buffer, curLength, totalLen - curLength)) != -1 && (curLength < totalLen))
				{
					curLength += readLength;

				}

				is.close();

				return buffer;

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * return external cache dir
	 * @param context
	 * @return
	 */
	public static String getCacheDirExt(Context context)
	{
		File file = context.getExternalCacheDir();
		return null == file ? null : file.toString();
	}

	/**
	 * return inner cache dir
	 * @param context
	 * @return
	 */
	public static String getCacheDir(Context context)
	{
		File file = context.getCacheDir();
		return null == file ? null : file.toString();
	}

	public static String getCacheDirAuto(Context context) {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? getCacheDirExt(context) : getCacheDir(context);
	}

	public static void clearCache(Context context)
	{
		deleteDir(getCacheDir(context));
		deleteDir(getCacheDirExt(context));
	}

	public static long calculateCache(Context context) {
		return calculateCache(getCacheDir(context), getCacheDirExt(context));
	}

	public static long calculateCache(String ... dirs) {

		if (null == dirs || 0 == dirs.length)
			return 0;

		long length = 0;

		for (String dir : dirs) {
			length += calculateFileLength(dir);
		}

		return length;
	}

	public static long calculateFileLength(String fileName) {
		File file = new File(fileName);

		long length = 0;
		if (file.exists() && file.isDirectory()) {

			File[] files = file.listFiles();

			if (null != files && files.length > 0) {
				for (File f : files) {
					if (f.isDirectory())
						length += calculateFileLength(f.getAbsolutePath());
					else {
						length += f.length();
					}
				}

				return length;
			}
		}

		return 0;
	}

}
