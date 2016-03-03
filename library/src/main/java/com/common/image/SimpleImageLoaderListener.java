/**
 *  
 */
package com.common.image;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

/**
 * @author hanbing
 * @date 2015-7-10
 */
public class SimpleImageLoaderListener implements ImageLoaderListener {

	@Override
	public void onLoadStarted(View view, String uri) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoading(View view, String uri, long total, long current) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoadCompleted(final View view, String uri, final Bitmap bm) {
		// TODO Auto-generated method stub
		view.post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (view instanceof ImageView) {
					((ImageView) view).setImageBitmap(bm);
				} else {
					view.setBackgroundDrawable(new BitmapDrawable(view
							.getResources(), bm));
				}
			}
		});
	}

	@Override
	public void onLoadFailed(View view, String uri) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoadCancelled(View view, String uri) {
		// TODO Auto-generated method stub

	}

}
