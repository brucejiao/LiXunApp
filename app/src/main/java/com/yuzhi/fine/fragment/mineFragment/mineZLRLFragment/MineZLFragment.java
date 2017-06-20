package com.yuzhi.fine.fragment.mineFragment.mineZLRLFragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.model.MineFindBean;
import com.yuzhi.fine.ui.CustomViewpager;
import com.yuzhi.fine.ui.FragmentAdapter.MineFindItemapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的--招领认领--我的招领
 */
public class MineZLFragment extends Fragment {
    @Bind(R.id.mine_zl_list)
    ListView mMineZLListview;
    private MineFindItemapter mMineFindItemAdapter;
//    mine_zl_list
    public MineZLFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_zl, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
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
        ArrayList<MineFindBean> arrayBean = new ArrayList<MineFindBean>();

        String ImagerURL = "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg";

        for (int i = 0; i < 10; i++) {
            MineFindBean bean = new MineFindBean();
            bean.setMineFindTime("发布时间 ：2017-06-20 10:2"+i);
            bean.setMineFindLooker("2"+i);
            bean.setMineFindFocuson("1"+i);
            bean.setMineFindMessage("11"+i);
            bean.setMineFindPrice("8"+i);
            bean.setMineFindTitle("我要找狗！！！");
            bean.setMineFindContent("我丢狗了我丢狗了我丢狗了我丢狗了我丢狗了我丢狗了");
            bean.setMineFindIng("进行中...");
            bean.setMineFindPeopleNum("线索提供(7)");
            arrayBean.add(bean);
        }

        mMineFindItemAdapter = new MineFindItemapter(getActivity(), arrayBean);
        mMineZLListview .setAdapter(mMineFindItemAdapter);

    }

}
