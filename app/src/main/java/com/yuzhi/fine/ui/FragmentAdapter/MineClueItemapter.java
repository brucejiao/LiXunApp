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
import com.yuzhi.fine.utils.DeviceUtil;

import java.util.ArrayList;

/**
 * 我的---我的线索
 */
public class MineClueItemapter extends BaseAdapter {
	private Context activity;
	private ArrayList<MineFindBean> arrayBean;

	public MineClueItemapter(Context activity, ArrayList<MineFindBean> arrayBean) {
		super();
		this.activity = activity;
		this.arrayBean = arrayBean;
	}

	private class ViewHolder {
		public TextView mine_clue_time;//发布时间
		public TextView mine_clue__state;// 进行中
		public RoundedImageView  mine_clue_header_img;// 照片
		public TextView mine_clue__title;// 标题
		public TextView mine_clue__content;// 内容


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
			view = ((Activity) activity).getLayoutInflater().inflate(R.layout.item_mine_clue, parent, false);
			holder = new ViewHolder();

			holder.mine_clue_time = (TextView) view.findViewById(R.id.mine_clue_time);
			holder.mine_clue__state = (TextView) view.findViewById(R.id.mine_clue__state);
			holder.mine_clue_header_img = (RoundedImageView) view.findViewById(R.id.mine_clue_header_img);
			holder.mine_clue__title = (TextView) view.findViewById(R.id.mine_clue__title);
			holder.mine_clue__content = (TextView) view.findViewById(R.id.mine_clue__content);

			view.setTag(holder); // 给View添加一个格外的数据
		} else {
			holder = (ViewHolder) view.getTag(); // 把数据取出来
		}
		MineFindBean bean = getItem(position);



		Picasso.with(activity).load(bean.getMineFindHeaderImg())
				.resize(DeviceUtil.dp2px(activity,50), DeviceUtil.dp2px(activity,50))
				.placeholder(R.drawable.default_image).into(holder.mine_clue_header_img);

		holder.mine_clue_time.setText(bean.getMineFindTime());
		holder.mine_clue__title.setText(bean.getMineFindTitle());
		holder.mine_clue__state.setText(bean.getMineFindIng());
		holder.mine_clue__content.setText(bean.getMineFindContent());


		return view;
	}
}
