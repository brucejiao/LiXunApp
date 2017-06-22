package com.yuzhi.fine.fragment.coreFragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.LXFind.FindListBean;
import com.yuzhi.fine.model.LXFind.FindListPicList;
import com.yuzhi.fine.model.LXFindServerBean;
import com.yuzhi.fine.ui.CustomViewpager;
import com.yuzhi.fine.ui.FragmentAdapter.FindServerItemapter;
import com.yuzhi.fine.utils.AnimationUtil;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.DeviceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.Constant.PARENTID_WTXR;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;


/**
 *委托寻人
 */
public class WTXRFragment extends Fragment {
    private Activity mContext ;
    //
    @Bind(R.id.shaixuan_textview)
    TextView mSXLayout;

    @Bind(R.id.lx_find_find_listview)
    ListView mFindXSListview;

    FindServerItemapter mFindItemAdapter;

    @Bind(R.id.find_new_btn)
    Button mNewBtn;//最新
    @Bind(R.id.find_top_btn)
    Button mTopBtn;//置顶
    @Bind(R.id.find_xs_btn)
    Button mXSBtn;//悬赏

    @Bind(R.id.find_second_list)
    LinearLayout mFindSecondList;//
    @Bind(R.id.find_third_btn)
    LinearLayout mThirdBtn;

