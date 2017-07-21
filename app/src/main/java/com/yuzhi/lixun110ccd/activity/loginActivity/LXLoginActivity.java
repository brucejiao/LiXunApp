package com.yuzhi.lixun110ccd.activity.loginActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.http.Caller;
import com.yuzhi.lixun110ccd.http.HttpClient;
import com.yuzhi.lixun110ccd.http.HttpResponseHandler;
import com.yuzhi.lixun110ccd.http.RestApiResponse;
import com.yuzhi.lixun110ccd.model.LXLogin.LXLoginBean;
import com.yuzhi.lixun110ccd.ui.UIHelper;
import com.yuzhi.lixun110ccd.utils.CommUtil;
import com.yuzhi.lixun110ccd.utils.SharePreferenceUtil1;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

import static com.yuzhi.lixun110ccd.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.lixun110ccd.utils.Constant.SHARE_LOGIN_ISLOGIN;
import static com.yuzhi.lixun110ccd.utils.Constant.SHARE_LOGIN_USERID;

public class LXLoginActivity extends AppCompatActivity {

    private LXLoginActivity mContext = this;

    @Bind(R.id.btnBack)
    LinearLayout mBackBtn;//返回
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

    SharePreferenceUtil1 share ;
    private boolean isLogin = false;//登录状态




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lxlogin);
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
        isLogin = false;
        share.putBoolean(SHARE_LOGIN_ISLOGIN,isLogin);
        HashMap<String, String> params = new HashMap<>();
        params.put("password",mPassWord.getText().toString().trim());
        params.put("mobile",mPhone.getText().toString().trim());
        HttpClient.get(Caller.LOGIN,params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();
                if(!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)){
                    isLogin = true;
                    LXLoginBean bean = JSON.parseObject(data,LXLoginBean.class);
//                    CommUtil.showAlert(bean.getUserID()+"---"+bean.getUserName(),mContext);
                    share.putString(SHARE_LOGIN_USERID,bean.getUserID());//保存用户Id
                    //保存登录状态
                    UIHelper.showHome(mContext);
                    CommUtil.showToast(message,mContext);
                    share.putBoolean(SHARE_LOGIN_ISLOGIN,isLogin);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            UIHelper.showHome(mContext);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
