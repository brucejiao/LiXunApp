package com.yuzhi.fine.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yuzhi.fine.R;
import com.yuzhi.fine.model.MainAd;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.DeviceUtil;

import java.util.ArrayList;

/**
 * Created by JiaoJianJun on 2017/6/1.
 */

public class HorizontalListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MainAd> adBeanList;
    public HorizontalListViewAdapter(Context con,ArrayList<MainAd> adBeanList){
        this.context = con ;
        this.adBeanList = adBeanList;
        mInflater= LayoutInflater.from(con);
    }
    @Override
    public int getCount() {
        return adBeanList.size();
    }
    private LayoutInflater mInflater;
    @Override
    public Object getItem(int position) {
        return position;
    }
    private ViewHolder vh 	 =new ViewHolder();
    private static class ViewHolder {
        private ImageView im;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = mInflater.inflate(R.layout.horizontallistview_item, null);
            vh.im=(ImageView)convertView.findViewById(R.id.iv_pic);
            convertView.setTag(vh);
        }else{
            vh=(ViewHolder)convertView.getTag();
        }

        if(!CommUtil.isNullOrBlank(adBeanList) && adBeanList.size()!=0){
            for (int i=0; i<adBeanList.size(); i++){
                Picasso.with(context).load(adBeanList.get(i).getImgpath())
                        .resize(DeviceUtil.dp2px(context, 140), DeviceUtil.dp2px(context, 75))
                        .placeholder(R.drawable.default_image).into(vh.im);
            }
        }
        return convertView;
    }
}