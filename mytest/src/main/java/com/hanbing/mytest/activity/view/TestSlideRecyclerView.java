package com.hanbing.mytest.activity.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hanbing.library.android.view.recycler.OnItemClickListener;
import com.hanbing.library.android.view.recycler.OnItemLongClickListener;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.library.android.view.plugin.DrawerItemWrapper;
import com.hanbing.library.android.view.recycler.DrawerRecyclerView;

public class TestSlideRecyclerView extends AppCompatActivity {

    DrawerRecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_test_slide_recycler_view);

        {
            DrawerRecyclerView recyclerView = new DrawerRecyclerView(this);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new MyAdapter());
            ViewUtils.bindOnItemClickListener(recyclerView, new OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView recyclerView, View view, int position) {

                    LogUtils.e("onItemClick " + position);
                }
            }, new OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(RecyclerView recyclerView, View view, int position) {
                    LogUtils.e("onItemLongClick " + position);
                    return true;
                }
            });

            mRecyclerView = recyclerView;
        }

        setContentView(mRecyclerView);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<ViewHolder> implements DrawerItemWrapper.Adapter
    {



        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder( LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_slidelistview, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            View convertView = holder.itemView;

            TextView text = (TextView) convertView.findViewById(R.id.tv_title);
            text.setText("Position " + position);
            text.setTextSize(40);
            text.setTextColor(Color.BLACK);
            text.setBackgroundColor(Color.YELLOW);

            TextView textExtra = (TextView) convertView.findViewById(R.id.tv_menu1);
            textExtra.setText("Option " + position);
            textExtra.setTextSize(40);
            textExtra.setBackgroundColor(Color.RED);
            textExtra.setTextColor(Color.GREEN);
            textExtra.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Toast.makeText(getApplicationContext(), "click option " + position, Toast.LENGTH_SHORT).show();
                    mRecyclerView.closeDrawer();

                }
            });
            textExtra.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // TODO Auto-generated method stub
                    Log.e("", "touch " + position + ",action=" + event.getAction());
                    return false;
                }
            });
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        @Override
        public int getMaxScroll(int position) {
            View child = mRecyclerView.getLayoutManager().findViewByPosition(position);
            if (null != child)
            {
                View menu = ViewUtils.findViewById(child, R.id.layout_menu);
                return menu.getMeasuredWidth();
            }

            return 0;
        }

    }
}
