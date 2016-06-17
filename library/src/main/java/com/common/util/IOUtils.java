/**
 * 
 */
package com.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @author hanbing
 * @date 2015-06-06
 */
public class IOUtils {

    public static void close(Closeable c)
    {
	if (null != c)
	{
	    try {
		c.close();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    c = null;
	}
    }

	public static byte[] read(InputStream inputStream) {
		if (null != inputStream) {

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];

			int readLen = 0;

			try {
				while ((readLen = inputStream.read(buffer)) != -1) {

					byteArrayOutputStream.write(buffer, 0, readLen);
                }

				return byteArrayOutputStream.toByteArray();

			} catch (IOException e) {
				e.printStackTrace();
			}


		}

		return null;
	}
}
