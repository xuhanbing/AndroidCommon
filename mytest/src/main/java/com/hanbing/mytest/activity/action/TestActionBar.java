/**
 * 
 */
package com.hanbing.mytest.activity.action;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.hanbing.library.android.util.ToastUtils;
import com.hanbing.mytest.activity.BaseActivity;

/**
 * @author hanbing
 * @date 2015-11-30
 */
public class TestActionBar extends BaseActivity {

    
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
	
//	getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
	
	ActionBar actionBar = getActionBar();
	actionBar.setTitle("back");
	actionBar.setIcon(R.drawable.a);
	actionBar.setDisplayHomeAsUpEnabled(true);
	TextView view = new TextView(this);
	view.setBackgroundColor(Color.WHITE);
	view.setText("custom view");
	actionBar.setCustomView(view);
        super.onCreate(arg0);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
	getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    } 
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
	
	ToastUtils.showToast(this, item.getTitle().toString());
	switch (item.getItemId())
	{
	case R.id.action_exit:
	    finish();
	    break;
	}
        return super.onOptionsItemSelected(item);
    }

}
