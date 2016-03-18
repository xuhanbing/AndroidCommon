package com.hanbing.mytest.view.gif2;


import android.graphics.Bitmap;

public class GifFrame {
	/**
	 * ���캯��
	 * @param im ͼƬ
	 * @param del ��ʱ
	 */
	public GifFrame(Bitmap im, int del) {
		image = im;
		delay = del;
	}
	/**ͼƬ*/
	public Bitmap image;
	/**��ʱ*/
	public int delay;
	/**��һ֡*/
	public GifFrame nextFrame = null;
}

