package com.hanbing.mytest.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import com.hanbing.library.android.util.DipUtils;


/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��3��19�� 
 * Time : ����1:37:07
 */
/**
 * ����ˢ��listview
 * @author hanbing 
 * @date 2014��3��19�� 
 * @time ����1:37:07
 */
public class PullToRefreshListView extends LockPositionListView {


    private static int STATUS_DEFAULT = 0;
    private static int STATUS_PULL_TO_REFLESH = 1;
    private static int STATUS_RELEASE_TO_REFLESH = 2;
    private static int STATUS_REFLESHING = 3;
    private static int ORGIN_MARGIN_TOP = -200;
    
    View headerView;
    View footerView;
    ImageView headerArrow;
    TextView headerText;
    ProgressBar headerProgress;
    int status = STATUS_DEFAULT;
    Scroller scroller;
    
    String TAG = "MyScrollView";
    
    Animation flipAnim;
    Animation upAnim;
    
    Context mContext;
    /**
     * @param context
     */
    public PullToRefreshListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init(context);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public PullToRefreshListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init(context);
    }
    
    private void init(Context context)
    {
//        this.setOrientation(VERTICAL);
        mContext = context;
        
        ORGIN_MARGIN_TOP = -1 * DipUtils.dip2px(context, 200);
        
        headerView = LayoutInflater.from(context)
                .inflate(R.layout.layout_pull_to_reflesh_header, null, true);
        
        headerText = (TextView) headerView.findViewById(R.id.tv_reflesh_text_pull);
        headerText.setText(R.string.pull_to_refresh_pull_label);
        headerProgress = (ProgressBar) headerView.findViewById(R.id.pb_reflesh_progress);
        headerArrow = (ImageView) headerView.findViewById(R.id.iv_reflesh_arrow);
        headerArrow.setImageResource(R.drawable.ic_pulltorefresh_arrow);
        headerProgress.setVisibility(View.GONE);
        
//        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, -ORGIN_MARGIN_TOP);
//        headerView.setLayoutParams(lp);
        
        addHeaderView(headerView, null, false);
        
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

    boolean isFirst = true;
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        
        int height = headerView.getMeasuredHeight();
        
        if (isFirst && height > 0)
        {
            isFirst = false;
            ORGIN_MARGIN_TOP = - height;
            headerView.setPadding(0, ORGIN_MARGIN_TOP, 0, 0);
            headerView.requestLayout();
        }
    }
    
    boolean canScollUp = true;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        doTouch(ev);
        
//        return !isLoading  && super.onTouchEvent(ev);
        return super.onTouchEvent(ev);
        
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
                    release();
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
    
    
    /**
     * @param ev
     */
    private void move(MotionEvent ev) {
        // TODO Auto-generated method stub
        
        
        int scrollY = getScrollY();
        
        Log.e("","scrollY="+scrollY);
        
        int index = getFirstVisiblePosition();
        //��һ����header���ϻ�
        canScollUp = !(index == 0);

        int dy = (int) ((ev.getY() - lastY ) * 0.9f);
        Log.e("","top=" + headerView.getTop());
            if (status != STATUS_REFLESHING)
            {
                int paddingTop = headerView.getPaddingTop();
                paddingTop+= dy;
                
                paddingTop = Math.max(paddingTop, ORGIN_MARGIN_TOP);
                headerView.setPadding(0, paddingTop, 0, 0);
                headerView.invalidate();
                invalidate();
                Log.e("","top=" + headerView.getTop() + ",paddingtop = " +paddingTop +",dy=" + dy);
                
                headerText.setVisibility(View.VISIBLE);
                headerArrow.setVisibility(View.VISIBLE);
                headerProgress.setVisibility(View.GONE);
                if ((paddingTop > ORGIN_MARGIN_TOP && paddingTop < 0 /*&& headerView.getTop() >= 0*/) 
                        || (/*headerView.getTop() < 0 && */headerView.getTop() + paddingTop <= 0))
                {
                    Log.e("","STATUS_PULL_TO_REFLESH");
                    headerText.setText(R.string.pull_to_refresh_pull_label);
                    
                    if (status == STATUS_RELEASE_TO_REFLESH)
                    {
                        headerArrow.clearAnimation();
                        headerArrow.startAnimation(flipAnim);
                    }
                    
                    status = STATUS_PULL_TO_REFLESH;
                    
                }
                else if (paddingTop >= 20)
                {
                    Log.e("","STATUS_RELEASE_TO_REFLESH");
                    
                    if (status == STATUS_DEFAULT)
                    {
                	status = STATUS_PULL_TO_REFLESH;
                    }
                    
                    if (status == STATUS_PULL_TO_REFLESH)
                    {
                        headerArrow.clearAnimation();
                        headerArrow.startAnimation(upAnim);
                    }
                    
                    headerText.setText(R.string.pull_to_refresh_release_label);
                    
                    status = STATUS_RELEASE_TO_REFLESH;
                    
                    
                }

            }
        

        lastY = ev.getY();
        
    }
    
    
    /**
     * ˢ������
     */
    private void reflesh()
    {
        Log.e("","STATUS_REFLESHING");
        status = STATUS_REFLESHING;
        isLoading = true;
        
        if (null != refreshListener)
        {
            
            new Thread(new Runnable() {
                
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    
                    refreshListener.onReflesh();
                    
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            goOrig();
                        }
                    });
                    
                }
            }).start();
            
        }
        else
        {
            goOrig();
        }
        
    }
    
    /**
     * �ͷ�
     */
    boolean isLoading = false;
    private void release()
    {
        
        int top = headerView.getPaddingTop();
        canScollUp = true;
        if (top + headerView.getTop() >= 0 
                && status == STATUS_RELEASE_TO_REFLESH
                && getFirstVisiblePosition() == 0)
        {
            
            headerProgress.setVisibility(View.VISIBLE);
            headerText.setText(R.string.pull_to_refresh_refreshing_label);
            headerArrow.setAnimation(null);
            headerArrow.setVisibility(View.GONE);
            
            headerView.setPadding(0, 0, 0, 0);
            headerView.invalidate();
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
        isLoading = false;
        canScollUp = true;

        headerView.setPadding(0, ORGIN_MARGIN_TOP, 0, 0);
        headerText.setVisibility(View.VISIBLE);
        headerArrow.setVisibility(View.VISIBLE);
        headerArrow.clearAnimation();
        headerProgress.setVisibility(View.GONE);
        headerView.invalidate();
        invalidate();
    }

    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
//        Log.e("","computeScroll="+scroller.computeScrollOffset());
        if (scroller.computeScrollOffset()) {
            int i = this.scroller.getCurrY();
            int k = Math.max(i, ORGIN_MARGIN_TOP);
            Log.e("","k="+k);
            headerView.setPadding(0, k, 0, 0);
            
            this.headerView.invalidate();
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
