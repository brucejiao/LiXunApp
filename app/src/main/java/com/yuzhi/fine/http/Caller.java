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

}
