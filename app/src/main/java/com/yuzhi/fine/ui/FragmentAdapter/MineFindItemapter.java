package com.yuzhi.fine.ui.FragmentAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuzhi.fine.R;
import com.yuzhi.fine.model.MineFindBean;

import java.util.ArrayList;

/**
 * 我的---我的寻找
 */
public class MineFindItemapter extends BaseAdapter {
	private Context activity;
	private ArrayList<MineFindBean> arrayBean;

	public MineFindItemapter(Context activity, ArrayList<MineFindBean> arrayBean) {
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
		public TextView mine_find_price;// 价格

		public TextView mine_find__title;// 标题
		public TextView mine_find__ing;// 进行中
		public TextView mine_find__content;// 内容
		public TextView mine_find_people_num;// 线索

		public RoundedImageView mine_account_img1;//
		public RoundedImageView mine_account_img2;//
		public RoundedImageView mine_account_img3;//
		public RoundedImageView mine_account_img4;//
		public RoundedImageView mine_account_img5;//
		public RoundedImageView mine_account_img6;//
		public RoundedImageView mine_account_img7;//


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
			view = ((Activity) activity).getLayoutInflater().inflate(R.layout.item_mine_find, parent, false);
			holder = new ViewHolder();

			holder.mine_find_time = (TextView) view.findViewById(R.id.mine_find_time);
			holder.mine_find_looker = (TextView) view.findViewById(R.id.mine_find_looker);
			holder.mine_find_focuson = (TextView) view.findViewById(R.id.mine_find_focuson);
			holder.mine_find_message = (TextView) view.findViewById(R.id.mine_find_message);
			holder.mine_find_header_img = (RoundedImageView) view.findViewById(R.id.mine_find_header_img);
			holder.mine_find_price = (TextView) view.findViewById(R.id.mine_find_price);
			holder.mine_find__title = (TextView) view.findViewById(R.id.mine_find__title);
			holder.mine_find__ing = (TextView) view.findViewById(R.id.mine_find__ing);
			holder.mine_find__content = (TextView) view.findViewById(R.id.mine_find__content);
			holder.mine_find_people_num = (TextView) view.findViewById(R.id.mine_find_people_num);
			holder.mine_account_img1 = (RoundedImageView) view.findViewById(R.id.mine_account_img1);
			holder.mine_account_img2 = (RoundedImageView) view.findViewById(R.id.mine_account_img2);
			holder.mine_account_img3 = (RoundedImageView) view.findViewById(R.id.mine_account_img3);
			holder.mine_account_img4 = (RoundedImageView) view.findViewById(R.id.mine_account_img4);
			holder.mine_account_img5 = (RoundedImageView) view.findViewById(R.id.mine_account_img5);
			holder.mine_account_img6 = (RoundedImageView) view.findViewById(R.id.mine_account_img6);
			holder.mine_account_img7 = (RoundedImageView) view.findViewById(R.id.mine_account_img7);

			view.setTag(holder); // 给View添加一个格外的数据
		} else {
			holder = (ViewHolder) view.getTag(); // 把数据取出来
		}
		MineFindBean bean = getItem(position);

//		holder.lx_header_img.setBackgroundResource(R.drawable.default_headimg);
		holder.mine_find_header_img.setImageResource(R.drawable.default_headimg);
		holder.mine_account_img1.setBackgroundResource(R.drawable.house_background);  //ImageResource(R.drawable.house_background);
		holder.mine_account_img2.setBackgroundResource(R.drawable.house_background);  //.setImageResource(R.drawable.house_background);
		holder.mine_account_img3.setBackgroundResource(R.drawable.house_background);
		holder.mine_account_img4.setBackgroundResource(R.drawable.house_background);  //ImageResource(R.drawable.house_background);
		holder.mine_account_img5.setBackgroundResource(R.drawable.house_background);  //.setImageResource(R.drawable.house_background);
		holder.mine_account_img6.setBackgroundResource(R.drawable.house_background);
		holder.mine_account_img7.setBackgroundResource(R.drawable.house_background);  //ImageResource(R.drawable.house_background);



		holder.mine_find_time.setText(bean.getMineFindTime());
		holder.mine_find_looker.setText(bean.getMineFindLooker());
		holder.mine_find_focuson.setText(bean.getMineFindFocuson());
		holder.mine_find_message.setText(bean.getMineFindMessage());
		holder.mine_find_price.setText(bean.getMineFindPrice());
		holder.mine_find__title.setText(bean.getMineFindTitle());
		holder.mine_find__ing.setText(bean.getMineFindIng());
		holder.mine_find__content.setText(bean.getMineFindContent());


		return view;
	}
}
