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
import com.yuzhi.fine.activity.mineActivity.MineFocusActivity;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.LXFind.FindListBean;
import com.yuzhi.fine.ui.AddContentDialog;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.utils.CommUtil;
import com.yuzhi.fine.utils.DeviceUtil;
import com.yuzhi.fine.utils.SharePreferenceUtil1;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Request;

import static com.yuzhi.fine.utils.CommUtil.showToast;
import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;
import static com.yuzhi.fine.utils.Constant.SHARE_LOGIN_USERID;

/**
 * 我的---我的关注
 */
public class MineFocusItemapter extends BaseAdapter {
	private MineFocusActivity activity;
	private ArrayList<FindListBean> arrayBean;
	SharePreferenceUtil1 share;
	private IRefresh mIRefresh = null;

	public MineFocusItemapter(MineFocusActivity activity, ArrayList<FindListBean> arrayBean) {
		super();
		this.activity = activity;
		this.arrayBean = arrayBean;
		share = new SharePreferenceUtil1(activity, "lx_data", 0);
		mIRefresh = activity;
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
		holder.mine_draft_price.setText(CommUtil.subMoneyZero(bean.getMoneyPaid(),2)+"元");
		holder.mine_draft_edit_btn.setText("取消关注");
		mIRefresh.onRefresh(false);
        holder.mine_draft_edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				final  AddContentDialog	mDialog = new AddContentDialog(activity, R.style.MessageDialog);
				mDialog.setDialog(R.layout.dialog_add_content);
				mDialog.txt_Title.setText("提示");
				mDialog.txt_content.setText("确定取消关注吗？");
				mDialog.txt_content.setEnabled(false);
				mDialog.txt_content.setHeight(20);
				mDialog.dialog_button_details.setText("确定");
				mDialog.dialog_button_cancel.setText("取消");
				mDialog.dialog_button_details.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String publistID =bean.getPublishID();//发布ID
						String userID = share.getString(SHARE_LOGIN_USERID, "");// 用户Id
						HashMap<String, String> params = new HashMap<>();
						params.put("publishid", publistID);
						params.put("userid", userID);

						HttpClient.get(Caller.CANCLE_FOCUS, params, new HttpResponseHandler() {
							@Override
							public void onSuccess(RestApiResponse response) {
								String result = response.getResult();
								String message = response.getMessage();

								if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {
                  			 		 showToast(message, activity);
									mIRefresh.onRefresh(true);
									mDialog.dismiss();
								} else {
									showToast(message, activity);
									mIRefresh.onRefresh(false);
									mDialog.dismiss();
								}
							}

							@Override
							public void onFailure(Request request, Exception e) {
								showToast("取消关注失败", activity);
								mIRefresh.onRefresh(false);
								mDialog.dismiss();
							}
						});
					}
				});
				mDialog.dialog_button_cancel.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						mDialog.dismiss();
					}
				});
				mDialog.show();


            }
        });

		return view;
	}

	public interface IRefresh {
		public void onRefresh(boolean refresh);
	}
}
