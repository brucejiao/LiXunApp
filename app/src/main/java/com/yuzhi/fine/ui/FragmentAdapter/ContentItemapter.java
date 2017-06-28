package com.yuzhi.fine.ui.FragmentAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.yuzhi.fine.R;
import com.yuzhi.fine.model.DetailsComment.CommentBean;
import com.yuzhi.fine.utils.DeviceUtil;

import java.util.ArrayList;

import static com.yuzhi.fine.R.id.details_review;

/**
 * 评论列表适配器
 */
public class ContentItemapter extends BaseAdapter {
    private Context activity;
    private ArrayList<CommentBean> arrayBean;

    public ContentItemapter(Context activity, ArrayList<CommentBean> arrayBean) {
        super();
        this.activity = activity;
        this.arrayBean = arrayBean;

    }

    private class ViewHolder {
        public RoundedImageView lx_header_img;
        public TextView username;
        public TextView content;
        public TextView time;


    }

    @Override
    public int getCount() {
        return arrayBean.size();
    }

    @Override
    public CommentBean getItem(int position) {

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
            view = ((Activity) activity).getLayoutInflater().inflate(R.layout.item_details_review, parent, false);
            holder = new ViewHolder();
            holder.lx_header_img = (RoundedImageView) view.findViewById(details_review);
            holder.username = (TextView) view.findViewById(R.id.details_review_username);
            holder.content = (TextView) view.findViewById(R.id.details_review_content);
            holder.time = (TextView) view.findViewById(R.id.details_review_time);
            view.setTag(holder); // 给View添加一个格外的数据
        } else {
            holder = (ViewHolder) view.getTag(); // 把数据取出来
        }
        CommentBean bean = getItem(position);

        //头像
        Picasso.with(activity).load(bean.getImgFilePath())
                .resize(DeviceUtil.dp2px(activity, 25), DeviceUtil.dp2px(activity, 25))
                .placeholder(R.drawable.default_image).into(holder.lx_header_img);
        holder.username.setText(bean.getUserName());
        holder.content.setText(bean.getContent());
        holder.time.setText(bean.getCreateTime());
        return view;
    }
}
