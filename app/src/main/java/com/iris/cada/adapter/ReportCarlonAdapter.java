package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.ProfitApplication;
import com.iris.cada.entity.ILogData;
import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.CommonAdapter;
import com.iris.cada.utils.StringUtils;
import com.iris.foundation.utils.CommonHolder;
import com.iris.cada.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReportCarlonAdapter extends CommonAdapter<IViewProfitReport> {
	private Context context;
	private List<IViewProfitReport> datas;

	public ReportCarlonAdapter(Context context, List<IViewProfitReport> datas) {
		super(context, datas);
		this.context = context;
		this.datas = datas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_report_carlon, null);
			viewHolder.salesconsultant = (TextView) convertView.findViewById(R.id.item_carlon_name);
			viewHolder.Turnover = (TextView) convertView.findViewById(R.id.item_carlon_name1);
			viewHolder.carloanSaleNum = (TextView) convertView.findViewById(R.id.item_carlon_name2);
			viewHolder.carloanpermeaterate = (TextView) convertView.findViewById(R.id.item_carlon_name3);
			viewHolder.carloanfees = (TextView) convertView.findViewById(R.id.item_carlon_name4);
			viewHolder.carloanMaoLiRate = (TextView) convertView.findViewById(R.id.item_carlon_name5);//毛利率
			viewHolder.avgcarloanGross = (TextView) convertView.findViewById(R.id.item_carlon_name6);
			viewHolder.ll_item=(LinearLayout) convertView.findViewById(R.id.item_carlon_linner);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (!ProfitApplication.isConsultantMode) {
			viewHolder.salesconsultant.setText(datas.get(position).getModels());
		}else {
			viewHolder.salesconsultant.setText(datas.get(position).getSalesconsultant());
		}
		viewHolder.Turnover.setText(datas.get(position).getTurnover());
		viewHolder.carloanSaleNum.setText(datas.get(position).getCarloanSaleNum());
		viewHolder.carloanpermeaterate.setText(datas.get(position).getCarloanpermeaterate()+"%");
		viewHolder.carloanfees.setText(StringUtils.intDivTo0(datas.get(position).getCarloanfees()));
		viewHolder.avgcarloanGross.setText(StringUtils.intDivTo0(datas.get(position).getAvgcarloanGross()));
		viewHolder.carloanMaoLiRate.setText(StringUtils.nullTo0(datas.get(position).getCarloanGrossrate())+"%");//毛利率
		if(position%2==0){
			viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
		}else{
			viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ebebeb"));
		}
		return convertView;
	}

	private static class ViewHolder {
		// 销售顾问
		private TextView salesconsultant;
		// 车系
		private TextView models;
		// 总交车数
		private TextView Turnover;
		// 车贷销售数
		private TextView carloanSaleNum;
		// 车贷渗透率
		private TextView carloanpermeaterate;
		// 车贷总毛利
		private TextView carloanfees;
		//车贷毛利率
		private TextView carloanMaoLiRate;
		// 平均单车车贷毛利
		private TextView avgcarloanGross;
		
		private LinearLayout ll_item;
	}

}
