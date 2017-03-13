package com.hanbing.demo.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hanbing.demo.R;
import com.hanbing.library.android.util.LogUtils;
import com.hanbing.library.android.util.ValueUtils;
import com.hanbing.library.android.view.recycler.decoration.GridItemDecoration;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestImageLoaderActivity extends AppCompatActivity {


    @BindView(R.id.spinner)
    Spinner mSpinner;
    @BindView(R.id.listView)
    ListView mListView;

    int index = 0;

    public static final String[] IMAGE_URLS = {
            "drawable://" + R.drawable.a,
            "file:///storage/emulated/0/Download/1.png",
            "assets://b.jpg",
            "http://m2.quanjing.com/2m/fod_liv002/fo-11171537.jpg",
//            "http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg",
//            "http://pic56.nipic.com/file/20141227/19674963_215052431000_2.jpg",
//            "http://img3.redocn.com/tupian/20150430/mantenghuawenmodianshiliangbeijing_3924704.jpg",
            "http://qq.yh31.com/tp/zjbq/201611131154402229.gif",

//            "http://img15.3lian.com/2015/f2/50/d/71.jpg",
//            "http://img4.imgtn.bdimg.com/it/u=3737218198,1821201454&fm=21&gp=0.jpg",
//            "http://imgsrc.baidu.com/forum/pic/item/57eab1119313b07e6acd7dfd0cd7912396dd8cef.jpg",
//            "http://image60.360doc.com/DownloadImg/2013/04/1613/31674132_19.jpg",
//            "http://imgsrc.baidu.com/forum/pic/item/40a99f510fb30f24ea06dd88c895d143ac4b03b8.jpg",
//            "http://image60.360doc.com/DownloadImg/2013/04/1613/31674132_11.jpg",
//            "http://e.hiphotos.baidu.com/zhidao/pic/item/38dbb6fd5266d01695ab94bf952bd40734fa35f2.jpg",


    };
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image_loader);
        ButterKnife.bind(this);


        CopyOnWriteArrayList arrayList;



        int padding = 10;
        mRecyclerView.setPadding(padding, padding, padding, padding);
        mRecyclerView.setBackgroundColor(Color.BLACK);
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addItemDecoration(new GridItemDecoration.Builder(this).setColor(Color.BLACK).setSize(padding).create());

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = position;

                refresh();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        refresh();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Glide.get(this).clearMemory();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public View mImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView =  itemView;
        }
    }

    private void refresh() {

        if (0 == index)
            mRecyclerView.setAdapter(null);


        mRecyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

//                ImageView imageView = new SimpleDraweeView(getApplicationContext());
//                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);


                View view = new TextView(getApplicationContext());
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

                ViewGroup.LayoutParams layoutParams = holder.mImageView.getLayoutParams();
                layoutParams.width = getWidth(position);
                layoutParams.height = getHeight(position);
                holder.mImageView.setLayoutParams(layoutParams);
                holder.mImageView.setBackgroundColor(Color.GRAY);

                String url = IMAGE_URLS[position % IMAGE_URLS.length];
                displayImage(url, holder.mImageView);

            }

            @Override
            public int getItemCount() {
                return IMAGE_URLS.length;
            }

            private int getWidth(int position) {
                return ViewGroup.LayoutParams.MATCH_PARENT;
            }

            private int getHeight(int position) {
//                return position % 4 * 200 + 200;
                return 500;
            }
        });

