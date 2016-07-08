package com.hanbing.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.hanbing.sample.menu.SampleMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = new ListView(this);

        final List<Map<String, Object>> data = new ArrayList<>();

        {
            Map<String, Object> map = new HashMap<>();
            map.put("title", "Menu");
            map.put("class", SampleMenu.class);
            data.add(map);
        }

        listView.setAdapter(new SimpleAdapter(this, data, android.R.layout.simple_list_item_1, new String[]{"title"}, new int[]{android.R.id.text1}));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(getApplicationContext(), (Class<?>) data.get(position).get("class")));
            }
        });

        setContentView(listView);

    }




}
