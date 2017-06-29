package com.yuzhi.fine.common;

import android.app.Activity;
import android.app.Application;

import com.yuzhi.fine.utils.LocationUtils;
import com.yuzhi.fine.utils.LogUtil;

import java.util.LinkedList;

public class AppContext extends Application {

    private static AppContext app;

    private LinkedList<Activity> activitys = null;


//    private RefWatcher refWatcher;//LeakCanary检测

    public AppContext() {
        app = this;
    }

    public static synchronized AppContext getInstance() {
        if (app == null) {
            app = new AppContext();
        }
        return app;
    }


//    public static RefWatcher getRefWatcher(Context context) {
//        AppContext application = (AppContext) context.getApplicationContext();
//        return application.refWatcher;
//    }


    @Override
    public void onCreate() {
        super.onCreate();
        LocationUtils.initLocation(this);
        activitys = new LinkedList<Activity>();

        registerUncaughtExceptionHandler();
        //内存泄漏检测
//        LeakCanary.install(this);
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        refWatcher = LeakCanary.install(this);


}


    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (activitys != null && activitys.size() > 0) {
            if (!activitys.contains(activity)) {
                activitys.add(activity);
                LogUtil.i("==activity==", activity+"\t<---->length:\t"+activitys.size());
            }
        } else {

            activitys.add(activity);
            LogUtil.i("==activity==", activity+"\t<---->length:\t"+activitys.size());
        }
    }

    // 遍历所有Activity并finish
    public void exitAll() {

        exit();
        System.exit(0);
    }

    public void exit() {

        if (activitys != null && activitys.size() > 0) {
            for (Activity activity : activitys) {
                activity.finish();
            }
        }

    }


    // 注册App异常崩溃处理器
    private void registerUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
    }



}