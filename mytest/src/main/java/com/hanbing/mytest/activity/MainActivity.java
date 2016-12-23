package com.hanbing.mytest.activity;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.hanbing.library.android.activity.BaseAppCompatActivity;
import com.hanbing.library.android.util.ToastUtils;
import com.hanbing.mytest.activity.action.TestShortcut;
import com.hanbing.mytest.fragment.NumFragment;
import com.hanbing.mytest.module.TestJni;
import com.hanbing.mytest.service.TestService;
import com.hanbing.mytest.view.RoundDrawable;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Timer;

public class MainActivity extends BaseAppCompatActivity {

	static  Class<?> DEFAULT_CLASS = null;

	private static final String TAG = "123";
	private static final int CONNECT_TIME_OUT = 3 * 1000;
	private static final int READ_TIME_OUT = CONNECT_TIME_OUT;
	Context mContext;
	ExpandableListView mLvActivityList;
	BaseExpandableListAdapter adapter;
	List<CategoryEntity> mData = new ArrayList<CategoryEntity>();
	List<CategoryEntity> mBackupData = new ArrayList<CategoryEntity>();
	// List<Class<?>> list = new ArrayList<Class<?>>();

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	protected void onCreate(Bundle savedInstanceState) {


		TestJni.print("");

		DisplayMetrics dm = getResources().getDisplayMetrics();
		super.onCreate(savedInstanceState);

		int ret = getApplicationContext().checkCallingOrSelfPermission(permission.CAMERA);

		Log.e("", "camera ret=" + ret + "," + PackageManager.PERMISSION_GRANTED);

		mContext = this.getApplicationContext();

		setContentView(R.layout.layout_main);

		initList();

		mLvActivityList = (ExpandableListView) findViewById(R.id.lv_content);

		Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_in_from_down);
		LayoutAnimationController controller = new LayoutAnimationController(animation);
		mLvActivityList.setLayoutAnimation(controller);

		NumFragment fragment = new NumFragment();

		View header = LayoutInflater.from(this).inflate(R.layout.layout_simple, null);

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

		ft.replace(R.id.layout_simple, fragment);

		ft.commit();

		mLvActivityList.addHeaderView(header);
		mLvActivityList.setBackgroundColor(Color.RED);

		 adapter = new ActivityAdapter();
		mLvActivityList.setAdapter(adapter);

