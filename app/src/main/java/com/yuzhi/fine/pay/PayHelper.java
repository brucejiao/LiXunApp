package com.yuzhi.fine.pay;

import android.app.Activity;
import android.content.Intent;

import com.yuzhi.fine.activity.mineActivity.MinePayActivity.MinePayPriceActivity;
import com.yuzhi.fine.activity.mineActivity.MinePayActivity.MinePayPriceActivity.PayType;
import com.yuzhi.fine.pay.aliPay.AlipayManager;
import com.yuzhi.fine.pay.PaymentManager.PaymentCallback;
import com.yuzhi.fine.utils.CommUtil;

/**
 * Created by sky.
 */

public class PayHelper{
    private Activity activity;
    private MinePayPriceActivity minePayPriceActivity;

    public PayHelper(final Activity activity) {
        this.activity = activity;
        minePayPriceActivity = (MinePayPriceActivity)activity;

        minePayPriceActivity.setConfirmListener(new MinePayPriceActivity.ConfirmListener() {
            @Override
            public void onReportConfirm(MinePayPriceActivity.PayType type) {
                if (PayType.PAY_TYPE_ALIPAY == type) {
                    payWithAlipy();
                }
                else if (PayType.PAY_TYPE_WECHAT == type) {
//                    payWithWechat();
                    CommUtil.showToast("微信支付正在开发中...",activity);
                }
            }
        });
    }

    private String mOrderId;
    private String money;
    private double mOrderPrice;

    public void requestPay(String orderId, double orderPriceYuan, String money, PaymentCallback cb) {
        mOrderId = orderId;
        money = money;
        mOrderPrice = orderPriceYuan;
        paymentCallback = cb;
//        dialog.show();
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        return false;
    }

    public void onResume() {
    }
//    private WeChatPayManager mWechatPayManager;
    private AlipayManager mAlipayManager;

    private void payWithAlipy() {
        if (mAlipayManager == null) {
            mAlipayManager = new AlipayManager(activity);
        }
        new Thread() {
            @Override
            public void run() {
                int fen = (int) (mOrderPrice * 100);
                String subject = "立寻";
                String body = "余额充值";
                mAlipayManager.startPay(fen, subject, body, String.valueOf(mOrderId), getPaymentCallback());
            }
        }.start();
    }


    private PaymentCallback paymentCallback;

    private PaymentCallback getPaymentCallback() {
        return paymentCallback;
    }




//    private void payWithWechat() {
//        if (mWechatPayManager == null) {
//            mWechatPayManager = new WeChatPayManager(activity);
//        }
//        new Thread() {
//            @Override
//            public void run() {
//                int fen = (int) (mOrderPrice * 100);
//                String subject = getPaymentOrderTitle();
//                String body = getPaymentOrderBody();
//                mWechatPayManager.startPay(fen, subject, body, String.valueOf(mOrderId), getPaymentCallback());
//            }
//        }.start();
//    }



}
