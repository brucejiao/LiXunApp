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
import com.yuzhi.lixun110ccd.model.MineFindBean;
import com.yuzhi.lixun110ccd.utils.DeviceUtil;

import java.util.ArrayList;

/**
 * 我的---网络社交
 */
public class MineWLSJItemapter extends BaseAdapter {
	private Context activity;
	private ArrayList<MineFindBean> arrayBean;

	public MineWLSJItemapter(Context activity, ArrayList<MineFindBean> arrayBean) {
		super();
		this.activity = activity;
		this.arrayBean = arrayBean;
	}

	private class ViewHolder {
		public TextView mine_find_time;//发布时间
		public TextView mine_find_looker;//阅读人数
		public TextView mine_find_focuson;//关注人数
		public TextView mine_find_message;//留言人数
		public RoundedImageView  mine_find_header_img;// 照片
		public TextView mine_find__title;// 标题
		public TextView mine_find__content;// 内容
		public TextView mine_find_people_num;// 线索



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
			view = ((Activity) activity).getLayoutInflater().inflate(R.layout.item_mine_wlsj, parent, false);
			holder = new ViewHolder();
			holder.mine_find_time = (TextView) view.findViewById(R.id.mine_find_time);
			holder.mine_find_looker = (TextView) view.findViewById(R.id.mine_find_looker);
			holder.mine_find_focuson = (TextView) view.findViewById(R.id.mine_find_focuson);
			holder.mine_find_message = (TextView) view.findViewById(R.id.mine_find_message);
			holder.mine_find_header_img = (RoundedImageView) view.findViewById(R.id.mine_find_header_img);
			holder.mine_find__title = (TextView) view.findViewById(R.id.mine_find__title);
			holder.mine_find__content = (TextView) view.findViewById(R.id.mine_find__content);
			holder.mine_find_people_num = (TextView) view.findViewById(R.id.mine_find_people_num);
			view.setTag(holder); // 给View添加一个格外的数据
		} else {
			holder = (ViewHolder) view.getTag(); // 把数据取出来
		}
		MineFindBean bean = getItem(position);
		Picasso.with(activity).load(bean.getMineFindHeaderImg())
				.resize(DeviceUtil.dp2px(activity,50), DeviceUtil.dp2px(activity,50))
				.placeholder(R.drawable.default_image).into(holder.mine_find_header_img);
		holder.mine_find_time.setText(bean.getMineFindTime());
		holder.mine_find_looker.setText(bean.getMineFindLooker());
		holder.mine_find_focuson.setText(bean.getMineFindFocuson());
		holder.mine_find_message.setText(bean.getMineFindMessage());
		holder.mine_find__title.setText(bean.getMineFindTitle());
		holder.mine_find__content.setText(bean.getMineFindContent());
		return view;
	}
}
