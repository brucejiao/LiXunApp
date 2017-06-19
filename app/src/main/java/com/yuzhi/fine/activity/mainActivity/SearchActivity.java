package com.yuzhi.fine.activity.mainActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.ui.FlowTagLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

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
        initUI();
    }

    private void initUI(){
        String[] tagArr = TAGS.split(" ");
        for (String tag : tagArr) {

            TextView textView = makeTextView();
            textView.setText(tag);
            mFlowTagLayout.addView(textView);
        }
    }





    private TextView makeTextView() {
        TextView textView = new TextView(this);
        textView.setBackgroundResource(R.drawable.editsharp_white);
        textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(Color.GRAY);
        textView.setTextSize(10);
        return textView;
    }

    private void initData(){

    }


    @OnClick(R.id.btnBack)
    public void onBack(View view){
        finish();
    }
}
