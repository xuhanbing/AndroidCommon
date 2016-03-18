/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014�?�?4�?
 * Time : 上午11:40:10
 */
package com.hanbing.mytest.listener;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * MulitPointTouchListener.java 
 * @author hanbing 
 * @date 2014�?�?4�?
 * @time 上午11:40:10
 */
public class MulitPointTouchListener implements OnTouchListener {

    public static final String TAG = "MulitPointTouchListener";
    
    //动作
    final static int TYPE_NONE = 0;
    final static int TYPE_DRAG = 1;
    final static int TYPE_SCALE = 2;
    final static int TYPE_DB_CLICK = 3;
    final static int TYPE_LONG_CLICK = 4;
    
    //缩放�?��间距
    final static float MIN_DIST = 20.0f;
    
    //点击误差范围
    final static float MISS_DIST = 10.0f;
    
    //长按时间
    final static long LONG_CLICK_DURATION = 500;
    
    //双击放大的�?�?
    final static float DOUBLE_CLICK_SCALE = 1.5f;
    
    //放大持续时间ms
    final static int DOUBLE_CLICK_SCALE_DURATION = 150;
    
    //双击间隔
    final static long DB_CLICK_DURATION = 300;
    
    //点击等待时间
    final static long CLICK_DURATION = 500;
    
    //点击时间，超过时间则不是点击
    final static long CLICK_DURATION_MAX = 120;
    final static long CLICK_DURATION_MIN = 30;
    final static long MAX_SCALE = 3;
    
    public MulitPointTouchListener(Context context)
    {
        this.context = context;
    }
    
    public MulitPointTouchListener(Context context, OnLongClickListener onLongClickLsner, OnClickListener onClickLsner)
    {
        this.context = context;
        this.onLongClickLsner = onLongClickLsner;
        this.onClickLsner = onClickLsner;
    }
    
    
    Context context;
    
    //计时�?
    Timer timer = null;
    ClickTimerTask task = null;
    
    class ClickTimerTask extends TimerTask
    {

        ImageView image;
        OnClickListener onClickLsner;
        public ClickTimerTask(ImageView image, OnClickListener onClickLsner)
        {
            this.image = image;
            this.onClickLsner = onClickLsner;
        }
        
        /* (non-Javadoc)
         * @see java.util.TimerTask#run()
         */
        @Override
        public void run() {
            // TODO Auto-generated method stub
            
            //如果没有up，说明还有操�?
            if (onClickLsner != null && image != null && TYPE_NONE == type)
            {
//                ((Activity) context).runOnUiThread(new Runnable() {
//
//                    public void run() {
//                        onClickLsner.onClick(image);
//                    }
//                });
                onClickLsner.onClick(image);
            }
            
        }
        
    }
    
    
    //长按的操�?
    public OnLongClickListener onLongClickLsner;
    
    //普�?点击
    public OnClickListener onClickLsner;
    
    public OnLongClickListener getOnLongClickLsner() {
        return onLongClickLsner;
    }

    
    public void setOnLongClickLsner(OnLongClickListener onLongClickLsner) {
        this.onLongClickLsner = onLongClickLsner;
    }

    
    public OnClickListener getOnClickLsner() {
        return onClickLsner;
    }

    
    public void setOnClickLsner(OnClickListener onClickLsner) {
        this.onClickLsner = onClickLsner;
    }


    
    
    
    //当前大小
    Matrix matrix = new Matrix();
    
    //操作�?��时的大小
    Matrix savedMatrix = new Matrix();
    
    //原始大小
    Matrix orginMatrix = new Matrix();
    
    //双击放大前的大小
    Matrix dbClickMatrix = new Matrix(); 
    
    //判断是否可以形成长按
    boolean canBeLongClick = false;
    
    //上次缩放的大�?
    float lastScale = 1.0f;
    
    int type = TYPE_NONE;
    
    PointF start = new PointF();
    PointF current = new PointF();
    PointF center = new PointF();
    
    PointF dbScaleCenter = new PointF();
    
    //双击放大�?��时的坐标，还原时以此坐标为轴坐标
    PointF dbClickPoint = new PointF(); 
    
