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
import com.yuzhi.fine.model.MineFindBean;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.DeviceUtil;

import java.util.ArrayList;

/**
 * 我的---提供线索
 */
public class MineProvideClueItemapter extends BaseAdapter {
	private Context activity;
	private ArrayList<MineFindBean> arrayBean;

	public MineProvideClueItemapter(Context activity, ArrayList<MineFindBean> arrayBean) {
		super();
		this.activity = activity;
		this.arrayBean = arrayBean;
	}

	private class ViewHolder {
		public TextView mine_find_time;//发布时间
		public RoundedImageView  mine_find_header_img;// 照片
		public TextView mine_find__title;// 标题
		public TextView mine_find__ing;// 进行中
		public TextView mine_find__content;// 内容



	}

	@Override
	public int getCount() {
		return arrayBean.size();
	}

	@Override
	public MineFindBean getItem(int position) {

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
			view = ((Activity) activity).getLayoutInflater().inflate(R.layout.item_mine_provide, parent, false);
			holder = new ViewHolder();

			holder.mine_find_time = (TextView) view.findViewById(R.id.mine_find_time);
			holder.mine_find_header_img = (RoundedImageView) view.findViewById(R.id.mine_find_header_img);
			holder.mine_find__title = (TextView) view.findViewById(R.id.mine_find__title);
			holder.mine_find__ing = (TextView) view.findViewById(R.id.mine_find__ing);
			holder.mine_find__content = (TextView) view.findViewById(R.id.mine_find__content);

			view.setTag(holder); // 给View添加一个格外的数据
		} else {
			holder = (ViewHolder) view.getTag(); // 把数据取出来
		}

		MineFindBean bean = getItem(position);
		Picasso.with(activity).load(bean.getMineFindHeaderImg())
				.resize(DeviceUtil.dp2px(activity,50), DeviceUtil.dp2px(activity,50))
				.placeholder(R.drawable.default_image).into(holder.mine_find_header_img);

		holder.mine_find_time.setText(bean.getMineFindTime());
		holder.mine_find__title.setText(bean.getMineFindTitle());
		holder.mine_find__ing.setText(bean.getMineFindIng());
		holder.mine_find__content.setText(bean.getMineFindContent());

		return view;
	}
}
