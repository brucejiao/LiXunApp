package com.yuzhi.fine.activity.mineActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.LXFind.FindListBean;
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
    LinearLayout mBtnBack;
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
        final    ArrayList<FindListBean> arrayBean = new ArrayList<FindListBean>();
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
                        FindListBean bean = new FindListBean();
                        //接口数据
                        bean.setPublishID(findList.get(index).getPublishID());
                        bean.setTitle(findList.get(index).getTitle());//标题
                        bean.setContent(findList.get(index).getContent());//内容
                        bean.setUserID(findList.get(index).getUserID());
                        bean.setPictureID(findList.get(index).getPictureID());
                        bean.setParentCategoryID(findList.get(index).getParentCategoryID());
                        bean.setMoney(findList.get(index).getMoney());//悬赏金
                        bean.setProvince(findList.get(index).getProvince());
                        bean.setCity(findList.get(index).getCity());
                        bean.setCountry(findList.get(index).getCountry());
                        bean.setAddress(findList.get(index).getAddress());//地址
                        bean.setPushType(findList.get(index).getPushType());//推广类型（0所有，1推广，2不推广）
                        bean.setPushMoney(findList.get(index).getPushMoney());
                        bean.setTopType(findList.get(index).getTopType());//置顶类型（0所有，1置顶，2不置顶）
                        bean.setTopMoney(findList.get(index).getTopMoney());
                        bean.setCreateTime(findList.get(index).getCreateTime());
                        bean.setUpdateTime(findList.get(index).getUpdateTime());
                        bean.setPublishStatus(findList.get(index).getPublishStatus());
                        bean.setIsDelete(findList.get(index).getIsDelete());
                        bean.setCheckState(findList.get(index).getCheckState());
                        bean.setCheckID( findList.get(index).getCheckID());
                        bean.setCheckTime(findList.get(index).getCheckTime());
                        bean.setCheckRemark(findList.get(index).getCheckRemark());
                        bean.setFollowCount(findList.get(index).getFollowCount());
                        bean.setCommentCount(findList.get(index).getCommentCount());
                        bean.setVisitCount(findList.get(index).getVisitCount());
                        bean.setClueUserName(findList.get(index).getClueUserName());
                        bean.setPaymentTypeID(findList.get(index).getPaymentTypeID());
                        bean.setPaymentTypeName(findList.get(index).getPaymentTypeName());
                        bean.setPaymentStatus(findList.get(index).getPaymentStatus());
                        bean.setDatePayOrder(findList.get(index).getDatePayOrder());
                        bean.setMoneyPaid(findList.get(index).getMoneyPaid());
                        bean.setUserName(findList.get(index).getUserName());
                        bean.setCheckUserName(findList.get(index).getCheckUserName());
                        bean.setImgFilePath(findList.get(index).getImgFilePath());
                        bean.setProvinceName(findList.get(index).getProvinceName());
                        bean.setCityName(findList.get(index).getCityName());
                        bean.setCountryName(findList.get(index).getCountryName());
                        bean.setPictureList(findList.get(index).getPictureList());
                        bean.setPicturePath(findList.get(index).getPicturePath());
                        bean.setCategoryName(findList.get(index).getCategoryName());
                        bean.setFollowTime(findList.get(index).getFollowTime());
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
