package com.yuzhi.lixun110ccd.activity.mineActivity.MineComplainActivity;

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
import com.yuzhi.lixun110ccd.ui.UIHelper;
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
import static com.yuzhi.lixun110ccd.utils.CommUtil.generateFileName;
import static com.yuzhi.lixun110ccd.utils.CommUtil.showToast;
import static com.yuzhi.lixun110ccd.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.lixun110ccd.utils.Constant.SHARE_LOGIN_USERID;

/**
 * 投诉建议
 */
public class MainComplainsActivity extends AppCompatActivity {
    private MainComplainsActivity mContext;
    private ProgressDialog progress;
    private SharePreferenceUtil1 share ;
    @Bind(R.id.btnBack)
    LinearLayout mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题

    @Bind(R.id.id1)
    TextView id1;
    @Bind(R.id.id2)
    TextView id2;
    @Bind(R.id.id3)
    LinearLayout id3;
    @Bind(R.id.id4)
    EditText id4;//被举报人昵称
    @Bind(R.id.id5)
    TextView id5;
    @Bind(R.id.id6)
    EditText id6;
    @Bind(R.id.id7)
    TextView id7;
    @Bind(R.id.id8)
    Button id8;
    @Bind(R.id.img1)
    ImageView img1;
    @Bind(R.id.img2)
    ImageView img2;
    @Bind(R.id.img3)
    ImageView img3;
    @Bind(R.id.img4)
    ImageView img4;
    @Bind(R.id.img5)
    ImageView img5;
    @Bind(R.id.img6)
    ImageView img6;
    @Bind(R.id.img7)
    ImageView img7;
    @Bind(R.id.img8)
    ImageView img8;
    private List<Uri> mImgList = new ArrayList<Uri>() ;
    private List<Picturelist> mPicturelists ;
    private  String mType = "";//投诉类型（1、投诉举报；2、APP吐槽）


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_complains);

        mContext= this;
        ButterKnife.bind(this);
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);

        initUI();
        initData();
    }

    /**
     * 初始化界面
     */
    private void initUI(){
        //返回
        mBackBtn.setVisibility(View.VISIBLE);
        //标题
        mTextHeaderTitle.setText("投诉建议");
        hideImgView();
        checkOnClick();
        jubao_UI();

    }

    private void initData(){
    }

    /**
     * 选择走失目标图片
     */
    public void onCheckImg(View v){
        //打开手机相册
        final Intent intent = CommUtil.openCamera();

        switch (v.getId()){
            case R.id.img1:
                startActivityForResult(intent, Constant.ISSUE_RESULT_FIRST);
                break;
            case R.id.img2:
                startActivityForResult(intent, Constant.ISSUE_RESULT_SECOND);
                break;
            case R.id.img3:
                startActivityForResult(intent, Constant.ISSUE_RESULT_THIRD);
                break;
            case R.id.img4:
                startActivityForResult(intent, Constant.ISSUE_RESULT_FOURTH);
                break;
            case R.id.img5:
                startActivityForResult(intent, Constant.ISSUE_RESULT_FIVETH);
                break;
            case R.id.img6:
                startActivityForResult(intent, Constant.ISSUE_RESULT_SIXTH);
                break;
            case R.id.img7:
                startActivityForResult(intent, Constant.ISSUE_RESULT_SEVENTH);
                break;
            case R.id.img8:
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
                    img1.setImageURI(uri1);
                    img2.setVisibility(View.VISIBLE);
                    mImgList.add(uri1);
                    break;
                //图片二
                case Constant.ISSUE_RESULT_SECOND:
                    Uri uri2 = data.getData();
                    img2.setImageURI(uri2);
                    img3.setVisibility(View.VISIBLE);
                    mImgList.add(uri2);
                    break;
                //图片三
                case Constant.ISSUE_RESULT_THIRD:
                    Uri uri3 = data.getData();
                    img3.setImageURI(uri3);
                    img4.setVisibility(View.VISIBLE);
                    mImgList.add(uri3);
                    break;
                //图片四
                case Constant.ISSUE_RESULT_FOURTH:
                    Uri uri4 = data.getData();
                    img4.setImageURI(uri4);
                    img5.setVisibility(View.VISIBLE);
                    mImgList.add(uri4);
                    break;
                //图片五
                case Constant.ISSUE_RESULT_FIVETH:
                    Uri uri5 = data.getData();
                    img5.setImageURI(uri5);
                    img6.setVisibility(View.VISIBLE);
                    mImgList.add(uri5);
                    break;
                //图片六
                case Constant.ISSUE_RESULT_SIXTH:
                    Uri uri6 = data.getData();
                    img6.setImageURI(uri6);
                    img7.setVisibility(View.VISIBLE);
                    mImgList.add(uri6);
                    break;
                //图片七
                case Constant.ISSUE_RESULT_SEVENTH:
                    Uri uri7 = data.getData();
                    img7.setImageURI(uri7);
                    img8.setVisibility(View.VISIBLE);
                    mImgList.add(uri7);
                    break;
                //图片八
                case Constant.ISSUE_RESULT_EIGHTH:
                    Uri uri8 = data.getData();
                    img8.setImageURI(uri8);
                    mImgList.add(uri8);
                    break;
                default:break;
            }
        }
    }


    /**
     * 按钮背景切换
     */
    public void checkOnClick(){
        id1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jubao_UI();

            }
        });
        id2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tucao_UI();
            }
        });
    }

    /**
     * 举报ui
     */
    private void jubao_UI(){
        id1.setTextColor(getResources().getColor(R.color.green));
        id2.setTextColor(getResources().getColor(R.color.black));
        id1.setBackgroundResource(R.drawable.btn_selected);
        id2.setBackgroundResource(R.drawable.btn_select);
        id3.setVisibility(View.VISIBLE);
        id5.setText("举报原因");
        mType = "1";
    }

    /**
     * 吐槽ui
     */
    private void tucao_UI(){
        id1.setTextColor(getResources().getColor(R.color.black));
        id2.setTextColor(getResources().getColor(R.color.green));
        id1.setBackgroundResource(R.drawable.btn_select);
        id2.setBackgroundResource(R.drawable.btn_selected);
        id3.setVisibility(View.GONE);
        id5.setText("内容");
        mType = "2";
    }

    /**
     * 隐藏图片选择器
     */
    private  void hideImgView(){
        img1.setVisibility(View.VISIBLE);
        img2.setVisibility(View.GONE);
        img3.setVisibility(View.GONE);
        img4.setVisibility(View.GONE);
        img5.setVisibility(View.GONE);
        img6.setVisibility(View.GONE);
        img7.setVisibility(View.GONE);
        img8.setVisibility(View.GONE);

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
                            Message saveDraftMsg = new Message();
                            saveDraftMsg.what = 100101;
                            saveDraftHandler.sendMessage(saveDraftMsg);
//                            showToast("图片上传成功",mContext);
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
     * 投诉建议/吐槽
     */
    @OnClick(R.id.id8)
    public void tushuOnclick(View view){
        //保存草稿箱
        uploadImage();


    }
    /**
     * 投诉建议
     */
    private void toushu(){
        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        HashMap<String, String> params = new HashMap<>();
        params.put("userid",userID);
        params.put("type",mType);//投诉类型（1、投诉举报；2、APP吐槽）
        params.put("content",id6.getText().toString().trim());//内容
        if(CommUtil.isNullOrBlank(mType) && mType.equals("1")){
            params.put("username",id4.getText().toString().trim());//被举报用户名
        }
        params.put("picturelist", toJSONString(mPicturelists));//发布图片信息实体 [{PictureID:""},{},{}]
        //        params.put("provepicturelist","=====");//发布证明图片信息实体
        HttpClient.get(Caller.SUBMIT_ADD_ADVICE,params, new HttpResponseHandler() {
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

    Handler saveDraftHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 100101){
                toushu();// 投诉建议/吐槽
            }
        }
    };

}
