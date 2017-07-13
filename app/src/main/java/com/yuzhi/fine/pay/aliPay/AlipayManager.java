package com.yuzhi.fine.pay.aliPay;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.pay.BasePaymentManager;

import java.util.Map;

/**
 * 重要说明:
 * <p>
 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
 */
public class AlipayManager extends BasePaymentManager {
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2017051807278319";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088721000412067";

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String __RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDpOjRuxMDrhP8s9OMu0eZIjWVTuzkRjD+IRXbKbjZ6Fznh2NLCLo8NgR9tJNv6IrduZj+WdwktCfOd3EZL8xr27iFCosEGC2DP0UPHmWmJHlauwccIfkasrUKvH+c7AAk8mc0h4fv4dN9qMfiOWR/xZfBXGxQ6c5NSFCTtcY13KM//m8jYgkOncYKjD3B1ckr5ptcD4Xy8y0t4X9L2CNOFqbvPaYCg1oyEJmwM10VWlU4tnNawZksvr4cliKCe/7+5H+sS64grRGk6BKjiX5rB8In8PdfdguZY/JV35SkLQaJV4GMbei1Qe+25xGqtsn/oSFdmH64UeCNmTnm3BXN5AgMBAAECggEARSngoLSybWW8cq2QrbLLctqToPgVnXGU6amH59I1T5GRtbsiDTnXbG7NTgWvQoeHFDM4Sju0uemImFjZc+59IZtOu78eNNes9xQc4JVLKcGYfSy0BH3GIbEj5GE2plWFZ7ZIkfK9UVn/YzO8fSJLCcBsPLioEAl0N9QHe27AQASGdVLViwuPO0alyieDgwQikOnOwmRTqakifovC6wyDPLboksIsSseIZL+QZeVNrimikV0n1WklvM8C+FuDwUTjZ6NIDVbH+fL4CCss4pIXGKhKAj14livORDz86HVcqI3ghnKY7AJoL5Pt2JaWRbHMB3TpmxLji8jX5nH6lMohjQKBgQD03q9HEwauH3uL1H3DEm2tcWIDoUNFEVArifAdh4hZMa2IIgYLYPjHL+zzB+/Qi3ZmKYlRZsJoj4V7dktiXQPZaz2zxMP0C/GxOrJGA51lbJjr/Wmn3eAfpvOq/HWiPyDPlOWdoor8BdEaJRo9vtKXane6zQNbbRnCJI27jYqE8wKBgQDz1Awsp6Il/efNns2P5o80ZFE2OeOD5aN0qJuNdO/2fEN47W9F0TxsX8ArN/aR+gkfiVDY77Alg6MCEGPtnvUba6FjEWuRounKOcpD0EvE4uk54WIDj4J47T14gtx1WG41V8JWUXClPvgMixU6JBosDEJF5kDQi0EW22F+s6ww4wKBgQCp4JtE+aQWxGyXk3E0FeLVAuX2krfygJJXwjg8pDwpdNdorAH5furYdR0zdXwf98DKG2LSgDG7DGaUQnsF4HW9LjL+NjGja23fgFMRU4ysQmzMu1/DP6AvFUnSg5awWo9Os4OgmpVFRlvMgZT05R+AQDKT+4qqsMO/9lAFn6pLlQKBgHmk+M3uc/7wRY1YBMYeCKPiyIF9L/zFvF6fH7va8zzNkfvquPDkCnkm7ACj0ufRDmwlXahdLEwK+HA3LSOHglFDyShbsIbf+DNj0X0zlhmL+z9dKkEMf9NEyL4uyz3f+Fu0hMf7qW9Hkwju+pAfIs+G7ilhTkS8tKRqnqFPkkBxAoGBAMytyhUGyUIGZnFUun/BWX+hLqvQAbJa/QJXP5WnjKTGnV7xNOrG4k1HNtIks9AD5JBNfOhW5tn8zhSKl1A/7xVRnrE1AgLfaAmZy1wcE8xI+DF+1tTqQ+dJeiOOqViG2wa2+MAp6x9AOiEBZkgeJZCJWaq3itr11XeNZ31c+tcM";
    private static final String RSA_KEY = __RSA2_PRIVATE;
    private static final boolean RSA_KEY_IS_VERSION_2 = true;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public AlipayManager(Activity activity) {
        super(activity);
    }

