package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.ProfitApplication;
import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.CommonAdapter;
import com.iris.cada.utils.StringUtils;
import com.iris.cada.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReportExtendAdapter extends CommonAdapter<IViewProfitReport> {
	public ReportExtendAdapter(Context context, List<IViewProfitReport> datas) {
		super(context, datas);
		this.context = context;
		this.datas = datas;
	}

	private Context context;
	private List<IViewProfitReport> datas;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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
		if (!ProfitApplication.isConsultantMode) {
			viewHolder.salesconsultant.setText(datas.get(position).getModels());
		}else {
			viewHolder.salesconsultant.setText(datas.get(position).getSalesconsultant());
		}
		viewHolder.Turnover.setText(datas.get(position).getTurnover());
		viewHolder.lastInsuranceSaleNum.setText(datas.get(position).getLastInsuranceSaleNum());
		viewHolder.lastInsurancepermeaterate.setText(datas.get(position).getLastInsurancepermeaterate()+"%");
		viewHolder.lastInsuranceIncome.setText(StringUtils.intDivTo0(datas.get(position).getLastInsuranceIncome()));
		viewHolder.avglastInsuranceGross.setText(StringUtils.intDivTo0(datas.get(position).getAvglastInsuranceGross()));
		viewHolder.lastInsuranceMaoLiRate.setText(StringUtils.nullTo0(datas.get(position).getLastInsuranceGrossrate())+"%");
		if(position%2==0){
			viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
		}else {
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
