package com.yuzhi.lixun110ccd.common;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.location.service.LocationService;
import com.baidu.mapapi.SDKInitializer;
import com.yuzhi.lixun110ccd.http.HttpRequestUtil;
import com.yuzhi.lixun110ccd.utils.CommUtil;
import com.yuzhi.lixun110ccd.utils.LocationUtils;
import com.yuzhi.lixun110ccd.utils.LogUtil;

import java.util.LinkedList;

import static com.yuzhi.lixun110ccd.http.Caller.GOOGLE_MAP_LOCATION;

public class AppContext extends Application {

    private static AppContext app;
    private LinkedList<Activity> activitys = null;
    public boolean beginTraceFlag =false;
    public static String mAddress = "";

    public LocationService locationService;
    public Vibrator mVibrator;
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


        /***
         * 初始化定位sdk，建议在Application中创建
         */
        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.initialize(this);

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


        //调用接口保存所有的菜单ID
        CommUtil.getCategoryIdNameList(this);


//        locToAddress();


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

    private void locToAddress() {
        //baidu
//        String params = "output=json&location="+location;
//        String resutl =  HttpRequestUtil.sendGet(BAIDU_MAP_LOCATION,params.trim());
        //google
        new Thread(){
            @Override
            public void run() {
                super.run();
                LocationUtils.initLocation(app);
                String loc = String.valueOf(LocationUtils.latitude) + "," + String.valueOf(LocationUtils.longitude);
//                String loc = String.valueOf(32.03) + "," + String.valueOf(118.46);
                String params = "latlng=" + loc + "&sensor=true&language=zh-CN";
                String  GoogleAddressresutl = HttpRequestUtil.sendGet(GOOGLE_MAP_LOCATION, params);
                mAddress = GoogleAddressresutl;
            }
        }.start();

    }

    // 注册App异常崩溃处理器
    private void registerUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
    }



}