    //双击次数，双数放大，单数还原
    int dbClickCount = 0; 
    
    
    //上次点击的时间，down的时�?
    long lastClickTime = 0;
    
    float oldDist = 0.0f;
    float newDist = 0.0f;
    
    boolean isInit =false;
    
    //图片的宽�?
    int picWidth = 0;
    int picHeight = 0;
    
    //界面的宽�?
    int viewWidth = 0;
    int viewHeight = 0;
    
    //初始化设置，只调用一�?
    private void init (ImageView image)
    {
        if (!isInit)
        {
            Drawable d = image.getDrawable();
            picWidth = d.getIntrinsicWidth();
            picHeight = d.getIntrinsicHeight();
            
            viewWidth = image.getWidth();
            viewHeight = image.getHeight();
            
            orginMatrix.set(image.getImageMatrix());
            matrix.set(orginMatrix);
            isInit = true;
            
        }
        
    }
    
    /* (non-Javadoc)
     * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        
        ImageView image = (ImageView) v;
        
        //没有图片
        if (null == image.getDrawable())
            return false;
        
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                
                image.setScaleType(ScaleType.MATRIX);
                
                init(image);
                
                if (isDbClick())
                {
                    cancelTimer();
                    type = TYPE_DB_CLICK;
                    dbScaleCenter = getScaleCenter(start);
                    scaleImageAnim(image, dbScaleCenter);
                    
                    return true;
                }
                else
                {
                    type = TYPE_DRAG;
                    
                    //重新计时
                    updateTimer(image);
                }
                
//                matrix.set(image.getImageMatrix());   
                
                savedMatrix.set(matrix);
                
                start.set(event.getX(), event.getY());
                current.set(start);
                
                canBeLongClick = true;
                
                
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                
                oldDist = calcDist(event);
                
                if (oldDist > MIN_DIST)
                {
                    cancelTimer();
                    savedMatrix.set(matrix); 
                    midPoint(center, event);
                    center = getScaleCenter(center);
                    type = TYPE_SCALE;
                    dbClickCount = 0;
                    image.clearAnimation();
                    lastScale = 1.0f;
                }
                
                break;
            case MotionEvent.ACTION_MOVE:
            {
                
                if (type == TYPE_SCALE)
                {
                    
                    newDist = calcDist(event);
                    
                    if (newDist > MIN_DIST)
                    {
                        float scale = newDist / oldDist;
//                        if (scale>MAX_SCALE)
//                        {
//							matrix.set(savedMatrix);
//							matrix.postScale(MAX_SCALE, MAX_SCALE, center.x, center.y);
//						}
//                        else 
						{
						    
                        	 matrix.set(savedMatrix);
                             
                             matrix.postScale(scale, scale, center.x, center.y);   
						}
                       
                    }
                    
                }
                else if (type == TYPE_DRAG)
                {
                    
                    //如果第一次出现距离超过最大误差，说明产生拖动，停止计时器
                    if (canBeLongClick && calcDist(start, event) > MISS_DIST)
                    {
                        cancelTimer();
                        canBeLongClick = false;
                    }
                    
                    //长按
                    if (canBeLongClick && checkLongClick(image, event))
                    {
                        if (onLongClickLsner != null)
                        {
                            onLongClickLsner.onLongClick(image);
                        }
                        
                        type = TYPE_LONG_CLICK;
                        
                        return true;
                        
                    }
                    else
                    {
                        //拖动
                        float dx = event.getX() - current.x;
                        float dy = event.getY() - current.y;
                        
                        current.set(event.getX(), event.getY());
                        
                        matrix.set(getMatrix(dx, dy, false));
                    }
                    
                    
                }
                
                
            }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                
                if (type == TYPE_SCALE)
                {
                    
                    float[] values0 = new float[9];
                    float[] values1 = new float[9];
                    
                    orginMatrix.getValues(values0);
                    matrix.getValues(values1);
                    
                    
                    //比初始小，还�?
                    if (values0[0] > values1[0])
                    {
                        matrix.set(orginMatrix);
                    }
                    else if (values1[0] / values0[0] > MAX_SCALE)
                    {
                        matrix.set(orginMatrix);
                        
                        matrix.postScale(MAX_SCALE, MAX_SCALE, center.x, center.y);
                    }
                    else
                    {
                        //�?��边界，保持各个顶点在边界�?
                        matrix.set(getMatrix(0, 0, true));
                    }
                    
                    
                    PointF c = getScaleFinishCenter();
                    
                    
                }
                else if (type == TYPE_DRAG || type == TYPE_NONE)
                {
                    
                }
                else if (type == TYPE_DB_CLICK)
                {
                    
                }
                
                type = TYPE_NONE;
                break;
            case MotionEvent.ACTION_CANCEL:
                //快�?滑动
                cancelTimer();
                type = TYPE_NONE;
                break;
        }
        image.setImageMatrix(matrix);
        return true;
    }   
    
    
    /**
     * 计算缩放中心，如果宽或高小于view的高，则使用view的中心，否则以点击位置为中心
     * @return
     */
    private PointF getScaleCenter(PointF center) {
        // TODO Auto-generated method stub
        float[] values = new float[9];
        
        matrix.getValues(values);
        
        float scaleX = values[0];
        float scaleY = values[4];
        float x = values[2];
        float y = values[5];
        
        //当前图像的宽�?
        float w = scaleX * picWidth;
        float h = scaleY * picHeight;
        
        float centerX = center.x;
        float centerY = center.y;
        
        //如果图片宽高比view小，缩放以view为中�?
        if (w <= viewWidth)
        {
            centerX = viewWidth / 2;
        }
        
        if (h <= viewHeight)
        {
            centerY = viewHeight/2;
        }
        return new PointF(centerX, centerY);
    }
    
