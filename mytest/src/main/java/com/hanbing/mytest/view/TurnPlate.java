/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��6��26�� 
 * Time : ����11:20:49
 */
package com.hanbing.mytest.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hanbing.library.android.util.LogUtils;
import com.hanbing.mytest.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * TurnPlate.java 
 * @author hanbing 
 * @date 2014��6��26�� 
 * @time ����11:20:49
 */
public class TurnPlate extends View {

    
    List<HashMap<String, Object>> _contentList = new ArrayList<HashMap<String,Object>>();
    int _count = 0;
    
    /**
     * ���ٻ���ʱ�䣬С�ڸ�ʱ�䶼��Ϊ�ǿ��ٻ���
     */
    final long FLING_DUR_TIME = 100;
    /**
     * ÿ��ת���Ƕ�
     */
    double _turnSpeed = 20;
    
    /**
     * �Ƕȱ����������ٶ�
     */
    double ANGLE_SCALE = 1;
    
    /**
     * ƫת�Ƕȣ�y������Ϊ0��
     */
    double _angle = 0.0d;
    
    /**
     * ����Ƕȣ������ݵȷ�
     */
    double _angleSpace = 0.0d;
    
    /**
     * �����߽���
     */
    int _width;
    int _height;
    
    /**
     * �ڻ��뾶
     */
    int _radiusInner; 
    
    /**
     * �⻷�뾶
     */
    int _radiusOuter; 
    
    /**
     * ���İ뾶��
     */
    int _radiusRing;
    
    /**
     * ����λ��
     */
    PointF _center;
    
    /**
     * ����
     */
    Paint _paint;
    
    
    /**
     * �Զ���ת����
     */
    final int AUTO_TURN_TIMES = 20;
    
    /**
     * �Զ���תʱ����
     */
    final int AUTO_TURN_DUR_TIME = 20;
    
    /**
     * ÿ����������ʹ��������Ч��
     */
    final int AUTO_TURN_DUR_TIME_ADD = 8;
    
