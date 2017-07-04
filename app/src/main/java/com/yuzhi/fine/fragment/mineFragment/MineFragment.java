package com.yuzhi.fine.fragment.mineFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.UserInfos.UserInfo;
import com.yuzhi.fine.ui.GridImageAdapter;
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
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.fine.utils.Constant.SHARE_LOGIN_USERID;

public class MineFragment extends Fragment {

    private Activity mContext;

    //我的头部信息
    @Bind(R.id.mine_header_infos_layout)
    LinearLayout mMineHeaferInfosLayout;
    //标题
    @Bind(R.id.textHeadTitle)
    TextView mHeader;
    //圆形头像
    @Bind(R.id.mine_round_header)
    RoundedImageView mRoundHeader;
    //昵称
    @Bind(R.id.mine_username)
    TextView mUserName;
    //个性签名
    @Bind(R.id.mine_user_style_text)
    EditText mUserStyleText;
    //个人完善度
    @Bind(R.id.mine_complete_layout)
    LinearLayout mComplete;
    //是否认证
    @Bind(R.id.mine_certification_text)
    TextView mMineCertifi;
    //个人账户
    @Bind(R.id.mine_account_layout)
    LinearLayout mMineAccountLayout;
    @Bind(R.id.mine_account_text)
    TextView mAccountText;
    @Bind(R.id.mine_account_xuanshang)
    TextView mAccountXuanShang;
    @Bind(R.id.mine_account_jifen)
    TextView mAccountJiFen;
    //个人认证
    @Bind(R.id.mine_user_verifi)
    LinearLayout mMineUserVerifi;

    //设置
    @Bind(R.id.mine_setting)
    LinearLayout mMineSetting;
    //投诉建议
    @Bind(R.id.mine_complaints)
    LinearLayout mMineComplaints;

    private ProgressDialog progress;
    private SharePreferenceUtil1 share ;
    private String mUserImageHeader;//用户头像


    //GridView
    @Bind(R.id.lxmine_gridview)
    GridView mMineGridView;
    private Integer[] icon = {R.drawable.my_find, R.drawable.zlrl,
            R.drawable.my_tg, R.drawable.draftbox, R.drawable.networking,
            R.drawable.myguanzhu, R.drawable.yaoqing, R.drawable.xiansuo,R.color.white};
    private String[] iconName = {"我的寻找", "招领认领", "我的推广", "草稿箱", "网络社交", "我的关注", "提供线索", "好友邀请",""};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lxmine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initData();
        initView();
    }

    void initView() {
        mHeader.setText("我的");
        share = new SharePreferenceUtil1(getActivity(), "lx_data", 0);
        //添加元素给gridview
        GridImageAdapter adapter = new GridImageAdapter(getActivity(), icon, iconName,true);
        mMineGridView.setAdapter(adapter);
        CommUtil.calGridViewWidthAndHeigh(3, mMineGridView);
        getUserInfos();
        editUserMotoo();
    }

    private void initData() {

        mMineGridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if (0 == position) {//我的寻找
                UIHelper.showMineFind(getActivity());
                } else if (1 == position) {//招领认领

                     UIHelper.showMinZLRL(getActivity());

                } else if (2 == position) {//我的推广
                    UIHelper.showMinPromote(getActivity());

                } else if (3 == position) {//草稿箱
                    UIHelper.showMinDraft(getActivity());


                } else if (4 == position) {//网络社交

                    showToast("网络社交",mContext);

                } else if (5 == position) {//我的关注



                } else if (6 == position) {//提供线索

                } else if (7 == position) {//好友邀请


                }
                else {
                        return;

                }

            }

        });
    }

    private void initOnClickLinstener(){

    }

    @OnClick(R.id.mine_account_layout)
    public void goAccountInfos(View view)
    {
        UIHelper.showMineAccount(mContext);
    }

    /**
     * 获取用户信息
     */
    private void getUserInfos(){
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        progress = CommUtil.showProgress(getActivity(), "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("userid",userID);//

        HttpClient.get(Caller.GET_USER_INFO, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();
                UserInfo userInfo = parseObject(data,UserInfo.class);

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {

                    //1.头像
                    if (!CommUtil.isNullOrBlank(userInfo.getImgFilePath())){
                        mUserImageHeader = userInfo.getImgFilePath();
                        Picasso.with(mContext).load(userInfo.getImgFilePath())
                            .resize(DeviceUtil.dp2px(mContext,65), DeviceUtil.dp2px(mContext,65))
                            .placeholder(R.drawable.default_image).into(mRoundHeader);
                    }
                    //2.名称
                    mUserName.setText(userInfo.getUserName());
                    //3.签名
                    mUserStyleText.setText(userInfo.getMotto());
                    //4.账户余额
                    mAccountText.setText(userInfo.getBalance());
                    //5.悬赏金额
                    mAccountXuanShang.setText(userInfo.getBalanceNoCash());
                    //6.我的积分
                    mAccountJiFen.setText(userInfo.getPoints());
                    //7.是否认证 认证状态  1未认证 2等待认证  3认证没通过 4认证通过
                    switch (userInfo.getApproveState()){
                        case "1":
                            mMineCertifi.setText("未认证");
                            break;
                        case "2":
                            mMineCertifi.setText("等待认证");
                            break;
                        case "3":
                            mMineCertifi.setText("认证没通过");
                            break;
                        case "4":
                            mMineCertifi.setText("认证通过");
                            break;
                       default:break;
                    }

                    if (progress != null) {
                        progress.dismiss();
                    }
                } else {
                    showAlert(message, getActivity());
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
                showToast("修改用户签名失败", getActivity());
            }
        });
    }


    /**
     * 修改用户个性签名
     */
    private void editUserMotoo(){
        mUserStyleText.setOnFocusChangeListener(new android.view.View. OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    mUserStyleText.setEnabled(true);
                }
            }
        });
        //点击EditText控件的父布局提交数据
        mMineHeaferInfosLayout.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                mMineHeaferInfosLayout.setFocusable(true);
                mMineHeaferInfosLayout.setFocusableInTouchMode(true);
                mMineHeaferInfosLayout.requestFocus();
                // 此处为失去焦点时的处理内容
                String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
                HashMap<String, String> params = new HashMap<>();
                params.put("userid",userID);//
                params.put("motto",mUserStyleText.getText().toString().trim());//

                HttpClient.get(Caller.MODIFY_USER_MOTOO, params, new HttpResponseHandler() {
                    @Override
                    public void onSuccess(RestApiResponse response) {
                        String result = response.getResult();
                        String message = response.getMessage();

                        if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
//                            showAlert(message, getActivity());
                            if (progress != null) {
                                progress.dismiss();
                            }
                        } else {
                            showAlert(message, getActivity());
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
                        showToast("修改用户签名失败", getActivity());
                    }
                });
                return false;
            }
        });
    }

    /**
     *  个人完善信息
     */
    @OnClick(R.id.mine_complete_layout)
    public void userCompletedInfos(View view){
        UIHelper.showCompleteUserInfos(getActivity() , mUserImageHeader);
    }

    @Override
    public void onPause() {
        super.onPause();
        getUserInfos();
    }

    @Override
    public void onResume() {
        super.onResume();
//        getUserInfos();
    }
}