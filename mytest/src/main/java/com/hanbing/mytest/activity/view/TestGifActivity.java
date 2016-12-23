package com.hanbing.mytest.activity.view;


import com.hanbing.library.android.image.ImageLoader;
import com.hanbing.mytest.activity.base.SlideRightFinishActivity;
import com.hanbing.mytest.view.gif.AnimatedGifDrawable;
import com.hanbing.mytest.view.gif.AnimatedGifDrawable.UpdateListener;
import com.hanbing.mytest.view.gif.AnimatedImageSpan;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class TestGifActivity extends SlideRightFinishActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		ScrollView sv = new ScrollView(this);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
//		GifView gv = new GifView(this);
		sv.addView(layout);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 200);
//		for (int i = 1; i <= 16; i++)
//		{
//			final com.xhb.mytest.view.gif3.GifView gv = new com.xhb.mytest.view.gif3.GifView(this);
////			gv.setGifImageType(GifImageType.WAIT_FINISH);
//			
//			final String address = "http://p.freebao.com:81/cartoon/1/1_"+i+".gif";//"http://s1.dwstatic.com/group1/M00/5C/E1/428aab0b55f2a0f2af91eee76251b51e.gif";
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					// TODO Auto-generated method stub
//					
//					//"http://ww3.sinaimg.cn/bmiddle/dc66b248gw1emr1kcc3eqg209g05b4qq.gif";//"http://s1.dwstatic.com/group1/M00/5C/E1/428aab0b55f2a0f2af91eee76251b51e.gif";
//					URL url;
//					InputStream is = null;
//					try {
//						url = new URL(address);
//						is = url.openStream();
//						
//						gv.setInputStream(is);
////						
////						gv.setGifImage(is);
//						
//					}  catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}//getResources().openRawResource(R.raw.test);
//					
//				}
//			}).start();
//			
//			
//			layout.addView(gv, lp);
//		}
//		
		
		
//		com.xhb.mytest.view.gif3.GifView gv3 = new com.xhb.mytest.view.gif3.GifView(this);
//		gv3.setInputStream(getResources().openRawResource(R.raw.a2));
//		layout.addView(gv3);

		String url = "http://p.freebao.com:81/cartoon/4/4_1.gif";
		final GifImageView gv1 = new GifImageView(this);
//		gv3.setImageURI(Uri.parse("http://p.freebao.com:81/cartoon/4/4_1.gif"));
		gv1.setImageResource(R.drawable.n1);

		final ImageLoader instance = ImageLoader.getInstance(this);
		instance.displayImage(gv1, url);
		layout.addView(gv1);
		
		GifImageView gv3 = new GifImageView(this);
		gv3.setImageURI(Uri.parse("http://p.freebao.com:81/cartoon/4/4_1.gif"));
//		gv3.setInputStream(getResources().openRawResource(R.raw.a2));
		layout.addView(gv3);
		
		GifImageView gv4 = new GifImageView(this);
		gv4.setImageURI(Uri.parse("http://p.freebao.com:81/cartoon/4/4_1.gif"));
//		gv4.setImageResource(R.raw.a2);
		layout.addView(gv4);
		
		final TextView text = new TextView(this);
		String string = "0123456789";
		SpannableStringBuilder ss = new SpannableStringBuilder(string);
		
		ss.setSpan(new AnimatedImageSpan(new AnimatedGifDrawable(
				getResources().openRawResource(R.raw.a1), new UpdateListener() {
					
					@Override
					public void update() {
						// TODO Auto-generated method stub
						text.postInvalidate();
					}
				})),
				0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
		
		text.setText(ss);
		layout.addView(text, lp);
		
		setContentView(sv);
	}
	
	
	class GifSpan extends DynamicDrawableSpan
	{
		AnimationDrawable drawable;
		
		Handler handler = new Handler()
		{
			
		};
		public GifSpan(AnimationDrawable d)
		{
			this.drawable = d;
			
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
				}
			});
		}
		@Override
		public Drawable getDrawable() {
			// TODO Auto-generated method stub
			return drawable.getCurrent();
		}
		
	}

}
