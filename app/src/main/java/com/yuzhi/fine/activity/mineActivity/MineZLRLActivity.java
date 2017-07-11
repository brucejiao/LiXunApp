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
import com.yuzhi.fine.fragment.mineFragment.mineZLRLFragment.MineRLFragment;
import com.yuzhi.fine.fragment.mineFragment.mineZLRLFragment.MineZLFragment;
import com.yuzhi.fine.ui.Find_tab_Adapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的--招领认领
 */
public class MineZLRLActivity extends AppCompatActivity {
    private MineZLRLActivity mContext ;

    @Bind(R.id.btnBack)
    LinearLayout mBtnBack;
    @Bind(R.id.textHeadTitle)
    TextView mTextHeadTitle;

    //TabLayout
    @Bind(R.id.tab_FindFragment_title)
    TabLayout tab_FindFragment_title;                            //定义TabLayout
    @Bind(R.id.vp_FindFragment_pager)
    ViewPager vp_FindFragment_pager;                     //定义viewPager
    private FragmentPagerAdapter fAdapter;                               //定义adapter
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表
    private MineZLFragment mineZLFragment;            //我的招领
    private MineRLFragment mineRLFragment;              //我的认领
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_zlrl);
        ButterKnife.bind(this);
        mContext = this;
        initUI();
    }

    public void initUI(){
        mBtnBack.setVisibility(View.VISIBLE);
        mTextHeadTitle.setText("招领认领");
        findServersViewPager();
    }

    //返回
    @OnClick(R.id.btnBack)
    public void onBack(View view){
        finish();
    }
    /**
     * 招领认领
     */
    public void findServersViewPager() {

        //初始化各fragment
        mineZLFragment = new MineZLFragment();//我的招领
        mineRLFragment = new MineRLFragment(); //我的认领


        //将fragment装进列表中
        list_fragment = new ArrayList<Fragment>();
        list_fragment.add(mineZLFragment);
        list_fragment.add(mineRLFragment);

        //将名称加载tab名字列表，正常情况下，我们应该在values/arrays.xml中进行定义然后调用
        list_title = new ArrayList<String>();
        list_title.add("我发出的招领");
        list_title.add("我的认领");

        //设置TabLayout的模式
        tab_FindFragment_title.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(0)));
        tab_FindFragment_title.addTab(tab_FindFragment_title.newTab().setText(list_title.get(1)));
        //   getActivity().getSupportFragmentManager()会导致一个问题：数据丢失  看网上讲这边要用getChildFragmentManager()
        fAdapter = new Find_tab_Adapter(getSupportFragmentManager(), list_fragment, list_title);// getChildFragmentManager  getActivity().getSupportFragmentManager()会导致一个问题：数据丢失

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
//                vp_FindFragment_pager.resetHeight(position);

                switch (position){
                    case 0:
//                        CommUtil.showToast("我发出的招领",mContext);
                        break;
                    case 1:
//                        CommUtil.showToast("我的认领",mContext);
                        break;

                    default: break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

}
