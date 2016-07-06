/**
 *
 */
package com.hanbing.library.android.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author hanbing
 * @date 2015-06-06
 */
public class IOUtils {

    public static void close(Closeable c) {
        if (null != c) {
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

    /**
     * 获取stream的数据长度
     *
     * @param inputStream
     * @return
     */
    public static long readStreamLength(InputStream inputStream) {
        if (inputStream instanceof FileInputStream || inputStream instanceof ByteArrayInputStream)
            try {
                return inputStream.available();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return 0;
    }

    /**
     * read string from assets
     * @param context
     * @param fileName
     * @return
     */
    public static String readStringFromAssets(Context context, String fileName) {

        if (null != context) {
            InputStream inputStream = null;

            try {
                inputStream = context.getResources().getAssets().open(fileName);

                return readStringFromStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(inputStream);
            }

        }

        return null;
    }

    /**
     * read string from raw
     * @param context
     * @param rawId
     * @return
     */
    public static String readStringFromRaw(Context context, int rawId) {

        if (null != context) {
            InputStream inputStream = null;

            try {
                inputStream = context.getResources().openRawResource(rawId);
                return readStringFromStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(inputStream);
            }

        }

        return null;
    }

    /**
     * read string from inputStream
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String readStringFromStream(InputStream inputStream) throws IOException {
        if (null != inputStream) {

            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String string = null;

            while ((string = reader.readLine()) != null) {

                stringBuilder.append(string);
                stringBuilder.append("\n");
            }

            return stringBuilder.toString();

        }

        return null;
    }
}
