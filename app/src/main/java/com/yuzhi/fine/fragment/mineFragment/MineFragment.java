package com.yuzhi.fine.fragment.mineFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.ui.GridImageAdapter;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.SharePreferenceUtil1;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

import static com.yuzhi.fine.utils.CommUtil.showAlert;
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.fine.utils.Constant.SHARE_LOGIN_USERID;

public class MineFragment extends Fragment {

    private Activity mContext;


    //标题
    @Bind(R.id.textHeadTitle)
    TextView mHeader;
    //圆形头像
    @Bind(R.id.mine_round_header)
    RoundedImageView mRoundHeader;
    //圆形头像
    @Bind(R.id.mine_username)
    TextView mUserName;
    //个性签名
    @Bind(R.id.mine_user_style_text)
    EditText mUserStyleText;
    //个人完善度
    @Bind(R.id.mine_complete)
    TextView mComplete;
    //是否认证
    @Bind(R.id.mine_certification_text)
    TextView mMineCertifi;
    //个人账户
    @Bind(R.id.mine_account)
    LinearLayout mMineAccount;
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

    @OnClick(R.id.mine_account)
    public void goAccountInfos(View view)
    {
        UIHelper.showMineAccount(mContext);
    }

    /**
     * 修改用户个性签名
     * @param view
     */
    @OnClick(R.id.mine_user_style_text)
    public void editUserInfos(View view)
    {
        mUserStyleText.setEnabled(true);
        showAlert("11111", getActivity());
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        progress = CommUtil.showProgress(getActivity(), "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("userid",userID);//
        params.put("motto",mUserStyleText.getText().toString().trim());//

        HttpClient.get(Caller.MODIFY_USER_MOTOO, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {

                    showAlert(message, getActivity());
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

}