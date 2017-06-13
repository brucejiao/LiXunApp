package com.yuzhi.fine.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.yuzhi.fine.model.IssueModel.SecondMenu;

import java.util.List;

/**
 * Created by JiaoJianJun on 2017/6/13.
 * 自定义适配器用来定义spinner样式
 */


public class SpinnerArrayAdapter  extends ArrayAdapter {
    private Context mContext;

    private List<SecondMenu> mStringArray;

    public SpinnerArrayAdapter(Context context,List<SecondMenu> stringArray) {
        super(context, android.R.layout.simple_spinner_item, stringArray);
        mContext = context;
        mStringArray = stringArray;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // 修改Spinner展开后的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        // 此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
        tv.setText(mStringArray.get(position).getCategoryTitle());
        tv.setTextSize(12f);
        tv.setTextColor(Color.BLACK);

        return convertView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 修改Spinner选择后结果的字体颜色
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);
        }

        // 此处text1是Spinner默认的用来显示文字的TextView
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);
//        tv.setText(mStringArray[position]);
        tv.setText(mStringArray.get(position).getCategoryTitle());
        tv.setTextSize(12f);
        tv.setTextColor(Color.BLACK);
        return convertView;
    }

}