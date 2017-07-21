package com.yuzhi.lixun110ccd.fragment.mineFragment.mineFindFragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.http.Caller;
import com.yuzhi.lixun110ccd.http.HttpClient;
import com.yuzhi.lixun110ccd.http.HttpResponseHandler;
import com.yuzhi.lixun110ccd.http.RestApiResponse;
import com.yuzhi.lixun110ccd.model.CateGoryID2Name;
import com.yuzhi.lixun110ccd.model.LXFind.FindListBean;
import com.yuzhi.lixun110ccd.model.MineFindBean;
import com.yuzhi.lixun110ccd.ui.FragmentAdapter.MineFindItemapter;
import com.yuzhi.lixun110ccd.ui.UIHelper;
import com.yuzhi.lixun110ccd.utils.CommUtil;
import com.yuzhi.lixun110ccd.utils.SharePreferenceUtil1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

import static com.yuzhi.lixun110ccd.utils.CommUtil.getCategoryId2Name;
import static com.yuzhi.lixun110ccd.utils.CommUtil.showAlert;
import static com.yuzhi.lixun110ccd.utils.CommUtil.showToast;
import static com.yuzhi.lixun110ccd.utils.CommUtil.subMoneyZero;
import static com.yuzhi.lixun110ccd.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.lixun110ccd.utils.Constant.SHARE_LOGIN_USERID;

/**
 * 我的--我的寻找（全部）
 */
public class MineFindAllFragment extends Fragment {
    private MineFindAllFragment mContext;
    private ProgressDialog progress;
    private SharePreferenceUtil1 share;
    @Bind(R.id.mine_find_all)
    ListView mMineFindAllListView;
    private MineFindItemapter mMineFindItemAdapter;

    public MineFindAllFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_find_all, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = this ;
//        init();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    /**
     * 初始化
     */
    public void init() {
        share = new SharePreferenceUtil1(getActivity(), "lx_data", 0);
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

        progress = CommUtil.showProgress(getActivity(), "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userID);//

        HttpClient.get(Caller.GET_MINE_FIND_LIST, params, new HttpResponseHandler() {
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
                        bean.setMineFindTime("发布时间:"+CommUtil.subTime(createTime));
                        bean.setMineFindLooker(visitCount);
                        bean.setMineFindFocuson(followCount);
                        bean.setMineFindMessage(commentCount);
                        bean.setMineFindPrice(subMoneyZero(moneyPaid,2) + "元");
                        bean.setMineFindTitle(title);
                        bean.setMineFindContent(content);
                        switch (checkState){
                            case "1":
                                bean.setMineFindIng("待审核");
                                break;
                            case "2":
                                bean.setMineFindIng("审核通过");
                                break;
                            case "3":
                                bean.setMineFindIng("审核不通过");
                                break;
                            default:break;

                        }
                        arrayBean.add(bean);
                    }
                    mMineFindItemAdapter = new MineFindItemapter(getActivity(), arrayBean);
                    mMineFindAllListView .setAdapter(mMineFindItemAdapter);
                    mMineFindAllListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String publistID = findList.get(position).getPublishID();
                            String mCategoryName = getCategoryId2Name(findList.get(position).getCategoryID(),cateIDList);
                            UIHelper.showDetails(getActivity(),publistID,mCategoryName,2);
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
                showToast("查询我的找寻失败", getActivity());
            }
        });
    }

}
