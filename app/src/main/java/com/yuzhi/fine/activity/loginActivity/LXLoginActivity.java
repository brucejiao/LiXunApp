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

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

public class LXLoginActivity extends AppCompatActivity {

    private LXLoginActivity mContext = this;

    @Bind(R.id.btnBack)
    Button mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题
    @Bind(R.id.phone)
    EditText mPhone;//手机号码
    @Bind(R.id.phone_regx)
    TextView mPhoneRegx;//手机号码验证
    @Bind(R.id.password)
    EditText mPassWord;//手机密码
    @Bind(R.id.btnLogin)
    Button mLogin;//登录
    @Bind(R.id.lx_register)
    TextView mLXRegister;//注册入口
    @Bind(R.id.lx_forgetpwd)
    TextView mForgetPwd;//忘记密码入口
    @Bind(R.id.fastlogin_qq)
    Button mFastLoginQQ;//QQ登录
    @Bind(R.id.fastlogin_wx)
    Button mFastLoginWX;//微信登录
    @Bind(R.id.fastlogin_sina)
    Button mFastLoginSina;//新浪登录




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lxlogin);
        ButterKnife.bind(this);

        initUI();
        onClickListener();
    }

    /**
     * 初始化界面
     */
    private void initUI(){
        //测试数据
        mPhone.setText("18724199038");
        mPassWord.setText("123123");
        //返回
        mBackBtn.setVisibility(View.VISIBLE);
        //标题
        mTextHeaderTitle.setText("登录");
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
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        //跳转注册
        mLXRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            UIHelper.showRegister(mContext);

            }
        });
        //跳转忘记密码
        mForgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showForgetPwd(mContext);
            }
        });
    }


    /**
     * 获取数据
     */
    private void initData(){
        HashMap<String, String> params = new HashMap<>();
        params.put("password",mPassWord.getText().toString().trim());
        params.put("mobile",mPhone.getText(

        ).toString().trim());
        HttpClient.get(Caller.LOGIN,params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                if(!CommUtil.isNullOrBlank(result) && result.equals("true")){
                    //保存登录状态
                    UIHelper.showHome(mContext);
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
