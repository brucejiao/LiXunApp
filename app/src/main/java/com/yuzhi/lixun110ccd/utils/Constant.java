package com.yuzhi.lixun110ccd.utils;

/**
 * Created by JiaoJianJun on 2017/6/7.
 *
 *   Picasso.with(activity).load(bean.getImgFilePath())
 .resize(DeviceUtil.dp2px(activity, 25), DeviceUtil.dp2px(activity, 25))
 .placeholder(R.drawable.default_image).into(holder.lx_header_img);
 */

public class Constant {
    public static final String BAIDU_AK = "7rz5hfbqvtU9FIvROxfUtA6bj4wOXypL";
    public static final String RESUTL_TRUE = "true";
    public static final String RESUTL_FALSE = "false";

    //发布--选择图片一
    public static final int ISSUE_RESULT_FIRST = 0x1001;
    //发布--选择图片二
    public static final int ISSUE_RESULT_SECOND = 0x1002;
    //发布--选择图片三
    public static final int ISSUE_RESULT_THIRD = 0x1003;
    //发布--选择图片四
    public static final int ISSUE_RESULT_FOURTH = 0x1004;
    //发布--选择图片五
    public static final int ISSUE_RESULT_FIVETH = 0x1005;
    //发布--选择图片六
    public static final int ISSUE_RESULT_SIXTH = 0x1006;
    //发布--选择图片七
    public static final int ISSUE_RESULT_SEVENTH = 0x1007;
    //发布--选择图片八
    public static final int ISSUE_RESULT_EIGHTH = 0x1008;

    //首页--定位
    public static final int LX_MAIN_ADDRESS_RESULT = 0x1009;
    public static final int LX_MAIN_ADDRESS_REQUEST = 0x1010;

    //我的认领--选择图片
    public static final int RL_RESULT_FIRST = 0x1011;
    public static final int RL_RESULT_SECOND = 0x1012;
    public static final int RL_RESULT_THIRD = 0x1013;
    public static final int RL_RESULT_FOURTH = 0x1014;

    //我的--修改用户头像
    public static final int MINE_MODIFY_USER_HEADER = 0x1015;

    //我有线索--选择图片
    public static final int XS_RESULT_FIRST = 0x1015;
    public static final int XS_RESULT_SECOND = 0x1016;
    public static final int XS_RESULT_THIRD = 0x1017;
    public static final int XS_RESULT_FOURTH = 0x1018;

    //我的
    public static final int MINE_REQUEST_REFRESH = 0x1019;
    public static final int MINE_RESULT_REFRESH = 0x1020;

    //我的--上传身份证
    public static final int UPLOAD_IDCARD_A= 0x1020;
    public static final int UPLOAD_IDCARD_B = 0x1021;


    //SharePreferenceUtil1 Constant
    public static final String SHARE_LOGIN_USERID = "lxUserId";//用户ID
    public static final String SHARE_LOGIN_ISLOGIN = "isLogin";//是否登录过
    public static final String SHARE_REGISTER_CODE = "codeId";//注册码ID



    //Parentid
    public static final String PARENTID_WLBG = "80";//网络曝光
    public static final String PARENTID_WLQZHU = "81";//网络求助
    public static final String PARENTID_WTXW= "82";//委托寻物
    public static final String PARENTID_WTXR = "83";//委托寻人
    public static final String PARENTID_ZLRL = "394";//招领认领
    public static final String PARENTID_WLQZI= "549";//网络圈子





}
