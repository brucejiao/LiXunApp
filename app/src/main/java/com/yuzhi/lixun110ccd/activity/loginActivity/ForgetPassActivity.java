package com.yuzhi.lixun110ccd.activity.loginActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.http.Caller;
import com.yuzhi.lixun110ccd.http.HttpClient;
import com.yuzhi.lixun110ccd.http.HttpResponseHandler;
import com.yuzhi.lixun110ccd.http.RestApiResponse;
import com.yuzhi.lixun110ccd.ui.UIHelper;
import com.yuzhi.lixun110ccd.utils.CommUtil;
import com.yuzhi.lixun110ccd.utils.SharePreferenceUtil1;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

import static com.yuzhi.lixun110ccd.utils.Constant.RESUTL_TRUE;

public class ForgetPassActivity extends AppCompatActivity {
    private ForgetPassActivity mContext = this;
    @Bind(R.id.btnBack)
    LinearLayout mBackBtn;//返回
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
    @Bind(R.id.password)
    EditText mPwd;//新密码
    @Bind(R.id.btnSure)
    Button mBtnSure;//注册

    SharePreferenceUtil1 share ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        ButterKnife.bind(this);
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);
        initUI();
        onClickListener();
    }


    /**
     * 初始化界面
     */
    private void initUI(){
        //返回
        mBackBtn.setVisibility(View.VISIBLE);
        //标题
        mTextHeaderTitle.setText("找回密码");
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

        //发送验证码
        mSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        //登录
        mBtnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    /**
     * 获取数据
     */
    private void initData(){
        String codeId = share.getString("pwdcodeId", "");// 验证码Id
        HashMap<String, String> params = new HashMap<>();
        params.put("mobile",mPhone.getText().toString().trim());//手机号
        params.put("vaildcode",mCode.getText().toString().trim());//验证码
        params.put("codeid",codeId);//验证码编号
        params.put("password",mPwd.getText().toString().trim());//密码
        HttpClient.get(Caller.FORGET_PWD,params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                if(!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)){
                    //保存登录状态
                    CommUtil.showToast(message,mContext);
                    UIHelper.showHome(mContext);
                    share.remove("pwdcodeId");
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
                    share.putString("pwdcodeId", data);
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
