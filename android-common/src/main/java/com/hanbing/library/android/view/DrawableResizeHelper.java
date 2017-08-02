package com.hanbing.library.android.view;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by hanbing on 2017/8/2
 */

public class DrawableResizeHelper {

    public interface Fetcher {
        int getDrawableSize(Drawable drawable);
    }

    //Force set width equals height, default is false
    boolean mForceSquare;
    TextView mTextView;
    Fetcher mFetcher;

    static final float SIZE_RATIO = 0.9f;

    Fetcher DEFAULT_FETCHER = new Fetcher() {
        @Override
        public int getDrawableSize(Drawable drawable) {
            if (null != mTextView) {
                return (int) (mTextView.getTextSize() * SIZE_RATIO);
            }
            return 0;
        }
    };

    public DrawableResizeHelper(TextView textView) {
        mTextView = textView;
    }

    public DrawableResizeHelper(TextView textView, boolean forceSquare) {
        mForceSquare = forceSquare;
        mTextView = textView;
    }

    public DrawableResizeHelper(TextView textView, Fetcher fetcher, boolean forceSquare) {
        mForceSquare = forceSquare;
        mFetcher = fetcher;
        mTextView = textView;
    }

    public void resize() {

        TextView textView = mTextView;
        if (null == textView)
            return;

        Fetcher fetcher = mFetcher;

        if (null == fetcher) fetcher = DEFAULT_FETCHER;

        Drawable[] compoundDrawables = textView.getCompoundDrawables();



        boolean update = false;
        if (null != compoundDrawables) {
            for (Drawable drawable : compoundDrawables) {
                if (null != drawable) {

                    int size = fetcher.getDrawableSize(drawable);

                    if (size <= 0) {
                        size = (int) (textView.getTextSize() * SIZE_RATIO);
                    }

                    if (drawable.getBounds().height() != size) {
                        update = true;
                    }
                    int width = drawable.getIntrinsicWidth();
                    int height = drawable.getIntrinsicHeight();

                    if (!mForceSquare && width > 0 && height > 0) {
                        float ratio = width * 1.0f / height;
                        width = (int) (size * ratio);
                        height = size;
                    } else {
                        width = height = size;
                    }
                    drawable.setBounds(0, 0, width, height);
                }
            }

            if (update)
                textView.setCompoundDrawables(compoundDrawables[0], compoundDrawables[1], compoundDrawables[2], compoundDrawables[3]);
        }
    }
}
