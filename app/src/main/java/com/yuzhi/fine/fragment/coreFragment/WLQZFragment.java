package com.yuzhi.fine.fragment.coreFragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuzhi.fine.R;
import com.yuzhi.fine.ui.CustomViewpager;

import butterknife.ButterKnife;

/**
 * 网络求助
 */
public class WLQZFragment extends Fragment {

    public CustomViewpager customViewpager;

    public WLQZFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ValidFragment")
    public WLQZFragment(CustomViewpager customViewpager){
        this.customViewpager =  customViewpager ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wlqz, container, false);
        ButterKnife.bind(this, view);
        customViewpager.setObjectForPosition(view,3);
        return view;
    }

}