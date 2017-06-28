package com.yuzhi.fine.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.yuzhi.fine.R;

/**
 * Created by JiaoJianJun on 2017/6/28.
 */

public class AddContentDialog  extends Dialog {

    private Window window = null;
    public TextView txt_Title = null;
    public EditText txt_content = null;
    public Button dialog_button_details = null;
    public Button dialog_button_cancel = null;



    public AddContentDialog(Context context){
        super(context);
    }


    public AddContentDialog(Context context, int theme)
    {
        super(context, theme);
    }

    public void setDialog(int layoutResID) {
        setContentView(layoutResID);
        txt_Title = (TextView) findViewById(R.id.txt_Title);
        txt_content = (EditText) findViewById(R.id.txt_content);
        dialog_button_details = (Button) findViewById(R.id.dialog_button_details);
        dialog_button_cancel = (Button) findViewById(R.id.dialog_button_cancel);

        window = getWindow(); // 得到对话框
        window.setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画

        // 设置触摸对话框意外的地方不能取消对话框
        setCanceledOnTouchOutside(false);
        // 阻止返回键响应
        setCancelable(false);
    }

    // 如果自定义的Dialog声明为局部变量，就可以调用下面两个方法进行显示和消失，全局变量则无所谓
    /**
     * 显示dialog
     */
    public void showDialog() {
        show();
    }

    /**
     * 关闭dialog
     */
    public void Closedialog() {
        dismiss();
    }
}
