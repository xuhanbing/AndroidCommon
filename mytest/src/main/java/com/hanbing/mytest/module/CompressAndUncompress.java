package com.hanbing.mytest.module;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import android.os.Environment;

public class CompressAndUncompress {
	
	String fileName = "1.txt";
	String root = Environment.getExternalStorageDirectory().getAbsolutePath();
	String srcPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileName;

	public CompressAndUncompress ()
	{
		
	}
	public CompressAndUncompress(String root) {
		// TODO Auto-generated constructor stub
		System.out.println("xhb root=" + root);
		this.root = root;
	}
	
	public void start()
	{
		String zipPath = root + "/" + fileName + ".zip";
		String dstPath = root + "/" + fileName + "_uncom";
		
		try {
			
			FileInputStream is = new FileInputStream(srcPath);
			ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath));
			
			zos.putNextEntry(new ZipEntry(fileName));
			byte[] buffer = new byte[1024];
			
			int len = 0;
			while ((len = is.read(buffer)) != -1)
			{
				zos.write(buffer, 0, len);
			}
			
			is.close();
			
			zos.flush();
			zos.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		try {
			
			ZipFile file = new ZipFile(zipPath);
			
			InputStream zis = file.getInputStream(new ZipEntry(fileName));//new ZipInputStream(new FileInputStream(zipPath));
			FileOutputStream os = new FileOutputStream(dstPath);
			
			
			byte[] buffer = new byte[1024];
			
			int len = 0;
			while ((len = zis.read(buffer)) != -1)
			{
				os.write(buffer, 0, len);
			}
			
			zis.close();
			
			os.flush();
			os.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * ��ѹ�����ݵ��ַ���
	 * @param data ������������
	 * @param charset �ַ��������ʽ
	 * @return
	 */
	public String unzipString(byte[] data, String charset)
	{
		String result = "";
		
		try {
			
			GZIPInputStream is = new GZIPInputStream(new ByteArrayInputStream(data));
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
				
			int len = 0;
			while ((len = is.read(buffer)) != -1)
			{
				os.write(buffer, 0, len);
			}
			
			os.flush();
			os.close();
			
			result = new String(os.toByteArray(), charset);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	/**
	 * ѹ���ַ���
	 * @param stream
	 * @param charset
	 * @return
	 */
	public byte[] zipString(String stream, String charset)
	{
		try {
			byte[] data = stream.getBytes(charset);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GZIPOutputStream os = new GZIPOutputStream(baos);
			
			os.write(data);
			os.flush();
			os.close();
			
			return baos.toByteArray();
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ��ѹ�ļ���Ĭ����Դ�ļ�ͬ��Ŀ¼��
	 * @param zipPath
	 */
	public static void unzip(String zipPath)
	{
		
		String dstFolder = zipPath.substring(0, zipPath.lastIndexOf("/"));
		
		unzip(zipPath, dstFolder);
		
	}
	
	/**
	 * ��ѹzip�ļ���ָ��Ŀ¼
	 * @param zipPath
	 * @param dstFolder
	 */
	public static void unzip(String zipPath, String dstFolder)
	{
		
		try {
			unzip(new FileInputStream(zipPath), dstFolder);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ��ѹ������ָ��Ŀ¼
	 * @param is
	 * @param dstFolder
	 */
	public static void unzip(InputStream is, String dstFolder)
	{
		
		try {
			
			ZipInputStream zis = new ZipInputStream(is);
			
			ZipEntry zipEntry = null;
			String name = null;
			
			while ((zipEntry = zis.getNextEntry()) != null)
			{
				name = zipEntry.getName();
				
				if (zipEntry.isDirectory())
				{
					name = name.substring(0, name.length()-1);
					
					File f = new File(dstFolder + "/" + name);
					if (!f.exists())
					{
						f.mkdirs();
					}
				}
				else
				{
					FileOutputStream os = new FileOutputStream(dstFolder + "/" + name);
					
					byte[] buffer = new byte[1024];
					
					int len = 0;
					while ((len = zis.read(buffer)) != -1)
					{
						os.write(buffer, 0, len);
					}
					
					os.flush();
					os.close();
				}
				
			}
			
			zis.close();
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void test()
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String srcString = "���������ķ�������������ͷ���˼��죬��ĩ��լ�˼������ڽ��������������ķ����洢������ȷ�ġ�"
						+ "���׻����ʲô���Ĵ����أ���Ҳ���˵˵����ʵ������ֻ��Ҫ��ʼ���������ݣ�������ٱ��棬������������·��"
						+ "1 ����  2 �Ǿ���������ݻ��д���";
				String dstString = "";
				
				String charset = "gb2312";
				byte[] data = zipString(srcString, charset);
				String newString = "";
				try {
					newString = new String(data, "iso8859-1");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				dstString = unzipString(data, "gb2312");
				try {
					dstString = unzipString(newString.getBytes("iso8859-1"), charset);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("ret=" + dstString + "==" + srcString.equals(dstString));
			}
		}).start();
		
	}
	
}
