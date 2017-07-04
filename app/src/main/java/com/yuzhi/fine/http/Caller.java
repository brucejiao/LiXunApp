package com.yuzhi.fine.http;

/**
 * Created by Administrator on 2017/6/11.
 * 接口
 */

public class Caller  {

    /**
     * 支付宝帐号：Kx012345@qq.com
      合作伙伴身份（PID）：2088721000412067
       MD5密钥：fihrw34i0zudeszpec4zckry79d85f1g

     APPID_ALIPAY
     2017051807278319

     支付宝参数  RSA_PRIMARY_KEY_ALIPAY
     MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDpOjRuxMDrhP8s9OMu0eZIjWVTuzkRjD+IRXbKbjZ6Fznh2NLCLo8NgR9tJNv6IrduZj+WdwktCfOd3EZL8xr27iFCosEGC2DP0UPHmWmJHlauwccIfkasrUKvH+c7AAk8mc0h4fv4dN9qMfiOWR/xZfBXGxQ6c5NSFCTtcY13KM//m8jYgkOncYKjD3B1ckr5ptcD4Xy8y0t4X9L2CNOFqbvPaYCg1oyEJmwM10VWlU4tnNawZksvr4cliKCe/7+5H+sS64grRGk6BKjiX5rB8In8PdfdguZY/JV35SkLQaJV4GMbei1Qe+25xGqtsn/oSFdmH64UeCNmTnm3BXN5AgMBAAECggEARSngoLSybWW8cq2QrbLLctqToPgVnXGU6amH59I1T5GRtbsiDTnXbG7NTgWvQoeHFDM4Sju0uemImFjZc+59IZtOu78eNNes9xQc4JVLKcGYfSy0BH3GIbEj5GE2plWFZ7ZIkfK9UVn/YzO8fSJLCcBsPLioEAl0N9QHe27AQASGdVLViwuPO0alyieDgwQikOnOwmRTqakifovC6wyDPLboksIsSseIZL+QZeVNrimikV0n1WklvM8C+FuDwUTjZ6NIDVbH+fL4CCss4pIXGKhKAj14livORDz86HVcqI3ghnKY7AJoL5Pt2JaWRbHMB3TpmxLji8jX5nH6lMohjQKBgQD03q9HEwauH3uL1H3DEm2tcWIDoUNFEVArifAdh4hZMa2IIgYLYPjHL+zzB+/Qi3ZmKYlRZsJoj4V7dktiXQPZaz2zxMP0C/GxOrJGA51lbJjr/Wmn3eAfpvOq/HWiPyDPlOWdoor8BdEaJRo9vtKXane6zQNbbRnCJI27jYqE8wKBgQDz1Awsp6Il/efNns2P5o80ZFE2OeOD5aN0qJuNdO/2fEN47W9F0TxsX8ArN/aR+gkfiVDY77Alg6MCEGPtnvUba6FjEWuRounKOcpD0EvE4uk54WIDj4J47T14gtx1WG41V8JWUXClPvgMixU6JBosDEJF5kDQi0EW22F+s6ww4wKBgQCp4JtE+aQWxGyXk3E0FeLVAuX2krfygJJXwjg8pDwpdNdorAH5furYdR0zdXwf98DKG2LSgDG7DGaUQnsF4HW9LjL+NjGja23fgFMRU4ysQmzMu1/DP6AvFUnSg5awWo9Os4OgmpVFRlvMgZT05R+AQDKT+4qqsMO/9lAFn6pLlQKBgHmk+M3uc/7wRY1YBMYeCKPiyIF9L/zFvF6fH7va8zzNkfvquPDkCnkm7ACj0ufRDmwlXahdLEwK+HA3LSOHglFDyShbsIbf+DNj0X0zlhmL+z9dKkEMf9NEyL4uyz3f+Fu0hMf7qW9Hkwju+pAfIs+G7ilhTkS8tKRqnqFPkkBxAoGBAMytyhUGyUIGZnFUun/BWX+hLqvQAbJa/QJXP5WnjKTGnV7xNOrG4k1HNtIks9AD5JBNfOhW5tn8zhSKl1A/7xVRnrE1AgLfaAmZy1wcE8xI+DF+1tTqQ+dJeiOOqViG2wa2+MAp6x9AOiEBZkgeJZCJWaq3itr11XeNZ31c+tcM


     微信支付回调地址
     http://user.lixun110.com/payment/WxPay/ResultNotifyPage.aspx
     string APPID = "wx7074ea72756fdfbc";
     string MCHID = "1480542612";
     string KEY = "zhaomandewangzhanlixun110henniub";
     string APPSECRET = "db305d6d99746433564ec3c13330bc44";


     Picasso.with(mContext).load("http://pic.nipic.com/2008-07-11/20087119630716_2.jpg").resize(DeviceUtil.dp2px(mContext,73), DeviceUtil.dp2px(mContext,73)).placeholder(R.drawable.default_image).into(mIssueImgOne);

     */

