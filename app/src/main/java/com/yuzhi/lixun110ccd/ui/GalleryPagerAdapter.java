package com.yuzhi.lixun110ccd.ui;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.utils.CommUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 * 轮播图适配器
 */

public class GalleryPagerAdapter extends PagerAdapter {
    private Context context;
//    private int[] imageViewIds;
    private List<String> imageMainList;//图片列表
    private List<String> advList;//广告url跳转列表


    public GalleryPagerAdapter(List<String> imageMainList ,List<String> advList,Context context){
        super();
        this.context = context;
        this.imageMainList = imageMainList ;
        this.advList = advList ;
    }

    @Override
    public int getCount() {
        return imageMainList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView item = new ImageView(context);
        if(!CommUtil.isNullOrBlank(imageMainList.get(position).toString())){
            Picasso.with(context).load(imageMainList.get(position))
                    .placeholder(R.drawable.default_image).into(item);
        }else{
            item.setBackgroundResource(R.drawable.default_image);
        }

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        item.setLayoutParams(params);
        item.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(item);

        final int pos = position;
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent = new Intent(context, ImageGalleryActivity.class);
                intent.putStringArrayListExtra("images", (ArrayList<String>) advList);
                intent.putExtra("position", pos);
                context.startActivity(intent);*/

              /*  Intent intent = new Intent(context, LinkUrlActivity.class);
                intent.putStringArrayListExtra("advList", (ArrayList<String>) advList);
                intent.putExtra("position", pos);
                context.startActivity(intent);*/
              UIHelper.toH5Page(context,(ArrayList<String>) advList,pos,"",0);
            }
        });

        return item;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }
}