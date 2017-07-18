package com.yuzhi.fine.fragment.lxMainFragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.CateGoryID2Name;
import com.yuzhi.fine.model.LXFind.FindListBean;
import com.yuzhi.fine.model.LXFind.FindListPicList;
import com.yuzhi.fine.model.LXFindServerBean;
import com.yuzhi.fine.ui.CustomViewpager;
import com.yuzhi.fine.ui.FragmentAdapter.FindServerItemapter;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.SharePreferenceUtil1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.yuzhi.fine.utils.CommUtil.currentDate;
import static com.yuzhi.fine.utils.CommUtil.daysBetween2;
import static com.yuzhi.fine.utils.CommUtil.getCategoryId2Name;
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.CommUtil.subMoneyZero;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;

/**
 * Created by Administrator on 2017/5/31.
 *全国寻找
 */

public class LXFindPTFragment extends Fragment {
    @Bind(R.id.lx_find_pt_listview)
    ListView mFindXSListview;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @Bind(R.id.lx_top_btn)
    Button mTopBtn;//置顶
    @Bind(R.id.lx_new_btn)
    Button mNenBtn;//最新

    FindServerItemapter mFindItemAdapter;

    private ProgressDialog progress;
    private SharePreferenceUtil1 share;
    private int mFlag =0  ;//最新0  置顶 1
    private String lastnumber ="";//最后一条记录的ID

    public CustomViewpager customViewpager;
    public LXFindPTFragment(){}

    @SuppressLint("ValidFragment")
    public LXFindPTFragment(CustomViewpager customViewpager){
        this.customViewpager =  customViewpager ;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_lx_find_pt, container, false);
            ButterKnife.bind(this, view);
            customViewpager.setObjectForPosition(view,1);
            return view;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        share = new SharePreferenceUtil1(getActivity(), "lx_data", 0);
        lxFindOnClick();
        setRefreshLayout();//刷新
    }


    /**
     * 主页----悬赏找寻服务
     * moneytype : 1
     */
    private void getWTXRData(String toptype ,String number) {
        final   List<CateGoryID2Name> cateIDList =  share.getModels("categoryIDListKey", CateGoryID2Name.class);
        progress = CommUtil.showProgress(getActivity(), "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("pushtype", "1");//推广类型（0所有，1推广，2不推广）
        params.put("toptype", toptype);//	置顶类型（0所有，1置顶，2不置顶）
        params.put("moneytype", "0");//赏金类型（0所有，1有赏金，2无赏金）
//        params.put("parentid", PARENTID_WTXR);//发布类别父级ID
//        params.put("categoryid", categoryID);//发布类别ID
//        params.put("keywords", value);//搜索关键词
//        params.put("pagesize", value);//每页显示条数（默认10条）
        if(!CommUtil.isNullOrBlank(number)){
            params.put("lastnumber", number);//最后一条记录的ID
        }
        final ArrayList<LXFindServerBean> arrayBean = new ArrayList<LXFindServerBean>();
        HttpClient.get(Caller.MAIN_FIND_LIST_INFOS, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                  final  List<FindListBean> findList =  parseArray(data, FindListBean.class);
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
                        lxFindServerBean.setPrice(subMoneyZero(money,1)+"元");
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
                    if(findListNum >10){
                        String publishID = findList.get(findListNum-1).getPublishID();
                        lastnumber = publishID;
                    }
                    mFindItemAdapter = new FindServerItemapter(getActivity(), arrayBean,1);
                    mFindXSListview.setAdapter(mFindItemAdapter);
                    CommUtil.setListViewHeightBasedOnChildren(mFindXSListview, mFindItemAdapter);
                    mFindXSListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String publistID = findList.get(position).getPublishID();
                            String mCategoryName = getCategoryId2Name(findList.get(position).getCategoryID(),cateIDList);
                            UIHelper.showDetails(getActivity(),publistID,mCategoryName,0);
                        }
                    });

                    if (progress != null) {
                        progress.dismiss();
                    }

                } else {
                    showToast(message, getActivity());
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
     * 置顶/最新-按钮背景切换
     */
    public void lxFindOnClick(){
        getWTXRData("0","");
        mTopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopBtn.setTextColor(getActivity().getResources().getColor(R.color.white));
                mNenBtn.setTextColor(getActivity().getResources().getColor(R.color.black));
                mTopBtn.setBackgroundResource(R.drawable.editsharp_green_all);
                mNenBtn.setBackgroundResource(R.drawable.zuixin);
                getWTXRData("1","");
                mFlag = 1;//最新0  置顶 1

            }
        });
        mNenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopBtn.setTextColor(getActivity().getResources().getColor(R.color.black));
                mNenBtn.setTextColor(getActivity().getResources().getColor(R.color.white));
                mTopBtn.setBackgroundResource(R.drawable.zuixin);
                mNenBtn.setBackgroundResource(R.drawable.editsharp_green_all);
                getWTXRData("0","");
                mFlag =0 ;//最新0  置顶 1

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
    }

    private void setRefreshLayout(){
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000);
//
//            }
//        });
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
                ////最新0  置顶 1
                switch (mFlag){
                    case 0:
                        getWTXRData("0",lastnumber);
                        break;
                    case 1:
                        getWTXRData("1",lastnumber);
                        break;
                    default:
                        getWTXRData("0",lastnumber);
                        break;
                }
            }
        });
    }
}
