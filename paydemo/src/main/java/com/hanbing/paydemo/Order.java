package com.hanbing.paydemo;

import java.io.Serializable;

/**
 * Created by hanbing on 2016/10/14
 */

public class Order {

    public class Alipay implements Serializable {
        String info;

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }

    public class WxPay implements Serializable {
        String appId;

        String nonceStr;

        String sign;

        String partnerId;

        String prepayId;

        String packageValue;

        String timestamp;


        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public String getPackageValue() {
            return packageValue;
        }

        public void setPackageValue(String packageValue) {
            this.packageValue = packageValue;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public void setPartnerId(String partnerId) {
            this.partnerId = partnerId;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }



    }
}
