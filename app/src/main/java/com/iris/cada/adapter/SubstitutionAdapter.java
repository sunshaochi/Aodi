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

public class SubstitutionAdapter extends CommonAdapter<IViewProfitReport> {
	private Context context;
	private List<IViewProfitReport> datas;

	public SubstitutionAdapter(Context context, List<IViewProfitReport> fineProfitdata) {
		super(context, fineProfitdata);
		this.context = context;
		this.datas = fineProfitdata;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_substi, null);
			viewHolder.salesconsultant = (TextView) convertView.findViewById(R.id.item_report_substi_name);
			viewHolder.Turnover = (TextView) convertView.findViewById(R.id.item_report_substi_name1);
			viewHolder.permuteSaleNum = (TextView) convertView.findViewById(R.id.item_report_substi_name2);
			viewHolder.permuterate = (TextView) convertView.findViewById(R.id.item_report_substi_name3);
			viewHolder.permuterebate = (TextView) convertView.findViewById(R.id.item_report_substi_name4);
			viewHolder.avgpermuteGross = (TextView) convertView.findViewById(R.id.item_report_substi_name5);
			viewHolder.ll_item=(LinearLayout) convertView.findViewById(R.id.item_report_substi_linner);
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
		viewHolder.permuteSaleNum.setText(datas.get(position).getPermuteSaleNum());
		viewHolder.permuterate.setText(datas.get(position).getPermuterate()+"%");
		viewHolder.permuterebate.setText(StringUtils.intDivTo0(datas.get(position).getPermuterebate()));
		viewHolder.avgpermuteGross.setText(StringUtils.intDivTo0(datas.get(position).getAvgpermuteGross()));
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
		// 置换数
		private TextView permuteSaleNum;
		// 置换率
		private TextView permuterate;
		// 置换返利
		private TextView permuterebate;
		// 平均单车置换返利
		private TextView avgpermuteGross;
		
		private LinearLayout ll_item;
	}
}
