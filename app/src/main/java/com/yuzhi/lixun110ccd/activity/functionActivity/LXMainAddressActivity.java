package com.yuzhi.lixun110ccd.activity.functionActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.service.LocationService;
import com.yuzhi.lixun110ccd.R;
import com.yuzhi.lixun110ccd.model.City;
import com.yuzhi.lixun110ccd.ui.MyLetterListView;
import com.yuzhi.lixun110ccd.utils.ChineseCharToEn;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Pattern;

import static com.yuzhi.lixun110ccd.utils.CommUtil.showProgress;
import static com.yuzhi.lixun110ccd.utils.Constant.LX_MAIN_ADDRESS_RESULT;


public class LXMainAddressActivity extends AppCompatActivity {
    private LocationService locationService;
    private LXMainAddressActivity mContext ;
    private ListAdapter adapter;
    private ListView personList;
    private ImageView imgback;
    private TextView overlay; // 对话框首字母textview
    private MyLetterListView letterListView; // A-Z listview
    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private String[] sections;// 存放存在的汉语拼音首字母
    private Handler handler;
    private OverlayThread overlayThread; // 显示首字母对话框
    private ArrayList<City> allCity_lists; // 所有城市列表
    private ArrayList<City> ShowCity_lists; // 需要显示的城市列表-随搜索而改变
    private ArrayList<City> city_lists;// 城市列表
    private String lngCityName ="";//存放返回的城市名
    private JSONArray chineseCities ;
    private EditText sh;
    private TextView lng_city;
    private LinearLayout lng_city_lay;
    private ProgressDialog progress;
    private static final int SHOWDIALOG = 2;
    private static final int DISMISSDIALOG = 3;
//    private WindowManager mWindowManager;
    private String[] mAddressIdArray;//地区id对照表

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lxmain_address);
        mContext = this ;
        mAddressIdArray = getResources().getStringArray(R.array.address_arrays);

