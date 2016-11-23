package com.hanbing.library.android.tool;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.hanbing.library.android.util.ReflectUtils;

/**
 * Created by hanbing on 2016/11/4
 */

public class TextCounter {

    public interface Formatter {
        public CharSequence format(EditText input, int currentLength, int totalLength);
    }


   static Formatter mFormatter = new Formatter() {
        @Override
        public CharSequence format(EditText input, int currentLength, int totalLength) {
            return currentLength + "/" + totalLength;
        }
    };


    public static void setup(final EditText input, final TextView textView, Formatter formatter) {
        if (null == input || null == textView)
            return;

        int maxLength = 0;
        InputFilter[] filters = input.getFilters();
        if (null != filters && filters.length > 0) {
            for (InputFilter inputFilter : filters) {
                if (inputFilter instanceof InputFilter.LengthFilter) {

                    maxLength =  ReflectUtils.getValue(inputFilter, "mMax", 0);
                    break;
                }
            }
        }


        final Formatter localFormatter = null != formatter ? formatter : mFormatter;
        final int maxLength2 = maxLength;
        textView.setText(localFormatter.format(input, 0, maxLength2));

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textView.setText(localFormatter.format(input, s.length(), maxLength2));
            }
        });
    }

}