    /**
     * 支付宝支付业务
     */
    @Override
    public void startPay(int totalAmountCNY_fen, String subject, String body, String outTradeNO, PaymentCallback cb) {
        setPaymentCallback(cb);
        notifyPrepareStart();
        //
        String notifyUrl = Caller.ALIPAY_CALLBACK_URL;
        BizContent biz = new BizContent();
        biz.setBody(body);
        biz.setTotalAmount(String.format("%.2f", totalAmountCNY_fen / 100f));
        biz.setSubject(subject);
        biz.setOutTradeNO(outTradeNO);
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, biz, notifyUrl, RSA_KEY_IS_VERSION_2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
        //
        String sign = OrderInfoUtil2_0.getSign(params, RSA_KEY, RSA_KEY_IS_VERSION_2);
        final String orderInfo = orderParam + "&" + sign;
        //
        notifyPrepareFinish();
        //
        new PayTaskThread(orderInfo, cb).start();
    }

    /**
     * 支付宝账户授权业务
     */
    public void startAuth(String businessUID, AuthCallback cb) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, businessUID, RSA_KEY_IS_VERSION_2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, RSA_KEY, RSA_KEY_IS_VERSION_2);
        final String authInfo = info + "&" + sign;
        new AuthTaskThread(authInfo, cb).start();
    }

    public interface AuthCallback {
        public void onAuthFinish(boolean success);
    }

    private abstract class TowWayThread extends Thread {

        private boolean taskUndo = true;

        @Override
        public final void run() {
            if (taskUndo) {
                doTaskInBankground();
                taskUndo = false;
                mHandler.post(this);
            } else {
                handlerResultInUIThread();
            }
        }

        protected abstract void doTaskInBankground();

        protected abstract void handlerResultInUIThread();
    }

    private class AuthTaskThread extends TowWayThread {
        final String authInfo;
        final AuthCallback authCallback;
        Map<String, String> authResultMap;

        public AuthTaskThread(String authInfo, AuthCallback authCallback) {
            this.authInfo = authInfo;
            this.authCallback = authCallback;
        }


        @Override
        protected void doTaskInBankground() {
            // 构造AuthTask 对象
            AuthTask authTask = new AuthTask(getActivity());
            // 调用授权接口，获取授权结果
            authResultMap = authTask.authV2(authInfo, true);
        }

        @Override
        protected void handlerResultInUIThread() {
            AuthResult authResult = new AuthResult(authResultMap, true);
            String resultStatus = authResult.getResultStatus();
            // 判断resultStatus 为“9000”且result_code
            // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
            if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                // 获取alipay_open_id，调支付时作为参数extern_token 的value传入，则支付账户为该授权账户
                if (authCallback != null) {
                    authCallback.onAuthFinish(true);
                }
            } else {
                // 其他状态值则为授权失败
                if (authCallback != null) {
                    authCallback.onAuthFinish(false);
                }
            }
        }
    }

    private class PayTaskThread extends Thread {
        final String orderInfo;
        final PaymentCallback callback;
        Map<String, String> payResultMap;

        public PayTaskThread(String orderInfo, PaymentCallback cb) {
            this.orderInfo = orderInfo;
            this.callback = cb;
        }

        @Override
        public void run() {
            PayTask alipay = new PayTask(getActivity());
            payResultMap = alipay.payV2(orderInfo, true);
            Log.i("msp", payResultMap.toString());
            //parse result----------------------------------
            PayResult payResult = new PayResult(payResultMap);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                notifyPayResult(true, "支付成功");
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                notifyPayResult(false, "支付失败");
            }
        }
    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public String getSDKVersion() {
        PayTask payTask = new PayTask(getActivity());
        return payTask.getVersion();
    }
}
