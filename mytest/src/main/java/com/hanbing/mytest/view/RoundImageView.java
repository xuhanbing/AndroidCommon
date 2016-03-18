package com.hanbing.mytest.view;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;


/**
 * ��RoundImageView.java��ʵ��������TODO ��ʵ������ 
 * @author Administrator 2014��2��21�� ����5:29:59
 */
public class RoundImageView extends ImageView {

    
    static float mRoundSize = 10.0f; //Բ�Ǵ�С��Ĭ��0px
    
    Paint mPaint;
    /**
     * @param context
     */
    public RoundImageView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init();
    }

    private void init()
    {
        mPaint = new Paint();
    }
    
    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(getCircleDrawable(getResources(), background));
    }
    
    @Override
    public void setBackgroundResource(int resid) {
        //Don't worry, we don't need to override it,because it will be call 
        //setBackgroundDrawable(Drawable background)
        super.setBackgroundResource(resid);
    }
    
    @Override
    public void setImageBitmap(Bitmap bm) {
        //Don't worry, we don't need to override it,because it will be call 
        //setImageDrawable(Drawable drawable)
        super.setImageBitmap(getCircleBitmap(bm));
    }
    
    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(getCircleDrawable(getResources(), drawable));
    }
    
    @Override
    public void setImageURI(Uri uri) {
      //cheat it, let's change the way to implement
        super.setImageURI(uri);
        Drawable img = getCircleDrawable(getResources(), getDrawable());
        super.setImageDrawable(img);
    }
    
    @Override
    public void setImageResource(int resId) {
        //cheat it, let's change the way to implement
        Drawable img = getCircleDrawable(getResources(), resId);
        super.setImageDrawable(img);
    }
    
    private static final int SPACING_LINE = 2;
    
    private static Paint mCirclePaint = null;
    private static Paint mLinePaint = null;
    private static final int STROKE_WIDTH = 4;
    
    private static Paint getCirclePaint(){
        if(mCirclePaint == null){
            mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mCirclePaint.setAntiAlias(true);
            mCirclePaint.setFilterBitmap(true);

        }
        return mCirclePaint;
    }
    
    private static Paint getLinePaint(){
        if(mLinePaint == null){
            mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mLinePaint.setStyle(Style.STROKE);
            //You can use it to change the width of the line
            mLinePaint.setStrokeWidth(1);
            //You can use it to change the color of the line
            mLinePaint.setColor(Color.BLACK);
        }
        return mLinePaint;
    }
    
    /**
     * You can call this method to generate the circular bitmap, 
     *  even if you don't use this class
     */
    public  static Bitmap getCircleBitmap(Bitmap src){
        
        if(src == null ){
            return null;
        }
        
        int width  = src.getWidth();
        int height = src.getHeight();
        
        int centerX = width / 2;
        int centerY = height / 2;
        
        int radius  = Math.min(centerX, centerY);
        int faceRadius = radius - STROKE_WIDTH; //ͷ��İ뾶
        
        Bitmap result = Bitmap.createBitmap(radius * 2, radius * 2, Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        //canvas.drawCircle(radius, radius, faceRadius, getCirclePaint());
        //getCirclePaint().setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(src, -(centerX - radius), -(centerY - radius), getCirclePaint());
        
        
        //����ɫԲȦ
        Paint mPaint = new Paint();
        mPaint.setColor(0xFF1AB2A5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(0);
        mPaint.setAntiAlias(true);
        //canvas.drawCircle(radius, radius, radius - STROKE_WIDTH / 2, mPaint);
        getCirclePaint().setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawRoundRect(new RectF(0, 0,
                                       width,
                                       height),  
                                       mRoundSize, 
                                       mRoundSize, 
                                       mPaint);
        
        
        //reset
        getCirclePaint().setXfermode(null);
        //recycle
        //src.recycle();
        return result;
    }
    
    public static Bitmap getCircleBitmap(Drawable src){
        if(src instanceof BitmapDrawable){
            return getCircleBitmap(((BitmapDrawable)src).getBitmap());
        }else{
            //now, i don't know how to do...
            throw new UnsupportedException("Unsupported");
        }
    }
    
    public static Bitmap getCircleBitmap(Resources res,int id){
        return getCircleBitmap(BitmapFactory.decodeResource(res, id));
    }
    
    public static Drawable getCircleDrawable(Resources res, Bitmap src){
        return new BitmapDrawable(res,getCircleBitmap(src));
    }
    
    public static Drawable getCircleDrawable(Resources res, Drawable src){
        return new BitmapDrawable(res,getCircleBitmap(src));
    }
    
    public static Drawable getCircleDrawable(Resources res, int id) {
        return new BitmapDrawable(res, getCircleBitmap(res, id));
    }
    
    static class UnsupportedException extends RuntimeException{
        
        private static final long serialVersionUID = 1L;

        public UnsupportedException(String str){
            super(str);
        }
    }

    
    
    
}
