package com.yuzhi.fine.fragment.findFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.fragment.coreFragment.WLBGFragment;
import com.yuzhi.fine.fragment.coreFragment.WLQZFragment;
import com.yuzhi.fine.fragment.coreFragment.WTXRFragment;
import com.yuzhi.fine.fragment.coreFragment.WTXWFragment;
import com.yuzhi.fine.fragment.coreFragment.ZLRLFragment;
import com.yuzhi.fine.ui.CustomViewpager;
import com.yuzhi.fine.ui.Find_tab_Adapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FindMainFragment extends Fragment {

    private Activity context;

    //标题
    @Bind(R.id.textHeadTitle)
    TextView mHeader;
    //
    //TabLayout
    @Bind(R.id.find_viewpaper_title)
    TabLayout tabLayout;  //定义TabLayout
    @Bind(R.id.find_viewpaper_content)
    CustomViewpager viewpager;  //定义viewPager

    @Bind(R.id.find_hor_scro_view)
    HorizontalScrollView horizontalScrollView;  //标题列表容器


    private FragmentPagerAdapter fAdapter;                               //定义adapter
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表

    //委托寻人/委托寻物/认领招领/网络求助/网络曝光
    private WTXRFragment wtxrFragment;
    private WTXWFragment wtxwFragment;
    private ZLRLFragment zlrlFragment;
    private WLQZFragment wlqzFragment;
    private WLBGFragment wlbgFragment;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_find_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initView();
        initData();

    }

    void initView() {
        mHeader.setText("发现");
        //添加元素给gridview
        viewPaperList();



    }


    private void initData() {

    }

    /**
     * 委托寻人/委托寻物/认领招领/网络求助/网络曝光
     */
    private void viewPaperList() {
//        tabLayout = (TabLayout) getActivity().findViewById(R.id.find_viewpaper_title);
//        viewpager = (CustomViewpager) getActivity().findViewById(R.id.find_viewpaper_content);

        //初始化各fragment
        //ptFragment = new LXFindPTFragment(viewpager); //普通找寻服务fragment

        wtxrFragment = new WTXRFragment(viewpager);//
        wtxwFragment = new WTXWFragment(viewpager);
        zlrlFragment = new ZLRLFragment(viewpager);
        wlqzFragment = new WLQZFragment(viewpager);
        wlbgFragment = new WLBGFragment(viewpager);


        //将fragment装进列表中
        list_fragment = new ArrayList<Fragment>();
        list_fragment.add(wtxrFragment);
        list_fragment.add(wtxwFragment);
        list_fragment.add(zlrlFragment);
        list_fragment.add(wlqzFragment);
        list_fragment.add(wlbgFragment);

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<String>();
        list_title.add("委托寻人");
        list_title.add("委托寻物");
        list_title.add("认领招领");
        list_title.add("网络求助");
        list_title.add("网络曝光");

        //设置TabLayout的模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(3)));
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(4)));
        //   getActivity().getSupportFragmentManager()会导致一个问题：数据丢失  看网上讲这边要用getChildFragmentManager()
        fAdapter = new Find_tab_Adapter(getChildFragmentManager(), list_fragment, list_title);//getChildFragmentManager()   getActivity().getSupportFragmentManager()会导致一个问题：数据丢失

        //viewpager加载adapter
        viewpager.setAdapter(fAdapter);
//      tab_FindFragment_title.setViewPager(viewpager);

        //TabLayout加载viewpager
        tabLayout.setupWithViewPager(viewpager);
        tabLayout.setTabsFromPagerAdapter(fAdapter);


        //切换viewpaper事件
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewpager.resetHeight(position);

                switch (position) {
                    case 0:
                        horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_LEFT);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewpager.resetHeight(0);


    }

}
