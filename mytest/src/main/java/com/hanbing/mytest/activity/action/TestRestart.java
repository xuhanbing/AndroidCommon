/**
 * 
 */
package com.hanbing.mytest.activity.action;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.hanbing.mytest.activity.BaseActivity;

/**
 * @author hanbing
 * @date 2015-8-14
 */
public class TestRestart extends BaseActivity {


    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        
        Button btn = new Button(this);
        
        btn.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		restart();
	    }
	});
        btn.setText("restart");
        setContentView(btn);
    }
    
    private void restart()
    {
	Intent i = getPackageManager()  
                .getLaunchIntentForPackage(getPackageName());  
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
        startActivity(i);
    }
}
