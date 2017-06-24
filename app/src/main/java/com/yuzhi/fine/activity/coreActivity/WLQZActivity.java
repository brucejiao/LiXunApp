package com.yuzhi.fine.activity.coreActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
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
import com.yuzhi.fine.ui.FragmentAdapter.FindServerItemapter;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.utils.CommUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.yuzhi.fine.R.id.btnBack;
import static com.yuzhi.fine.utils.CommUtil.currentDate;
import static com.yuzhi.fine.utils.CommUtil.daysBetween2;
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.Constant.PARENTID_WLQZHU;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;

/**
 * 网络求助
 */
public class WLQZActivity extends AppCompatActivity {
    private WLQZActivity mContext;
    @Bind(btnBack)
    Button mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题
    //
    @Bind(R.id.lx_find_find_listview)
    ListView mFindXSListview;

    FindServerItemapter mFindItemAdapter;

    @Bind(R.id.find_new_btn)
    Button mNewBtn;//最新
    @Bind(R.id.find_top_btn)
    Button mTopBtn;//置顶
    @Bind(R.id.find_xs_btn)
    Button mXSBtn;//悬赏

    @Bind(R.id.find_third_btn)
    LinearLayout mThirdBtn;

//    @Bind(R.id.find_all)
//    TextView mFindAll;
//    @Bind(R.id.find_zzqr)
//    TextView mFindZzqr;
//    @Bind(R.id.find_zzz)
//    TextView mFindZzz;
//    @Bind(R.id.find_zpy)
//    TextView mFindZpy;
//    @Bind(R.id.find_zpy2)
//    TextView mFindZpy2;

    @Bind(R.id.checked_horlist_layout)//横向滚动布局
            LinearLayout mCheckedHorlistLayout;

    List<TextView> mSecondMenuList = new ArrayList<TextView>();//二级菜单列表
    List<String> mCategoryIDList = new ArrayList<String>();//保存二级菜单ID
    private ProgressDialog progress;
    //第一次调接口 如果出现没有数据会弹出default_page 但是默认是要加载出当前界面的
    // 加个标志位进了第一次之后再判断是否为空
    private boolean isOpenNet  = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wtxr);
        ButterKnife.bind(this);
        mContext = this;
        initUI();
        initView();
        initData();
    }

    private void initUI() {
        //返回
        mBackBtn.setVisibility(View.VISIBLE);
        //标题
        mTextHeaderTitle.setText("网络求助");
    }

    //返回
    @OnClick(btnBack)
    public void onBack(View view) {
        UIHelper.showHome(mContext);
        finish();
    }


    void initView() {
        initListView();
        //获取目标类型
        getIssueSecondList(PARENTID_WLQZHU);

    }


    private void initData() {

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
//
    }



    /**
     * 网络求助--获取发布列表
     * String categoryID
     * String toptype
     * String monetype
     */
    private void getWTXRData(String categoryID ,String toptype , String monetype) {
        HashMap<String, String> params = new HashMap<>();
        params.put("pushtype", "0");//推广类型（0所有，1推广，2不推广）
        params.put("toptype", toptype);//	置顶类型（0所有，1置顶，2不置顶）
        params.put("moneytype", monetype);//赏金类型（0所有，1有赏金，2无赏金）
        params.put("parentid", PARENTID_WLQZHU);//发布类别父级ID
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
                    List<FindListBean> findList = parseArray(data, FindListBean.class);
                    //判断当前界面是否有数据
                    //如果没有数据 则展示默认页
                    if (CommUtil.isNullOrBlank(findList)&& isOpenNet == true){
                        setContentView(R.layout.activity_nodata_default);
                        Button btnBack = (Button)findViewById((R.id.btnBack));
                        TextView textHeadTitle = (TextView)findViewById(R.id.textHeadTitle);
                        TextView default_return_mainpage = (TextView)findViewById(R.id.default_return_mainpage);
                        //返回
                        btnBack.setVisibility(View.VISIBLE);
                        //标题
                        textHeadTitle.setText("网络求助");
                        btnBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UIHelper.showMainWLQZ(mContext);
                                finish();
                            }
                        });
                        default_return_mainpage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UIHelper.showHome(mContext);
                                finish();
                            }
                        });
                        return;
                    }//if List为空
                    final int findListNum = findList.size();
                    for (int index = 0; index < findListNum; index++) {
                        isOpenNet = true;
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
                        lxFindServerBean.setPrice(money+"元");
                        lxFindServerBean.setContent(content);

                        String distanceTime = daysBetween2(createTime,currentDate());
                        lxFindServerBean.setTime(distanceTime);
                        lxFindServerBean.setLookerNum(followCount);
                        lxFindServerBean.setFocusonNum(commentCount);
                        lxFindServerBean.setMessageNum(visitCount);

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
                    mFindItemAdapter = new FindServerItemapter(mContext, arrayBean);
                    mFindXSListview.setAdapter(mFindItemAdapter);

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
                        mCategoryIDList.add(cateID);
                        checkedSecondMenu(index,menuNum,mCategoryIDList);//二级菜单获取选中/非选择效果  根据ID 获取二级菜单对应的内容
//                        byIdGetSecondContent(index,mCategoryIDList);//
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
     */
    private void checkedSecondMenu(final int index,final int size,final List<String> mCategoryIDList) {
        //默认第一位选中状态
        mSecondMenuList.get(0).setBackgroundResource(R.drawable.editsharp_green_all);
        mSecondMenuList.get(0).setTextColor(getResources().getColor(R.color.white));
        getWTXRData(mCategoryIDList.get(0),"0","0");
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
                getWTXRData(mCategoryIDList.get(index),"0","0");
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
                getWTXRData(mCategoryIDList.get(index),"1","0");
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
                getWTXRData(mCategoryIDList.get(index),"0","0");

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
                getWTXRData(mCategoryIDList.get(index),"0","1");

            }
        });
    }
}
