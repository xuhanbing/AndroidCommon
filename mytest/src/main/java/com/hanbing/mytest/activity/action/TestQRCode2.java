package com.hanbing.mytest.activity.action;

import java.io.IOException;
import java.util.Hashtable;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.hanbing.mytest.R;
import com.hanbing.mytest.utils.camera.CameraManager;
import com.hanbing.mytest.view.QRCodeImageView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class TestQRCode2 extends Activity implements SurfaceHolder.Callback{

	
	SurfaceHolder mSurfaceHolder;
	SurfaceView mPreview;
	Camera mCamera;
	
	MultiFormatReader multiFormatReader;
	
	int mOrientation = 0;
	OrientationEventListener mOrientationEventLsner = null;
	
	boolean isDecoding = true;
	boolean isSurfaceOk = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState); 
		
		setContentView(R.layout.activity_qrcode);
		
		mPreview = (SurfaceView) findViewById(R.id.sv_preview);
		
		init();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		CameraManager.get().closeDriver();
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		if (isSurfaceOk)
		{
			startCamera(mSurfaceHolder);
			
		}
		else
		{
		}
		
		
		
		super.onResume();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		CameraManager.get().stopPreview();
		super.onStop();
	}
	
	private void init()
	{
		
		CameraManager.init(this);
		if (null == mSurfaceHolder)
		{
			mSurfaceHolder = mPreview.getHolder();
			mSurfaceHolder.addCallback(this);
		}
		
	}
	
	private void startCamera(SurfaceHolder holder)
	{
		try {
			CameraManager.get().openDriver(holder);
			CameraManager.get().setDisplayOrientation(
					getWindowManager().getDefaultDisplay().getRotation());
			CameraManager.get().requestPreviewFrame(decodeHandler, 123);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CameraManager.get().startPreview();
	}
	
	Handler decodeHandler = new Handler()
	{
		public void dispatchMessage(Message msg) {
			
			if (isDecoding)
			{
				Result rawResult = null;
				
				byte[] data = (byte[]) msg.obj;
				int dataWidth = msg.arg1;
				int dataHeight = msg.arg2;
				int left = 0;
				int top = 0;
				int width = dataWidth;
				int height = dataHeight;
				PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(data, 
						dataWidth, 
						dataHeight, 0, 0, width, height);
				
				BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			    try {
			      rawResult = multiFormatReader.decodeWithState(bitmap);
			    } catch (ReaderException re) {
			      // continue
			    } finally {
			      multiFormatReader.reset();
			    }
			    
			    if (null != rawResult)
			    {
			    	isDecoding = false;
			    	
			    	Bitmap bm = source.renderCroppedGreyscaleBitmap();
			    	
			    	Matrix matrix = new Matrix();
			    	matrix.postRotate(0);
			    	
			    	Bitmap nb = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, false);
					showResult(rawResult.getText(), nb);
//			    	Toast.makeText(getApplicationContext(), rawResult.getText(), Toast.LENGTH_SHORT).show();
			    }
			}
		};
	};
	

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		if (!isSurfaceOk)
		{
			isSurfaceOk = true;
			
			startCamera(holder);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		isSurfaceOk = false;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		
		super.onConfigurationChanged(newConfig);
	}
	
	private void showResult(String result, Bitmap bm)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		QRCodeImageView image = new QRCodeImageView(this);
		image.setLayoutParams(new LayoutParams(100, 100));
		image.setImageBitmap(bm);
		
		
		builder.setTitle(result);
		builder.setView(image);
		
		builder.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				isDecoding = true;
			}
		});
		
		builder.create().show();
	}

	public final class PlanarYUVLuminanceSource extends LuminanceSource {
		  private final byte[] yuvData;
		  private final int dataWidth;
		  private final int dataHeight;
		  private final int left;
		  private final int top;

		  public PlanarYUVLuminanceSource(byte[] yuvData, int dataWidth, int dataHeight, int left, int top,
		      int width, int height) {
		    super(width, height);

		    if (left + width > dataWidth || top + height > dataHeight) {
		      throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
		    }

		    this.yuvData = yuvData;
		    this.dataWidth = dataWidth;
		    this.dataHeight = dataHeight;
		    this.left = left;
		    this.top = top;
		  }

		  @Override
		  public byte[] getRow(int y, byte[] row) {
		    if (y < 0 || y >= getHeight()) {
		      throw new IllegalArgumentException("Requested row is outside the image: " + y);
		    }
		    int width = getWidth();
		    if (row == null || row.length < width) {
		      row = new byte[width];
		    }
		    int offset = (y + top) * dataWidth + left;
		    System.arraycopy(yuvData, offset, row, 0, width);
		    return row;
		  }

		  @Override
		  public byte[] getMatrix() {
		    int width = getWidth();
		    int height = getHeight();

		    // If the caller asks for the entire underlying image, save the copy and give them the
		    // original data. The docs specifically warn that result.length must be ignored.
		    if (width == dataWidth && height == dataHeight) {
		      return yuvData;
		    }

		    int area = width * height;
		    byte[] matrix = new byte[area];
		    int inputOffset = top * dataWidth + left;

		    // If the width matches the full width of the underlying data, perform a single copy.
		    if (width == dataWidth) {
		      System.arraycopy(yuvData, inputOffset, matrix, 0, area);
		      return matrix;
		    }

		    // Otherwise copy one cropped row at a time.
		    byte[] yuv = yuvData;
		    for (int y = 0; y < height; y++) {
		      int outputOffset = y * width;
		      System.arraycopy(yuv, inputOffset, matrix, outputOffset, width);
		      inputOffset += dataWidth;
		    }
		    return matrix;
		  }

		  @Override
		  public boolean isCropSupported() {
		    return true;
		  }

		  public int getDataWidth() {
		    return dataWidth;
		  }

		  public int getDataHeight() {
		    return dataHeight;
		  }

		  public Bitmap renderCroppedGreyscaleBitmap() {
		    int width = getWidth();
		    int height = getHeight();
		    int[] pixels = new int[width * height];
		    byte[] yuv = yuvData;
		    int inputOffset = top * dataWidth + left;

		    for (int y = 0; y < height; y++) {
		      int outputOffset = y * width;
		      for (int x = 0; x < width; x++) {
		        int grey = yuv[inputOffset + x] & 0xff;
		        pixels[outputOffset + x] = 0xFF000000 | (grey * 0x00010101);
		      }
		      inputOffset += dataWidth;
		    }

		    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		    bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		    return bitmap;
		  }
		}
	
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		menu.add(0, 0, 0, "Create2DCode");
		return super.onCreateOptionsMenu(menu);
	};
	
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId())
		{
		case 0:
		{
			QRCodeImageView image = new QRCodeImageView(this);
			
			Bitmap center  = BitmapFactory.decodeResource(getResources(), R.drawable.a);
			image.setCenterImage(center);
			
			CreateQRImageTest test = new CreateQRImageTest();
			
			Bitmap bm = test.createQRImage("");
			image.setImageBitmap(bm);
			
			
			Toast.makeText(this, "Create qr image bm=" + bm , Toast.LENGTH_SHORT).show();
			
			image.setLayoutParams(new LayoutParams(400, 400));
			
			PopupWindow pw = new PopupWindow(image, 400, 400);
			
			pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			pw.setFocusable(false);
			pw.setOutsideTouchable(true);
			
			pw.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0,0);
			
			
		}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public class CreateQRImageTest
	{
		private ImageView sweepIV;
		private int QR_WIDTH = 200, QR_HEIGHT = 200;
		/**
		 * @��������˵��: ���ɶ�ά��ͼƬ,ʵ��ʹ��ʱҪ��ʼ��sweepIV,��Ȼ�ᱨ��ָ�����
		 * @����:������
		 * @ʱ��:2013-4-18����11:14:16
		 * @����: @param url Ҫת���ĵ�ַ���ַ���,����������
		 * @return void
		 * @throws
		 */
		
		//Ҫת���ĵ�ַ���ַ���,����������
		public Bitmap createQRImage(String url)
		{
			try
			{
				//�ж�URL�Ϸ���
				if (url == null || "".equals(url) || url.length() < 1)
				{
					return null;
				}
				Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
				hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
				//ͼ������ת����ʹ���˾���ת��
				BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
				int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
				//�������ﰴ�ն�ά����㷨��������ɶ�ά���ͼƬ��
				//����forѭ����ͼƬ����ɨ��Ľ��
				for (int y = 0; y < QR_HEIGHT; y++)
				{
					for (int x = 0; x < QR_WIDTH; x++)
					{
						if (bitMatrix.get(x, y))
						{
							pixels[y * QR_WIDTH + x] = 0xff000000;
						}
						else
						{
							pixels[y * QR_WIDTH + x] = 0xffffffff;
						}
					}
				}
				//���ɶ�ά��ͼƬ�ĸ�ʽ��ʹ��ARGB_8888
				Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
				bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
//				//��ʾ��һ��ImageView����
//				sweepIV.setImageBitmap(bitmap);
				
				return bitmap;
			}
			catch (WriterException e)
			{
				e.printStackTrace();
			}
			return null;
		}
	}
}
