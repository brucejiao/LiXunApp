package com.yuzhi.fine.activity.mineActivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.fragment.mineFragment.mineFindFragment.MineFindAllFragment;
import com.yuzhi.fine.fragment.mineFragment.mineFindFragment.MineWTZRFragment;
import com.yuzhi.fine.fragment.mineFragment.mineFindFragment.MineWTZWFragment;
import com.yuzhi.fine.ui.Find_tab_Adapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的--我的寻找
 */
public class MineFindActivity extends AppCompatActivity {
    private MineFindActivity mContext ;

    @Bind(R.id.btnBack)
    LinearLayout mBtnBack;
    @Bind(R.id.textHeadTitle)
    TextView mTextHeadTitle;

    //TabLayout
    @Bind(R.id.tab_FindFragment_title)
    TabLayout tab_FindFragment_title;                            //定义TabLayout
    @Bind(R.id.vp_FindFragment_pager)
    ViewPager vp_FindFragment_pager;
    private FragmentPagerAdapter fAdapter;                               //定义adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_find);
        ButterKnife.bind(this);
        mContext = this;
        initUI();
    }

    public void initUI(){
        mBtnBack.setVisibility(View.VISIBLE);
        mTextHeadTitle.setText("我的寻找");
        findServersViewPager();
    }

    //返回
    @OnClick(R.id.btnBack)
    public void onBack(View view){
        finish();
    }

    /**
     * 我的寻找
     */
    public void findServersViewPager() {
        try {
            MineFindAllFragment  mineFindAllFragment = new MineFindAllFragment();//我的寻找--全部
            MineWTZRFragment     mineWTZRFragment = new MineWTZRFragment(); //委托找人
            MineWTZWFragment     mineWTZWFragment = new MineWTZWFragment(); //委托找物

            //将fragment装进列表中
            List<Fragment>    list_fragment = new ArrayList<Fragment>();
            list_fragment.add(mineFindAllFragment);
            list_fragment.add(mineWTZRFragment);
            list_fragment.add(mineWTZWFragment);

            //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
            List<String>   list_title = new ArrayList<String>();
            list_title.add("全部");
            list_title.add("委托找人");
            list_title.add("委托找物");

            //设置TabLayout的模式
            tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);
            //为TabLayout添加tab名称
            tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)));
            tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));
            tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(2)));
            //   getActivity().getSupportFragmentManager()会导致一个问题：数据丢失  看网上讲这边要用getChildFragmentManager()
            fAdapter = new Find_tab_Adapter( getSupportFragmentManager(), list_fragment, list_title);// getChildFragmentManager  getActivity().getSupportFragmentManager()会导致一个问题：数据丢失

            //viewpager加载adapter
            vp_FindFragment_pager.setAdapter(fAdapter);
//      tab_FindFragment_title.setViewPager(vp_FindFragment_pager);

            //TabLayout加载viewpager
            tab_FindFragment_title.setupWithViewPager(vp_FindFragment_pager);
            tab_FindFragment_title.setTabsFromPagerAdapter(fAdapter);
            vp_FindFragment_pager.setCurrentItem(0);
            //切换viewpaper事件
            vp_FindFragment_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    switch (position){
                        case 0:
//                        CommUtil.showToast("全部",mContext);
                            break;
                        case 1:
//                        CommUtil.showToast("找人",mContext);
                            break;
                        case 2:
//                        CommUtil.showToast("找物",mContext);
                            break;
                        default: break;
                    }

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }catch (Exception e)
        {
            e.printStackTrace();
            e.printStackTrace();
            e.printStackTrace();
            e.printStackTrace();
        }



    }

}
