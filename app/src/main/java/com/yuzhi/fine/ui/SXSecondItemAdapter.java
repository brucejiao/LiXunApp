package com.yuzhi.fine.ui;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.model.IssueModel.SecondMenu;

import java.util.List;

/**
 * Created by JiaoJianJun on 2017/6/19.
 */

public class SXSecondItemAdapter extends BaseAdapter {
    private Context activity;
    private List<SecondMenu> arrayBean;

    public SXSecondItemAdapter(Context activity,List<SecondMenu> arrayBean) {
        super();
        this.activity = activity;
        this.arrayBean = arrayBean;
    }

    private class ViewHolder {
        public TextView item_second_name;// 名称
        public TextView item_second_id;// id

    }

    @Override
    public int getCount() {
        return arrayBean.size();
    }

    @Override
    public SecondMenu getItem(int position) {

        return arrayBean.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (convertView == null) {
            view = ((Activity) activity).getLayoutInflater().inflate(R.layout.item_sx_secondlayout, parent, false);
            holder = new ViewHolder();
            holder.item_second_id = (TextView) view.findViewById(R.id.item_second_id);
            holder.item_second_name = (TextView) view.findViewById(R.id.item_second_name);
            view.setTag(holder); // 给View添加一个格外的数据
        } else {
            holder = (ViewHolder) view.getTag(); // 把数据取出来
        }
        SecondMenu bean = getItem(position);
        holder.item_second_id.setText(bean.getCategoryID());//
        holder.item_second_name.setText(bean.getCategoryTitle());
        return view;
    }
}
