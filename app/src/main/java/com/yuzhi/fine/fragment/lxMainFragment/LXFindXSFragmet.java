package com.yuzhi.fine.fragment.lxMainFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.model.LXFindServerBean;
import com.yuzhi.fine.ui.CustomViewpager;
import com.yuzhi.fine.ui.FragmentAdapter.FindServerItemapter;
import com.yuzhi.fine.utils.CommUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

//import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Administrator on 2017/5/31.
 * 悬赏找寻服务
 */

public class LXFindXSFragmet extends Fragment {

    @Bind(R.id.lx_find_xs_listview)
    ListView mFindXSListview;

    @Bind(R.id.lx_top_btn)
    Button mTopBtn;//置顶
    @Bind(R.id.lx_new_btn)
    Button mNenBtn;//最新


    FindServerItemapter mFindItemAdapter;

    public CustomViewpager customViewpager;

    public LXFindXSFragmet() {
        super();
    }

    @SuppressLint("ValidFragment")
    public LXFindXSFragmet(CustomViewpager customViewpager) {
        super();
        this.customViewpager = customViewpager;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lx_find_xs, container, false);
        ButterKnife.bind(this, view);
        customViewpager.setObjectForPosition(view, 0);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        //FIXME Leak
//        RefWatcher refWatcher = AppContext.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 初始化
     */
    public void init() {
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
                mNenBtn.setTextColor(getActivity().getResources().getColor(R.color.black));
                mTopBtn.setBackgroundResource(R.drawable.editsharp_green_all);
                mNenBtn.setBackgroundResource(R.drawable.zuixin);

            }
        });
        mNenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopBtn.setTextColor(getActivity().getResources().getColor(R.color.black));
                mNenBtn.setTextColor(getActivity().getResources().getColor(R.color.white));
                mTopBtn.setBackgroundResource(R.drawable.zuixin);
                mNenBtn.setBackgroundResource(R.drawable.editsharp_green_all);

            }
        });
    }
}
