/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��7��17�� 
 * Time : ����2:37:49
 */
package com.hanbing.mytest.activity.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.hanbing.mytest.listener.OnPageChangedListener;
import com.hanbing.mytest.view.PageTextView;
import com.hanbing.mytest.view.PageTextView.OnCalculateFinishListener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * TestPageText.java 
 * @author hanbing 
 * @date 2014��7��17�� 
 * @time ����2:37:49
 */
public class TestPageText extends Activity {

    
    String filePath = "/storage/emulated/0/11.txt";
//	String filePath = "/storage/emulated/0/VAorder/Config/549/1112_13.txt";
    
    ScrollView scrollView;
    PageTextView pageTextView = null;
    PageTextView pageTextViewClone = null;
    
    Handler mHandler = new Handler();
    
    
    byte[] buffer = null;
    String string;
    int height = 0;
    /**
     * 
     */
    public TestPageText() {
        // TODO Auto-generated constructor stub
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_pagetext);
        
        pageTextView  = (PageTextView) findViewById(R.id.tv_pagetext_content);
        pageTextViewClone = (PageTextView) findViewById(R.id.tv_pagetext_content_clone);
        pageTextViewClone.setOnCalculateFinishListener(new OnCalculateFinishListener() {
			
			@Override
			public void onCalculateFinish() {
				// TODO Auto-generated method stub
				pageTextView.setTotalPage(pageTextViewClone.getTotalPage());
			}
		});
        showContent();
        
//        createFileChooser();
    }
    
    private void createFileChooser() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("text/*"); 
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        
		startActivityForResult(Intent.createChooser(intent, "ѡ���ļ�"), 123);
	}

	private void showContent() {
		// TODO Auto-generated method stub
		showProgress();
    	try {
            FileInputStream in = new FileInputStream(filePath);
            
            buffer = new byte[in.available()];
            
            in.read(buffer);
            
            in.close();
            in = null;
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
//        pageTextView.setText(buffer, start, num);
        string = new String(buffer);
        System.out.println("string len=" + string.length());
//        
        pageTextView.setContent(string);
        pageTextView.setText(string);
        pageTextView.setFilePath(filePath);
        
        pageTextView.setOnPageChangedListener(new OnPageChangedListener() {
			
			@Override
			public void onPageChanged(int current, int total) {
				// TODO Auto-generated method stub
				setTitle(current + "/" + total);
			}
		});
        
        pageTextViewClone.setContent(string);
        pageTextViewClone.setText(string);
//        pageTextViewClone.calculatePages();
        
        
//        int width = pageTextView.getWidth();
//        int height = pageTextView.getHeight();
//        
//        System.out.println("1w,h=" + width + "," + height);
//        
//        
//        System.out.println("1layout=" + pageTextView.getLayout());
        int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.EXACTLY);
        int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.EXACTLY);
        
        pageTextViewClone.measure(w, h);
        
//        pageTextView.calculatePages();
//        pageTextView.setText(string);
//        
//        w =pageTextView.getMeasuredWidth();
//        h = pageTextView.getMeasuredHeight();
//        System.out.println("2w,h=" + w + "," + h);
//        
//        System.out.println("2layout=" + pageTextView.getLayout() + ",count=" + pageTextView.getCharNum());
        
        final PageTextView view = pageTextViewClone;
        final ViewTreeObserver vto = view.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                int height = view.getMeasuredHeight();
                int width = view.getMeasuredWidth();
                System.out.println("3w,h=" + width + "," + height);
                
                System.out.println("3layout=" + view.getLayout()+ ",count=" + view.getCharNum());
                
                
                mHandler.post(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
//						pageTextViewClone.calculatePages();
						pageTextViewClone.calcPages();
		                
//		                pageTextView.setTotalPage(pageTextViewClone.getTotalPage());
		                
		                hideProgress();
					}
				});
				
                
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                
                return true;
            }
        });
//        
//        vto.addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
//			
//			@Override
//			public void onDraw() {
//				// TODO Auto-generated method stub
//				System.out.println("onDraw");
//			}
//		});
//        
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//			
//			@Override
//			public void onGlobalLayout() {
//				// TODO Auto-generated method stub
//				System.out.println("onGlobalLayout");
//			}
//		});
        
	}
	
	
	ProgressDialog progressDialog;
	
	private void hideProgress() {
		// TODO Auto-generated method stub
		if (null != progressDialog
				&& progressDialog.isShowing())
		{
			progressDialog.dismiss();
		}
	}

	private void showProgress() {
		// TODO Auto-generated method stub
	
		progressDialog = ProgressDialog.show(this, "��ʾ", "���ڼ���ҳ�������Ժ�...", true);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		if (resultCode == RESULT_OK)
		{
			switch (requestCode)
			{
			case 123:
				
				if (null != data)
				{
					
					Uri uri = data.getData();
					
					filePath = getPath(uri);
					showContent();
				}
				
				
				break;
				
			}
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private String getPath(Uri uri)
	{
		
		String path = "";
		
		if (uri.getScheme().equals("content"))
		{
			String[] projection = { "_data" };
			Cursor cursor = getContentResolver().query(uri, projection, 
					null, null, null);
			
			if (cursor.moveToFirst())
			{
				path = cursor.getString(cursor.getColumnIndex("_data"));
			}
			
			cursor.close();
		}
		else if (uri.getScheme().equals("file"))
		{
			path = uri.getPath();
		}
		
		System.out.println("src file path=" + path);
		return path;
	}

	@Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    }
    
    
    
    public void prev(View view)
    {
//        pageTextView.scrollBy(0, -height);
    	
    	pageTextView.prev();
    }
    
    public void next(View view)
    {
//        pageTextView.scrollBy(0, height);
    	
    	pageTextView.next();
    	
    	
    }
    
    
}
