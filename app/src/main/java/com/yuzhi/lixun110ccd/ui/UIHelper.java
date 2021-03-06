package com.yuzhi.lixun110ccd.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.yuzhi.lixun110ccd.activity.HouseDetailActivity;
import com.yuzhi.lixun110ccd.activity.LoginActivity;
import com.yuzhi.lixun110ccd.activity.MainActivity;
import com.yuzhi.lixun110ccd.activity.coreActivity.DetailsActivity;
import com.yuzhi.lixun110ccd.activity.coreActivity.EditDraftActivity;
import com.yuzhi.lixun110ccd.activity.coreActivity.IssueActivity;
import com.yuzhi.lixun110ccd.activity.coreActivity.LXQZActivity;
import com.yuzhi.lixun110ccd.activity.coreActivity.MineRLActivity;
import com.yuzhi.lixun110ccd.activity.coreActivity.MineXSActivity;
import com.yuzhi.lixun110ccd.activity.coreActivity.WLBGActivity;
import com.yuzhi.lixun110ccd.activity.coreActivity.WLQZActivity;
import com.yuzhi.lixun110ccd.activity.coreActivity.WTXRActivity;
import com.yuzhi.lixun110ccd.activity.coreActivity.WTXWActivity;
import com.yuzhi.lixun110ccd.activity.coreActivity.ZLRLActivity;
import com.yuzhi.lixun110ccd.activity.loginActivity.ForgetPassActivity;
import com.yuzhi.lixun110ccd.activity.loginActivity.LXLoginActivity;
import com.yuzhi.lixun110ccd.activity.loginActivity.RegisteActivity;
import com.yuzhi.lixun110ccd.activity.mainActivity.LinkUrlActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.AccountInfosActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.ClueDetailsActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.MineClueActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.MineComplainActivity.MainComplainsActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.MineDraftActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.MineFindActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.MineFocusActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.MinePayActivity.MinePayPriceActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.MinePeopleVerfiActivity.MinePeoVrifiActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.MinePeopleVerfiActivity.MinePeoVrifiPage2Activity;
import com.yuzhi.lixun110ccd.activity.mineActivity.MinePromoteActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.MineProvideClueActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.MineSettingActivity.MainSettingsActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.MineUserCompletedActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.MineWLSJActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.MineZLRLActivity;
import com.yuzhi.lixun110ccd.activity.mineActivity.WLSJDetailsActivity;
import com.yuzhi.lixun110ccd.fragment.mineFragment.MineFragment;
import com.yuzhi.lixun110ccd.model.LXFind.FindListBean;

import java.util.ArrayList;
import java.util.List;

import static com.yuzhi.lixun110ccd.utils.Constant.MINE_REQUEST_REFRESH;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 */
public class UIHelper {

    public final static String TAG = "UIHelper";

    public final static int RESULT_OK = 0x00;
    public final static int REQUEST_CODE = 0x01;

