package com.yuzhi.fine.fragment.mineFragment.mineZLRLFragment;


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
import com.yuzhi.fine.R;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.ClueBean;
import com.yuzhi.fine.model.MineZLRL;
import com.yuzhi.fine.ui.FragmentAdapter.MineZLRLItemapter;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.SharePreferenceUtil1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

import static com.yuzhi.fine.utils.CommUtil.showAlert;
import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.fine.utils.Constant.SHARE_LOGIN_USERID;

/**
 *我的--招领认领--我的认领
 */
public class MineRLFragment extends Fragment {
    @Bind(R.id.mine_rl_list)
    ListView mMineRLListview;
    private MineRLFragment mContext;
    private ProgressDialog progress;
    private SharePreferenceUtil1 share;

    private MineZLRLItemapter mMineZLRLAdapter;

    public MineRLFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine_rl, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = this;
//        init();
        share = new SharePreferenceUtil1(getActivity(), "lx_data", 0);
        getMineZLRLList();
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
//        ArrayList<MineFindBean> arrayBean = new ArrayList<MineFindBean>();
//
//        String ImagerURL = "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg";
//
//        for (int i = 0; i < 10; i++) {
//            MineFindBean bean = new MineFindBean();
//            bean.setMineFindTime("发布时间 ：2017-06-20 10:2"+i);
//            bean.setMineFindLooker("2"+i);
//            bean.setMineFindFocuson("1"+i);
//            bean.setMineFindMessage("11"+i);
//            bean.setMineFindPrice("8"+i);
//            bean.setMineFindTitle("我要找狗！！！");
//            bean.setMineFindContent("我丢狗了我丢狗了我丢狗了我丢狗了我丢狗了我丢狗了");
//            bean.setMineFindIng("进行中...");
//            bean.setMineFindPeopleNum("线索提供(7)");
//            arrayBean.add(bean);
//        }
//
//        mMineFindItemAdapter = new MineFindItemapter(getActivity(), arrayBean);
//        mMineRLListview .setAdapter(mMineFindItemAdapter);

    }
    /**
     * 获取发布列表（我的寻找）
     */
    private void getMineZLRLList() {
        final ArrayList<MineZLRL> arrayBean = new ArrayList<MineZLRL>();
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id

        progress = CommUtil.showProgress(getActivity(), "正在加载数据，请稍候...");
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userID);//

        HttpClient.get(Caller.GET_MINE_RL, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {

                    final List<ClueBean> findList =  JSON.parseArray(data, ClueBean.class);
                    final int findListNum = findList.size();
                    for(int index = 0;index < findListNum ;index ++){
                        MineZLRL bean = new MineZLRL();
                        //1.头像
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

                        bean.setZlrl_header_img(headerImg);
                        bean.setZlrl_username(userName);
                        bean.setZlrl_time("认领时间"+createTime);
                        bean.setZlrl_pic(picturePath);
                        bean.setZlrl_title(publishTitle);
                        bean.setZlrl_content(content);
                        bean.setZlrl_price(publishMoney+"元");

                        switch (checkState){
                            case "1":
                                bean.setZlrl_checkstate("待审核");
                                break;
                            case "2":
                                bean.setZlrl_checkstate("审核通过");
                                break;
                            case "3":
                                bean.setZlrl_checkstate("审核不通过");
                                break;
                            default:break;

                        }
                        arrayBean.add(bean);
                    }
                    mMineZLRLAdapter = new MineZLRLItemapter(getActivity(), arrayBean);
                    mMineRLListview .setAdapter(mMineZLRLAdapter);
                    //跳转到线索详情界面
                    mMineRLListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String claimID = findList.get(position).getClaimID();
                            UIHelper.showClueDetails(getActivity(),claimID,"2");
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
                showToast("查询我的招领失败", getActivity());
            }
        });
    }


}
