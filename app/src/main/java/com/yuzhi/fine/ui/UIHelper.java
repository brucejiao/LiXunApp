package com.yuzhi.fine.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.yuzhi.fine.activity.HouseDetailActivity;
import com.yuzhi.fine.activity.LoginActivity;
import com.yuzhi.fine.activity.MainActivity;
import com.yuzhi.fine.activity.coreActivity.IssueActivity;
import com.yuzhi.fine.activity.loginActivity.ForgetPassActivity;
import com.yuzhi.fine.activity.loginActivity.LXLoginActivity;
import com.yuzhi.fine.activity.loginActivity.RegisteActivity;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 */
public class UIHelper {

	public final static String TAG = "UIHelper";

	public final static int RESULT_OK = 0x00;
	public final static int REQUEST_CODE = 0x01;

	public static void ToastMessage(Context cont, String msg) {
        if(cont == null || msg == null) {
            return;
        }
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, int msg) {
        if(cont == null || msg <= 0) {
            return;
        }
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
        if(cont == null || msg == null) {
            return;
        }
		Toast.makeText(cont, msg, time).show();
	}

    public static void showLogin(Activity context){
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void showHouseDetailActivity(Activity context){
        Intent intent = new Intent(context, HouseDetailActivity.class);
        context.startActivity(intent);
    }
//===============================================================================================================
    //显示主界面
    public static void showHome(Activity context){
        Intent intent = new Intent(context, MainActivity.class);
        context.finish();
        context.startActivity(intent);
    }

    //显示登录界面
    public static void showLXLogin(Activity context){
        Intent intent = new Intent(context, LXLoginActivity.class);
        context.startActivity(intent);
    }

    //显示登录界面
    public static void showRegister(Activity context){
        Intent intent = new Intent(context, RegisteActivity.class);
        context.startActivity(intent);
    }

    //显示忘记密码
    public static void showForgetPwd(Activity context){
        Intent intent = new Intent(context, ForgetPassActivity.class);
        context.startActivity(intent);
    }

    //显示发布界面
    //将一级菜单的Id传入issueActivity获取二级菜单
    public static void showIssue(Context context,int index){
        Intent intent = new Intent(context, IssueActivity.class);
        switch (index){
            case 0://曝光
                intent.putExtra("parentid","80");
                context.startActivity(intent);
                break;
            case 1://求助
                intent.putExtra("parentid","81");
                context.startActivity(intent);
                break;
            case 2://圈子
                intent.putExtra("parentid","549");
                context.startActivity(intent);
                break;
            case 3: //寻人
                intent.putExtra("parentid","83");
                context.startActivity(intent);
                break;
            case 4: //寻物
                intent.putExtra("parentid","82");
                context.startActivity(intent);
                break;
            case 5:  //招领认领
                intent.putExtra("parentid","394");
                context.startActivity(intent);
                break;
            default:
                break;
        }

    }

}
