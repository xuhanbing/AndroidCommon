package com.hanbing.demo;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.hanbing.library.android.util.FileUtils;
import com.hanbing.library.android.util.ImageUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.imageaware.ViewAware;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hanbing on 2016/10/20
 */

public class ImageLoader {

    static ImageLoader mImageLoader = new ImageLoader();



    public static ImageLoader getInstance() {

        return mImageLoader;
    }


    ILoader mLoader = new GlideLoader();

    public void init(Context context) {
       mLoader.init(context);
    }

    public void displayImage(String uri, View view) {
       mLoader.display(uri, view);
    }

    public void displayImageRes(int resId, View view) {
        mLoader.displayRes(resId, view);
    }


    public void displayImageAssets(String path, View view) {
        mLoader.displayAssets(path, view);
    }


    public void displayImage(String uri, View view, int defaultResId) {
       mLoader.display(uri, view, defaultResId);
    }



    public void displayImageResSync(int resId, View view) {
        mLoader.displayResSync(resId, view);
    }

    public void displayImageAssetsSync(String path, View view) {
        mLoader.displayAssetsSync(path, view);
    }

    public void displayImageSync(String uri, View view) {
        mLoader.displaySync(uri, view);
    }




    private static void display(View view, Bitmap bitmap) {
        if (null == view)
            return;

        if (view instanceof ImageView) {
            ((ImageView) view).setImageBitmap(bitmap);
        } else {
            view.setBackgroundDrawable(new BitmapDrawable(bitmap));
        }
    }

