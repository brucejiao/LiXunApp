package com.yuzhi.fine.fragment.coreFragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.model.LXFindServerBean;
import com.yuzhi.fine.ui.CustomViewpager;
import com.yuzhi.fine.ui.FragmentAdapter.FindServerItemapter;
import com.yuzhi.fine.utils.AnimationUtil;
import com.yuzhi.fine.utils.CommUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;





/**
 *委托寻人
 */
public class WTXRFragment extends Fragment {
    private Activity context ;
    //
    @Bind(R.id.shaixuan_textview)
    TextView mSXLayout;
    @Bind(R.id.find_third_btn)
    LinearLayout mTestBtn;

    @Bind(R.id.lx_find_find_listview)
    ListView mFindXSListview;
    FindServerItemapter mFindItemAdapter;

    @Bind(R.id.find_new_btn)
    Button mNewBtn;//最新
    @Bind(R.id.find_top_btn)
    Button mTopBtn;//置顶
    @Bind(R.id.find_xs_btn)
    Button mXSBtn;//悬赏


    public CustomViewpager customViewpager;

    public WTXRFragment() {
    }


    @SuppressLint("ValidFragment")
    public WTXRFragment(CustomViewpager customViewpager){
        this.customViewpager =  customViewpager ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wtxr, container, false);
        ButterKnife.bind(this, view);
        customViewpager.setObjectForPosition(view,0);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
        initData();

    }

    int i =1;
    void initView() {
        mSXLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if (i%2 ==0)
                {
                    CommUtil.showToast("===ok===",context);
                    mTestBtn.setVisibility(View.GONE);
                    mTestBtn.setAnimation(AnimationUtil.moveToViewBottom());
                }else{
                    CommUtil.showToast("===no===",context);
                    mTestBtn.setVisibility(View.VISIBLE);
                    mTestBtn.setAnimation(AnimationUtil.moveToViewLocation());
                }



            }
        });
        initListView();
    }


    private void initData() {

    }


    public void initListView() {
        ArrayList<LXFindServerBean> arrayBean = new ArrayList<LXFindServerBean>();

        String ImagerURL = "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg";

        for (int i = 0; i < 10; i++) {
            LXFindServerBean bean = new LXFindServerBean();
//            bean.setUserHeaderImg(ImagerURL);//头像
            bean.setUserName("阿" + i);//用户名
            bean.setIsCertification("已认证");//是否认证
            bean.setTitle("丢了丢了丢了");
            bean.setIsFind("招领" + i);
            bean.setIsGenerailze("宇宙推广" + i);
            bean.setAddress("男爵领地");
            bean.setPrice("50元");
            bean.setContent("我有一只小毛驴，我从来也不骑，我有一我骑着它去法克你一一一");
//            bean.setImgOne(ImagerURL);
//            bean.setImgTwo(ImagerURL);
//            bean.setImgThree(ImagerURL);
            bean.setTime(i + "分钟前发布");
            bean.setLookerNum("14");
            bean.setFocusonNum("21");
            bean.setMessageNum("35");

            arrayBean.add(bean);
        }


        mFindItemAdapter = new FindServerItemapter(getActivity(), arrayBean);
        mFindXSListview.setAdapter(mFindItemAdapter);
        CommUtil.setListViewHeightBasedOnChildren(mFindXSListview, mFindItemAdapter);

        lxFindOnClick();
    }

    /**
     * 置顶/最新-按钮背景切换
     */
    public void lxFindOnClick(){
        mTopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopBtn.setTextColor(getActivity().getResources().getColor(R.color.white));
                mNewBtn.setTextColor(getActivity().getResources().getColor(R.color.black));
                mTopBtn.setBackgroundResource(R.drawable.zhiding);
                mNewBtn.setBackgroundResource(R.drawable.zuixin);

            }
        });
        mNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopBtn.setTextColor(getActivity().getResources().getColor(R.color.black));
                mNewBtn.setTextColor(getActivity().getResources().getColor(R.color.white));
                mTopBtn.setBackgroundResource(R.drawable.zuixin);
                mNewBtn.setBackgroundResource(R.drawable.zhiding);

            }
        });

        mXSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopBtn.setTextColor(getActivity().getResources().getColor(R.color.black));
                mNewBtn.setTextColor(getActivity().getResources().getColor(R.color.white));
                mTopBtn.setBackgroundResource(R.drawable.zuixin);
                mNewBtn.setBackgroundResource(R.drawable.zhiding);

            }
        });
    }



}