    private PointF getScaleFinishCenter() {
        // TODO Auto-generated method stub
        float[] values = new float[9];
        
        matrix.getValues(values);
        
        float scaleX = values[0];
        float scaleY = values[4];
        float x = values[2];
        float y = values[5];
        
        //当前图像的宽�?
        float w = scaleX * picWidth;
        float h = scaleY * picHeight;
        
        float centerX = x + w / 2;
        float centerY = y + h / 2;
        
        //如果图片宽高比view小，缩放以view为中�?
        if (w <= viewWidth)
        {
            centerX = viewWidth / 2;
        }
        
        if (h <= viewHeight)
        {
            centerY = viewHeight/2;
        }
        return new PointF(centerX, centerY);
    }

    /**
     * 更新计时�?
     * @param image
     */
    private void updateTimer(ImageView image)
    {
        cancelTimer();

        task = new ClickTimerTask(image, onClickLsner);
        
        if (null == timer)
            timer = new Timer(TAG);
        timer.schedule(task, CLICK_DURATION);
    }
    
    /**
     * 取消定时�?
     */
    private void cancelTimer()
    {
        if (null != task)
        {
            task.cancel();
            task = null;
        }
    }
    
    /**
     * 单击
     */
    private boolean checkSingleClick(ImageView image , MotionEvent event) {
        // TODO Auto-generated method stub
        return checkClick(image, event, CLICK_DURATION_MIN);
    }
    
    /**
     * 长按
     */
    private boolean checkLongClick(ImageView image , MotionEvent event) {
        // TODO Auto-generated method stub
        return checkClick(image, event, DB_CLICK_DURATION);
    }
    
    /**
     * �?��点击
     * @param image
     * @param event
     * @param duration
     * @return
     */
    private boolean checkClick(ImageView image, MotionEvent event, long duration)
    {
        long time = System.currentTimeMillis() - lastClickTime;
        
        float dist = calcDist(start, event);
        
        if (dist < MISS_DIST && time > duration)
        {
            return true;
        }
        
        return false;
    }

