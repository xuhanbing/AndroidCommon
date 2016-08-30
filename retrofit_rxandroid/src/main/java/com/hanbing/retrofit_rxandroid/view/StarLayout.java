package com.hanbing.retrofit_rxandroid.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hanbing.library.android.adapter.BaseAdapter;
import com.hanbing.library.android.adapter.ViewHolder;
import com.hanbing.library.android.util.SystemUtils;
import com.hanbing.library.android.view.list.FullHeightGridView;
import com.hanbing.retrofit_rxandroid.R;
import com.hanbing.retrofit_rxandroid.bean.Star;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanbing on 2016/8/30
 */
public class StarLayout extends HorizontalScrollView {


    LinearLayout mLinearLayout;

    public StarLayout(Context context) {
        super(context);
        init();
    }

    public StarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
//
//    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//    public StarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    private void init(){
        mLinearLayout = new LinearLayout(getContext());
        addView(mLinearLayout);

        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
    }

    List<Star> mStars;

    public void setStars(List<Star> stars) {
        mStars = stars;

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),HORIZONTAL, false);
//
//        setLayoutManager(layoutManager);
//
//        setAdapter(new Adapter());

        mLinearLayout.removeAllViews();

        Adapter adapter = new Adapter();
        if (adapter.getItemCount() > 0) {

            for (int i = 0; i < adapter.getItemCount(); i++) {

                ViewHolder viewHolder = adapter.onCreateViewHolder(mLinearLayout, 0);

                adapter.onBindViewHolder(viewHolder, i);

                mLinearLayout.addView(viewHolder.mItemView);
            }
        }

    }

    static final int LAYOUT_ID = R.layout.item_subject_star;

    static class ViewHolder extends com.hanbing.library.android.adapter.ViewHolder{
        @BindView(R.id.avatar_iv)
        ImageView mAvatarIv;
        @BindView(R.id.name_tv)
        TextView mNameTv;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class Adapter extends BaseAdapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(LAYOUT_ID, parent, false));
        }

        @Override
        public void onBindViewHolder(StarLayout.ViewHolder holder, int position) {

            final Star star = mStars.get(position);

            if (null != star.getAvatars())
            Glide.with(getContext()).load(star.getAvatars().get()).into(holder.mAvatarIv);

            holder.mNameTv.setText(star.getName());
            holder.mAvatarIv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemUtils.openUrl(getContext(), star.getAlt());
                }
            });
        }

        @Override
        public int getItemCount() {
            return null == mStars ? 0 : mStars.size();
        }

    }
}