;

        personList = (ListView) findViewById(R.id.list_view);
        allCity_lists = new ArrayList<City>();
        letterListView = (MyLetterListView) findViewById(R.id.MyLetterListView01);
        lng_city_lay = (LinearLayout) findViewById(R.id.lng_city_lay);
        sh = (EditText) findViewById(R.id.sh);
        lng_city = (TextView) findViewById(R.id.lng_city);
        imgback = (ImageView) findViewById(R.id.imgback);

        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
        overlayThread = new OverlayThread();
        personList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                arg1.setBackgroundResource(R.color.short_gary);
                Intent intent = new Intent();
                intent.putExtra("lngCityName", ShowCity_lists.get(arg2).name);
                setResult(LX_MAIN_ADDRESS_RESULT,intent);
                finish();
            }
        });
        lng_city_lay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("lngCityName",lng_city.getText().toString());//lng_city   lngCityName
                setResult(LX_MAIN_ADDRESS_RESULT,intent);
                finish();
            }
        });
        imgback.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initOverlay();
        handler2.sendEmptyMessage(SHOWDIALOG);
        Thread thread = new Thread(){
            @Override
            public void run() {
                hotCityInit();
                handler2.sendEmptyMessage(DISMISSDIALOG);
                super.run();
            }
        };
        thread.start();
    }

    /**
     * 热门城市
     */
    public void hotCityInit() {
        City city;
        city = new City("上海市", "");
        allCity_lists.add(city);
        city = new City("北京市", "");
        allCity_lists.add(city);
        city = new City("广州市", "");
        allCity_lists.add(city);
        city = new City("深圳市", "");
        allCity_lists.add(city);
        city = new City("武汉市", "");
        allCity_lists.add(city);
        city = new City("天津市", "");
        allCity_lists.add(city);
        city = new City("西安市", "");
        allCity_lists.add(city);
        city = new City("杭州市", "");
        allCity_lists.add(city);
        city = new City("重庆市", "");
        allCity_lists.add(city);
        city = new City("南京市", "");
        allCity_lists.add(city);
        city = new City("成都市", "");
        allCity_lists.add(city);
        city_lists = getCityList();
        allCity_lists.addAll(city_lists);
        ShowCity_lists=allCity_lists;
    }

    private ArrayList<City> getCityList() {
        ArrayList<City> list = new ArrayList<City>();
        try {
           /* chineseCities = new JSONArray(getResources().getString(R.string.citys));
            for(int i=0;i<chineseCities.length();i++){
                JSONObject jsonObject = chineseCities.getJSONObject(i);
                City city = new City(jsonObject.getString("name"), jsonObject.getString("pinyin"));
                list.add(city);
            }*/

            for(int index=0;index<mAddressIdArray.length;index++){
                ChineseCharToEn cte = new ChineseCharToEn();
                String id = mAddressIdArray[index].substring(0, mAddressIdArray[index].indexOf("|"));
                String name = mAddressIdArray[index].substring(mAddressIdArray[index].indexOf("|")+1, mAddressIdArray[index].length());
                String pingYin = cte.getAllFirstLetter(name);
//                System.out.println("id---"+id+"获取拼音首字母："+ cte.getAllFirstLetter(name));
                if(!name.contains("区")){
                    City city = new City(name, pingYin);
                    list.add(city);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(list, comparator);
        return list;
    }

    /**
     * a-z排序
     */
    Comparator comparator = new Comparator<City>() {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getPinyi().substring(0, 1);
            String b = rhs.getPinyi().substring(0, 1);
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }

        }
    };

//	private void setAdapter(List<City> list) {
//		adapter = new ListAdapter(this, list);
//		personList.setAdapter(adapter);
//	}

    public class ListAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        final int VIEW_TYPE = 3;

        public ListAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
            alphaIndexer = new HashMap<String, Integer>();
            sections = new String[ShowCity_lists.size()];
            for (int i = 0; i < ShowCity_lists.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = getAlpha(ShowCity_lists.get(i).getPinyi());
                // 上一个汉语拼音首字母，如果不存在为“ ”
                String previewStr = (i - 1) >= 0 ? getAlpha(ShowCity_lists.get(i - 1)
                        .getPinyi()) : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(ShowCity_lists.get(i).getPinyi());
                    alphaIndexer.put(name, i);
                    sections[i] = name;
                }
            }
        }

        @Override
        public int getCount() {
            return ShowCity_lists.size();
        }

        @Override
        public Object getItem(int position) {
            return ShowCity_lists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            int type = 2;

            if (position == 0&&sh.getText().length()==0) {//不是在搜索状态下
                type = 0;
            }
            return type;
        }

        @Override
        public int getViewTypeCount() {// 这里需要返回需要集中布局类型，总大小为类型的种数的下标
            return VIEW_TYPE;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            int viewType = getItemViewType(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item, null);
                holder = new ViewHolder();
                holder.alpha = (TextView) convertView
                        .findViewById(R.id.alpha);
                holder.name = (TextView) convertView
                        .findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
//				if (sh.getText().length()==0) {//搜所状态
//					holder.name.setText(list.get(position).getName());
//					holder.alpha.setVisibility(View.GONE);
//				}else if(position>0){
            //显示拼音和热门城市，一次检查本次拼音和上一个字的拼音，如果一样则不显示，如果不一样则显示

            holder.name.setText(ShowCity_lists.get(position).getName());
            String currentStr = getAlpha(ShowCity_lists.get(position).getPinyi());//本次拼音
            String previewStr = (position-1) >= 0 ? getAlpha(ShowCity_lists.get(position-1).getPinyi()) : " ";//上一个拼音
            if (!previewStr.equals(currentStr)) {//不一样则显示
                holder.alpha.setVisibility(View.VISIBLE);
                if (currentStr.equals("#")) {
                    currentStr = "热门城市";
                }
                holder.alpha.setText(currentStr);
            } else {
                holder.alpha.setVisibility(View.GONE);
            }
//				}
            return convertView;
        }

        private class ViewHolder {
            TextView alpha; // 首字母标题
            TextView name; // 城市名字
        }
    }

    // 初始化汉语拼音首字母弹出提示框
    private void initOverlay() {
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                                                                        WindowManager.LayoutParams.TYPE_APPLICATION,
                                                                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                                                        PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);

    }

    private class LetterListViewListener implements MyLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                personList.setSelection(position);
                overlay.setText(sections[position]);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟一秒后执行，让overlay为不可见
                handler.postDelayed(overlayThread, 1500);
            }
        }

    }

    // 设置overlay不可见
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }

    }

    // 获得汉语拼音首字母
    private String getAlpha(String str) {

        if (str.equals("-")) {
            return "&";
        }
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else {
            return "#";
        }
    }








    @Override
    public void onDestroy() {
        super.onDestroy();
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }

//



    Handler handler2 = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case SHOWDIALOG:
                    progress = showProgress(mContext, "正在加载数据，请稍候...");
                    break;
                case DISMISSDIALOG:
                    if (progress != null)
                    {
                        progress.dismiss();
                    }
                    adapter = new ListAdapter(mContext);
                    personList.setAdapter(adapter);
//				personList.setAdapter(adapter);

                    sh.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                        }
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count,
                                                      int after) {
                        }
                        @Override
                        public void afterTextChanged(Editable s) {
                            //搜索符合用户输入的城市名
                            if(s.length()>0){
                                ArrayList<City> changecity = new ArrayList<City>();
                                for(int i=0;i<city_lists.size();i++){
                                    if(city_lists.get(i).name.indexOf(sh.getText().toString())!=-1){
                                        changecity.add(city_lists.get(i));
                                    }
                                }
                                ShowCity_lists = changecity;
                            }else{
                                ShowCity_lists = allCity_lists;
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    protected void onPause() {
        super.onPause();
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务

    }

    @Override
    public void onStart() {
        super.onStart();
//        locationService = ((AppContext)getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService = new LocationService(this);
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }



    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                lng_city.setText(location.getCity());
            }
        }
        public void onConnectHotSpotMessage(String s, int i){
        }
    };
}
