/**
 * 
 */
package com.common.util;

import java.io.Closeable;
import java.io.IOException;

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
}
