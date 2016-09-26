package com.hanbing.mytest.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by hanbing on 2016/9/20
 */
public class MyTextView extends TextView {
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        String text = getText().toString();

        TextPaint paint = getPaint();


        int height = getHeight();

        float x = 0;
        float y = height / 2;

        paint.setColor(Color.BLACK);
        canvas.drawText(text, x, y, paint);


        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        drawLine(canvas, "ascent", x, y + fontMetrics.ascent, Color.BLACK);
        drawLine(canvas, "descent", x, y + fontMetrics.descent, Color.YELLOW);
        drawLine(canvas, "top", x, y + fontMetrics.top, Color.GREEN);
        drawLine(canvas, "bottom", x, y + fontMetrics.bottom, Color.RED);
    }

    private void drawLine(Canvas canvas, String text, float x, float y, int color) {
        Paint paint = new Paint();
        paint.setTextSize(30);
        paint.setColor(color);
        canvas.drawText(text, x, y, paint);
        canvas.drawLine(x, y, x + getWidth(), y, paint);
    }

}
