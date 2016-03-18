/**
 * 
 */
package com.hanbing.mytest.activity.action;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.hanbing.mytest.R;
import com.hanbing.mytest.activity.BaseActivity;

/**
 * @author hanbing
 * @date 2015-9-17
 */
public class TestAsyncTask extends BaseActivity {

    
    Executor executor1 = Executors.newFixedThreadPool(6);
    Executor executor2 = Executors.newFixedThreadPool(3);
    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        
        ScrollView scrollView = new ScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        
        addTask("default", layout, null, 10);
        addTask("executor1", layout, executor1, 10);
        addTask("executor2", layout, executor2, 10);
        
        scrollView.addView(layout);
        setContentView(scrollView);
    }
    
    public void addTask(String tag, ViewGroup layout, Executor executor, int count)
    {
        
        int i = 0;
        while (i < count)
        {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_progressbar, null);
            final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
            progressBar.setProgress(0);
            progressBar.setMax(100);
            layout.addView(view);
            
            MyTask task = new MyTask(tag + " task " + i){
        	@Override
        	protected void onProgressUpdate(Integer... values) {
        	    // TODO Auto-generated method stub
        	    int progress = values[0];
        	    
        	    progressBar.setProgress(progress);
        	}
            };
            
            if (null == executor)
            {
        	task.execute();
            }
            else
        	task.executeOnExecutor(executor);
            i++;
        }
    }
    
    
    class MyTask extends AsyncTask<String, Integer, Integer>
    {

	String tag;
	public MyTask(String tag)
	{
	    this.tag = tag;
	}
	@Override
	protected Integer doInBackground(String... params) {
	    // TODO Auto-generated method stub
	    Log.e("", tag + " start");
	    int count = 0;
	    
	    while (count < 100)
	    {
		count += 2;
		
		publishProgress(count);
		
		SystemClock.sleep(200);
	    }
	    
	    Log.e("", tag + " end");
	    
	    return 0;
	}
	
    }

}
