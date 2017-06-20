package com.yuzhi.fine.activity.mineActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.model.MineFindBean;
import com.yuzhi.fine.ui.FragmentAdapter.MineFindItemapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的--我的推广
 */
public class MinePromoteActivity extends AppCompatActivity {
    private MinePromoteActivity mContext;
    @Bind(R.id.mine_promote_list)
    ListView mMinePromoteList;
    @Bind(R.id.btnBack)
    Button mBtnBack;
    @Bind(R.id.textHeadTitle)
    TextView mTextHeadTitle;
    private MineFindItemapter mMineFindItemAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_promote);
        ButterKnife.bind(this);
        mContext = this;
        initUI();
    }

    public void initUI(){
        mBtnBack.setVisibility(View.VISIBLE);
        mTextHeadTitle.setText("我的寻找");
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
        ArrayList<MineFindBean> arrayBean = new ArrayList<MineFindBean>();

        String ImagerURL = "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg";

        for (int i = 0; i < 10; i++) {
            MineFindBean bean = new MineFindBean();
            bean.setMineFindTime("发布时间 ：2017-06-20 10:2"+i);
            bean.setMineFindLooker("2"+i);
            bean.setMineFindFocuson("1"+i);
            bean.setMineFindMessage("11"+i);
            bean.setMineFindPrice("8"+i);
            bean.setMineFindTitle("我要找狗！！！");
            bean.setMineFindContent("我丢狗了我丢狗了我丢狗了我丢狗了我丢狗了我丢狗了");
            bean.setMineFindIng("进行中...");
            bean.setMineFindPeopleNum("线索提供(7)");
            arrayBean.add(bean);
        }

        mMineFindItemAdapter = new MineFindItemapter(mContext, arrayBean);
        mMinePromoteList .setAdapter(mMineFindItemAdapter);

    }
}
