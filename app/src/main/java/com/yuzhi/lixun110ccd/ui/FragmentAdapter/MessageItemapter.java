package com.yuzhi.lixun110ccd.ui.FragmentAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.model.UserInfos.MessageList;

import java.util.ArrayList;

/**
 * 消息
 */
public class MessageItemapter extends BaseAdapter {
    private Context activity;
    private ArrayList<MessageList> arrayBean;

    public MessageItemapter(Context activity, ArrayList<MessageList> arrayBean) {
        super();
        this.activity = activity;
        this.arrayBean = arrayBean;
    }

    private class ViewHolder {
        public TextView message_type;// 消息类型
        public TextView message_content;//消息内容
        public TextView message_time;// 消息时间


    }

    @Override
    public int getCount() {
        return arrayBean.size();
    }

    @Override
    public MessageList getItem(int position) {

        return arrayBean.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }
    //判断获取到数据则展示 否则展示缺省页
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (convertView == null) {
            view = ((Activity) activity).getLayoutInflater().inflate(R.layout.item_message_layout, parent, false);
            holder = new ViewHolder();
            holder.message_type = (TextView) view.findViewById(R.id.message_type);
            holder.message_content = (TextView) view.findViewById(R.id.message_content);
            holder.message_time = (TextView) view.findViewById(R.id.message_time);

            view.setTag(holder); // 给View添加一个格外的数据
        } else {
            holder = (ViewHolder) view.getTag(); // 把数据取出来
        }
        MessageList bean = getItem(position);
        holder.message_type.setText(bean.getMessageBody());
        holder.message_content.setText(bean.getMessageSubject());
        holder.message_time.setText(bean.getMessageTime());


        return view;
    }
}
