/**
 * 
 */
package com.hanbing.library.android.view;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by hanbing
 */
public class CircleImageView extends RoundImageView {

    /**
     * @param context
     */
    public CircleImageView(Context context) {
	super(context);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     */
    public CircleImageView(Context context, AttributeSet attrs) {
	super(context, attrs);
	// TODO Auto-generated constructor stub
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
	super(context, attrs, defStyleAttr);
	// TODO Auto-generated constructor stub
    }

	@Override
	void init(Context context, AttributeSet attrs, int defStyle) {
		super.init(context, attrs, defStyle);

		mType = TYPE_CIRCLE;
	}
}
