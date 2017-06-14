package com.yuzhi.fine.activity.coreActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpRequestUtil;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.AddressDtailsEntity;
import com.yuzhi.fine.model.AddressModel;
import com.yuzhi.fine.model.IssueModel.SecondMenu;
import com.yuzhi.fine.ui.ChooseAddressWheel;
import com.yuzhi.fine.ui.SpinnerArrayAdapter;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.ui.wheelview.listener.OnAddressChangeListener;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.Constant;
import com.yuzhi.fine.utils.JsonUtil;
import com.yuzhi.fine.utils.LocationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

import static com.yuzhi.fine.http.Caller.BAIDU_MAP_LOCATION;
import static com.yuzhi.fine.utils.CommUtil.getAddressId;
import static com.yuzhi.fine.utils.CommUtil.readAssert;
import static com.yuzhi.fine.utils.CommUtil.showAlert;

public class IssueActivity extends AppCompatActivity implements OnAddressChangeListener {

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

    private SpinnerArrayAdapter mSpinnerAdapter;// 自定义spinner
    private ChooseAddressWheel chooseAddressWheel = null;
    private String[] mAddressIdArray;//地区id对照表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_issue);
        ButterKnife.bind(this);
        mAddressIdArray = getResources().getStringArray(R.array.address_arrays);
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

