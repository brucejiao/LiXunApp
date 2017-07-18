package com.yuzhi.fine.activity.mainActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.ui.FlowTagLayout;
import com.yuzhi.fine.utils.DeviceUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    private SearchActivity mContext;
    @Bind(R.id.flowTagLayout)
    FlowTagLayout mFlowTagLayout;//瀑布流标签
    @Bind(R.id.btnBack)
    ImageView mbtnBack;//返回


    public static final String TAGS = "找狗狗 法律顾问 失踪儿童 找债权人 我的好战友 我的钱包丢了 找狗狗";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mContext = this;
        initUI();
    }

    private void initUI() {
        String[] tagArr = TAGS.split(" ");
        for (int index = 0 ;index <tagArr.length ; index ++) {
            TextView textView = makeTextView();
            textView.setText(tagArr[index]);
            mFlowTagLayout.addView(textView);
        }
    }


    private TextView makeTextView() {
        TextView textView = new TextView(this);
        textView.setBackgroundResource(R.drawable.editsharp_white);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(Color.GRAY);
        textView.setTextSize(DeviceUtil.px2sp(mContext, 26));
        LinearLayout.LayoutParams mLayoutParams = new
                LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mLayoutParams.leftMargin = DeviceUtil.px2dp(mContext, 20);
        mLayoutParams.rightMargin = DeviceUtil.px2dp(mContext, 20);
        mLayoutParams.topMargin = DeviceUtil.px2dp(mContext, 20);
        mLayoutParams.bottomMargin = DeviceUtil.px2dp(mContext, 20);
        mLayoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(mLayoutParams);
        return textView;
    }

    private void initData() {

    }


    @OnClick(R.id.btnBack)
    public void onBack(View view) {
        finish();
    }
}
