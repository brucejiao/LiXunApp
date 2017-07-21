package com.yuzhi.lixun110ccd.activity;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.ImageView;

import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.common.AppContext;
import com.yuzhi.lixun110ccd.utils.CommUtil;

public class WelcomeActivity extends Activity {

	private static final String TAG = "WelcomeActivity";
	SharedPreferences sp;
	AppContext app;
	ImageView logo;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		logo = (ImageView) findViewById(R.id.showLogo);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.welcome);
		isNetworkAvailable(WelcomeActivity.this);


	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		};
	};

	public  boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		} else {
			// 打印所有的网络状态
			NetworkInfo[] infos = cm.getAllNetworkInfo();
			// 如果仅仅是用来判断网络连接
			// 则可以使用 cm.getActiveNetworkInfo().isAvailable();
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo != null) {
				handler.sendEmptyMessageDelayed(0, 3000);
			} else {
				// Log.i(TAG, "isNetworkAvailable - 完成没有网络！");
				CommUtil.showAlert("无网络连接,请连接网络后重试!", context, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						android.os.Process.killProcess(android.os.Process.myPid());
					}
				});

				return false;
			}

			// 1、判断是否有3G网络
			if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				handler.sendEmptyMessageDelayed(0, 3000);
				return true;
			} else {
				// Log.i(TAG, "isNetworkAvailable - 没有3G网络");
			}

			// 2、判断是否有wifi连接
			if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				handler.sendEmptyMessageDelayed(0, 3000);
				return true;
			} else {
				// Log.i(TAG, "isNetworkAvailable - 没有wifi连接");
			}
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//finish();
	}
}
