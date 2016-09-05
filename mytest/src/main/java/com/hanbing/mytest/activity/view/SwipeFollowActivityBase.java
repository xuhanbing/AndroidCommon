package com.hanbing.mytest.activity.view;

import java.io.Serializable;

/**
 * Created by hanbing on 2016/9/2
 */
public interface SwipeFollowActivityBase extends Serializable {

    public void scrollWhenNextOpened();

    public void followScroll(int x, int y);
}
