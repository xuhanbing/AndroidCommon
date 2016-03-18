package com.hanbing.mytest.activity;

import com.hanbing.mytest.R;
import com.hanbing.mytest.activity.view.TestDraw;
import com.hanbing.mytest.activity.view.TestGifActivity;
import com.hanbing.mytest.activity.view.TestProgress;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

@SuppressLint("NewApi")
public class TestAppWidget extends AppWidgetProvider {

	static final String TAG = "TestAppWidget";
	
	
	
	public TestAppWidget() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.appwidget.AppWidgetProvider#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		System.out.println(TAG+":onReceive " + intent.getAction());
		String action = intent.getAction();
		
		if (action.equals(AppWidgetManager.ACTION_APPWIDGET_ENABLED))
		{
			
		}
	}

	/* (non-Javadoc)
	 * @see android.appwidget.AppWidgetProvider#onUpdate(android.content.Context, android.appwidget.AppWidgetManager, int[])
	 */
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		System.out.println(TAG+":onUpdate");
		
		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), 
				R.layout.layout_widget_main);
		
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
		
		ComponentName name = new ComponentName(context, TestAppWidget.class);
		AppWidgetManager m = AppWidgetManager.getInstance(context);
		
		m.updateAppWidget(name, remoteViews);
	}

	/* (non-Javadoc)
	 * @see android.appwidget.AppWidgetProvider#onAppWidgetOptionsChanged(android.content.Context, android.appwidget.AppWidgetManager, int, android.os.Bundle)
	 */
	@Override
	public void onAppWidgetOptionsChanged(Context context,
			AppWidgetManager appWidgetManager, int appWidgetId,
			Bundle newOptions) {
		// TODO Auto-generated method stub
		super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId,
				newOptions);
		System.out.println(TAG+":onAppWidgetOptionsChanged");
	}

	/* (non-Javadoc)
	 * @see android.appwidget.AppWidgetProvider#onDeleted(android.content.Context, int[])
	 */
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onDeleted(context, appWidgetIds);
		System.out.println(TAG+":onDeleted");
	}

	/* (non-Javadoc)
	 * @see android.appwidget.AppWidgetProvider#onEnabled(android.content.Context)
	 */
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
		System.out.println(TAG+":onEnabled");
	}

	/* (non-Javadoc)
	 * @see android.appwidget.AppWidgetProvider#onDisabled(android.content.Context)
	 */
	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
		System.out.println(TAG+":onDisabled");
	}
	
	

}
