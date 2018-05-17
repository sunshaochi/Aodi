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

public class YbAdapter extends BaseAdapter{
	private Context context;
	private List<IViewProfitReport> zjlist;// 总计集合
	private List<IViewProfitReport> pjlist;// 平均集合
	
	
	public YbAdapter(Context context, List<IViewProfitReport> zjlist, List<IViewProfitReport> pjlist) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_report_extend, null);
			viewHolder.salesconsultant = (TextView) convertView.findViewById(R.id.item_extend_name);
			viewHolder.Turnover = (TextView) convertView.findViewById(R.id.item_extend_name1);
			viewHolder.lastInsuranceSaleNum = (TextView) convertView.findViewById(R.id.item_extend_name2);
			viewHolder.lastInsurancepermeaterate = (TextView) convertView.findViewById(R.id.item_extend_name3);
			viewHolder.lastInsuranceIncome = (TextView) convertView.findViewById(R.id.item_extend_name4);
			viewHolder.avglastInsuranceGross = (TextView) convertView.findViewById(R.id.item_extend_name6);
			viewHolder.lastInsuranceMaoLiRate = (TextView) convertView.findViewById(R.id.item_extend_name5);//毛利率
			viewHolder.ll_item=(LinearLayout) convertView.findViewById(R.id.item_extend_linner);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (zjlist != null && zjlist.size() != 0 && pjlist != null && pjlist.size() != 0) {
			if (position == 0) {
				viewHolder.salesconsultant.setText("总计");
				viewHolder.Turnover.setText(zjlist.get(0).getTurnover());
				viewHolder.lastInsuranceSaleNum.setText(zjlist.get(0).getLastInsuranceSaleNum());
//				viewHolder.lastInsurancepermeaterate.setText(zjlist.get(0).getLastInsurancepermeaterate()+"%");
//				viewHolder.lastInsurancepermeaterate.setText("-");
				viewHolder.lastInsurancepermeaterate.setText(pjlist.get(0).getLastInsurancepermeaterate()+"%");
				viewHolder.lastInsuranceIncome.setText(StringUtils.intDivTo0(zjlist.get(0).getLastInsuranceIncome()));
//				viewHolder.avglastInsuranceGross.setText(StringUtils.intDivTo0(zjlist.get(0).getAvglastInsuranceGross()));
//				viewHolder.avglastInsuranceGross.setText("-");
				viewHolder.avglastInsuranceGross.setText(StringUtils.intDivTo0(pjlist.get(0).getAvglastInsuranceGross()));
//				viewHolder.lastInsuranceMaoLiRate.setText(StringUtils.nullTo0(zjlist.get(0).getLastInsuranceGrossrate())+"%");
//				viewHolder.lastInsuranceMaoLiRate.setText("-");
				viewHolder.lastInsuranceMaoLiRate.setText(StringUtils.nullTo0(pjlist.get(0).getLastInsuranceGrossrate())+"%");
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
			}else if (position == 1) {
				viewHolder.salesconsultant.setText("平均");
				viewHolder.Turnover.setText(pjlist.get(0).getTurnover());
				viewHolder.lastInsuranceSaleNum.setText(pjlist.get(0).getLastInsuranceSaleNum());
				viewHolder.lastInsurancepermeaterate.setText("-");
//				viewHolder.lastInsurancepermeaterate.setText(pjlist.get(0).getLastInsurancepermeaterate()+"%");
				viewHolder.lastInsuranceIncome.setText(StringUtils.intDivTo0(pjlist.get(0).getLastInsuranceIncome()));
				viewHolder.avglastInsuranceGross.setText("-");
//				viewHolder.avglastInsuranceGross.setText(StringUtils.intDivTo0(pjlist.get(0).getAvglastInsuranceGross()));
				viewHolder.lastInsuranceMaoLiRate.setText("-");
//				viewHolder.lastInsuranceMaoLiRate.setText(StringUtils.nullTo0(pjlist.get(0).getLastInsuranceGrossrate())+"%");
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
		// 延保销售数
		private TextView lastInsuranceSaleNum;
		// 延保渗透率
		private TextView lastInsurancepermeaterate;
		// 延保总毛利
		private TextView lastInsuranceIncome;
		//延保毛利率
		private TextView lastInsuranceMaoLiRate;
		// 平均单车延保毛利
		private TextView avglastInsuranceGross;
		
		private LinearLayout ll_item;
	}


}
