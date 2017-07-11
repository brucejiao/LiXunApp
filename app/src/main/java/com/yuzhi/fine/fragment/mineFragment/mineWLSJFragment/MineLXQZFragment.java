package com.yuzhi.fine.fragment.mineFragment.mineWLSJFragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.CateGoryID2Name;
import com.yuzhi.fine.model.LXFind.FindListBean;
import com.yuzhi.fine.model.MineFindBean;
import com.yuzhi.fine.ui.FragmentAdapter.MineWLSJItemapter;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.SharePreferenceUtil1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

import static com.yuzhi.fine.utils.CommUtil.getCategoryId2Name;
import static com.yuzhi.fine.utils.CommUtil.showAlert;
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.CommUtil.subMoneyZero;
import static com.yuzhi.fine.utils.Constant.PARENTID_WLQZI;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.fine.utils.Constant.SHARE_LOGIN_USERID;

/**
 *网络社交---立寻圈子
 */
public class MineLXQZFragment extends Fragment {
    private MineLXQZFragment mContext;
    private ProgressDialog progress;
    private SharePreferenceUtil1 share;
    @Bind(R.id.mine_wlsj_all)
    ListView mMineWLSJAllListView;
    private MineWLSJItemapter mMineFindItemAdapter;

    public MineLXQZFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_wlsjall, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = this ;
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        share = new SharePreferenceUtil1(getActivity(), "lx_data", 0);
        getMineFindList();
    }

    //
    /**
     * 立寻圈子
     */
    private void getMineFindList() {
        final ArrayList<MineFindBean> arrayBean = new ArrayList<MineFindBean>();
        //获取的所有菜单的id
        final List<CateGoryID2Name> cateIDList =  share.getModels("categoryIDListKey", CateGoryID2Name.class);
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id

        progress = CommUtil.showProgress(getActivity(), "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userID);//
        params.put("parentid", PARENTID_WLQZI);//

        HttpClient.get(Caller.GET_MINE_NETCHAT, params, new HttpResponseHandler() {
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
                        String moneyPaid = findList.get(index).getMoneyPaid();
                        String categoryID = findList.get(index).getCategoryID();



                        bean.setMineFindHeaderImg(headerImg);
                        bean.setMineFindTime("发布时间:"+createTime);
                        bean.setMineFindLooker(visitCount);
                        bean.setMineFindFocuson(followCount);
                        bean.setMineFindMessage(commentCount);
                        bean.setMineFindPrice(subMoneyZero(moneyPaid,1) + "元");
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
                    mMineFindItemAdapter = new MineWLSJItemapter(getActivity(), arrayBean);
                    mMineWLSJAllListView .setAdapter(mMineFindItemAdapter);
                    mMineWLSJAllListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String publistID = findList.get(position).getPublishID();
                            String mCategoryName = getCategoryId2Name(findList.get(position).getCategoryID(),cateIDList);
                            UIHelper.showWLSJDetails(getActivity(),publistID);
                        }
                    });
                    if (progress != null) {
                        progress.dismiss();
                    }
                } else {
                    showAlert(message, getActivity());
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
                showToast("查询网络社交列表失败", getActivity());
            }
        });
    }

}
