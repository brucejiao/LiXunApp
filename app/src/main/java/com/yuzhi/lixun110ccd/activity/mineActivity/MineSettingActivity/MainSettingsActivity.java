package com.yuzhi.lixun110ccd.activity.mineActivity.MineSettingActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.ui.UIHelper;
import com.yuzhi.lixun110ccd.utils.ClearSpData;
import com.yuzhi.lixun110ccd.utils.SharePreferenceUtil1;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yuzhi.lixun110ccd.utils.Constant.SHARE_LOGIN_ISLOGIN;

/**
 * 设置
 */
public class MainSettingsActivity extends AppCompatActivity {
    private MainSettingsActivity mContext;
    private ProgressDialog progress;
    private SharePreferenceUtil1 share ;
    @Bind(R.id.btnBack)
    LinearLayout mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题

    @Bind(R.id.setting_infos)
    LinearLayout mSettingInfos;//通知与消息
    @Bind(R.id.setting_clear_data)
    LinearLayout mSettingClearData;//清除缓存
    @Bind(R.id.setting_update_apk)
    LinearLayout mSettingUpdateApk;//检查更新
    @Bind(R.id.setting_about_app)
    LinearLayout mSettingAboutApp;//关于立寻
    @Bind(R.id.setting_complain)
    LinearLayout mSettingComplain;//反馈意见
    @Bind(R.id.setting_exit)
    Button mSettingExit;//退出当前账号

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_settings);

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
        mTextHeaderTitle.setText("设置");

    }

    private void initData(){


    }

    @OnClick(R.id.btnBack)
    public void onBackBtn(View view){
            finish();
    }

    @OnClick(R.id.setting_exit)
    public void exitCurrentLogin(View view){
        ClearSpData.clearSharePreference(mContext);
        share.putBoolean(SHARE_LOGIN_ISLOGIN,false);
        finish();
        UIHelper.showLXLogin(mContext);
    }


}
