package com.yuzhi.fine.fragment.coreFragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.yuzhi.fine.model.IssueModel.SecondMenu;
import com.yuzhi.fine.model.LXFind.FindListBean;
import com.yuzhi.fine.model.LXFind.FindListPicList;
import com.yuzhi.fine.model.LXFindServerBean;
import com.yuzhi.fine.ui.CustomViewpager;
import com.yuzhi.fine.ui.FragmentAdapter.FindServerItemapter;
import com.yuzhi.fine.ui.UIHelper;
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
import static com.yuzhi.fine.utils.CommUtil.currentDate;
import static com.yuzhi.fine.utils.CommUtil.daysBetween2;
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.CommUtil.subMoneyZero;
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

//    private ProgressDialog progress;
        @Bind(R.id.checked_horlist_layout)//横向滚动布局
        LinearLayout mCheckedHorlistLayout;
    public CustomViewpager customViewpager;
    List<TextView> mSecondMenuList = new ArrayList<TextView>();//二级菜单TextView列表
    List<String> mSecondMenu= new ArrayList<String>();//二级菜单名称列表
    List<String> mCategoryIDList = new ArrayList<String>();//保存二级菜单ID
    private ProgressDialog progress;
    private boolean isLoadAll;
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

    }


    private void initData() {
        //获取目标类型
        getIssueSecondList(PARENTID_WTXR);

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
                params.height=screenHeight-DeviceUtil.dp2px(getActivity(),height2)- DeviceUtil.dp2px(getActivity(),height3) - DeviceUtil.dp2px(getActivity(),42);//35设置当前控件布局的高度
//        CommUtil.showAlert("Height："+ params.height,context);
                mFindXSListview.setLayoutParams(params);//将设置好的布局参数应用到控件中
                break;
            case 1://thirdShrink
                //获取当前控件的布局对象
                params.height=screenHeight-DeviceUtil.dp2px(getActivity(),height2)- DeviceUtil.dp2px(getActivity(),height3) - DeviceUtil.dp2px(getActivity(),10);//设置当前控件布局的高度
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
    private void getWTXRData(String categoryID ,String toptype , String monetype,final String secondmenu) {

//        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("pushtype", "0");//推广类型（0所有，1推广，2不推广）
        params.put("toptype", toptype);//	置顶类型（0所有，1置顶，2不置顶）
        params.put("moneytype", monetype);//赏金类型（0所有，1有赏金，2无赏金）
        params.put("parentid", PARENTID_WTXR);//发布类别父级ID
        params.put("categoryid", categoryID);//发布类别ID
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
                   final List<FindListBean> findList =  parseArray(data, FindListBean.class);
                    final int findListNum = findList.size();
                    for (int index  = 0 ; index < findListNum ; index ++){
                        LXFindServerBean lxFindServerBean = new LXFindServerBean();
                        //接口数据
//                        findList.get(index).getPublishID();
                        String title = findList.get(index).getTitle();//标题
                        String content = findList.get(index).getContent();//内容
//                        findList.get(index).getUserID();
//                        findList.get(index).getPictureID();
//                        findList.get(index).getCategoryID();
                        String money = findList.get(index).getMoney();//悬赏金
//                        findList.get(index).getProvince();
//                        findList.get(index).getCity();
//                        findList.get(index).getCountry();
                        String address = findList.get(index).getAddress();//地址
                        String pushType = findList.get(index).getPushType();//推广类型（0所有，1推广，2不推广）
//                        findList.get(index).getPushMoney();
                        String topType = findList.get(index).getTopType();//置顶类型（0所有，1置顶，2不置顶）
//                        findList.get(index).getTopMoney();
                        String createTime = findList.get(index).getCreateTime();
                        String updateTime = findList.get(index).getUpdateTime();
//                        findList.get(index).getPublishStatus();
//                        findList.get(index).getIsDelete();
//                        findList.get(index).getCheckState();
//                        findList.get(index).getCheckID();
//                        findList.get(index).getCheckTime();
//                        findList.get(index).getCheckRemark();
                        String followCount = findList.get(index).getFollowCount();
                        String commentCount = findList.get(index).getCommentCount();
                        String visitCount = findList.get(index).getVisitCount();
//                        findList.get(index).getClueUserName();
//                        findList.get(index).getPaymentTypeID();
//                        findList.get(index).getPaymentTypeName();
//                        findList.get(index).getPaymentStatus();
//                        findList.get(index).getDatePayOrder();
//                        findList.get(index).getMoneyPaid();
                        String userName = findList.get(index).getUserName();
//                        findList.get(index).getCheckUserName();
                        String headerImgPath = findList.get(index).getImgFilePath();
                        String provinceName = findList.get(index).getProvinceName();
                        String cityName = findList.get(index).getCityName();
                        String countryName = findList.get(index).getCountryName();
                        String pictueeList = findList.get(index).getPictureList();///////
                        String picturePath = findList.get(index).getPicturePath();
//                        findList.get(index).getCategoryName();
//                        findList.get(index).getFollowTime();


                        lxFindServerBean.setUserHeaderImg(headerImgPath);//头像
                        lxFindServerBean.setUserName(userName);//用户名
                        lxFindServerBean.setIsCertification("已认证");//是否认证
                        lxFindServerBean.setTitle(title);
                        lxFindServerBean.setIsFind("招领" + index);
                        lxFindServerBean.setIsGenerailze(pushType.trim().equals("1")?"全国推广":"");
                        lxFindServerBean.setAddress(provinceName + cityName + countryName);
                        lxFindServerBean.setPrice(subMoneyZero(money)+"元");
                        lxFindServerBean.setContent(content);

                        String distanceTime = daysBetween2(createTime,currentDate());
                        lxFindServerBean.setTime(distanceTime);
                        lxFindServerBean.setLookerNum(visitCount);
                        lxFindServerBean.setFocusonNum(followCount);
                        lxFindServerBean.setMessageNum(commentCount);

                        List<FindListPicList> findListPicLists = parseArray(pictueeList, FindListPicList.class);
                        final int findPicNum = findListPicLists.size();
                        if (findPicNum == 0) {
                            lxFindServerBean.setImgOne("");
                            lxFindServerBean.setImgTwo("");
                            lxFindServerBean.setImgThree("");
                        }
                        if (findPicNum == 1) {
                            lxFindServerBean.setImgOne(findListPicLists.get(0).getImgFilePath());
                            lxFindServerBean.setImgTwo("");
                            lxFindServerBean.setImgThree("");
                        }
                        if (findPicNum == 2) {
                            lxFindServerBean.setImgOne(findListPicLists.get(0).getImgFilePath());
                            lxFindServerBean.setImgTwo(findListPicLists.get(1).getImgFilePath());
                            lxFindServerBean.setImgThree("");
                        }
                        if (findPicNum >= 3) {
                            lxFindServerBean.setImgOne(findListPicLists.get(0).getImgFilePath());
                            lxFindServerBean.setImgTwo(findListPicLists.get(1).getImgFilePath());
                            lxFindServerBean.setImgThree(findListPicLists.get(2).getImgFilePath());
                        }
                        arrayBean.add(lxFindServerBean);
                    }
                    mFindItemAdapter = new FindServerItemapter(getActivity(), arrayBean,2);
                    mFindXSListview.setAdapter(mFindItemAdapter);
                    mFindXSListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String publistID = findList.get(position).getPublishID();
                            UIHelper.showDetails(mContext,publistID,secondmenu,0);
                        }
                    });
                    if (progress != null) {
                        progress.dismiss();
                    }

                } else {
                    showToast(message, mContext);
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
                showToast("发现列表获取失败", mContext);
            }
        });

    }


    /**
     * 目标类型--获取发布类别列表（二级）
     */
    private void getIssueSecondList(String value) {
//        mFindXSListview.setLoadMoreViewTextLoading();
        mCategoryIDList.clear();
        mSecondMenuList.clear();
        mSecondMenu.clear();
        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("parentid", value);

        HttpClient.get(Caller.ISSUE_TYPE_SECOND_LIST, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                    List<SecondMenu> menu = JSON.parseArray(data, SecondMenu.class);
                    final int menuNum = menu.size();
                    for (int index = 0; index < menuNum; index++) {
                        TextView mTextView = new TextView(mContext);
                        String cateID = menu.get(index).getCategoryID();
                        String cateTitle = menu.get(index).getCategoryTitle();

                        mTextView.setText(cateTitle);
                        mTextView.setTextSize(10);
                        mTextView.setTextColor(getResources().getColor(R.color.black));
                        mTextView.setGravity(Gravity.CENTER);
                        LinearLayout.LayoutParams mLayoutParams = new
//                                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                                LinearLayout.LayoutParams(180,60);
                        mLayoutParams.leftMargin = 35;
                        mLayoutParams.gravity= Gravity.CENTER;
                        mCheckedHorlistLayout.addView(mTextView, mLayoutParams);
                        mSecondMenuList.add(mTextView);
                        mSecondMenu.add(cateTitle);
                        mCategoryIDList.add(cateID);
                        checkedSecondMenu(index,menuNum,mCategoryIDList,mSecondMenu);//二级菜单获取选中/非选择效果  根据ID 获取二级菜单对应的内容
                    }
                } else {
                    showToast(message, mContext);
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                showToast("二级菜单获取失败", mContext);
            }
        });
    }

    /**
     * 二级菜单获取选中/非选择效果
     *
     */
    private void checkedSecondMenu(final int index,final int size,final List<String> mCategoryIDList,final List<String> mSecondMenu) {
//        CommUtil.showToast("index-->"+index+"\nsize-->"+size+"\nList-->"+mCategoryIDList.size(),mContext);
        //默认第一位选中状态
        mSecondMenuList.get(0).setBackgroundResource(R.drawable.editsharp_green_all);
        mSecondMenuList.get(0).setTextColor(getResources().getColor(R.color.white));
        getWTXRData(mCategoryIDList.get(0),"0","0",mSecondMenu.get(0));
        //筛选置顶/悬赏...
        lxFindOnClick(0);
        mSecondMenuList.get(index).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先将所有的TextView 刷成白色
                for (int i = 0 ;i < size; i++){
                    mSecondMenuList.get(i).setBackgroundResource(R.color.white);
                    mSecondMenuList.get(i).setTextColor(getResources().getColor(R.color.black));
                }
                //单独将选中的刷绿
                mSecondMenuList.get(index).setBackgroundResource(R.drawable.editsharp_green_all);
                mSecondMenuList.get(index).setTextColor(getResources().getColor(R.color.white));
                getWTXRData(mCategoryIDList.get(index),"0","0",mSecondMenu.get(index));
                //筛选置顶/悬赏...
                lxFindOnClick(index);
            }
        });
    }

    /**
     * 置顶/最新-按钮背景切换
     */
    public void lxFindOnClick(final int index) {
        mTopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopBtn.setTextColor(mContext.getResources().getColor(R.color.white));
                mNewBtn.setTextColor(mContext.getResources().getColor(R.color.black));
                mXSBtn.setTextColor(mContext.getResources().getColor(R.color.black));
                mTopBtn.setBackgroundResource(R.drawable.editsharp_green_all);
                mNewBtn.setBackgroundResource(R.drawable.zuixin);
                mXSBtn.setBackgroundResource(R.drawable.zuixin);
                getWTXRData(mCategoryIDList.get(index),"1","0",mSecondMenu.get(index));
            }
        });
        mNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewBtn.setTextColor(mContext.getResources().getColor(R.color.white));
                mTopBtn.setTextColor(mContext.getResources().getColor(R.color.black));
                mXSBtn.setTextColor(mContext.getResources().getColor(R.color.black));
                mNewBtn.setBackgroundResource(R.drawable.editsharp_green_all);
                mTopBtn.setBackgroundResource(R.drawable.zuixin);
                mXSBtn.setBackgroundResource(R.drawable.zuixin);
                getWTXRData(mCategoryIDList.get(index),"0","0",mSecondMenu.get(index));

            }
        });

        mXSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mXSBtn.setTextColor(mContext.getResources().getColor(R.color.white));
                mTopBtn.setTextColor(mContext.getResources().getColor(R.color.black));
                mNewBtn.setTextColor(mContext.getResources().getColor(R.color.black));
                mXSBtn.setBackgroundResource(R.drawable.editsharp_green_all);
                mTopBtn.setBackgroundResource(R.drawable.zuixin);
                mNewBtn.setBackgroundResource(R.drawable.zuixin);
                getWTXRData(mCategoryIDList.get(index),"0","1",mSecondMenu.get(index));

            }
        });
    }


}