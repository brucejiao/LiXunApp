
package com.yuzhi.fine.activity;

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

import com.yuzhi.fine.R;
import com.yuzhi.fine.fragment.findFragment.FindMainFragment;
import com.yuzhi.fine.fragment.lxMainFragment.LXMainFragment;
import com.yuzhi.fine.fragment.mineFragment.MineFragment;
import com.yuzhi.fine.fragment.msgFragment.MessageFragment;
import com.yuzhi.fine.ui.IssuePopWin;
import com.yuzhi.fine.ui.UIHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends BaseFragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String CURR_INDEX = "currIndex";
    private static int currIndex = 0;

    private RadioGroup group;

    private ArrayList<String> fragmentTags;
    private FragmentManager fragmentManager;

    private ImageView mIssue;//发布

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
    }

    /**
     * 发布事件监听
     */
    private void issueListener(){

        mIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        if (currIndex == 3) {
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
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
