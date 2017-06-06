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
 *委托寻人
 */
public class WTXRFragment extends Fragment {

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

}
