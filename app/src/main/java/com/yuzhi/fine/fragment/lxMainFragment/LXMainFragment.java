package com.yuzhi.fine.fragment.lxMainFragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.activity.functionActivity.LXMainAddressActivity;
import com.yuzhi.fine.activity.mainActivity.SearchActivity;
import com.yuzhi.fine.activity.mainActivity.ShaiXuanActivity;
import com.yuzhi.fine.ui.CustomViewpager;
import com.yuzhi.fine.ui.Find_tab_Adapter;
import com.yuzhi.fine.ui.GalleryPagerAdapter;
import com.yuzhi.fine.ui.GridImageAdapter;
import com.yuzhi.fine.ui.HorizontalListView;
import com.yuzhi.fine.ui.HorizontalListViewAdapter;
import com.yuzhi.fine.ui.loopviewpager.AutoLoopViewPager;
import com.yuzhi.fine.ui.viewpagerindicator.CirclePageIndicator;
import com.yuzhi.fine.utils.CommUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yuzhi.fine.utils.Constant.LX_MAIN_ADDRESS_REQUEST;
import static com.yuzhi.fine.utils.Constant.LX_MAIN_ADDRESS_RESULT;

//import com.squareup.leakcanary.RefWatcher;


public class LXMainFragment extends Fragment {

    //定位
    @Bind(R.id.lx_main_address_text)
    TextView mLxMainAddressText;
    //搜索
    @Bind(R.id.lxmain_search_layout)
    RelativeLayout mLxMainSearchLayout;
    //轮播
    @Bind(R.id.pager)
    AutoLoopViewPager pager;
    @Bind(R.id.indicator)
    CirclePageIndicator indicator;
    //筛选
    @Bind(R.id.sx)
    TextView mSX;
    @Bind(R.id.sx_img)
    ImageView mSXImg;


