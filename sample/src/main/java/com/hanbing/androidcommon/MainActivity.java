package com.hanbing.androidcommon;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.common.base.BaseAppCompatActivity;
import com.common.base.BaseRecycleViewAdaper;
import com.common.listener.OnItemClickListener;
import com.common.util.SystemUtils;
import com.common.util.ToastUtils;
import com.common.util.ViewUtils;
import com.common.widget.recyclerview.animator.BaseSimpleItemAnimator;
import com.common.widget.recyclerview.animator.FadeInItemAnimator;
import com.common.widget.recyclerview.animator.FlipInBottomItemAnimator;
import com.common.widget.recyclerview.animator.FlipInLeftItemAnimator;
import com.common.widget.recyclerview.animator.FlipInRightItemAnimator;
import com.common.widget.recyclerview.animator.FlipInTopItemAnimator;
import com.common.widget.recyclerview.animator.LandingItemAnimator;
import com.common.widget.recyclerview.animator.RotateItemAnimator;
import com.common.widget.recyclerview.animator.ScaleInBottomItemAnimator;
import com.common.widget.recyclerview.animator.ScaleInCenterItemAnimator;
import com.common.widget.recyclerview.animator.ScaleInLeftItemAnimator;
import com.common.widget.recyclerview.animator.ScaleInRightItemAnimator;
import com.common.widget.recyclerview.animator.ScaleInTopItemAnimator;
import com.common.widget.recyclerview.animator.SlideInBottomItemAnimator;
import com.common.widget.recyclerview.animator.SlideInRightItemAnimator;
import com.common.widget.recyclerview.animator.SlideInTopItemAnimator;
import com.common.widget.recyclerview.decoration.BaseItemDecoration;
import com.common.widget.recyclerview.decoration.GridItemDecoration;
import com.common.widget.recyclerview.decoration.LineItemDecoration;
import com.common.widget.recyclerview.animator.SlideInLeftItemAnimator;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


@ContentView(R.layout.activity_main)
public class MainActivity extends BaseAppCompatActivity {

    static  final int TYPE_0 = 0;
    static final int TYPE_1 = 1;
    static final int TYPE_2 = 2;

    @ViewInject(R.id.rg_orientation)
    RadioGroup oriention;

    @ViewInject(R.id.rg_style)
    RadioGroup style;

    @ViewInject(R.id.recycleView)
    RecyclerView recyclerView;



    @ViewInject(R.id.sb_item_count)
    SeekBar itemCountSeekBar;

    @ViewInject(R.id.tv_item_count)
    TextView itemCountTextView;

    @ViewInject(R.id.spinner)
    Spinner spinner;

    @ViewInject(R.id.tv_animator)
    TextView animator;

    @Event(value = {R.id.rg_orientation, R.id.rg_style}, type = RadioGroup.OnCheckedChangeListener.class)
    private void onCheckedChange(RadioGroup group, int checkedId) {
        if (group.getId() == R.id.rg_style)
        {

            switch (checkedId)
            {
                case R.id.rb_linear:
                {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    linearLayoutManager.setOrientation(horizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL);
                    layoutManager = linearLayoutManager;
                }
                    break;
                case R.id.rb_grid:
                {
                    GridLayoutManager linearLayoutManager = new GridLayoutManager(this, spanCount);
                    linearLayoutManager.setOrientation(horizontal ? GridLayoutManager.HORIZONTAL : GridLayoutManager.VERTICAL);
                    layoutManager = linearLayoutManager;
                }
                    break;
                case R.id.rb_staggered:
                {
                    StaggeredGridLayoutManager linearLayoutManager = new StaggeredGridLayoutManager(spanCount,
                            horizontal ? StaggeredGridLayoutManager.HORIZONTAL : StaggeredGridLayoutManager.VERTICAL);
                    layoutManager = linearLayoutManager;
                }
                    break;
            }
        } else {

            horizontal = checkedId == R.id.rb_horizontal;
            if (null != layoutManager)
            {
                if (layoutManager instanceof  LinearLayoutManager)
                {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    linearLayoutManager.setOrientation(horizontal ? LinearLayoutManager.HORIZONTAL : LinearLayoutManager.VERTICAL);
                    type = TYPE_0;
                }

                if (layoutManager instanceof  GridLayoutManager)
                {
                    GridLayoutManager linearLayoutManager = (GridLayoutManager) layoutManager;
                    linearLayoutManager.setOrientation(horizontal ? GridLayoutManager.HORIZONTAL : GridLayoutManager.VERTICAL);
                    type = TYPE_1;
                }

                if (layoutManager instanceof  StaggeredGridLayoutManager)
                {
                    StaggeredGridLayoutManager linearLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    linearLayoutManager.setOrientation(horizontal ? StaggeredGridLayoutManager.HORIZONTAL : StaggeredGridLayoutManager.VERTICAL);
                    type = TYPE_2;
                }
            }
        }


        addItemDecoration(horizontal ? OrientationHelper.HORIZONTAL : OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Event(value= R.id.sb_item_count, type = SeekBar.OnSeekBarChangeListener.class, method = "onStopTrackingTouch")
    private  void onStopTrackingTouch(SeekBar seekBar)
    {

        itemCount = seekBar.getProgress();
        itemCountTextView.setText(""+seekBar.getProgress());

        reset();
    }


    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    int itemCount = 50;
    int spanCount = 4;

    static int addItemCount = 0;

    boolean horizontal = false;
    int type = TYPE_0;

    List<String> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        itemCountSeekBar.setProgress(itemCount);
        itemCountTextView.setText(itemCount+"");

//        RecyclerView recyclerView = ViewUtils.findViewById(this, R.id.recycleView);

//        if (recyclerView instanceof RecyclerViewWithHeaderOrFooter)
//        {
//            RecyclerViewWithHeaderOrFooter rv = (RecyclerViewWithHeaderOrFooter) recyclerView;
//
//
//            View view1 = LayoutInflater.from(this).inflate(R.layout.item_recycleview, null);
//            View view2 = LayoutInflater.from(this).inflate(R.layout.item_recycleview, null);
//            View view3 = LayoutInflater.from(this).inflate(R.layout.item_recycleview, null);
//            View view4 = LayoutInflater.from(this).inflate(R.layout.item_recycleview, null);
//
//
//            rv.addHeaderView(view1);
//            rv.addHeaderView(view2);
//
//            rv.addFooterView(view3);
//            rv.addFooterView(view4);
//
//            view1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    ToastUtils.showToast(getApplicationContext(), "click view1");
//                }
//
//            });
//        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);

        GridLayoutManager gridLayoutManager  = new GridLayoutManager(this, spanCount);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        StaggeredGridLayoutManager staggeredGridLayoutManager2 = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);


        layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new SlideInTopItemAnimator());


//        addItemDecoration(OrientationHelper.VERTICAL);


