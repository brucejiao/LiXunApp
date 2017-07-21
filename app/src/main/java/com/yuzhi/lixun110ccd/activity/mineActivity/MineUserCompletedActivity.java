package com.yuzhi.lixun110ccd.activity.mineActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.fragment.mineFragment.MineFragment;
import com.yuzhi.lixun110ccd.http.Caller;
import com.yuzhi.lixun110ccd.http.HttpClient;
import com.yuzhi.lixun110ccd.http.HttpResponseHandler;
import com.yuzhi.lixun110ccd.http.RestApiResponse;
import com.yuzhi.lixun110ccd.model.AddressDtailsEntity;
import com.yuzhi.lixun110ccd.model.AddressModel;
import com.yuzhi.lixun110ccd.model.UploadImg.Picturelist;
import com.yuzhi.lixun110ccd.model.UploadImg.UploadImg;
import com.yuzhi.lixun110ccd.ui.ChooseAddressWheel;
import com.yuzhi.lixun110ccd.ui.wheelview.listener.OnAddressChangeListener;
import com.yuzhi.lixun110ccd.utils.CommUtil;
import com.yuzhi.lixun110ccd.utils.Constant;
import com.yuzhi.lixun110ccd.utils.DeviceUtil;
import com.yuzhi.lixun110ccd.utils.ImageUtils;
import com.yuzhi.lixun110ccd.utils.JsonUtil;
import com.yuzhi.lixun110ccd.utils.SharePreferenceUtil1;

import java.io.File;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.yuzhi.lixun110ccd.utils.CommUtil.generateFileName;
import static com.yuzhi.lixun110ccd.utils.CommUtil.readAssert;
import static com.yuzhi.lixun110ccd.utils.CommUtil.showAlert;
import static com.yuzhi.lixun110ccd.utils.CommUtil.showToast;
import static com.yuzhi.lixun110ccd.utils.Constant.MINE_RESULT_REFRESH;
import static com.yuzhi.lixun110ccd.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.lixun110ccd.utils.Constant.SHARE_LOGIN_USERID;

/**
 * 完善个人信息界面
 */
public class MineUserCompletedActivity extends AppCompatActivity implements OnAddressChangeListener {
    private MineUserCompletedActivity mContext;
    private ProgressDialog progress;
    private SharePreferenceUtil1 share ;
    private ChooseAddressWheel chooseAddressWheel = null;
    private String[] mAddressIdArray;//地区id对照表
    private String provinceId;
    private String cityId;
    private String areaId;

    @Bind(R.id.btnBack)
    LinearLayout mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题

    @Bind(R.id.mine_user_infos_header)
    RoundedImageView mMineUserInfosHeader;//头像

    @Bind(R.id.mine_xqah)
    EditText mMineXQAH;//兴趣爱好
    @Bind(R.id.mine_complete_city)
    TextView mCompleteCity;//所属区域
    @Bind(R.id.mine_xxdz)
    EditText mMineXXDZ;//详细地址

