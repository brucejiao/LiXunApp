package com.yuzhi.lixun110ccd.activity.mineActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.ui.UIHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账户信息
 */
public class AccountInfosActivity extends AppCompatActivity {
    private AccountInfosActivity mContext ;
    @Bind(R.id.btnBack)
    LinearLayout mBtnBack;
    @Bind(R.id.addPrice)
    LinearLayout mAddPrice;//充值
    @Bind(R.id.textHeadTitle)
    TextView mTextHeadTitle;
    //账户余额
    @Bind(R.id.mine_account_price)
    TextView mAccountPrice;
    //悬赏金额
    @Bind(R.id.mine_account_xuanshang)
    TextView mAccountXuanShang;
    //账户积分
    @Bind(R.id.mine_account_jifen)
    TextView mAccountJiFen;
    //提现
    @Bind(R.id.mine_account_tixian)
    TextView mAccountTiXian;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_infos);
        ButterKnife.bind(this);
        mContext = this ;
        initUI();
    }

    public void initUI(){
        mBtnBack.setVisibility(View.VISIBLE);
        mAddPrice.setVisibility(View.VISIBLE);
        mTextHeadTitle.setText("现金余额");
        String account = getIntent().getStringExtra("account");//余额
        String xuanshang = getIntent().getStringExtra("xuanshang");//悬赏
        String jifen = getIntent().getStringExtra("jifen");//积分

        mAccountPrice.setText(account+"元");
        mAccountXuanShang.setText(xuanshang+"元");
        mAccountJiFen.setText(jifen+"积分");
        mAccountTiXian.setText(account+"元");

    }

    @OnClick(R.id.addPrice)
    public void addPrice(View view){
        UIHelper.showPayPage(mContext);
    }


    //返回
    @OnClick(R.id.btnBack)
    public void onBack(View view){
        finish();
    }
}
