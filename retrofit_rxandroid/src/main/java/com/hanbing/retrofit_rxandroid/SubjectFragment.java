package com.hanbing.retrofit_rxandroid;

import android.app.ProgressDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hanbing.library.android.fragment.list.RecyclerViewFragment;
import com.hanbing.library.android.tool.PagingManager;
import com.hanbing.library.android.util.SystemUtils;
import com.hanbing.library.android.view.recycler.animator.SlideInRightItemAnimator;
import com.hanbing.retrofit_rxandroid.bean.Data;
import com.hanbing.retrofit_rxandroid.bean.Subject;
import com.hanbing.retrofit_rxandroid.view.StarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

/**
 * Created by hanbing on 2016/8/30
 */
public class SubjectFragment extends RecyclerViewFragment {




    List<Subject> mSubjects = new ArrayList<>();
    private Adapter mAdapter;


    @Override
    protected void initRecyclerView(RecyclerView recyclerView) {
//        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        recyclerView.setItemAnimator(new SlideInRightItemAnimator());

        recyclerView.setAdapter(createAdapter());
    }

    @Override
    public PagingManager createPagingManager() {
        return new PagingManager(20, true);
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return null == mAdapter ?  mAdapter = new Adapter() : mAdapter;
    }


    @Override
    public void onLoadData(final boolean isRefresh, final int pageIndex, int pageSize) {
//        RetrofitClient.getApiService().getSubjects(pageIndex * pageSize, pageSize).enqueue(new Callback<Data>() {
//            @Override
//            public void onResponse(Call<Data> call, Response<Data> response) {
//                Data data = response.body();
//
//                onSuccess(data);
//            }
//
//            @Override
//            public void onFailure(Call<Data> call, Throwable t) {
//                Toast.makeText(getContext(), "获取失败", Toast.LENGTH_SHORT).show();
//                onLoadFailure("");
//            }
//        });

        RetrofitClient.getApiService().getSubjects((pageIndex) * pageSize, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                        String msg = "";

                        PagingManager pagingManager = getPagingManager();
                        if (pagingManager.isMaxCountAvaiable()) {
                            msg =  "请求第" + (pageIndex + 1) + "页的数据，共" + pagingManager.getMaxCount()
                                    + "条，" + pagingManager.getMaxPageCount() + "页";
                        } else {
                            msg = "请求数据";
                        }
                        showProgress(msg);
                    }
                })
                .subscribe(new Subscriber<Data>() {
                    @Override
                    public void onCompleted() {
                        hideProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideProgress();
                    }

                    @Override
                    public void onNext(Data data) {
                        onSuccess(data);
                    }
                })

        ;

    }

    ProgressDialog mProgressDialog = null;

    private void showProgress(String msg) {
        hideProgress();

        mProgressDialog = new ProgressDialog(getContext());

        mProgressDialog.setMessage(msg);

        mProgressDialog.show();
    }

    private void hideProgress() {
        if (null != mProgressDialog)
        {
            mProgressDialog.dismiss();
        }
    }

    private void onSuccess(Data data) {
        if (getPagingManager().isRefresh())
        {
            mSubjects.clear();
            mAdapter.notifyDataSetChanged();
        }

        if (null != data) {
            List<Subject> subjects = data.getSubjects();
            getPagingManager().setMaxCount(data.getTotal());

            if (null != subjects && subjects.size() > 0)
            {
                int count = mSubjects.size();
                mSubjects.addAll(subjects);
//                        mAdapter.notifyDataSetChanged();
                mAdapter.notifyItemRangeInserted(count, subjects.size());

                onLoadSuccess();
                return;
            }

        }

        onLoadSuccessNoData();
    }


    public static final int LAYOUT_ID = R.layout.item_subject;


    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.picture_iv)
        ImageView mPictureIv;
        @BindView(R.id.title_tv)
        TextView mTitleTv;
        @BindView(R.id.score_tv)
        TextView mScoreTv;
        @BindView(R.id.directorLayout)
        StarLayout mDirectorLayout;
        @BindView(R.id.castLayout)
        StarLayout mCastLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(LAYOUT_ID, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final Subject subject = mSubjects.get(position);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemUtils.openUrl(getContext(), subject.getAlt());
                }
            });

            holder.mTitleTv.setText(subject.getTitle());
            holder.mScoreTv.setText("平均分：" +subject.getRating().getAverage()+"");
            Glide.with(getContext()).load(subject.getImages().get()).into(holder.mPictureIv);
            holder.mDirectorLayout.setStars(subject.getDirectors());
            holder.mCastLayout.setStars(subject.getCasts());
        }

        @Override
        public int getItemCount() {
            return null == mSubjects ? 0 : mSubjects.size();
        }
    }
}
