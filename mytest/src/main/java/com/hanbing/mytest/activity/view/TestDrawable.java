/**
 * 
 */
package com.hanbing.mytest.activity.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.hanbing.mytest.activity.BaseActivity;
import com.hanbing.mytest.view.RoundImageView2;

/**
 * @author hanbing
 * @date 2015-12-17
 */
public class TestDrawable extends BaseActivity {

    /**
     * 
     */
    public TestDrawable() {
	// TODO Auto-generated constructor stub
    }
    
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
	
	
	LinearLayout layout = new LinearLayout(this);
	layout.setOrientation(LinearLayout.VERTICAL);
	
	
	
	Drawable drawable = getResources().getDrawable(R.drawable.a);
		
	int w = drawable.getIntrinsicWidth();
	int h = drawable.getIntrinsicHeight();
	
	Log.e("", "drawable w=" + w + ",h=" + h);

	int x = w / 2;
	int y = 0;
	int width = w/2;
	int height = h/2;
	
	Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
	{
	    drawable.setBounds(0, 0, w, h);
	    Canvas canvas = new Canvas(bitmap);
	    drawable.draw(canvas);
	}
	
	Bitmap bitmap1 = Bitmap.createBitmap(width, height, Config.ARGB_8888);
	{
	    drawable.setBounds(-x, -y, w-x, h-y);
	    Canvas canvas = new Canvas(bitmap1);
		canvas.drawColor(Color.GREEN);
		drawable.draw(canvas);
	}
	
	Bitmap bitmap2 = Bitmap.createBitmap(width, height, Config.ARGB_8888);
	{
	    drawable.setBounds(0, 0, width, height);
	    Canvas canvas = new Canvas(bitmap2);
	    canvas.save();
	    canvas.translate(-width/2, -height/2);
		canvas.drawColor(Color.GREEN);
		drawable.draw(canvas);
		canvas.restore();
	}
	
	
        super.onCreate(arg0);
        
        RoundImageView2 imageView = new RoundImageView2(this);
        imageView.setImageDrawable(drawable);
        imageView.setBackgroundColor(Color.RED);
        imageView.setScaleType(ScaleType.CENTER_CROP);
        
        RoundImageView2 imageView2 = new RoundImageView2(this);
        imageView2.setImageBitmap(bitmap1);
        imageView2.setBackgroundColor(Color.YELLOW);
        imageView2.setScaleType(ScaleType.CENTER_CROP);
        
        RoundImageView2 imageView3 = new RoundImageView2(this);
        imageView3.setImageBitmap(bitmap2);
        imageView3.setBackgroundColor(Color.YELLOW);
        imageView3.setScaleType(ScaleType.CENTER_CROP);
        
        LayoutParams params = new LayoutParams(200, -1);
	layout.addView(imageView, new LayoutParams(200, bitmap.getHeight() * 200 / bitmap.getWidth()) );
	layout.addView(imageView2, new LayoutParams(200, bitmap1.getHeight() * 200 / bitmap1.getWidth()) );
	layout.addView(imageView3, new LayoutParams(200, bitmap2.getHeight() * 200 / bitmap2.getWidth()) );
        
        setContentView(layout);
    }

}