        final BaseRecycleViewAdaper adaper = new BaseRecycleViewAdaper<ViewHolder>() {



            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onBindViewHolderImpl(ViewHolder holder, int position) {


                int[] res = {R.drawable.logo_custom_58, R.drawable.logo_custom_oppo,
                        R.drawable.logo_custom_pingan, R.drawable.logo_custom_qner, R.drawable.logo_custom_qq,
                        R.drawable.logo_custom_sina, R.drawable.logo_custom_tmall, R.drawable.logo_custom_volvo};
                int[] colors = {R.color.lightblue, R.color.lightgray, R.color.lightsalmon};
                int color = getResources().getColor(colors[position % colors.length]);


//                holder.imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                holder.imageView.setImageResource(res[position % res.length]);
//                holder.imageView.setBackgroundColor(getResources().getColor(color));

//                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(holder.imageView, "alpha", 0.1f, 1.0f);
//                objectAnimator.setDuration(1000);
//
//                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(holder.imageView, "scaleX", 0.5f, 1.0f);
//                objectAnimator1.setDuration(1000);
//
//                ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(holder.imageView, "scaleY", 0.5f, 1.0f);
//                objectAnimator2.setDuration(1000);
//
//                PropertyValuesHolder propertyValuesHolder1 = PropertyValuesHolder.ofFloat("alpha",  0.1f, 1.0f);
//                PropertyValuesHolder propertyValuesHolder2 = PropertyValuesHolder.ofFloat("scaleX",  0.1f, 1.0f);
//                PropertyValuesHolder propertyValuesHolder3 = PropertyValuesHolder.ofFloat("scaleY",  0.1f, 1.0f);
//                PropertyValuesHolder propertyValuesHolder4 = PropertyValuesHolder.ofFloat("translationX", -100f, 0);
//
//
//                PropertyValuesHolder[] propertyValuesHolders = {propertyValuesHolder1, propertyValuesHolder2, propertyValuesHolder3, propertyValuesHolder4};
//                objectAnimator = ObjectAnimator.ofPropertyValuesHolder(holder.imageView, propertyValuesHolders);
//
//                objectAnimator.setDuration(1000);
//                objectAnimator.start();

//                AnimatorSet set = new AnimatorSet();
//                set.playTogether(objectAnimator, objectAnimator1, objectAnimator2);
//
//                set.start();

                TextView textView = holder.textView;

                textView.setGravity(Gravity.CENTER);
                textView.setText(items.get(position));



//                if (layoutManager instanceof StaggeredGridLayoutManager)
//                {
//                    int size = (int) (200 * Math.random() + 200);
//
//                    if (horizontal)
//                        textView.setMinWidth(size);
//                    else
//                        textView.setMinHeight(size);
//                } else {
//
//                    textView.setMinWidth(400);
//                    textView.setMinWidth(400);
//                }

                holder.itemView.setMinimumWidth(200);
                holder.itemView.setMinimumHeight(200);

                holder.itemView.setBackgroundColor(color);
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                return new ViewHolder(getLayoutInflater().inflate(R.layout.item_recycleview, parent, false));
            }

            @Override
            public int getItemCount() {
                return null == items ? 0 : items.size();
            }



        };

