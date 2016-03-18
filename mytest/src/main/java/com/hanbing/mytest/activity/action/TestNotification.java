/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��4��8�� 
 * Time : ����4:30:21
 */
package com.hanbing.mytest.activity.action;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;













import com.hanbing.mytest.R;
import com.hanbing.mytest.activity.view.TestDraw;
import com.hanbing.mytest.activity.view.TestGifActivity;
import com.hanbing.mytest.activity.view.TestGridView2;
import com.hanbing.mytest.activity.view.TestProgress;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.ScrollView;
import android.widget.TextView;


/**
 * TestNotification.java 
 * @author hanbing 
 * @date 2014��4��8�� 
 * @time ����4:30:21
 */
public class TestNotification extends Activity {

    NotificationManager notificationMagager;
    Notification notification;
    
    ScrollView scrollView;
    LinearLayout layout;
    TextView text;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_notify);
        
//        notificationMagager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//    
//        scrollView = new ScrollView(this);
//        layout = new LinearLayout(this);
        text = new TextView(this);
//        
//        scrollView.addView(layout);
//        layout.addView(text);
//        
//        
//        setContentView(scrollView);
//        
//        SpannableStringBuilder sb = new SpannableStringBuilder();
//        sb.append("smiles1234567890");
//
//        AnimationDrawable ad = (AnimationDrawable) getResources().getDrawable(R.drawable.emoji_smile);
//        ad.setBounds(0, 0, ad.getIntrinsicWidth(), ad.getIntrinsicHeight());
//        
//        ImageSpan imageSpan = new ImageSpan(ad);
//        
//        sb.setSpan(imageSpan, 0, sb.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//        
//        text.setText(sb);
//        
//        ad.start();
        
        setContentView(text);
        
//        init();
        
        new Thread(
                   new Runnable() {
                    
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        
                        showNofication();

                    }
                }).start();
        
        
    }

    String f = "(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?";
    String f1 = "http://www.*.com";
    String f2 = "(((http\\:\\/\\/)|(https\\:\\/\\/)|(www\\.))[a-zA-Z0-9_\\.com]+)";
    /**
     * 
     */
    private void init() {
        // TODO Auto-generated method stub
        String s0 = "http://www.baidu.com";
        String s1 = "https://www.baidu.com";
        String s2 = "www.baidu.com";
        String s3 = "baidu.com";
        String s4 = "http://baidu.com";
        String s5 = "https://baidu.com";
        String s6 = "hi.org";
        String s7 = "hi.me";
        String s8 = "hi.mob";
        String s9 = "hi.cc";
        String s10 = "hz.58.com";
        
        List<String> list = new ArrayList<String>();
        list.add(s0);
        list.add(s1);
        list.add(s2);
        list.add(s3);
        list.add(s4);
        list.add(s5);
        list.add(s6);
        list.add(s7);
        list.add(s8);
        list.add(s9);
        list.add(s10);
        
        Pattern p = Pattern.compile(f);
        
        
        StringBuilder sb2 = new StringBuilder();
        SpannableStringBuilder ss = new SpannableStringBuilder(sb2);
        
        for (String s : list)
        {
            Matcher m = p.matcher(s);
            
            while (m.find())
            {
                String str = m.group();
                int start = ss.length();
                int end = start + str.length();
                ss.append(str);
                String url = str;
                if (!url.contains("http://"))
                {
                    url = "http://" + str;
                }
                ss.setSpan(new URLSpan(url), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
                ss.append("\n");
            }
        }
        
        
        text.setText(ss);
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }


    @SuppressWarnings("deprecation")
//    private void showNofication()
//    {
//        
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//        mNotificationManager.cancelAll();
//        
//        
//        int icon = R.drawable.ic_launcher;
//        
//        String tickerText = "Notification";
//        String contentTitle = "Test";
//        String contentText = "This is a test!";
//        
//        Notification.Builder builder = new Builder(this);
//        
//        
//        Intent notificationIntent = new Intent(this, TestGridView2.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        Bundle bundle = new Bundle();
//        notificationIntent.putExtras(bundle);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        
////        Notification notification = builder.build();
//        Notification notification = new Notification(icon, tickerText, System.currentTimeMillis());
//        
//        
//        //led
//        notification.ledARGB =  Color.RED;
//        notification.ledOnMS = 1000;
//        notification.ledOffMS = 1000;
//        
//        notification.flags |= (Notification.FLAG_ONLY_ALERT_ONCE
//                | Notification.FLAG_AUTO_CANCEL
//                | Notification.FLAG_SHOW_LIGHTS);
//        
//        notification.defaults = Notification.DEFAULT_ALL;
//               ;
//
//        //notification.defaults |= Notification.DEFAULT_SOUND;
//        notification.setLatestEventInfo(this, contentTitle,contentText, contentIntent);
//        
//        notification.largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.p1);
//        
//        mNotificationManager.notify(0, notification);
//        
//        
//    }
    
    
    private void showNofication()
    {
        
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();
        
        
        int icon = R.drawable.ic_launcher;
        
        String tickerText = "Notification";
        String contentTitle = "Test";
        String contentText = "This is a test!";
        
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        
        
        Intent notificationIntent = new Intent(this, TestGridView2.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        notificationIntent.putExtras(bundle);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setTicker(tickerText);
        builder.setContentText(contentText);
        builder.setContentTitle(contentTitle);
        builder.setSmallIcon(R.drawable.ic_launcher);
        
        Context context = this;
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layout_widget_main);
        
        {
			Intent intent = new Intent(context, TestGifActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			remoteViews.setOnClickPendingIntent(R.id.tv_left, pendingIntent);
		}
		
		{
			Intent intent = new Intent(context, TestDraw.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			remoteViews.setOnClickPendingIntent(R.id.tv_center, pendingIntent);
		}
		
		{
			Intent intent = new Intent(context, TestProgress.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			remoteViews.setOnClickPendingIntent(R.id.tv_right, pendingIntent);
		}
		
        builder.setContent(remoteViews);
        
        mNotificationManager.notify(0, builder.build());
        
        
    }
}
