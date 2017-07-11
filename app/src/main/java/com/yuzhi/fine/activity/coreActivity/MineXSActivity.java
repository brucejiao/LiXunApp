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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.AddressDtailsEntity;
import com.yuzhi.fine.model.AddressModel;
import com.yuzhi.fine.model.UploadImg.Picturelist;
import com.yuzhi.fine.model.UploadImg.UploadImg;
import com.yuzhi.fine.ui.ChooseAddressWheel;
import com.yuzhi.fine.ui.wheelview.listener.OnAddressChangeListener;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.Constant;
import com.yuzhi.fine.utils.ImageUtils;
import com.yuzhi.fine.utils.JsonUtil;
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
import static com.yuzhi.fine.utils.CommUtil.generateFileName;
import static com.yuzhi.fine.utils.CommUtil.getAddressId;
import static com.yuzhi.fine.utils.CommUtil.readAssert;
import static com.yuzhi.fine.utils.CommUtil.showAlert;
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.fine.utils.Constant.SHARE_LOGIN_USERID;

/**
 * 我有线索
 */
public class MineXSActivity extends AppCompatActivity implements OnAddressChangeListener {

    @Bind(R.id.btnBack)
    LinearLayout mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题
    @Bind(R.id.xs_content)
    EditText mXSContent;//认领信息
    @Bind(R.id.xs_content_text_number)
    EditText mXSContentNumber;//认领信息字数
    @Bind(R.id.xs_find_city)
    TextView mXSFindCity;//发现城市
    @Bind(R.id.xs_detail_address)
    EditText mXSDetailAddress;//详细地址
    @Bind(R.id.xs_img_one)
    ImageView mXSImgOne;//认领信息图片
    @Bind(R.id.xs_img_two)
    ImageView mXSImgTwo;//认领信息图片
    @Bind(R.id.xs_img_three)
    ImageView mXSImgThree;//认领信息图片
    @Bind(R.id.xs_img_four)
    ImageView mXSImgFour;//认领信息图片
    @Bind(R.id.xs_now_submit)
    Button mSubBtn;//发布认领信息;

