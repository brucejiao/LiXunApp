package com.yuzhi.fine.activity.mineActivity.MineComplainActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.utils.SharePreferenceUtil1;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 投诉建议
 */
public class MainComplainsActivity extends AppCompatActivity {
    private MainComplainsActivity mContext;
    private ProgressDialog progress;
    private SharePreferenceUtil1 share ;
    @Bind(R.id.btnBack)
    LinearLayout mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_complains);

        mContext= this;
        ButterKnife.bind(this);
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);

        initUI();
        initData();
    }

    /**
     * 初始化界面
     */
    private void initUI(){
        //返回
        mBackBtn.setVisibility(View.VISIBLE);
        //标题
        mTextHeaderTitle.setText("个人认证");

    }

    private void initData(){
    }

}
