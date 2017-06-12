package com.yuzhi.fine.activity.coreActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuzhi.fine.R;
import com.yuzhi.fine.ui.UIHelper;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IssueActivity extends AppCompatActivity {

    private IssueActivity mContext = this;

    @Bind(R.id.btnBack)
    Button mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题
    @Bind(R.id.issue_userheader)
    RoundedImageView mIssueUserHeader;//用户头像
    @Bind(R.id.issue_title)
    EditText mIssueTitle;//发布标题
    @Bind(R.id.issue_title_text_number)
    TextView mIssueTextNum;//标题字数
    @Bind(R.id.issue_type)
    TextView mIssueType;//发布类型
    @Bind(R.id.issue_content)
    EditText mIssueContent;//发布内容
    @Bind(R.id.issue_content_text_number)
    TextView mIssueContentTextNum;//发布内容字数
    @Bind(R.id.issue_second_type_spiner)
    Spinner mIssueSecondType;//二级菜单（类型）
    @Bind(R.id.issue_price)
    EditText mIssuePrice;//悬赏金额
    @Bind(R.id.issue_city)
    TextView mIssueCity;//走失城市
    @Bind(R.id.issue_detail_address)
    EditText mIssueDetailAddress;//详细地址
    @Bind(R.id.issue_img_one)
    ImageView mIssueImgOne;//图片一
    @Bind(R.id.issue_img_two)
    ImageView mIssueImgTwo;//图片二
    @Bind(R.id.issue_img_three)
    ImageView mIssueImgThree;//图片三
    @Bind(R.id.issue_img_four)
    ImageView mIssueImgFour;//图片四
    @Bind(R.id.issue_img_five)
    ImageView mIssueImgFive;//图片五
    @Bind(R.id.issue_img_six)
    ImageView mIssueImgSix;//图片六
    @Bind(R.id.issue_img_seven)
    ImageView mIssueImgSeven;//图片七
    @Bind(R.id.issue_img_eight)
    ImageView mIssueImgEight;//图片八
    @Bind(R.id.issue_choose_city)
    TextView mIssueChooseCity;//选择推送地区
    @Bind(R.id.issue_choose_national)
    TextView mIssueChooseNational;//选择推送全国
    @Bind(R.id.issue_push_price)
    EditText mIssuePushPrice;//推送价格
    @Bind(R.id.issue_push_highest_price)
    TextView mIssuePushHighPrice;//推送最高价格
    @Bind(R.id.issue_area_top_no)
    TextView mIssueAreaTopNo;//地区置顶---不需要
    @Bind(R.id.issue_area_top_yes)
    TextView mIssueAreaTopYes;//地区置顶---需要
    @Bind(R.id.issue_area_top_price)
    EditText mIssueAreaTopPrice;//地区置顶价格
    @Bind(R.id.issue_area_top_highest_price)
    TextView mIssueAreaTopHighPrice;//地区置顶最高价格
    @Bind(R.id.issue_save_draft)
    Button mIssueSaveDraft;//保存到草稿箱
    @Bind(R.id.issue_now_submit)
    TextView mIssueNowSubmit;//现在发布

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_issue);
        ButterKnife.bind(this);
        initUI();
        initData();
        onClickListener();
    }

    /**
     * 初始化界面
     */
    private void initUI(){
        //返回
        mBackBtn.setVisibility(View.VISIBLE);
        //标题
        mTextHeaderTitle.setText("发布信息");

//        Picasso.with(mContext).load("http://pic.nipic.com/2008-07-11/20087119630716_2.jpg").resize(DeviceUtil.dp2px(mContext,73), DeviceUtil.dp2px(mContext,73)).placeholder(R.drawable.default_image).into(mIssueImgOne);
        hideImgView();
    }

    /**
     * 隐藏图片选择器
     */
    private  void hideImgView(){
        mIssueImgOne.setVisibility(View.VISIBLE);
        mIssueImgTwo.setVisibility(View.GONE);
        mIssueImgThree.setVisibility(View.GONE);
        mIssueImgFour.setVisibility(View.GONE);
        mIssueImgFive.setVisibility(View.GONE);
        mIssueImgSix.setVisibility(View.GONE);
        mIssueImgSeven.setVisibility(View.GONE);
        mIssueImgEight.setVisibility(View.GONE);
    }

//    private void img

    /**
     * 事件监听
     */
    private void onClickListener(){
        //返回
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showHome(mContext);
            }
        });


    }



    /**
     * 获取数据
     */
    private void initData(){
        String parentID= getIntent().getStringExtra("parentid");
        switch (parentID){
            case "80":
                mIssueType.setText("曝光");
                break;
            case "81":
                mIssueType.setText("求助");
                break;
            case "549":
                mIssueType.setText("圈子");
                break;
            case "83":
                mIssueType.setText("寻人");
                break;
            case "82":
                mIssueType.setText("寻物");
                break;
            case "394":
                mIssueType.setText("招领");
                break;
           default: break;
        }
    }
}
