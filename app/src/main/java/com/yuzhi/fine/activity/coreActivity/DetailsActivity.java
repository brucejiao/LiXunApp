package com.yuzhi.fine.activity.coreActivity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.DetailsComment.CommentBean;
import com.yuzhi.fine.model.LXFind.FindListBean;
import com.yuzhi.fine.model.LXFind.FindListPicList;
import com.yuzhi.fine.model.LXFindServerBean;
import com.yuzhi.fine.ui.AddContentDialog;
import com.yuzhi.fine.ui.FragmentAdapter.ContentItemapter;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.DeviceUtil;
import com.yuzhi.fine.utils.SharePreferenceUtil1;

import java.util.ArrayList;
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
import static com.yuzhi.fine.utils.CommUtil.subMoneyZero;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.fine.utils.Constant.SHARE_LOGIN_USERID;

/**
 * 发布详情界面
 */
public class DetailsActivity extends AppCompatActivity {
    private DetailsActivity mContext;
    @Bind(R.id.btnBack)
    LinearLayout mBackBtn;//返回
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
    @Bind(R.id.details_review_lv)
    ListView mDetailsReviewLV;//评论区

    @Bind(R.id.details_attention)
    Button mDetailsAttention;//关注
    @Bind(R.id.details_comment)
    Button mDetailsComment;//评论
    @Bind(R.id.details_chat)
    Button mDetailsChat;//聊一聊
    @Bind(R.id.details_track)
    Button mDetailsTrack;//我有线索

