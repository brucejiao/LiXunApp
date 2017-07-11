package com.yuzhi.fine.activity.mineActivity.MinePeopleVerfiActivity;

import android.app.ProgressDialog;
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
import com.yuzhi.fine.model.UploadImg.UploadImg;
import com.yuzhi.fine.model.UserInfos.UserInfo;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.Constant;
import com.yuzhi.fine.utils.ImageUtils;
import com.yuzhi.fine.utils.SharePreferenceUtil1;

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
import static com.yuzhi.fine.utils.CommUtil.generateFileName;
import static com.yuzhi.fine.utils.CommUtil.showAlert;
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.fine.utils.Constant.SHARE_LOGIN_USERID;

/**
 * 个人认证--二级界面
 */
public class MinePeoVrifiPage2Activity extends AppCompatActivity {

    private MinePeoVrifiPage2Activity mContext;
    private ProgressDialog progress;
    private SharePreferenceUtil1 share ;
    @Bind(R.id.btnBack)
    LinearLayout mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题
    @Bind(R.id.verifi_username)
    EditText mVerifiUserName;//用户名
    @Bind(R.id.verifi_idcard)
    EditText mVerifiIdcard;//身份证号码
    @Bind(R.id.verifi_idcard_a)
    ImageView mVerifiIdcardA;//身份证正面
    @Bind(R.id.verifi_idcard_b)
    ImageView mVerifiIdcardB;//身份证反面
    @Bind(R.id.verifi_next)
    Button mVerifiNext;//下一步

    private String mIdCardAid;
    private String mIdCardBid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_peo_vrifi_page2);
        mContext= this;
        ButterKnife.bind(this);
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);

        initUI();
        initData();

    }

    private void initUI(){
        //返回
        mBackBtn.setVisibility(View.VISIBLE);
        //标题
        mTextHeaderTitle.setText("个人认证");
    }

    private void initData(){
    }

    /**
     * 上传身份证照片
     * @param view
     */
    @OnClick({R.id.verifi_idcard_a,R.id.verifi_idcard_b})
    public void modifyUserHeader(View view){
        //打开手机相册
        final Intent intent = CommUtil.openCamera();
        switch (view.getId())
        {
            case R.id.verifi_idcard_a:
                startActivityForResult(intent, Constant.UPLOAD_IDCARD_A);
                break;
            case R.id.verifi_idcard_b:
                startActivityForResult(intent, Constant.UPLOAD_IDCARD_B);
                break;
            default:break;

        }


    }

    /**
     *回调相册结果返回--走失目标图片
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if(requestCode == Constant.UPLOAD_IDCARD_A){
                Uri uri = data.getData();
                mVerifiIdcardA.setImageURI(uri);
                uploadImageA(uri);

            }
            if(requestCode == Constant.UPLOAD_IDCARD_B){
                Uri uri = data.getData();
                mVerifiIdcardB.setImageURI(uri);
                uploadImageB(uri);

            }
        }
    }

    /**
     * 上传身份证照片
     */
    private  void uploadImageA(Uri uri){
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
                    mIdCardAid = id ;
                    showToast(message,mContext);
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

    private  void uploadImageB(Uri uri){
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
                    mIdCardBid = id ;
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
     * 认证户信息
     */
    private void verifiUser() {
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userID);//
        if(!CommUtil.isNullOrBlank(mVerifiUserName.getText().toString().trim())){
            params.put("realname", mVerifiUserName.getText().toString().trim());//真实姓名（身份证上）
        }else {
            CommUtil.showAlert("请输入您的姓名",mContext);
            if (progress != null) {
                progress.dismiss();
            }
            return;
        }
        params.put("cardid", mIdCardAid);//身份证正面图片ID
        params.put("cardbackid", mIdCardBid);//身份证反面图片ID
        CommUtil.showToast("mIdCardAid-->"+mIdCardAid+"《---》mIdCardBid"+mIdCardBid,mContext);
        HttpClient.get(Caller.EDIT_USER_CERTIFICATE, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();
                UserInfo userInfo = parseObject(data, UserInfo.class);

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                    Message msg = new Message();
                    msg.what = 1001;
                    verifiHandler.sendMessage(msg);

                } else {
                    showAlert(message, mContext);
                    Message msg = new Message();
                    msg.what = 1001;
                    verifiHandler.sendMessage(msg);
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
            }
        });
    }

    /**
     * 下一步
     * @param view
     */
    @OnClick(R.id.verifi_next)
    public void verifiNext(View view)
    {
        verifiUser();
    }

    Handler  verifiHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1001){
                UIHelper.showCompleteUserInfos(mContext);
            }
        }
    };
}
