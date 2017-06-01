package com.yuzhi.fine.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.yuzhi.fine.R;

/**
 * Created by JiaoJianJun on 2017/6/1.
 */

public class HorizontalListViewAdapter extends BaseAdapter {

    public HorizontalListViewAdapter(Context con){
        mInflater= LayoutInflater.from(con);
    }
    @Override
    public int getCount() {
        return 5;
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
        return convertView;
    }
}