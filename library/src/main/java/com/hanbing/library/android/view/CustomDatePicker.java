package com.hanbing.library.android.view;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

import com.androidcommon.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 日期选择器
 * 
 * @author hanbing
 * @date 2015-6-19
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CustomDatePicker extends LinearLayout implements
		NumberPicker.OnValueChangeListener {

	public static interface OnDateChangedListener {
		public void onDateChanged(NumberPicker picker, int year,
				int monthOfYear, int dayOfMonth);
	}

	NumberPicker mYearPicker;
	NumberPicker mMonthPicker;
	NumberPicker mDayPicker;

	OnDateChangedListener mOnDateChangedListener;

	public CustomDatePicker(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	public CustomDatePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public CustomDatePicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		// setOrientation(HORIZONTAL);
		//
		// LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT,
		// 1);
		//
		// mYearPicker = new NumberPicker(getContext());
		// mMonthPicker = new NumberPicker(getContext());
		// mDayPicker = new NumberPicker(getContext());
		//
		// addView(mYearPicker, params);
		// addView(mMonthPicker, params);
		// addView(mDayPicker, params);

		LayoutInflater.from(getContext()).inflate(R.layout.layout_datepicker,
				this);

		mYearPicker = (NumberPicker) findViewById(R.id.np_year);
		mMonthPicker = (NumberPicker) findViewById(R.id.np_month);
		mDayPicker = (NumberPicker) findViewById(R.id.np_day);

		mYearPicker.setFormatter(new NumberPicker.Formatter() {

			@Override
			public String format(int value) {
				// TODO Auto-generated method stub
				return value + "年";
			}
		});

		mMonthPicker.setFormatter(new NumberPicker.Formatter() {

			@Override
			public String format(int value) {
				// TODO Auto-generated method stub
				return value + "月";
			}
		});

		mDayPicker.setFormatter(new NumberPicker.Formatter() {

			@Override
			public String format(int value) {
				// TODO Auto-generated method stub
				return value + "日";
			}
		});

		mYearPicker.setOnValueChangedListener(this);
		mMonthPicker.setOnValueChangedListener(this);
		mDayPicker.setOnValueChangedListener(this);

		// int color = getResources().getColor(R.color.common_main_color);
		//
		// /**字体颜色设置为黑色*/
		// setPickerColor(mYearPicker, color);
		// setPickerColor(mMonthPicker, color);
		// setPickerColor(mDayPicker, color);

		/** 关闭编辑模式 */
		mYearPicker
				.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
		mMonthPicker
				.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
		mDayPicker
				.setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);

		/** 初始化时间为的当前时间 **/
		Date date = new Date(System.currentTimeMillis());
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		init(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
				c.get(Calendar.DAY_OF_MONTH), null);
	}

	/**
	 * 初始化数据
	 * 
	 * @param year
	 *            年
	 * @param monthOfYear
	 *            月，从0开始到11结束
	 * @param dayOfMonth
	 *            日，从1开始
	 * @param lsner
	 *            数据回调
	 */
	public void init(int year, int monthOfYear, int dayOfMonth,
			OnDateChangedListener lsner) {

		mYearPicker.setMinValue(year - 150);
		mYearPicker.setMaxValue(year);
		mYearPicker.setValue(year);
		mYearPicker.setWrapSelectorWheel(false);

		mMonthPicker.setMinValue(1);
		mMonthPicker.setMaxValue(12);
		mMonthPicker.setValue(monthOfYear + 1);

		mDayPicker.setMinValue(1);
		mDayPicker.setMaxValue(31);
		mDayPicker.setValue(dayOfMonth);

		this.mOnDateChangedListener = lsner;

		updateValues(null);
	}

	public void setOnDateChangedListener(OnDateChangedListener lsner) {
		this.mOnDateChangedListener = lsner;
	}

	/**
	 * 设置分割线
	 * 
	 * @param color
	 */
	public void setSelectionDivider(int color) {
		setSelectionDivider(new ColorDrawable(color));
	}

	/**
	 * 设置分割线
	 * 
	 * @param d
	 */
	public void setSelectionDivider(Drawable d) {
		setPickerDivider(mYearPicker, d);
		setPickerDivider(mMonthPicker, d);
		setPickerDivider(mDayPicker, d);
	}

	/**
	 * 设置picker的分割线
	 * 
	 * @param picker
	 * @param d
	 */
	private void setPickerDivider(NumberPicker picker, Drawable d) {
		setPickerDivider(picker, d, 1);
	}

	/**
	 * 设置picker的分割线
	 * 
	 * @param picker
	 * @param d
	 *            图片
	 * @param height
	 *            分割线高度
	 */
	private void setPickerDivider(NumberPicker picker, Drawable d, int height) {
		// TODO Auto-generated method stub
		Field[] fields = NumberPicker.class.getDeclaredFields();
		for (Field f : fields) {
			if (f.getName().equals("mSelectionDivider")) {
				f.setAccessible(true);

				try {
					f.set(picker, d);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					f.setAccessible(false);
				}

			} else if (f.getName().equals("mSelectionDividerHeight")) {
				/**
				 * 设置分割线高度
				 */
				f.setAccessible(true);

				try {
					f.set(picker, height);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					f.setAccessible(false);
				}
			}
		}

	}

	private void setPickerColor(NumberPicker picker, int color) {
		// TODO Auto-generated method stub
		Field[] fields = NumberPicker.class.getDeclaredFields();

		for (Field f : fields) {
			if (f.getName().equals("mSelectorWheelPaint")) {
				f.setAccessible(true);
				try {
					Paint paint = (Paint) f.get(picker);
					paint.setColor(color);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					f.setAccessible(false);
				}
			} else if (f.getName().equals("mInputText")) {
				f.setAccessible(true);
				try {
					EditText edit = (EditText) f.get(picker);
					edit.setTextColor(color);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					f.setAccessible(false);
				}
				break;
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.NumberPicker.OnValueChangeListener#onValueChange(android
	 * .widget.NumberPicker, int, int)
	 */
	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		// TODO Auto-generated method stub

		updateValues(picker);

		if (null != mOnDateChangedListener) {
			mOnDateChangedListener.onDateChanged(picker,
					mYearPicker.getValue(), mMonthPicker.getValue() - 1,
					mDayPicker.getValue());
		}
	}

	/**
	 * 设置最大值
	 * 
	 * @param picker
	 */
	private void updateValues(NumberPicker picker) {
		// TODO Auto-generated method stub
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		int year = c.get(Calendar.YEAR);
		int monthOfYear = c.get(Calendar.MONTH);
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);

		int yValue = mYearPicker.getValue();
		int mValue = mMonthPicker.getValue();
		int dValue = mDayPicker.getValue();

		/** 计算当年当月最大天数 **/
		int maxDay = getDays(yValue, mValue - 1);

		if (yValue == year) {
			/** 如果当前年份，最大为当月 **/
			updatePicker(mMonthPicker, mValue, 1, monthOfYear + 1, true);

			/** 如果当月， 最大为当天 **/
			if (mValue == monthOfYear + 1) {
				updatePicker(mDayPicker, dValue, 1, dayOfMonth, true);
			} else {
				updatePicker(mDayPicker, dValue, 1, maxDay, true);
			}
		} else {
			/** 一年12个月 **/
			updatePicker(mMonthPicker, mValue, 1, 12, true);

			updatePicker(mDayPicker, dValue, 1, maxDay, true);

		}
	}

	private void updatePicker(NumberPicker picker, int curValue, int minValue,
			int maxValue, boolean wheel) {
		picker.setMinValue(minValue);
		picker.setMaxValue(maxValue);
		picker.setValue(curValue);
		picker.setWrapSelectorWheel(wheel);
	}

	public int getDays(int year, int monthOfYear) {
		int[] days = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

		boolean isLeapYear = year % 400 == 0
				|| (year % 100 != 0 && year % 4 == 0);

		if (monthOfYear == 1 && isLeapYear) {
			return days[1] + 1;
		} else {
			return days[monthOfYear];
		}
	}

}
