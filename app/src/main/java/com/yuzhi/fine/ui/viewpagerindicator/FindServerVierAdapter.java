package com.yuzhi.fine.ui.viewpagerindicator;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 *  悬赏/普通找寻适配器
 * Created by JiaoJianJun on 2017/5/31.
 */

public class FindServerVierAdapter extends PagerAdapter{
    private Context context;
    private List<String> mTitle;
    private List<String> mDatas;
   public  FindServerVierAdapter(Context context, List<String> mTitle,List<String> mDatas){
       super();
       this.context = context;
       this.mTitle = mTitle;
       this.mDatas = mDatas;
    }

        @Override
        public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
        @Override
        public int getCount() {
        return mDatas.size();
    }
        @Override
        public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
        @Override
        public Object instantiateItem(View container, int position) {
        TextView tv = new TextView(context);
        tv.setTextSize(24.f);
        tv.setPadding(15,10,15,10);
        tv.setText(mDatas.get(position));
        ((ViewPager) container).addView(tv);
        return tv;
    }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

}
