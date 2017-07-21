package com.yuzhi.lixun110ccd.activity.mineActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.http.Caller;
import com.yuzhi.lixun110ccd.http.HttpClient;
import com.yuzhi.lixun110ccd.http.HttpResponseHandler;
import com.yuzhi.lixun110ccd.http.RestApiResponse;
import com.yuzhi.lixun110ccd.model.CateGoryID2Name;
import com.yuzhi.lixun110ccd.model.LXFind.FindListBean;
import com.yuzhi.lixun110ccd.ui.FragmentAdapter.MineFocusItemapter;
import com.yuzhi.lixun110ccd.ui.UIHelper;
import com.yuzhi.lixun110ccd.utils.CommUtil;
import com.yuzhi.lixun110ccd.utils.SharePreferenceUtil1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.yuzhi.lixun110ccd.utils.CommUtil.getCategoryId2Name;
import static com.yuzhi.lixun110ccd.utils.CommUtil.showToast;
import static com.yuzhi.lixun110ccd.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.lixun110ccd.utils.Constant.SHARE_LOGIN_USERID;

/**
 * 我的---我的关注
 */
public class MineFocusActivity extends AppCompatActivity implements MineFocusItemapter.IRefresh {
    private MineFocusActivity mContext;
    @Bind(R.id.btnBack)
    LinearLayout mBtnBack;
    @Bind(R.id.textHeadTitle)
    TextView mTextHeadTitle;
    @Bind(R.id.mine_focus_list)
    ListView mMineFocusListView;
    MineFocusItemapter mMineFocusAdapter;
    private ProgressDialog progress;
    SharePreferenceUtil1 share ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_focus);
        ButterKnife.bind(this);
        mContext = this;
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);
        initUI();
    }

    public void initUI(){
        mBtnBack.setVisibility(View.VISIBLE);
        mTextHeadTitle.setText("我的关注");
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
    public void initData(){
        getMineFocusData();
    }

    /**
     * 我的关注列表
     */
    private void getMineFocusData() {
        final   List<CateGoryID2Name> cateIDList =  share.getModels("categoryIDListKey", CateGoryID2Name.class);
        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        HashMap<String, String> params = new HashMap<>();
//        params.put("parentid", PARENTID_WLQZI);//发布类别父级ID
        params.put("userid", userID);//
        final ArrayList<FindListBean> arrayBean = new ArrayList<FindListBean>();
        HttpClient.get(Caller.GET_MINE_FOCUS_LIST, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                    final List<FindListBean> findList =  parseArray(data, FindListBean.class);
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
                    mMineFocusAdapter = new MineFocusItemapter(mContext, arrayBean);
                    mMineFocusListView .setAdapter(mMineFocusAdapter);
                    mMineFocusListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String publistID = findList.get(position).getPublishID();
                            String mCategoryName = getCategoryId2Name(findList.get(position).getCategoryID(),cateIDList);
                            UIHelper.showDetails(mContext,publistID,mCategoryName,0);
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
                showToast("我的关注列表获取失败", mContext);
            }
        });

    }

    @Override
    public void onRefresh(boolean refresh) {
        if (refresh){
            getMineFocusData();
        }

    }
}
