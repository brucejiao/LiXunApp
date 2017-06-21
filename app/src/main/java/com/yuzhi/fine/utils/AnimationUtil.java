package com.yuzhi.fine.utils;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by JiaoJianJun on 2017/6/7.
 */

public class AnimationUtil {

    private static final String TAG = AnimationUtil.class.getSimpleName();

    /**
     * 从控件所在位置移动到控件的底部
     *隐藏
     * @return
     */
    public static TranslateAnimation moveToViewBottom() {
//        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//                                                                  Animation.RELATIVE_TO_SELF, 0.0f,
//                                                                  Animation.RELATIVE_TO_SELF,0.0f,
//                                                                  Animation.RELATIVE_TO_SELF, 1.0f);
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                                                                  Animation.RELATIVE_TO_SELF, 0.0f,
                                                                  Animation.RELATIVE_TO_SELF,1.0f,
                                                                  Animation.RELATIVE_TO_SELF, 0.0f);
//        mHiddenAction.setDuration(400);
        return mHiddenAction;
    }

    /**
     * 从控件的底部移动到控件所在位置
     *显示
     * @return
     */
    public static TranslateAnimation moveToViewLocation() {
//        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
//                                                                  Animation.RELATIVE_TO_SELF, 0.0f,
//                                                                  Animation.RELATIVE_TO_SELF,1.0f,
//                                                                  Animation.RELATIVE_TO_SELF, 0.0f);
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                                                                  Animation.RELATIVE_TO_SELF, 0.0f,
                                                                  Animation.RELATIVE_TO_SELF,0.0f,
                                                                  Animation.RELATIVE_TO_SELF, 1.0f);
//        mHiddenAction.setDuration(400);
        return mHiddenAction;
    }
}