    @Bind(R.id.find_all)
    TextView mFindAll;
    @Bind(R.id.find_zzqr)
    TextView mFindZzqr;
    @Bind(R.id.find_zzz)
    TextView mFindZzz;
    @Bind(R.id.find_zpy)
    TextView mFindZpy;
    @Bind(R.id.find_zpy2)
    TextView mFindZpy2;
//    private ProgressDialog progress;

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initView();
        initData();

    }

    int isShowFlag =1;
    void initView() {
        scaleListViewHigh(1);
        //判断三级菜单是否显示
        mSXLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShowFlag++;
                if (isShowFlag%2 ==0)
                {
                    scaleListViewHigh(0);//显示三级菜单listview高度
                    mThirdBtn.setVisibility(View.VISIBLE);
                    mThirdBtn.setAnimation(AnimationUtil.moveToViewLocation());

                }else{
                    scaleListViewHigh(1);//隐藏三级菜单listview高度
                    mThirdBtn.setVisibility(View.GONE);
                    mThirdBtn.setAnimation(AnimationUtil.moveToViewBottom());
                }



            }
        });
        initListView();
    }


    private void initData() {
        getWTXRData();
    }


    public void initListView() {
/*        ArrayList<LXFindServerBean> arrayBean = new ArrayList<LXFindServerBean>();

        String ImagerURL = "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg";

        for (int i = 0; i < 10; i++) {
            LXFindServerBean bean = new LXFindServerBean();
//            bean.setUserHeaderImg(ImagerURL);//头像
            bean.setUserName("阿" + i);//用户名
            bean.setIsCertification("已认证");//是否认证
            bean.setTitle("丢了丢了丢了");
            bean.setIsFind("招领" + i);
            bean.setIsGenerailze("宇宙推广" + i);
            bean.setAddress("男爵领地");
            bean.setPrice("50元");
            bean.setContent("我有一只小毛驴，我从来也不骑，我有一我骑着它去法克你一一一");
//            bean.setImgOne(ImagerURL);
//            bean.setImgTwo(ImagerURL);
//            bean.setImgThree(ImagerURL);
            bean.setTime(i + "分钟前发布");
            bean.setLookerNum("14");
            bean.setFocusonNum("21");
            bean.setMessageNum("35");

            arrayBean.add(bean);
        }


        mFindItemAdapter = new FindServerItemapter(getActivity(), arrayBean);
//        CommUtil.setListViewHeightBasedOnChildren(mFindXSListview);
        mFindXSListview.setAdapter(mFindItemAdapter);
//        CommUtil.setListViewHeightBasedOnChildren(mFindXSListview, mFindItemAdapter);
//        CommUtil.fixListViewHeight(mFindXSListview);*/

        lxFindOnClick();
        secondListShow();
    }

    /**
     * 置顶/最新-按钮背景切换
     */
    public void lxFindOnClick(){
        mTopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopBtn.setTextColor(getActivity().getResources().getColor(R.color.white));
                mNewBtn.setTextColor(getActivity().getResources().getColor(R.color.black));
                mXSBtn.setTextColor(getActivity().getResources().getColor(R.color.black));
                mTopBtn.setBackgroundResource(R.drawable.editsharp_green_all);
                mNewBtn.setBackgroundResource(R.drawable.zuixin);
                mXSBtn.setBackgroundResource(R.drawable.zuixin);

            }
        });
        mNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewBtn.setTextColor(getActivity().getResources().getColor(R.color.white));
                mTopBtn.setTextColor(getActivity().getResources().getColor(R.color.black));
                mXSBtn.setTextColor(getActivity().getResources().getColor(R.color.black));
                mNewBtn.setBackgroundResource(R.drawable.editsharp_green_all);
                mTopBtn.setBackgroundResource(R.drawable.zuixin);
                mXSBtn.setBackgroundResource(R.drawable.zuixin);

            }
        });

        mXSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXSBtn.setTextColor(getActivity().getResources().getColor(R.color.white));
                mTopBtn.setTextColor(getActivity().getResources().getColor(R.color.black));
                mNewBtn.setTextColor(getActivity().getResources().getColor(R.color.black));
                mXSBtn.setBackgroundResource(R.drawable.editsharp_green_all);
                mTopBtn.setBackgroundResource(R.drawable.zuixin);
                mNewBtn.setBackgroundResource(R.drawable.zuixin);

            }
        });
    }

    /**
     * 设置二级菜单选中状态
     */
    private void secondListShow(){

        mFindAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFindAll.setBackgroundResource(R.drawable.editsharp_green_all);
                mFindZzqr.setBackgroundResource(R.color.white);
                mFindZzz.setBackgroundResource(R.color.white);
                mFindZpy.setBackgroundResource(R.color.white);
                mFindZpy2.setBackgroundResource(R.color.white);
            }
        });
        mFindZzqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFindZzqr.setBackgroundResource(R.drawable.editsharp_green_all);
                mFindAll.setBackgroundResource(R.color.white);
                mFindZzz.setBackgroundResource(R.color.white);
                mFindZpy.setBackgroundResource(R.color.white);
                mFindZpy2.setBackgroundResource(R.color.white);
            }
        });
        mFindZzz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFindZzz.setBackgroundResource(R.drawable.editsharp_green_all);
                mFindZzqr.setBackgroundResource(R.color.white);
                mFindAll.setBackgroundResource(R.color.white);
                mFindZpy.setBackgroundResource(R.color.white);
                mFindZpy2.setBackgroundResource(R.color.white);
            }
        });
        mFindZpy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFindZpy.setBackgroundResource(R.drawable.editsharp_green_all);
                mFindZzqr.setBackgroundResource(R.color.white);
                mFindZzz.setBackgroundResource(R.color.white);
                mFindAll.setBackgroundResource(R.color.white);
                mFindZpy2.setBackgroundResource(R.color.white);
            }
        });
        mFindZpy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFindZpy2.setBackgroundResource(R.drawable.editsharp_green_all);
                mFindZzqr.setBackgroundResource(R.color.white);
                mFindZzz.setBackgroundResource(R.color.white);
                mFindZpy.setBackgroundResource(R.color.white);
                mFindAll.setBackgroundResource(R.color.white);
            }
        });


    }

    /**
     * 计算列表高度
     * @param Flag
     */
    private void scaleListViewHigh(int Flag){
        //得到全屏高宽度
        int screenHeight=  DeviceUtil.getHeight(getActivity());
        //二级菜单的高度
        mFindSecondList.measure(0,0);
        //获取组件的宽度
        int width2=mFindSecondList.getMeasuredWidth();
        //获取组件的高度
        int height2=mFindSecondList.getMeasuredHeight();
        //三级菜单的高度
        mThirdBtn.measure(0,0);
        //获取组件的宽度
        int width3=mThirdBtn.getMeasuredWidth();
        //获取组件的高度
        int height3=mThirdBtn.getMeasuredHeight();

        //获取当前控件的布局对象
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) mFindXSListview.getLayoutParams();
        switch (Flag){
            case 0://thirdExpand
                //770
                params.height=screenHeight-DeviceUtil.dp2px(getActivity(),height2)- DeviceUtil.dp2px(getActivity(),height3) - DeviceUtil.dp2px(getActivity(),35);//设置当前控件布局的高度
//        CommUtil.showAlert("Height："+ params.height,context);
                mFindXSListview.setLayoutParams(params);//将设置好的布局参数应用到控件中
                break;
            case 1://thirdShrink
                //获取当前控件的布局对象
                params.height=screenHeight-DeviceUtil.dp2px(getActivity(),height2)- DeviceUtil.dp2px(getActivity(),height3) - DeviceUtil.dp2px(getActivity(),4);//设置当前控件布局的高度
//        CommUtil.showAlert("Height："+ params.height,context);
                mFindXSListview.setLayoutParams(params);//将设置好的布局参数应用到控件中
                break;
            default:
                break;
        }
    }


    /**
     * 委托寻人--获取发布列表
     */
    private void getWTXRData() {
//        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
//        params.put("pushtype", "0");//推广类型（0所有，1推广，2不推广）
//        params.put("toptype", "0");//	置顶类型（0所有，1置顶，2不置顶）
//        params.put("moneytype", "0");//赏金类型（0所有，1有赏金，2无赏金）
        params.put("parentid", PARENTID_WTXR);//发布类别父级ID
//        params.put("categoryid", value);//发布类别ID
//        params.put("keywords", value);//搜索关键词
//        params.put("pagesize", value);//每页显示条数（默认10条）
//        params.put("lastnumber", value);//最后一条记录的ID
       final ArrayList<LXFindServerBean> arrayBean = new ArrayList<LXFindServerBean>();
        HttpClient.get(Caller.FIND_LIST_INFOS, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                    List<FindListBean> findList =  parseArray(data, FindListBean.class);
                    final int findListNum = findList.size();
                    for (int index  = 0 ; index < findListNum ; index ++){
                        LXFindServerBean lxFindServerBean = new LXFindServerBean();
                        //接口数据
//                        findList.get(index).getPublishID();
                         String title     = findList.get(index).getTitle();//标题
                         String content   = findList.get(index).getContent();//内容
//                        findList.get(index).getUserID();
//                        findList.get(index).getPictureID();
//                        findList.get(index).getCategoryID();
                        String money     = findList.get(index).getMoney();//悬赏金
//                        findList.get(index).getProvince();
//                        findList.get(index).getCity();
//                        findList.get(index).getCountry();
                        String address     = findList.get(index).getAddress();//地址
                        String  pushType   = findList.get(index).getPushType();//推广类型（0所有，1推广，2不推广）
//                        findList.get(index).getPushMoney();
                        String topType     = findList.get(index).getTopType();//置顶类型（0所有，1置顶，2不置顶）
//                        findList.get(index).getTopMoney();
                        String createTime  = findList.get(index).getCreateTime();
                        String updateTime  = findList.get(index).getUpdateTime();
//                        findList.get(index).getPublishStatus();
//                        findList.get(index).getIsDelete();
//                        findList.get(index).getCheckState();
//                        findList.get(index).getCheckID();
//                        findList.get(index).getCheckTime();
//                        findList.get(index).getCheckRemark();
                        String followCount   =  findList.get(index).getFollowCount();
                        String commentCount  =  findList.get(index).getCommentCount();
                        String visitCount    = findList.get(index).getVisitCount();
//                        findList.get(index).getClueUserName();
//                        findList.get(index).getPaymentTypeID();
//                        findList.get(index).getPaymentTypeName();
//                        findList.get(index).getPaymentStatus();
//                        findList.get(index).getDatePayOrder();
//                        findList.get(index).getMoneyPaid();
                        String userName      = findList.get(index).getUserName();
//                        findList.get(index).getCheckUserName();
                        String headerImgPath = findList.get(index).getImgFilePath();
                        String provinceName  = findList.get(index).getProvinceName();
                        String cityName      = findList.get(index).getCityName();
                        String countryName   = findList.get(index).getCountryName();
                        String pictueeList   = findList.get(index).getPictureList();///////
                        String picturePath   = findList.get(index).getPicturePath();
//                        findList.get(index).getCategoryName();
//                        findList.get(index).getFollowTime();



                        lxFindServerBean.setUserHeaderImg(headerImgPath);//头像
                        lxFindServerBean.setUserName(userName);//用户名
                        lxFindServerBean.setIsCertification("已认证");//是否认证
                        lxFindServerBean.setTitle(title);
                        lxFindServerBean.setIsFind("招领" + index);
                        lxFindServerBean.setIsGenerailze("宇宙推广" + index);
                        lxFindServerBean.setAddress(provinceName+cityName+countryName);
                        lxFindServerBean.setPrice(money);
                        lxFindServerBean.setContent(content);

                        lxFindServerBean.setTime(index + "分钟前发布");
                        lxFindServerBean.setLookerNum(followCount);
                        lxFindServerBean.setFocusonNum(commentCount);
                        lxFindServerBean.setMessageNum(visitCount);

                        List<FindListPicList> findListPicLists = JSON.parseArray(pictueeList, FindListPicList.class);
                        final int findPicNum = findListPicLists.size();
                        if (findPicNum == 0){
                            lxFindServerBean.setImgOne("");
                            lxFindServerBean.setImgTwo("");
                            lxFindServerBean.setImgThree("");
                        }
                        if (findPicNum == 1){
                            lxFindServerBean.setImgOne(findListPicLists.get(0).getImgFilePath());
                            lxFindServerBean.setImgTwo("");
                            lxFindServerBean.setImgThree("");
                        }
                        if (findPicNum == 2){
                            lxFindServerBean.setImgOne(findListPicLists.get(0).getImgFilePath());
                            lxFindServerBean.setImgTwo(findListPicLists.get(1).getImgFilePath());
                            lxFindServerBean.setImgThree("");
                        }
                        if (findPicNum >= 3){
                            lxFindServerBean.setImgOne(findListPicLists.get(0).getImgFilePath());
                            lxFindServerBean.setImgTwo(findListPicLists.get(1).getImgFilePath());
                            lxFindServerBean.setImgThree(findListPicLists.get(2).getImgFilePath());
                        }
                        arrayBean.add(lxFindServerBean);
                    }
                    mFindItemAdapter = new FindServerItemapter(getActivity(), arrayBean);
                    mFindXSListview.setAdapter(mFindItemAdapter);

/*
                    if (progress != null) {
                        progress.dismiss();
                    }*/

                } else {
                    showToast(message, mContext);
                    /*if (progress != null) {
                        progress.dismiss();
                    }*/
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                /*if (progress != null) {
                    progress.dismiss();
                }*/
                showToast("发现列表获取失败", mContext);
            }
        });

    }
}