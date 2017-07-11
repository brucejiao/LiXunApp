package com.yuzhi.fine.activity.mineActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.ClueBean;
import com.yuzhi.fine.model.LXFind.FindListBean;
import com.yuzhi.fine.model.LXFind.FindListPicList;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.DeviceUtil;
import com.yuzhi.fine.utils.SharePreferenceUtil1;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.yuzhi.fine.R.id.btnBack;
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;

/**
 * 网络社交详情
 */
public class WLSJDetailsActivity extends AppCompatActivity {
    private WLSJDetailsActivity mContext;
    private ProgressDialog progress;
    private SharePreferenceUtil1 share;
    @Bind(R.id.btnBack)
    LinearLayout mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题

    @Bind(R.id.clue_details_header_img)
    RoundedImageView mDetailsHeaderImg;//头像
    @Bind(R.id.clue_details_username)
    TextView mClueDetailsUsername;//用户名
    @Bind(R.id.clue_details_state)
    TextView mClueDetailsState;//状态
    @Bind(R.id.clue_details_pic)
    RoundedImageView mClueDetailsPic;//图片
    @Bind(R.id.clue_details_title)
    TextView mClueDetailsTitle;//标题
    @Bind(R.id.clue_details_content)
    TextView mClueDetailsContent;//内容
    @Bind(R.id.clue_details_pic_layout)
    LinearLayout mClueDetailsPicLayout;//上传的图片
    @Bind(R.id.clue_details_pic_layout2)
    LinearLayout mClueDetailsPicLayout2;//上传的图片


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wlsj_details);
        ButterKnife.bind(this);
        mContext = this;
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        //返回
        mBackBtn.setVisibility(View.VISIBLE);
        //标题
        mTextHeaderTitle.setText("发布详情");
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);
        getClueDetailsInfos();
    }

    //返回
    @OnClick(btnBack)
    public void onBack(View view) {
        finish();
    }

    /**
     * 获取线索详情
     */
    private void getClueDetailsInfos() {
        String publishID = getIntent().getStringExtra("publishID");//发布ID
        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("publishid", publishID);

        HttpClient.get(Caller.DETAILS_ISSUE_INFOS, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                    ClueBean clueBean = JSON.parseObject(data, ClueBean.class);
                    String  username = clueBean.getUserName();
                    String checkState = clueBean.getCheckState();// 审核状态(1.待审核，2.审核通过，3.审核不通过)
                    String picturePath =  clueBean.getImgFilePath();//头像
                    String publishPic = clueBean.getPicturePath();//小图片
                    String publishTtile = clueBean.getPublishTitle();
                    String publishContent = clueBean.getPublishContent();

                    //头像
                    Picasso.with(mContext).load(picturePath)
                            .resize(DeviceUtil.dp2px(mContext, 35), DeviceUtil.dp2px(mContext, 35))
                            .placeholder(R.drawable.default_image).into(mDetailsHeaderImg);
                    mClueDetailsUsername.setText(username);
                    switch (checkState){
                        case "1":
                            mClueDetailsState.setText("待审核");
                            break;
                        case "2":
                            mClueDetailsState.setText("审核通过");
                            break;
                        case "3":
                            mClueDetailsState.setText("审核不通过");
                            break;
                        default:
                            break;
                    }

                    //小图片
                    Picasso.with(mContext).load(publishPic)
                            .resize(DeviceUtil.dp2px(mContext, 50), DeviceUtil.dp2px(mContext, 50))
                            .placeholder(R.drawable.default_image).into(mClueDetailsPic);
                    mClueDetailsTitle.setText(publishTtile);
                    mClueDetailsContent.setText(publishContent);

                    FindListBean findList = JSON.parseObject(data, FindListBean.class);
                    String pictueeList = findList.getPictureList();///////
                    List<FindListPicList> findListPicLists = parseArray(pictueeList, FindListPicList.class);
                    final int findListPicListsNum = findListPicLists.size();
                    for (int index = 0; index < findListPicListsNum; index++) {

                        if(index <4){
                            String picDetails = findListPicLists.get(index).getImgFilePath();
                            ImageView imageView = new ImageView(mContext);
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            LinearLayout.LayoutParams mLayoutParams = new
                                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            mLayoutParams.topMargin = 10;
                            mLayoutParams.gravity = Gravity.CENTER;
                            mLayoutParams.leftMargin = 5;
                            Picasso.with(mContext).load(picDetails)
                                    .resize(DeviceUtil.dp2px(mContext, 75), DeviceUtil.dp2px(mContext, 75))
                                    .placeholder(R.drawable.default_image).into(imageView);
                            mClueDetailsPicLayout.addView(imageView, mLayoutParams);
                        }else if(index >=4){
                            String picDetails = findListPicLists.get(index).getImgFilePath();
                            ImageView imageView = new ImageView(mContext);
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            LinearLayout.LayoutParams mLayoutParams = new
                                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            mLayoutParams.topMargin = 10;
                            mLayoutParams.gravity = Gravity.CENTER;
                            mLayoutParams.leftMargin = 5;
                            Picasso.with(mContext).load(picDetails)
                                    .resize(DeviceUtil.dp2px(mContext, 75), DeviceUtil.dp2px(mContext, 75))
                                    .placeholder(R.drawable.default_image).into(imageView);
                            mClueDetailsPicLayout2.addView(imageView, mLayoutParams);
                        }



                    }
                    ///////////////////////////////////////
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
                showToast("线索详情获取失败", mContext);
                if (progress != null) {
                    progress.dismiss();
                }
            }
        });
    }
}