    public static String HTTP = "http://api.lixun110.com/";

    public static String APPID ="66666666";//android 66666666 ios 99999999

    public static String SIGN ="======";

    //注册
    public static String REGISTER =HTTP+"user/memberuser/memberuserregiest.html?appid="+APPID ;// +"&sign={SIGN}";

    //发送验证码
    public static String SEND_MESSAGE =HTTP+"common/messages/sendsmscode.html?appid="+APPID ;// +"&sign={SIGN}";

    //登录
    public static String LOGIN =HTTP+"user/memberuser/memberuserlogin.html?appid="+APPID ;// +"&sign={SIGN}";

    //用户找回密码
    public static String FORGET_PWD =HTTP+"user/memberuser/memberuserretrievepassword.html?appid="+APPID ;// +"&sign={SIGN}";

    //获取发布类别列表（二级）
    public static String ISSUE_TYPE_SECOND_LIST =HTTP+"publish/publish/getcategorytwolist.html?appid="+APPID ;// +"&sign={SIGN}";

    //解析经纬度为具体地点
//    public static String BAIDU_MAP_LOCATION ="http://api.map.baidu.com/geocoder";//output=json &location =32.170222,118.718123
  public static String GOOGLE_MAP_LOCATION ="http://maps.google.cn/maps/api/geocode/json";//?latlng=32,118&sensor=true&language=zh-CN";

    //图片上传
    public static String UPLOAD_IMAGE =HTTP+"common/filespace/uploadimg.html?appid="+APPID ;// +"&sign={SIGN}";

    //添加发布信息(草稿箱)
    public static String ADD_ISSUE_DRAFT_INFO =HTTP+"publish/publish/addpublishinfo.html?appid="+APPID ;// +"&sign={SIGN}";

    //现在发布信息
    public static String ADD_ISSUE_INFO =HTTP+"publish/publish/addsubmitpublishinfo.html?appid="+APPID ;// +"&sign={SIGN}";

    //发现列表
    public static String FIND_LIST_INFOS =HTTP+"publish/publish/getfindpublishcomplexlist.html?appid="+APPID ;// +"&sign={SIGN}";

    // 获取发布列表（我的草稿箱）
    public static String MINE_DRAFT_INFOS =HTTP+"publish/publish/getuserdraftpublishlist.html?appid="+APPID ;// +"&sign={SIGN}";

    //发现列表(首页)
    public static String MAIN_FIND_LIST_INFOS =HTTP+"publish/publish/getindexpublishcomplexlist.html?appid="+APPID ;// +"&sign={SIGN}";

    //获取发布信息详情（包含图片列表）
    public static String DETAILS_ISSUE_INFOS =HTTP+"publish/publish/getpublishdetailcomplex.html?appid="+APPID ;// +"&sign={SIGN}";


    //获取评论发布列表（某条发布信息的评论）
    public static String DETAILS_COMMENT_LIST =HTTP+"publish/publish/getpublishcommentpublishlist.html?appid="+APPID ;// +"&sign={SIGN}";

    //添加评论发布信息
    public static String DETAILS_ADD_COMMENT =HTTP+"publish/publish/addcommentpublishinfo.html?appid="+APPID ;// +"&sign={SIGN}";

