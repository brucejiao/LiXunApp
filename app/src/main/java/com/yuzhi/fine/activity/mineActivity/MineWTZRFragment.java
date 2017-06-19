package com.yuzhi.fine.activity.mineActivity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuzhi.fine.R;
import com.yuzhi.fine.ui.CustomViewpager;

/**
 * 我的--委托找人
 */
public class MineWTZRFragment extends Fragment {
    public CustomViewpager customViewpager;

    public MineWTZRFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public MineWTZRFragment(CustomViewpager customViewpager){
        this.customViewpager =  customViewpager ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine_wtzr, container, false);
    }

}
