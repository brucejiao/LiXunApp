package com.yuzhi.fine.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.utils.CommUtil;

/**
 * Created by JiaoJianJun on 2017/6/8.
 */

public class IssuePopWin extends PopupWindow {

    private final Context mContext;

    private View view;

    private ImageView issue_baoguang;
    private ImageView issue_qiuzhu;
    private ImageView issue_quanzi;
    private ImageView issue_xunren;
    private ImageView issue_xunwu;
    private ImageView issue_zlrl;


    private TextView btn_cancel;


    public IssuePopWin(Context context) {
       this.mContext = context ;
        this.view = LayoutInflater.from(context).inflate(R.layout.popupwidwow_issue, null);

        issue_baoguang = (ImageView) view.findViewById(R.id.issue_baoguang);//曝光
        issue_qiuzhu = (ImageView) view.findViewById(R.id.issue_qiuzhu);//求助
        issue_quanzi = (ImageView) view.findViewById(R.id.issue_quanzi);//圈子
        issue_xunren = (ImageView) view.findViewById(R.id.issue_xunren);//寻人
        issue_xunwu = (ImageView) view.findViewById(R.id.issue_xunwu);//寻物
        issue_zlrl = (ImageView) view.findViewById(R.id.issue_zlrl);//招领认领
        btn_cancel = (TextView) view.findViewById(R.id.issue_cancel);   // 取消按钮

        // 设置按钮监听
        //曝光
        issue_baoguang.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                CommUtil.showAlert("曝光",mContext);
            }
        });
        //求助
        issue_qiuzhu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                CommUtil.showAlert("求助",mContext);
            }
        });
        //圈子
        issue_quanzi.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                CommUtil.showAlert("圈子",mContext);
            }
        });
        //寻人
        issue_xunren.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                CommUtil.showAlert("寻人",mContext);
            }
        });
        //寻物
        issue_xunwu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                CommUtil.showAlert("寻物",mContext);
            }
        });
        //招领认领
        issue_zlrl.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                CommUtil.showAlert("招领认领",mContext);
            }
        });
//============================================================================================================================================
        // 取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // 销毁弹出框
                dismiss();
            }
        });


        // 设置外部可点击
        this.setOutsideTouchable(true);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        this.view.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = view.findViewById(R.id.pop_layout).getTop();

                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });


    /* 设置弹出窗口特征 */
        // 设置视图
        this.setContentView(this.view);
        // 设置弹出窗体的宽和高
        this.setHeight(RelativeLayout.LayoutParams.MATCH_PARENT);//WRAP_CONTENT
        this.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);

        // 设置弹出窗体可点击
        this.setFocusable(true);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置弹出窗体的背景
        this.setBackgroundDrawable(dw);

        // 设置弹出窗体显示时的动画，从底部向上弹出
        this.setAnimationStyle(R.style.take_photo_anim);

    }
}
