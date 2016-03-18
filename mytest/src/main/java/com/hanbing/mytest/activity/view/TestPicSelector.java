/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��6��9�� 
 * Time : ����10:28:51
 */
package com.hanbing.mytest.activity.view;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * TestPicSelector.java 
 * @author hanbing 
 * @date 2014��6��9�� 
 * @time ����10:28:51
 */
public class TestPicSelector extends Activity {

    
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        
        
        
        
        Button button = new Button(this);
        
        
        button.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startSelector();
            }
        });
        
        button.setText("click");
        
        layout.addView(button);
        
        
        image = new ImageView(this);
        
        layout.addView(image);
        
        setContentView(layout);
    }

    /**
     * 
     */
    private void startSelector() {
        // TODO Auto-generated method stub
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        
//        startActivityForResult(intent, 123);
        
        
        Intent mIntent = new Intent(Intent.ACTION_GET_CONTENT);
        mIntent.addCategory(Intent.CATEGORY_OPENABLE);
        mIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(mIntent,
                "selector"),
                123);
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        
        super.onActivityResult(requestCode, resultCode, data);
        
        // TODO Auto-generated method stub
        Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        
        
        switch (requestCode)
        {
            case 123:
                
                
                break;
                
        }
        
        
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        System.out.println("onDestroy()");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        System.out.println("onPause()");
        super.onPause();
    }


    
}
