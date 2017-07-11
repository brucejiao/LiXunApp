package com.yuzhi.fine.activity.mineActivity.MinePeopleVerfiActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.yuzhi.fine.R;
import com.yuzhi.fine.fragment.mineFragment.MineFragment;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.UserInfos.UserInfo;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.DeviceUtil;
import com.yuzhi.fine.utils.SharePreferenceUtil1;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.yuzhi.fine.utils.CommUtil.showAlert;
import static com.yuzhi.fine.utils.Constant.MINE_RESULT_REFRESH;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.fine.utils.Constant.SHARE_LOGIN_USERID;

/**
 * 个人认证
 */
public class MinePeoVrifiActivity extends AppCompatActivity {
    private MinePeoVrifiActivity mContext;
    private ProgressDialog progress;
    private SharePreferenceUtil1 share ;
    @Bind(R.id.btnBack)
    LinearLayout mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题

    @Bind(R.id.mine_user_vrifi_header)
    RoundedImageView mMineUserInfosHeader;//头像
    @Bind(R.id.mine_user_tel)
    TextView mMineUserTel;//用户电话
    @Bind(R.id.mine_user_state_img)
    ImageView mMineUserStateImg;//用户状态图片
    @Bind(R.id.mine_user_state)
    TextView mMineUserState;//用户状态
    @Bind(R.id.mine_user_go_vrifi)
    Button mMineUserGoVrifi;//去认证
    @Bind(R.id.mine_user_next)
    Button mMineUserNext;//以后再说

    private int mUserStateFlag = 0;//是否认证

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_peo_vrifi);
        mContext= this;
        ButterKnife.bind(this);
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);

        initUI();
        initData();
        onClickListener();
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

    /**
     * 事件监听
     */
    private void onClickListener(){
        //返回
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MineFragment.class);
                setResult(MINE_RESULT_REFRESH,intent);
                finish();
            }
        });
    }

    /**
     * 去认证
     * @param view
     */
    @OnClick(R.id.mine_user_go_vrifi)
    public void isVerifi(View view){
        mUserStateFlag =0;
       switch (mUserStateFlag){
           case 0://未认证
               UIHelper.showPeoVrifi2(mContext);
               break;
           case 1://等待认证
               CommUtil.showAlert("正在审核中，请耐心等待...",mContext);
               break;
           case 2://认证没通过
               UIHelper.showPeoVrifi2(mContext);
               break;
           case 3://认证通过
               CommUtil.showAlert("已经认证通过",mContext);
               break;
           default:
               break;
       }


    }

    /**
     * 下次再说
     * @param view
     */
    @OnClick(R.id.mine_user_next)
    public void goBack(View view){
        finish();
    }

    private void initData(){
        getUserInfos();
    }

    /**
     * 获取用户信息
     */
    private void getUserInfos() {
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userID);//

        HttpClient.get(Caller.GET_USER_INFO, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();
                UserInfo userInfo = parseObject(data, UserInfo.class);

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {

                    //1.头像
                    if (!CommUtil.isNullOrBlank(userInfo.getImgFilePath())) {
                        Picasso.with(mContext).load(userInfo.getImgFilePath())
                                .resize(DeviceUtil.dp2px(mContext, 65), DeviceUtil.dp2px(mContext, 65))
                                .placeholder(R.drawable.default_image).into(mMineUserInfosHeader);
                    }

                    mMineUserTel.setText(userInfo.getCellPhone());
                    //是否认证 认证状态  1未认证 2等待认证  3认证没通过 4认证通过
                    switch (userInfo.getApproveState()) {
                        case "1":
                            mUserStateFlag = 0;
                            mMineUserState.setText("未认证");
                            break;
                        case "2":
                            mUserStateFlag = 1;
                            mMineUserState.setText("等待认证");
                            break;
                        case "3":
                            mUserStateFlag = 2;
                            mMineUserState.setText("认证没通过");
                            break;
                        case "4":
                            mUserStateFlag = 3;
                            mMineUserState.setText("认证通过");
                            break;
                        default:
                            break;
                    }
                    if (progress != null) {
                        progress.dismiss();
                    }
                } else {
                    showAlert(message, mContext);
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
            }
        });
    }

}
