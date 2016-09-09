package com.hanbing.library.android.tool;

import com.hanbing.library.android.util.ReflectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by hanbing on 2016/9/9
 */
public abstract class ListenerManager<T> {

    byte[] mLock = new byte[1];

    List<T> mListeners;

    public void addListener(T listener) {
        synchronized (mLock) {
            if (null == mListeners)
                mListeners = new ArrayList<>();

            if (null != listener)
            mListeners.add(listener);
        }
    }

    public void removeListener(T listener) {
        synchronized (mLock) {
            if (null != mListeners && !mListeners.isEmpty() && null != listener)
                mListeners.remove(listener);
        }
    }

    public List<T> getListeners() {
        synchronized (mLock) {
            return mListeners;
        }
    }

    public List<T> getListeners(boolean copy) {
        synchronized (mLock) {

            if (copy) {
                List<T> list = new ArrayList<>();

                if (null != mListeners && !mListeners.isEmpty())
                    list.addAll(mListeners);

                return  list;
            } else{
                return mListeners;
            }
        }
    }
}
