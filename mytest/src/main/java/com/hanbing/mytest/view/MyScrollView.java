package com.hanbing.mytest.view;
import com.hanbing.mytest.R;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;


/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��3��19�� 
 * Time : ����1:37:07
 */
/**
 * MyScrollView.java 
 * @author hanbing 
 * @date 2014��3��19�� 
 * @time ����1:37:07
 */
public class MyScrollView extends LinearLayout {


    private static int STATUS_DEFAULT = 0;
    private static int STATUS_PULL_TO_REFLESH = 1;
    private static int STATUS_RELEASE_TO_REFLESH = 2;
    private static int STATUS_REFLESHING = 3;
    private static int ORGIN_MARGIN_TOP = -120;
    
    View headView;
    ImageView headArrow;
    TextView headText;
    ProgressBar headProgress;
    int status = STATUS_DEFAULT;
    Scroller scroller;
    
    String TAG = "MyScrollView";
    
    Animation flipAnim;
    Animation upAnim;
    
    Context mContext;
    /**
     * @param context
     */
    public MyScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init(context);
    }
    
    private void init(Context context)
    {
        this.setOrientation(VERTICAL);
        mContext = context;
        
        headView = LayoutInflater.from(context)
                .inflate(R.layout.layout_pull_to_reflesh_header, null, true);
        
        headText = (TextView) headView.findViewById(R.id.tv_reflesh_text_pull);
        headText.setText(R.string.pull_to_refresh_pull_label);
        headProgress = (ProgressBar) headView.findViewById(R.id.pb_reflesh_progress);
        headArrow = (ImageView) headView.findViewById(R.id.iv_reflesh_arrow);
        headArrow.setImageResource(R.drawable.goicon);
        headProgress.setVisibility(View.GONE);
        
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, -ORGIN_MARGIN_TOP);
        lp.topMargin = ORGIN_MARGIN_TOP;
        lp.gravity = Gravity.CENTER;
        addView(headView, lp);
        
        scroller = new Scroller(context);
        
        
        flipAnim = new RotateAnimation(180, 0, 
                                       RotateAnimation.RELATIVE_TO_SELF, 0.5f, 
                                       RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        flipAnim.setDuration(500);
        flipAnim.setFillAfter(true);
        
        upAnim = new RotateAnimation(0, 180,
                                     RotateAnimation.RELATIVE_TO_SELF, 0.5f, 
                                     RotateAnimation.RELATIVE_TO_SELF, 0.5f );
        upAnim.setDuration(500);
        upAnim.setFillAfter(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (this.getChildCount() > 0)
        {
            doTouch(ev);
        }
        return true;
    }

    
    float lastY = 0.0f;
    
    /**
     * @param ev
     */
    private void doTouch(MotionEvent ev) {
        // TODO Auto-generated method stub
        
            switch (ev.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                {
                    begin(ev);
                    
                }
                    break;
                case MotionEvent.ACTION_MOVE:
                {
                    
                    move(ev);
                }
                    break;
                case MotionEvent.ACTION_UP:
                {
                    flip();
                }
                    break;
            }
        
    }
    
    
    /**
     * ��ʼ�ƶ�����¼��ʼλ��
     * @param ev
     */
    private void begin(MotionEvent ev) {
        // TODO Auto-generated method stub
        lastY = ev.getY();
    }
    
    
    int downY;
    
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
//        return super.onInterceptTouchEvent(ev);
        
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                lastY = downY;
                break;
            case MotionEvent.ACTION_MOVE:
            {
                int dif = (int) (ev.getY() - downY);
                if (canScroll(dif))
                {
                    return true;
                }
            }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * @param ev
     * @return
     */
    private boolean canScroll(int dif) {
        // TODO Auto-generated method stub
        
        if (getChildCount() > 1)
        {
            View childView = getChildAt(1);
            
            if (childView instanceof ScrollView)
            {
                if (childView.getScrollY() == 0 && dif >= 0)
                {
                    return true;
                }
                
            }
            else if (childView instanceof ListView) {
                int top = ((ListView) childView).getChildAt(0).getTop();
                int pad = ((ListView) childView).getListPaddingTop();
                if ((Math.abs(top - pad)) < 3
                        && ((ListView) childView).getFirstVisiblePosition() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * @param ev
     */
    private void move(MotionEvent ev) {
        // TODO Auto-generated method stub
        
        if (getChildCount() < 2)
            return;
        
        View child = getChildAt(1);
        LayoutParams lp = (LayoutParams) headView.getLayoutParams();
        
        int dy = (int) ((ev.getY() - lastY ) * 0.8f);
        
        if (status != STATUS_REFLESHING && child.getScrollY() == 0)
        {
            lp.topMargin = lp.topMargin+dy;
            
            int i = lp.topMargin;
            
            System.out.println("lp.topMargin=" +lp.topMargin);
            headView.setLayoutParams(lp);
            headView.invalidate();
            invalidate();
            
            headText.setVisibility(View.VISIBLE);
            headArrow.setVisibility(View.VISIBLE);
            headProgress.setVisibility(View.GONE);
            if (i > ORGIN_MARGIN_TOP && i < 0)
            {
                System.out.println("STATUS_PULL_TO_REFLESH");
                headText.setText(R.string.pull_to_refresh_pull_label);
                
                if (status == STATUS_RELEASE_TO_REFLESH)
                {
                    headArrow.clearAnimation();
                    headArrow.startAnimation(flipAnim);
                }
                
                status = STATUS_PULL_TO_REFLESH;
                
            }
            else if (i >= 0)
            {
                System.out.println("STATUS_RELEASE_TO_REFLESH");
                if (status == STATUS_PULL_TO_REFLESH)
                {
                    headArrow.clearAnimation();
                    headArrow.startAnimation(upAnim);
                }
                
                headText.setText(R.string.pull_to_refresh_release_label);
                
                status = STATUS_RELEASE_TO_REFLESH;
                
                
            }
        }
        

        lastY = ev.getY();
        
    }
    
    public Handler handler;

    private void reflesh()
    {
//        new Thread(new Runnable() {
//            
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                
//                handler.post(new Runnable() {
//                    
//                    @Override
//                    public void run() {
//                        // TODO Auto-generated method stub
//                        goOrig();
//                    }
//                });
//                
//            }
//        }).start();
        
        if (null != refreshListener)
        {
            refreshListener.onReflesh();
        }
        else
        {
            goOrig();
        }
        
//        handler.post(new Runnable() {
//          
//          @Override
//          public void run() {
//              // TODO Auto-generated method stub
//              goOrig();
//          }
//        });
    }
    
    
    private void flip()
    {
        LayoutParams lp = (LayoutParams) headView.getLayoutParams();
        
        if (lp.topMargin > 0 && status == STATUS_RELEASE_TO_REFLESH)
        {
            
            headProgress.setVisibility(View.VISIBLE);
            headText.setText(R.string.pull_to_refresh_refreshing_label);
            headArrow.setAnimation(null);
            headArrow.setVisibility(View.GONE);
            
            lp.topMargin = 0;
            
            headView.setLayoutParams(lp);
            headView.invalidate();
            invalidate();
            
            reflesh();
            
        }
        else
        {
            goOrig();
        }
        
        
    }
    
    /**
     * ���س�ʼλ��
     */
    public void goOrig()
    {
        status = STATUS_DEFAULT;
        LayoutParams lp = (LayoutParams) headView.getLayoutParams();
        
        lp.topMargin = ORGIN_MARGIN_TOP;
        headView.setLayoutParams(lp);
        
        headText.setVisibility(View.VISIBLE);
        headArrow.setVisibility(View.VISIBLE);
        headProgress.setVisibility(View.GONE);
        headView.invalidate();
        invalidate();
    }

    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        if (scroller.computeScrollOffset()) {
            int i = this.scroller.getCurrY();
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.headView
                    .getLayoutParams();
            int k = Math.max(i, ORGIN_MARGIN_TOP);
//            System.out.println("===lp.topMargin="+lp.topMargin + ",scroller.getCurrY()=" +i);
            lp.topMargin = k;
            
            this.headView.setLayoutParams(lp);
            this.headView.invalidate();
            invalidate();
        }
    }
    
    
    RefreshListener refreshListener;
    
    public RefreshListener getRefreshListener() {
        return refreshListener;
    }

    
    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }
    public interface RefreshListener
    {
        public abstract void onReflesh();
    }
    
}
