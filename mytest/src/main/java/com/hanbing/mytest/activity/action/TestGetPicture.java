/**
 *
 */
package com.hanbing.mytest.activity.action;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.image.ImageLoader;
import com.common.util.FileUtils;
import com.hanbing.mytest.activity.BaseActivity;

import java.io.File;
import java.io.FileFilter;

/**
 * @author hanbing
 * @date 2015-11-26
 */
public class TestGetPicture extends BaseActivity {


    ImageView imageView;

    ViewPager viewPager;
    PagerAdapter pagerAdapter;

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
//        layout.addView(imageView);


        viewPager = new ViewPager(this);
        layout.addView(viewPager);

        setContentView(layout);

        if (null != getIntent()) {

            Uri data = getIntent().getData();

            if (null != data)
            showPictures(FileUtils.getAbsPath(this, data));

        }
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        // TODO Auto-generated method stub
        if (RESULT_OK == arg1 && 0 == arg0) {
            if (null != arg2) {
                Uri uri = arg2.getData();

                String path = FileUtils.getAbsPath(this, uri);

                showPictures(path);

            }
        }
        super.onActivityResult(arg0, arg1, arg2);
    }

    private boolean isPictureSuffix(String path)
    {
        if (null == path)
            return false;

        path = path.toLowerCase();
        if (path.endsWith(".jpg")
                || path.endsWith(".png")
                || path.endsWith(".jpeg"))
            return true;

        return false;
    }
    private void showPictures(final String path) {
//        imageView.setImageBitmap(BitmapFactory.decodeFile(path));
        if (null == path)
            return;

        File file = new File(path);

        final File[] files = file.getParentFile().listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {

                if (pathname.length() <= 1024)
                    return false;

                if (isPictureSuffix(path)) {

                    if (isPictureSuffix(pathname.getAbsolutePath())){
                        return true;
                    } else {
                        return false;
                    }

                } else {

                    return true;
                }

            }
        });

        viewPager.setAdapter(new PagerAdapter() {

            @Override
            public int getCount() {
                return null == files ? 0 : files.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View)object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                FrameLayout layout = new FrameLayout(getApplicationContext());

                ImageView image = new ImageView(getApplicationContext());
                TextView title = new TextView(getApplicationContext());


                String path =  files[position].getAbsolutePath();
                ImageLoader.getInstance(getApplicationContext())
                        .displayImage(image,path);

                title.setText("" + path);
                title.setTextSize(15);
                title.setBackgroundColor(0xf0000000);
                title.setTextColor(0xffffffff);
                title.setPadding(10,10,10,10);

                layout.addView(image);
                layout.addView(title, new ViewGroup.LayoutParams(-1, -2));

                container.addView(layout);

                return layout;

            }
        });
    }
}
