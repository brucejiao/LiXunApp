package com.yuzhi.fine.activity.coreActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.yuzhi.fine.model.GoogleLoc2Add.GoogleAddressComponents;
import com.yuzhi.fine.model.GoogleLoc2Add.GoogleLoc;
import com.yuzhi.fine.model.GoogleLoc2Add.GoogleResults;
import com.yuzhi.fine.model.IssueModel.IssueContent;
import com.yuzhi.fine.model.IssueModel.SecondMenu;
import com.yuzhi.fine.model.UploadImg.Picturelist;
import com.yuzhi.fine.model.UploadImg.UploadImg;
import com.yuzhi.fine.ui.ChooseAddressWheel;
import com.yuzhi.fine.ui.SpinnerArrayAdapter;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.ui.wheelview.listener.OnAddressChangeListener;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.Constant;
import com.yuzhi.fine.utils.ImageUtils;
import com.yuzhi.fine.utils.JsonUtil;
import com.yuzhi.fine.utils.LocationUtils;
import com.yuzhi.fine.utils.LogUtil;
import com.yuzhi.fine.utils.SharePreferenceUtil1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.alibaba.fastjson.JSON.toJSONString;
import static com.yuzhi.fine.http.Caller.GOOGLE_MAP_LOCATION;
import static com.yuzhi.fine.utils.CommUtil.generateFileName;
import static com.yuzhi.fine.utils.CommUtil.getAddressId;
import static com.yuzhi.fine.utils.CommUtil.readAssert;
import static com.yuzhi.fine.utils.CommUtil.showEditString;
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.Constant.PARENTID_WLBG;
import static com.yuzhi.fine.utils.Constant.PARENTID_WLQZHU;
import static com.yuzhi.fine.utils.Constant.PARENTID_WLQZI;
import static com.yuzhi.fine.utils.Constant.PARENTID_WTXR;
import static com.yuzhi.fine.utils.Constant.PARENTID_WTXW;
import static com.yuzhi.fine.utils.Constant.PARENTID_ZLRL;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.fine.utils.Constant.SHARE_LOGIN_USERID;

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
    @Bind(R.id.issue_type_layout)
    LinearLayout mIssueTypeLayout;//发布类型布局
    @Bind(R.id.issue_second_layout)
    LinearLayout mIssueSecondLayout;//目标类型布局
    @Bind(R.id.issue_city_layout)
    LinearLayout mIssueCitydLayout;//目标城市布局
    @Bind(R.id.issue_server_layout)
    LinearLayout mIssueServerdLayout;//服务支持布局
    @Bind(R.id.issue_push_price_layout)
    LinearLayout mIssuePushPriceLayout;//推送价格布局
    @Bind(R.id.issue_push_layout)
    LinearLayout mIssuePushLayout;//推送布局
    @Bind(R.id.issue_area_top_layout)
    LinearLayout mIssueAreaTopLayout;//地区置顶布局
    @Bind(R.id.issue_area_top_price_layout)
    LinearLayout mIssueAreaTopPriceLayout;//地区置顶价格布局
    @Bind(R.id.issue_remark_layout)
    LinearLayout mIssueRemarkLayout;//说明文字布局
    @Bind(R.id.issue_type)
    TextView mIssueType;//发布类型
    @Bind(R.id.issue_content)
    EditText mIssueContent;//发布内容
    @Bind(R.id.issue_content_text_number)
    TextView mIssueContentTextNum;//发布内容字数
    @Bind(R.id.issue_type_text)
    TextView mIssueTypeText;//目标类型标题
    @Bind(R.id.issue_second_type_spiner)
    Spinner mIssueSecondType;//二级菜单（类型）
    @Bind(R.id.issue_price_layout)
    LinearLayout mIssuePriceLayout;//悬赏金额
    @Bind(R.id.issue_price)
    EditText mIssuePrice;//悬赏金额
    @Bind(R.id.issue_price_text)
    TextView mIssuePriceText;//悬赏金额标题
    @Bind(R.id.issue_price_after)
    TextView mIssuePriceAfter;//悬赏金额后的说明文字
    @Bind(R.id.issue_text_city)
    TextView mIssueTextCity;//走失城市标题
    @Bind(R.id.issue_city)
    TextView mIssueCity;//走失城市
    @Bind(R.id.issue_detail_address_layout)
    LinearLayout mIssueDetailAddressLayout;//详细地址
    @Bind(R.id.issue_detail_address)
    EditText mIssueDetailAddress;//详细地址
    @Bind(R.id.issue_imgview_title)
    TextView mIssueImgTitle;//图片标题
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
    private List<Uri>  mImgList = new ArrayList<Uri>() ;
    private List<Picturelist> mPicturelists ;
    private IssueContent issueContent = new IssueContent();
    private String provinceId;
    private String cityId;
    //这个个类型默认状态和界面默认状态需保持一致
    private String mPushType = "1";
    private String mTopType ="1";
    //目标类型ID
    private String mCategoryID ;
    private ProgressDialog progress;
    SharePreferenceUtil1 share ;

    private final int mDraftFlag = 101;
    private final int mIssueFlag = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_issue);
        ButterKnife.bind(this);
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);
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

        //初始化定位
        new Thread(networkTask).start();



    }

    /**
     * 初始化地区选择滚轮
     */
    private void initWheel() {
        chooseAddressWheel = new ChooseAddressWheel(this,2);
        chooseAddressWheel.setOnAddressChangeListener(this);

        String address = readAssert(this, "address.txt");
        AddressModel model = JsonUtil.parseJson(address, AddressModel.class);
        if (model != null) {
            AddressDtailsEntity data = model.Result;
            if (data == null) return;
            mIssueCity.setText(data.Province + " " + data.City /*+ " " + data.Area*/);

            provinceId = getAddressId(mAddressIdArray,data.Province);
            cityId     = getAddressId(mAddressIdArray,data.City);
//             showAlert(provinceId+"---"+cityId,mContext);

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
         provinceId = getAddressId(mAddressIdArray,province);
         cityId     = getAddressId(mAddressIdArray,city);
//        showAlert(provinceId+"---"+cityId,mContext);
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
        //对目标类型spinner监听
        mIssueSecondType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SecondMenu secondMenu = (SecondMenu)mIssueSecondType.getSelectedItem();
//                String title = secondMenu.getCategoryTitle();
//                showToast(title,mContext);
                mCategoryID = secondMenu.getCategoryID();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }



    /**
     * 获取数据
     */
    private void initData(){
        String parentID= getIntent().getStringExtra("parentid");
        switch (parentID){
            case PARENTID_WLBG:
                mIssueType.setText("曝光");
                //initUI
                mIssueTextCity.setText("所在城市");
                mIssuePriceLayout.setVisibility(View.GONE);
                mIssueDetailAddressLayout.setVisibility(View.GONE);
                //获取目标类型
                getIssueSecondList(PARENTID_WLBG);
                break;
            case PARENTID_WLQZHU:
                mIssueType.setText("求助");
                //initUI
                mIssueTextCity.setText("所在城市");
                mIssuePriceLayout.setVisibility(View.GONE);
                mIssueDetailAddressLayout.setVisibility(View.GONE);
                //获取目标类型
                getIssueSecondList(PARENTID_WLQZHU);
                break;
            case PARENTID_WLQZI:
                mIssueType.setText("圈子");
                //initUI
                mIssueContent.setHint("有啥好玩的，好笑的，好吃的，高营养鸡汤...给大家分享下吧");
                mIssueTypeLayout.setVisibility(View.GONE);
                mIssueSecondLayout.setVisibility(View.GONE);
                mIssuePriceLayout.setVisibility(View.GONE);
                mIssueCitydLayout.setVisibility(View.GONE);
                mIssueDetailAddressLayout.setVisibility(View.GONE);
                mIssueServerdLayout.setVisibility(View.GONE);
                mIssueSaveDraft.setVisibility(View.GONE);
                mIssuePushPriceLayout.setVisibility(View.GONE);
                mIssuePushLayout.setVisibility(View.GONE);
                mIssueAreaTopLayout.setVisibility(View.GONE);
                mIssueAreaTopPriceLayout.setVisibility(View.GONE);
                mIssueRemarkLayout.setVisibility(View.GONE);
                //获取目标类型
                getIssueSecondList(PARENTID_WLQZI);
                break;
            case PARENTID_WTXR:
                mIssueType.setText("寻人");
                //initUI
                mIssueTextCity.setText("丢失城市");
                //获取目标类型
                getIssueSecondList(PARENTID_WTXR);
                break;
            case PARENTID_WTXW:
                mIssueType.setText("寻物");
                //initUI
                mIssueTextCity.setText("丢失城市");
                //获取目标类型
                getIssueSecondList(PARENTID_WTXW);
                break;
            case PARENTID_ZLRL:
                mIssueType.setText("招领");
                //initUI
                mIssueTypeText.setText("招领类型");
                mIssuePriceText.setText("认领金额");
                mIssueTextCity.setText("拾遗城市");
                mIssueImgTitle.setText("认领物品图片");
                mIssuePriceAfter.setText("0元即表示无，由失主线下支付");
                //获取目标类型
                getIssueSecondList(PARENTID_ZLRL);
                break;
           default: break;
        }
    }

    /**
     * 目标类型--获取发布类别列表（二级）
     */
    private void getIssueSecondList(String value){
        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("parentid",value);

        HttpClient.get(Caller.ISSUE_TYPE_SECOND_LIST,params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String     result   =  response.getResult();
                String     message  =  response.getMessage();
                String     data     =  response.getData();

                if(!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)){
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
                    if (progress != null)
                    {
                        progress.dismiss();
                    }

                }else{
                    showToast(message,mContext);
                    if (progress != null)
                    {
                        progress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                if (progress != null)
                {
                    progress.dismiss();
                }
                showToast("二级菜单获取失败",mContext);
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
                    mImgList.add(uri1);
                    break;
                //图片二
                case Constant.ISSUE_RESULT_SECOND:
                    Uri uri2 = data.getData();
                    mIssueImgTwo.setImageURI(uri2);
                    mIssueImgThree.setVisibility(View.VISIBLE);
                    mImgList.add(uri2);
                    break;
                //图片三
                case Constant.ISSUE_RESULT_THIRD:
                    Uri uri3 = data.getData();
                    mIssueImgThree.setImageURI(uri3);
                    mIssueImgFour.setVisibility(View.VISIBLE);
                    mImgList.add(uri3);
                    break;
                //图片四
                case Constant.ISSUE_RESULT_FOURTH:
                    Uri uri4 = data.getData();
                    mIssueImgFour.setImageURI(uri4);
                    mIssueImgFive.setVisibility(View.VISIBLE);
                    mImgList.add(uri4);
                    break;
                //图片五
                case Constant.ISSUE_RESULT_FIVETH:
                    Uri uri5 = data.getData();
                    mIssueImgFive.setImageURI(uri5);
                    mIssueImgSix.setVisibility(View.VISIBLE);
                    mImgList.add(uri5);
                    break;
                //图片六
                case Constant.ISSUE_RESULT_SIXTH:
                    Uri uri6 = data.getData();
                    mIssueImgSix.setImageURI(uri6);
                    mIssueImgSeven.setVisibility(View.VISIBLE);
                    mImgList.add(uri6);
                    break;
                //图片七
                case Constant.ISSUE_RESULT_SEVENTH:
                    Uri uri7 = data.getData();
                    mIssueImgSeven.setImageURI(uri7);
                    mIssueImgEight.setVisibility(View.VISIBLE);
                    mImgList.add(uri7);
                    break;
                //图片八
                case Constant.ISSUE_RESULT_EIGHTH:
                    Uri uri8 = data.getData();
                    mIssueImgEight.setImageURI(uri8);
                    mImgList.add(uri8);
                    break;
                default:break;
            }
        }
    }

    /**
     * 将经纬度转换为地址
     * location 拼接方式   ： 纬度，经度
     */
    private String locToAddress(String location){
        //baidu
//        String params = "output=json&location="+location;
//        String resutl =  HttpRequestUtil.sendGet(BAIDU_MAP_LOCATION,params.trim());
        //google
        String params = "latlng="+location+"&sensor=true&language=zh-CN";
        String resutl =  HttpRequestUtil.sendGet(GOOGLE_MAP_LOCATION,params);

        return resutl;
    }

    /**
     * 获取请求地址结果并更新到UI
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
                // UI界面的更新等相关操作
//            Location2Address location2Address = parseObject(val,Location2Address.class);
//            LocResult locResult = location2Address.getResult();
//            String address = locResult.getFormatted_address();
//            mIssueDetailAddress.setText(address);
            GoogleLoc googleLoc = parseObject(val,GoogleLoc.class);
            List<GoogleResults> googleResults = JSON.parseArray(googleLoc.getResults(),GoogleResults.class);
            String address_components =  googleResults.get(0).getAddress_components();
            List<GoogleAddressComponents> googleAddressComponents = JSON.parseArray(address_components, GoogleAddressComponents.class);
            String road = googleAddressComponents.get(0).getLong_name();//道路
            String county = googleAddressComponents.get(1).getLong_name();//县级市或者区
            String city = googleAddressComponents.get(2).getLong_name();//地级市
            String province =  googleAddressComponents.get(3).getLong_name();//省
            String national =  googleAddressComponents.get(4).getLong_name();//国家
            //详细地址
            String address =  googleResults.get(0).getFormatted_address();
            String addressStr = address.substring(2,address.length());//截去‘中国’
            if (addressStr.contains(" ")){
                String[] addressStrArray =  addressStr.split(" ");
                mIssueDetailAddress.setText(addressStrArray[0]);
            }else{
                mIssueDetailAddress.setText(addressStr);
            }

        }
    };


    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // 在这里进行 http request.网络请求相关操作
            String loc = String.valueOf(LocationUtils.latitude)+","+String.valueOf(LocationUtils.longitude);
            String address = locToAddress(loc);
            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", address);
            msg.setData(data);
            handler.sendMessage(msg);
        }
    };

    /**
     * 选择推送地区 0
     */
    @OnClick(R.id.issue_choose_city)
    public void chooseCity(View view){
        mIssueChooseCity.setBackgroundResource(R.drawable.btn_selected);
        mIssueChooseNational.setBackgroundResource(R.drawable.btn_select);
        mPushType = "0";

    }

    /**
     * 选择推送全国 1
     */
    @OnClick(R.id.issue_choose_national)
    public void chooseNational(View view){
        mIssueChooseCity.setBackgroundResource(R.drawable.btn_select);
        mIssueChooseNational.setBackgroundResource(R.drawable.btn_selected);
        mPushType = "1";
    }

    /**
     * 地区置顶---不需要 0
     */
    @OnClick(R.id.issue_area_top_no)
    public void areaTopNo(View view){
        mIssueAreaTopNo.setBackgroundResource(R.drawable.btn_selected);
        mIssueAreaTopYes.setBackgroundResource(R.drawable.btn_select);
        mTopType = "0";
    }

    /**
     * 地区置顶---需要 1
     */
    @OnClick(R.id.issue_area_top_yes)
    public void areaTopYes(View view){
        mIssueAreaTopNo.setBackgroundResource(R.drawable.btn_select);
        mIssueAreaTopYes.setBackgroundResource(R.drawable.btn_selected);
        mTopType = "1";
    }

    /**
     * 上传图片
     */
    private  void uploadImage(final int Flag){
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        mPicturelists = new ArrayList<Picturelist>();
        //保存图片id集合
        for ( int i =0 ; i< mImgList.size(); i++){
            String fileUrl =  ImageUtils.getImageAbsolutePath(mContext,mImgList.get(i));
            File file1 = new File(fileUrl);
            RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream") , file1);//multipart/form-data

            //文件类型
//        String fileType = fileUrl.substring(fileUrl.indexOf("."),fileUrl.length());
            //生成文件名
            String file1Name =generateFileName(".jpg");
            String boundary = "---androidxiaowangzi----";
            MultipartBody mBody = new MultipartBody.Builder(boundary).setType(MultipartBody.FORM)
                    .addFormDataPart("userid" ,userID)
                    .addFormDataPart("filedata" , file1Name , fileBody)
                    .build();

            HttpClient.upload(Caller.UPLOAD_IMAGE, mBody,new HttpResponseHandler() {
                @Override
                public void onSuccess(RestApiResponse response) {
                    String     result   =  response.getResult();
                    String     message  =  response.getMessage();
                    String     data     =  response.getData();

                    UploadImg uploadImg = parseObject(data, UploadImg.class);

                    if(!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)){
                        String id = uploadImg.getFileid();
                        String path =  uploadImg.getFilepath();
                        Picturelist  picture = new Picturelist();
                        picture.setPictureID(id);
                        mPicturelists.add(picture);

                        //图片id -List长度 等于 选择图片的List长度  才可以发消息进行保存操作
                        if(mPicturelists.size() == mImgList.size())
                        {
                            switch (Flag){
                                case mDraftFlag:
                                    Message saveDraftMsg = new Message();
                                    saveDraftMsg.what = mDraftFlag;
                                    saveDraftHandler.sendMessage(saveDraftMsg);
                                    showToast("图片上传成功",mContext);
                                    break;
                                case mIssueFlag:
                                    Message saveIssueMsg = new Message();
                                    saveIssueMsg.what = mIssueFlag;
                                    saveDraftHandler.sendMessage(saveIssueMsg);
                                    showToast("图片上传成功",mContext);
                                    break;
                                default:break;
                            }
                        }
                        if (progress != null)
                        {
                            progress.dismiss();
                        }
                    }else{
                        showToast(message,mContext);
                        if (progress != null)
                        {
                            progress.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Request request, Exception e) {
                    showToast("图片上传失败",mContext);
                    if (progress != null)
                    {
                        progress.dismiss();
                    }
                }
            });
        }
    }

    /**
     * 发布消息内容
     */
    public String issueContent(){

        IssueContent issueContent = new IssueContent();
        issueContent.setTitle(showEditString(mIssueTitle));//标题
        issueContent.setContent(showEditString(mIssueContent));//发布内容
        issueContent.setCategoryID(mCategoryID);//目标类型
        issueContent.setMoney(CommUtil.isNullOrBlank(showEditString(mIssuePrice))?"0":showEditString(mIssuePrice));//悬赏金额
        issueContent.setProvince(provinceId);//省ID
        issueContent.setCity(cityId);//市ID
        issueContent.setAddress(showEditString(mIssueDetailAddress));//详细地址
//        issueContent.setPushType(mPushType);//推送地区 0 选择地区 1 全国
//        issueContent.setPushMoney(CommUtil.isNullOrBlank(showEditString(mIssuePushPrice))?"0":showEditString(mIssuePushPrice));//推送价格
//        issueContent.setTopType(mTopType);//地区置顶 0不需要 1 需要
//        issueContent.setTopMoney(CommUtil.isNullOrBlank(showEditString(mIssueAreaTopPrice))?"0":showEditString(mIssueAreaTopPrice));//置顶价格

        String publishinfo = JSON.toJSONString(issueContent);

//        showAlert(publishinfo,mContext);

        return publishinfo;
    }

    /**
     * 保存草稿箱
     *点击 【保存草稿箱】-上传所有图片-图片上传完毕-开始调保存草稿箱接口
     */
    @OnClick(R.id.issue_save_draft)
    public void uploadImg(View view){
        //保存草稿箱
        uploadImage(mDraftFlag);
    }
    /**
     * 保存至草稿箱
     */
    private void saveDraft(){
        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
//             showToast("imgLIST===>"+ toJSONString(mPicturelists),mContext);
         String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
            //发布信息实体
               String publishinfo =  issueContent();
               HashMap<String, String> params = new HashMap<>();
                params.put("userid",userID);
                params.put("publishinfo",publishinfo);//发布信息实体
                params.put("picturelist", toJSONString(mPicturelists));//发布图片信息实体 [{PictureID:""},{},{}]
                //        params.put("provepicturelist","=====");//发布证明图片信息实体
               LogUtil.d("=============params=============",publishinfo+"----\n----"+toJSONString(mPicturelists));
                HttpClient.get(Caller.ADD_ISSUE_DRAFT_INFO,params, new HttpResponseHandler() {
                    @Override
                    public void onSuccess(RestApiResponse response) {
                        String     result   =  response.getResult();
                        String     message  =  response.getMessage();

                        if(!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)){
                            if (progress != null)
                            {
                                progress.dismiss();
                            }
                            CommUtil.showAlert(message, mContext, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    UIHelper.showHome(mContext);
                                    finish();
                                }
                            });


                        }else{
                            if (progress != null)
                            {
                                progress.dismiss();
                            }
                            showToast(message,mContext);
                        }
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {
                        e.printStackTrace();
                        if (progress != null)
                        {
                            progress.dismiss();
                        }
                        showToast("发布信息失败",mContext);
                    }
                });
    }


    /**
     * 现在发布
     *点击 【现在发布】-上传所有图片-图片上传完毕-开始调现在发布接口
     */
    @OnClick(R.id.issue_now_submit)
    public void saveIssue(View view){
        //保存草稿箱
        uploadImage(mIssueFlag);
    }


    /**
     * 发布信息
     */
    private void saveIssue(){
        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
//             showToast("imgLIST===>"+ toJSONString(mPicturelists),mContext);
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        //发布信息实体
        String publishinfo =  issueContent();
        HashMap<String, String> params = new HashMap<>();
        params.put("userid",userID);
        params.put("publishinfo",publishinfo);//发布信息实体
        params.put("picturelist", toJSONString(mPicturelists));//发布图片信息实体 [{PictureID:""},{},{}]
        //        params.put("provepicturelist","=====");//发布证明图片信息实体
        LogUtil.d("=============params=============",publishinfo+"----\n----"+toJSONString(mPicturelists));
        HttpClient.get(Caller.ADD_ISSUE_INFO,params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String     result   =  response.getResult();
                String     message  =  response.getMessage();
                String     data  =  response.getData();

                if(!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)){
                    if (progress != null)
                    {
                        progress.dismiss();
                    }
                    CommUtil.showAlert(message, mContext, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            UIHelper.showHome(mContext);
                            finish();
                        }
                    });


                }else{
                    if (progress != null)
                    {
                        progress.dismiss();
                    }
                    showToast(message,mContext);
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                e.printStackTrace();
                if (progress != null)
                {
                    progress.dismiss();
                }
                showToast("发布信息失败",mContext);
            }
        });
    }


    Handler saveDraftHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                //保存到草稿箱
                case mDraftFlag:
                    saveDraft();
                    break;
                //发布信息
                case mIssueFlag:
                    saveIssue();
                    break;
                default:break;
            }
        }
    };
}
