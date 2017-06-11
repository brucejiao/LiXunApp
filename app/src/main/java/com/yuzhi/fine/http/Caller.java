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
}
