package com.yuzhi.fine.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.ui.Find_tab_Adapter;
import com.yuzhi.fine.ui.GalleryPagerAdapter;
import com.yuzhi.fine.ui.GridImageAdapter;
import com.yuzhi.fine.ui.loopviewpager.AutoLoopViewPager;
import com.yuzhi.fine.ui.viewpagerindicator.CirclePageIndicator;
import com.yuzhi.fine.utils.CommUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class LXMainFragment extends Fragment {

    private Activity context;
    @Bind(R.id.pager)
    AutoLoopViewPager pager;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;

    @Bind(R.id.lxmain_gridview)
    GridView mLxMainGridView;

    @Bind(R.id.lixunviewpager)
    ViewPager mainviewpage;
//////////////////////////
   private TabLayout tab_FindFragment_title;                            //定义TabLayout
    private ViewPager vp_FindFragment_pager;                             //定义viewPager
    private FragmentPagerAdapter fAdapter;                               //定义adapter
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表
    private LXFindXSFranmet xsFragment;              //悬赏找寻服务fragment
    private LXFindPTFragment ptFragment;            //普通找寻服务fragment



    private int[] imageViewIds;
    private List<String> imageList = new ArrayList<String>(Arrays.asList(
            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg",
            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg",
            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg"));
    private GalleryPagerAdapter galleryAdapter;

    //接口回调 2  创建接口对象
    private OnFragmentInteractionListener mListener;


    // 图片封装为一个数组
    private Integer[] icon = { R.drawable.menu_xr, R.drawable.menu_xw,
            R.drawable.menu_zlrl, R.drawable.menu_zsjm, R.drawable.menu_wlbg,
            R.drawable.menu_wlqz, R.drawable.menu_quanzi, R.drawable.menu_shop };
    private String[] iconName = { "委托寻人", "委托寻物", "招领认领", "招商加盟", "网络曝光", "网络求助", "立寻圈子",
            "积分商城" };



    public LXMainFragment() {
        // Required empty public constructor
    }

    /**
     * 使用LXMainFragment传值
     * @return A new instance of fragment LXMainFragment.
     */
//    public static LXMainFragment newInstance(String param1, String param2) {
//        LXMainFragment fragment = new LXMainFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取值
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lxmain, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        findServersViewPager();//悬赏/普通找寻服务
        initGalleryViewPager();//图片切换



    }


    void initView() {
        imageViewIds = new int[] { R.drawable.house_background, R.drawable.house_background_1, R.drawable.house_background_2};

        galleryAdapter = new GalleryPagerAdapter(imageViewIds,imageList,getActivity());
        pager.setAdapter(galleryAdapter);
        indicator.setViewPager(pager);
        indicator.setPadding(5, 5, 10, 5);

        // 添加元素给gridview
        GridImageAdapter adapter =  new GridImageAdapter(getActivity(), icon, iconName);
        mLxMainGridView.setAdapter(adapter);
        CommUtil.calGridViewWidthAndHeigh(4,mLxMainGridView);


    }




    //接口赋值
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //接口对象上下文
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * 接口回调 1
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    /**
     *图片切换
     */
    private void initGalleryViewPager() {
       //PhotoViewAdapter pagerAdapter = new PhotoViewAdapter(getActivity(),(ArrayList<String>) imageList);
//        pagerAdapter.setOnItemChangeListener(new PhotoViewAdapter.OnItemChangeListener() {
//            int len = imgUrls.size();
//            @Override
//            public void onItemChange(int currentPosition) {
//                headTitle.setText((currentPosition+1) + "/" + len);
//            }
//        });






//         1.设置幕后item的缓存数目
        mainviewpage.setOffscreenPageLimit(3);
//        // 2.设置页与页之间的间距
        mainviewpage.setPageMargin(30);
//        mViewPager.setAdapter(pagerAdapter);


        galleryAdapter = new GalleryPagerAdapter(imageViewIds,imageList,getActivity());
        mainviewpage.setAdapter(galleryAdapter);



    }

    //悬赏/普通找寻服务
    public void findServersViewPager(){

        tab_FindFragment_title = (TabLayout)getActivity().findViewById(R.id.tab_FindFragment_title);
        vp_FindFragment_pager = (ViewPager)getActivity().findViewById(R.id.vp_FindFragment_pager);


        //初始化各fragment
        xsFragment = new LXFindXSFranmet();//悬赏找寻服务fragment
        ptFragment = new LXFindPTFragment(); //普通找寻服务fragment


            //将fragment装进列表中
            list_fragment = new ArrayList<Fragment>();
            list_fragment.add(xsFragment);
            list_fragment.add(ptFragment);

            //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
            list_title = new ArrayList<String>();
            list_title.add("悬赏找寻服务");
            list_title.add("普通找寻服务");

            //设置TabLayout的模式
            tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);
            //为TabLayout添加tab名称
            tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)));
            tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));

            fAdapter = new Find_tab_Adapter(getChildFragmentManager(),list_fragment,list_title);//   getActivity().getSupportFragmentManager()会导致一个问题：数据丢失

            //viewpager加载adapter
            vp_FindFragment_pager.setAdapter(fAdapter);
//            tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
            //TabLayout加载viewpager
            tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);
            tab_FindFragment_title.setTabsFromPagerAdapter(fAdapter);

        //tabLayout事件
        vp_FindFragment_pager.setCurrentItem(0);
        tab_FindFragment_title.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
              if(tab == tab_FindFragment_title.getTabAt(0)){//悬赏找寻服务
                  vp_FindFragment_pager.setCurrentItem(0);
//                    CommUtil.showAlert("okok",getActivity());

              }else if(tab == tab_FindFragment_title.getTabAt(1)){//普通找寻服务
                  vp_FindFragment_pager.setCurrentItem(1);
//                  CommUtil.showAlert("okok2",getActivity());

              }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中tab的逻辑
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //再次选中tab的逻辑

            }
        });



    }


}







