package com.hanbing.mytest.activity.action;

import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class TestMediaPlayer extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
//		final String url = "http://freebao.com/sound/20150131/00393059083.mp3";
		final String url = "http://sc.111ttt.com/up/mp3/5931/DE513FA73DBDDD190DF4F17FCFA56D3E.mp3";
		Button btn = new Button(this);
		btn.setText("play");
		btn.setLayoutParams(new LayoutParams(200, 200));
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				play(url);
			}
		});
		
		
		setContentView(btn);
	}
	
	
	MediaPlayer player = null;
	private void play(String url)
	{
		if (null == player)
		{
			player = new MediaPlayer();
		}
		else
		{
			player.stop();
		}
		
		try {
			
			player.setOnPreparedListener(new OnPreparedListener() {
				
				@Override
				public void onPrepared(MediaPlayer mp) {
					// TODO Auto-generated method stub
					Log.e("", "onPrepared");
					player.start();
				}
			});
			
			player.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// TODO Auto-generated method stub
					Log.e("", "onError[" + what + "," + extra+ "]" );
					return false;
				}
			});
			
			player.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					Log.e("", "onCompletion");
				}
			});
			
			player.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
				
				@Override
				public void onBufferingUpdate(MediaPlayer mp, int percent) {
					// TODO Auto-generated method stub
					Log.e("", "onBufferingUpdate[" + percent + "]");
				}
			});
			
			player.setOnInfoListener(new OnInfoListener() {
				
				@Override
				public boolean onInfo(MediaPlayer mp, int what, int extra) {
					// TODO Auto-generated method stub
					Log.e("", "onInfo[" + what + "," + extra+ "]" );
					return false;
				}
			});
			
			Log.e("", "step 1");
			player.reset();
			player.setDataSource(url);
			Log.e("", "step 2");
//			player.prepare();
			player.prepareAsync();
			Log.e("", "step 3");
//			player.start();
			Log.e("", "step 4");
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (null != player)
		{
			player.stop();
			player.release();
		}
		super.onDestroy();
	}

}
