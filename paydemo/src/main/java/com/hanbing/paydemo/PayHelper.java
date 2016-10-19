package com.qnfamily.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.hanbing.paydemo.Constants;
import com.hanbing.paydemo.Order;
import com.hanbing.paydemo.SignUtils;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by hanbing on 2016/8/12
 */
public class PayHelper {
    public static final String WXPAY_URL = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
    private static final int SDK_PAY_FLAG = 1;

    private static PayHelper ourInstance;

    public static PayHelper getInstance() {
        if (null == ourInstance)
            ourInstance = new PayHelper();
        return ourInstance;
    }


    private IWXAPI api;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
//                        Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
//                            Toast.makeText(PayDemoActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                            Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    private PayHelper() {


    }


    public interface OnResultListener {

        public void onSuccess();
        public void onError(int code, String msg);
    }



    /**
     * 支付
     * @param platform 平台，微信或支付宝{@link com.hanbing.paydemo.Constants.Platform}
     * @param order 订单信息
     * @param listener
     */
    public void pay(Activity activity, int platform, Order order, OnResultListener listener) {

        switch (platform) {
            case Constants.Platform.WEIXIN:
                wxpay(activity, order, listener);
                break;
            case Constants.Platform.ALIPAY:
                alipay(activity, order, listener);
                break;
        }
    }

    private void wxpay(Activity activity, Order order, OnResultListener listener) {

        api = WXAPIFactory.createWXAPI(activity, null);
        // 将该app注册到微信
        api.registerApp(Constants.WX_APP_ID);

        boolean isPaySupported = api.getWXAppSupportAPI() >= com.tencent.mm.sdk.constants.Build.PAY_SUPPORTED_SDK_INT;

        if (!isPaySupported) {
            if (null != listener) listener.onError(-1, "不支持微信支付");
        } else {
            String url = WXPAY_URL;
            try{
                byte[] buf = null;//Util.httpGet(url);
                if (buf != null && buf.length > 0) {
                    String content = new String(buf);
                    Log.e("get server pay params:", content);
                    JSONObject json = new JSONObject(content);
                    if(null != json && !json.has("retcode") ){
                        PayReq req = new PayReq();
                        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                        req.appId			= json.getString("appid");
                        req.partnerId		= json.getString("partnerid");
                        req.prepayId		= json.getString("prepayid");
                        req.nonceStr		= json.getString("noncestr");
                        req.timeStamp		= json.getString("timestamp");
                        req.packageValue	= json.getString("package");
                        req.sign			= json.getString("sign");
                        req.extData			= "app data"; // optional
                        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                        api.sendReq(req);
                    }else{
                        String msg = json.getString("retmsg");

                        if (null != listener) listener.onError(-1, msg);
                    }
                }else{
                    if (null != listener) listener.onError(-1, "服务器请求错误");
                }
            }catch(Exception e){
                if (null != listener) listener.onError(-1, "未知错误");
            }
        }
    }

    public void wxpay(final Activity activity, Order.WxPay wxPay, final OnResultListener listener) {
        api = WXAPIFactory.createWXAPI(activity, null);
        // 将该app注册到微信
        api.registerApp(Constants.WX_APP_ID);

        boolean isPaySupported = api.getWXAppSupportAPI() >= com.tencent.mm.sdk.constants.Build.PAY_SUPPORTED_SDK_INT;

        if (!isPaySupported) {
            if (null != listener) listener.onError(-1, "不支持微信支付");
        } else {
            if (wxPay != null) {
                PayReq req = new PayReq();
                //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                req.appId			=  wxPay.getAppId();
                req.partnerId		= wxPay.getPartnerId();
                req.prepayId		=  wxPay.getPrepayId();
                req.nonceStr		=  wxPay.getNonceStr();
                req.timeStamp		= wxPay.getTimestamp();
                req.packageValue	= wxPay.getPackageValue();
                req.sign			= wxPay.getSign();
                req.extData			= ""; // optional
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                api.sendReq(req);
            }
        }
    }

    public void alipay(final Activity activity, final String payInfo, final OnResultListener listener) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(activity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                PayResult payResult = new PayResult(result);
                /**
                 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                 * docType=1) 建议商户依赖异步通知
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
//                        Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    if (null != listener) listener.onSuccess();
                } else {
                    // 判断resultStatus 为非"9000"则代表可能支付失败
                    // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
//                            Toast.makeText(PayDemoActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        if (null != listener) listener.onSuccess();
                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                            Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        if (null != listener) listener.onError(-1, "支付失败");
                    }
                }
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public void alipay(final Activity activity, Order order, final OnResultListener listener) {

        String orderInfo = getOrderInfo();//getOrderInfo("测试的商品", "该测试商品的详细描述", "0.01");

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        alipay(activity, payInfo, listener);
    }

    private String getOrderInfo(){
            // 签约合作者身份ID
            String orderInfo = "partner=" + "\"" + Constants.ALIPAY_PARTNER + "\"";

            // 签约卖家支付宝账号
            orderInfo += "&seller_id=" + "\"" + Constants.ALIPAY_SELLER + "\"";

            // 商户网站唯一订单号
            orderInfo += "&out_trade_no=" + "\"" + "2016082600000006" + "\"";

            // 商品名称
            orderInfo += "&subject=" + "\"" + "G20少年警校托管班" + "\"";

            // 商品详情
            orderInfo += "&body=" + "\"" + "G20少年警校托管班" + "\"";

            // 商品金额
            orderInfo += "&total_fee=" + "\"" + 0.01 + "\"";

            // 服务器异步通知页面路径
            orderInfo += "&notify_url=" + "\"" + "http://120.27.128.250:8000/gowhere_api/alipayCallback.cgi" + "\"";

            // 服务接口名称， 固定值
            orderInfo += "&service=\"mobile.securitypay.pay\"";

            // 支付类型， 固定值
            orderInfo += "&payment_type=\"1\"";

            // 参数编码， 固定值
            orderInfo += "&_input_charset=\"utf-8\"";

            // 设置未付款交易的超时时间
            // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
            // 取值范围：1m～15d。
            // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
            // 该参数数值不接受小数点，如1.5h，可转换为90m。
            //orderInfo += "&it_b_pay=\"30m\"";

            // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
            // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

            // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
            //orderInfo += "&return_url=\"m.alipay.com\"";

            // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
            // orderInfo += "&paymethod=\"expressGateway\"";

            return orderInfo;
    }

    /**
     * create the order info. 创建订单信息
     *
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + Constants.ALIPAY_PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Constants.ALIPAY_SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, Constants.ALIPAY_RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    public class PayResult {
        private String resultStatus;
        private String result;
        private String memo;

        public PayResult(String rawResult) {

            if (TextUtils.isEmpty(rawResult))
                return;

            String[] resultParams = rawResult.split(";");
            for (String resultParam : resultParams) {
                if (resultParam.startsWith("resultStatus")) {
                    resultStatus = gatValue(resultParam, "resultStatus");
                }
                if (resultParam.startsWith("result")) {
                    result = gatValue(resultParam, "result");
                }
                if (resultParam.startsWith("memo")) {
                    memo = gatValue(resultParam, "memo");
                }
            }
        }

        @Override
        public String toString() {
            return "resultStatus={" + resultStatus + "};memo={" + memo
                    + "};result={" + result + "}";
        }

        private String gatValue(String content, String key) {
            String prefix = key + "={";
            return content.substring(content.indexOf(prefix) + prefix.length(),
                    content.lastIndexOf("}"));
        }

        /**
         * @return the resultStatus
         */
        public String getResultStatus() {
            return resultStatus;
        }

        /**
         * @return the memo
         */
        public String getMemo() {
            return memo;
        }

        /**
         * @return the result
         */
        public String getResult() {
            return result;
        }
    }
}
