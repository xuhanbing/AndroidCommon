package com.hanbing.library.android.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by hanbing on 2016/4/25.
 */
public class ZipUtils {
    public static void zip(String srcFile, String zipFilePath)
    {
        File file = new File(zipFilePath);

        try {

            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(file));

            zip(srcFile, zipOutputStream, new File(srcFile));

            zipOutputStream.flush();
            zipOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zip(String dir, ZipOutputStream os, File file) throws IOException {

        if (file.isDirectory())
        {
            File[] files = file.listFiles();
            for (File f : files) {
                zip(dir, os, f);
            }

        } else {
            if (!dir.endsWith("\\"))
            {
                dir += "\\";
            }
            String name = file.getAbsolutePath().replace(dir, "");

            ZipEntry zipEntry = new ZipEntry(name);

            os.putNextEntry(zipEntry);

            FileInputStream in = new FileInputStream(file);

            byte[] buffer = new byte[1024];

            int len = 0;

            while ((len = in.read(buffer))  != -1)
            {
                os.write(buffer, 0, len);
            }

            in.close();
        }
    }

    public static void unzip(String zipFilePath, String dstDir)
    {
        try {
            ZipFile zipFile = new ZipFile(zipFilePath);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while (entries.hasMoreElements())
            {
                ZipEntry zipEntry = entries.nextElement();
                String name = zipEntry.getName();

                if (zipEntry.isDirectory())
                {

                } else {
                    InputStream inputStream = zipFile.getInputStream(zipEntry);

                    File file = new File(dstDir, name);

                    file.getParentFile().mkdirs();

                    FileOutputStream fileOutputStream = new FileOutputStream(file);

                    byte[] buffer = new byte[1024];

                    int readLen = 0;
                    while ((readLen = inputStream.read(buffer)) != -1 )
                    {
                        fileOutputStream.write(buffer, 0, readLen);
                    }

                    inputStream.close();

                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
