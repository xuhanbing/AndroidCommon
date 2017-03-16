/**
 * 
 */
package com.hanbing.library.android.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;

/**
 * @author hanbing
 * 
 */
public class ImageUtils {

	static final int RESOLUTION_WIDTH = 720;
	static final int RESOLUTION_HEIGHT = 1280;

	/**
	 * 默认模糊程度，值越高越模糊
	 */
	
	static final int DEFAULT_BLUR_LEVEL = 20;
	/**
	 * 裁剪图片
	 * 
	 * @param activity
	 * @param requestCode
	 *            请求的code，可以在onActivityResult中做相应的处理
	 * @param src
	 *            原图uri
	 * @param dst
	 *            目标uri，如果需要保存到sd卡指定路径时使用，如果不需要可传null
	 * @param outputX
	 *            输出图片宽
	 * @param outputY
	 *            输出图片高
	 * @param returnData
	 *            是否返回数据（适用裁剪后图片较小的情况，如果裁剪后图片较大使用uri）
	 */
	public static void cropImage(Activity activity, int requestCode, Uri src,
			Uri dst, int outputX, int outputY, boolean returnData) {
		cropImage(activity, requestCode, src, dst, CompressFormat.JPEG,
				outputX, outputY, outputX, outputY, true, returnData);
	}

	/**
	 * 裁剪图片
	 * 
	 * @param activity
	 * @param requestCode
	 *            请求的code，可以在onActivityResult中做相应的处理
	 * @param src
	 *            原图uri
	 * @param dst
	 *            目标uri，如果需要保存到sd卡指定路径时使用，如果不需要可传null
	 * @param format
	 *            图片格式
	 * @param aspectX
	 *            选取框宽比值（如果不需固定比例，可传0）
	 * @param aspectY
	 *            选取框高比值（如果不需固定比例，可传0）
	 * @param outputX
	 *            输出图片宽（不能太大，否则可能无法裁剪。输出图片宽高比最好与选取框比值一致）
	 * @param outputY
	 *            输出图片高（不能太大，否则可能无法裁剪。输出图片宽高比最好与选取框比值一致）
	 * @param scale
	 *            是否拉伸图片，当选取的图片与输出的图片比例不一致时，拉伸图片 （如果需要返回bitmap数据，该值必须为true）
	 * @param returnData
	 *            是否返回数据（适用裁剪后图片较小的情况，如果裁剪后图片较大使用uri）
	 */
	public static void cropImage(Activity activity, int requestCode, Uri src,
			Uri dst, CompressFormat format, int aspectX, int aspectY,
			int outputX, int outputY, boolean scale, boolean returnData) {

		Intent intent = new Intent();
		intent.setAction("com.android.camera.action.CROP");
		intent.setDataAndType(src, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", scale);
		intent.putExtra("scaleUpIfNeeded", scale);
		intent.putExtra("return-data", returnData);
		if (null != dst) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT, dst);
			intent.putExtra("outputFormat", format.toString());
		}

