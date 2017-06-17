package com.yuzhi.fine.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.yuzhi.fine.http.HttpRequestUtil;
import com.yuzhi.fine.model.GoogleLoc2Add.GoogleAddressComponents;
import com.yuzhi.fine.model.GoogleLoc2Add.GoogleLoc;
import com.yuzhi.fine.model.GoogleLoc2Add.GoogleResults;
import com.yuzhi.fine.model.GoogleLoc2Add.LocationModels;

import java.util.List;

import static com.yuzhi.fine.http.Caller.GOOGLE_MAP_LOCATION;

/**
 * Created by JiaoJianJun on 2017/6/16.
 */

public class GoogleLocationUtil {

    static   LocationModels mLocationModels ;
     static  Context mContext ;

   public  GoogleLocationUtil(Context context)
   {
       this.mContext = context;
       //初始化定位工具
       LocationUtils.initLocation(mContext);
//       new Thread(networkTask).start();

       locToAddress();
    }


    public static LocationModels getmLocationModels(){
        //Google 接口获取数据
        GoogleLoc googleLoc = JSON.parseObject(locToAddress(), GoogleLoc.class);
        List<GoogleResults> googleResults = JSON.parseArray(googleLoc.getResults(), GoogleResults.class);
        String address_components = googleResults.get(0).getAddress_components();
        List<GoogleAddressComponents> googleAddressComponents = JSON.parseArray(address_components, GoogleAddressComponents.class);

        String road = googleAddressComponents.get(0).getLong_name();//道路
        String county = googleAddressComponents.get(1).getLong_name();//县级市或者区
        String city = googleAddressComponents.get(2).getLong_name();//地级市
        String province = googleAddressComponents.get(3).getLong_name();//省
        String national = googleAddressComponents.get(4).getLong_name();//国家

        mLocationModels.setRoad(road);
        mLocationModels.setCounty(county);
        mLocationModels.setCity(city);
        mLocationModels.setProvince(province);
        mLocationModels.setNational(national);

        //详细地址
        String address = googleResults.get(0).getFormatted_address();
        String addressStr = address.substring(2, address.length());//截去‘中国’
        if (addressStr.contains(" ")) {
            String[] addressStrArray = addressStr.split(" ");
//                mIssueDetailAddress.setText(addressStrArray[0]);
            mLocationModels.setDetailsAddress(addressStrArray[0]);
        } else {
//                mIssueDetailAddress.setText(addressStr);
            mLocationModels.setDetailsAddress(addressStr);
        }
        return  mLocationModels;
    }

    /**
     * 获取请求地址结果并更新到UI
     */
//    public static Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            mLocationModels = new LocationModels();
//            // 在这里进行 http request.网络请求相关操作
//            Bundle data = msg.getData();
//            String val = data.getString("value");

//    }
//};

        /**
         * 网络操作相关的子线程
         */
//        Runnable networkTask = new Runnable() {
//
//            @Override
//            public void run() {
//                // 在这里进行 http request.网络请求相关操作
//                String loc = String.valueOf(LocationUtils.latitude)+","+String.valueOf(LocationUtils.longitude);
//                String address = locToAddress(loc);
//                Message msg = new Message();
//                Bundle data = new Bundle();
//                data.putString("value", address);
//                msg.setData(data);
//                handler.sendMessage(msg);
//            }
//        };



    /**
     * 将经纬度转换为地址
     * location 拼接方式   ： 纬度，经度
     */
    private static  String locToAddress(){
        String loc = String.valueOf(LocationUtils.latitude)+","+String.valueOf(LocationUtils.longitude);

        //google
        String params = "latlng="+loc+"&sensor=true&language=zh-CN";

        return HttpRequestUtil.sendGet(GOOGLE_MAP_LOCATION,params);
    }
}
