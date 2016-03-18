/**
 * 
 */
package com.hanbing.mytest.activity.view;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.NumberPicker.Formatter;

import com.hanbing.mytest.activity.BaseActivity;

/**
 * @author hanbing
 * @date 2015-8-17
 */
public class TestNumberPicker extends BaseActivity {

    NumberPicker numberPicker = null;
    
    DatePicker datePicker = null;
    /**
     * 
     */
    public TestNumberPicker() {
	// TODO Auto-generated constructor stub
    }

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
	
//	setTheme(R.style.NumberPickerTheme);
        super.onCreate(arg0);
        
        numberPicker = new NumberPicker(this);
        numberPicker.setMaxValue(20);
        numberPicker.setMinValue(0);
        numberPicker.setFormatter(new Formatter() {
	    
	    @Override
	    public String format(int value) {
		// TODO Auto-generated method stub
		return value + "��";
	    }
	});
        
//        setContentView(numberPicker);
        
        datePicker = new DatePicker(this);
        setContentView(datePicker);
    }
}
