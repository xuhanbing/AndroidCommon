/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��4��3�� 
 * Time : ����3:50:29
 */
package com.hanbing.mytest.activity.view;

import java.io.File;
import java.util.Date;

import com.hanbing.mytest.adapter.MyCursorAdapter;
import com.hanbing.mytest.data.UserInfo;
import com.hanbing.mytest.db.DBCommon;
import com.hanbing.mytest.db.DBHelper;
import com.hanbing.mytest.db.DBCommon.TbUserInfo;
import com.hanbing.mytest.view.MyScrollView;
import com.hanbing.mytest.view.PullToRefreshListView;
import com.hanbing.mytest.view.PullToRefreshListView.RefreshListener;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * TestListView.java
 * 
 * @author hanbing
 * @date 2014��4��3��
 * @time ����3:50:29
 */
public class TestListView extends Activity {

    ListView listView;
    Cursor cursor;
    DBHelper helper;
    MyCursorAdapter adapter;
    SQLiteDatabase db;

    Handler handler;

    MyScrollView sv;

    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);

	helper = DBHelper.getInstance(this);// new DBHelper(this);

	handler = new Handler() {
	    @Override
	    public void dispatchMessage(Message msg) {
		// TODO Auto-generated method stub

	    }
	};

	db = helper.getWritableDatabase();

	registerObserver();

	edit = new EditText(this);
	edit.setBackgroundColor(Color.YELLOW);

	LinearLayout layout = new LinearLayout(this);
	layout.setOrientation(LinearLayout.VERTICAL);

	initData(false);
	initCursor();
	// initData();

	adapter = new MyCursorAdapter(this, cursor, true);

	initPull();
	// sv = new MyScrollView(this);

	LinearLayout.LayoutParams lps = new LayoutParams(
		LayoutParams.MATCH_PARENT, 0, 1);

	layout.addView(listView, lps);
	layout.addView(edit);
	// ListView l = new ListView(this);
	// l.setAdapter(adapter);
	// sv.addView(listView);
	setContentView(layout);

	startThread();

    }

    ContentObserver contentObserver = new ContentObserver(handler) {

	public void onChange(boolean selfChange) {
	    Log.e("dbtest", "db onChange");
	};
    };

    String dbPath = "";

    private void registerObserver() {
	// TODO Auto-generated method stub
	Uri uri = Uri.fromFile(new File(dbPath));
	getContentResolver().registerContentObserver(uri, false,
		contentObserver);
    }

    private void unregisterObserver() {
	getContentResolver().unregisterContentObserver(contentObserver);
    }

    static final int TEST_COUNT = 1000;

    private void startThread() {
	// TODO Auto-generated method stub

	int times = 3;

	// startInsertThread("insert" + 0);
	for (int i = 0; i < times; i++) {
	    startInsertThread("insert" + i);
	    startGetThread("get" + i);
	}

    }

    private void startInsertThread(final String tag) {
	new Thread(new Runnable() {

	    @Override
	    public void run() {
		// TODO Auto-generated method stub

		// DBHelper helper = new DBHelper(getApplicationContext());
		// SQLiteDatabase db = helper.getWritableDatabase();
		// Log.e("dbtest",tag + " db=" + db.toString());
		int count = 0;

		long start = System.currentTimeMillis();

		// db.beginTransaction();

		while (count < TEST_COUNT) {
		    write();

		    count++;
		}

		// db.setTransactionSuccessful();
		// db.endTransaction();

		Log.e("dbtest", tag + " cost="
			+ (System.currentTimeMillis() - start));
		// db.close();
		// helper.close();
	    }
	}).start();
    }

    private void startGetThread(final String tag) {
	new Thread(new Runnable() {

	    @Override
	    public void run() {
		// TODO Auto-generated method stub

		// DBHelper helper = new DBHelper(getApplicationContext());
		// SQLiteDatabase db = helper.getReadableDatabase();
		// Log.e("dbtest",tag + " db =" + db.toString());
		int count = 0;

		long start = System.currentTimeMillis();

		// db.beginTransaction();
		while (count < TEST_COUNT) {

		    read();

		    count++;
		}

		// db.setTransactionSuccessful();
		// db.endTransaction();

		Log.e("dbtest", tag + " cost="
			+ (System.currentTimeMillis() - start));

		// db.close();
		// helper.close();

	    }
	}).start();
    }

    private void read() {
	DBHelper helper = DBHelper.getInstance(getApplicationContext());// new
									// DBHelper(getApplicationContext());
	SQLiteDatabase db = helper.getWritableDatabase();

	Cursor cursor = db.rawQuery("select * from "
		+ DBCommon.TbUserInfo.TB_NAME, null);

	cursor.close();

	// db.close();
	// helper.close();
    }

    private void write() {
	DBHelper helper = DBHelper.getInstance(getApplicationContext());// new
									// DBHelper(getApplicationContext());
	SQLiteDatabase db = helper.getWritableDatabase();

	int i = (int) System.currentTimeMillis();

	UserInfo userInfo = new UserInfo();
	userInfo.setId(i);
	userInfo.setName("haha=" + new Date().toString());
	userInfo.setAge(i);
	db.insert(TbUserInfo.TB_NAME, null, userInfo.getContentValues());

	// db.close();
	// helper.close();
    }

    LinearLayout layout;
    TextView header;

    private void init() {
	listView = new ListView(this);

	layout = new LinearLayout(this);
	layout.setOrientation(LinearLayout.VERTICAL);
	header = new TextView(this);

	TextView text = new TextView(this);

	header.setText("This is Header");
	header.setTextSize(50);

	text.setText("This is Extra");
	text.setTextSize(250);
	layout.addView(text);
	layout.addView(header);

	listView.addHeaderView(layout);
	listView.setAdapter(adapter);

	listView.setOnScrollListener(new OnScrollListener() {

	    @Override
	    public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void onScroll(AbsListView view, int firstVisibleItem,
		    int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

		int[] loc1 = new int[2];
		int[] loc2 = new int[2];
		Rect r = new Rect();
		header.getGlobalVisibleRect(r);

		header.getLocationOnScreen(loc1);
		header.getLocationInWindow(loc2);

		Log.e("tag", "onScroll" + "onscreen[" + loc1[0] + "," + loc1[1]
			+ "]" + "inwindow[" + loc2[0] + "," + loc2[1] + "]"
			+ "rect=" + r.toString());

	    }
	});

    }

    private void initPull() {
	listView = new PullToRefreshListView(this);

	listView.setAdapter(adapter);

	listView.setOnItemLongClickListener(new OnItemLongClickListener() {

	    @Override
	    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		    int arg2, long arg3) {
		// TODO Auto-generated method stub
		return false;
	    }
	});
	listView.setOnItemClickListener(new OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		    long arg3) {
		// TODO Auto-generated method stub
		String text = "pos = " + arg2;
		Toast.makeText(TestListView.this, text, Toast.LENGTH_SHORT)
			.show();
	    }
	});

	((PullToRefreshListView) listView)
		.setRefreshListener(new RefreshListener() {

		    @Override
		    public void onReflesh() {
			// TODO Auto-generated method stub
			reflesh(false);

			try {
			    Thread.sleep(1000);
			} catch (InterruptedException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		    }
		});

//	listView.setSelection(cursor.getCount());

    }

    private void reflesh(boolean isAdd) {
	initData(isAdd);
	initCursor();

	handler.post(new Runnable() {

	    @Override
	    public void run() {
		// TODO Auto-generated method stub
		adapter.changeCursor(cursor);
	    }
	});
    }

    /**
     * 
     */
    private void initCursor() {
	// TODO Auto-generated method stub
	cursor = db.rawQuery("select * from " + TbUserInfo.TB_NAME, null);
    }

    private void initData(boolean isAdd) {
	if (!isAdd) {
	    db.delete(TbUserInfo.TB_NAME, null, null);
	}

	db.beginTransaction();
	for (int i = 0; i < 20; i++) {
	    UserInfo userInfo = new UserInfo();
	    userInfo.setId(i);
	    userInfo.setName("haha=" + new Date().toString());
	    userInfo.setAge(i);
	    Log.e("dbtest", "insert");
	    long count = db.insert(TbUserInfo.TB_NAME, null,
		    userInfo.getContentValues());

	}

	db.setTransactionSuccessful();
	db.endTransaction();
    }

    @Override
    protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	db.close();

	unregisterObserver();
    }

}
