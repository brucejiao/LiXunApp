package com.yuzhi.fine.activity.mineActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.model.MineDraftListBean;
import com.yuzhi.fine.ui.FragmentAdapter.MineDraftItemapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的--草稿箱
 */
public class MineDraftActivity extends AppCompatActivity {
    private MineDraftActivity mContext;
    @Bind(R.id.mine_draftbox_list)
    ListView mMineDraftListView;
    @Bind(R.id.btnBack)
    Button mBtnBack;
    @Bind(R.id.textHeadTitle)
    TextView mTextHeadTitle;
    MineDraftItemapter mMineDraftAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_draft);
        ButterKnife.bind(this);
        mContext = this;
        initUI();
    }

    public void initUI(){
        mBtnBack.setVisibility(View.VISIBLE);
        mTextHeadTitle.setText("草稿箱");
        initData();
    }

    //返回
    @OnClick(R.id.btnBack)
    public void onBack(View view){
        finish();
    }

    /**
     * 初始化
     */
    public void initData() {
        ArrayList<MineDraftListBean> arrayBean = new ArrayList<MineDraftListBean>();

        String ImagerURL = "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg";

        for (int i = 0; i < 10; i++) {
            MineDraftListBean bean = new MineDraftListBean();
            bean.setDraftTime("发布时间 ：2017-06-20 10:2"+i);
            bean.setDraftHeaderImg("2"+i);
            bean.setDraftTitle("我要找狗！！！"+i);
            bean.setDraftContent("我丢狗了我丢狗了我丢狗了我丢狗了我丢狗了我丢狗了"+i);
            bean.setDraftPrice("8"+i);
            bean.setDraftEditBtn("编辑");

            arrayBean.add(bean);
        }

        mMineDraftAdapter = new MineDraftItemapter(mContext, arrayBean);
        mMineDraftListView .setAdapter(mMineDraftAdapter);
//        CommUtil.setListViewHeightBasedOnChildren(mMineRLListview, mMineFindItemAdapter);

    }
}
