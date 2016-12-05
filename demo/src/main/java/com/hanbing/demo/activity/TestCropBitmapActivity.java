package com.hanbing.demo.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hanbing.demo.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestCropBitmapActivity extends AppCompatActivity {

    @BindView(R.id.scaleX_tv)
    TextView mScaleXTv;
    @BindView(R.id.scaleX_sb)
    SeekBar mScaleXSb;
    @BindView(R.id.scaleY_tv)
    TextView mScaleYTv;
    @BindView(R.id.scaleY_sb)
    SeekBar mScaleYSb;
    @BindView(R.id.translateX_tv)
    TextView mTranslateXTv;
    @BindView(R.id.translateX_sb)
    SeekBar mTranslateXSb;
    @BindView(R.id.translateY_tv)
    TextView mTranslateYTv;
    @BindView(R.id.translateY_sb)
    SeekBar mTranslateYSb;
    @BindView(R.id.image)
    ImageView mImage;


    SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            switch (seekBar.getId()) {
                case R.id.scaleX_sb:
                    mScaleXTv.setText("scaleX = " + progress * 1.0f / seekBar.getMax());
                    break;
                case R.id.scaleY_sb:
                    mScaleYTv.setText("scaleY = " + progress * 1.0f / seekBar.getMax());
                    break;
                case R.id.translateX_sb:
                    mTranslateXTv.setText("translateX = " + progress);
                    break;
                case R.id.translateY_sb:
                    mTranslateYTv.setText("translateY = " + progress);
                    break;
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    String url = "http://image60.360doc.com/DownloadImg/2013/04/1613/31674132_11.jpg";

    Bitmap mSrcBitmap = null;
    @BindView(R.id.image_src)
    ImageView mImageSrc;
    @BindView(R.id.activity_test_crop_bitmap)
    LinearLayout mActivityTestCropBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_crop_bitmap);
        ButterKnife.bind(this);

        mImageSrc.post(new Runnable() {
            @Override
            public void run() {
                ImageLoader.getInstance().displayImage(url, mImageSrc, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        mSrcBitmap = loadedImage;
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
            }
        });


        mScaleXSb.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mScaleYSb.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mTranslateXSb.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        mTranslateYSb.setOnSeekBarChangeListener(mOnSeekBarChangeListener);

    }


    public void crop(View view) {

        if (null == mSrcBitmap)
            return;

        float scaleX = mScaleXSb.getProgress() * 1.0f / mScaleXSb.getMax();
        float scaleY = mScaleYSb.getProgress() * 1.0f / mScaleYSb.getMax();
        int translateX = mTranslateXSb.getProgress();
        int translateY = mTranslateYSb.getProgress();


        Matrix m = new Matrix();

        m.postScale(scaleX, scaleY);
        m.postTranslate(translateX, translateY);


//        Bitmap bitmap = Bitmap.createBitmap(mSrcBitmap, 0, 0, 400, 400, m, false);

        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(bitmap);

        canvas.drawBitmap(mSrcBitmap, m, null);

        mImage.setImageBitmap(bitmap);

    }


}