		mLvActivityList.setOnGroupClickListener(new OnGroupClickListener() {

			@SuppressLint("NewApi")
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
				// TODO Auto-generated method stub
				if (mLvActivityList.expandGroup(groupPosition, true)) {

				} else {
					mLvActivityList.collapseGroup(groupPosition);
				}
				return true;
			}
		});

		mLvActivityList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
					long id) {
				// TODO Auto-generated method stub
				startActivity((Class<?>) adapter.getChild(groupPosition, childPosition));
				return true;
			}
		});
		// mLvActivityList.setGroupIndicator(getResources().getDrawable(R.drawable.expandlist_indicator));
		// mLvActivityList.setChildIndicator(getResources().getDrawable(R.drawable.b));

		mLvActivityList
				.setBackgroundDrawable(new RoundDrawable(BitmapFactory.decodeResource(getResources(), R.drawable.a)));

		initToolbar();

		startService(new Intent(this, TestService.class));
	}

	@Override
	protected void setContentView() {

	}

	Toolbar toolbar;

	SearchView searchView;
	/**
	* 
	*/
	private void initToolbar() {
		// TODO Auto-generated method stub
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
//		toolbar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//			
//			@Override
//			public boolean onMenuItemClick(MenuItem arg0) {
//				// TODO Auto-generated method stub
//				
//				switch(arg0.getItemId())
//				{
//				case R.id.action_search:
//				{
//					Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_SHORT).show();
//				}
//					break;
//				}
//				return true;
//			}
//		});
		
	}

	public static void d(String msg) {
		Log.d("aaa", msg);
	}

	public static void e(String msg) {
		Log.e("aaa", msg);
	}

	private boolean startLastActivity() {
		// TODO Auto-generated method stub
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

		List<RunningTaskInfo> tasks = am.getRunningTasks(5);

		if (tasks.size() > 2) {
			RunningTaskInfo task = tasks.get(1);

			Intent intent = new Intent();
			intent.setComponent(task.baseActivity);
			startActivity(intent);

			System.out.println("last task is=" + task.baseActivity);
			return true;
		}

		return false;

	}


	public static String getFormatTime() {
		SimpleDateFormat f = new SimpleDateFormat("hh:mm:ss");
		return f.format(new Date());
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// setBg();
		// Toast.makeText(this, getResources().getDisplayMetrics().toString(),
		// Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);


		MenuItem item = menu.findItem(R.id.action_search);
		
		if (null != item && item.getActionView() != null)
		{
			searchView = (SearchView) item.getActionView();
			searchView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ToastUtils.showToast(mContext, "search");
				}
			});
			searchView.setOnQueryTextListener(new OnQueryTextListener() {

				@Override
				public boolean onQueryTextSubmit(String arg0) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public boolean onQueryTextChange(String arg0) {
					// TODO Auto-generated method stub
					search(arg0);
					return false;
				}
			});

			searchView.setOnSearchClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ToastUtils.showToast(mContext, "search");
				}
			});
		}
		
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * @param arg0
	 */
	protected void search(String arg0) {
		// TODO Auto-generated method stub
		
		
		mData.clear();
		
		
		
		if (!TextUtils.isEmpty(arg0))
		{
			
			CategoryEntity result = new CategoryEntity();
			result.list = new ArrayList<Class<?>>();
			
			
			for (int i = 0; i < mBackupData.size(); i++)
			{
				
				CategoryEntity entity = mBackupData.get(i);
				
				
				Iterator<Class<?>> iterator = entity.list.iterator();
				
				while (iterator.hasNext())
				{
					Class<?> next = iterator.next();
					
					if (next.getSimpleName().toLowerCase().contains(arg0.toLowerCase()))
					{
						result.list.add(next);
						continue;
					} else {
//						iterator.remove();
					}
				}
			
			}
			
			mData.add(result);
		} else {
			mData.addAll(mBackupData);
		}
		
		adapter.notifyDataSetChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 0: {
			new Thread(new Runnable() {

				@Override
				public void run() {
					try {

						// Thread.sleep(3000);
						// TODO Auto-generated method stub
						URL url = new URL("http://p.freebao.com/contentImg/2015/0410/13235256297.jpg");
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();

						conn.connect();

						int totalLength = conn.getContentLength();

						Log.e("", "content length=" + totalLength);

						// InputStream is = conn.getInputStream();
						//
						// is.close();
						conn.disconnect();

						int alreadyDownLength = 0;

						int needLength = 10000;

						RandomAccessFile file = new RandomAccessFile("/sdcard/1.jpg", "rw");

						while (true) {
							int from = alreadyDownLength;
							int len = Math.min(needLength, totalLength - alreadyDownLength);
							int to = from + len;

							Log.e("", "content get range:" + from + "-" + to + ",len=" + len);

							url = new URL("http://p.freebao.com/contentImg/2015/0410/13235256297.jpg");
							conn = (HttpURLConnection) url.openConnection();
							conn.setReadTimeout(2000);
							conn.setConnectTimeout(2000);
							conn.setRequestMethod("GET");
							conn.setRequestProperty("Range",
									"bytes=" + from + "-"/*
															 * + to
															 */);

							conn.connect();
							Log.e("", "resp code=" + conn.getResponseCode());
							Log.e("", "ret=" + conn.getResponseMessage());
							InputStream is = conn.getInputStream();

							byte[] buffer = new byte[len];

							int ll = 0;
							int ret = 0;
							while ((ret = is.read(buffer, ll, len - ll)) != -1 && ll < len) {
								ll += ret;
							}

							alreadyDownLength += ll;
							is.close();
							conn.disconnect();
							file.write(buffer, 0, ll);

							if (alreadyDownLength >= totalLength)
								break;
						}

						file.close();

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

				}
			}).start();
		}
			break;
		case 1:
		case 2:
		case 3:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	static final String TAG_VIEW = "view";
	static final String TAG_ACTION = "action";
	static final String TAG_FRAGMENT = "fragment";
	static final String TAG_GAME = "game";

	private void initList() {

		// initViewList();
		// initActionList();
		// initFragmentList();
		// initGameList();

		ApplicationInfo applicationInfo = getApplicationInfo();

		Map<String, List<Class<?>>> tags = new HashMap<String, List<Class<?>>>();

		PackageInfo packageInfo;



		try {
			packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);

			ActivityInfo[] activities = packageInfo.activities;

			for (ActivityInfo activityInfo : activities) {
				// System.out.println("activity:" + activityInfo.name);

				String name = activityInfo.name;

				if (name.equals(this.getClass().getName()))
					continue;

				String[] arr = name.split("\\.");

				String tag = "*";
				if (arr.length < 2) {

				} else
					tag = arr[arr.length - 2];

				List<Class<?>> list = tags.get(tag);

				Class<?> clazz = null;
				try {

					clazz = Class.forName(name);

				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();

				}

				if (null == clazz)
					continue;

				if (null == list) {
					list = new ArrayList<Class<?>>();
					tags.put(tag, list);
				}

				list.add(clazz);


			}

		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Iterator<Entry<String, List<Class<?>>>> iterator = tags.entrySet().iterator();

		while (iterator.hasNext()) {

			Entry<String, List<Class<?>>> next = iterator.next();

			if (next.getValue().size() > 0)
				addCategory(next.getKey(), next.getValue());
		}

		if (null != DEFAULT_CLASS)
			startActivity(DEFAULT_CLASS);
	}





	private void addCategory(String key, List<Class<?>> list) {

		Collections.sort(list, new Comparator<Class<?>>() {

			@Override
			public int compare(Class<?> lhs, Class<?> rhs) {
				// TODO Auto-generated method stub
				String lname = lhs.getSimpleName();
				String rname = rhs.getSimpleName();
				return lname.compareTo(rname);
			}

		});

		CategoryEntity c = new CategoryEntity();
		c.key = key;
		c.list = list;

		mData.add(c);
		mBackupData.add(c);
	}

	private void startActivity(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
	}

	class ActivityAdapter extends BaseExpandableListAdapter {

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return null == mData ? 0 : mData.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			// TODO Auto-generated method stub
			List<Class<?>> list = null == mData ? null : mData.get(groupPosition).list;
			return null == list ? 0 : list.size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return null == mData ? "" : mData.get(groupPosition).key+"";
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			List<Class<?>> list = null == mData ? null : mData.get(groupPosition).list;
			return null == list ? null : list.get(childPosition);

		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			ViewHolder holder = new ViewHolder();

			CardView cardView = new CardView(parent.getContext());
			TextView text = new TextView(mContext);

			text.setText(getGroup(groupPosition).toString());
			text.setTextSize(30);
			text.setTextColor(Color.BLACK);
			text.setGravity(Gravity.CENTER);

			cardView.addView(text);
			
			return cardView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub

			TextView text = new TextView(mContext);
			text.setPadding(20, 20, 20, 20);
			text.setTextSize(25);

			Class<?> clazz = (Class<?>) getChild(groupPosition, childPosition);

			String[] names = clazz.getName().split("\\.");
			text.setText(names[names.length - 1]);
			text.setTextSize(25);
			// text.setGravity(Gravity.CENTER);

			return text;
		}

		class ViewHolder {
			public View itemView;
			public TextView textView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return true;
		}

//		private TextView getTextView(View convertView) {
//			TextView text = null;
//
//			if (null == convertView) {
//				text = new TextView(mContext);
//				text.setPadding(20, 20, 20, 20);
//				text.setTextSize(25);
//				convertView = text;
//			} else {
//				text = (TextView) convertView;
//			}
//			return text;
//		}
	}

	boolean isFirstPressBack = true;

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isFirstPressBack = true;
	}

	Timer timer = new Timer();

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stopService(new Intent(this, TestService.class));
		deleteShortCut();
		super.onDestroy();
		// Debug.stopMethodTracing();
	}

	String shortcutIntentAction = "com.android.action.test";

	private void createShortCut() {
		Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		Intent shortCutIntent = new Intent(this, TestShortcut.class);
		shortCutIntent.setAction(shortcutIntentAction);
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCutIntent);
		intent.putExtra("duplicate", true);
		ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(this, R.drawable.a);
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "TestShortCut");

		sendBroadcast(intent);
	}

	private void deleteShortCut() {
		// Intent intent = new
		// Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
		// // intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "TestShortCut");
		// String appClass = TestShortcut.class.getName();
		// ComponentName comp = new ComponentName(this.getPackageName(),
		// appClass);
		// intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new
		// Intent(Intent.ACTION_MAIN).setComponent(comp));
		// sendBroadcast(intent);

		Intent intent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
		Intent shortCutIntent = new Intent(this, TestShortcut.class);
		shortCutIntent.setAction(shortcutIntentAction);
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCutIntent);
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "TestShortCut");

		sendBroadcast(intent);
	}

	class CategoryEntity {
		String key;
		List<Class<?>> list;
	}

}
