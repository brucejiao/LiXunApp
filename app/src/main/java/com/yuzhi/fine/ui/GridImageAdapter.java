package com.yuzhi.fine.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuzhi.fine.R;


public class GridImageAdapter extends BaseAdapter{
	 // 定义Context
	  private LayoutInflater inflater;
	  
	  // 定义整型数组 即图片源
	
	  private Integer[]   mImageIds ;
	
	  
	  private String[]   mTextIs ;

	 private boolean  mLines;//GridView 是否带网格
	
	  public GridImageAdapter(Context context,Integer[] mImageIds,String[]  mTextIs ,boolean mLines){
		  inflater = LayoutInflater.from(context);
		  this.mImageIds=mImageIds;
		  this.mTextIs=mTextIs;
		  this.mLines = mLines;
	  }
	  
	  
	  public GridImageAdapter(Context context){
		
		  inflater = LayoutInflater.from(context);
			
	  }
	  // 获取图片的个数
	
	  @Override
	public int getCount(){		
	      return mImageIds.length;
	  }
	
	
	
	  // 获取图片在库中的位置
	
	  @Override
	public Object getItem(int position){
	
	      return position;
	
	  }
	
	
	  // 获取图片ID
	
	  @Override
	public long getItemId(int position){
	
	      return position;
	
	  }
					
	  @Override
	public View getView(int position, View paramView, ViewGroup parent){
          //判断是否带网格效果
		  if (mLines){
			  paramView = inflater.inflate(R.layout.menu_item_line, null);//带网格效果
		  }
		  else {
			  paramView = inflater.inflate(R.layout.menu_item, null);//不带网格效果
		  }

			TextView text = (TextView)paramView.findViewById(R.id.activity_name);
			
			text.setText(mTextIs[position]);	
			Drawable draw =  inflater.getContext().getResources().getDrawable(mImageIds[position]);
			draw.setBounds(0, 0, draw.getIntrinsicWidth(), draw.getIntrinsicHeight());
			text.setCompoundDrawables(null, draw, null, null);
			
//			paramView.setMinimumHeight((int)(96.0F * inflater.getContext().getResources().getDisplayMetrics().density));
		    paramView.setMinimumHeight((int)(80.0F * inflater.getContext().getResources().getDisplayMetrics().density));
			paramView.setMinimumWidth(((-12 + inflater.getContext().getResources().getDisplayMetrics().widthPixels) / 3));
			
			return paramView;
	
	  }

}