		activity.startActivityForResult(intent, requestCode);
	}

	/**
	 * 获取圆角位图的方法
	 * 
	 * @param bitmap
	 *            需要转化成圆角的位图
	 * @param pixels
	 *            圆角的度数，数值越大，圆角越大
	 * @return 处理后的圆角位图
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode((new PorterDuffXfermode(Mode.SRC_IN)));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 得到图片
	 * 
	 * @param file
	 *            图片文件
	 * @return
	 */
	public static Bitmap getBitmap(File file) {
		return getBitmap(file, RESOLUTION_WIDTH, RESOLUTION_HEIGHT);
	}

	/**
	 * 得到图片
	 * 
	 * @param file
	 *            图片文件
	 * @param width
	 *            计算缩放比例，不是正真的图片宽
	 * @param height
	 *            计算缩放比例，不是正真的图片高
	 * @return
	 */
	public static Bitmap getBitmap(File file, int width, int height) {

		if (null == file || !file.exists())
			return null;

		if (width <= 0)
			width = RESOLUTION_WIDTH;
		if (height <= 0)
			height = RESOLUTION_HEIGHT;

		FileInputStream fis = null;
		Bitmap bm = null;
		try {
			fis = new FileInputStream(file);

			BitmapFactory.Options options = new Options();
			options.inJustDecodeBounds = true;

			bm = BitmapFactory.decodeFileDescriptor(fis.getFD(), null, options);

			options.inSampleSize = Math.min(
					Math.min(options.outWidth, options.outHeight) / width,
					Math.max(options.outWidth, options.outHeight) / height);

			options.inJustDecodeBounds = false;

			bm = BitmapFactory.decodeFileDescriptor(fis.getFD(), null, options);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			IOUtils.close(fis);
		}

		return bm;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmap(String path) {
		return getBitmap(path, RESOLUTION_WIDTH, RESOLUTION_HEIGHT);
	}

	/**
	 * 得到图片
	 * 
	 * @param path
	 *            图片路径
	 * @param width
	 *            计算缩放比例，不是正真的图片宽
	 * @param height
	 *            计算缩放比例，不是正真的图片高
	 * @return
	 */
	public static Bitmap getBitmap(String path, int width, int height) {
		if (!FileUtils.isExist(path))
			return null;
		return getBitmap(new File(path), width, height);
	}

	/**
	 * 从asset中打开图片
	 * 
	 * @param context
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmapAssets(Context context, String path) {
		return getBitmapAssets(context, path, RESOLUTION_WIDTH,
				RESOLUTION_HEIGHT);
	}

	/**
	 * 从asset中打开图片
	 * 
	 * @param context
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getBitmapAssets(Context context, String path,
			int width, int height) {
		Bitmap bm = null;

		InputStream is = null;

		if (width <= 0)
			width = RESOLUTION_WIDTH;
		if (height <= 0)
			height = RESOLUTION_HEIGHT;
		try {
			is = context.getAssets().open(path);

			BitmapFactory.Options options = new Options();
			options.inJustDecodeBounds = true;

			bm = BitmapFactory.decodeStream(is, null, options);

			IOUtils.close(is);

			is = context.getAssets().open(path);

			options.inSampleSize = Math.min(
					Math.min(options.outWidth, options.outHeight) / width,
					Math.max(options.outWidth, options.outHeight) / height);

			options.inJustDecodeBounds = false;

			bm = BitmapFactory.decodeStream(is, null, options);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(is);
		}

		return bm;
	}
	
	/**
	 * 从资源文件打开图片
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap getBitmapResource(Context context, int resId)
	{
		return getBitmapResource(context, resId, RESOLUTION_WIDTH, RESOLUTION_HEIGHT);
	}
	
	/**
	 * 从资源文件打开图片
	 * @param context
	 * @param resId
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getBitmapResource(Context context, int resId, int width, int height)
	{
		Bitmap bm = null;

		InputStream is = null;

		if (width <= 0)
			width = RESOLUTION_WIDTH;
		if (height <= 0)
			height = RESOLUTION_HEIGHT;
		try {
			is = context.getResources().openRawResource(resId);

			BitmapFactory.Options options = new Options();
			options.inJustDecodeBounds = true;

			bm = BitmapFactory.decodeStream(is, null, options);

			IOUtils.close(is);

			is = context.getResources().openRawResource(resId);

			options.inSampleSize = Math.min(
					Math.min(options.outWidth, options.outHeight) / width,
					Math.max(options.outWidth, options.outHeight) / height);

			options.inJustDecodeBounds = false;

			bm = BitmapFactory.decodeStream(is, null, options);

		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(is);
		}

		return bm;
	}

	/**
	     * 
	     * @param context
	     * @param src
	     * @return
	     */
	    public static Drawable blur(Context context, Bitmap src) {
		return blur(context, src, 15);
	    }

	    /**
	     * 模糊处理
	     * 
	     * @param context
	     * @param src
	     * @param radius
	     *            模糊度
	     * @return
	     */

	    /**
	     * 模糊处理
	     * 
	     * @param src
	     * @param radius
	     *            模糊程度
	     * @return
	     */
	    public static Drawable blur(Context context, Bitmap src, float radius) {
		return blur(context, src, 8, radius);
	    }

	    public static Drawable blur(Context context, Bitmap src, float scaleFactor,
		    float radius) {

		if (null == src)
		    return null;

		if (scaleFactor <= 0)
		    scaleFactor = 1;

		int width = src.getWidth();
		int height = src.getHeight();

		long startMs = System.currentTimeMillis();
		// float scaleFactor = 8;// 图片缩放比例；
		// float radius = 15;// 模糊程度

		Bitmap overlay = Bitmap.createBitmap((int) (width / scaleFactor),
			(int) (height / scaleFactor), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);
		canvas.translate(0 / scaleFactor, 0 / scaleFactor);
		canvas.scale(1 / scaleFactor, 1 / scaleFactor);
		Paint paint = new Paint();
		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
		canvas.drawBitmap(src, 0, 0, paint);

		overlay = ImageUtils.FastBlur.doBlur(overlay, (int) radius, true);
		/**
		 * 打印高斯模糊处理时间，如果时间大约16ms，用户就能感到到卡顿，时间越长卡顿越明显，如果对模糊完图片要求不高，
		 * 可是将scaleFactor设置大一些。
		 */
		Log.i("jerome", "blur time:" + (System.currentTimeMillis() - startMs));

		return new BitmapDrawable(context.getResources(), overlay);
	    }

	    /**
	     * 模糊处理
	     * 
	     * @param context
	     * @param src
	     * @return
	     */
	    public static Drawable blur(Context context, Drawable src) {
		return blur(context, src, 20);
	    }

	    /**
	     * 模糊处理
	     * 
	     * @param src
	     * @param radius
	     *            模糊程度
	     * @return
	     */
	    public static Drawable blur(Context context, Drawable src, float radius) {
		return blur(context, src, 8, radius);
	    }

	    /**
	     * 
	     * @param context
	     * @param src
	     * @param scaleFactor
	     *            缩放比例，图片缩小到该值的1/scaleFactor
	     * @param radius
	     * @return
	     */
	    public static Drawable blur(Context context, Drawable src,
		    float scaleFactor, float radius) {

		if (null == src)
		    return null;

		if (scaleFactor <= 0)
		    scaleFactor = 1;

		int width = src.getIntrinsicWidth();
		int height = src.getIntrinsicHeight();

		long startMs = System.currentTimeMillis();
		// float scaleFactor = 8;// 图片缩放比例；
		// float radius = 20;// 模糊程度

		Bitmap overlay = Bitmap.createBitmap((int) (width / scaleFactor),
			(int) (height / scaleFactor), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(overlay);
		canvas.translate(0 / scaleFactor, 0 / scaleFactor);
		canvas.scale(1 / scaleFactor, 1 / scaleFactor);
		Paint paint = new Paint();
		paint.setFlags(Paint.FILTER_BITMAP_FLAG);
		// canvas.drawBitmap(bkg, 0, 0, paint);
		src.setBounds(0, 0, width, height);
		src.draw(canvas);

		overlay = ImageUtils.FastBlur.doBlur(overlay, (int) radius, true);
		/**
		 * 打印高斯模糊处理时间，如果时间大约16ms，用户就能感到到卡顿，时间越长卡顿越明显，如果对模糊完图片要求不高，
		 * 可是将scaleFactor设置大一些。
		 */
		Log.i("jerome", "blur time:" + (System.currentTimeMillis() - startMs));

		return new BitmapDrawable(context.getResources(), overlay);
	    }

	/**
	 * 模糊算法
	 */
	public static class FastBlur {

		public static Bitmap doBlur(Bitmap sentBitmap, int radius,
				boolean canReuseInBitmap) {
			Bitmap bitmap;
			if (canReuseInBitmap) {
				bitmap = sentBitmap;
			} else {
				bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
			}

			if (radius < 1) {
				return (null);
			}

			int w = bitmap.getWidth();
			int h = bitmap.getHeight();

			int[] pix = new int[w * h];
			bitmap.getPixels(pix, 0, w, 0, 0, w, h);

			int wm = w - 1;
			int hm = h - 1;
			int wh = w * h;
			int div = radius + radius + 1;

			int r[] = new int[wh];
			int g[] = new int[wh];
			int b[] = new int[wh];
			int rsum, gsum, bsum, x, y, i, p, yp, yi, yw;
			int vmin[] = new int[Math.max(w, h)];

			int divsum = (div + 1) >> 1;
			divsum *= divsum;
			int dv[] = new int[256 * divsum];
			for (i = 0; i < 256 * divsum; i++) {
				dv[i] = (i / divsum);
			}

			yw = yi = 0;

			int[][] stack = new int[div][3];
			int stackpointer;
			int stackstart;
			int[] sir;
			int rbs;
			int r1 = radius + 1;
			int routsum, goutsum, boutsum;
			int rinsum, ginsum, binsum;

			for (y = 0; y < h; y++) {
				rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
				for (i = -radius; i <= radius; i++) {
					p = pix[yi + Math.min(wm, Math.max(i, 0))];
					sir = stack[i + radius];
					sir[0] = (p & 0xff0000) >> 16;
					sir[1] = (p & 0x00ff00) >> 8;
					sir[2] = (p & 0x0000ff);
					rbs = r1 - Math.abs(i);
					rsum += sir[0] * rbs;
					gsum += sir[1] * rbs;
					bsum += sir[2] * rbs;
					if (i > 0) {
						rinsum += sir[0];
						ginsum += sir[1];
						binsum += sir[2];
					} else {
						routsum += sir[0];
						goutsum += sir[1];
						boutsum += sir[2];
					}
				}
				stackpointer = radius;

				for (x = 0; x < w; x++) {

					r[yi] = dv[rsum];
					g[yi] = dv[gsum];
					b[yi] = dv[bsum];

					rsum -= routsum;
					gsum -= goutsum;
					bsum -= boutsum;

					stackstart = stackpointer - radius + div;
					sir = stack[stackstart % div];

					routsum -= sir[0];
					goutsum -= sir[1];
					boutsum -= sir[2];

					if (y == 0) {
						vmin[x] = Math.min(x + radius + 1, wm);
					}
					p = pix[yw + vmin[x]];

					sir[0] = (p & 0xff0000) >> 16;
					sir[1] = (p & 0x00ff00) >> 8;
					sir[2] = (p & 0x0000ff);

					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];

					rsum += rinsum;
					gsum += ginsum;
					bsum += binsum;

					stackpointer = (stackpointer + 1) % div;
					sir = stack[(stackpointer) % div];

					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];

					rinsum -= sir[0];
					ginsum -= sir[1];
					binsum -= sir[2];

					yi++;
				}
				yw += w;
			}
			for (x = 0; x < w; x++) {
				rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
				yp = -radius * w;
				for (i = -radius; i <= radius; i++) {
					yi = Math.max(0, yp) + x;

					sir = stack[i + radius];

					sir[0] = r[yi];
					sir[1] = g[yi];
					sir[2] = b[yi];

					rbs = r1 - Math.abs(i);

					rsum += r[yi] * rbs;
					gsum += g[yi] * rbs;
					bsum += b[yi] * rbs;

					if (i > 0) {
						rinsum += sir[0];
						ginsum += sir[1];
						binsum += sir[2];
					} else {
						routsum += sir[0];
						goutsum += sir[1];
						boutsum += sir[2];
					}

					if (i < hm) {
						yp += w;
					}
				}
				yi = x;
				stackpointer = radius;
				for (y = 0; y < h; y++) {
					// Preserve alpha channel: ( 0xff000000 & pix[yi] )
					pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16)
							| (dv[gsum] << 8) | dv[bsum];

					rsum -= routsum;
					gsum -= goutsum;
					bsum -= boutsum;

					stackstart = stackpointer - radius + div;
					sir = stack[stackstart % div];

					routsum -= sir[0];
					goutsum -= sir[1];
					boutsum -= sir[2];

					if (x == 0) {
						vmin[y] = Math.min(y + r1, hm) * w;
					}
					p = x + vmin[y];

					sir[0] = r[p];
					sir[1] = g[p];
					sir[2] = b[p];

					rinsum += sir[0];
					ginsum += sir[1];
					binsum += sir[2];

					rsum += rinsum;
					gsum += ginsum;
					bsum += binsum;

					stackpointer = (stackpointer + 1) % div;
					sir = stack[stackpointer];

					routsum += sir[0];
					goutsum += sir[1];
					boutsum += sir[2];

					rinsum -= sir[0];
					ginsum -= sir[1];
					binsum -= sir[2];

					yi += w;
				}
			}

			bitmap.setPixels(pix, 0, w, 0, 0, w, h);

			return (bitmap);
		}
	}

	/**
	 * 获取所有图片
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> getAlbumList(Context context) {
		return getAlbumList(context, Integer.MAX_VALUE, new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String filename) {
				// TODO Auto-generated method stub
				return filename.endsWith("jpg") || filename.endsWith("jpeg");
			}
		});
	}

	/**
	 * 获取手机上所有图片
	 * 
	 * @param context
	 * @param maxSize
	 *            最大数量
	 * @return
	 */
	public static List<String> getAlbumList(Context context, int maxSize, FilenameFilter filter) {
		List<String> list = new ArrayList<String>();

		Cursor cursor = null;

		String[] projection = new String[] { MediaStore.Images.ImageColumns.DATA };
		try {
			cursor = context.getContentResolver().query(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
					null, null, ImageColumns.DATE_ADDED + " desc"); // 默认从最新开始
			while (cursor.moveToNext()) {
				String path = cursor.getString(0);
				File file = new File(path);
				if (null == filter || filter.accept(file.getParentFile(), file.getName()))
				{
					list.add(path);
				}
				
				if (list.size() > maxSize)
					break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			IOUtils.close(cursor);
		}
		return list;
	}

	/**
	 * drawable转bitmap
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bm = null;
		if (null == drawable) {

			return bm;
		} else {

			if (drawable instanceof BitmapDrawable)
			{
				return ((BitmapDrawable) drawable).getBitmap();
			}

			int width = drawable.getIntrinsicWidth();
			int height = drawable.getIntrinsicHeight();
			bm = Bitmap.createBitmap(width, height, Config.RGB_565);
			drawable.setBounds(0, 0, width, height);
			Canvas canvas = new Canvas(bm);
			drawable.draw(canvas);
			return bm;
		}

	}

	/**
	 * bitmap转drawable
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap)
	{
		if (null == bitmap)
			return null;

		return new BitmapDrawable(bitmap);
	}


	/**
	 * 保存到sd卡
	 * @param bitmap
	 * @param path
	 * @param quality
	 * @return
	 */
	public static boolean saveBitmap(Bitmap bitmap, String path, int quality) {
		boolean ret = false;
		if (true) {

			File photoFile = new File(path);

			FileUtils.createDir(photoFile.getParent());

			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (bitmap != null) {
					if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality,
							fileOutputStream)) {
						fileOutputStream.flush();
						// fileOutputStream.close();
						ret = true;
					}
				}

			} catch (FileNotFoundException e) {
				photoFile.delete();
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				e.printStackTrace();
			} finally {
				IOUtils.close(fileOutputStream);
			}
		}

		return ret;
	}

	/**
	 *
	 * @param path
	 * @return
	 */
	public static int readPictureDegree(String path)
	{
		int degree  = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			System.out.println("orientation=" + orientation);
			switch(orientation)
			{
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 旋转图片
	 * @param roughBitmap
	 * @param degrees
	 * @param recycleSrc
	 * @return
	 */
	private static Bitmap rotateBitmap(Bitmap roughBitmap, float degrees, boolean recycleSrc)
	{
		if (null == roughBitmap)
		{
			return null;
		}


		if (degrees % 360 == 0)
		{
			return roughBitmap;
		}

		Matrix matrix = new Matrix();
		matrix.postRotate(degrees);

		Bitmap bm = Bitmap.createBitmap(roughBitmap, 0, 0,
				roughBitmap.getWidth(), roughBitmap.getHeight(),
				matrix, false);

		if (recycleSrc)
		{
			roughBitmap.recycle();
			roughBitmap = null;
		}



		return bm;
	}

	/**
	 * 让Gallery上能马上看到该图
	 */
	public static void scanPhoto(Context context, String imagePath) {
		File file = new File(imagePath);
		Uri contentUri = Uri.fromFile(file);
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,contentUri);
		try {
			context.sendBroadcast(mediaScanIntent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
