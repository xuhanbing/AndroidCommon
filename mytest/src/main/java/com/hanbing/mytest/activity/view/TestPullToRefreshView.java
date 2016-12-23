/**
 *
 */
package com.hanbing.mytest.activity.view;

import com.hanbing.library.android.util.ViewUtils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * @author hanbing
 * @date 2015-7-22
 */
public class TestPullToRefreshView extends Activity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);


//        setContentView(R.layout.activity_pulltorefresh);
//
//        PtrFrameLayout layout = ViewUtils.findViewById(this, R.id.store_house_ptr_frame);
//
//        TextView textView = new TextView(this);
//        textView.setText("Header");
//
//
//        layout.setHeaderView(textView);


        PtrFrameLayout layout = null;
        View view = ViewUtils.inflate(this, R.layout.activity_pulltorefresh, null, false);

        list = new ListView(this);

        final BaseAdapter adapter = new MyAdapter();
        layout = ViewUtils.findViewById(view, R.id.store_house_ptr_frame);


        StoreHouseHeader header = new StoreHouseHeader(this);
        header.setPadding(0, 50, 0, 0);
        header.initWithString("Hello my boy");
        header.setTextColor(Color.WHITE);
        header.setScale(2);
        header.setLoadingAniDuration(5000);

        final LinearLayout linearLayout = ViewUtils.findViewById(view, R.id.store_house_ptr_image_content);

        linearLayout.addView(list);

        layout.setHeaderView(header);
        layout.addPtrUIHandler(header);
        layout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);

                if (0 == list.getFirstVisiblePosition())
                {
                    View child = list.getChildAt(0);
                    return child.getTop() >= list.getPaddingTop();
                }
                return false;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        count += 10;
                        adapter.notifyDataSetChanged();

                        frame.refreshComplete();

                    }
                }, 3000);
            }
        });
        PtrFrameLayout mPtrFrame = layout;


        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
// default is false
        mPtrFrame.setPullToRefresh(false);
// default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);


        for (int i = 0; i < 1; i++) {
            TextView text = new TextView(this);
            text.setText("this is header " + i);
            text.setTextSize(25);
            text.setBackgroundColor(Color.WHITE);
            text.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

            list.addHeaderView(text);
        }


        list.setDivider(new ColorDrawable(Color.GREEN));
        list.setDividerHeight(10);
        list.setAdapter(adapter);
        list.setHeaderDividersEnabled(false);

        list.setBackgroundColor(Color.RED);


        setContentView(view);
    }

    int count = 10;

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return count;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub

            ImageView text = new ImageView(getApplicationContext());

//	    text.setText("Title " + position);
//	    text.setTextSize(25);
//	    text.setTextColor(Color.RED);
            text.setImageResource(R.drawable.a);
            text.setBackgroundColor(Color.GRAY);
            text.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, 400));
            return text;
        }

    }
}
