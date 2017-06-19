package com.yuzhi.fine.ui.FragmentAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuzhi.fine.R;
import com.yuzhi.fine.model.LXFindServerBean;

import java.util.ArrayList;

/**
 * 悬赏/普通找寻适配器
 */
public class MineFindItemapter extends BaseAdapter {
	private Context activity;
	private ArrayList<LXFindServerBean> arrayBean;

	public MineFindItemapter(Context activity, ArrayList<LXFindServerBean> arrayBean) {
		super();
		this.activity = activity;
		this.arrayBean = arrayBean;
	}

	private class ViewHolder {
		public RoundedImageView lx_header_img;// 用户头像
		public TextView  username;// 用户名
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (convertView == null) {
			view = ((Activity) activity).getLayoutInflater().inflate(R.layout.item_mine_find, parent, false);
			holder = new ViewHolder();

			holder.lx_header_img = (RoundedImageView) view.findViewById(R.id.lx_header_img);
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

//		holder.lx_header_img.setBackgroundResource(R.drawable.default_headimg);
		holder.lx_header_img.setImageResource(R.drawable.default_headimg);
		holder.lx_img_one.setBackgroundResource(R.drawable.house_background);  //ImageResource(R.drawable.house_background);
		holder.lx_img_two.setBackgroundResource(R.drawable.house_background);  //.setImageResource(R.drawable.house_background);
		holder.lx_img_three.setBackgroundResource(R.drawable.house_background);

//		holder.lx_header_img.setImageBitmap(CommUtil.getURLimage(bean.getUserHeaderImg()));
//		holder.lx_img_one.setImageBitmap(CommUtil.getURLimage(bean.getImgOne()));
//		holder.lx_img_two.setImageBitmap(CommUtil.getURLimage(bean.getImgTwo()));
//		holder.lx_img_three.setImageBitmap(CommUtil.getURLimage(bean.getImgThree()));

		holder.username.setText(bean.getUserName());
		holder.certification_text.setText(bean.getIsCertification());
		holder.lx_title.setText(bean.getTitle());
		holder.lx_isfind.setText(bean.getIsFind());
		holder.lx_isgenerailze.setText(bean.getIsGenerailze());
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