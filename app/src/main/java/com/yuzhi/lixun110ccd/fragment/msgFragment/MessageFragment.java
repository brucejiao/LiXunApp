package com.yuzhi.lixun110ccd.fragment.msgFragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.http.Caller;
import com.yuzhi.lixun110ccd.http.HttpClient;
import com.yuzhi.lixun110ccd.http.HttpResponseHandler;
import com.yuzhi.lixun110ccd.http.RestApiResponse;
import com.yuzhi.lixun110ccd.model.UserInfos.MessageList;
import com.yuzhi.lixun110ccd.ui.FragmentAdapter.MessageItemapter;
import com.yuzhi.lixun110ccd.utils.CommUtil;
import com.yuzhi.lixun110ccd.utils.SharePreferenceUtil1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.yuzhi.lixun110ccd.utils.CommUtil.currentDate;
import static com.yuzhi.lixun110ccd.utils.CommUtil.daysBetween2;
import static com.yuzhi.lixun110ccd.utils.CommUtil.showToast;
import static com.yuzhi.lixun110ccd.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.lixun110ccd.utils.Constant.SHARE_LOGIN_USERID;

/**
 * 消息
 */
public class MessageFragment extends Fragment {
    private Activity context;
    @Bind(R.id.textHeadTitle)
    TextView mHeader;
    @Bind(R.id.message_listview)
    ListView mMessageListView;
    private ProgressDialog progress;
    private SharePreferenceUtil1 share;
    private MessageItemapter msgAdapter;

    public MessageFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initData();
        initView();
    }

    private void initView() {
        mHeader.setText("消息");


    }

    private void initData() {
        share = new SharePreferenceUtil1(getActivity(), "lx_data", 0);
        getMessageList();
    }

    /**
     * 获取消息列表
     */
    private void getMessageList() {
        progress = CommUtil.showProgress(getActivity(), "正在加载数据，请稍候...");
        String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", userID);
        final ArrayList<MessageList> arrayBean = new ArrayList<MessageList>();
        HttpClient.get(Caller.GET_MESSAGE_LIST, params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String result = response.getResult();
                String message = response.getMessage();
                String data = response.getData();

                if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                    final List<MessageList> messageList =  parseArray(data, MessageList.class);
                    final int msgListNum = messageList.size();
                    for (int index  = 0 ; index < msgListNum ; index ++){
                        MessageList msgBean = new MessageList();
                        //接口数据
                        String messageCategoryID = messageList.get(index).getMessageCategoryID();
                        String messageSubject = messageList.get(index).getMessageSubject();
                        String messageBody = messageList.get(index).getMessageBody();
                        String messageTime = messageList.get(index).getMessageTime();
                        String messageIsDelete = messageList.get(index).getIsDelete();
                        String messageIsRead = messageList.get(index).getIsRead();
                        String messageCategoryName = messageList.get(index).getCategoryName();

                        String distanceTime = daysBetween2(messageTime,currentDate());

                        msgBean.setMessageCategoryID(messageCategoryID);
                        msgBean.setMessageSubject(messageSubject);
                        msgBean.setMessageBody(messageBody);
                        msgBean.setMessageTime(distanceTime);
                        msgBean.setIsDelete(messageIsDelete);
                        msgBean.setIsRead(messageIsRead);
                        msgBean.setCategoryName(messageCategoryName);

                        arrayBean.add(msgBean);
                    }


                    msgAdapter = new MessageItemapter(getActivity(), arrayBean);
                    mMessageListView.setAdapter(msgAdapter);
//                    CommUtil.setListViewHeightBasedOnChildren(mMessageListView, msgAdapter);
//                    mFindXSListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            String publistID = findList.get(position).getPublishID();
//                            String mCategoryName = getCategoryId2Name(findList.get(position).getCategoryID(),cateIDList);
//                            UIHelper.showDetails(getActivity(),publistID,mCategoryName,0);
//                        }
//                    });

                    if (progress != null) {
                        progress.dismiss();
                    }

                } else {
                    showToast(message, getActivity());
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
                showToast("悬赏列表获取失败", getActivity());
            }
        });

    }

}
