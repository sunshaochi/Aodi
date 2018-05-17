package com.iris.cada.adapter;

import java.util.List;

import com.iris.foundation.utils.CommonAdapter;
import com.iris.cada.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyGridViewAdapter extends CommonAdapter<String> {
	
	private int selectIndex = 0;

	public MyGridViewAdapter(Context context, List<String> datas) {
		super(context, datas);
	}
	
	public void setSelection(int index){
		this.selectIndex = index;
	}
	
	public void setData(List<String> datas){
		this.datas = datas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
        } else {
        	viewHolder = (ViewHolder) convertView.getTag();
        }
		
		String string = datas.get(position);
		viewHolder.setData(string, position, convertView);
		
		return viewHolder.getConvertView();
	}
	
	class ViewHolder extends BaseHolder<String>{

		private TextView textView;
		
		@Override
		protected View initView() {
			View view = View.inflate(context,R.layout.item_gridview, null);
			textView = (TextView) view.findViewById(R.id.item_gridview_textview);
			return view;
		}

		@Override
		protected void refreshView(String data, int position, View convertView) {
			if( position == selectIndex ){
				textView.setTextColor(Color.parseColor("#ff4400"));
			}else{
				textView.setTextColor(Color.parseColor("#333333"));
			}
			textView.setText(datas.get(position));
		}
	}

}