        ViewUtils.bindOnItemClickListener(recyclerView, new OnItemClickListener(){


            @Override
            public void onItemClick(RecyclerView recyclerView, View view, int position) {
                ToastUtils.showToast(getApplicationContext(), "onItemClick " + position);

                String title = "add item " + addItemCount++;
                items.add(position + 1, title);

                recyclerView.getAdapter().notifyItemInserted(position+1);
            }

            @Override
            public void onItemLongClick(RecyclerView recyclerView, View view, int position) {
                ToastUtils.showToast(getApplicationContext(), "onItemLongClick " + position);
                items.remove(position);

                recyclerView.getAdapter().notifyItemRemoved(position);
            }
        });

//        adaper.setOnItemClickListener(new BaseRecycleViewAdaper.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position, long id) {
//                ToastUtils.showToast(getApplicationContext(), "onItemClick " + position);
//
//                String title = items.get(position) + "-a";
//                items.add(position + 1, title);
//
//                recyclerView.getAdapter().notifyItemInserted(position+1);
//            }
//
//            @Override
//            public boolean onItemLongClick(View view, int position, long id) {
//                ToastUtils.showToast(getApplicationContext(), "onItemLongClick " + position);
//                items.remove(position);
//
//                recyclerView.getAdapter().notifyItemRemoved(position);
//                return true;
//            }
//        });

        recyclerView.setAdapter(adaper);


        initSpinner();


        reset();


    }



    RecyclerView.ItemAnimator itemAnimator;
    private void initSpinner() {


        final List<Class<? extends  RecyclerView.ItemAnimator>> list = new ArrayList<>();
        list.add(FadeInItemAnimator.class);
        list.add(SlideInLeftItemAnimator.class);
        list.add(SlideInRightItemAnimator.class);
        list.add(SlideInTopItemAnimator.class);
        list.add(SlideInBottomItemAnimator.class);
        list.add(ScaleInCenterItemAnimator.class);
        list.add(ScaleInLeftItemAnimator.class);
        list.add(ScaleInRightItemAnimator.class);
        list.add(ScaleInTopItemAnimator.class);
        list.add(ScaleInBottomItemAnimator.class);
        list.add(FlipInLeftItemAnimator.class);
        list.add(FlipInRightItemAnimator.class);
        list.add(FlipInTopItemAnimator.class);
        list.add(FlipInBottomItemAnimator.class);
        list.add(LandingItemAnimator.class);
        list.add(RotateItemAnimator.class);


        spinner.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return list.size();
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
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                TextView textView = new TextView(getApplicationContext());

                textView.setText(list.get(position).getSimpleName());

                return textView;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Class<? extends RecyclerView.ItemAnimator> clazz = list.get(position);

                try {
                    itemAnimator = clazz.newInstance();
                    recyclerView.setItemAnimator(itemAnimator);

                    animator.setText(clazz.getSimpleName());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);

        animator.setText(BaseSimpleItemAnimator.class.getSimpleName());

    }

    RecyclerView.ItemDecoration itemDecoration;
    private void addItemDecoration(int orientation) {

        BaseItemDecoration.Builder builder = null;

        switch (type)
        {
            case TYPE_0:
                builder = new LineItemDecoration.Builder(this);
                break;
            case TYPE_1:
            case TYPE_2:
                builder = new GridItemDecoration.Builder(this);
                break;

        }
        builder.setColor(Color.GREEN)
                .setDrawableRes(R.drawable.logo_custom_sina)
                .setOrientation(orientation).setSize(10);


        if (null != itemDecoration)
        recyclerView.removeItemDecoration(itemDecoration);
        recyclerView.addItemDecoration(itemDecoration=builder.create());

    }

    public void initItems()
    {
        items = new ArrayList<>();
        for (int i = 0; i < itemCount; i++)
        {
            items.add("item " + i);
        }
    }
    public void reset()
    {
        initItems();

        addItemDecoration(horizontal ? OrientationHelper.HORIZONTAL : OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share_text) {
//            SystemUtils.share(this, "this is title", null, "this is content");
            SystemUtils.mail(this, "xu_han_bing@163.com", "title", "subject", "this is a mail");
            return true;
        } else  if (id == R.id.action_share_image) {
            SystemUtils.shareImage(this, "this is image", "this is image sub", "/sdcard/1.jpg");
            return true;
        } else  if (id == R.id.action_share_file) {
            SystemUtils.shareFile(this, "this is file", "this is file sub", "/sdcard/1.txt");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);

            textView = ViewUtils.findViewById(itemView, R.id.tv_title);
        }
    }



    class MyItemAnimator extends DefaultItemAnimator {

    }
}
