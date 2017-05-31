package com.yuzhi.fine.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.activity.ImageGalleryActivity;
import com.yuzhi.fine.ui.GridImageAdapter;
import com.yuzhi.fine.ui.loopviewpager.AutoLoopViewPager;
import com.yuzhi.fine.ui.viewpagerindicator.CirclePageIndicator;
import com.yuzhi.fine.ui.viewpagerindicator.FindServerVierAdapter;
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

    //初始化页面
    @Bind(R.id.tl)
    TabLayout mTabLayout;
    //添加 标题
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    //找寻服务适配器
    FindServerVierAdapter findServerAdapter;
    private List<String> mTitle = new ArrayList<String>();
    private List<String> mDatas = new ArrayList<String>();



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

        initGalleryViewPager();//图片切换

        findServersViewPager();

    }


    void initView() {
        imageViewIds = new int[] { R.drawable.house_background, R.drawable.house_background_1, R.drawable.house_background_2};

        galleryAdapter = new GalleryPagerAdapter();
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


        galleryAdapter = new GalleryPagerAdapter();
        mainviewpage.setAdapter(galleryAdapter);



    }

    //悬赏/普通找寻服务
    public void findServersViewPager(){

        /**
         *   //初始化页面
         @Bind(R.id.tab_FindFragment_title)
         TabLayout find_viewpager;
         //添加 标题
         @Bind(R.id.vp_FindFragment_pager)
         ViewPager find_viewtabpager;
         */
        mTitle.add("热门推荐");
        mTitle.add("热门收藏");

        mDatas.add("热门推荐");
        mDatas.add("热门收藏");

        findServerAdapter = new FindServerVierAdapter(getActivity(),mTitle,mDatas);
        //1，设置Tab的标题来自PagerAdapter.getPageTitle()
        mTabLayout.setTabsFromPagerAdapter(findServerAdapter);


        //2，设置TabLayout的选项卡监听
        /*
        find_viewpager.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/
        //3，设置TabLayout.TabLayoutOnPageChangeListener监听给ViewPager
        /*TabLayout.TabLayoutOnPageChangeListener listener =
                new TabLayout.TabLayoutOnPageChangeListener(mTabLayout);
        mViewPager.addOnPageChangeListener(listener);*/

        //4，viewpager设置适配器
        mViewPager.setAdapter(findServerAdapter);
        //这个方法是addOnPageChangeListener和setOnTabSelectedListener的封装。代替2,3步骤
        mTabLayout.setupWithViewPager(mViewPager);


    }

    //轮播图适配器
    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageViewIds.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView item = new ImageView(getActivity());
            item.setImageResource(imageViewIds[position]);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            item.setLayoutParams(params);
            item.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(item);

            final int pos = position;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), ImageGalleryActivity.class);
                    intent.putStringArrayListExtra("images", (ArrayList<String>) imageList);
                    intent.putExtra("position", pos);
                    startActivity(intent);
                }
            });

            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }


}




