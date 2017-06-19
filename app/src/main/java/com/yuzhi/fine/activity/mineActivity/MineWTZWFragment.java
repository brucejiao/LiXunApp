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
 * 我的--委托找物
 */
public class MineWTZWFragment extends Fragment {
    public CustomViewpager customViewpager;

    public MineWTZWFragment() {
    }

    @SuppressLint("ValidFragment")
    public MineWTZWFragment(CustomViewpager customViewpager){
        this.customViewpager =  customViewpager ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine_wtzw, container, false);
    }

}
