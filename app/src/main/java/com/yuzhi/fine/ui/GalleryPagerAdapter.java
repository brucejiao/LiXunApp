package com.yuzhi.fine.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yuzhi.fine.R;
import com.yuzhi.fine.activity.ImageGalleryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 * 轮播图适配器
 */

public class GalleryPagerAdapter extends PagerAdapter {
    private Context context;
//    private int[] imageViewIds;
    private List<String> imageMainList;
    private List<String> imageList;


    public GalleryPagerAdapter(List<String> imageMainList/*int[] imageViewIds*/ ,List<String> imageList,Context context){
        super();
        this.context = context;
        this.imageMainList = imageMainList ;
        this.imageList = imageList ;
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
//        item.setImageResource(imageViewIds[position]);
        Picasso.with(context).load(imageMainList.get(position))
//                .resize(DeviceUtil.dp2px(context,73), DeviceUtil.dp2px(context,73))
                .placeholder(R.drawable.default_image).into(item);
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