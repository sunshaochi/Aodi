package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.ProfitApplication;
import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.utils.CommonAdapter;
import com.iris.cada.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OperatePopuAdapter extends CommonAdapter<IViewOperateReport> {

	private Context context;
	private List<IViewOperateReport> datas;

	public OperatePopuAdapter(Context context, List<IViewOperateReport> datas) {
		super(context, datas);
		this.context = context;
		this.datas = datas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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
			viewHolder.ll_item=(LinearLayout) convertView.findViewById(R.id.item_oper_linner);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (!ProfitApplication.isConsultantMode) {
			viewHolder.salesconsultant.setText(datas.get(position).getModels());
		}else {
			viewHolder.salesconsultant.setText(datas.get(position).getSalesconsultant());
		}
		viewHolder.newpotential.setText(datas.get(position).getXinpotential());
		viewHolder.firstDrive.setText(datas.get(position).getFirstDrive());
		viewHolder.quotation.setText(datas.get(position).getQuotation());
		viewHolder.indent.setText(datas.get(position).getIndent());
		viewHolder.firstindent.setText(datas.get(position).getFirstindent());
		viewHolder.Turnover.setText(datas.get(position).getTurnover());
		if(position%2==0){
			viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
		}else {
			viewHolder.ll_item.setBackgroundColor(Color.parseColor("#EBEBEB"));
		}
		return convertView;
	}

	private static class ViewHolder {
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
		
		//总的布局
		private LinearLayout ll_item;
	}
	public void ClearData(){
		datas.clear();
	}
}
