/**
 * Copyright@2014 WeiQun -hanbing
 * Date : 2014��6��27�� 
 * Time : ����9:29:32
 */
package com.hanbing.mytest.activity.action;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hanbing.mytest.R;
import com.hanbing.mytest.custom.ExtAudioRecorder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;


/**
 * TestMediaRecorder.java 
 * @author hanbing 
 * @date 2014��6��27�� 
 * @time ����9:29:32
 */
public class TestMediaRecorder extends Activity {

    /**
     * 
     */
    public TestMediaRecorder() {
        // TODO Auto-generated constructor stub
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_mediarecorder);
    }

    String filePath = "";
    ExtAudioRecorder extAudioRecorder = null;
    public void start(View view)
    {
        filePath = getFilePath();
        
        extAudioRecorder = ExtAudioRecorder.getInstanse(false);
        
        ExtAudioRecorder.recordChat(extAudioRecorder, filePath);
        
    }
    
    public void stop(View view)
    {
        ExtAudioRecorder.stopRecord(extAudioRecorder);
        
        
        
//        byte[] data = getVoiceData(filePath);
//        
//        
//        try {
//            
//            String cs = "UTF-16";
//            String string = MsgEcryptUtil.bytesToHexString(data);//new String(data);
//            
//            byte[] newData = MsgEcryptUtil.hexStringToBytes(string);
//            
//            int index = filePath.indexOf(".");
//            String newPath = filePath.substring(0, index) + "_cvrt" 
//                    + filePath.substring(index);
//            
//            saveVoiceData(newData, newPath);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        
        
        Toast.makeText(this, "record file path:" + filePath, Toast.LENGTH_SHORT).show();
    }
    
    private String getFilePath()
    {
        
        String folder = getFilesDir().getParent();
        String path = "";
        
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            folder = Environment.getExternalStorageDirectory().getAbsolutePath();
            
            folder += File.separator + "MyTest/voice";
            
            File file = new File(folder);
            
            if (!file.exists())
            {
                file.mkdirs();
            }
        }
        
        String format = ExtAudioRecorder.testOutputFormatNames[ExtAudioRecorder.testOutputFormatIndex];
        String encoder = ExtAudioRecorder.testAudioEncoderNames[ExtAudioRecorder.testAudioEncoderIndex];
        int samples = ExtAudioRecorder.sampleRates[ExtAudioRecorder.testSampleRateIndex];
        
        path = folder + "/" + System.currentTimeMillis()  + "_" + samples 
                + (ExtAudioRecorder.testCompress ? "_" + encoder : "")
                + (ExtAudioRecorder.testCompress ? "." + format : ".wav");
        
        return path;
    }
    
    int count = 0;
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        
        int[] arr = ExtAudioRecorder.sampleRates;
        
        int index = 0;
        
        
        menu.add(6, 6, index++, "=====samples");
        
        for (int i = 0; i < arr.length; i++)
        {
            menu.add(0, i, index, ""+arr[i]);
            index++;
        }
        
        menu.add(6, 7, index++, "=====output format");
        String[] strs = ExtAudioRecorder.testOutputFormatNames;
        for (int i = 0; i < strs.length; i++)
        {
            menu.add(1, i, index, strs[i]);
            index++;
        }
        
        
        menu.add(6, 8, index++, "=====encoder");
        strs = ExtAudioRecorder.testAudioEncoderNames;
        for (int i = 0; i < strs.length; i++)
        {
            menu.add(2, i, index, strs[i]);
            index++;
        }
        
        menu.add(3, 0, index++, "compress");
        
        return super.onCreateOptionsMenu(menu);
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        
        
        switch (item.getGroupId())
        {
            case 0:
                ExtAudioRecorder.testSampleRateIndex = item.getItemId();
                break;
            case 1:
                ExtAudioRecorder.testOutputFormatIndex = item.getItemId();
                ExtAudioRecorder.testCompress = true;
                break;
            case 2:
                ExtAudioRecorder.testAudioEncoderIndex = item.getItemId();
                ExtAudioRecorder.testCompress = true;
                break;
            case 3:
                ExtAudioRecorder.testCompress = true;
                break;
               
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    private byte[] getVoiceData(String voicePath)
    {
        byte[] data = null;
        File file = new File(voicePath);
        if (file.exists())
        {
            try {
                FileInputStream is = new FileInputStream(file);
                data = new byte[is.available()];
                
                is.read(data);
                is.close();
                
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            
        }
        
        return data;
    }
    
    private void saveVoiceData(byte[] data, String path)
    {
        
        File file = new File(path);
        
        if (!file.exists())
        {
            if (!file.getParentFile().exists())
            {
                file.getParentFile().mkdirs();
            }
            
            try {
                
                file.createNewFile();
                FileOutputStream os = new FileOutputStream(file);
                
                os.write(data);
                os.flush();
                os.close();
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
    
    private final static Charset UNICODE_CHARSET = Charset.forName("UTF-8");
    
    public static byte[] string2Bytes(String str)
    {
        if (null == str)
            return null;
        return UNICODE_CHARSET.encode(str).array();
    }
    
    public static String bytes2String(byte[] buffer)
    {
        if (null == buffer)
            return null;
        return new String(buffer, UNICODE_CHARSET);
    }
}
