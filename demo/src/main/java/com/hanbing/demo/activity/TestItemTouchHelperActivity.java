package com.hanbing.demo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanbing.demo.BaseActivity;
import com.hanbing.demo.R;
import com.hanbing.library.android.adapter.BaseRecyclerViewAdapter;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ViewUtils;
import com.hanbing.library.android.view.recycler.OnItemClickListener;
import com.hanbing.library.android.view.recycler.OnItemLongClickListener;
import com.hanbing.library.android.view.recycler.Utils;
import com.hanbing.library.android.view.recycler.decoration.LineItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestItemTouchHelperActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_item_touch_helper);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);


        final DefaultAdapter2 adapter = new DefaultAdapter2(20);
        recyclerView.setAdapter(adapter);

        Utils.bindOrReplaceListener(recyclerView, new OnItemClickListener() {
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
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                int dragFlags = makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, ItemTouchHelper.UP | ItemTouchHelper.DOWN );
                int swipeFlags = makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.START | ItemTouchHelper.END);

                int adapterPosition = viewHolder.getAdapterPosition();

                if (0 == adapterPosition)
                    return 0;
                if (1 == adapterPosition)
                    return makeMovementFlags(dragFlags, 0);
                else if (2 == adapterPosition)
                    return makeMovementFlags(0, swipeFlags);
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                LogUtils.e("onMove current = " + viewHolder.getAdapterPosition() + ", target = " + target.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;

            }


            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                LogUtils.e("onSwiped current = " + viewHolder.getAdapterPosition() + ", direction = " + direction);


                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        }) {

        };


        itemTouchHelper.attachToRecyclerView(recyclerView);




//        recyclerView.addItemDecoration(new LineItemDecoration.Builder(this)
//                .setSize(2)
//                .setColor(Color.RED)
//                .create()
//        );
    }


    public class DefaultAdapter2 extends BaseRecyclerViewAdapter<ViewHolder> {

        List<String> mList;

        public DefaultAdapter2() {
            this(20);
        }

        public DefaultAdapter2(int count) {
            mList = new ArrayList<>();

            for (int i = 0; i < count; i++) {
                mList.add("Item " + i);
            }

            registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    super.onItemRangeChanged(positionStart, itemCount);
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
                    super.onItemRangeChanged(positionStart, itemCount, payload);
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    super.onItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
                    Collections.swap(mList, fromPosition, toPosition);
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    mList.remove(positionStart);
                }
            });
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View convertView = ViewUtils.inflate(parent.getContext(), android.R.layout.simple_list_item_1, parent, false);
            return new ViewHolder(convertView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TextView title = ViewUtils.findViewById(holder.itemView, android.R.id.text1);
            title.setTextColor(Color.BLACK);
            title.setBackgroundColor(position % 2 == 0 ? Color.GRAY : Color.LTGRAY);
            title.setText(mList.get(position));

        }


        @Override
        public int getItemCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }




    }

    class ViewHolder extends com.hanbing.library.android.adapter.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
