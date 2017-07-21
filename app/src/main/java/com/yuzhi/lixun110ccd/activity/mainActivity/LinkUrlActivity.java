package com.yuzhi.lixun110ccd.activity.mainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.utils.SharePreferenceUtil1;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 跳转H5界面
 */
public class LinkUrlActivity extends AppCompatActivity {
    private LinkUrlActivity mContext;
    @Bind(R.id.btnBack)
    LinearLayout mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题
    SharePreferenceUtil1 share;
    private int position;
    private List<String> liknUrls; //跳转url列表
    @Bind(R.id.linkurl)
    WebView mLinlUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_url);
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
        mTextHeaderTitle.setText("立寻");
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData(){
        Intent intent = getIntent();
       int  flag = intent.getIntExtra("flag", 0);
        switch (flag){
            case 0:
                firstAD();
                break;
            case 1:
                secondAD();
                break;
            default:
                break;

        }
    }



    private void firstAD(){
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        liknUrls = intent.getStringArrayListExtra("advList");
        if(liknUrls == null) {
            liknUrls = new ArrayList<>();
        }
        mLinlUrl.loadUrl(liknUrls.get(position).toString());
        webSetting();
    }

    private void secondAD(){
        Intent intent = getIntent();
        String  linkurl = intent.getStringExtra("linkurl");
        mLinlUrl.loadUrl(linkurl);
        webSetting();
    }

    private void webSetting(){
        WebSettings webSettings = mLinlUrl.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);//关键点

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        webSettings.setDisplayZoomControls(true);
        webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setBuiltInZoomControls(false); // 设置显示缩放按钮
        webSettings.setSupportZoom(true); // 支持缩放
        webSettings.setLoadWithOverviewMode(true);
    }
}