        initWheel();



    }

    /**
     * 初始化地区选择滚轮
     */
    private void initWheel() {
        chooseAddressWheel = new ChooseAddressWheel(this);
        chooseAddressWheel.setOnAddressChangeListener(this);

        String address = readAssert(this, "address.txt");
        AddressModel model = JsonUtil.parseJson(address, AddressModel.class);
        if (model != null) {
            AddressDtailsEntity data = model.Result;
            if (data == null) return;
            mIssueCity.setText(data.Province + " " + data.City /*+ " " + data.Area*/);

            String provinceId = getAddressId(mAddressIdArray,data.Province);
            String cityId     = getAddressId(mAddressIdArray,data.City);
            showAlert(provinceId+"---"+cityId,mContext);

            if (data.ProvinceItems != null && data.ProvinceItems.Province != null) {

                chooseAddressWheel.setProvince(data.ProvinceItems.Province);
                chooseAddressWheel.defaultValue(data.Province, data.City, data.Area);
            }
        }
    }

    /**
     * 选择地区事件
     * @param view
     */
    @OnClick(R.id.issue_city)
    public void addressClick(View view) {
        CommUtil.hideKeyBoard(this);
        chooseAddressWheel.show(view);
    }

    /**
     * 选择地区滚轮事件改变
     * @param province
     * @param city
     * @param district
     */
    @Override
    public void onAddressChange(String province, String city, String district) {
        String provinceId = getAddressId(mAddressIdArray,province);
        String cityId     = getAddressId(mAddressIdArray,city);
        showAlert(provinceId+"---"+cityId,mContext);
        mIssueCity.setText(province + " " + city /*+ " " + district*/);
    }

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

    @OnClick(R.id.issue_detail_address)
    public void getLocationAddress(){
        //初始化
        LocationUtils.initLocation(mContext);
        //获取经纬度
        //Log.e("经度："+LocationUtils.longitude);
        //Log.e("纬度："+LocationUtils.latitude);
//        showAlert("location--->"+String.valueOf(LocationUtils.longitude+"---"+String.valueOf(LocationUtils.latitude)),mContext);
//        String loc = String.valueOf(LocationUtils.latitude)+","+String.valueOf(LocationUtils.longitude);
//        locToAddress(loc);
    }

    /**
     * 获取数据
     */
    private void initData(){
        String parentID= getIntent().getStringExtra("parentid");
        switch (parentID){
            case "80":
                mIssueType.setText("曝光");
                getIssueSecondList("80");
                break;
            case "81":
                mIssueType.setText("求助");
                getIssueSecondList("81");
                break;
            case "549":
                mIssueType.setText("圈子");
                getIssueSecondList("549");
                break;
            case "83":
                mIssueType.setText("寻人");
                getIssueSecondList("83");
                break;
            case "82":
                mIssueType.setText("寻物");
                getIssueSecondList("82");
                break;
            case "394":
                mIssueType.setText("招领");
                getIssueSecondList("394");
                break;
           default: break;
        }
    }

    /**
     * 获取发布类别列表（二级）
     */
    private void getIssueSecondList(String value){
        HashMap<String, String> params = new HashMap<>();
        params.put("parentid",value);
  ;
        HttpClient.get(Caller.ISSUE_TYPE_SECOND_LIST,params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String     result   =  response.getResult();
                String     message  =  response.getMessage();
                String     data     =  response.getData();

                if(!CommUtil.isNullOrBlank(result) && result.equals("true")){
                    List<SecondMenu> menu     =  JSON.parseArray(data, SecondMenu.class);
                    final    int     menuNum  =  menu.size();
                    List<SecondMenu>  menuList = new ArrayList<SecondMenu>();

                    for (int index = 0 ; index < menuNum ; index ++){
                        SecondMenu  menuMap  = new SecondMenu();
                        String     cateID    =  menu.get(index).getCategoryID();
                        String     cateTitle =  menu.get(index).getCategoryTitle();
                        menuMap.setCategoryID(cateID);
                        menuMap.setCategoryTitle(cateTitle);
                        menuList.add(menuMap);

                        mSpinnerAdapter = new SpinnerArrayAdapter(mContext, menuList);
                    }
                    mIssueSecondType.setAdapter(mSpinnerAdapter);

                }else{
                    CommUtil.showAlert(message,mContext);
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                CommUtil.showToast("二级菜单获取失败",mContext);
            }
        });
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


    /**
     * 选择走失目标图片
     */
    public void onCheckImg(View v){
        //打开手机相册
        final Intent intent = CommUtil.openCamera();

        switch (v.getId()){
            case R.id.issue_img_one:
                startActivityForResult(intent, Constant.ISSUE_RESULT_FIRST);
                break;
            case R.id.issue_img_two:
                startActivityForResult(intent, Constant.ISSUE_RESULT_SECOND);
                break;
            case R.id.issue_img_three:
                startActivityForResult(intent, Constant.ISSUE_RESULT_THIRD);
                break;
            case R.id.issue_img_four:
                startActivityForResult(intent, Constant.ISSUE_RESULT_FOURTH);
                break;
            case R.id.issue_img_five:
                startActivityForResult(intent, Constant.ISSUE_RESULT_FIVETH);
                break;
            case R.id.issue_img_six:
                startActivityForResult(intent, Constant.ISSUE_RESULT_SIXTH);
                break;
            case R.id.issue_img_seven:
                startActivityForResult(intent, Constant.ISSUE_RESULT_SEVENTH);
                break;
            case R.id.issue_img_eight:
                startActivityForResult(intent, Constant.ISSUE_RESULT_EIGHTH);
                break;
            default:break;
        }
    }


    /**
     *回调相册结果返回--走失目标图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode){

                //图片一
                case Constant.ISSUE_RESULT_FIRST:
                    Uri uri1 = data.getData();
                    mIssueImgOne.setImageURI(uri1);
                    mIssueImgTwo.setVisibility(View.VISIBLE);
                    break;
                //图片二
                case Constant.ISSUE_RESULT_SECOND:
                    Uri uri2 = data.getData();
                    mIssueImgTwo.setImageURI(uri2);
                    mIssueImgThree.setVisibility(View.VISIBLE);
                    break;
                //图片三
                case Constant.ISSUE_RESULT_THIRD:
                    Uri uri3 = data.getData();
                    mIssueImgThree.setImageURI(uri3);
                    mIssueImgFour.setVisibility(View.VISIBLE);
                    break;
                //图片四
                case Constant.ISSUE_RESULT_FOURTH:
                    Uri uri4 = data.getData();
                    mIssueImgFour.setImageURI(uri4);
                    mIssueImgFive.setVisibility(View.VISIBLE);
                    break;
                //图片五
                case Constant.ISSUE_RESULT_FIVETH:
                    Uri uri5 = data.getData();
                    mIssueImgFive.setImageURI(uri5);
                    mIssueImgSix.setVisibility(View.VISIBLE);
                    break;
                //图片六
                case Constant.ISSUE_RESULT_SIXTH:
                    Uri uri6 = data.getData();
                    mIssueImgSix.setImageURI(uri6);
                    mIssueImgSeven.setVisibility(View.VISIBLE);
                    break;
                //图片七
                case Constant.ISSUE_RESULT_SEVENTH:
                    Uri uri7 = data.getData();
                    mIssueImgSeven.setImageURI(uri7);
                    mIssueImgEight.setVisibility(View.VISIBLE);
                    break;
                //图片八
                case Constant.ISSUE_RESULT_EIGHTH:
                    Uri uri8 = data.getData();
                    mIssueImgEight.setImageURI(uri8);
                    break;
                default:break;
            }
        }
    }

    /**
     * 将经纬度转换为地址
     * location 拼接方式   ： 纬度，经度
     */
    private void locToAddress(String location){

        String params = "output=json &location ="+location;
        String resutl =  HttpRequestUtil.sendGet(BAIDU_MAP_LOCATION,params);

        showAlert("resutl--->"+resutl,mContext);

    }
}
