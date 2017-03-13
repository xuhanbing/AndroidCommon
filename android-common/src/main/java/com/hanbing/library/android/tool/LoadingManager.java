package com.hanbing.library.android.tool;

/**
 * Created by hanbing on 2016/3/28.
 */
public class LoadingManager  {

    byte[] mLock = new byte[0];

    /**
     *  判断是否空闲
     */
    boolean mIsBusy = false;

    /**
     * 锁定，表示开始请求
     * @return 如果有未完成动作，将返回false否则返回true
     */
    public  boolean lock()
    {
        synchronized (mLock)
        {
            if (mIsBusy)
            {
                return false;
            }

            mIsBusy = true;

            return true;

        }
    }

    /**
     * 释放，表示请求结束了
     */
    public  void unlock()
    {

        synchronized (mLock)
        {
            mIsBusy = false;
        }

    }

    /**
     * 判断是否有任务
     * @return
     */
    public boolean isBusy()
    {
        synchronized (mLock)
        {
            return mIsBusy;
        }
    }

    /**
     * 重置，如果有请求，将直接返回
     */
    public void reset()
    {

        if (isBusy())
        {
            return;
        }

        forceReset();
    }



    /**
     * 强制重置
     */
    public void forceReset()
    {
        mIsBusy = false;
    }


}
