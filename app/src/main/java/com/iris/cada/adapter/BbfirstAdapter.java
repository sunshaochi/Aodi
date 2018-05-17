package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.adapter.BbpjzjAdapter.ViewHolder;
import com.iris.cada.entity.BbkcBean;
import com.iris.cada.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BbfirstAdapter extends BaseAdapter{
	private Context context;
	private List<BbkcBean>list;
	
	

	public BbfirstAdapter(Context context, List<BbkcBean> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_operoter, null);
			viewHolder.salesconsultant = (TextView) convertView.findViewById(R.id.item_oper_name);
			viewHolder.newpotential = (TextView) convertView.findViewById(R.id.item_oper_name1);
			viewHolder.firstDrive = (TextView) convertView.findViewById(R.id.item_oper_name2);
			viewHolder.quotation = (TextView) convertView.findViewById(R.id.item_oper_name3);
			viewHolder.indent = (TextView) convertView.findViewById(R.id.item_oper_name4);
			viewHolder.firstindent = (TextView) convertView.findViewById(R.id.item_oper_name5);
			viewHolder.Turnover = (TextView) convertView.findViewById(R.id.item_oper_name6);
			viewHolder.firstindent.setVisibility(View.GONE);
			viewHolder.Turnover.setVisibility(View.GONE);
			viewHolder.ll_item=(LinearLayout) convertView.findViewById(R.id.item_oper_linner);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		Log.e("重新过来", list.toString());
		if (list!=null&&list.size()>0) {
			
				viewHolder.salesconsultant.setText(list.get(position).getCarType());
				viewHolder.newpotential.setText(list.get(position).getZtsl());
				viewHolder.firstDrive.setText(list.get(position).getZksl());
				viewHolder.quotation.setText(list.get(position).getKcsl());
				viewHolder.indent.setText(list.get(position).getPjkcts());
					
		} 
		if(position%2==0){
			viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
		}else {
			viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ebebeb"));
		}
		return convertView;
	}
	
	class ViewHolder {
		// 销售顾问
		private TextView salesconsultant;
		// 车系
		private TextView models;
		// 新增潜客
		private TextView newpotential;
		// 首次进店试乘试驾
		private TextView firstDrive;
		// 报价数
		private TextView quotation;
		// 成交数
		private TextView indent;
		// 首次进店成交数
		private TextView firstindent;
		// 总交车数
		private TextView Turnover;
		
		private LinearLayout ll_item;
	}

}
