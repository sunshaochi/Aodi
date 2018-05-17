package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.StringUtils;
import com.iris.cada.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CdAdapter extends BaseAdapter{
	private Context context;
	private List<IViewProfitReport> zjlist;// 总计集合
	private List<IViewProfitReport> pjlist;// 平均集合
	
	
	public CdAdapter(Context context, List<IViewProfitReport> zjlist, List<IViewProfitReport> pjlist) {
		super();
		this.context = context;
		this.zjlist = zjlist;
		this.pjlist = pjlist;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return zjlist.size() + pjlist.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
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
		if (zjlist != null && zjlist.size() != 0 && pjlist != null && pjlist.size() != 0) {
			if (position == 0) {
				viewHolder.salesconsultant.setText("总计");
				viewHolder.Turnover.setText(zjlist.get(0).getTurnover());
				viewHolder.carloanSaleNum.setText(zjlist.get(0).getCarloanSaleNum());
//				viewHolder.carloanpermeaterate.setText(zjlist.get(0).getCarloanpermeaterate()+"%");
//				viewHolder.carloanpermeaterate.setText("-");
				viewHolder.carloanpermeaterate.setText(pjlist.get(0).getCarloanpermeaterate()+"%");//渗透率
				viewHolder.carloanfees.setText(StringUtils.intDivTo0(zjlist.get(0).getCarloanfees()));
				viewHolder.avgcarloanGross.setText(StringUtils.intDivTo0(zjlist.get(0).getAvgcarloanGross()));
//				viewHolder.avgcarloanGross.setText("-");//单车贷款
//				viewHolder.carloanMaoLiRate.setText(StringUtils.nullTo0(zjlist.get(0).getCarloanGrossrate())+"%");//毛利率
//				viewHolder.carloanMaoLiRate.setText("-");
				viewHolder.carloanMaoLiRate.setText(StringUtils.nullTo0(pjlist.get(0).getCarloanGrossrate())+"%");//毛利率
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
			}else if (position == 1) {
				viewHolder.salesconsultant.setText("平均");
				viewHolder.Turnover.setText(pjlist.get(0).getTurnover());
				viewHolder.carloanSaleNum.setText(pjlist.get(0).getCarloanSaleNum());
				viewHolder.carloanpermeaterate.setText("-");
//				viewHolder.carloanpermeaterate.setText(pjlist.get(0).getCarloanpermeaterate()+"%");
				viewHolder.carloanfees.setText(StringUtils.intDivTo0(pjlist.get(0).getCarloanfees()));
//				viewHolder.avgcarloanGross.setText(StringUtils.intDivTo0(pjlist.get(0).getAvgcarloanGross()));
				viewHolder.avgcarloanGross.setText("-");
				viewHolder.carloanMaoLiRate.setText("-");
//				viewHolder.carloanMaoLiRate.setText(StringUtils.nullTo0(pjlist.get(0).getCarloanGrossrate())+"%");//毛利率
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ebebeb"));
			}
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
