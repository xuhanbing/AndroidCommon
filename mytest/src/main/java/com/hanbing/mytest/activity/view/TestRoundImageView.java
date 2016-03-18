package com.hanbing.mytest.activity.view;

import com.hanbing.mytest.R;
import com.hanbing.mytest.view.RoundImageView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;


/**
 * ��TestRoundImageView.java��ʵ��������TODO ��ʵ������ 
 * @author Administrator 2014��2��21�� ����5:35:07
 */
public class TestRoundImageView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        ScrollView sv =new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        
//        RoundImageView image = new RoundImageView(this);
//        image.setBackgroundResource(R.drawable.ic_launcher);
        RoundImageView image = new RoundImageView(this);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.a00);
        image.setImageBitmap(bm);  
        ll.addView(image, new LayoutParams(150,150));
        
        Button btn = new Button(this);
        btn.setText("Hello");
        
        btn.setBackgroundResource(R.drawable.btn_bg);
        ll.addView(btn, new LayoutParams(150, 150));
        
        sv.addView(ll);
        this.setContentView(sv);
       
        
    }

    

}
