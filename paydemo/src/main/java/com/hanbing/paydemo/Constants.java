package com.hanbing.paydemo;

/**
 * Created by hanbing on 2016/10/14
 */

public class Constants {

    public  interface Platform {
        int ALIPAY = 0;
        int WEIXIN = 2;
    }

    //qq
    public static final String QQ_APP_ID = "1105586350";
    public static final String QQ_APP_KEY = "vAuEDUGcOiNzzs04";

    // weixin
    public static final String WX_APP_ID = "wx681a130dd8cac51f";
    public static final String WX_APP_SECRET = "fc2b8fa5472d539b3347085b020e7104";

    //weibo
    public static final String WEIBO_APP_KEY = "2789128698";
    public static final String WEIBO_APP_SECRET = "f8d1d9780b5fa050253ca0a7d4182cbc";

    // 商户PID
    public static final String ALIPAY_PARTNER = "2088021054446798";
    // 商户收款账号
    public static final String ALIPAY_SELLER = "2088021054446798";
    // 商户私钥，pkcs8格式
    public static final String ALIPAY_RSA_PRIVATE = "";
    // 支付宝公钥
    public static final String ALIPAY_RSA_PUBLIC = "";
}
