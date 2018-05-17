package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.ProfitApplication;
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

public class NewinSuranceAdapter extends CommonAdapter<IViewProfitReport> {
	private Context context;
	private List<IViewProfitReport> datas;

	public NewinSuranceAdapter(Context context, List<IViewProfitReport> datas) {
		super(context, datas);
		this.context = context;
		this.datas = datas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_report_new_insurance, null);
			viewHolder.salesconsultant = (TextView) convertView.findViewById(R.id.item_new_insu_name);
			viewHolder.Turnover = (TextView) convertView.findViewById(R.id.item_new_insu_name1);
			viewHolder.xinInsuranceSaleNum = (TextView) convertView.findViewById(R.id.item_new_insu_name2);
			viewHolder.xinInsurancepermeaterate = (TextView) convertView.findViewById(R.id.item_new_insu_name3);
			viewHolder.xinInsuranceIncome = (TextView) convertView.findViewById(R.id.item_new_insu_name4);
			viewHolder.avgnewInsuranceGross = (TextView) convertView.findViewById(R.id.item_new_insu_name6);
			viewHolder.xinInsuranceMaoLiRate = (TextView) convertView.findViewById(R.id.item_new_insu_name5);//毛利率
			viewHolder.ll_item=(LinearLayout) convertView.findViewById(R.id.item_new_insu_linner);
			convertView.setTag(viewHolder);
		} else {
             viewHolder=(ViewHolder) convertView.getTag();
		}
		if (!ProfitApplication.isConsultantMode) {
			viewHolder.salesconsultant.setText(datas.get(position).getModels());
		}else {
			viewHolder.salesconsultant.setText(datas.get(position).getSalesconsultant());
		}
		viewHolder.Turnover.setText(datas.get(position).getTurnover());
		viewHolder.xinInsuranceSaleNum.setText(datas.get(position).getXinInsuranceSaleNum());
		viewHolder.xinInsurancepermeaterate.setText(datas.get(position).getXinInsurancepermeaterate()+"%");
		viewHolder.xinInsuranceIncome.setText(StringUtils.intDivTo0(datas.get(position).getXinInsuranceIncome()));
		viewHolder.avgnewInsuranceGross.setText(StringUtils.intDivTo0(datas.get(position).getAvgxinInsuranceGross()));
		viewHolder.xinInsuranceMaoLiRate.setText(StringUtils.nullTo0(datas.get(position).getXinInsuranceGrossrate())+"%");
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
		// 新保销售数
		private TextView xinInsuranceSaleNum;
		// 新保渗透率
		private TextView xinInsurancepermeaterate;
		// 新保总毛利
		private TextView xinInsuranceIncome;
		//新保毛利率
		private TextView xinInsuranceMaoLiRate;
		// 平均单车新保毛利
		private TextView avgnewInsuranceGross;
		
		private LinearLayout ll_item;
	}

}
