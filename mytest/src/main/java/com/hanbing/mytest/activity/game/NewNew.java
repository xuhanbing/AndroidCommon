package com.hanbing.mytest.activity.game;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.hanbing.mytest.R;
import com.hanbing.mytest.module.GameNewNew.Controller;
import com.hanbing.mytest.module.GameNewNew.NewNewPlayer;
import com.hanbing.mytest.module.GameNewNew.Poker;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NewNew extends Activity{
	
	Context mContext;
	List<NewNewPlayer> players = new ArrayList<NewNewPlayer>();
	
	MyAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mContext = getApplicationContext();
		super.onCreate(savedInstanceState);
		
		
		ListView list = new ListView(this);
		Button btn = new Button(this);
		btn.setText("Start");
		
		list.addFooterView(btn);
		
		adapter = new MyAdapter();
		list.setAdapter(adapter);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				play();
				
			}
		});
		
		setContentView(list);
		
	}
	
	
	protected void play() {
		// TODO Auto-generated method stub
		players.clear();
		
		int nums = 4;
		
		int banker = (int) (Math.random() * nums);
		
		List<int[]> pokers = Controller.getPokers(nums);
		
		for (int i = 0; i < nums; i++)
		{
			NewNewPlayer player = new NewNewPlayer();
			
			player.setPokers(pokers.get(i));
			player.setBanker(banker == i);
			player.setName("Player " + i);
			
			players.add(player);
		}
		
		adapter.notifyDataSetChanged();
	}


	public class MyAdapter extends BaseAdapter
	{
		

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return null == players ? 0 : players.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null == players ? null : players.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			
			NewNewPlayer player = (NewNewPlayer) getItem(position);
			
			Holder holder = null;
			
			if (null == convertView)
			{
				holder = new Holder();
				
				convertView = LayoutInflater.from(mContext)
						.inflate(R.layout.layout_newnewplayer, null);
				
				holder.name = (TextView) convertView.findViewById(R.id.tv_player_name);
				holder.actor = (TextView) convertView.findViewById(R.id.tv_player_actor);
				holder.chip = (TextView) convertView.findViewById(R.id.tv_player_chip);
				holder.wager = (TextView) convertView.findViewById(R.id.tv_player_wager);
				holder.pokers = (ViewGroup) convertView.findViewById(R.id.layout_player_poker);
				
				convertView.setTag(holder);
			}
			else
			{
				holder = (Holder) convertView.getTag();
			}
			
			holder.name.setText(player.getName());
			holder.actor.setText(player.isBanker() ? "ׯ" : "��");
			holder.actor.setTextColor(player.isBanker() ? Color.RED : Color.GREEN);
			holder.chip.setText(player.getChip() + " $");
			holder.wager.setText(player.getWager() + " $");
			
			holder.pokers.removeAllViews();
			int[] pokers = player.getPokers();
			for (int i = 0; i < pokers.length; i++)
			{
				holder.pokers.addView(getPokerView(pokers[i]));
			}
			
			return convertView;
		}
		
		View getPokerView(int value)
		{
			ImageView image = new ImageView(mContext);
			
			image.setLayoutParams(new LayoutParams(200, 200));
			InputStream is = null;
			try {
				is = mContext.getAssets().open(Poker.getPokerResName(value));
				
				image.setImageBitmap(BitmapFactory.decodeStream(is));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				image.setImageResource(R.drawable.a);
			}
			
			
			return image;
		}
		
		class Holder
		{
			TextView name;
			TextView actor;
			TextView chip;
			TextView wager;
			ViewGroup pokers;
		}
		
	}
	
}
