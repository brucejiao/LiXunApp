package com.yuzhi.fine.activity.mineActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yuzhi.fine.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 账户信息
 */
public class AccountInfosActivity extends AppCompatActivity {
    @Bind(R.id.btnBack)
    Button mBtnBack;
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
        initUI();
    }

    public void initUI(){
        mBtnBack.setVisibility(View.VISIBLE);
        mTextHeadTitle.setText("现金余额");
    }

    //返回
    @OnClick(R.id.btnBack)
    public void onBack(View view){
        finish();
    }
}
