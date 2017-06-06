package com.yuzhi.fine.fragment.lxMainFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuzhi.fine.R;
import com.yuzhi.fine.ui.CustomViewpager;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/31.
 * 普通找寻服务
 */

public class LXFindPTFragment extends Fragment {

    public CustomViewpager customViewpager;

    public LXFindPTFragment(){}

    @SuppressLint("ValidFragment")
    public LXFindPTFragment(CustomViewpager customViewpager){
        this.customViewpager =  customViewpager ;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lx_find_pt, container, false);
        ButterKnife.bind(this, view);
        customViewpager.setObjectForPosition(view,1);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
