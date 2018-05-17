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

public class ZhAdapter extends BaseAdapter{
	private Context context;
	private List<IViewProfitReport> zjlist;// 总计集合
	private List<IViewProfitReport> pjlist;// 平均集合
	
	
	public ZhAdapter(Context context, List<IViewProfitReport> zjlist, List<IViewProfitReport> pjlist) {
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
		if (zjlist != null && zjlist.size() != 0 && pjlist != null && pjlist.size() != 0) {
			if (position == 0) {
				viewHolder.salesconsultant.setText("总计");
				viewHolder.Turnover.setText(zjlist.get(0).getTurnover());
				viewHolder.permuteSaleNum.setText(zjlist.get(0).getPermuteSaleNum());
//				viewHolder.permuterate.setText(zjlist.get(0).getPermuterate()+"%");
//				viewHolder.permuterate.setText("-");
				viewHolder.permuterate.setText(pjlist.get(0).getPermuterate()+"%");
				viewHolder.permuterebate.setText(StringUtils.intDivTo0(zjlist.get(0).getPermuterebate()));
//				viewHolder.avgpermuteGross.setText(StringUtils.intDivTo0(zjlist.get(0).getAvgpermuteGross()));
//				viewHolder.avgpermuteGross.setText("-");
				viewHolder.avgpermuteGross.setText(StringUtils.intDivTo0(pjlist.get(0).getAvgpermuteGross()));
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
			}
			if (position == 1) {
				viewHolder.salesconsultant.setText("平均");
				viewHolder.Turnover.setText(pjlist.get(0).getTurnover());
				viewHolder.permuteSaleNum.setText(pjlist.get(0).getPermuteSaleNum());
				viewHolder.permuterate.setText("-");
//				viewHolder.permuterate.setText(pjlist.get(0).getPermuterate()+"%");
				viewHolder.permuterebate.setText(StringUtils.intDivTo0(pjlist.get(0).getPermuterebate()));
				viewHolder.avgpermuteGross.setText("-");
//				viewHolder.avgpermuteGross.setText(StringUtils.intDivTo0(pjlist.get(0).getAvgpermuteGross()));
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