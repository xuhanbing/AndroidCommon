package com.hanbing.mytest.activity.action;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TestTcp extends Activity {

	LinearLayout layout;
	TextView text;
	boolean run;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		final EditText ip = new EditText(this);
		final EditText port = new EditText(this);
		
		ip.setText("t.freebao.com");
		port.setText("80");
		
		Button start = new Button(this);
		Button stop = new Button(this);
		text = new TextView(this);
		
		start.setText("start");
		stop.setText("stop");
		layout.addView(ip);
		layout.addView(port);
		layout.addView(start);
		layout.addView(stop);
		layout.addView(text);
		
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				run = true;
				connect(ip.getText().toString(), Integer.valueOf(port.getText().toString()));
			}
		});
		
		stop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				run = false;
			}
		});

		setContentView(layout);
	}
	
	StringBuilder sb = new StringBuilder();
	public void connect(final String addr, final int port)
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				int count = 0;
				while (run)
				{
					Socket socket = new Socket();
			        SocketAddress remoteAddr = new InetSocketAddress(addr/*"127.0.0.1"*/, port);
			        
			        String message = "";
			        
			        try {
						socket.connect(remoteAddr, 3 * 1000);
					} 
			        catch (SocketTimeoutException e1)
			        {
			        	message = e1.getMessage();
			        }
			        catch (IOException e) {
						// TODO Auto-generated catch block
						message = e.getMessage();
						e.printStackTrace();
					}
			        
			        String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
			        final String string = "[" + time + "]connect to " + addr + ":" + port
			        		+ "(" + socket.getLocalAddress().toString() + ")"
			        		+  " " +  (socket.isConnected() ? "success" : "faliled::\n") + message + "\n" ;
			        
		        	
			        runOnUiThread(new Runnable() {
						public void run() {
							text.setText(string);
						}
					});
			        
			        
			        try {
						socket.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			        try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		
	}

}
