package com.yuzhi.lixun110ccd.activity.mineActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.http.Caller;
import com.yuzhi.lixun110ccd.http.HttpClient;
import com.yuzhi.lixun110ccd.http.HttpResponseHandler;
import com.yuzhi.lixun110ccd.http.RestApiResponse;
import com.yuzhi.lixun110ccd.model.CateGoryID2Name;
import com.yuzhi.lixun110ccd.model.ClueBean;
import com.yuzhi.lixun110ccd.model.MineFindBean;
import com.yuzhi.lixun110ccd.ui.FragmentAdapter.MineProvideClueItemapter;
import com.yuzhi.lixun110ccd.ui.UIHelper;
import com.yuzhi.lixun110ccd.utils.CommUtil;
import com.yuzhi.lixun110ccd.utils.SharePreferenceUtil1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

import static com.yuzhi.lixun110ccd.utils.CommUtil.showAlert;
import static com.yuzhi.lixun110ccd.utils.CommUtil.showToast;
import static com.yuzhi.lixun110ccd.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.lixun110ccd.utils.Constant.SHARE_LOGIN_USERID;

/**
 * 我的--提供线索
 */
public class MineProvideClueActivity extends AppCompatActivity {
    private MineProvideClueActivity mContext;
    @Bind(R.id.mine_provide_list)
    ListView mMineProvideList;
    @Bind(R.id.btnBack)
    LinearLayout mBtnBack;
    @Bind(R.id.textHeadTitle)
    TextView mTextHeadTitle;
    private ProgressDialog progress;
    private SharePreferenceUtil1 share;
    private MineProvideClueItemapter MineProvideClueItemapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_provide_clue);
        ButterKnife.bind(this);
        mContext = this;
        initUI();
    }

    public void initUI(){
        mBtnBack.setVisibility(View.VISIBLE);
        mTextHeadTitle.setText("提供线索");
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
    private void initData(){
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);
        getMineFindList();
    }


    /**
     * 获取发布列表（我的寻找）
     */
    private void getMineFindList() {
        final ArrayList<MineFindBean> arrayBean = new ArrayList<MineFindBean>();
        //获取的所有菜单的id
        final   List<CateGoryID2Name> cateIDList =  share.getModels("categoryIDListKey", CateGoryID2Name.class);
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userID);

        HttpClient.get(Caller.GET_MINE_CLUE_LIST, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {

                    final List<ClueBean> findList =  JSON.parseArray(data, ClueBean.class);
                    final int findListNum = findList.size();
                    for(int index = 0;index < findListNum ;index ++){
                        MineFindBean bean = new MineFindBean();
                        String headerImg = findList.get(index).getImgFilePath();
                        String createTime = findList.get(index).getCreateTime();
                        String content = findList.get(index).getContent();
                        String checkState = findList.get(index).getCheckState();// 审核状态(1.待审核，2.审核通过，3.审核不通过)
                        String publishStatus = findList.get(index).getPublishStatus();// 发布状态（1.待发布，2.已发布，3.已结束，4.已完成）
                        String userName = findList.get(index).getUserName();
                        String picturePath = findList.get(index).getPicturePath();
                        String publishTitle = findList.get(index).getPublishTitle();
                        String publishContent = findList.get(index).getPublishContent();
                        String publishMoney = findList.get(index).getPublishMoney();


                        bean.setMineFindHeaderImg(headerImg);
                        bean.setMineFindTime("发布时间:"+createTime);
                        bean.setMineFindTitle(publishTitle);
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
                    MineProvideClueItemapter = new MineProvideClueItemapter(mContext, arrayBean);
                    mMineProvideList .setAdapter(MineProvideClueItemapter);
                    mMineProvideList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //跳转到线索详情界面
                            String claimID = findList.get(position).getClaimID();
                            UIHelper.showClueDetails(mContext,claimID,"3");
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
                showToast("查询我的推广失败", mContext);
            }
        });
    }

}
