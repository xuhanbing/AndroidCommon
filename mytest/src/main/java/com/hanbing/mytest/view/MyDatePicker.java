package com.hanbing.mytest.view;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

public class MyDatePicker extends LinearLayout {
	
	public interface OnDateChangedListener
	{
		public void onDateChanged(NumberPicker picker, int year, int month, int day);
	}
	
	
	NumberPicker mYearPicker;
	NumberPicker mMonthPicker;
	NumberPicker mDayPicker;

	public MyDatePicker(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public MyDatePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public MyDatePicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}
	
	private void init()
	{
		setOrientation(HORIZONTAL);
		
		mYearPicker = new NumberPicker(getContext());
		
		addView(mYearPicker);
		
		mMonthPicker = new NumberPicker(getContext());
		
		addView(mMonthPicker);
		
		mDayPicker = new NumberPicker(getContext());
		addView(mDayPicker);
	
		
		Date date = new Date(System.currentTimeMillis());
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		init(c.get(DateFormat.YEAR_FIELD), c.get(DateFormat.MONTH_FIELD), c.get(DateFormat.DATE_FIELD));
	}
	
	public void init(int year, int monthOfYear, int monthOfDay)
	{
		mYearPicker.setValue(year);
		mYearPicker.setMinValue(1970);
		mYearPicker.setMaxValue(2099);
		
		mMonthPicker.setValue(monthOfYear + 1);
		mMonthPicker.setMinValue(1);
		mMonthPicker.setMaxValue(12);
		
		mDayPicker.setValue(monthOfDay + 1);
		mDayPicker.setMinValue(1);
		mDayPicker.setMaxValue(31);
	}
	
	public void setSelectionDivider(int color)
	{
		setSelectionDivider(new ColorDrawable(color));
	}
	
	public void setSelectionDivider(ColorDrawable d)
	{
		setPickerDivider(mYearPicker, d);
		setPickerDivider(mMonthPicker, d);
		setPickerDivider(mDayPicker, d);
	}
	
	private void setPickerDivider(NumberPicker picker, ColorDrawable d) {
		// TODO Auto-generated method stub
		Field[] fields = NumberPicker.class.getDeclaredFields();
		
		for (Field f : fields)
		{
			if (f.getName().equals("mSelectionDivider"))
			{
				f.setAccessible(true);
				
				try {
					f.set(picker, d);
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
		
	}

}
