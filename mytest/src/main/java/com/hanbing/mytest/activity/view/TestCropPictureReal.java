package com.hanbing.mytest.activity.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

import com.hanbing.library.android.util.ImageUtils;
import com.hanbing.mytest.R;
import com.hanbing.mytest.activity.BaseActivity;
import com.hanbing.mytest.view.CropImageView;

public class TestCropPictureReal extends BaseActivity {

	
	CropImageView cropImageView;
	
	Uri src;
	int aspectX = 1;
	int aspectY = 1;
	int outputX = 100;
	int outputY = 100;
	boolean returnData = true;
	
	String savePath = "/sdcard/crop.jpg";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_cropimagereal);
		
		cropImageView = (CropImageView) findViewById(R.id.iv_cropimage);
		
		Intent intent = getIntent();
		
		if (null == intent)
		{
			
			Toast.makeText(this, "Illegal params!", Toast.LENGTH_SHORT).show();
			
			finish();
			
			return;
		}
		
		DisplayMetrics dm = getResources().getDisplayMetrics();
		
		int w = dm.widthPixels;
		int h = dm.heightPixels;
		
		outputX = outputY = Math.min(w, h) / 2;
		
		src = intent.getData();
		aspectX = intent.getExtras().getInt("aspectX", aspectX);
		aspectY = intent.getExtras().getInt("aspectY", aspectY);
		outputX = intent.getExtras().getInt("outputX", outputX);
		outputY = intent.getExtras().getInt("outputY", outputY);
		
		
		cropImageView.setSrc(src);
		cropImageView.setAspect(aspectX, aspectY);
		cropImageView.setOutput(outputX, outputY);
		cropImageView.setImageURI(src);
		
		
	}
	
	
	public void onConfirm(View v)
	{
		setResult(RESULT_OK);
		
		Bitmap bm =  cropImageView.cropImage();
		
		if (null != bm)
		{
			ImageView image = new ImageView(this);
			image.setImageBitmap(bm);
			addContentView(image, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			
			if (returnData)
			{
//				Intent data = new Intent();
//				data.putExtra("data", bm);
				
//				setResult(RESULT_OK, data);
			}
//			ImageTool.savePhotoToSDCard(bm, savePath, 100);

			ImageUtils.saveBitmap(bm, savePath, 100);
		}
		
//		finish();
	}
	
	public void onCancel(View v)
	{
		setResult(RESULT_CANCELED);
		finish();
	}

}
