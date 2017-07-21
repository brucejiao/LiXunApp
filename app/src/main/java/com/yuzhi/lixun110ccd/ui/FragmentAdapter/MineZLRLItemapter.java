package com.yuzhi.lixun110ccd.ui.FragmentAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.model.MineZLRL;
import com.yuzhi.lixun110ccd.utils.DeviceUtil;

import java.util.ArrayList;

/**
 * 我的---我的线索
 */
public class MineZLRLItemapter extends BaseAdapter {
	private Context activity;
	private ArrayList<MineZLRL> arrayBean;

	public MineZLRLItemapter(Context activity, ArrayList<MineZLRL> arrayBean) {
		super();
		this.activity = activity;
		this.arrayBean = arrayBean;
	}

	private class ViewHolder {
		public RoundedImageView  zlrl_header_img;// 头像
		public TextView zlrl_username;//用户名
		public TextView zlrl_time;// 时间
		public RoundedImageView zlrl_pic;//
		public TextView zlrl_title;//
		public TextView zlrl_content;//
		public TextView zlrl_price;//
		public TextView zlrl_checkstate;


	}

	@Override
	public int getCount() {
		return arrayBean.size();
	}

	@Override
	public MineZLRL getItem(int position) {

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
			view = ((Activity) activity).getLayoutInflater().inflate(R.layout.item_mine_zlrl, parent, false);
			holder = new ViewHolder();

			holder.zlrl_header_img = (RoundedImageView) view.findViewById(R.id.zlrl_header_img);
			holder.zlrl_username = (TextView) view.findViewById(R.id.zlrl_username);
			holder.zlrl_time = (TextView) view.findViewById(R.id.zlrl_time);
			holder.zlrl_pic = (RoundedImageView) view.findViewById(R.id.zlrl_pic);
			holder.zlrl_title = (TextView) view.findViewById(R.id.zlrl_title);
			holder.zlrl_content = (TextView) view.findViewById(R.id.zlrl_content);
			holder.zlrl_price = (TextView) view.findViewById(R.id.zlrl_price);
			holder.zlrl_checkstate = (TextView) view.findViewById(R.id.zlrl_checkstate);

			view.setTag(holder); // 给View添加一个格外的数据
		} else {
			holder = (ViewHolder) view.getTag(); // 把数据取出来
		}
		MineZLRL bean = getItem(position);



		Picasso.with(activity).load(bean.getZlrl_header_img())
				.resize(DeviceUtil.dp2px(activity,50), DeviceUtil.dp2px(activity,50))
				.placeholder(R.drawable.default_image).into(holder.zlrl_header_img);

		holder.zlrl_username.setText(bean.getZlrl_username());
		holder.zlrl_time.setText(bean.getZlrl_time());
		Picasso.with(activity).load(bean.getZlrl_pic())
				.resize(DeviceUtil.dp2px(activity,50), DeviceUtil.dp2px(activity,50))
				.placeholder(R.drawable.default_image).into(holder.zlrl_pic);
		holder.zlrl_title.setText(bean.getZlrl_title());
		holder.zlrl_content.setText(bean.getZlrl_content());
		holder.zlrl_price.setText(bean.getZlrl_price());
		holder.zlrl_checkstate.setText(bean.getZlrl_checkstate());


		return view;
	}
}
