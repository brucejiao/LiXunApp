package com.yuzhi.lixun110ccd.fragment.lxMainFragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.service.LocationService;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.activity.functionActivity.LXMainAddressActivity;
import com.yuzhi.lixun110ccd.activity.mainActivity.SearchActivity;
import com.yuzhi.lixun110ccd.activity.mainActivity.ShaiXuanActivity;
import com.yuzhi.lixun110ccd.common.AppContext;
import com.yuzhi.lixun110ccd.http.Caller;
import com.yuzhi.lixun110ccd.http.HttpClient;
import com.yuzhi.lixun110ccd.http.HttpResponseHandler;
import com.yuzhi.lixun110ccd.http.RestApiResponse;
import com.yuzhi.lixun110ccd.model.MainAd;
import com.yuzhi.lixun110ccd.ui.CustomViewpager;
import com.yuzhi.lixun110ccd.ui.Find_tab_Adapter;
import com.yuzhi.lixun110ccd.ui.GalleryPagerAdapter;
import com.yuzhi.lixun110ccd.ui.GridImageAdapter;
import com.yuzhi.lixun110ccd.ui.HorizontalListView;
import com.yuzhi.lixun110ccd.ui.HorizontalListViewAdapter;
import com.yuzhi.lixun110ccd.ui.UIHelper;
import com.yuzhi.lixun110ccd.ui.loopviewpager.AutoLoopViewPager;
import com.yuzhi.lixun110ccd.ui.viewpagerindicator.CirclePageIndicator;
import com.yuzhi.lixun110ccd.utils.CommUtil;
import com.yuzhi.lixun110ccd.utils.SharePreferenceUtil1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

import static com.yuzhi.lixun110ccd.utils.CommUtil.showAlert;
import static com.yuzhi.lixun110ccd.utils.CommUtil.showToast;
import static com.yuzhi.lixun110ccd.utils.Constant.LX_MAIN_ADDRESS_REQUEST;
import static com.yuzhi.lixun110ccd.utils.Constant.LX_MAIN_ADDRESS_RESULT;
import static com.yuzhi.lixun110ccd.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.lixun110ccd.utils.Constant.SHARE_LOGIN_USERID;

//import com.squareup.leakcanary.RefWatcher;


public class LXMainFragment extends Fragment {
    private LocationService locationService;
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
    @Bind(R.id.lx_add_point)
    Button mLXAddPoint;


    private int[] imageViewIds;
//    private List<String> imageList = new ArrayList<String>(Arrays.asList(
//            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg",
//            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg",
//            "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg"));
    private GalleryPagerAdapter galleryAdapter;


    //图片切换
    @Bind(R.id.lx_hor_listview_img)
    HorizontalListView mHorLViewImg;
    private HorizontalListViewAdapter hlva;


    //接口回调 2  创建接口对象
//    private OnFragmentInteractionListener mListener;


    //GridView 图片封装为一个数组
    @Bind(R.id.lxmain_gridview)
    GridView mLxMainGridView;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private Integer[] icon = {R.drawable.menu_xr, R.drawable.menu_xw,
            R.drawable.menu_zlrl, R.drawable.menu_zsjm, R.drawable.menu_wlbg,
            R.drawable.menu_wlqz, R.drawable.menu_quanzi, R.drawable.menu_shop};
    private String[] iconName = {"委托寻人", "委托寻物", "招领认领", "人脸识别", "网络曝光", "网络求助", "立寻圈子", "公用平台"};


    //TabLayout
    private TabLayout tab_FindFragment_title;                            //定义TabLayout
    private CustomViewpager vp_FindFragment_pager;                             //定义viewPager
    private FragmentPagerAdapter fAdapter;                               //定义adapter
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表
    private LXFindXSFragmet xsFragment;              //悬赏找寻服务fragment
    private LXFindPTFragment ptFragment;            //普通找寻服务fragment
    private ProgressDialog progress;
    SharePreferenceUtil1 share ;

    private String[] mAddressIdArray;//地区id对照表

