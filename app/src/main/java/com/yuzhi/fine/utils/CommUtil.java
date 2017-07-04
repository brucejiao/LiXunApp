package com.yuzhi.fine.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yuzhi.fine.R;
import com.yuzhi.fine.common.AppContext;
import com.yuzhi.fine.http.Caller;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.http.RestApiResponse;
import com.yuzhi.fine.model.CateGoryID;
import com.yuzhi.fine.model.CateGoryID2Name;
import com.yuzhi.fine.ui.AddContentDialog;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import okhttp3.Request;

import static com.yuzhi.fine.utils.Constant.RESUTL_TRUE;

/**
 * @author 工具类，封装常用的操作方法
 */
public class CommUtil {
	private static Boolean isExit = false;
	private static SharePreferenceUtil1 share;

	/**
	 * Alert提示框
	 * 
	 * @param msg
	 *            需要提示的消息
	 * @param context
	 *            当前Activity的Context对象
	 */
	public static void showAlert(String msg, final Context context) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg).setCancelable(false).setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Alert提示框
	 * 
	 * @param msg
	 *            需要提示的消息
	 * @param context
	 *            当前Activity的Context对象
	 */
	public static void showAlert(String msg, final Context context, OnClickListener confirClick) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg).setCancelable(false).setPositiveButton("确定", confirClick);

		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * Alert提示框
	 * 
	 * @param msg
	 *            需要提示的消息
	 * @param context
	 *            当前Activity的Context对象
	 *
	 */
	public static void showAlert(String msg, final Context context, OnClickListener confirClick,
			OnClickListener cancelClick) {

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg).setCancelable(false).setPositiveButton("确定", confirClick).setNegativeButton("取消",
				cancelClick);
		AlertDialog alert = builder.create();
		alert.show();
	}

	/**
	 * 退出应用，带提示
	 * 
	 * @param context
	 */
	public static void quitApp(final Context context) {

		new AlertDialog.Builder(context).setTitle("退出移动办公").setMessage("确认是否立即退出移动办公吗？")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						quitAppDirect(context);
					}
				}).setNegativeButton("返回", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 点击“返回”后的操作,这里不设置没有任何操作

					}
				}).show();
	}

	/**
	 * 直接退出应用，无提示
	 * 
	 * @param context
	 */
	public static void quitAppDirect(final Context context) {

		// if(CommUtil.isServiceRunning(context,"com.aisino.mobioaclient.page.pedometer.StepService")){
		//
		// System.out.println("最小化");
		// Intent home = new Intent(Intent.ACTION_MAIN);
		// home.addCategory(Intent.CATEGORY_HOME);
		// context.startActivity(home);
		//
		// }else{
		 AppContext.getInstance().exit();
		// }
	}

	public static boolean isServiceRunning(Context mContext, String className) {

		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);

		List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(Integer.MAX_VALUE);

		if (!(serviceList.size() > 0)) {
			return false;
		}

		for (int i = 0; i < serviceList.size(); i++) {

			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}

	/**
	 * 消息提示
	 * 
	 * @param msg
	 *            提示的消息
	 * @param context
	 *            当前Activity的Context对象
	 */
	public static void showToast(String msg, final Context context) {
		Toast toast = new Toast(context);
		toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		toast.show();// 显示
	}

	/**
	 * 获取AndroidManifest.xml中App meteData
	 * 
	 * @param metaDataName
	 * @param context
	 * @return
	 */
	public static String getAppMetaData(String metaDataName, final Context context) {
		ApplicationInfo info;
		String strData = "";
		try {
			String a = context.getPackageName();
			info = context.getPackageManager().getApplicationInfo(a, PackageManager.GET_META_DATA);
			strData = info.metaData.getString(metaDataName);
			System.out.println(strData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strData;
	}

	public static boolean isConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						Log.v("网络状态", "连通状态...........");

						return true;
					}
				}
			}
		} catch (Exception e) {
			Log.v("error", e.toString());
		}
		Log.v("网络状态", "网络不通...........");

		return false;
	}

	/**
	 * 判断对象是否为空
	 * 
	 * @param value
	 * @return boolean
	 */
	public static boolean isNullOrBlank(Object value) {
		if (null == value || value.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNullOrBlank(List value) {
		if (null == value || value.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static String objectString(Object value) {

		if (value == null || "".equals(value)) {
			return "";
		} else {
			return value.toString();
		}

	}

	// public static boolean isDevMode() {
	// return 0 == Constant.DEV_MODE;
	// }



	public static String DateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}





	/**
	 * 读取assest下文件
	 * 
	 * @param mContext
	 * @param fileName
	 * @return
	 */
	public static String getFromAssets(Context mContext, String fileName) {
		try {
			InputStreamReader inputReader = new InputStreamReader(mContext.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			LogUtil.i("vvvvvvvvv\t\t", line);
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// 自定义listView每个Itenview的高度
		public static void setListViewHeightBasedOnChildren(ListView listView, BaseAdapter adapter) {
			// 获取listview的adapter
			if (adapter == null) {
				return;
			}
			// 固定列宽，有多少列
			int col = 1;// listView.getNumColumns();
			int totalHeight = 0;
			// i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
			// listAdapter.getCount()小于等于8时计算两次高度相加
			for (int i = 0; i < adapter.getCount(); i += col) {
				// 获取listview的每一个item
				View listItem = adapter.getView(i, null, listView);
				listItem.measure(0, 0);
				// 获取item的高度和
				totalHeight += listItem.getMeasuredHeight();
			}

			// 获取listview的布局参数
			ViewGroup.LayoutParams params = listView.getLayoutParams();
			// 设置高度
			params.height = totalHeight;
			// 设置margin
			((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
			// 设置参数
			listView.setLayoutParams(params);
		}

	// 自定义GridView每个Itenview的高度
	public static void setGridViewHeightBasedOnChildren(GridView listView, BaseAdapter adapter) {
		// 获取listview的adapter
		if (adapter == null) {
			return;
		}
		// 固定列宽，有多少列
		int col = 1;// listView.getNumColumns();
		int totalHeight = 0;
		// i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
		// listAdapter.getCount()小于等于8时计算两次高度相加
		for (int i = 0; i < adapter.getCount(); i += col) {
			// 获取listview的每一个item
			View listItem = adapter.getView(i, null, listView);
			listItem.measure(0, 0);
			// 获取item的高度和
			totalHeight += listItem.getMeasuredHeight();
		}

		// 获取listview的布局参数
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		// 设置高度
		params.height = totalHeight;
		// 设置margin
		((MarginLayoutParams) params).setMargins(10, 10, 10, 10);
		// 设置参数
		listView.setLayoutParams(params);
	}

	/**
	 * 计算GridView宽高
	 * @param gridView
	 */
	public static void calGridViewWidthAndHeigh(int numColumns ,GridView gridView) {


		// 获取GridView对应的Adapter
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, gridView);
			listItem.measure(0, 0); // 计算子项View 的宽高

			if ((i+1)%numColumns == 0) {
				totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
			}

			if ((i+1) == len && (i+1)%numColumns != 0) {
				totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
			}
		}

		totalHeight += 10;

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight;
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		gridView.setLayoutParams(params);

	}


	public static  void fixListViewHeight(ListView listView) {
		// 如果没有设置数据适配器，则ListView没有子项，返回。
		ListAdapter listAdapter = listView.getAdapter();
		int totalHeight = 0;
		if (listAdapter == null) {
			return;
		}
		for (int index = 0, len = listAdapter.getCount(); index< len; index++) {
			View listViewItem = listAdapter.getView(index , null, listView);
			// 计算子项View 的宽高
			listViewItem.measure(0, 0);
			// 计算所有子项的高度和
			totalHeight += listViewItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		// listView.getDividerHeight()获取子项间分隔符的高度
		// params.height设置ListView完全显示需要的高度
		params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}


	/**
	 * 加载网络图片
	 * @param url
	 * @return
	 */
	public static Bitmap getURLimage(String url) {
		Bitmap bmp = null;
		try {
			URL myurl = new URL(url);
			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
			conn.setConnectTimeout(6000);//设置超时
			conn.setDoInput(true);
			conn.setUseCaches(false);//不缓存
			conn.connect();
			InputStream is = conn.getInputStream();//获得图片的数据流
			bmp = BitmapFactory.decodeStream(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bmp;
	}

	/**
	 * 打开手机相册
	 */
	public static Intent openCamera(){
		Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
		intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
		intent.setAction(Intent.ACTION_GET_CONTENT);
		return intent;
	}

	public static int getScreenWidth(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.widthPixels;
	}

	public static int getScreenHeight(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.heightPixels;
	}

	/**
	 * 获取assest文件夹下文件
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String readAssert(Context context,  String fileName){
		String jsonString="";
		String resultString="";
		try {
			InputStream inputStream=context.getResources().getAssets().open(fileName);
			byte[] buffer=new byte[inputStream.available()];
			inputStream.read(buffer);
			resultString=new String(buffer,"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

	public static void hideKeyBoard(Activity context) {
		if (context != null && context.getCurrentFocus() != null) {
			((InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(context.getCurrentFocus()
									.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}


	/**
	 * 获取地区编码id
	 */
	public static String getAddressId(String[] arrays , String params){
		final  int arraysNum = arrays.length;
		for (int index = 0 ; index < arraysNum ; index ++)
		{
			//1|中国
			String id = arrays[index].substring(0, arrays[index].indexOf("|"));
			String name = arrays[index].substring(arrays[index].indexOf("|")+1, arrays[index].length());
			if (params.equals(name))
			{
				return id;
			}

		}
				return "";
	}


/**
 *fileType 文件后缀名
 */
	public static  String generateFileName(String fileType){
		String str[] = { "a", "b", "c", "d", "e", "f", "g","h", "i", "j","k", "l", "m", "n", "o", "p", "q","r", "s", "t"
				, "u", "v", "w", "x", "y", "z"};
		Random tmp = new Random();
		UUID uuid = UUID.randomUUID();
		long currentTime = System.currentTimeMillis();
		String fileName = uuid+ str[tmp.nextInt(26)]+currentTime + "." + fileType;

		return fileName;
	}

	/**
	 * 返回EditText的文本信息
	 * @param editText
	 * @return
	 */
	public static String showEditString(EditText editText){
		return  editText.getText().toString().trim();
	}


	/**
	 * 加载进度条
	 * @param activity
	 * @param hintText
	 * @return
	 * 用法
	 * private ProgressDialog progress;
	 *  progress = CommUtil.showProgress(mContext, "正在加载数据，请稍候...");
	 * if (progress != null)
		{
		progress.dismiss();
		}
	 */
	public static ProgressDialog showProgress(Activity activity, String hintText) {
		Activity mActivity = null;
		if (activity.getParent() != null) {
			mActivity = activity.getParent();
			if (mActivity.getParent() != null) {
				mActivity = mActivity.getParent();
			}
		} else {
			mActivity = activity;
		}
		final Activity finalActivity = mActivity;
		ProgressDialog window = ProgressDialog.show(finalActivity, "", hintText);
		window.getWindow().setGravity(Gravity.CENTER);

		window.setCancelable(false);
		return window;
	}



	/**
	 *字符串的日期格式的计算
	 */
	public static String daysBetween(String smdate,String bdate) {
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			long between_days=(time2-time1)/(1000*3600*24);
			int distanceDate = Integer.parseInt(String.valueOf(between_days));
			if (distanceDate>0){
				return String.valueOf(between_days);
			}
		}catch (ParseException  e){

		}
		return  "";
	}

	/**
	 *字符串的日期格式的计算
	 */
	public static String daysBetween2(String smdate,String bdate) {
		long day = 1000 * 24 * 60 * 60;
		long hour = 1000 * 60 * 60;
		long minute = 1000 * 60;
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();

			long between_days=(time2-time1)/day;
			int distanceDate = Integer.parseInt(String.valueOf(between_days));
			if (distanceDate>0){
				return String.valueOf(between_days)+"天前发布";
			}
			else
			{
				long between_times=(time2-time1)/hour;
				return String.valueOf(between_times)+"小时前发布";
			}


		}catch (ParseException  e){

		}
		return  "";
	}

	/**
	 * 获取当前时间
	 * @return
	 */
	public static String currentDate(){
		String temp_str="";
		Date dt = new Date();
		//hh 12
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss hh");
		temp_str=sdf.format(dt);
		return temp_str;
	}

	/**
	 *截取价格小数点后的0
	 */
	public static String subMoneyZero(String params){
		if(params.contains(".")){
			String subMoney =  params.substring(params.indexOf(".")+1,params.length());
			if(subMoney.equals("0")){
				String money =  params.substring(0,params.indexOf("."));
				return money;
			}else{
				return params;
			}
		}else{
			return params;
		}
	}

	/**
	 * @param mDialog    dialog对象
	 * @param mContext   上下文
	 * @param title      标题
	 * @param msg        消息
	 * @param ok         确定
	 * @param cancel     取消
	 * @param okClick    确定监听
	 * @param cancelClick 取消监听
	 */
	public static void okCancleDialog(AddContentDialog mDialog, Context mContext, String title,String msg, String ok, android.view.View.OnClickListener okClick, String cancel, android.view.View.OnClickListener cancelClick){
		mDialog.setDialog(R.layout.dialog_add_content);
		mDialog.txt_Title.setText(title);
		mDialog.txt_content.setText(msg);
		mDialog.dialog_button_details.setText(ok);
		mDialog.dialog_button_cancel.setText(cancel);
		mDialog.dialog_button_details.setOnClickListener((android.view.View.OnClickListener) okClick);
		mDialog.dialog_button_cancel.setOnClickListener((android.view.View.OnClickListener) cancelClick);
		mDialog.show();
	}

	/**
	 *查询所有一二级菜单的id和名称
	 * @return
	 */
	public static void   getCategoryIdNameList(Context context) {
		share = new SharePreferenceUtil1(context, "lx_data", 0);
		final List<CateGoryID2Name> mapArrayList = new ArrayList<CateGoryID2Name>();
		HttpClient.get(Caller.GET_FRIST_SECOND_MENU, null, new HttpResponseHandler() {
			@Override
			public void onSuccess(RestApiResponse response) {
				String result = response.getResult();
				String message = response.getMessage();
				String data = response.getData();
				if (!CommUtil.isNullOrBlank(result) && result.equals(RESUTL_TRUE)) {

					final List<CateGoryID> CateGoryIDList = JSON.parseArray(data, CateGoryID.class);
					final int findListNum = CateGoryIDList.size();
					for (int index = 0; index < findListNum; index++) {
						CateGoryID2Name cateGoryID2Name = new CateGoryID2Name();
						String categoryID = CateGoryIDList.get(index).getCategoryID();
						String categoryTitle = CateGoryIDList.get(index).getCategoryTitle();
						cateGoryID2Name.setCategoryID(categoryID);
						cateGoryID2Name.setCategoryTitle(categoryTitle);
						mapArrayList.add(cateGoryID2Name);

					}
					share.putModels("categoryIDListKey",mapArrayList);

				}
			}

			@Override
			public void onFailure(Request request, Exception e) {

			}
		});
	}

	/**
	 * 根据菜单的id查询菜单的名称
	 * @param categoryID
	 * @return
	 */
	public static  String getCategoryId2Name(String categoryID,List<CateGoryID2Name> cateIDList){
		String categoryName ="";
		for (int index = 0 ; index <cateIDList.size(); index ++){
			if (categoryID.equals(cateIDList.get(index).getCategoryID())){
				categoryName = cateIDList.get(index).getCategoryTitle();
				return categoryName;

			}
		}
		return categoryName;
	}

}

