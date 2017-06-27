package com.yuzhi.fine.activity.coreActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.LXFind.FindListBean;
import com.yuzhi.fine.model.LXFind.FindListPicList;
import com.yuzhi.fine.model.LXFindServerBean;
import com.yuzhi.fine.utils.CommUtil;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.yuzhi.fine.R.id.btnBack;
import static com.yuzhi.fine.R.id.details_loseaddress;
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;

/**
 * 发布详情界面
 */
public class DetailsActivity extends AppCompatActivity {
    private DetailsActivity mContext;
    @Bind(btnBack)
    Button mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题
    @Bind(R.id.details_round_header)
    RoundedImageView mDetailsRoundHeader;//头像
    @Bind(R.id.details_username)
    TextView mDetailsUserName;//用户名
    @Bind(R.id.details_certification_text)
    TextView mDetailsCertifi;//是否认证
    @Bind(R.id.details_personinfos)
    TextView mDetailsPersonInfos;//个人详情
    @Bind(R.id.details_price)
    TextView mDetailsPrice;//悬赏金额
    @Bind(R.id.details_report)
    TextView mDetailsReport;//举报
    @Bind(R.id.details_secondmenu)
    TextView mDetailSecondMenu;//二级菜单小标
    @Bind(R.id.details_title)
    TextView mDetailsTitle;//详情标题
    @Bind(R.id.details_address)
    TextView mDetailsAddress;//发布信息位置
    @Bind(R.id.details_content)
    TextView mDetailsContent;//发布内容
    @Bind(details_loseaddress)
    TextView mDetailsLoseAddress;//目标丢失地
    @Bind(R.id.details_find_people_num)
    TextView mDetailsPeoNum;//线索提供人数
    @Bind(R.id.details_images)
    LinearLayout mDetailsImages;//详情图片
    @Bind(R.id.details_peo_img1)
    RoundedImageView mDetailsPeoImg1;//头像1
    @Bind(R.id.details_peo_img2)
    RoundedImageView mDetailsPeoImg2;//头像2
    @Bind(R.id.details_peo_img3)
    RoundedImageView mDetailsPeoImg3;//头像3
    @Bind(R.id.details_peo_img4)
    RoundedImageView mDetailsPeoImg4;//头像4
    @Bind(R.id.details_peo_img5)
    RoundedImageView mDetailsPeoImg5;//头像5
    @Bind(R.id.details_peo_img6)
    RoundedImageView mDetailsPeoImg6;//头像6
    @Bind(R.id.details_peo_img7)
    RoundedImageView mDetailsPeoImg7;//头像7
    @Bind(R.id.details_review_lv)
    ListView mDetailsReviewLV;//评论区
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mContext = this ;
        ButterKnife.bind(this);

        initUI();
        initData();
    }

    private void initUI(){
        //返回
        mBackBtn.setVisibility(View.VISIBLE);
        //标题
        mTextHeaderTitle.setText("信息详情");
    }

    //返回
    @OnClick(btnBack)
    public void onBack(View view) {
        finish();
    }

    private void initData(){
        getDetailsInfos();
    }


    /**
     * 根据pushID获取发布消息详情
     */
    private void getDetailsInfos() {
        String publistID = getIntent().getStringExtra("publistID");//发布ID
        final String secondMenu = getIntent().getStringExtra("secondMenu");//二级菜单
        CommUtil.showAlert(publistID+"---"+secondMenu,mContext);


        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("publishid", publistID);

        HttpClient.get(Caller.DETAILS_ISSUE_INFOS, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                    final List<FindListBean> findList = parseArray(data, FindListBean.class);
                    final int findListNum = findList.size();
                    for (int index = 0; index < findListNum; index++) {
                        LXFindServerBean lxFindServerBean = new LXFindServerBean();
                        //接口数据
                        String publistID = findList.get(index).getPublishID();
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


//                        lxFindServerBean.setUserHeaderImg(headerImgPath);//头像
//                        lxFindServerBean.setUserName(userName);//用户名
//                        lxFindServerBean.setIsCertification("已认证");//是否认证
//                        lxFindServerBean.setTitle(title);
//                        lxFindServerBean.setIsFind("招领" + index);
//                        lxFindServerBean.setIsGenerailze(pushType.trim().equals("1")?"全国推广":"");
//                        lxFindServerBean.setAddress(provinceName + cityName + countryName);
//                        lxFindServerBean.setPrice(money+"元");
//                        lxFindServerBean.setContent(content);
//
//                        String distanceTime = daysBetween2(createTime,currentDate());
//                        lxFindServerBean.setTime(distanceTime);
//                        lxFindServerBean.setLookerNum(followCount);
//                        lxFindServerBean.setFocusonNum(commentCount);
//                        lxFindServerBean.setMessageNum(visitCount);

                        List<FindListPicList> findListPicLists = parseArray(pictueeList, FindListPicList.class);
                        //  lxFindServerBean.setImgOne(findListPicLists.get(0).getImgFilePath());

                        //////////////////////////////////////////////////////
                        //  .resize(DeviceUtil.dp2px(mContext,73), DeviceUtil.dp2px(mContext,73))
                        //头像
                        Picasso.with(mContext).load(headerImgPath).placeholder(R.drawable.default_image).into(mDetailsRoundHeader);
                        mDetailsUserName.setText(userName);
                        mDetailsPrice.setText("¥"+money+"元");
                        mDetailsAddress.setText(provinceName + cityName + countryName);
                        mDetailSecondMenu.setText(secondMenu);
                        mDetailsTitle.setText(title);
                        mDetailsContent.setText(content);
                        mDetailsLoseAddress.setText(provinceName + cityName + countryName);
                    }
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
                showToast("信息详情获取失败", mContext);
                if (progress != null) {
                    progress.dismiss();
                }
            }
        });
    }
}

