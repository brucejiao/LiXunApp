package com.yuzhi.fine.utils;

import android.content.Context;

public class ClearSpData {
	/**
	 * 清空缓存
	 */
	public static void clearSharePreference(Context mContext) {
		SharePreferenceUtil1 share = new SharePreferenceUtil1(mContext, "lx_data", 0);
		// 清除用户id
		share.remove(Constant.SHARE_LOGIN_USERID);

	}
}