    AppContext app;

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
    }

    /**
     * 初始化组件
     */
    public void initView() {
        share = new SharePreferenceUtil1(getActivity(), "lx_data", 0);
        //1.轮播图片
        getFristAd();

        //2. 添加元素给gridview
        GridImageAdapter adapter = new GridImageAdapter(getActivity(), icon, iconName, false);
        mLxMainGridView.setAdapter(adapter);
        CommUtil.calGridViewWidthAndHeigh(4, mLxMainGridView);
        gridViewOnItemClick();

        //3.图片切换
//        initGalleryViewPager();
        //Line 670

        //4.悬赏/普通找寻服务
        findServersViewPager();

        //5.判断是否签到
        isLXAddPoint();
        //刷新
        setRefreshLayout();

    }




    /**
     * GridView 事件监听
     */
    private void gridViewOnItemClick() {
        final FragmentManager fm = getActivity().getSupportFragmentManager();
        mLxMainGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                if (0 == position) {//委托寻人
                    UIHelper.showMainWTZR(getActivity());
                } else if (1 == position) {//委托寻物
                    UIHelper.showMainWTZW(getActivity());
                } else if (2 == position) {//招领认领
                    UIHelper.showMainZLRL(getActivity());
                } else if (3 == position) {//招商加盟
                    showToast("正在开发中...", getActivity());

                } else if (4 == position) {//网络曝光
                    UIHelper.showMainWLBG(getActivity());

                } else if (5 == position) {//网络求助
                    UIHelper.showMainWLQZ(getActivity());
                } else if (6 == position) {//立寻圈子
                    UIHelper.showMainLXQZ(getActivity());
                } else if (7 == position) {//积分商城
                    showToast("正在开发中...", getActivity());
                } else {
                    return;

                }

            }

        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
     * 获取首页推荐广告(第一行)
     */
    private void getFristAd() {
        HttpClient.get(Caller.GET_MAIN_FIRST_AD, null, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();
                List<String> imageList = new ArrayList<String>();
                List<String> advList = new ArrayList<String>();
                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                    List<MainAd> findList = JSON.parseArray(data, MainAd.class);
                    final int findListNum = findList.size();
                    if(findListNum != 0){
                        for (int index = 0; index < findListNum; index++) {
                            String Imgpath = findList.get(index).getImgpath();
                            String linkurl = findList.get(index).getLinkurl();
                            imageList.add(Imgpath);
                            advList.add(linkurl);
                        }

//                        imageViewIds = new int[]{R.drawable.house_background, R.drawable.house_background_1, R.drawable.house_background_2};
                        galleryAdapter = new GalleryPagerAdapter(imageList, advList, getActivity());
                        pager.setAdapter(galleryAdapter);
                        indicator.setViewPager(pager);
                        indicator.setPadding(5, 5, 10, 5);


                    }

               } else {
                    showToast(message, getActivity());
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                showToast("图片获取失败", getActivity());
            }
        });
    }


    /**
     * 获取首页推荐广告(第二行)
     */
    private void getSecondAd(String addressID) {
        HashMap<String, String> params = new HashMap<String, String>();

        if (!CommUtil.isNullOrBlank(addressID)){
            params.put("cityid",addressID);
        }else{
            params.put("cityid","0");
        }
        HttpClient.get(Caller.GET_MAIN_SECOND_AD, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();
                ArrayList<MainAd> adBeanList = new ArrayList<MainAd>();
                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                    final List<MainAd> findList = JSON.parseArray(data, MainAd.class);
                    final int findListNum = findList.size();
                    if (CommUtil.isNullOrBlank(findList))
                    {
                        Message messageSecondAdv = new Message();
                        messageSecondAdv.what = 1001;
                        mSecondAdcHandler.sendMessage(messageSecondAdv);
                        return;
                    }
                    for (int index = 0; index < findListNum; index++) {
                        MainAd adBean = new MainAd();
                        String Title = findList.get(index).getTitle();
                        String Imgpath = findList.get(index).getImgpath();
               /*         String Backgroundcolor = findList.get(index).getBackgroundcolor();
                        String Linktype = findList.get(index).getLinktype();
                        String Linkurl = findList.get(index).getLinkurl();
                        String Cityid = findList.get(index).getCityid();*/
                        String Linkurl = findList.get(index).getLinkurl();
                        adBean.setTitle(Title);
                        adBean.setImgpath(Imgpath);
                        adBeanList.add(adBean);
                    }

                    hlva = new HorizontalListViewAdapter(getActivity(),adBeanList);
                    hlva.notifyDataSetChanged();
                    mHorLViewImg.setAdapter(hlva);
                    mHorLViewImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String Linkurl = findList.get(position).getLinkurl();
                            UIHelper.toH5Page(getActivity(),null,0,Linkurl,1);
                        }
                    });

                } else {
                    showToast(message, getActivity());
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                showToast("图片获取失败", getActivity());
            }
        });
    }

    /**
     * FIXME 先这么写   有时间再改   时间太紧  来不及构造了
     * 如果地区广告为空，调此接口展示全国的广告
     */
    private void getSecondAd() {
        HttpClient.get(Caller.GET_MAIN_SECOND_AD, null, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();
                ArrayList<MainAd> adBeanList = new ArrayList<MainAd>();
                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                    final   List<MainAd> findList = JSON.parseArray(data, MainAd.class);
                    final int findListNum = findList.size();
                    for (int index = 0; index < findListNum; index++) {
                        MainAd adBean = new MainAd();
                        String Title = findList.get(index).getTitle();
                        String Imgpath = findList.get(index).getImgpath();
               /*         String Backgroundcolor = findList.get(index).getBackgroundcolor();
                        String Linktype = findList.get(index).getLinktype();
                        String Linkurl = findList.get(index).getLinkurl();
                        String Cityid = findList.get(index).getCityid();*/
                        String Linkurl = findList.get(index).getLinkurl();
                        adBean.setTitle(Title);
                        adBean.setImgpath(Imgpath);
                        adBeanList.add(adBean);
                    }

                    hlva = new HorizontalListViewAdapter(getActivity(),adBeanList);
                    hlva.notifyDataSetChanged();
                    mHorLViewImg.setAdapter(hlva);
                    mHorLViewImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String Linkurl = findList.get(position).getLinkurl();
                            UIHelper.toH5Page(getActivity(),null,0,Linkurl,1);
                        }
                    });
                } else {
                    showToast(message, getActivity());
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                showToast("图片获取失败", getActivity());
            }
        });
    }

    /**
     * 如果地区广告为空，展示全国的广告
     */
    Handler mSecondAdcHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == 1001){
                getSecondAd();
            }
        }
    };


    /**
     * 悬赏/普通找寻服务
     */
    public void findServersViewPager() {

        tab_FindFragment_title = (TabLayout) getActivity().findViewById(R.id.tab_FindFragment_title);
        vp_FindFragment_pager = (CustomViewpager) getActivity().findViewById(R.id.vp_FindFragment_pager);


        //初始化各fragment
        ptFragment = new LXFindPTFragment(vp_FindFragment_pager); //全国寻找
        xsFragment = new LXFindXSFragmet(vp_FindFragment_pager);//地区寻找


        //将fragment装进列表中
        list_fragment = new ArrayList<Fragment>();
        list_fragment.add(ptFragment);
        list_fragment.add(xsFragment);

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<String>();
        list_title.add("全国寻找");
        list_title.add("地区寻找");

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


                } else if (position == 1) {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
//                        activityScdetailsBottomVp.resetHeight(2);
//                    } else {
//                        activityScdetailsBottomVp.resetHeight(1);
//                    }
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
    public void addressResult(View view) {
        Intent intent = new Intent(getActivity(), LXMainAddressActivity.class);
        LXMainFragment.this.startActivityForResult(intent, LX_MAIN_ADDRESS_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LX_MAIN_ADDRESS_REQUEST) {
            if (resultCode == LX_MAIN_ADDRESS_RESULT) {
                mLxMainAddressText.setText(data.getStringExtra("lngCityName"));
                String  mAddressId= getAddressId(mAddressIdArray, mLxMainAddressText.getText().toString());
                Message msg1 = new Message();
                Bundle data1 = new Bundle();
                data1.putString("addressID", mAddressId);
                msg1.setData(data1);
                msg1.what = 10001;
                addressIDHandler.sendMessage(msg1);
//                CommUtil.showAlert("addressId-->" + addressId, getActivity());
            }
        }
    }


    /**
     * 获取地区编码id
     */
    public static String getAddressId(String[] arrays, String params) {
        final int arraysNum = arrays.length;
        for (int index = 0; index < arraysNum; index++) {
            //1|中国
            String id = arrays[index].substring(0, arrays[index].indexOf("|"));
            String name = arrays[index].substring(arrays[index].indexOf("|") + 1, arrays[index].length());
            if (params.equals(name)) {
                return id;
            }

        }
        return "";
    }

    //搜索
    @OnClick(R.id.lxmain_search_layout)
    public void seacchLayout(View view) {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        getActivity().startActivity(intent);
    }

    //筛选
    @OnClick({R.id.sx_img, R.id.sx})
    public void sxOnclick(View view) {
        switch (view.getId()) {
            case R.id.sx_img:
            case R.id.sx:
                Intent intent = new Intent(getActivity(), ShaiXuanActivity.class);
                getActivity().startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 判断用户是否已签到
     */
    private void isLXAddPoint(){
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        HashMap<String, String> params = new HashMap<>();
        params.put("userid",userID);//

        HttpClient.get(Caller.IS_ADD_POINT, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {

//                    showAlert(message, getActivity());
                    mLXAddPoint.setText("已签到");

                } else {
//                    showAlert(message, getActivity());

                }
            }
            @Override
            public void onFailure(Request request, Exception e) {
                showToast("查询是否已签到失败", getActivity());
            }
        });
    }

    /**
     * 签到
     * @param view
     */
    @OnClick(R.id.lx_add_point)
    public void setmLXAddPoint(View view){
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        progress = CommUtil.showProgress(getActivity(), "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("userid",userID);//

        HttpClient.get(Caller.USER_ADD_POINT, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {

                    showAlert(message, getActivity(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (progress != null) {
                                progress.dismiss();
                            }
                        }
                    });
                    isLXAddPoint();
                } else {
                    showAlert(message, getActivity());
                    if (progress != null) {
                        progress.dismiss();
                    }
                }
            }
            @Override
            public void onFailure(Request request, Exception e) {
                if (progress != null) {
                    progress.dismiss();
                }
                showToast("悬赏列表获取失败", getActivity());
            }
        });
    }

    /**
     *获取地区ID
     * 加载首页图片
     */
    Handler addressIDHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
         switch (msg.what){
             case 10001:
                 Bundle data = msg.getData();
                 String addressID = data.getString("addressID");
                 getSecondAd(addressID);
                 break;
             case 10003:
                 Bundle data3 = msg.getData();
                 String addressID3 = data3.getString("addressID");
                 getSecondAd(addressID3);
                 locationService.unregisterListener(mListener); //注销掉监听
                 locationService.stop(); //停止定位服务
                 break;
             default:break;
         }

        }
    };

    @Override
    public void onPause() {
        super.onPause();
//        new Thread(networkTask).interrupt();

    }

    @Override
    public void onStart() {
        super.onStart();
        locationService = ((AppContext) getActivity().getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getActivity().getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK
    }

    @Override
    public void onStop() {
        super.onStop();
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }


    private void setRefreshLayout(){
        refreshLayout.setEnableRefresh(false);
        //设置 Header 为 Material风格
        refreshLayout.setRefreshHeader(new TaurusHeader(getActivity()));
        //设置 Footer 为 球脉冲
        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                getFristAd();
                //2. 添加元素给gridview
                gridViewOnItemClick();
                //3.图片切换
                //4.悬赏/普通找寻服务
                findServersViewPager();
                //5.判断是否签到
                isLXAddPoint();
            }
        });
//        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                CommUtil.showToast("shuaxin222",getActivity());
//                refreshlayout.finishLoadmore(2000);
//            }
//        });
        refreshLayout.setEnableLoadmore(false);
    }

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                mLxMainAddressText.setText(location.getCity());
                if (!CommUtil.isNullOrBlank(mLxMainAddressText.getText().toString())){
                    String  mAddressId = getAddressId(mAddressIdArray, mLxMainAddressText.getText().toString());
                    Message msg3 = new Message();
                    Bundle data3 = new Bundle();
                    data3.putString("addressID", mAddressId);
                    msg3.setData(data3);
                    msg3.what = 10003;
                    addressIDHandler.sendMessage(msg3);
                }else{
                    CommUtil.showToast("获取地址信息失败,请重试!",getActivity());
                    locationService.stop();// 定位SDK
                }
            }
        }
        public void onConnectHotSpotMessage(String s, int i){
        }
    };


}







