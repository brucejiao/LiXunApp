package com.yuzhi.fine.ui.FragmentAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.yuzhi.fine.R;
import com.yuzhi.fine.model.LXFind.FindListBean;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.utils.DeviceUtil;

import java.util.ArrayList;

/**
 * 我的---草稿箱
 */
public class MineDraftItemapter extends BaseAdapter {
	private Context activity;
	private ArrayList<FindListBean> arrayBean;

	public MineDraftItemapter(Context activity, ArrayList<FindListBean> arrayBean) {
		super();
		this.activity = activity;
		this.arrayBean = arrayBean;
	}

	private class ViewHolder {
		public TextView mine_draft_time;//发布时间
		public RoundedImageView mine_draft_header_img;//图片
		public TextView mine_draft__title;// 标题
		public TextView mine_draft__content;// 内容
		public TextView mine_draft_price;// 价格
		public Button mine_draft_edit_btn;// 编辑



	}

	@Override
	public int getCount() {
		return arrayBean.size();
	}

	@Override
	public FindListBean getItem(int position) {

		return arrayBean.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (convertView == null) {
			view = ((Activity) activity).getLayoutInflater().inflate(R.layout.item_mine_draftbox, parent, false);
			holder = new ViewHolder();

			holder.mine_draft_time = (TextView) view.findViewById(R.id.mine_draft_time);//发布时间
			holder.mine_draft_header_img = (RoundedImageView) view.findViewById(R.id.mine_draft_header_img);//图片
			holder.mine_draft__title = (TextView) view.findViewById(R.id.mine_draft__title);// 标题
			holder.mine_draft__content = (TextView) view.findViewById(R.id.mine_draft__content);// 内容
			holder.mine_draft_price = (TextView) view.findViewById(R.id.mine_draft_price);// 价格
			holder.mine_draft_edit_btn = (Button) view.findViewById(R.id.mine_draft_edit_btn);// 编辑


			view.setTag(holder); // 给View添加一个格外的数据
		} else {
			holder = (ViewHolder) view.getTag(); // 把数据取出来
		}
		final FindListBean bean = getItem(position);

		Picasso.with(activity).load(bean.getPicturePath())
				.resize(DeviceUtil.dp2px(activity, 50), DeviceUtil.dp2px(activity, 50))
				.placeholder(R.drawable.default_image).into(holder.mine_draft_header_img);

		holder.mine_draft_time.setText(bean.getCreateTime());
//		holder.mine_draft_header_img.setImageResource(R.drawable.default_headimg);
		holder.mine_draft__title.setText(bean.getTitle());
		holder.mine_draft__content.setText(bean.getContent());
		holder.mine_draft_price.setText(bean.getMoneyPaid());
		holder.mine_draft_edit_btn.setText("编辑");

        holder.mine_draft_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				String parentId = bean.getParentCategoryID();

//                CommUtil.showToast("position"+position+"--categoryID-->"+bean.getParentCategoryID(),activity);
				UIHelper.showEditDraft(activity,parentId,bean);

            }
        });

		return view;
	}
}