//        if (0 == index)
//            mListView.setAdapter(null);
//        mListView.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return 20;
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//
//                if (null == convertView) {
//                    convertView = new SimpleDraweeView(getApplicationContext());
//                    convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400));
//                }
//
//                ImageView imageView = (ImageView) convertView;
//                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                imageView.setImageBitmap(null);
//
//                displayImage(IMAGE_URLS[position % IMAGE_URLS.length], imageView);
//
//                return convertView;
//            }
//        });


    }


    int URL = 0;
    int FILE = 1;
    int DRAWABLE = 2;
    int ASSETS = 3;


    public void displayImage(String string, View imageView) {



        String uri = string;
        int tag = URL;

        if (uri.startsWith("file://")) {
            uri = uri.replace("file://", "");
            tag = FILE;
        } else if (uri.startsWith("drawable://")) {
            uri = uri.replace("drawable://", "");
            tag = DRAWABLE;

        } else if (uri.startsWith("assets://")) {
            uri = uri.replace("assets://", "");
            tag = ASSETS;

        }

        switch (index - 1) {
            case 0:
//                if (FILE == tag)
//                    uri = ImageDownloader.Scheme.FILE.wrap(uri);
//                else if (DRAWABLE == tag)
//                    uri = ImageDownloader.Scheme.DRAWABLE.wrap(uri);
//                else if (ASSETS == tag)
//                    uri = ImageDownloader.Scheme.ASSETS.wrap(uri);

//                ImageLoader.getInstance().displayImage(uri, imageView);




                if (DRAWABLE == tag)
                {
                    com.hanbing.demo.ImageLoader.getInstance().displayImageResSync(Integer.valueOf(uri), imageView);
                }
                else if (ASSETS == tag)
                {
                    com.hanbing.demo.ImageLoader.getInstance().displayImageAssetsSync(uri, imageView);
                }
                else {
                    com.hanbing.demo.ImageLoader.getInstance().displayImageSync(uri, imageView);
                }

                break;
            case 1:

            {
                Picasso with = Picasso.with(this);
                RequestCreator requestCreator = null;
                if (FILE == tag)
                    requestCreator = with.load(new File(uri));
                else if (DRAWABLE == tag)
                    requestCreator = with.load(ValueUtils.intValueOf(uri));
                else if (ASSETS == tag) {
                    requestCreator = with.load(stringToUri(string));
                } else
                    requestCreator = with.load(uri);

                requestCreator.transform(new Transformation() {
                    @Override
                    public Bitmap transform(Bitmap source) {
                        return null;
                    }

                    @Override
                    public String key() {
                        return null;
                    }
                }).error(R.mipmap.ic_launcher).into((ImageView) imageView);
            }

            break;
            case 2:

            {
                RequestManager with = Glide.with(this);

                Glide.get(this).register(InputStream.class, InputStream.class, new ModelLoaderFactory<InputStream, InputStream>() {
                    @Override
                    public ModelLoader<InputStream, InputStream> build(Context context, GenericLoaderFactory factories) {
                        return new ModelLoader<InputStream, InputStream>() {
                            @Override
                            public DataFetcher<InputStream> getResourceFetcher(final InputStream model, int width, int height) {
                                return new DataFetcher<InputStream>() {
                                    @Override
                                    public InputStream loadData(Priority priority) throws Exception {
                                        LogUtils.e("load data");
                                        Thread.sleep(5000);
                                        return model;
                                    }

                                    @Override
                                    public void cleanup() {
                                        LogUtils.e("cleanup");

                                        if (null != model )
                                            try {
                                                model.close();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                    }

                                    @Override
                                    public String getId() {
                                        LogUtils.e("getId" );

                                        return "InputStream" + System.currentTimeMillis();
                                    }

                                    @Override
                                    public void cancel() {
                                        LogUtils.e("cancel");
                                    }
                                };
                            }
                        };
                    }

                    @Override
                    public void teardown() {

                    }
                });
                DrawableTypeRequest requestCreator = null;
                if (FILE == tag)
                    requestCreator = with.load(new File(uri));
                else if (DRAWABLE == tag)
                    requestCreator = with.load(ValueUtils.intValueOf(uri));
                else if (ASSETS == tag)
                {
//                    requestCreator = with.load(stringToUri(string));
                    try {
                        InputStream inputStream = getAssets().open(uri);
                        requestCreator =  with.load(inputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                    requestCreator = with.load(uri);


                requestCreator.error(R.mipmap.ic_launcher).into((ImageView) imageView);
            }
            break;
            case 3:


            {

//                Uri uri1 = Uri.parse(uri);
//
//                RequestManager with = Glide.with(this);
//                DrawableTypeRequest requestCreator = null;
//                if (FILE == tag)
//                    uri1 = Uri.fromFile(new File(uri));
//                else if (DRAWABLE == tag)
//                {
//                    uri = "res://" +  getPackageName() +
//                            "/" + ValueUtils.intValueOf(uri);
//                    uri1 = Uri.parse(uri);
//                }
//                else if (ASSETS == tag)
//                {
//                    uri = "assets://" + uri;
//                    uri1 = Uri.parse(uri);
//
//                }


                SimpleDraweeView simpleDraweeView = (SimpleDraweeView) imageView;


                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setAutoPlayAnimations(true)
                        .setOldController(simpleDraweeView.getController())
                        .build();
                simpleDraweeView.setController(controller);

                simpleDraweeView.setImageURI(stringToUri(uri));

            }
            break;


        }

    }


    private Uri stringToUri(String string) {

        Uri uri = null;
        if (string.startsWith("file://")) {
            string = string.replace("file://", "");
            uri = Uri.fromFile(new File(string));
        } else if (string.startsWith("drawable://")) {
            string = string.replace("drawable://", "");
            uri = Uri.parse("res://" + getPackageName() + "/" + ValueUtils.intValueOf(string));
        } else if (string.startsWith("assets://")) {
            string = string.replace("assets://", "");
            uri = Uri.parse("file:///android_asset/" + string);
        } else {
            uri = Uri.parse(string);
        }


        return uri;
    }
}
