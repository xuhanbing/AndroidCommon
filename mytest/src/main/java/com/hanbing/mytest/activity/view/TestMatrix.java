/**
 * 
 */
package com.hanbing.mytest.activity.view;

import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.ImageView.ScaleType;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.hanbing.mytest.activity.BaseActivity;

/**
 * @author hanbing
 * @date 2015-9-21
 */
public class TestMatrix extends BaseActivity {

    ImageView image;
    SeekBar seekBarX;
    SeekBar seekBarY;

    float scaleX = 1.0f;
    float scaleY = 1.0f;

    Matrix matrix = null;
    @Override
    protected void onCreate(Bundle arg0) {
	// TODO Auto-generated method stub
	super.onCreate(arg0);

	setContentView(R.layout.activity_testmatrix);

	image = (ImageView) findViewById(R.id.image);
	image.setScaleType(ScaleType.CENTER_INSIDE);
	image.setImageResource(R.drawable.n1);

	seekBarX = (SeekBar) findViewById(R.id.scalex);
	seekBarY = (SeekBar) findViewById(R.id.scaley);

	seekBarX.setMax(200);
	seekBarX.setProgress(100);

	seekBarY.setMax(200);
	seekBarY.setProgress(100);
	
//	image.setScaleType(ScaleType.MATRIX);
	matrix = new Matrix();
	
	seekBarX.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

	    @Override
	    public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onProgressChanged(SeekBar seekBar, int progress,
		    boolean fromUser) {
		// TODO Auto-generated method stub
		scaleX = progress / 100.0f;
		Log.e("", "sx=" + scaleX);
		scale();
	    }
	});

	seekBarY.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

	    @Override
	    public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onProgressChanged(SeekBar seekBar, int progress,
		    boolean fromUser) {
		// TODO Auto-generated method stub
		scaleY = progress / 100.0f;
		Log.e("", "sy=" + scaleY);
		scale();
	    }
	});
    }

    private void scale() {
	
	Drawable drawable = image.getDrawable();
	int width = drawable.getIntrinsicWidth();
	int height = drawable.getIntrinsicHeight();
	
	int w = image.getWidth();
	int h = image.getHeight();
	int tx = Math.max(0, (w - width) / 2);
	int ty = Math.max(0, (h - height) / 2);
	
	image.setScaleType(ScaleType.MATRIX);
	float root = Math.min(w * 1.0f/ width, h * 1.0f/ height);
	matrix.setScale(root * scaleX, root * scaleY);
//	matrix.setTranslate(tx / root, ty / root);
//	matrix.setRotate(180 * scaleX, image.getWidth()/2, image.getHeight() / 2);

	image.setImageMatrix(matrix);
    }
}
