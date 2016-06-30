package com.hanbing.mytest;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.common.util.DipUtils;
import com.common.util.LogUtils;
import com.common.util.ViewUtils;
import com.hanbing.mytest.activity.BaseActivity;
import com.hanbing.mytest.activity.view.TestPtr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewMainActivity extends BaseActivity {

    Class<?> DEFAULT_CLASS = TestPtr.class;

    String key;

    ListView listView;

    List<CategoryEntity> dataList;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_new_main);



        listView = ViewUtils.findViewById(this, R.id.listView);

        init(getIntent());

        if (null != DEFAULT_CLASS) {
            startActivity(new Intent(this, DEFAULT_CLASS));
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        init(getIntent());
    }

    private void init(Intent intent) {
        if (null == intent)
            return;

        key = intent.getStringExtra("key");

        if (TextUtils.isEmpty(key)) {
            key = "";
        }


        initList();


    }

    private void initList() {

        if (null != adapter) {
            listView.setAdapter(null);
        }


        if (null != dataList) {
            dataList.clear();
        } else {
            dataList = new ArrayList<>();
        }

        ApplicationInfo applicationInfo = getApplicationInfo();

        PackageManager pm = getPackageManager();

        String pkgName = getPackageName();


        Map<String, List<Class<?>>> tags = new HashMap<String, List<Class<?>>>();

        PackageInfo packageInfo;


        try {
            String appName = pm.getApplicationLabel(pm.getApplicationInfo(pkgName, 0)).toString();
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);

            ActivityInfo[] activities = packageInfo.activities;

            for (ActivityInfo activityInfo : activities) {
                // System.out.println("activity:" + activityInfo.name);



                String name = activityInfo.name;

                String label = activityInfo.loadLabel(pm).toString();

//                LogUtils.e("activity : " + name);

                if (name.equals(this.getClass().getName()) || !name.startsWith(pkgName))
                    continue;


                //如果没有设置label，或者为app名称，使用类名
                if (TextUtils.isEmpty(label) || label.equals(appName)) {
                    label = name;
                }

                //如果以apk包名开头，去掉
                if (label.startsWith(pkgName)) {
                    label =  label.replace(pkgName + ".activity", "");
                }




                //替换'.'为'/'，与label保持统一
                label = label.replace(".", "/");

                //去掉开头的.
                if (label.startsWith("/")) {
                    label = label.substring(1);
                }


//                LogUtils.e("label : " + label);


                CategoryEntity ce = new CategoryEntity();




                if (TextUtils.isEmpty(key)) {
                    //默认方式

                    String[] arr = label.split("/");

                    if (arr.length > 1) {
                        ce.name =  ce.key = arr[0];
                    } else {
                        ce.name = arr[0];

                        try {
                            ce.clazz = Class.forName(name);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            continue;
                        }

                    }


                }  else if (label.toUpperCase().startsWith(key.toUpperCase())) {


                    String s = label.toUpperCase().replaceFirst(key.toUpperCase() + "/", "");

                    s = label.substring(label.length() - s.length());

                    String[] arr = s.split("/");

                    ce.name = arr[0];


                    if (arr.length > 1) {
                        ce.key = key + "/" + arr[0];
                    } else {
                        ce.key = key;

                        try {
                            ce.clazz = Class.forName(name);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            continue;
                        }
                    }




                } else {
                    ce = null;
                    continue;
                }

                int index = dataList.indexOf(ce);
                if (index < 0)
                {
                    ce.count = 1;
                    dataList.add(ce);
                } else {
                    dataList.get(index).count++;
                }

            }

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        sort();


        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return dataList.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {


                ViewHolder holder;


                if (null != convertView) {
                   holder = (ViewHolder) convertView.getTag();
                } else {
                    TextView textView = new TextView(getApplicationContext());
                    textView.setPadding(20, 20, 20, 20);
                    textView.setTextSize(14);
                    textView.setTextColor(Color.BLACK);


                    ImageView arrow = new ImageView(getApplicationContext());
                    arrow.setImageResource(R.drawable.n2);


                    LinearLayout layout = new LinearLayout(getApplicationContext());

                    layout.addView(textView, new LinearLayout.LayoutParams(-1, -2, 1));

                    int width, height;
                    width = height = DipUtils.dip2px(getApplicationContext(), 20);

                    LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(width, height);
                    lp2.gravity = Gravity.CENTER_VERTICAL;

                    layout.addView(arrow, lp2);

                    holder = new ViewHolder();
                    holder.textView = textView;
                    holder.arrow = arrow;




                    convertView = layout;
                    convertView.setTag(holder);

                }

                CategoryEntity ce = dataList.get(position);
                String text = ce.name;
                text = (text.substring(0, 1).toUpperCase()) + text.substring(1);

                if (null == ce.clazz) {
                    text += "(" + ce.count + ")";
                }

                holder.textView.setText(text);

                holder.arrow.setVisibility(null == ce.clazz ? View.VISIBLE : View.GONE);


                return convertView;
            }


            class ViewHolder {
                TextView textView;
                ImageView arrow;

            }
        };
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CategoryEntity ce = dataList.get(position);

                if (null == ce.clazz) {

                    Intent intent = new Intent(getApplicationContext(), NewMainActivity.class);
                    intent.putExtra("key", ce.key);

                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), ce.clazz);
                    startActivity(intent);
                }
            }
        });
    }

    private void sort() {

        Collections.sort(dataList, new Comparator<CategoryEntity>() {
            @Override
            public int compare(CategoryEntity lhs, CategoryEntity rhs) {
                if (null == lhs.clazz && null != rhs.clazz) {
                    return -1;
                } else if (null != lhs.clazz && null == rhs.clazz) {
                    return 1;
                }
                return lhs.name.compareTo(rhs.name);
            }
        });
    }


    class CategoryEntity{
        String key;
        String name;
        int count = 0;
        Class<?> clazz;



        @Override
        public boolean equals(Object o) {
            if (o instanceof CategoryEntity) {

                CategoryEntity ce = (CategoryEntity) o;


                boolean isEqual = null == key ? (null == ce.key) : (null != ce.key && key.toUpperCase().equals(ce.key.toUpperCase()));


                isEqual &= null == clazz ? (null == ce.clazz) : (null != ce.clazz && clazz.getName().equals(ce.clazz.getName()));

                return isEqual;
            }

            return false;
        }

        @Override
        public int hashCode() {
            int hashCode =  null == key ? 0 : key.hashCode();

            hashCode += (null == clazz ? 0 : clazz.hashCode());

            return hashCode;
        }
    }
}