    /**
     * 控制边界
     * @param dx
     * @param dy
     * @param isScale  如果是缩放结束时，图像宽高比view的小，控制图像中心为view的中�?
     * @return
     */
    public Matrix getMatrix(float dx, float dy, boolean isScale)
    {
        Matrix m = new Matrix();
        
        float[] values = new float[9];
        
        matrix.getValues(values);
        
        float scaleX = values[0];
        float scaleY = values[4];
        
        //当前顶点坐标
        float x = values[2];
        float y = values[5];
        
        //当前图像的宽�?
        float w = scaleX * picWidth;
        float h = scaleY * picHeight;
        
        //偏移
        x += dx;
        y += dy;
        
        if (w >= viewWidth)
        {
            x = Math.min(0, Math.max(viewWidth - w, x));
        }
        else
        {
            x = isScale ? (viewWidth / 2 - w / 2) : values[2];
        }
        
        if (h >= viewHeight)
        {
            y = Math.min(0, Math.max(viewHeight - h, y));
        }
        else
        {
            y = isScale ? (viewHeight / 2 - h / 2) : values[5];
        }
        
        values[2] = x;
        values[5] = y;
        
        m.setValues(values);
        
        return m;
    }
    
    /**
     * 是否是双�?
     * 小于�?��间隔
     */
    public boolean isDbClick() {
        // TODO Auto-generated method stub
        long time = System.currentTimeMillis();
        
        boolean isDouble = false;
        if (lastClickTime != 0 && time - lastClickTime < DB_CLICK_DURATION)
        {
            isDouble = true;
            lastClickTime = 0;
        }
        
        lastClickTime = time;
        
        return isDouble;
    }
    
    /**
     * 双击时放大或缩小图片
     * @param image
     * @param center
     */
    public void scaleImage(ImageView image, PointF center, boolean useAnim)
    {
        
        float scale = DOUBLE_CLICK_SCALE;
        
        if (dbClickCount % 2 == 0)
        {
            dbClickPoint.set(center);
            dbClickMatrix.set(matrix);
            matrix.postScale(scale, scale, center.x, center.y);
        }
        else
        {
            matrix.set(dbClickMatrix);
            scale = 1/scale;
        }
        dbClickCount ++;
        
        
        if (useAnim)
        {
            float from = lastScale;
            
            lastScale *= scale;
            
            float to = lastScale;
            
            ScaleAnimation anim = new ScaleAnimation(from, to, from, to, dbClickPoint.x, dbClickPoint.y);
            anim.setDuration(DOUBLE_CLICK_SCALE_DURATION);
//            anim.setInterpolator(new AccelerateInterpolator(0.5f));
            anim.setFillAfter(true);
            
            image.startAnimation(anim);
        }
        else
        {
            image.setImageMatrix(matrix);
        }
        
        
    }
    
    
    /**
     * 双击时放大或缩小图片
     * 动画方式放大缩小
     * @param image
     * @param center
     */
    public void scaleImageAnim(ImageView image, PointF center)
    {
        scaleImage(image, center, true);
    }
    
    /**
     * 计算多点触摸是间�?
     * @param event
     * @return
     */
    private float calcDist(MotionEvent event) {   
        PointF p0 = new PointF(event.getX(0), event.getY(0));
        PointF p1 = new PointF(event.getX(1), event.getY(1));
        
        return calcDist(p0, p1);
    }   
    
    /**
     * 计算单点与起始位置间�?
     * @param p0
     * @param event
     * @return
     */
    private float calcDist(PointF p0, MotionEvent event)
    {
        PointF p1 = new PointF(event.getX(), event.getY());
        return calcDist(p0, p1);
    }
    
    /**
     * 计算两点间距
     * @param p0
     * @param p1
     * @return
     */
    private float calcDist(PointF p0, PointF p1)
    {
        float x = p0.x - p1.x;   
        float y = p0.y - p1.y;  
        
        return (float) Math.sqrt(x * x + y * y);   
    }
    
    
    /**
     * 计算缩放中点坐标
     * @param point
     * @param event
     */
    private void midPoint(PointF point, MotionEvent event) {   
        float x = event.getX(0) + event.getX(1);   
        float y = event.getY(0) + event.getY(1);   
        point.set(x / 2, y / 2);   
    }   
    
    /**
     * 释放timer
     */
    public void release()
    {
        if (timer != null)
        {
            timer.cancel();
            timer = null;
        }
    }
    
}