    private ProgressDialog progress;
    SharePreferenceUtil1 share;
    AddContentDialog mDialog;
    private int isFocusFlag = 1;//是否关注的标志位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mContext = this;
        ButterKnife.bind(this);
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);
        initUI();
        initData();
    }

    private void initUI() {
        //返回
        mBackBtn.setVisibility(View.VISIBLE);
        //标题
        mTextHeaderTitle.setText("信息详情");
        int flag = getIntent().getIntExtra("flag", 0);
        switch (flag) {
            case 0:
                //我有线索
                mDetailsTrack.setText("我有线索");
                break;
            case 1:
                //我要认领
                mDetailsTrack.setText("我要认领");
                break;
            case 2:
                //我要认领
                mDetailsTrack.setText("查看线索");
                break;
            case 3:
                //我要认领
                mDetailsTrack.setText("我有线索");
                break;
            default:
                break;
        }
    }

    //返回
    @OnClick(btnBack)
    public void onBack(View view) {
        finish();
    }

    private void initData() {
        getDetailsInfos();
        getCommentList();
        focusState();
    }


    /**
     * 根据pushID获取发布消息详情
     */
    private void getDetailsInfos() {
        String publistID = getIntent().getStringExtra("publistID");//发布ID
        final String secondMenu = getIntent().getStringExtra("secondMenu");//二级菜单
//        CommUtil.showAlert(publistID+"---"+secondMenu,mContext);


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
                    FindListBean findList = JSON.parseObject(data, FindListBean.class);

                    LXFindServerBean lxFindServerBean = new LXFindServerBean();
                    //接口数据
                    String publistID = findList.getPublishID();
                    String title = findList.getTitle();//标题
                    String content = findList.getContent();//内容
//                        findList.get(index).getUserID();
//                        findList.get(index).getPictureID();
//                        findList.get(index).getCategoryID();
                    String money = findList.getMoney();//悬赏金
//                        findList.get(index).getProvince();
//                        findList.get(index).getCity();
//                        findList.get(index).getCountry();
                    String address = findList.getAddress();//地址
                    String pushType = findList.getPushType();//推广类型（0所有，1推广，2不推广）
//                        findList.get(index).getPushMoney();
                    String topType = findList.getTopType();//置顶类型（0所有，1置顶，2不置顶）
//                        findList.get(index).getTopMoney();
                    String createTime = findList.getCreateTime();
                    String updateTime = findList.getUpdateTime();
//                        findList.get(index).getPublishStatus();
//                        findList.get(index).getIsDelete();
//                        findList.get(index).getCheckState();
//                        findList.get(index).getCheckID();
//                        findList.get(index).getCheckTime();
//                        findList.get(index).getCheckRemark();
                    String followCount = findList.getFollowCount();
                    String commentCount = findList.getCommentCount();
                    String visitCount = findList.getVisitCount();
//                        findList.get(index).getClueUserName();
//                        findList.get(index).getPaymentTypeID();
//                        findList.get(index).getPaymentTypeName();
//                        findList.get(index).getPaymentStatus();
//                        findList.get(index).getDatePayOrder();
//                        findList.get(index).getMoneyPaid();
                    String userName = findList.getUserName();
//                        findList.get(index).getCheckUserName();
                    String headerImgPath = findList.getImgFilePath();
                    String provinceName = findList.getProvinceName();
                    String cityName = findList.getCityName();
                    String countryName = findList.getCountryName();
                    String pictueeList = findList.getPictureList();///////
                    String picturePath = findList.getPicturePath();
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
                    final int findListPicListsNum = findListPicLists.size();
                    for (int index = 0; index < findListPicListsNum; index++) {
                        String picDetails = findListPicLists.get(index).getImgFilePath();
                        ImageView imageView = new ImageView(mContext);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        LinearLayout.LayoutParams mLayoutParams = new
                                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                                    LinearLayout.LayoutParams(300,300);
                        mLayoutParams.topMargin = 10;
                        mLayoutParams.gravity = Gravity.CENTER;
                        Picasso.with(mContext).load(picDetails)
                                .resize(DeviceUtil.dp2px(mContext, 290), DeviceUtil.dp2px(mContext, 375))
                                .placeholder(R.drawable.default_image).into(imageView);
                        mDetailsImages.addView(imageView, mLayoutParams);

                    }
                    //数据刷新到界面上
                    //头像
                    Picasso.with(mContext).load(headerImgPath).resize(DeviceUtil.dp2px(mContext, 50), DeviceUtil.dp2px(mContext, 50)).placeholder(R.drawable.default_image).into(mDetailsRoundHeader);
                    mDetailsUserName.setText(userName);
                    mDetailsPrice.setText("¥" + subMoneyZero(money,1) + "元");
                    mDetailsAddress.setText(address);
                    if (!CommUtil.isNullOrBlank(secondMenu)) {
                        mDetailSecondMenu.setText(secondMenu);
                    } else {
                        mDetailSecondMenu.setVisibility(View.GONE);
                    }
                    mDetailsTitle.setText(title);
                    mDetailsContent.setText(content);
                    mDetailsLoseAddress.setText(address);


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


    /**
     * 获取评论列表
     */
    private void getCommentList() {
        String publistID = getIntent().getStringExtra("publistID");//发布ID
        final String secondMenu = getIntent().getStringExtra("secondMenu");//二级菜单
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("publishid", publistID);
//        params.put("pagesize", publistID);//每页显示条数（默认10条）
//        params.put("lastnumber", publistID);//最后一条记录的ID

        HttpClient.get(Caller.DETAILS_COMMENT_LIST, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();
                ArrayList<CommentBean> commentBeanList = new ArrayList<CommentBean>();
                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                    List<CommentBean> findList = JSON.parseArray(data, CommentBean.class);
                    final int findListNum = findList.size();
                    mDetailsPeoNum.setText("评论(" + findListNum + ")");
                    for (int index = 0; index < findListNum; index++) {
                        CommentBean commentBean = new CommentBean();
                        String content = findList.get(index).getContent();//评论内容
                        String userName = findList.get(index).getUserName();//评论人
                        String createTime = findList.get(index).getCreateTime();//评论时间
                        String imgFilePath = findList.get(index).getImgFilePath();//评论人头像
                        commentBean.setContent(content);
                        commentBean.setUserName(userName);
                        commentBean.setCreateTime(createTime);
                        commentBean.setImgFilePath(imgFilePath);
                        commentBeanList.add(commentBean);
                    }
                    ContentItemapter contentItemapter = new ContentItemapter(mContext, commentBeanList);
                    mDetailsReviewLV.setAdapter(contentItemapter);
                    CommUtil.setListViewHeightBasedOnChildren(mDetailsReviewLV, contentItemapter);

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


    /**
     * 发表评论
     */
    private void addCommentItem(String content) {
        String publistID = getIntent().getStringExtra("publistID");//发布ID
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        HashMap<String, String> params = new HashMap<>();
        params.put("publishid", publistID);
        params.put("userid", userID);
        params.put("content", content);

        HttpClient.get(Caller.DETAILS_ADD_COMMENT, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                    showToast(message, mContext);
                    getCommentList();
                    mDialog.dismiss();
                } else {
                    showToast(message, mContext);
                    if (progress != null) {
                        progress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                showToast("发表评论失败", mContext);
                if (progress != null) {
                    progress.dismiss();
                }
            }
        });
    }


    /**
     * 添加关注
     */
    private void addFocus() {
        String publistID = getIntent().getStringExtra("publistID");//发布ID
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        HashMap<String, String> params = new HashMap<>();
        params.put("publishid", publistID);
        params.put("userid", userID);

        HttpClient.get(Caller.ADD_FOCUS, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
//                    showToast(message, mContext);
                } else {
                    showToast(message, mContext);
                    if (progress != null) {
                        progress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                showToast("添加关注失败", mContext);
                if (progress != null) {
                    progress.dismiss();
                }
            }
        });
    }

    /**
     * 取消关注
     */
    private void cancleFocus() {
        String publistID = getIntent().getStringExtra("publistID");//发布ID
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        HashMap<String, String> params = new HashMap<>();
        params.put("publishid", publistID);
        params.put("userid", userID);

        HttpClient.get(Caller.CANCLE_FOCUS, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
//                    showToast(message, mContext);
                } else {
                    showToast(message, mContext);
                    if (progress != null) {
                        progress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                showToast("取消关注失败", mContext);
                if (progress != null) {
                    progress.dismiss();
                }
            }
        });
    }

    /**
     * 关注状态
     */
    private void focusState() {
        String publistID = getIntent().getStringExtra("publistID");//发布ID
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        HashMap<String, String> params = new HashMap<>();
        params.put("publishid", publistID);
        params.put("userid", userID);

        HttpClient.get(Caller.FOCUS_STATE, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
//                    showToast(message, mContext);
                    isFocusFlag = 2;//能被2整除即可
                    Drawable top = getResources().getDrawable(R.drawable.info_ft_gzed);
                    mDetailsAttention.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                } else {
                    isFocusFlag = 1;
//                    showToast(message, mContext);
                    Drawable top = getResources().getDrawable(R.drawable.info_ft_gz);
                    mDetailsAttention.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                    if (progress != null) {
                        progress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                showToast("获取关注状态失败", mContext);
                if (progress != null) {
                    progress.dismiss();
                }
            }
        });
    }

    /**
     * 底部按钮监听
     *
     * @param view
     */
    @OnClick({R.id.details_attention, R.id.details_comment, R.id.details_chat, R.id.details_track})
    public void getBottomClick(View view) {
        switch (view.getId()) {
            //关注
            case R.id.details_attention:
                isFocusFlag++;
                if (isFocusFlag % 2 == 0) {
                    addFocus();
                    Drawable top = getResources().getDrawable(R.drawable.info_ft_gzed);
                    mDetailsAttention.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                } else {
                    cancleFocus();
                    Drawable top = getResources().getDrawable(R.drawable.info_ft_gz);
                    mDetailsAttention.setCompoundDrawablesWithIntrinsicBounds(null, top, null, null);
                }
                break;
            //评论
            case R.id.details_comment:
                mDialog = new AddContentDialog(mContext, R.style.MessageDialog);
                mDialog.setDialog(R.layout.dialog_add_content);
                mDialog.txt_Title.setText("发表评论");
                mDialog.txt_content.setHint("发表评论");
                mDialog.dialog_button_details.setText("确定");
                mDialog.dialog_button_cancel.setText("取消");
                mDialog.dialog_button_details.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        addCommentItem(mDialog.txt_content.getText().toString());
                    }
                });
                mDialog.dialog_button_cancel.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
                mDialog.show();
                break;
            //聊一聊
            case R.id.details_chat:
                CommUtil.showToast("333", mContext);
                break;
            //我有线索/我要认领
            case R.id.details_track:
                String publistID = getIntent().getStringExtra("publistID");//发布ID
                int flag = getIntent().getIntExtra("flag", 0);
                switch (flag) {
                    case 0:
                        //我有线索
                        UIHelper.showMineXS(mContext, publistID);
                        break;
                    case 1:
                        //我要认领
                        UIHelper.showMineRL(mContext, publistID);
                        break;
                    case 2:
                        //查看线索
                        String publistId = getIntent().getStringExtra("publistID");//发布ID
//                        CommUtil.showToast("publistId--->"+publistId,mContext);
                        UIHelper.showClueList(mContext, publistId);
                        break;
                    case 3:
                        //我有线索
                        String publistId1 = getIntent().getStringExtra("publistID");//发布ID
                        UIHelper.showMineXS(mContext, publistId1);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }


}

