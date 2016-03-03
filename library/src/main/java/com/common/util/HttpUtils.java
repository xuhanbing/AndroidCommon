/**
 * 
 */
package com.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;

/**
 * @author hanbing
 * @date 2015-9-1
 */
public class HttpUtils {

    public static interface HttpCallback {
	public void onSuccess(byte[] data);

	public void onFailure();
    }

    public static void doGet(final String url, final HttpCallback callback) {
	new AsyncTask<String, Integer, byte[]>() {

	    @Override
	    protected void onPostExecute(byte[] result) {
	        // TODO Auto-generated method stub
		if (null != result)
		{
		    if (null != callback)
		    {
			callback.onSuccess(result);
		    }
		}
		else
		{
		    if (null != callback)
		    {
			callback.onFailure();
		    }
		}
	    }
	    @Override
	    protected byte[] doInBackground(String... params) {
		// TODO Auto-generated method stub
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
		    conn = (HttpURLConnection) new URL(url).openConnection();
		    conn.connect();

		    int responseCode = conn.getResponseCode();
		    if (responseCode == HttpURLConnection.HTTP_OK) {
			in = conn.getInputStream();

			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			int ch;
			while ((ch = in.read()) != -1) {
			    bos.write((byte) ch);
			}

			return bos.toByteArray();

		    }

		} catch (MalformedURLException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} finally {
		    if (null != conn) {
			conn.disconnect();
		    }
		}

		return null;
	    }
	}.execute();
    }

}
