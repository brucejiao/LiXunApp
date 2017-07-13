package com.yuzhi.fine.pay.aliPay;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by skg.
 */

public abstract class BasePaymentManager implements PaymentManager {
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private Activity mActivity;
    private PaymentCallback mPaymentCallback;

    protected void setPaymentCallback(PaymentCallback cb) {
        mPaymentCallback = cb;
    }

    public BasePaymentManager(Activity activity) {
        this.mActivity = activity;
    }

    protected Activity getActivity() {
        return mActivity;
    }

    protected void notifyPrepareStart() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mPaymentCallback != null) {
                    mPaymentCallback.onPrepareStart(BasePaymentManager.this);
                }
            }
        });
    }

    protected void notifyPrepareFinish() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mPaymentCallback != null) {
                    mPaymentCallback.onPrepareFinish(BasePaymentManager.this);
                }
            }
        });
    }

    protected void notifyPayResult(final boolean sucess, final String msg) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mPaymentCallback != null) {
                    mPaymentCallback.onPayFinish(BasePaymentManager.this, sucess, msg);
                }
            }
        });
    }
}
