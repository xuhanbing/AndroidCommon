package com.hanbing.dianping.model;

import com.hanbing.dianping.R;

/**
 * Created by hanbing on 2016/6/13.
 */
public class Status {
    //NULL
    public static final int NULL = 0;
    //在线预定
    public static final int BOOLKING = 1;
    //优惠促销
    public static final int COUPON = 2;
    //优惠精选
    public static final int CHOSEN = 3;
    //免费吃
    public static final int FREE = 4;
    //买单优惠
    public static final int HUI = 5;
    //抽奖
    public static final int LOTTERY = 6;
    //新
    public static final int NEW = 7;
    //在线订
    public static final int ONLINE = 8;
    //免预约
    public static final int RESERVATION = 9;

    static int[] DRAWABLE_RESIDS = {
            0,
            R.drawable.deal_list_item_status_booking,
            R.drawable.deal_list_item_status_coupon,
            R.drawable.deal_list_item_status_dianping_chosen,
            R.drawable.deal_list_item_status_free,
            R.drawable.deal_list_item_status_hui,
            R.drawable.deal_list_item_status_lottery,
            R.drawable.deal_list_item_status_new,
            R.drawable.deal_list_item_status_reservation};

    public static int getDrawableResId(int status) {
        if (status >= DRAWABLE_RESIDS.length || status < 0)
            return 0;
        return DRAWABLE_RESIDS[status];
    }
}
