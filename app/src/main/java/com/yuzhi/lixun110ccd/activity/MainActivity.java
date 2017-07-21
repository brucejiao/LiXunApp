
package com.yuzhi.lixun110ccd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.fragment.findFragment.FindMainFragment;
import com.yuzhi.lixun110ccd.fragment.lxMainFragment.LXMainFragment;
import com.yuzhi.lixun110ccd.fragment.mineFragment.MineFragment;
import com.yuzhi.lixun110ccd.fragment.msgFragment.MessageFragment;
import com.yuzhi.lixun110ccd.ui.IssuePopWin;
import com.yuzhi.lixun110ccd.ui.UIHelper;
import com.yuzhi.lixun110ccd.utils.SharePreferenceUtil1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import static com.yuzhi.lixun110ccd.utils.Constant.SHARE_LOGIN_ISLOGIN;

public class MainActivity extends BaseFragmentActivity {
    private MainActivity mContext ;
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String CURR_INDEX = "currIndex";
    private static int currIndex = 0;

    private RadioGroup group;

    private ArrayList<String> fragmentTags;
    private FragmentManager fragmentManager;


    private ImageView mIssue;//发布
    SharePreferenceUtil1 share ;

    private TextView textUnreadLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext =this;
        share = new SharePreferenceUtil1(mContext, "lx_data", 0);

        fragmentManager = getSupportFragmentManager();
        initData(savedInstanceState);
        initView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURR_INDEX, currIndex);
    }

    private void initData(Bundle savedInstanceState) {
        fragmentTags = new ArrayList<>(Arrays.asList("HomeFragment", "ImFragment", "InterestFragment", "MemberFragment"));
        currIndex = 0;
        if(savedInstanceState != null) {
            currIndex = savedInstanceState.getInt(CURR_INDEX);
            hideSavedFragment();
        }
    }

    private void hideSavedFragment() {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if(fragment != null) {
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }

    private void initView() {
        mIssue =  (ImageView)findViewById(R.id.foot_bar_issue);//发布
        issueListener();//发布事件监听
        group = (RadioGroup) findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home: currIndex = 0; break;//立寻
                    case R.id.foot_bar_find: currIndex = 1; break;//发现
                    case R.id.foot_bar_message: currIndex = 2; break;//消息
                    case R.id.foot_bar_mine: currIndex = 3; break;//我的
                    default: break;
                }
                showFragment();
            }
        });
        showFragment();
        messageRedPoint();

    }

    private void messageRedPoint(){
        textUnreadLabel = (TextView) findViewById(R.id.textUnreadLabel);//消息小红点
        textUnreadLabel.setText("10");
    }

    /**
     * 发布事件监听
     */
    private void issueListener(){

        mIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否成功登陆过
                boolean isLogin = share.getBoolean(SHARE_LOGIN_ISLOGIN, false);// 是否登陆
                if(!isLogin){
                    UIHelper.showLXLogin(MainActivity.this);
                    return;
                }
                IssuePopWin takePhotoPopWin = new IssuePopWin(MainActivity.this);
                // 设置Popupwindow显示位置（从底部弹出）
                takePhotoPopWin.showAtLocation(findViewById(R.id.foot_bar_issue), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
                WindowManager.LayoutParams params = getWindow().getAttributes();
                //当弹出Popupwindow时，背景变半透明
                params.alpha=1f;
                getWindow().setAttributes(params);
                //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
                takePhotoPopWin.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams  params = getWindow().getAttributes();
                        params.alpha=1f;
                        getWindow().setAttributes(params);
                    }
                });
            }


        });

    }

    private void showFragment() {
        //判断是否成功登陆过
        boolean isLogin = share.getBoolean(SHARE_LOGIN_ISLOGIN, false);// 是否登陆
        if(!isLogin && currIndex == 2){
            UIHelper.showLXLogin(MainActivity.this);
        }else if(!isLogin && currIndex == 3){
            UIHelper.showLXLogin(MainActivity.this);
        }


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if(fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = fragmentManager.findFragmentByTag(fragmentTags.get(i));
            if(f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0:
                return new LXMainFragment();//   MainPagerFragment()  LXMainFragment
            case 1:
                return new FindMainFragment();//FindMainFragment  BufferKnifeFragment
            case 2:
                return new MessageFragment();//MessageFragment  BufferKnifeFragment
            case 3:
                return new MineFragment();//MemberFragment()我的 MineFragment
            default: return null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(true);
            exitBy2Click(); // 调用双击退出函数
            return true;
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
			/*
			 * finish(); System.exit(0);
			 */
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());

        }
    }

}
