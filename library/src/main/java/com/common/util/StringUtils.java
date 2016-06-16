package com.common.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hanbing on 2016/6/15.
 */
public class StringUtils {

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
