package com.hanbing.mytest.module;

public class TestJni {
	
	static
	{
		System.loadLibrary("MyTest");
	}

	public TestJni() {
		// TODO Auto-generated constructor stub
	}

	
	public static native String print(String msg);
	public native String doInBackground(OnResultListener lsner);
	public static void onResult(String string)
	{
		System.out.println("onResult:" + string);
	}
	
	public interface OnResultListener
	{
		public void onResult(String string);
	}
}
