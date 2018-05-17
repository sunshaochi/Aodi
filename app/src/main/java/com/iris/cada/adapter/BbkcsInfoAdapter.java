package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.adapter.BbfirstAdapter.ViewHolder;
import com.iris.cada.entity.BbkcBean;
import com.iris.cada.entity.BbkcsBean;
import com.iris.cada.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BbkcsInfoAdapter extends BaseAdapter{
	private Context context;
	private List<BbkcsBean>list;
	
	

	public BbkcsInfoAdapter(Context context, List<BbkcsBean> list) {
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
//			viewHolder.firstindent.setVisibility(View.GONE);
			viewHolder.Turnover.setVisibility(View.GONE);
			viewHolder.ll_item=(LinearLayout) convertView.findViewById(R.id.item_oper_linner);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (list!=null&&list.size()>0) {
			
				viewHolder.salesconsultant.setText(list.get(position).getCarType());
				viewHolder.newpotential.setText(list.get(position).getCoty030());
				viewHolder.firstDrive.setText(list.get(position).getCoty3160());
				viewHolder.quotation.setText(list.get(position).getCoty6190());
				viewHolder.indent.setText(list.get(position).getCoty91180());
				viewHolder.firstindent.setText(list.get(position).getCoty181());
					
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