    private static void display(View view, int resId) {
        if (null == view)
            return;

        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(resId);
        } else {
            view.setBackgroundResource(resId);
        }
    }

    interface ILoader {
        void init(Context context);
        void display(String uri, View view);
        void display(String uri, View view, int defaultResId);
        void displayAssets(String path, View view);
        void displayRes(int resId, View view);

        void displaySync(String uri, View view);
        void displaySync(String uri, View view, int defaultResId);
        void displayAssetsSync(String path, View view);
        void displayResSync(int resId, View view);


    }


    class UILLoader implements ILoader {
        protected DisplayImageOptions mDisplayImageOptions;
        @Override
        public void init(Context context) {
            mDisplayImageOptions = createDisplayImageOptions();
            com.nostra13.universalimageloader.core.ImageLoader.getInstance()
                    .init(new ImageLoaderConfiguration.Builder(context)
                            .defaultDisplayImageOptions(mDisplayImageOptions)
                            .diskCacheSize(58 * 1024 * 1024)
                            .build());
            com.nostra13.universalimageloader.utils.L.writeLogs(false);
        }

        public DisplayImageOptions getDisplayImageOptions() {
            return mDisplayImageOptions;
        }

        public void setDisplayImageOptions(DisplayImageOptions displayImageOptions) {
            mDisplayImageOptions = displayImageOptions;
        }

        public DisplayImageOptions createDisplayImageOptions() {
            DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
            displayImageOptionsBuilder
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565);

            return displayImageOptionsBuilder.build();
        }

        @Override
        public void display(String uri, View view) {
            displayImage(uri, view, createDisplayImageOptions());
        }

        @Override
        public void display(String uri, View view, int defaultResId) {
            DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
                    .cloneFrom(getDisplayImageOptions())
                    .showImageForEmptyUri(defaultResId)
                    .showImageOnFail(defaultResId)
//                .showImageOnLoading(defaultResId)
                    ;
            displayImage(uri, view, builder.build());
        }

        @Override
        public void displayAssets(String path, View view) {
            displayImageAssets(path, view, mDisplayImageOptions);
        }

        @Override
        public void displayRes(int resId, View view) {
            displayImageRes(resId, view, mDisplayImageOptions);

        }

        @Override
        public void displaySync(String uri, View view) {
            display(uri, view, 0);
        }

        @Override
        public void displaySync(String uri, View view, int defaultResId) {
            if (null == view)
                return;

            if (TextUtils.isEmpty(uri))
            {
                if (defaultResId > 0) {
                    ImageLoader.display(view, defaultResId);
                }
                return;
            }

            if (FileUtils.isExist(uri)) {
                uri = ImageDownloader.Scheme.FILE.wrap(uri);
            }


            Bitmap bitmap = com.nostra13.universalimageloader.core.ImageLoader.getInstance().loadImageSync(uri);

            ImageLoader.display(view, bitmap);
        }



        public void displayImageRes(int resId, View view, DisplayImageOptions options) {
            displayImage(ImageDownloader.Scheme.DRAWABLE.wrap("" + resId), view, options);
        }

        public void displayImageAssets(String path, View view, DisplayImageOptions options) {
            displayImage(ImageDownloader.Scheme.ASSETS.wrap(path), view, options);
        }

        @Override
        public void displayAssetsSync(String path, View view) {
            displayImageSync(ImageDownloader.Scheme.ASSETS.wrap(path), view);
        }

        @Override
        public void displayResSync(int resId, View view) {
            if (0 == resId)
                return;
            displayImageSync(ImageDownloader.Scheme.DRAWABLE.wrap("" + resId), view);
        }


        public void displayImage(String uri, View view, DisplayImageOptions options) {
            if (null == view)
                return;

            if (!TextUtils.isEmpty(uri))
            {
                if (FileUtils.isExist(uri)) {
                    //本地文件
                    uri = ImageDownloader.Scheme.FILE.wrap(uri);
                }
            }

            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(uri,
                    (view instanceof ImageView) ?
                            new ImageViewAware((ImageView) view)
                            :
                            new ViewAware(view) {
                                @Override
                                protected void setImageDrawableInto(Drawable drawable, View view) {
                                    view.setBackgroundDrawable(drawable);
                                }

                                @Override
                                protected void setImageBitmapInto(Bitmap bitmap, View view) {
                                    view.setBackgroundDrawable(ImageUtils.bitmapToDrawable(bitmap));
                                }
                            }
                    , null == options ? mDisplayImageOptions : options);


        }

    }


    class GlideLoader implements ILoader {

        @Override
        public void init(Context context) {
        }

        @Override
        public void display(String uri, View view) {
            display(uri, view, 0);
        }

        @Override
        public void display(String uri, View view, int defaultResId) {
            if (null == view)
                return;
            RequestManager with = Glide.with(view.getContext());

            if (TextUtils.isEmpty(uri)) {
                into(with.load(defaultResId), view);
            } else {
                if (FileUtils.isExist(uri)) {
                    //本地图片
                    into(with.load(new File(uri)), view, defaultResId);
                } else {
                    into(with.load(uri), view, defaultResId);
                }
            }
        }

        @Override
        public void displayAssets(String path, View view) {
//            if (null == view)
//                return;
//
//
//            Context context = view.getContext();
//
//            AssetManager assets = context.getAssets();
//
//            InputStream inputStream = null;
//            try {
//                inputStream  = assets.open(path);
//
//                Glide.get(context).register(InputStream.class, InputStream.class, INPUTSTREAM_MODEL_FACTORY);
//                into(Glide.with(context).load(inputStream), view);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            if (null == view)
                return;

            Uri uri = Uri.parse("file:///android_asset/" + path);
            into(Glide.with(view.getContext()).load(uri), view);
        }

        @Override
        public void displayRes(int resId, View view) {
            if (null == view)
                return;
            into(Glide.with(view.getContext()).load(resId), view);
        }

        @Override
        public void displaySync(String uri, View view) {
            displaySync(uri, view, 0);
        }

        @Override
        public void displaySync(String uri, View view, int defaultResId) {
            display(uri, view, defaultResId);
        }

        @Override
        public void displayAssetsSync(String path, View view) {
            displayAssets(path, view);
        }

        @Override
        public void displayResSync(int resId, View view) {
            displayRes(resId, view);
        }


        Target<GlideDrawable> into(DrawableTypeRequest request, final View view) {
            return into(request, view, 0);
        }

        Target<GlideDrawable> into(DrawableTypeRequest request, final View view, final int defaultResId) {

            return into(request, view, defaultResId, defaultResId);
        }
        Target<GlideDrawable> into(DrawableTypeRequest request, final View view, final int placeholderResId, final int errorResId) {
            if (null == request || null == view)
                return null;

            if (placeholderResId > 0)
                request.placeholder(placeholderResId);

            if (errorResId > 0)
                request.error(errorResId);

            if (view instanceof ImageView) {
                return request.into((ImageView) view);
            } else {
                return request.asBitmap().into(new SimpleTarget<Bitmap>(){
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if (null != view)
                        view.setBackgroundDrawable(new BitmapDrawable(resource));
                    }
                });
            }
        }


       ModelLoaderFactory<InputStream, InputStream> INPUTSTREAM_MODEL_FACTORY = new ModelLoaderFactory<InputStream, InputStream>() {
            @Override
            public ModelLoader<InputStream, InputStream> build(Context context, GenericLoaderFactory
            factories) {
                return new ModelLoader<InputStream, InputStream>() {
                    @Override
                    public DataFetcher<InputStream> getResourceFetcher(final InputStream model, int width, int height) {
                        return new DataFetcher<InputStream>() {
                            @Override
                            public InputStream loadData(Priority priority) throws Exception {
                                return model;
                            }

                            @Override
                            public void cleanup() {

                                if (null != model )
                                    try {
                                        model.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                            }

                            @Override
                            public String getId() {
                                return "InputStream" + System.currentTimeMillis();
                            }

                            @Override
                            public void cancel() {
                            }
                        };
                    }
                };
            }

            @Override
            public void teardown() {

            }
        };

    }

}
