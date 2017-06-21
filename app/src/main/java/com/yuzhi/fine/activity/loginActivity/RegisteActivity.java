package com.yuzhi.fine.activity.loginActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.SharePreferenceUtil1;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.fine.utils.Constant.SHARE_REGISTER_CODE;

public class RegisteActivity extends AppCompatActivity {
    private RegisteActivity mContext = this;
    @Bind(R.id.btnBack)
    Button mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题
    @Bind(R.id.phone)
    EditText mPhone;//手机号码
    @Bind(R.id.phone_regx)
    TextView mPhoneRegx;//手机号码验证
    @Bind(R.id.send_msg)
    Button mSendMsg;//发送验证码
    @Bind(R.id.code)
    EditText mCode;//手机验证码
    @Bind(R.id.nicheng)
    EditText mNiCheng;//昵称
    @Bind(R.id.password)
    EditText mPwd;//密码
    @Bind(R.id.btnSure)
    Button mBtnSure;//注册

    SharePreferenceUtil1 share ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registe);
        ButterKnife.bind(this);
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);
        initUI();
        onClickListener();
    }

    /**
     * 初始化界面
     */
    private void initUI(){
        mPhone.setText("15312376060");
        mCode.setText("078270");
        mNiCheng.setText("安卓菌");
        mPwd.setText("123123");

        //返回
        mBackBtn.setVisibility(View.VISIBLE);
        //标题
        mTextHeaderTitle.setText("注册");
        //手机号码验证隐藏
        mPhoneRegx.setVisibility(View.INVISIBLE);
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

        //登录
        mBtnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });

        //发送验证码
        mSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

    }

    /**
     * 获取数据
     */
    private void initData(){
        String codeId = share.getString(SHARE_REGISTER_CODE, "");// 验证码Id
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile",mPhone.getText().toString().trim());//手机号
        params.put("vaildcode",mCode.getText().toString().trim());//验证码
        params.put("codeid",codeId);//验证码编号
        params.put("username",mNiCheng.getText().toString().trim());//用户名
        params.put("password",mPwd.getText().toString().trim());//密码
        HttpClient.get(Caller.REGISTER,params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                if(!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)){
                    //保存登录状态
                    CommUtil.showToast(message,mContext);
                    UIHelper.showHome(mContext);
                    share.remove(SHARE_REGISTER_CODE);
                }else{
                    CommUtil.showAlert(message,mContext);
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                CommUtil.showToast("登录失败",mContext);
            }
        });

    }

    /**
     * 发送验证码
     */
    private void sendMessage(){
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile",mPhone.getText().toString().trim());//手机号
        HttpClient.get(Caller.SEND_MESSAGE,params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();
                if(!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)){
                    share.putString(SHARE_REGISTER_CODE, data);
                    CommUtil.showToast(message,mContext);
                }else{
                    CommUtil.showAlert(message,mContext);
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                CommUtil.showToast("登录失败",mContext);
            }
        });
    }
}
