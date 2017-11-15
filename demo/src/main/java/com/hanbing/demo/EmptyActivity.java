package com.hanbing.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.EditText;

import com.hanbing.library.android.util.ViewUtils;

public class EmptyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);


        EditText editText = (EditText) findViewById(R.id.edit);

        ViewUtils.disableKeyboard(editText);
    }
}
