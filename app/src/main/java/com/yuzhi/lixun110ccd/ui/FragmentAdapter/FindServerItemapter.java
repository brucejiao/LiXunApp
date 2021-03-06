package com.yuzhi.lixun110ccd.ui.FragmentAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.model.LXFindServerBean;
import com.yuzhi.lixun110ccd.utils.CommUtil;
import com.yuzhi.lixun110ccd.utils.DeviceUtil;

import java.util.ArrayList;

import static com.yuzhi.lixun110ccd.R.id.lx_header_img;

/**
 * 发现列表找寻适配器
 * 保留状态mFlag   0：首页GridView--委托寻人......传入
 *                1: 首页悬赏/普通找寻服务传入
 *               2:发现Fragment界面传入
 */
public class FindServerItemapter extends BaseAdapter {
    private Context activity;
    private ArrayList<LXFindServerBean> arrayBean;
    private int mFlag;

    public FindServerItemapter(Context activity, ArrayList<LXFindServerBean> arrayBean,int flag) {
        super();
        this.activity = activity;
        this.arrayBean = arrayBean;
        this.mFlag = flag ;
    }

    private class ViewHolder {
        public RoundedImageView lx_header_img;// 用户头像
        public TextView username;// 用户名
        public TextView certification_text;// 认证

        public TextView lx_title;// 标题
        public TextView lx_isfind;// 找寻、招领
        public TextView lx_isgenerailze;// 是否推广

        public TextView lx_address;// 地点
        public TextView lx_price;// 悬赏金额
        public TextView lx_content;// 发布内容

        public ImageView lx_img_one;// 图片一
        public ImageView lx_img_two;// 图片二
        public ImageView lx_img_three;// 图片三

        public TextView lx_time;//发布时间
        public TextView lx_looker;//阅读人数
        public TextView lx_focuson;//关注人数
        public TextView lx_message;//留言人数

    }

    @Override
    public int getCount() {
        return arrayBean.size();
    }

    @Override
    public LXFindServerBean getItem(int position) {

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
            view = ((Activity) activity).getLayoutInflater().inflate(R.layout.item_lx_find_layout, parent, false);
            holder = new ViewHolder();

            holder.lx_header_img = (RoundedImageView) view.findViewById(lx_header_img);
            holder.username = (TextView) view.findViewById(R.id.username);
            holder.certification_text = (TextView) view.findViewById(R.id.certification_text);
            holder.lx_title = (TextView) view.findViewById(R.id.lx_title);
            holder.lx_isfind = (TextView) view.findViewById(R.id.lx_isfind);
            holder.lx_isgenerailze = (TextView) view.findViewById(R.id.lx_isgenerailze);
            holder.lx_address = (TextView) view.findViewById(R.id.lx_address);
            holder.lx_price = (TextView) view.findViewById(R.id.lx_price);
            holder.lx_content = (TextView) view.findViewById(R.id.lx_content);
            holder.lx_img_one = (ImageView) view.findViewById(R.id.lx_img_one);
            holder.lx_img_two = (ImageView) view.findViewById(R.id.lx_img_two);
            holder.lx_img_three = (ImageView) view.findViewById(R.id.lx_img_three);
            holder.lx_time = (TextView) view.findViewById(R.id.lx_time);
            holder.lx_looker = (TextView) view.findViewById(R.id.lx_looker);
            holder.lx_focuson = (TextView) view.findViewById(R.id.lx_focuson);
            holder.lx_message = (TextView) view.findViewById(R.id.lx_message);

            view.setTag(holder); // 给View添加一个格外的数据
        } else {
            holder = (ViewHolder) view.getTag(); // 把数据取出来
        }
        LXFindServerBean bean = getItem(position);

        //头像
        Picasso.with(activity).load(bean.getUserHeaderImg())
                .resize(DeviceUtil.dp2px(activity, 43), DeviceUtil.dp2px(activity, 43))
                .placeholder(R.drawable.default_image).into(holder.lx_header_img);
        if (!CommUtil.isNullOrBlank(bean.getImgOne())) {
            //图片一
            Picasso.with(activity).load(bean.getImgOne())
                    .resize(DeviceUtil.dp2px(activity, 75), DeviceUtil.dp2px(activity, 75))
                    .placeholder(R.drawable.default_image).into(holder.lx_img_one);
        } else {
            holder.lx_img_one.setVisibility(View.GONE);
        }
        //图片二
        if (!CommUtil.isNullOrBlank(bean.getImgTwo())) {
            Picasso.with(activity).load(bean.getImgTwo())
                    .resize(DeviceUtil.dp2px(activity, 75), DeviceUtil.dp2px(activity, 75))
                    .placeholder(R.drawable.default_image).into(holder.lx_img_two);
        } else {
            holder.lx_img_two.setVisibility(View.GONE);
        }
        //图片三
        if (!CommUtil.isNullOrBlank(bean.getImgThree())) {
            Picasso.with(activity).load(bean.getImgThree())
                    .resize(DeviceUtil.dp2px(activity, 75), DeviceUtil.dp2px(activity, 75))
                    .placeholder(R.drawable.default_image).into(holder.lx_img_three);
        } else {
            holder.lx_img_three.setVisibility(View.GONE);
        }


        holder.username.setText(bean.getUserName());
        holder.certification_text.setText(bean.getIsCertification());
        holder.lx_title.setText(bean.getTitle());
        holder.lx_isfind.setText(bean.getIsFind());
        if (!CommUtil.isNullOrBlank(bean.getIsGenerailze())){
            holder.lx_isgenerailze.setText(bean.getIsGenerailze());
        }else {
            holder.lx_isgenerailze.setVisibility(View.GONE);
        }

        holder.lx_address.setText(bean.getAddress());
        holder.lx_price.setText(bean.getPrice());
        holder.lx_content.setText(bean.getContent());
        holder.lx_time.setText(bean.getTime());
        holder.lx_looker.setText(bean.getLookerNum());
        holder.lx_focuson.setText(bean.getFocusonNum());
        holder.lx_message.setText(bean.getMessageNum());


        return view;
    }

}
