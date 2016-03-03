/**
 */
package com.common.view;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import pl.droidsonroids.gif.GifDrawable;

/**
 * gif image view
 * 
 * @author hanbing
 * @date 2015-9-15
 */
public class GifImageView extends pl.droidsonroids.gif.GifImageView{

    /**
     * @param context
     */
    public GifImageView(Context context) {
	super(context);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     */
    public GifImageView(Context context, AttributeSet attrs) {
	super(context, attrs);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public GifImageView(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
	// TODO Auto-generated constructor stub
    }

    /**
     * set image stream
     * @param is
     */
    public void setInputStream(InputStream is) {
	try {
	    GifDrawable d = new GifDrawable(is);
	    setImageDrawable(d);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
}
