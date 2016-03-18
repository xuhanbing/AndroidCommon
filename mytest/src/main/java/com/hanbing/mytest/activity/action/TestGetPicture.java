/**
 * 
 */
package com.hanbing.mytest.activity.action;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.common.util.FileUtils;
import com.hanbing.mytest.activity.BaseActivity;

/**
 * @author hanbing
 * @date 2015-11-26
 */
public class TestGetPicture extends BaseActivity {

    
    
    ImageView imageView;
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        imageView = new ImageView(this);
        Button btn1 = new Button(this);
        btn1.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");
		startActivityForResult(intent, 0);
	    }
	});
        btn1.setText("" + Intent.ACTION_PICK);
        
        Button btn2 = new Button(this);
        btn2.setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, 0);
	    }
	});
        btn2.setText("" + Intent.ACTION_GET_CONTENT);
        
        layout.addView(btn1);
        layout.addView(btn2);
        layout.addView(imageView);
        
        setContentView(layout);
    }
    
    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
	if (RESULT_OK == arg1 && 0 == arg0)
	{
	    if (null != arg2)
	    {
		Uri uri = arg2.getData();
		
		String path = FileUtils.getAbsPath(this, uri);
		
		imageView.setImageBitmap(BitmapFactory.decodeFile(path));
			
	    }
	}
        super.onActivityResult(arg0, arg1, arg2);
    }
}
