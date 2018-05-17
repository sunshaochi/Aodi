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
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReportFineAdapter extends CommonAdapter<IViewProfitReport> {

	private Context context;
	private List<IViewProfitReport> datas;

	public ReportFineAdapter(Context context, List<IViewProfitReport> datas) {
		super(context, datas);
		this.context = context;
		this.datas = datas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_report_fine, null);
			viewHolder.salesconsultant = (TextView) convertView.findViewById(R.id.item_fine_name);
			viewHolder.Turnover = (TextView) convertView.findViewById(R.id.item_fine_name1);
			viewHolder.bountiqueSaleNum = (TextView) convertView.findViewById(R.id.item_fine_name2);
			viewHolder.bountiqueAddrate = (TextView) convertView.findViewById(R.id.item_fine_name3);
			viewHolder.bountiqueGross = (TextView) convertView.findViewById(R.id.item_fine_name4);//总毛利
			viewHolder.bountiqueGrossrate = (TextView) convertView.findViewById(R.id.item_fine_name5);
			viewHolder.avgbountiqueGross = (TextView) convertView.findViewById(R.id.item_fine_name6);//平均精品毛利
			viewHolder.ll_item=(LinearLayout) convertView.findViewById(R.id.item_fine_linner);
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
		viewHolder.bountiqueSaleNum.setText(datas.get(position).getBountiqueSaleNum());
		viewHolder.bountiqueAddrate.setText(datas.get(position).getBountiqueAddrate()+"%");
		viewHolder.bountiqueGross.setText(StringUtils.intDivTo0(datas.get(position).getBountiqueGross()));
		viewHolder.bountiqueGrossrate.setText(datas.get(position).getBountiqueGrossrate()+"%");
		viewHolder.avgbountiqueGross.setText(StringUtils.intDivTo0(datas.get(position).getAvgbountiqueGross()));
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
		// 精品销售数
		private TextView bountiqueSaleNum;
		// 精品加装率
		private TextView bountiqueAddrate;
		// 精品总毛利
		private TextView bountiqueGross;
		// 精品毛利率
		private TextView bountiqueGrossrate;
		// 平均单车精品毛利
		private TextView avgbountiqueGross;
		
		private LinearLayout ll_item;
	}
}