    //添加关注
    public static String ADD_FOCUS =HTTP+"publish/publish/addfollowpublishinfo.html?appid="+APPID ;// +"&sign={SIGN}";

    //取消关注
    public static String CANCLE_FOCUS =HTTP+"publish/publish/cancelfollowpublishinfo.html?appid="+APPID ;// +"&sign={SIGN}";

    //关注初始化状态
    public static String FOCUS_STATE =HTTP+"publish/publish/isexistsfollowpublishinfo.html?appid="+APPID ;// +"&sign={SIGN}";

    // 添加认领信息
    public static String ADD_RL_INFOS =HTTP+"publish/publish/addclaiminfo.html?appid="+APPID ;// +"&sign={SIGN}";

    // 添加线索信息
    public static String ADD_XS_INFOS =HTTP+"publish/publish/addclueinfo.html?appid="+APPID ;// +"&sign={SIGN}";

    // 用户签到加积分
    public static String USER_ADD_POINT =HTTP+"user/memberuser/usersignaddpoint.html?appid="+APPID ;// +"&sign={SIGN}";

    // 判断用户今日是否已签到
    public static String IS_ADD_POINT =HTTP+"user/memberuser/userissign.html?appid="+APPID ;// +"&sign={SIGN}";

    // 获取首页推荐广告
    public static String GET_MAIN_SECOND_AD =HTTP+"common/advert/getindexrecommendadvertlist.html?appid="+APPID ;// +"&sign={SIGN}";

    // 获取首页广告幻灯片
    public static String GET_MAIN_FIRST_AD =HTTP+"common/advert/getindexadvertlist.html?appid="+APPID ;// +"&sign={SIGN}";

    //获取欢迎界面广告
    public static String GET_WELCOME_AD =HTTP+"common/advert/getwelcomeadvert.html?appid="+APPID ;// +"&sign={SIGN}";

    //获取首次运行广告列表
    public static String GET_FIRST_RUN_AD =HTTP+"common/advert/getfirstrunadvertlist.html?appid="+APPID ;// +"&sign={SIGN}";

    // 获取用户手机号
    public static String GET_USER_MOBILE =HTTP+"user/memberuser/getmemberusermobile.html?appid="+APPID ;// +"&sign={SIGN}";

    //获取用户信息
    public static String GET_USER_INFO =HTTP+"user/memberuser/getmemberuserinfo.html?appid="+APPID ;// +"&sign={SIGN}";

    //修改用户密码
    public static String MODIFY_USER_PASSWORD =HTTP+"user/memberuser/modifyuserpassword.html?appid="+APPID ;// +"&sign={SIGN}";

    //修改用户头像
    public static String MODIFY_USER_HEADER =HTTP+"user/memberuser/modifyuserheadportrait.html?appid="+APPID ;// +"&sign={SIGN}";

    //修改用户签名
    public static String MODIFY_USER_MOTOO =HTTP+"user/memberuser/modifyusermotto.html?appid="+APPID ;// +"&sign={SIGN}";

    //修改用户信息
    public static String MODIFY_USER_INFO =HTTP+"user/memberuser/modifyuserinfo.html?appid="+APPID ;// +"&sign={SIGN}";

    //用户提交认证
    public static String EDIT_USER_CERTIFICATE =HTTP+"user/memberuser/editmemberusercertificate.html?appid="+APPID ;// +"&sign={SIGN}";

    //提交投诉建议
    public static String SUBMIT_ADD_ADVICE =HTTP+"user/memberuser/addadvice.html?appid="+APPID ;// +"&sign={SIGN}";

    //余额充值
    public static String USER_BALANCE_CHARGE =HTTP+"user/memberuser/userbalancerecharge.html?appid="+APPID ;// +"&sign={SIGN}";

    //余额提现
    public static String USER_BALANCE_RECASH =HTTP+"user/memberuser/userbalancerecash.html?appid="+APPID ;// +"&sign={SIGN}";

    //获取用户收支明细
    public static String GET_USER_MONEY_LIST =HTTP+"user/memberuser/getusermoneylist.html?appid="+APPID ;// +"&sign={SIGN}";

