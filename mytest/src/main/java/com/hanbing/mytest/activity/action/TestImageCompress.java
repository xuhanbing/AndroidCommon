/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��7��1�� 
 * Time : ����2:07:53
 */
package com.hanbing.mytest.activity.action;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.hanbing.library.android.util.FileUtils;
import com.hanbing.mytest.common.ConstantValues;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * TestImageCompress.java 
 * @author hanbing 
 * @date 2014��7��1�� 
 * @time ����2:07:53
 */
public class TestImageCompress extends Activity {

	private static final int REQUEST_CODE_CAMERA = 666;
    private static final int REQUEST_CODE_ALBUM = 667;
    
    private static final int DST_SIZE = 100 * 1024;

    EditText inputSrcPath;
    EditText inputWidth;
    EditText inputHeight;
    EditText inputSize;
    EditText inputQuality;
    EditText outputSize;
    String imagePath = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_imagecompress);
        
        initView();
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// TODO Auto-generated method stub
    	
    	menu.add(0, 0, 0, "Camera");
    	menu.add(0, 1, 0, "Album");
    	return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	switch (item.getItemId())
    	{
    	case 0:
    		startCamera();
    		break;
    	case 1:
    		startChooser();
    		break;
    	}
    	return super.onOptionsItemSelected(item);
    }

    /**
     * 
     */
    private void initView() {
        // TODO Auto-generated method stub
        inputSrcPath = (EditText) findViewById(R.id.et_imagepath);
        inputWidth = (EditText) findViewById(R.id.et_width);
        inputHeight = (EditText) findViewById(R.id.et_height);
        inputSize = (EditText) findViewById(R.id.et_src_size);
        inputQuality = (EditText) findViewById(R.id.et_quality);
        outputSize = (EditText) findViewById(R.id.et_dst_size);
        
        inputQuality.setText("70");
        outputSize.setText("" + DST_SIZE);
    }
    
    private void startCamera()
    {
    	File file = getFolder();
    	
    	imagePath = file.getAbsolutePath() + "/Camera_" + System.currentTimeMillis() + ".jpg";
    	
    	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//    	intent.setData(Uri.fromFile(new File(imagePath)));
    	intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imagePath)));
    	startActivityForResult(intent, REQUEST_CODE_CAMERA);
    	
    }

    /**
     * 
     */
    private void startChooser() {
        // TODO Auto-generated method stub
        
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_ALBUM);
    }
    
    
    /* (non-Javadoc)
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        
        
        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQUEST_CODE_ALBUM:
                    if (null != data)
                    {
                        Uri uri = data.getData();
                        
                        imagePath = FileUtils.getAbsPath(this, uri);
                        
                        getParams();
                        
                    }
                    break;
                case REQUEST_CODE_CAMERA:
                {
                	getParams();
                }
                	break;
            }
        }
    }
    
    private void getParams() {
		// TODO Auto-generated method stub
    	inputSrcPath.setText(imagePath);
    	
    	scanPhoto(imagePath);
	}

	public void start(View view)
    {
		
		File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/pic");
		
		for (File f : file.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getAbsolutePath().endsWith(".jpg");
			}
		}))
		{
			String dstPath = getFolder().getAbsolutePath() + "/Compress_" + System.currentTimeMillis()+ ".jpg";

		}
		
//		String dstPath = getFolder().getAbsolutePath() + "/Compress_" + System.currentTimeMillis()+ ".jpg";
		
//		compressImage(imagePath, dstPath);
		
		
        
    }
	
	public boolean compressImage(String srcPath, String dstPath)
	{
		int dstSize = Integer.valueOf(outputSize.getEditableText().toString());
        int quality = Integer.valueOf(inputQuality.getEditableText().toString()); 
        
        int width = 0;
        int height = 0;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        
        opts.inJustDecodeBounds = true;
        
        File file = new File(srcPath);
        
        FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			
			int srcSize = fis.available();
	        
	        Bitmap bm = BitmapFactory.decodeStream(fis, null, opts);
	        
	        fis.close();
	        
	        inputWidth.setText(""+opts.outWidth);
	        inputHeight.setText(""+opts.outHeight);
	        inputSize.setText(""+srcSize);
	        
	        width = opts.outWidth;
	        height = opts.outHeight;
	        
	        int srcWidth = width;
	        int srcHeight = height;
	        int dstWidth = 720;
			int dstHeight = 1280;
	        
	        String msg = "";
	        if (srcSize < dstSize)
	        {
	        	msg = "mem size small than need:" + srcSize + "/" + dstSize;
	        	log(msg);
	        	return true;
	        }
	        else
	        {
	        	msg = "mem size big than need:" + srcSize + "/" + dstSize;
	        	log(msg);
	        }
	        
	        int memSampleSize = Math.round(srcSize*1.0f/dstSize);
	        int resolutionSampleSize = calculateSampleSize(srcWidth, srcHeight, dstWidth, dstHeight);
	        
	        opts.inJustDecodeBounds = false;
	        opts.inInputShareable = true;
	        opts.inPurgeable = true;
	        opts.inPreferredConfig = Config.RGB_565;
	        opts.inSampleSize = resolutionSampleSize;
	        
	        fis = new FileInputStream(file);
	        bm = BitmapFactory.decodeFileDescriptor(fis.getFD(), null, opts);
	        fis.close();
	        
	        
	        quality = calculateQuality(resolutionSampleSize, memSampleSize);
	        
	        inputQuality.setText("" + quality);
	        
	        OutputStream os = new FileOutputStream(dstPath);
	        bm.compress(CompressFormat.JPEG, quality, os);
	        
	        
	        os.flush();
	        os.close();
	        
	        
	        fis = new FileInputStream(dstPath);
	        log("Compress success : " + dstPath + ",size=" + fis.available());
	        fis.close();
	        
	        scanPhoto(dstPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	private int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth,
			int dstHeight) {
		// TODO Auto-generated method stub
		int sampleSize = 0;
		
		int w1 = Math.min(srcWidth, srcHeight);
		int h1 = srcWidth + srcHeight - w1;
		
		int w2 = Math.min(srcWidth, dstHeight);
		int h2 = dstWidth + dstHeight - w2;
		
		if (w1 > w2 || h1 > h2)
		{
			sampleSize = Math.min(Math.round(w1*1.0f/w2), 
        			Math.round(h1*1.0f/h2));
		}
		
		sampleSize = scaleSampleSize(Math.max(1, sampleSize));
		
		return sampleSize;
	}
	
	public static int scaleSampleSize(int inSampleSize) {
        // TODO Auto-generated method stub
        
        int size = inSampleSize;
        
        int i = 0;
        
        
        while (size > Math.pow(2, i))
        {
        	i++;
        }
        
        size = (int) Math.pow(2, i);
        
        return size;
    }

	private int calculateQuality(int resolutionSampleSize, int memSampleSize) {
		// TODO Auto-generated method stub
		int quality = 0;
		if (memSampleSize > resolutionSampleSize)
        {
        	float ratio = memSampleSize * 1.0f / resolutionSampleSize;
	        //�ֱ��ʲ��ߣ���ͼƬռ�ڴ�ܴ�ͼƬ�������������𼶽�������
	        if (ratio > 30)
	        {
	        	quality = 15;
	        }
	        else if (ratio > 20)
	        {
	        	quality = 20;
	        }
	        else if (ratio > 10)
	        {
	        	quality = 25;
	        }
	        else if (ratio > 6)
	        {
	        	quality = 30;
	        }
	        else if (ratio > 3)
	        {
	        	quality = 35;
	        }
	        else
	        {
	        	quality = 50;
	        }
	        
        	ratio = 1 / ratio;
        	quality = (int) (ratio * ratio * (100 - quality) + quality);
        }
        else
        {
        	quality = 70;
        }
		
		return quality;
	}

	private void log(String msg)
	{
		Log.e("test compress", msg);
//    	Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	private File getFolder()
	{
		String dir = getCacheDir().getParent();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            dir = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        
        dir += File.separator + ConstantValues.FOLDER_NAME + "/compress";
        
        File file = new File(dir);
        file.mkdirs();
        return file;
	}
	
	public void scanPhoto(String imgFileName) {
		File file = new File(imgFileName);
		Uri contentUri = Uri.fromFile(file);
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,contentUri);
		sendBroadcast(mediaScanIntent);
	}
}
