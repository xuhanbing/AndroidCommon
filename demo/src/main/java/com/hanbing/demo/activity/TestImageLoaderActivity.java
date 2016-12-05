package com.hanbing.demo.activity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hanbing.demo.R;
import com.hanbing.library.android.util.ValueUtils;
import com.hanbing.library.android.view.recycler.decoration.GridItemDecoration;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestImageLoaderActivity extends AppCompatActivity {


    @BindView(R.id.spinner)
    Spinner mSpinner;
    @BindView(R.id.listView)
    ListView mListView;

    int index = 0;

    public static final String[] IMAGE_URLS = {
//            "drawable://" + R.drawable.a,
//            "file:///storage/emulated/0/Download/1.png",
//            "assets://b.jpg",
//            "null",
//            "http://m2.quanjing.com/2m/fod_liv002/fo-11171537.jpg",
//            "http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg",
//            "http://pic56.nipic.com/file/20141227/19674963_215052431000_2.jpg",
//            "http://img3.redocn.com/tupian/20150430/mantenghuawenmodianshiliangbeijing_3924704.jpg",
//            "http://qq.yh31.com/tp/zjbq/201611131154402229.gif",
            "http://img15.3lian.com/2015/f2/50/d/71.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3737218198,1821201454&fm=21&gp=0.jpg",
            "http://imgsrc.baidu.com/forum/pic/item/57eab1119313b07e6acd7dfd0cd7912396dd8cef.jpg",
            "http://image60.360doc.com/DownloadImg/2013/04/1613/31674132_19.jpg",
            "http://imgsrc.baidu.com/forum/pic/item/40a99f510fb30f24ea06dd88c895d143ac4b03b8.jpg",
            "http://image60.360doc.com/DownloadImg/2013/04/1613/31674132_11.jpg",
            "http://e.hiphotos.baidu.com/zhidao/pic/item/38dbb6fd5266d01695ab94bf952bd40734fa35f2.jpg",
    };
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image_loader);
        ButterKnife.bind(this);


        int padding = 10;
        mRecyclerView.setPadding(padding, padding, padding, padding);
        mRecyclerView.setBackgroundColor(Color.BLACK);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));

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

        public ImageView mImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView;
        }
    }

    private void refresh() {

        if (0 == index)
            mRecyclerView.setAdapter(null);


        mRecyclerView.setAdapter(new RecyclerView.Adapter<ViewHolder>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                ImageView imageView = new SimpleDraweeView(getApplicationContext());
                imageView.setLayoutParams(new ViewGroup.LayoutParams(400, ViewGroup.LayoutParams.WRAP_CONTENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                return new ViewHolder(imageView);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

                ViewGroup.LayoutParams layoutParams = holder.mImageView.getLayoutParams();
                layoutParams.height = getHeight(position);
                holder.mImageView.setLayoutParams(layoutParams);
                holder.mImageView.setBackgroundColor(Color.GRAY);

                String url = IMAGE_URLS[position % IMAGE_URLS.length];
                displayImage(url, holder.mImageView);

            }

            @Override
            public int getItemCount() {
                return 40;
            }

            private int getHeight(int position) {
                return position % 4 * 200 + 200;
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


    public void displayImage(String string, ImageView imageView) {


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
                if (FILE == tag)
                    uri = ImageDownloader.Scheme.FILE.wrap(uri);
                else if (DRAWABLE == tag)
                    uri = ImageDownloader.Scheme.DRAWABLE.wrap(uri);
                else if (ASSETS == tag)
                    uri = ImageDownloader.Scheme.ASSETS.wrap(uri);

                ImageLoader.getInstance().displayImage(uri, imageView);


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

                requestCreator.error(R.mipmap.ic_launcher).into(imageView);
            }

            break;
            case 2:

            {
                RequestManager with = Glide.with(this);
                DrawableTypeRequest requestCreator = null;
                if (FILE == tag)
                    requestCreator = with.load(new File(uri));
                else if (DRAWABLE == tag)
                    requestCreator = with.load(ValueUtils.intValueOf(uri));
                else if (ASSETS == tag)
                    requestCreator = with.load(stringToUri(uri));
                else
                    requestCreator = with.load(uri);


                requestCreator.error(R.mipmap.ic_launcher).into(imageView);
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
