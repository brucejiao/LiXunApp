package com.yuzhi.lixun110ccd.activity.coreActivity;

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

import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.http.Caller;
import com.yuzhi.lixun110ccd.http.HttpClient;
import com.yuzhi.lixun110ccd.http.HttpResponseHandler;
import com.yuzhi.lixun110ccd.http.RestApiResponse;
import com.yuzhi.lixun110ccd.model.UploadImg.Picturelist;
import com.yuzhi.lixun110ccd.model.UploadImg.UploadImg;
import com.yuzhi.lixun110ccd.utils.CommUtil;
import com.yuzhi.lixun110ccd.utils.Constant;
import com.yuzhi.lixun110ccd.utils.ImageUtils;
import com.yuzhi.lixun110ccd.utils.SharePreferenceUtil1;

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
import static com.yuzhi.lixun110ccd.R.id.btnBack;
import static com.yuzhi.lixun110ccd.utils.CommUtil.generateFileName;
import static com.yuzhi.lixun110ccd.utils.CommUtil.showToast;
import static com.yuzhi.lixun110ccd.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.lixun110ccd.utils.Constant.SHARE_LOGIN_USERID;

/**
 * 我要认领
 */
public class MineRLActivity extends AppCompatActivity {
    private MineRLActivity mContext;
    private ProgressDialog progress;
    SharePreferenceUtil1 share ;
    private List<Uri> mImgList = new ArrayList<Uri>() ;
    private List<Picturelist> mPicturelists ;
    private final int mRLFlag = 1001;
    @Bind(R.id.btnBack)
    LinearLayout mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题3
    @Bind(R.id.rl_content)
    EditText mRlContent;//认领信息
    @Bind(R.id.rl_content_text_number)
    EditText mRlContentNumber;//认领信息字数
    @Bind(R.id.rl_phone)
    EditText mRlPhone;//认领信息电话
    @Bind(R.id.rl_img_one)
    ImageView mRlImgOne;//认领信息图片
    @Bind(R.id.rl_img_two)
    ImageView mRlImgTwo;//认领信息图片
    @Bind(R.id.rl_img_three)
    ImageView mRlImgThree;//认领信息图片
    @Bind(R.id.rl_img_four)
    ImageView mRlImgFour;//认领信息图片
    @Bind(R.id.rl_now_submit)
    Button mSubBtn;//发布认领信息



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_rl);
        mContext = this;
        ButterKnife.bind(this);
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);
        initUI();
        initData();
    }

    private void initUI(){
        //返回
        mBackBtn.setVisibility(View.VISIBLE);
        //标题
        mTextHeaderTitle.setText("信息详情");
//        mRlContentNumber.addTextChangedListener(new MaxLengthWatcher(11,mRlContentNumber));
        hideImgView();
    }

    //返回
    @OnClick(btnBack)
    public void onBack(View view) {
        finish();
    }

    private void initData(){

    }

    /**
     * 隐藏图片选择器
     */
    private  void hideImgView(){
        mRlImgOne.setVisibility(View.VISIBLE);
        mRlImgTwo.setVisibility(View.GONE);
        mRlImgThree.setVisibility(View.GONE);
        mRlImgFour.setVisibility(View.GONE);
    }

    /**
     * 选择走失目标图片
     */
    public void onCheckImg(View v){
        //打开手机相册
        final Intent intent = CommUtil.openCamera();
        switch (v.getId()){
            case R.id.rl_img_one:
                startActivityForResult(intent, Constant.RL_RESULT_FIRST);
                break;
            case R.id.rl_img_two:
                startActivityForResult(intent, Constant.RL_RESULT_SECOND);
                break;
            case R.id.rl_img_three:
                startActivityForResult(intent, Constant.RL_RESULT_THIRD);
                break;
            case R.id.rl_img_four:
                startActivityForResult(intent, Constant.RL_RESULT_FOURTH);
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
                case Constant.RL_RESULT_FIRST:
                    Uri uri1 = data.getData();
                    mRlImgOne.setImageURI(uri1);
                    mRlImgTwo.setVisibility(View.VISIBLE);
                    mImgList.add(uri1);
                    break;
                //图片二
                case Constant.RL_RESULT_SECOND:
                    Uri uri2 = data.getData();
                    mRlImgTwo.setImageURI(uri2);
                    mRlImgThree.setVisibility(View.VISIBLE);
                    mImgList.add(uri2);
                    break;
                //图片三
                case Constant.RL_RESULT_THIRD:
                    Uri uri3 = data.getData();
                    mRlImgThree.setImageURI(uri3);
                    mRlImgFour.setVisibility(View.VISIBLE);
                    mImgList.add(uri3);
                    break;
                //图片四
                case Constant.RL_RESULT_FOURTH:
                    Uri uri4 = data.getData();
                    mRlImgFour.setImageURI(uri4);
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
    private void addRLInfos() {
        String publistID = getIntent().getStringExtra("publistID");//发布ID
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userID);
        params.put("publishid", publistID);
        params.put("content", mRlContent.getText().toString().trim());
        params.put("mobile", mRlPhone.getText().toString().trim());
        params.put("picturelist", toJSONString(mPicturelists));

        HttpClient.get(Caller.ADD_RL_INFOS, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
//                    showToast(message, mContext);
                    if (progress != null) { progress.dismiss();}
                    CommUtil.showAlert(message, mContext, new DialogInterface.OnClickListener() {
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
                addRLInfos();
            }
        }
    };

    @OnClick(R.id.rl_now_submit)
    public void saveIssue(View view){
        //发布认领
        uploadImage();
    }
}
