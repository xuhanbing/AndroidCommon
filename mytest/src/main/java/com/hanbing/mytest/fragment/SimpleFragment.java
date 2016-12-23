package com.hanbing.mytest.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hanbing.library.android.util.ViewUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleFragment extends Fragment {


    public SimpleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_simple, container, false);

        ListView listView = ViewUtils.findViewById(view, R.id.list);

        List<Map<String, String>> data = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", "item " + i);
            data.add(map);
        }

//        listView.setAdapter(new SimpleAdapter(getContext(), data, android.R.layout.simple_list_item_1, new String[]{"title"}, new int[]{android.R.id.text1}));

        listView.setAdapter(new MyAdapter());
        return view;
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 20;
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

            TextView textView  = new TextView(getContext());

            textView.setText(getString(R.string.app_name) + ", item " + position);

            return textView;
        }
    }

}
