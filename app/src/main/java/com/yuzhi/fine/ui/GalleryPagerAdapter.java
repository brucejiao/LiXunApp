package com.yuzhi.fine.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuzhi.fine.activity.ImageGalleryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 * 轮播图适配器
 */

public class GalleryPagerAdapter extends PagerAdapter {
    private Context context;
    private int[] imageViewIds;
    private List<String> imageList;


    public GalleryPagerAdapter(int[] imageViewIds ,List<String> imageList,Context context){
        super();
        this.context = context;
        this.imageViewIds = imageViewIds ;
        this.imageList = imageList ;
    }

    @Override
    public int getCount() {
        return imageViewIds.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView item = new ImageView(context);
        item.setImageResource(imageViewIds[position]);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        item.setLayoutParams(params);
        item.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(item);

        final int pos = position;
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ImageGalleryActivity.class);
                intent.putStringArrayListExtra("images", (ArrayList<String>) imageList);
                intent.putExtra("position", pos);
                context.startActivity(intent);
            }
        });

        return item;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }
}