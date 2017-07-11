package com.yuzhi.fine.activity.mineActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.CateGoryID2Name;
import com.yuzhi.fine.model.LXFind.FindListBean;
import com.yuzhi.fine.model.MineFindBean;
import com.yuzhi.fine.ui.FragmentAdapter.MineFindItemapter;
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

import static com.yuzhi.fine.utils.CommUtil.getCategoryId2Name;
import static com.yuzhi.fine.utils.CommUtil.showAlert;
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.CommUtil.subMoneyZero;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.fine.utils.Constant.SHARE_LOGIN_USERID;

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
    private ProgressDialog progress;
    private SharePreferenceUtil1 share;
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
        mTextHeadTitle.setText("我的推广");
        initData();
    }

    //返回
    @OnClick(R.id.btnBack)
    public void onBack(View view){
        finish();
    }

    /**
     * 测试数据
     */
    public void testData() {
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

        HttpClient.get(Caller.GET_MINE_PUBLISH, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {

                    final List<FindListBean> findList =  JSON.parseArray(data, FindListBean.class);
                    final int findListNum = findList.size();
                    for(int index = 0;index < findListNum ;index ++){
                        MineFindBean bean = new MineFindBean();
                        //1.头像
                        String headerImg = findList.get(index).getPicturePath();
                        String createTime = findList.get(index).getCreateTime();
                        String visitCount = findList.get(index).getVisitCount();
                        String followCount = findList.get(index).getFollowCount();// 关注人数
                        String commentCount = findList.get(index).getCommentCount();// 评论人数
                        String title = findList.get(index).getTitle();
                        String content = findList.get(index).getContent();
                        String checkState = findList.get(index).getCheckState();// 审核状态(1.待审核，2.审核通过，3.审核不通过)
                        String publishStatus = findList.get(index).getPublishStatus();// 发布状态（1.待发布，2.已发布，3.已结束，4.已完成）
                        String pushMoney = findList.get(index).getPushMoney();
                        String pushType= findList.get(index).getPushType();

                        bean.setMineFindHeaderImg(headerImg);
                        bean.setMineFindTime("发布时间:"+createTime);
                        bean.setMineFindLooker(visitCount);
                        bean.setMineFindFocuson(followCount);
                        bean.setMineFindMessage(commentCount);
                        bean.setMineFindPrice(subMoneyZero(pushMoney,1) + "元/天");
                        bean.setMineFindTitle(title);
                        bean.setMineFindContent(content);
                        bean.setMineFindType(pushType.equals("1")?"全国":"");
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
                    mMineFindItemAdapter = new MineFindItemapter(mContext, arrayBean);
                    mMinePromoteList .setAdapter(mMineFindItemAdapter);
                    mMinePromoteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String publistID = findList.get(position).getPublishID();
                            String mCategoryName = getCategoryId2Name(findList.get(position).getCategoryID(),cateIDList);
                            UIHelper.showDetails(mContext,publistID,mCategoryName,3);
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
