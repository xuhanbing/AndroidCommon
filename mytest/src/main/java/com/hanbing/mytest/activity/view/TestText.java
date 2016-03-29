package com.hanbing.mytest.activity.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hanbing.mytest.R;

import java.util.ArrayList;
import java.util.List;

public class TestText extends AppCompatActivity {


    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_text);



    }


   public static class MyView extends LinearLayout {
        public MyView(Context context) {
            super(context);
        }

        public MyView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

       Handler handler = new Handler();


       int x = 0;

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Paint paint = new Paint();
            paint.setTextSize(25);
            paint.setStrokeWidth(20);
            paint.setColor(Color.RED);

            canvas.drawRect(0, 0, 200, 200, paint);

            paint.setColor(Color.GREEN);


            String text = "Hallow";

            Rect rect = new Rect();
            paint.getTextBounds(text, 0, text.length(), rect);

            int width = rect.width();

            x += 5;

            int delay = 10;
            int offset = width;
            if (x >= width + offset)
            {
                x = 0;
                paint.setShader(null);
                paint.setColor(Color.BLACK);
                delay = 200;
            } else {


            }
            float start = 0;
            start = x * 1.0f / (width + offset);


            int [] colors = {Color.BLACK, Color.WHITE, Color.BLACK};
            float[] positions = {0, start, 1};

            LinearGradient gradient = new LinearGradient(-offset, 0, width+ offset, 0, colors, positions, Shader.TileMode.CLAMP);

            paint.setShader(gradient);




            canvas.drawText(text, 0,  width, paint);

//
//            canvas.drawLine(0, 0, 40, 40, paint);
//
//            canvas.drawText("Hallow", 0, 0, paint);
//
//
//
//            Rect rect = new Rect();
//            paint.getTextBounds(text, 0, text.length(), rect);
//            canvas.drawText(text, 0, rect.width(), paint);

            postInvalidateDelayed(delay);
        }
    }
}