    /**
     * ֻ����һ��
     */
    boolean isAutoTurnning = false;
    
   
    /**
     * @param context
     */
    public TurnPlate(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     */
    public TurnPlate(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public TurnPlate(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init();
    }


    void init(){
    }

    boolean isInit = false;
    private void initValues()
    {
        
        if (isInit)
            return;
        
        isInit = true;
        
        _width = getWidth();
        _height = getHeight();
        
        _radiusOuter = Math.min(_width, _height) / 2;
        _radiusInner = _radiusOuter / 2;
        
        _radiusRing = (_radiusInner + _radiusOuter) / 2;
        
        _center = new PointF(_width / 2,
                             _height / 2);
        
        for (int i = 0; i < 6; i++)
        {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("info", "item " + i);
            
            _contentList.add(map);
        }
        
        _angleSpace = 360.0f / _contentList.size();
        
        _paint = new Paint();
    }
    
    
    /**
     * @param angle
     * @return
     */
    private PointF getCenterPoint(double angle)
    {
        PointF point = new PointF();
        
        double redian = Math.toRadians(angle);
        point.x = (float) (_radiusRing * Math.sin(redian)) + _center.x;
        point.y = -(float) (_radiusRing * Math.cos(redian)) + _center.y;
        
        return point;
    }

    
    /* (non-Javadoc)
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */


    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        initValues();

        drawContents(canvas);

    }
    
    
    Bitmap _bm = null;
    private void drawContents(Canvas canvas)
    {

        Shader shader = new LinearGradient(0, 0, getWidth(), 20, new int[] {Color.RED, Color.GREEN, Color.BLUE}, new float[]{0, 0.5f, 1}, Shader.TileMode.CLAMP);

        _paint.setShader(shader);

        canvas.drawRect(new Rect(0, 0, getWidth(), 20), _paint);
        _paint.setShader(null);
        
//        _paint.setStyle(Style.STROKE);
        _paint.setColor(Color.LTGRAY);
        canvas.drawCircle(_center.x, _center.y, _radiusOuter, _paint);
        _paint.setColor(Color.GRAY);
        canvas.drawCircle(_center.x, _center.y, _radiusRing, _paint);
        _paint.setColor(Color.WHITE);
        canvas.drawCircle(_center.x, _center.y, _radiusInner, _paint);
        
        _paint.setStyle(Style.FILL);
        int size = (int) ((_radiusOuter - _radiusInner) / Math.sqrt(2));
        int[] colors = {0xc0ff0000, 0xc000ff00, 0xc00000ff};
        int[] imageResIds = {R.drawable.p1, R.drawable.p2, R.drawable.p3};
        
//        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.p1);
        
        if (_bm == null)
        {
            _bm = BitmapFactory.decodeResource(getResources(), R.drawable.p1);
        }
        
        for (int i = 0; i < _contentList.size(); i++)
        {
            canvas.save();

            PointF center = getCenterPoint(0);

            float angle = (float)(_angle + i * _angleSpace);
            LogUtils.e("" + i + ", _angle = " + _angle );

            canvas.rotate(angle, getWidth() / 2, getHeight() / 2);
            
            Rect r = new Rect();
            r.left = (int) (center.x - size / 2);
            r.right = (int) (center.x + size / 2);
            r.top = (int) (center.y - size / 2);
            r.bottom = (int) (center.y + size / 2);


            _paint.setColor(colors[i % colors.length]);

//            canvas.drawRect(r, _paint);


//            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_turnplate, (ViewGroup) getParent());
//            TextView text = (TextView) view.findViewById(R.id.tv_turnplate_info);
//            text.setText("item " + i);
//
//            ImageView image = (ImageView) view.findViewById(R.id.tv_turnplate_icon);
//            image.setImageBitmap(_bm);
//
//            view.draw(canvas);


//            int textSize = 50;
//            int bmSize = size - textSize;
//
//            Rect rect = new Rect();
//            rect.left = (int) (center.x - bmSize / 2);
//            rect.right = rect.left + bmSize;
//            rect.top = (int) (center.y - size / 2);
//            rect.bottom = rect.top + bmSize;
//
//            canvas.drawBitmap(_bm, null, rect, _paint);
//
//            int x = (int) center.x;
//            int y = (int) (center.y + (bmSize - size/2) + textSize /2 + textSize / 4);
//
//            _paint.setTextAlign(Align.CENTER);
//            _paint.setColor(Color.BLACK);
//            _paint.setTextSize(textSize);
//
////            LinearGradient gradient = new LinearGradient(x, y, x+200, y, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);
////            _paint.setShader(gradient);
//            String text = "item " + i;
//            canvas.drawText(text, x, y, _paint);

            canvas.restore();
        }
        
        
        
    }

    /**
     * @param isCw
     */
    private void autoTurn(final boolean isCw)
    {
        if (isAutoTurnning)
            return;
        
        isAutoTurnning = true;
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                // TODO Auto-generated method stub
                
                int count = AUTO_TURN_TIMES;
                int durTime = AUTO_TURN_DUR_TIME;
                
                while (count-- > 0)
                {
                    turn(_turnSpeed * (isCw ? 1.0 : -1.0));
                    
                    try {
                        Thread.sleep(durTime);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                    durTime += AUTO_TURN_DUR_TIME_ADD;
                }
                
                isAutoTurnning = false;
                
            }
        }).start();
    }
    
    /**
     * @param angle
     */
    private void turn(double angle)
    {
        angle *= ANGLE_SCALE;
        
        _angle += angle;

        _angle %= 360;
        
        this.postInvalidate();
    }
    
    
    
    PointF _lastPoint;
    PointF _curPoint;
    PointF _downPoint;
    long _downTime;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return onTouchEvent(ev);
    }

    /* (non-Javadoc)
         * @see android.view.View#onTouchEvent(android.view.MotionEvent)
         */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                _lastPoint = new PointF(event.getX(), event.getY());
                _downPoint = new PointF(event.getX(), event.getY());
                _downTime = System.currentTimeMillis(); 
                break;
            case MotionEvent.ACTION_MOVE:
                _curPoint = new PointF(event.getX(), event.getY());
                move();
                break;
            case MotionEvent.ACTION_UP:
                long durTime = System.currentTimeMillis() - _downTime;
                _curPoint = new PointF(event.getX(), event.getY());
                if (durTime < FLING_DUR_TIME)
                {
                    boolean isCw = isCwDiretion(_downPoint, _curPoint);
                    autoTurn(isCw);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        
        return true;
        
    }

    /**
     */
    private void move() {
        // TODO Auto-generated method stub
        
        double angle = calcAngle();
        
        _lastPoint = new PointF(_curPoint.x, _curPoint.y);
        
        turn(angle);
    }
    
    /**
     * @return
     */
    private double calcAngle()
    {
        double angle = 0.0f;
        
        double sideA = calcDistance(_lastPoint, _center);
        double sideB = calcDistance(_curPoint, _center);
        double sideC = calcDistance(_lastPoint, _curPoint);
        
        
        
        angle =  Math.acos((sideA * sideA + sideB * sideB - sideC * sideC)
                          / (2 * sideA * sideB));
        
        angle = Math.toDegrees(angle);
        
        angle *= isCwDiretion(_lastPoint, _curPoint) ? 1.0 : -1.0;
        
        return angle;
    }
    
    /**
     * @return
     */
    private boolean isCwDiretion(PointF lastPoint, PointF curPoint)
    {
        boolean isCw = true;
        
        PointF last = transform2Zero(lastPoint);
        PointF cur = transform2Zero(curPoint);
        
        double angleLast = Math.atan(last.y / last.x);
        double angleCur = Math.atan(cur.y / cur.x);
        
        
        isCw = angleLast >= angleCur;
        
        //����һ���ٽ����
        if (last.x <= 0)
        {
            
        }
        else
        {
            last.y *= -1.0;
            cur.y *= -1.0;
        }
        
        if (last.y < 0 && cur.y > 0)
        {
            isCw = true;
        }
        else if (last.y > 0 && cur.y < 0)
        {
            isCw = false;
        }
        

        
        return isCw;
    }
    
    /**
     * @param p0
     * @param p1
     * @return
     */
    private double calcDistance(PointF p0, PointF p1)
    {
        double disX = p0.x - p1.x;
        double disY = p0.y - p1.y;
        
        return Math.sqrt(disX * disX + disY * disY);
    }
    
    
    /**
     * @param point
     * @return
     */
    private PointF transform2Zero(PointF point)
    {
        PointF p = new PointF(point.x - _center.x,
                              -(point.y - _center.y));
        
        return p;
    }
    

}