    //获取用户提现记录
    public static String GET_USER_BALANCE_RECASH_LIST =HTTP+"user/memberuser/getuserbalancerecashlist.html?appid="+APPID ;// +"&sign={SIGN}";

    //获取热门关键词列表
    public static String GET_HOT_KEYWORD_LIST =HTTP+"publish/publish/gethotkeywordslist.html?appid="+APPID ;// +"&sign={SIGN}";

    //修改发布信息（草稿箱）
    public static String MODIFY_PUBLISH_INFO =HTTP+"publish/publish/modifypublishinfo.html?appid="+APPID ;// +"&sign={SIGN}";

    //修改发布信息（现在发布）
    public static String MODIFY_SUBMIT_PUBLISH_INFO =HTTP+"publish/publish/modifysubmitpublishinfo.html?appid="+APPID ;// +"&sign={SIGN}";

    //删除发布信息
    public static String DELETE_PUBLISH_INFO =HTTP+"publish/publish/deletepublishinfo.html?appid="+APPID ;// +"&sign={SIGN}";

    //结束发布信息
    public static String END_PUBLISH_INFO =HTTP+"publish/publish/endpublishinfo.html?appid="+APPID ;// +"&sign={SIGN}";

    //完成发布信息
    public static String FINISH_PUBLISH_INFO =HTTP+"publish/publish/finishpublishinfo.html?appid="+APPID ;// +"&sign={SIGN}";

  //获取发布列表（我的寻找）
  public static String GET_MINE_FIND_LIST =HTTP+"publish/publish/getusersearchpublishlist.html?appid="+APPID ;// +"&sign={SIGN}";

  //获取发布列表（我的招领）
  public static String GET_MINE_ZL =HTTP+"publish/publish/getuserrecruitpublishlist.html?appid="+APPID ;// +"&sign={SIGN}";

  //获取认领列表（我的认领）
  public static String GET_MINE_RL =HTTP+"publish/publish/getuserclaimlist.html?appid="+APPID ;// +"&sign={SIGN}";

  //获取认领列表（某条发布信息的认领）
  public static String GET_MINE_RL_LIST =HTTP+"publish/publish/getpublishclaimlist.html?appid="+APPID ;// +"&sign={SIGN}";

  //获取认领信息详情（包含图片列表）
  public static String GET_MINE_RL_DETAIL =HTTP+"publish/publish/getclaimdetailcomplex.html?appid="+APPID ;// +"&sign={SIGN}";

  //获取发布列表（我的推广）
  public static String GET_MINE_PUBLISH =HTTP+"publish/publish/getuserpushpublishlist.html?appid="+APPID ;// +"&sign={SIGN}";

  //获取发布列表（我的网络社交）
  public static String GET_MINE_NETCHAT =HTTP+"publish/publish/getusersocialpublishlist.html?appid="+APPID ;// +"&sign={SIGN}";

  //添加线索信息
  public static String GET_MINE_ADD_CLUE_INFO=HTTP+"publish/publish/addclueinfo.html?appid="+APPID ;// +"&sign={SIGN}";

  //采纳线索/认领信息
  public static String GET_MINE_ADO_CLUE_INFO=HTTP+"publish/publish/adoptclueinfo.html?appid="+APPID ;// +"&sign={SIGN}";

  //获取线索列表（我提供线索）
  public static String GET_MINE_CLUE_LIST=HTTP+"publish/publish/getusercluelist.html?appid="+APPID ;// +"&sign={SIGN}";

  //获取线索列表（某条发布信息的线索）
  public static String GET_MINE_PUBLIST_CLUE_LIST=HTTP+"publish/publish/getpublishcluelist.html?appid="+APPID ;// +"&sign={SIGN}";

  //获取线索信息详情（包含图片列表）
  public static String GET_MINE_CLUE_DETAIL=HTTP+"publish/publish/getcluedetailcomplex.html?appid="+APPID ;// +"&sign={SIGN}";

  //获取发布类别列表（一二级）
  public static String GET_FRIST_SECOND_MENU=HTTP+"publish/publish/getcategoryiteratelist.html?appid="+APPID ;// +"&sign={SIGN}";


}
