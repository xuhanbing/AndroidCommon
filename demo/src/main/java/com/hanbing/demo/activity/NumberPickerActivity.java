package com.hanbing.demo.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.hanbing.demo.R;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.view.NumberPicker;

public class NumberPickerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_picker);
//        NumberPicker numberPicker = new NumberPicker(this);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);

//        numberPicker.setDisplayedValues(values);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(10);
        numberPicker.setTextColor(Color.RED);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                LogUtils.e("value changed old = " + oldVal + ", new = " + newVal);
            }
        });

//        setContentView(numberPicker);
    }
}