    @Bind(R.id.mine_sub_modify_userinfos)
    Button mMineSub;//完成

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_user_completed);
        mContext= this;
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

        String userHeader = getIntent().getStringExtra("userHeader");
        String mineMySummary = getIntent().getStringExtra("mySummary");//兴趣爱好

        String mineAddress = getIntent().getStringExtra("address");//详细地址

        Picasso.with(mContext).load(userHeader)
                .resize(DeviceUtil.dp2px(mContext,85), DeviceUtil.dp2px(mContext,85)).placeholder(R.drawable.default_image).into(mMineUserInfosHeader);

        mMineXQAH.setText(mineMySummary);

        mMineXXDZ.setText(mineAddress);

        initWheel();


    }

    private void initData(){

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

            String Province = getIntent().getStringExtra("province");//省
            String City = getIntent().getStringExtra("city");//市
            String Area = getIntent().getStringExtra("area");//区/县

            if(!CommUtil.isNullOrBlank(Province) && !CommUtil.isNullOrBlank(City) && !CommUtil.isNullOrBlank(Area)){

                mCompleteCity.setText(Province + " " + City + " " + Area);
                provinceId = getAddressId(mAddressIdArray,Province);
                cityId     = getAddressId(mAddressIdArray,City);
                areaId     = getAddressId(mAddressIdArray,Area);
//                showAlert(provinceId+"---"+cityId+"---"+areaId,mContext);

                if (data.ProvinceItems != null && data.ProvinceItems.Province != null) {

                    chooseAddressWheel.setProvince(data.ProvinceItems.Province);
                    chooseAddressWheel.defaultValue(Province, City, Area);
                }
            }else{
                mCompleteCity.setText(data.Province + " " + data.City + " " + data.Area);

                provinceId = getAddressId(mAddressIdArray,data.Province);
                cityId     = getAddressId(mAddressIdArray,data.City);
                areaId     = getAddressId(mAddressIdArray,data.Area);
//                showAlert(provinceId+"---"+cityId+"---"+areaId,mContext);

                if (data.ProvinceItems != null && data.ProvinceItems.Province != null) {

                    chooseAddressWheel.setProvince(data.ProvinceItems.Province);
                    chooseAddressWheel.defaultValue(data.Province, data.City, data.Area);
                }
            }


        }
    }


    /**
     * 选择地区事件
     * @param view
     */
    @OnClick(R.id.mine_complete_city)
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
        mCompleteCity.setText(province + " " + city + " " + district);
    }

    /**
     * 事件监听
     */
    private void onClickListener(){
        //返回
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MineFragment.class);
                setResult(MINE_RESULT_REFRESH,intent);
               finish();
            }
        });


    }


    /**
     * 修改用户头像
     * @param view
     */
    @OnClick(R.id.mine_user_infos_header)
    public void modifyUserHeader(View view){
        //打开手机相册
        final Intent intent = CommUtil.openCamera();
        startActivityForResult(intent, Constant.MINE_MODIFY_USER_HEADER);

    }

    /**
     * 提交修改用户信息
     * @param view
     */
    @OnClick(R.id.mine_sub_modify_userinfos)
    public void subModifyUserInfos(View view){
        modifyUserInfos();
    }


    /**
     *回调相册结果返回--走失目标图片
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if(requestCode == Constant.MINE_MODIFY_USER_HEADER){
                Uri uri = data.getData();
                mMineUserInfosHeader.setImageURI(uri);
                uploadImage(uri);

            }
        }
    }

    /**
     * 上传头像图片
     */
    private  void uploadImage(Uri uri){
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        //保存图片id集合
        String fileUrl =  ImageUtils.getImageAbsolutePath(mContext,uri);
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

        HttpClient.upload(Caller.MODIFY_USER_HEADER, mBody,new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String     result   =  response.getResult();
                String     message  =  response.getMessage();
                String     data     =  response.getData();

                UploadImg uploadImg = parseObject(data, UploadImg.class);

                if(!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)){
                    String id = uploadImg.getFileid();
                    String path =  uploadImg.getFilepath();
                    Picturelist picture = new Picturelist();
                    picture.setPictureID(id);
                    showToast(message,mContext);
                    Picasso.with(mContext).load(path)
                            .resize(DeviceUtil.dp2px(mContext,85), DeviceUtil.dp2px(mContext,85))
                            .placeholder(R.drawable.default_image).into(mMineUserInfosHeader);
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
                showToast("修改头像失败",mContext);
                if (progress != null)
                {
                    progress.dismiss();
                }
            }
        });

    }

    /**
     * 修改用户信息
     */
    private void modifyUserInfos(){
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userID);
        //兴趣爱好
        params.put("summary", mMineXQAH.getText().toString().trim());
        //省份ID
        params.put("provinceid", provinceId);
        //城市ID
        params.put("cityid", cityId);
        //区县ID
        params.put("countryid",areaId);
        //详细地址
        params.put("address",mMineXXDZ.getText().toString().trim());

        HttpClient.get(Caller.MODIFY_USER_INFO, params, new HttpResponseHandler() {
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
                            Intent intent = new Intent(mContext, MineFragment.class);
                            setResult(MINE_RESULT_REFRESH,intent);
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
                showToast("用户信息修改失败", mContext);
                if (progress != null) { progress.dismiss();}
            }
        });
    }

    /**
     * 获取地区编码id
     */
    public static String getAddressId(String[] arrays , String params){
        final  int arraysNum = arrays.length;
        for (int index = 0 ; index < arraysNum ; index ++)
        {
            //1|中国
            String id = arrays[index].substring(0, arrays[index].indexOf("|"));
            String name = arrays[index].substring(arrays[index].indexOf("|")+1, arrays[index].length());
            if (params.equals(name))
            {
                return id;
            }

        }
        return "";
    }


}
