package com.hanbing.mytest.activity.action;

import java.lang.reflect.Field;

import com.hanbing.mytest.activity.view.TestCropPictureReal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.Formatter;

public class TestCropPicture extends Activity {

	
	
	
	static final int CODE_CHOOSE_PICTURE = 1;
	static final int CODE_CROP_PICTURE = 2;
	
	
	FrameLayout frame;
	ImageView imageSrc;
	ImageView imageCrop;
	static Uri srcUri = Uri.parse("content://media/external/images/media/254463");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		imageSrc = new ImageView(this);
		imageCrop = new ImageView(this);
		
		frame = new FrameLayout(this);
		
		frame.addView(imageSrc, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		frame.addView(imageCrop, 200, 200);
		
		Button btn = new Button(this);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				choosePicture();
			}
		});
		
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
		
		frame.addView(btn, params);
		
		setContentView(frame);
		
		if (null == srcUri)
			choosePicture();
		else 
			cropPicture();
	}
	
	private void choosePicture()
	{
		
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
        intent.setType("image/*"); 
//		int width = 200;
//		int height = 200;
//		
//		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null)
//        .setType("image/*")
//        .putExtra("crop", "true")
//        .putExtra("aspectX", width)
//        .putExtra("aspectY", height)
//        .putExtra("outputX", width)
//        .putExtra("outputY", height)
//        .putExtra("scale", true)//榛戣竟
//        .putExtra("scaleUpIfNeeded", true)//榛戣竟
//        .putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File("/sdcard/crop.jpg")))
//        .putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		
		startActivityForResult(Intent.createChooser(intent, "Choose Picture"), CODE_CHOOSE_PICTURE);
		
		
//		Intent intent = new Intent(Intent.ACTION_PICK, People.CONTENT_URI);
//		startActivityForResult(intent, CODE_CHOOSE_PICTURE);
		
//		{
//			DatePicker picker = new DatePicker(this);
//			
//			Field[] fields = DatePicker.class.getDeclaredFields();
//			
//			for (Field f : fields)
//			{
//				String name = f.getName();
//				
//				if (name.equals("mYearSpinner")
//						|| name.equals("mMonthSpinner")
//						|| name.equals("mDaySpinner"))
//				{
//					
//					NumberPicker numberPicker;
//					try {
//						f.setAccessible(true);
//						numberPicker = (NumberPicker) f.get(picker);
//						setPickerDivider(numberPicker, 0xffff0000);
//					} catch (IllegalAccessException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IllegalArgumentException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					
//					
//				}
//			}
//			
//			picker.init(2015, 5, 3, new OnDateChangedListener() {
//				
//				@Override
//				public void onDateChanged(DatePicker view, int year, int monthOfYear,
//						int dayOfMonth) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
//			picker.setCalendarViewShown(false);
//			
//			addContentView(picker, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//		}
//		
		
//		{
//			NumberPicker picker = new NumberPicker(this);
//			picker.setMinValue(1);
//			picker.setMaxValue(31);
//			picker.setValue(20);
//			
//			setPickerDivider(picker, 0xffff0000);
//			
//			addContentView(picker, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//		}
		 
//		{
//			MyDatePicker picker = new MyDatePicker(this);
//			
//			addContentView(picker, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//		}
		
	}
	
	private void setPickerDivider(NumberPicker picker, int i) {
		// TODO Auto-generated method stub
		Field[] fields = NumberPicker.class.getDeclaredFields();
		
		for (Field f : fields)
		{
			if (f.getName().equals("mSelectionDivider"))
			{
				f.setAccessible(true);
				
				try {
					f.set(picker, new ColorDrawable(0xffff0000));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				f.setAccessible(false);
				
				break;
			}
		}
		
		picker.setFormatter(new Formatter() {
			
			@Override
			public String format(int value) {
				// TODO Auto-generated method stub
				return String.valueOf(value) + "日";
			}
		});
	}

	private void cropPicture()
	{
		Intent intent = new Intent();  
//        
//        intent.setAction("com.android.camera.action.CROP"); 
		intent.setClass(this, TestCropPictureReal.class);
        intent.setDataAndType(srcUri, "image/*");
        intent.putExtra("crop", "true");  
//        intent.putExtra("aspectX", 2);// 瑁佸壀妗嗘瘮渚�  
//        intent.putExtra("aspectY", 1);  
//        intent.putExtra("outputX", 150);// 杈撳嚭鍥剧墖澶у皬  
//        intent.putExtra("outputY", 150);  
        intent.putExtra("return-data", true);  
          
        startActivityForResult(intent, CODE_CROP_PICTURE);
		
		
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
		
		if (RESULT_OK == resultCode)
		{
			switch (requestCode)
			{
			case CODE_CHOOSE_PICTURE:
			{
				if (null != data)
				{
					srcUri = data.getData();
					
					imageSrc.setImageURI(srcUri);
					cropPicture();
				}
			}
				break;
			case CODE_CROP_PICTURE:
			{
				if (null != data)
				{
					Bitmap bm = data.getParcelableExtra("data");
					
					imageCrop.setImageBitmap(bm);
				}
			}
				break;
			}
		}
	}
}
