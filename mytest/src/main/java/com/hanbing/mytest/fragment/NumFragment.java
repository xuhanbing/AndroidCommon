/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��7��8�� 
 * Time : ����10:21:18
 */
package com.hanbing.mytest.fragment;

import com.hanbing.mytest.R;
import com.hanbing.mytest.activity.view.TestCustomEditText;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * NumFragment.java 
 * @author hanbing 
 * @date 2014��7��8�� 
 * @time ����10:21:18
 */
public class NumFragment extends Fragment{

    TextView text;
    int num = 1;
    
    View rootView = null;
    
    public static NumFragment newInstance(int count)
    {
	NumFragment fragment = new NumFragment();
        Bundle args = new Bundle();
        args.putInt("num", count);
        fragment.setArguments(args);
        
        return fragment;
    }
    
    /* (non-Javadoc)
     * @see android.app.Fragment#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        num = getArguments() != null ? getArguments().getInt("num") : 1;
        
        GridView grid;
        ListView list;
        
    }

    /* (non-Javadoc)
     * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
	Log.e("", "NumFragment " + num + " onCreateView");
	
	if (null != rootView)
	{
	    ViewGroup group = (ViewGroup) rootView.getParent();
	    
	}
	
        View view = inflater.inflate(R.layout.layout_numfragment, container, false);
        rootView = view;
//        view.setBackgroundColor(System.currentTimeMillis() % 2 == 0 ? Color.GRAY : Color.WHITE);
        text = (TextView) view.findViewById(R.id.tv_numfragment_text);
        
        text.setText("fragment " + num);
        text.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().startActivityForResult(new Intent(getActivity(), TestCustomEditText.class),  123);
			}
		});
        
//        BitmapUtils bmu = new BitmapUtils(getActivity());
//        bmu.display(text, "/storage/sdcard0/Samples/Pictures/Picture_A_Beach.jpg");

        view.setBackgroundColor(num % 2 == 0 ? Color.RED : Color.GREEN);
        view.setTag(num);
        return view;//super.onCreateView(inflater, container, savedInstanceState);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	if (resultCode == Activity.RESULT_OK)
		{
			switch (requestCode)
			{
			case 123:
			{
				String message = data.getStringExtra("data");
				
				Toast.makeText(getActivity(), "fragment text = " + message, Toast.LENGTH_SHORT).show();
			}
				break;
			}
		}
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
}
