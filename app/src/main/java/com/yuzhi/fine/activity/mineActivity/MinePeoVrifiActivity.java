package com.yuzhi.fine.activity.mineActivity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuzhi.fine.R;
import com.yuzhi.fine.utils.SharePreferenceUtil1;

import butterknife.Bind;

/**
 * 个人认证
 */
public class MinePeoVrifiActivity extends AppCompatActivity {
    private MinePeoVrifiActivity mContext;
    private ProgressDialog progress;
    private SharePreferenceUtil1 share ;
    @Bind(R.id.btnBack)
    Button mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题
    @Bind(R.id.mine_user_infos_header)
    RoundedImageView mMineUserInfosHeader;//头像
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_peo_vrifi);
    }
}
