package com.hanbing.mytest.activity.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.common.tool.PinyinParser;
import com.common.util.PinyinUtils;
import com.hanbing.mytest.activity.BaseActivity;
import com.hanbing.mytest.view.ClearableEditText;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * ��TestCustomEditText.java��ʵ��������TODO ��ʵ������ 
 * @author Administrator 2014��2��19�� ����2:37:51
 */
public class TestCustomEditText extends BaseActivity {

    Context mContext;
    ListView mListView;
    MyAdapter mAdapter;
    
    String [] values = {"�й�", "����", "������", 
                        "ɵ��", "2��", "ɣq˹", "������",
                        "hello", "wrold", "abc",
                        "big", "zig", "ill",
                        "week", "go", "low",
                        "Hah", "Get", "���", 
                        "a1��", "��ȥ", "h2h",
                        };
    List<String> mData = new ArrayList<String>();
    ClearableEditText et;
    
    TextWatcher filterListener = new TextWatcher()
    {

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
           mAdapter.filter(arg0.toString());
        }
        
    };
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mContext = this.getApplicationContext();
        initValues();
        
        
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        
        LinearLayout l = new LinearLayout(this);
        l.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
        		LayoutParams.WRAP_CONTENT));
        final EditText edittext = new EditText(this);
        edittext.setSingleLine();
        edittext.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        edittext.setOnEditorActionListener(new OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});
        edittext.addTextChangedListener(filterListener);
        l.addView(edittext, new LayoutParams(LayoutParams.MATCH_PARENT,
        		LayoutParams.WRAP_CONTENT));
        
        layout.addView(l);
        
//        et = new ClearableEditText(this.getApplicationContext());
//        et.setTextColor(Color.BLACK);
//        et.setHint("input");
//        et.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//        et.setSingleLine();
//        et.addTextChangedListener(filterListener);
//        
//        layout.addView(et);
//        
//        SearchView sv = new SearchView(this);
//        sv.setIconifiedByDefault(false);
//        sv.setOnQueryTextListener(new OnQueryTextListener()
//        {
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                // TODO Auto-generated method stub
//                mAdapter.filter(newText.toString());
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // TODO Auto-generated method stub
//                return false;
//            }
//            
//        });
//        layout.addView(sv);
//        
        mAdapter = new MyAdapter();
        mListView = new ListView(mContext);
        mListView.setAdapter(mAdapter);
        mListView.setDividerHeight(2);
        mListView.setOnItemClickListener(new OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                et.setText((String)mAdapter.getItem(arg2));
            }
            
        });
        layout.addView(mListView);
        
        setContentView(layout);
        
        
    }
    
    /**
     * 
     */
    private void initValues() {
        // TODO Auto-generated method stub
        
    	Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME}, 
                null, null, null);
    	
    	while (cursor.moveToNext())
    	{
    		mData.add(cursor.getString(0));
    	}
    	
    	cursor.close();
        
//        for (String s : values)
//        {
//            //mData.add(ChineseToPinyinHelper.hanziToPinyin(s));
//            mData.add(s);
//        }
    }

    class MyAdapter extends BaseAdapter
    {

        List<String> mSubData = new ArrayList<String>();
        private Comparator<? super String> comparator;
        public MyAdapter()
        {
            mSubData.addAll(mData);
            sort();
        }
        /* (non-Javadoc)
         * @see android.widget.Adapter#getCount()
         */
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mSubData.size();
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItem(int)
         */
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mSubData.get(position);
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getItemId(int)
         */
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        /* (non-Javadoc)
         * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            TextView text = new TextView(mContext);
            String value = (String)getItem(position);

            PinyinParser parser = new PinyinParser();
            String string = value+
            		"("+parser.getPinyin(value) + ")"
            		+ "(" + parser.getPinyinFirstCharUpperCase(value, false) + ")"
            		+ "(" + parser.getFirstChars(value) + ")";
            text.setText(string);
            text.setTextSize(12);
            text.setTextColor(Color.BLACK);
            return text;
        }
        
        public void filter(String s)
        {
            mSubData.clear();
            
            if (null == s || s.length() <= 0)
            {
                mSubData.addAll(mData);
            }
            else
            {
                for (String str : mData)
                {
                    if (match(str, s))
                    {
                        mSubData.add(str);
                    }
                }
            }
            sort();
            
            this.notifyDataSetChanged();
        }
        
        @SuppressWarnings("unchecked")
        private void sort()
        {
            Collections.sort(mSubData, new MyComparator());
        }
        
        
        class MyComparator implements Comparator
        {

            /* (non-Javadoc)
             * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
             */
            @Override
            public int compare(Object arg0, Object arg1) {
                // TODO Auto-generated method stub
                
                Collator collator = Collator.getInstance();
             
                String left = (String) arg0;
                String right = (String) arg1;
                
                String pyLeft = PinyinUtils.getPinyin((String) arg0);
                String pyRight = PinyinUtils.getPinyin((String)arg1);
                
                //ƴ������ĸ��Ӣ����ͬʱ����Ӣ�����ں�Ӣ���֮ǰ
                if (pyLeft.startsWith(pyRight.substring(0, 1)))
                {
                    if (left.equalsIgnoreCase(pyLeft)
                            && !right.equalsIgnoreCase(pyRight))
                    {
                        return -1;
                    }
                    else if (!left.equalsIgnoreCase(pyLeft)
                            && right.equalsIgnoreCase(pyRight))
                    {
                        return 1;
                    }
                }
                return collator.compare(pyLeft, pyRight);
                
            }
            
        }
    }
    
	public boolean match(String content, String search)
	{
		PinyinParser parser = new PinyinParser();
		
		String pyContentFirstCharUpperCase = parser.getPinyinFirstCharUpperCase(content, false);
		String pyContentFirstChar = parser.getFirstChars(content);
		String pySearch = parser.getPinyin(search);
		
		boolean isMatch = false;
		
		if (pySearch.equals(search))
		{
			//ȫӢ��
			isMatch = content.contains(search)  //����ƥ��
					|| pyContentFirstChar.contains(search.toUpperCase()) //����ĸƥ��
					|| pyContentFirstChar.contains(search.replace(" ", "").toUpperCase(Locale.getDefault())); //����ĸƥ�䣬ȥ���ո�
					
			//ƴ��ƥ��
			if (!isMatch)
			{
				Pattern pattern = Pattern  
		                .compile(search, Pattern.CASE_INSENSITIVE);  
				
				Matcher m = pattern.matcher(pyContentFirstCharUpperCase);
				//�������ÿ�����ֵĿ�ʼ����ƥ��
				while (m.find())
				{
					int start = m.start();
					//�����һ���ַ�������ĸ��˵��ƥ��ɹ��ˣ����򲻳ɹ�
					char c = pyContentFirstCharUpperCase.charAt(start);
					if (c >= 'A' && c <= 'Z')
					{
						isMatch = true;
						
						break;
					}
				}
			}
		}
		else
		{
			//�����ģ�����ƥ��
			isMatch = content.contains(search);
		}
		return isMatch;
	}

}
