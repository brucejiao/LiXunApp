package com.yuzhi.lixun110ccd.activity.mainActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.http.Caller;
import com.yuzhi.lixun110ccd.http.HttpClient;
import com.yuzhi.lixun110ccd.http.HttpResponseHandler;
import com.yuzhi.lixun110ccd.http.RestApiResponse;
import com.yuzhi.lixun110ccd.model.IssueModel.SecondMenu;
import com.yuzhi.lixun110ccd.ui.SXSecondItemAdapter;
import com.yuzhi.lixun110ccd.utils.CommUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Request;

public class ShaiXuanActivity extends AppCompatActivity {

    private  ShaiXuanActivity mContext;

    @Bind(R.id.btnBack)
    LinearLayout mBtnBack;//返回
    @Bind(R.id.textHeadTitle)
    TextView mTextHeaderTitle;//标题
    @Bind(R.id.sx_wtxr)
    TextView mSxWtxr;//委托寻人
    @Bind(R.id.sx_wtxw)
    TextView mSxWtxw;//委托寻物
    @Bind(R.id.sx_zlrl)
    TextView mSxZlrl;//招领认领
    @Bind(R.id.sx_wlqz)
    TextView mSxWlqz;//网络求助
    @Bind(R.id.sx_wlbg)
    TextView mSxWlbg;//网络曝光
    @Bind(R.id.sx_lxqz)
    TextView mSxLxqz;//立寻圈子
    @Bind(R.id.sx_listview)
    ListView mSxListView;//二级菜单

    SXSecondItemAdapter mSxSecondItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shai_xuan);
        ButterKnife.bind(this);
        mContext = this ;
        initUI();
    }


    private  void initUI(){
        mBtnBack.setVisibility(View.VISIBLE);
        mTextHeaderTitle.setText("选择分类");
    }


    @OnClick(R.id.btnBack)
    public void setmBtnBack(View view){
        finish();
    }

    //筛选事件
    @OnClick({R.id.sx_wtxr,R.id.sx_wtxw,R.id.sx_zlrl,
              R.id.sx_wlqz,R.id.sx_wlbg,R.id.sx_lxqz})
    public void choicedItem(View view){
        switch (view.getId()){
            case R.id.sx_wlbg://网络曝光
                checkedWLBGColors();
                getIssueSecondList("80");
                break;
            case R.id.sx_wlqz://网络求助
                checkedWLQZColors();
                getIssueSecondList("81");
                break;
            case R.id.sx_lxqz://立寻圈子
                checkedLXQZColors();
                getIssueSecondList("549");
                break;
            case R.id.sx_wtxr://委托寻人
                checkedWTXRColors();
                CommUtil.showAlert("委托寻人",mContext);
                getIssueSecondList("83");
                break;
            case R.id.sx_wtxw://委托寻物
                checkedWTXWColors();
                CommUtil.showAlert("委托寻物",mContext);
                getIssueSecondList("82");
                break;
            case R.id.sx_zlrl://招领认领
                checkedZLRLColors();
                getIssueSecondList("394");
                break;
            default:break;
        }

    }





    /**
     * 目标类型--获取发布类别列表（二级）
     */
    private void getIssueSecondList(String value){
        HashMap<String, String> params = new HashMap<>();
        params.put("parentid",value);

        HttpClient.get(Caller.ISSUE_TYPE_SECOND_LIST,params, new HttpResponseHandler() {
            @Override
            public void onSuccess(RestApiResponse response) {
                String     result   =  response.getResult();
                String     message  =  response.getMessage();
                String     data     =  response.getData();

                if(!CommUtil.isNullOrBlank(result) && result.equals("true")){
                    List<SecondMenu> menu     =  JSON.parseArray(data, SecondMenu.class);
                    final    int     menuNum  =  menu.size();
                    List<SecondMenu>  menuList = new ArrayList<SecondMenu>();

                    for (int index = 0 ; index < menuNum ; index ++){
                        SecondMenu menuMap  = new SecondMenu();
                        String     cateID    =  menu.get(index).getCategoryID();
                        String     cateTitle =  menu.get(index).getCategoryTitle();
                        menuMap.setCategoryID(cateID);
                        menuMap.setCategoryTitle(cateTitle);
                        menuList.add(menuMap);

                        mSxSecondItemAdapter = new SXSecondItemAdapter(mContext, menuList);
                    }
                    mSxListView.setAdapter(mSxSecondItemAdapter);


                }else{
                    CommUtil.showToast(message,mContext);
                }
            }

            @Override
            public void onFailure(Request request, Exception e) {
                CommUtil.showToast("二级菜单获取失败",mContext);
            }
        });
    }

    //网络曝光
    private  void  checkedWLBGColors(){
        mSxWlbg.setBackgroundResource(R.color.sx_gary);
        mSxWlqz.setBackgroundResource(R.color.white);
        mSxLxqz.setBackgroundResource(R.color.white);
        mSxWtxr.setBackgroundResource(R.color.white);
        mSxWtxw.setBackgroundResource(R.color.white);
        mSxZlrl.setBackgroundResource(R.color.white);
    }

    //网络求助
    private  void  checkedWLQZColors(){
        mSxWlqz.setBackgroundResource(R.color.sx_gary);
        mSxWlbg.setBackgroundResource(R.color.white);
        mSxLxqz.setBackgroundResource(R.color.white);
        mSxWtxr.setBackgroundResource(R.color.white);
        mSxWtxw.setBackgroundResource(R.color.white);
        mSxZlrl.setBackgroundResource(R.color.white);
    }

    //立寻圈子
    private  void  checkedLXQZColors(){
        mSxLxqz.setBackgroundResource(R.color.sx_gary);
        mSxWlqz.setBackgroundResource(R.color.white);
        mSxWlbg.setBackgroundResource(R.color.white);
        mSxWtxr.setBackgroundResource(R.color.white);
        mSxWtxw.setBackgroundResource(R.color.white);
        mSxZlrl.setBackgroundResource(R.color.white);
    }

    //委托寻人
    private  void  checkedWTXRColors(){
        mSxWtxr.setBackgroundResource(R.color.sx_gary);
        mSxLxqz.setBackgroundResource(R.color.white);
        mSxWlqz.setBackgroundResource(R.color.white);
        mSxWlbg.setBackgroundResource(R.color.white);
        mSxWtxw.setBackgroundResource(R.color.white);
        mSxZlrl.setBackgroundResource(R.color.white);
    }

    //委托寻物
    private  void  checkedWTXWColors(){
        mSxWtxw.setBackgroundResource(R.color.sx_gary);
        mSxLxqz.setBackgroundResource(R.color.white);
        mSxWlqz.setBackgroundResource(R.color.white);
        mSxWlbg.setBackgroundResource(R.color.white);
        mSxWtxr.setBackgroundResource(R.color.white);
        mSxZlrl.setBackgroundResource(R.color.white);
    }

    //招领认领
    private  void  checkedZLRLColors(){
        mSxZlrl.setBackgroundResource(R.color.sx_gary);
        mSxLxqz.setBackgroundResource(R.color.white);
        mSxWlqz.setBackgroundResource(R.color.white);
        mSxWlbg.setBackgroundResource(R.color.white);
        mSxWtxr.setBackgroundResource(R.color.white);
        mSxWtxw.setBackgroundResource(R.color.white);
    }

}
