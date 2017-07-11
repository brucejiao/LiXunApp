package com.yuzhi.fine.activity.mineActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.ClueBean;
import com.yuzhi.fine.model.MineFindBean;
import com.yuzhi.fine.ui.FragmentAdapter.MineClueItemapter;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.SharePreferenceUtil1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

import static com.yuzhi.fine.R.id.btnBack;
import static com.yuzhi.fine.utils.CommUtil.showAlert;
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;

/**
 * 我的线索
 */
public class MineClueActivity extends AppCompatActivity {
    private MineClueActivity mContext;
    private ProgressDialog progress;
    private SharePreferenceUtil1 share;
    private MineClueItemapter mMineClueItemAdapter;
    @Bind(R.id.btnBack)
    LinearLayout mBackBtn;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题
    @Bind(R.id.mine_clue_list)
    ListView mMineClueListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_clue);
        mContext = this ;
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        //返回
        mBackBtn.setVisibility(View.VISIBLE);
        //标题
        mTextHeaderTitle.setText("线索列表");
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);
        getMineClueList();
    }

    //返回
    @OnClick(btnBack)
    public void onBack(View view) {
        finish();
    }

    /**
     * 获取线索列表
     */
    private void getMineClueList() {
        final ArrayList<MineFindBean> arrayBean = new ArrayList<MineFindBean>();
        //获取的所有菜单的id
        String publistID = getIntent().getStringExtra("publistID");//发布ID
        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("publishid", publistID);//

        HttpClient.get(Caller.GET_MINE_PUBLIST_CLUE_LIST, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {

                    final List<ClueBean> findList =  JSON.parseArray(data, ClueBean.class);
                    //判断当前界面是否有数据
                    //如果没有数据 则展示默认页
                    isDataNull(findList);
                    final int findListNum = findList.size();
                    for(int index = 0;index < findListNum ;index ++){
                        MineFindBean bean = new MineFindBean();
                        //1.头像
                        String headerImg = findList.get(index).getPicturePath();
                        String createTime = findList.get(index).getCreateTime();
                        String title = findList.get(index).getPublishTitle();
                        String content = findList.get(index).getPublishContent();
                        String publishStatus = findList.get(index).getPublishStatus();// 发布状态（1.待发布，2.已发布，3.已结束，4.已完成）
                        String claimID = findList.get(index).getClaimID();

                        bean.setMineFindHeaderImg(headerImg);
                        bean.setMineFindTime("提供线索时间:"+createTime);
                        bean.setMineFindTitle(title);
                        bean.setMineFindContent(content);
                        switch (publishStatus){
                            case "1":
                                bean.setMineFindIng("待发布");
                                break;
                            case "2":
                                bean.setMineFindIng("已发布");
                                break;
                            case "3":
                                bean.setMineFindIng("已结束");
                                break;
                            case "4":
                                bean.setMineFindIng("已完成");
                                break;
                            default:break;

                        }
                        arrayBean.add(bean);
                    }
                    mMineClueItemAdapter = new MineClueItemapter(mContext, arrayBean);
                    mMineClueListView .setAdapter(mMineClueItemAdapter);
                    //跳转到线索详情界面
                    mMineClueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String claimID = findList.get(position).getClaimID();
                            UIHelper.showClueDetails(mContext,claimID,"0");
                        }
                    });
                    if (progress != null) {
                        progress.dismiss();
                    }
                } else {
                    showAlert(message, mContext);
                    if (progress != null) {
                        progress.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                if (progress != null) {
                    progress.dismiss();
                }
                showToast("查询我的找寻失败", mContext);
            }
        });
    }

    /**
     * 判断当前界面是否有数据
     * 如果没有数据 则展示默认页
     * @param findList
     */
    private void isDataNull(List findList){
        if (CommUtil.isNullOrBlank(findList)){
            setContentView(R.layout.activity_nodata_default);
            Button btnBack = (Button)findViewById(R.id.btnBack);
            TextView textHeadTitle = (TextView)findViewById(R.id.textHeadTitle);
            TextView default_return_mainpage = (TextView)findViewById(R.id.default_return_mainpage);
            //返回
            btnBack.setVisibility(View.VISIBLE);
            //标题
            textHeadTitle.setText("线索列表");
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            default_return_mainpage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UIHelper.showHome(mContext);
                    finish();
                }
            });
            if (progress != null) {
                progress.dismiss();
            }
            return;
        }
    }

}