    public static void ToastMessage(Context cont, String msg) {
        if (cont == null || msg == null) {
            return;
        }
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(Context cont, int msg) {
        if (cont == null || msg <= 0) {
            return;
        }
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    public static void ToastMessage(Context cont, String msg, int time) {
        if (cont == null || msg == null) {
            return;
        }
        Toast.makeText(cont, msg, time).show();
    }

    public static void showLogin(Activity context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void showHouseDetailActivity(Activity context) {
        Intent intent = new Intent(context, HouseDetailActivity.class);
        context.startActivity(intent);
    }

    //===============================================================================================================
    //显示主界面
    public static void showHome(Activity context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.finish();
        context.startActivity(intent);
    }

    //显示登录界面
    public static void showLXLogin(Activity context) {
        Intent intent = new Intent(context, LXLoginActivity.class);
        context.startActivity(intent);
    }

    //显示登录界面
    public static void showRegister(Activity context) {
        Intent intent = new Intent(context, RegisteActivity.class);
        context.startActivity(intent);
    }

    //显示忘记密码
    public static void showForgetPwd(Activity context) {
        Intent intent = new Intent(context, ForgetPassActivity.class);
        context.startActivity(intent);
    }

    //显示发布界面
    //将一级菜单的Id传入issueActivity获取二级菜单
    public static void showIssue(Context context, int index) {
        Intent intent = new Intent(context, IssueActivity.class);
        switch (index) {
            case 0://曝光
                intent.putExtra("parentid", "80");
                context.startActivity(intent);
                break;
            case 1://求助
                intent.putExtra("parentid", "81");
                context.startActivity(intent);
                break;
            case 2://圈子
                intent.putExtra("parentid", "549");
                context.startActivity(intent);
                break;
            case 3: //寻人
                intent.putExtra("parentid", "83");
                context.startActivity(intent);
                break;
            case 4: //寻物
                intent.putExtra("parentid", "82");
                context.startActivity(intent);
                break;
            case 5:  //招领认领
                intent.putExtra("parentid", "394");
                context.startActivity(intent);
                break;
            default:
                break;
        }

    }

    //我的-- 我的账户信息
    public static void showMineAccount(Activity context,String account,String xuanshang,String jifen) {
        Intent intent = new Intent(context, AccountInfosActivity.class);
        intent.putExtra("account",account);//余额
        intent.putExtra("xuanshang",xuanshang);//悬赏
        intent.putExtra("jifen",jifen);//积分
        context.startActivity(intent);
    }

    //我的-- 我的寻找
    public static void showMineFind(Activity context) {
        Intent intent = new Intent(context, MineFindActivity.class);
        context.startActivity(intent);
    }

    //我的-- 我的招领认领
    public static void showMinZLRL(Activity context) {
        Intent intent = new Intent(context, MineZLRLActivity.class);
        context.startActivity(intent);
    }

    //我的-- 我的推广
    public static void showMinPromote(Activity context) {
        Intent intent = new Intent(context, MinePromoteActivity.class);
        context.startActivity(intent);
    }

    //我的-- 草稿箱
    public static void showMinDraft(Activity context) {
        Intent intent = new Intent(context, MineDraftActivity.class);
        context.startActivity(intent);
    }

    //主界面--委托找人
    public static void showMainWTZR(Activity context) {
        Intent intent = new Intent(context, WTXRActivity.class);
        context.startActivity(intent);
    }

    //主界面--委托找物
    public static void showMainWTZW(Activity context) {
        Intent intent = new Intent(context, WTXWActivity.class);
        context.startActivity(intent);
    }

    //主界面--招领认领
    public static void showMainZLRL(Activity context) {
        Intent intent = new Intent(context, ZLRLActivity.class);
        context.startActivity(intent);
    }

    //主界面--网络曝光
    public static void showMainWLBG(Activity context) {
        Intent intent = new Intent(context, WLBGActivity.class);
        context.startActivity(intent);
    }

    //主界面--网络求助
    public static void showMainWLQZ(Activity context) {
        Intent intent = new Intent(context, WLQZActivity.class);
        context.startActivity(intent);
    }

    //主界面--立寻圈子
    public static void showMainLXQZ(Activity context) {
        Intent intent = new Intent(context, LXQZActivity.class);
        context.startActivity(intent);
    }

    //展示发布详情界面
    //flag 0:我有线索  1：我要认领  2:查看线索
    public  static void showDetails(Activity context,String publistID,String secondmenu,int flag){
        Intent intent = new Intent(context,DetailsActivity.class);
        intent.putExtra("publistID",publistID);
        intent.putExtra("secondMenu",secondmenu);
        intent.putExtra("flag",flag);
        context.startActivity(intent);
    }

    //我要认领
    public  static void showMineRL(Activity context,String publistID){
        Intent intent = new Intent(context,MineRLActivity.class);
        intent.putExtra("publistID",publistID);
        context.startActivity(intent);
    }

    //我有线索
    public  static void showMineXS(Activity context,String publistID){
        Intent intent = new Intent(context,MineXSActivity.class);
        intent.putExtra("publistID",publistID);
        context.startActivity(intent);
    }

    /**
     * 我的---完善用户信息
     * @param context
     * @param fragment
     * @param userHeader 头像网络路径
     * @param mMySummary 兴趣爱好
     * @param mProvince
     * @param mCity
     * @param mArea
     * @param mAddress 详细地址
     */
    public  static void showCompleteUserInfos(Activity context, MineFragment fragment,String userHeader,
                                              String mMySummary, String mProvince, String mCity,
                                              String mArea, String mAddress){

        Intent intent = new Intent(context,MineUserCompletedActivity.class);
        intent.putExtra("userHeader",userHeader);
        intent.putExtra("mySummary",mMySummary);
        intent.putExtra("province",mProvince);
        intent.putExtra("city",mCity);
        intent.putExtra("area",mArea);
        intent.putExtra("address",mAddress);
        fragment.startActivityForResult(intent, MINE_REQUEST_REFRESH);
    }

    /**
     * 我的---完善用户信息
     * @param context

     */
    public  static void showCompleteUserInfos(Activity context){

        Intent intent = new Intent(context,MineUserCompletedActivity.class);
        context.startActivity(intent);
    }

    //某条发布信息的线索列表
    public  static void showClueList(Activity context,String publistID){
        Intent intent = new Intent(context,MineClueActivity.class);
        intent.putExtra("publistID",publistID);
        context.startActivity(intent);
    }

    //线索列表详情界面
    //flag 0 线索详情  1 招领  2 认领  3 提供线索-- 线索详情
    public  static void showClueDetails(Activity context,String claimID,String flag){
        Intent intent = new Intent(context,ClueDetailsActivity.class);
        intent.putExtra("claimID",claimID);
        intent.putExtra("flag",flag);
        context.startActivity(intent);
    }

    //显示修改草稿箱界面
    public static void showEditDraft(Context context, String categoryID,FindListBean bean) {
        Intent intent = new Intent(context, EditDraftActivity.class);
        Bundle bundle;
        switch (categoryID) {
            case "80"://曝光
                bundle = new Bundle();
                bundle.putString("parentid", "80");
                bundle.putSerializable("bean", bean);
                intent.putExtras(bundle);
                context.startActivity(intent);
                break;
            case "81"://求助
                bundle = new Bundle();
                bundle.putString("parentid", "81");
                bundle.putSerializable("bean", bean);
                intent.putExtras(bundle);
                context.startActivity(intent);
                break;
            case "549"://圈子
                bundle = new Bundle();
                bundle.putString("parentid", "549");
                bundle.putSerializable("bean", bean);
                intent.putExtras(bundle);
                context.startActivity(intent);
                break;
            case "83": //寻人
                bundle = new Bundle();
                bundle.putString("parentid", "83");
                bundle.putSerializable("bean", bean);
                intent.putExtras(bundle);
                context.startActivity(intent);
                break;
            case "82": //寻物
                bundle = new Bundle();
                bundle.putString("parentid", "82");
                bundle.putSerializable("bean", bean);
                intent.putExtras(bundle);
                context.startActivity(intent);
                break;
            case "394":  //招领认领
                bundle = new Bundle();
                bundle.putString("parentid", "394");
                bundle.putSerializable("bean", bean);
                intent.putExtras(bundle);
                context.startActivity(intent);
                break;
            default:
                break;
        }

    }

    //我的---网络社交
    public  static void mineWLSJ(Activity context){
        Intent intent = new Intent(context,MineWLSJActivity.class);
        context.startActivity(intent);
    }

    //网络社交详情界面
    public  static void showWLSJDetails(Activity context,String publishID){
        Intent intent = new Intent(context, WLSJDetailsActivity.class);
        intent.putExtra("publishID",publishID);
        context.startActivity(intent);
    }

    //我的关注列表界面
    public  static void showMineFocus(Activity context/*,String publishID*/){
        Intent intent = new Intent(context, MineFocusActivity.class);
//        intent.putExtra("publishID",publishID);
        context.startActivity(intent);
    }

    //我的关注列表界面
    public  static void showMineProvideClue(Activity context){
        Intent intent = new Intent(context, MineProvideClueActivity.class);
        context.startActivity(intent);
    }

    //我的--个人认证
    public  static void showPeoVrifi(Activity context){
        Intent intent = new Intent(context, MinePeoVrifiActivity.class);
        context.startActivity(intent);
    }

    //我的--个人认证2
    public  static void showPeoVrifi2(Activity context){
        Intent intent = new Intent(context, MinePeoVrifiPage2Activity.class);
        context.startActivity(intent);
    }

    //余额充值
    public  static void showPayPage(Activity context){
        Intent intent = new Intent(context, MinePayPriceActivity.class);
        context.startActivity(intent);
    }

    //设置
    public  static void mineSettingPage(Activity context){
        Intent intent = new Intent(context, MainSettingsActivity.class);
        context.startActivity(intent);
    }

    //投诉建议
    public  static void mineComplainPage(Activity context){
        Intent intent = new Intent(context, MainComplainsActivity.class);
        context.startActivity(intent);
    }


    //H5界面
    public static void toH5Page(Context context, List<String> advList,int pos, String linkurl ,int flag) {
        Intent intent = new Intent(context, LinkUrlActivity.class);
        switch (flag) {
            case 0://轮播
                intent.putExtra("flag", 0);
                intent.putStringArrayListExtra("advList", (ArrayList<String>) advList);
                intent.putExtra("position", pos);
                context.startActivity(intent);
                break;
            case 1://中间广告
                intent.putExtra("flag", 1);
                intent.putExtra("linkurl", linkurl);
                context.startActivity(intent);
                break;
            default:
                break;
        }

    }

}
