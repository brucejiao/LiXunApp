package com.yuzhi.fine.fragment.mineFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuzhi.fine.R;
import com.yuzhi.fine.ui.GridImageAdapter;
import com.yuzhi.fine.utils.CommUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MineFragment extends Fragment {

    private Activity context;


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
        context = getActivity();
        initData();
        initView();
    }

    void initView() {
        mHeader.setText("我的");

        //添加元素给gridview
        GridImageAdapter adapter = new GridImageAdapter(getActivity(), icon, iconName,true);
        mMineGridView.setAdapter(adapter);
        CommUtil.calGridViewWidthAndHeigh(3, mMineGridView);

    }

    private void initData() {

    }
}