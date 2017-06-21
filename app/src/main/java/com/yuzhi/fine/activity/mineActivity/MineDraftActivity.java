package com.yuzhi.fine.activity.mineActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.LXFind.FindListBean;
import com.yuzhi.fine.model.MineDraftListBean;
import com.yuzhi.fine.ui.FragmentAdapter.MineDraftItemapter;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.SharePreferenceUtil1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.fine.utils.Constant.SHARE_LOGIN_USERID;

/**
 * 我的--草稿箱
 */
public class MineDraftActivity extends AppCompatActivity {
    private MineDraftActivity mContext;
    @Bind(R.id.mine_draftbox_list)
    ListView mMineDraftListView;
    @Bind(R.id.btnBack)
    Button mBtnBack;
    @Bind(R.id.textHeadTitle)
    TextView mTextHeadTitle;
    MineDraftItemapter mMineDraftAdapter;
    private ProgressDialog progress;
    SharePreferenceUtil1 share ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_draft);
        ButterKnife.bind(this);
        mContext = this;
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);
        initUI();
    }

    public void initUI(){
        mBtnBack.setVisibility(View.VISIBLE);
        mTextHeadTitle.setText("草稿箱");
        initData();
    }

    //返回
    @OnClick(R.id.btnBack)
    public void onBack(View view){
        finish();
    }

    /**
     * 初始化
     */
    public void initData() {
     /*   ArrayList<MineDraftListBean> arrayBean = new ArrayList<MineDraftListBean>();

        String ImagerURL = "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg";

        for (int i = 0; i < 10; i++) {
            MineDraftListBean bean = new MineDraftListBean();
            bean.setDraftTime("发布时间 ：2017-06-20 10:2"+i);
            bean.setDraftHeaderImg("2"+i);
            bean.setDraftTitle("我要找狗！！！"+i);
            bean.setDraftContent("我丢狗了我丢狗了我丢狗了我丢狗了我丢狗了我丢狗了"+i);
            bean.setDraftPrice("8"+i);
            bean.setDraftEditBtn("编辑");

            arrayBean.add(bean);
        }

        mMineDraftAdapter = new MineDraftItemapter(mContext, arrayBean);
        mMineDraftListView .setAdapter(mMineDraftAdapter);*/
        getWTXRData();
//        CommUtil.setListViewHeightBasedOnChildren(mMineRLListview, mMineFindItemAdapter);

    }

    /**
     * 获取草稿箱列表
     */
    private void getWTXRData() {
        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        HashMap<String, String> params = new HashMap<>();
//        params.put("parentid", PARENTID_WLQZI);//发布类别父级ID
        params.put("userid", userID);//
        final    ArrayList<MineDraftListBean> arrayBean = new ArrayList<MineDraftListBean>();
        HttpClient.get(Caller.MINE_DRAFT_INFOS, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                    List<FindListBean> findList =  parseArray(data, FindListBean.class);
                    final int findListNum = findList.size();
                    for (int index  = 0 ; index < findListNum ; index ++){
                        MineDraftListBean bean = new MineDraftListBean();
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


                        bean.setDraftTime("发布时间 ："+createTime);
                        bean.setDraftHeaderImg(picturePath);
                        bean.setDraftTitle(title);
                        bean.setDraftContent(content);
                        bean.setDraftPrice(money);
                        bean.setDraftEditBtn("编辑");


                        arrayBean.add(bean);
                    }
                    mMineDraftAdapter = new MineDraftItemapter(mContext, arrayBean);
                    mMineDraftListView .setAdapter(mMineDraftAdapter);


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
}
