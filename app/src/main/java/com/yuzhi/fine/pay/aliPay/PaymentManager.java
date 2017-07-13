package com.yuzhi.fine.pay.aliPay;

/**
 * Created by sky.
 */

public interface PaymentManager {
    public interface PaymentCallback {

        public void onPrepareStart(PaymentManager manager);

        public void onPrepareFinish(PaymentManager manager);

        public void onPayFinish(PaymentManager manager, boolean success, String msg);
    }

        public void startPay(int totalAmountCNY_fen, String subject, String body, String outTradeNO, PaymentCallback cb);
}
