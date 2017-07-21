package com.yuzhi.lixun110ccd.activity.mineActivity.MinePayActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.http.Caller;
import com.yuzhi.lixun110ccd.http.HttpClient;
import com.yuzhi.lixun110ccd.http.HttpResponseHandler;
import com.yuzhi.lixun110ccd.http.RestApiResponse;
import com.yuzhi.lixun110ccd.model.LXFind.FindListBean;
import com.yuzhi.lixun110ccd.pay.PayHelper;
import com.yuzhi.lixun110ccd.pay.PaymentManager;
import com.yuzhi.lixun110ccd.utils.CommUtil;
import com.yuzhi.lixun110ccd.utils.SharePreferenceUtil1;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

import static com.yuzhi.lixun110ccd.utils.CommUtil.showToast;
import static com.yuzhi.lixun110ccd.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.lixun110ccd.utils.Constant.SHARE_LOGIN_USERID;

/**
 * 余额充值
 */
public class MinePayPriceActivity extends AppCompatActivity {
    private MinePayPriceActivity mContext;
    @Bind(R.id.btnBack)
    LinearLayout mBtnBack;
    @Bind(R.id.textHeadTitle)
    TextView mTextHeadTitle;
    @Bind(R.id.czje)
    EditText mCzje;//充值金额
    @Bind(R.id.pay_type_alipay)
    CheckedTextView mPayAli;//支付宝
    @Bind(R.id.pay_type_wechat)
    CheckedTextView mPayWechat;//微信
    @Bind(R.id.pay_money_btn)
    Button mPayMoneyBtn;//立即支付

    private ProgressDialog progress;
    private SharePreferenceUtil1 share ;

    private String mOrderNumber = "";//订单编号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_pay_price);
        ButterKnife.bind(this);
        mContext = this;
        initUI();
    }

    private void initUI() {
        mBtnBack.setVisibility(View.VISIBLE);
        mTextHeadTitle.setText("余额充值");
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);
    }

    //返回
    @OnClick(R.id.btnBack)
    public void onBack(View view) {
        finish();

    }

    //立即支付
    @OnClick({R.id.pay_money_btn, R.id.pay_type_alipay, R.id.pay_type_wechat})
    public void payMoneyBtn(View view) {
        switch (view.getId()) {
            case R.id.pay_money_btn:
                if (mConfirmListener != null){
                    if (mPayAli.isChecked()) {
                        mConfirmListener.onReportConfirm(PayType.PAY_TYPE_ALIPAY);
                        doPayment();

                    } else if (mPayWechat.isChecked()) {
                        mConfirmListener.onReportConfirm(PayType.PAY_TYPE_WECHAT);

                    }else {
                        mConfirmListener.onReportConfirm(null);
                    }
                }
                break;
            case R.id.pay_type_alipay:
                mPayAli.setChecked(true);
                mPayWechat.setChecked(false);
                getOrderNumber();

                break;
            case R.id.pay_type_wechat:
                mPayAli.setChecked(false);
                mPayWechat.setChecked(true);
                getOrderNumber();
                break;
        }
    }

    /**
     * 获取订单编号
     */
    private void getOrderNumber() {
        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        HashMap<String, String> params = new HashMap<>();
        params.put("amount", mCzje.getText().toString());//发布类别父级ID
        params.put("userid", userID);//
        final ArrayList<FindListBean> arrayBean = new ArrayList<FindListBean>();
        HttpClient.get(Caller.ADD_CHARGE_PRICE, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                    Message orderMessage = new Message();
                    orderMessage.what = 110011;
                    orderedHandler.sendMessage(orderMessage);

                    mOrderNumber = data ;

                    if (progress != null) {
                        progress.dismiss();
                    }

                } else {
                    showToast(message, mContext);
                    if (progress != null) {
                        progress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                if (progress != null) {
                    progress.dismiss();
                }
                showToast("订单号获取失败", mContext);
            }
        });

    }

    Handler orderedHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 110011 ){
                doPayment();
                return;
            }
        }
    };


    private PayHelper mPayHelper;

    private void doPayment() {
        if (mPayHelper == null) {
            mPayHelper = new PayHelper(this);
        }
        mPayHelper.requestPay(mOrderNumber,Double.parseDouble(mCzje.getText().toString()), mCzje.getText().toString(), getPaymentCallback());
    }

    private PaymentManager.PaymentCallback paymentCallback;

    private PaymentManager.PaymentCallback getPaymentCallback() {
        if (paymentCallback == null) {
            paymentCallback = new PaymentManager.PaymentCallback() {
                @Override
                public void onPrepareStart(PaymentManager manager) {
//                    getDialogHelper().showProgressDialog();
                }

                @Override
                public void onPrepareFinish(PaymentManager manager) {
//                    getDialogHelper().dismissProgressDialog();
                }

                @Override
                public void onPayFinish(PaymentManager manager, boolean success, String msg) {
                    if (success) {
                        onPaySuccess();
                    } else {
                        showToast("支付失败",mContext);
                    }
                }
            };

        }
        return paymentCallback;
    }

    private void onPaySuccess() {
        showToast("支付成功",mContext);
        Intent intent = new Intent(this, MinePayPriceActivity.class);
        startActivity(intent);
        finish();
    }
    public enum PayType {
        PAY_TYPE_ALIPAY,
        PAY_TYPE_WECHAT;
    }

    public interface ConfirmListener {
        public void onReportConfirm(PayType type);
    }

    private ConfirmListener mConfirmListener = null;

    public void setConfirmListener(ConfirmListener confirmListener) {
        this.mConfirmListener = confirmListener;
    }

}
