package com.yuzhi.fine.activity.mineActivity.MinePayActivity;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuzhi.fine.R;

import butterknife.Bind;

/**
 * 余额充值
 */
public class SelectPayTypeDialog  extends Dialog implements OnClickListener{
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


    private CheckedTextView mAlipy;
    private CheckedTextView mWechat;

    public SelectPayTypeDialog(@NonNull Context context) {
        super(context, R.style.dialog_confirm_goods_select);
        setContentView(R.layout.activity_mine_pay_price);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setAttributes(lp);
        //
        mAlipy = (CheckedTextView) findViewById(R.id.pay_type_alipay);
        mWechat = (CheckedTextView) findViewById(R.id.pay_type_wechat);
        mBtnBack = (LinearLayout)findViewById(R.id.btnBack);
        mTextHeadTitle = (TextView)findViewById(R.id.textHeadTitle);
        mAlipy.setOnClickListener(this);
        mWechat.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
        findViewById(R.id.pay_money_btn).setOnClickListener(this);
        initUI();
    }

    private void initUI(){
        mBtnBack.setVisibility(View.VISIBLE);
        mTextHeadTitle.setText("余额充值");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_money_btn:
                dismiss();
                if (mConfirmListener != null) {
                    if (mAlipy.isChecked()) {
                        mConfirmListener.onReportConfirm(PayType.PAY_TYPE_ALIPAY);
                    } else if (mWechat.isChecked()) {
                        mConfirmListener.onReportConfirm(PayType.PAY_TYPE_WECHAT);
                    } else {
                        mConfirmListener.onReportConfirm(null);
                    }
                }
                break;
            case R.id.pay_type_alipay:
                mAlipy.setChecked(true);
                mWechat.setChecked(false);
                break;
            case R.id.pay_type_wechat:
                mAlipy.setChecked(false);
                mWechat.setChecked(true);
                break;
            case R.id.btnBack:
                dismiss();
                break;
            default:break;
        }
    }



    public enum PayType {
        PAY_TYPE_ALIPAY,
        PAY_TYPE_WECHAT;
    }

    public interface ConfirmListener {
        public void onReportConfirm(PayType type);
    }

    private ConfirmListener mConfirmListener;

    public void setConfirmListener(ConfirmListener l) {
        mConfirmListener = l;
    }
}