    private int[] imageViewIds;
    private List<String> imageList = new ArrayList<String>(Arrays.asList(
            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg",
            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg",
            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg"));
    private GalleryPagerAdapter galleryAdapter;


    //图片切换
    @Bind(R.id.lx_hor_listview_img)
    HorizontalListView mHorLViewImg;
    private HorizontalListViewAdapter hlva;


    //接口回调 2  创建接口对象
    private OnFragmentInteractionListener mListener;


    //GridView 图片封装为一个数组
    @Bind(R.id.lxmain_gridview)
    GridView mLxMainGridView;
    private Integer[] icon = {R.drawable.menu_xr, R.drawable.menu_xw,
            R.drawable.menu_zlrl, R.drawable.menu_zsjm, R.drawable.menu_wlbg,
            R.drawable.menu_wlqz, R.drawable.menu_quanzi, R.drawable.menu_shop};
    private String[] iconName = {"委托寻人", "委托寻物", "招领认领", "招商加盟", "网络曝光", "网络求助", "立寻圈子", "积分商城"};


    //TabLayout
    private TabLayout tab_FindFragment_title;                            //定义TabLayout
    private CustomViewpager vp_FindFragment_pager;                             //定义viewPager
    private FragmentPagerAdapter fAdapter;                               //定义adapter
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表
    private LXFindXSFragmet xsFragment;              //悬赏找寻服务fragment
    private LXFindPTFragment ptFragment;            //普通找寻服务fragment

    private String[] mAddressIdArray;//地区id对照表

    public LXMainFragment() {
        // Required empty public constructor
    }

    /**
     * 使用LXMainFragment传值
     *
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
        mAddressIdArray = getResources().getStringArray(R.array.address_arrays);
        //获取值
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lxmain, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        //FIXME Leak
//        RefWatcher refWatcher = AppContext.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }

    /**
     * 初始化组件
     */
    public void initView() {
        //1.轮播图片
        imageViewIds = new int[]{R.drawable.house_background, R.drawable.house_background_1, R.drawable.house_background_2};
        galleryAdapter = new GalleryPagerAdapter(imageViewIds, imageList, getActivity());
        pager.setAdapter(galleryAdapter);
        indicator.setViewPager(pager);
        indicator.setPadding(5, 5, 10, 5);

        //2. 添加元素给gridview
        GridImageAdapter adapter = new GridImageAdapter(getActivity(), icon, iconName,false);
        mLxMainGridView.setAdapter(adapter);
        CommUtil.calGridViewWidthAndHeigh(4, mLxMainGridView);

        //3.图片切换
        initGalleryViewPager();

        //4.悬赏/普通找寻服务
        findServersViewPager();


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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 接口回调 1
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    /**
     * 图片切换
     */
    private void initGalleryViewPager() {

        hlva = new HorizontalListViewAdapter(getActivity());
        hlva.notifyDataSetChanged();
        mHorLViewImg.setAdapter(hlva);

    }

    /**
     * 悬赏/普通找寻服务
     */
    public void findServersViewPager() {

        tab_FindFragment_title = (TabLayout) getActivity().findViewById(R.id.tab_FindFragment_title);
        vp_FindFragment_pager = (CustomViewpager) getActivity().findViewById(R.id.vp_FindFragment_pager);


        //初始化各fragment
        xsFragment = new LXFindXSFragmet(vp_FindFragment_pager);//悬赏找寻服务fragment
        ptFragment = new LXFindPTFragment(vp_FindFragment_pager); //普通找寻服务fragment


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
        //   getActivity().getSupportFragmentManager()会导致一个问题：数据丢失  看网上讲这边要用getChildFragmentManager()
        fAdapter = new Find_tab_Adapter(getChildFragmentManager(), list_fragment, list_title);//   getActivity().getSupportFragmentManager()会导致一个问题：数据丢失

        //viewpager加载adapter
        vp_FindFragment_pager.setAdapter(fAdapter);
//      tab_FindFragment_title.setViewPager(vp_FindFragment_pager);

        //TabLayout加载viewpager
        tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);
        tab_FindFragment_title.setTabsFromPagerAdapter(fAdapter);


        //切换viewpaper事件
        vp_FindFragment_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                vp_FindFragment_pager.resetHeight(position);

                if (position == 0) {

                    CommUtil.showAlert("xs--ok", getActivity());

                } else if (position == 1) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//                        activityScdetailsBottomVp.resetHeight(2);
//                    } else {
//                        activityScdetailsBottomVp.resetHeight(1);
//                    }
                    CommUtil.showAlert("pt--ok", getActivity());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_FindFragment_pager.resetHeight(0);


    }


    /**
     * 标题栏定位
     */
    @OnClick(R.id.lx_main_address_text)
    public void addressResult(View view){
        Intent intent = new Intent(getActivity(),LXMainAddressActivity.class);
        LXMainFragment.this.startActivityForResult(intent, LX_MAIN_ADDRESS_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LX_MAIN_ADDRESS_REQUEST){
            if (resultCode == LX_MAIN_ADDRESS_RESULT){
                mLxMainAddressText.setText(data.getStringExtra("lngCityName"));
                String addressId = getAddressId( mAddressIdArray,mLxMainAddressText.getText().toString());
                CommUtil.showAlert("addressId-->"+addressId,getActivity());
            }
        }
    }


    /**
     * 获取地区编码id
     */
    public static String getAddressId(String[] arrays , String params){
        final  int arraysNum = arrays.length;
        for (int index = 0 ; index < arraysNum ; index ++)
        {
            //1|中国
            String id = arrays[index].substring(0, arrays[index].indexOf("|"));
            String name = arrays[index].substring(arrays[index].indexOf("|")+1, arrays[index].length());
            if (params.equals(name))
            {
                return id;
            }

        }
        return "";
    }

    //搜索
    @OnClick(R.id.lxmain_search_layout)
    public void seacchLayout(View view){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        getActivity().startActivity(intent);
    }

    //筛选
    @OnClick({R.id.sx_img,R.id.sx})
    public void sxOnclick(View view){
        switch (view.getId()){
            case R.id.sx_img:
            case  R.id.sx:
                Intent intent = new Intent(getActivity(), ShaiXuanActivity.class);
                getActivity().startActivity(intent);
                break;
            default:
                break;
        }

    }

}