    private MineXSActivity mContext;
    private ProgressDialog progress;
    SharePreferenceUtil1 share ;
    private List<Uri> mImgList = new ArrayList<Uri>() ;
    private List<Picturelist> mPicturelists ;
    private final int mRLFlag = 1002;
    private ChooseAddressWheel chooseAddressWheel = null;
    private String[] mAddressIdArray;//地区id对照表
    private String provinceId;
    private String cityId;
    private String areaId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_xs);
        mContext = this;
        ButterKnife.bind(this);
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);
        mAddressIdArray = getResources().getStringArray(R.array.address_arrays);
        initUI();
        initData();
    }

    private void initUI(){
        //返回
        mBackBtn.setVisibility(View.VISIBLE);
        //标题
        mTextHeaderTitle.setText("我有线索");
//        mRlContentNumber.addTextChangedListener(new MaxLengthWatcher(11,mRlContentNumber));
        hideImgView();
        initWheel();
    }

    //返回
    @OnClick(R.id.btnBack)
    public void onBack(View view) {
        finish();
    }

    private void initData(){

    }

    /**
     * 隐藏图片选择器
     */
    private  void hideImgView(){
        mXSImgOne.setVisibility(View.VISIBLE);
        mXSImgTwo.setVisibility(View.GONE);
        mXSImgThree.setVisibility(View.GONE);
        mXSImgFour.setVisibility(View.GONE);
    }


    /**
     * 初始化地区选择滚轮
     */
    private void initWheel() {
        chooseAddressWheel = new ChooseAddressWheel(this,3);

        chooseAddressWheel.setOnAddressChangeListener(this);

        String address = readAssert(this, "address.txt");
        AddressModel model = JsonUtil.parseJson(address, AddressModel.class);
        if (model != null) {
            AddressDtailsEntity data = model.Result;
            if (data == null) return;
            mXSFindCity.setText(data.Province + " " + data.City + " " + data.Area);

            provinceId = getAddressId(mAddressIdArray,data.Province);
            cityId     = getAddressId(mAddressIdArray,data.City);
            areaId     = getAddressId(mAddressIdArray,data.Area);
//             showAlert(provinceId+"---"+cityId+"---"+areaId,mContext);

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
    @OnClick(R.id.xs_find_city)
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
        areaId     = getAddressId(mAddressIdArray,district);
//        showAlert(provinceId+"---"+cityId+"---"+areaId,mContext);
        mXSFindCity.setText(province + " " + city + " " + district);
    }

    /**
     * 选择走失目标图片
     */
    public void onCheckImg(View v){
        //打开手机相册
        final Intent intent = CommUtil.openCamera();
        switch (v.getId()){
            case R.id.xs_img_one:
                startActivityForResult(intent, Constant.XS_RESULT_FIRST);
                break;
            case R.id.xs_img_two:
                startActivityForResult(intent, Constant.XS_RESULT_SECOND);
                break;
            case R.id.xs_img_three:
                startActivityForResult(intent, Constant.XS_RESULT_THIRD);
                break;
            case R.id.xs_img_four:
                startActivityForResult(intent, Constant.XS_RESULT_FOURTH);
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
                case Constant.XS_RESULT_FIRST:
                    Uri uri1 = data.getData();
                    mXSImgOne.setImageURI(uri1);
                    mXSImgTwo.setVisibility(View.VISIBLE);
                    mImgList.add(uri1);
                    break;
                //图片二
                case Constant.XS_RESULT_SECOND:
                    Uri uri2 = data.getData();
                    mXSImgTwo.setImageURI(uri2);
                    mXSImgThree.setVisibility(View.VISIBLE);
                    mImgList.add(uri2);
                    break;
                //图片三
                case Constant.XS_RESULT_THIRD:
                    Uri uri3 = data.getData();
                    mXSImgThree.setImageURI(uri3);
                    mXSImgFour.setVisibility(View.VISIBLE);
                    mImgList.add(uri3);
                    break;
                //图片四
                case Constant.XS_RESULT_FOURTH:
                    Uri uri4 = data.getData();
                    mXSImgFour.setImageURI(uri4);
                    mImgList.add(uri4);
                    break;
                default:break;
            }
        }
    }

    /**
     * 上传图片
     */
    private  void uploadImage(){
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
                    .addFormDataPart("userid" , userID)
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
                            Message saveDraftMsg = new Message();
                            saveDraftMsg.what = mRLFlag;
                            saveDraftHandler.sendMessage(saveDraftMsg);
//                            showToast("图片上传成功",mContext);
                        }
                    }else{
                        showToast(message,mContext);
                        if (progress != null) { progress.dismiss();}

                    }
                }

                @Override
                public void onFailure(Request request, Exception e) {
                    showToast("图片上传失败",mContext);
                    if (progress != null) { progress.dismiss();}
                }
            });
        }



    }


    /**
     * 发表评论
     */
    private void addXSInfos() {
        String publistID = getIntent().getStringExtra("publistID");//发布ID
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userID);
        params.put("publishid", publistID);
        params.put("content", mXSContent.getText().toString().trim());
        //省份ID
        params.put("provinceid", provinceId);
        //城市ID
        params.put("cityid", cityId);
        //区县ID
        params.put("countryid",areaId);
        //详细地址
        params.put("address",mXSDetailAddress.getText().toString().trim());
        params.put("picturelist", toJSONString(mPicturelists));

        HttpClient.get(Caller.ADD_XS_INFOS, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
//                    showToast(message, mContext);
                    if (progress != null) { progress.dismiss();}
                    showAlert(message, mContext, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                } else {
                    showToast(message, mContext);
                    if (progress != null) {progress.dismiss();}
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                showToast("认领发布失败", mContext);
                if (progress != null) { progress.dismiss();}
            }
        });
    }


    Handler saveDraftHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == mRLFlag ){
                addXSInfos();
            }
        }
    };

    @OnClick(R.id.xs_now_submit)
    public void saveIssue(View view){
        //发布认领
        uploadImage();
    }

}
