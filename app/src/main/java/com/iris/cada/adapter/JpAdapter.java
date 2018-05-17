package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.ProfitApplication;
import com.iris.cada.entity.IViewOperateReport;
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

public class JpAdapter extends BaseAdapter{
	private Context context;
	private List<IViewProfitReport> zjlist;// 总计集合
	private List<IViewProfitReport> pjlist;// 平均集合
	
	
	public JpAdapter(Context context, List<IViewProfitReport> zjlist, List<IViewProfitReport> pjlist) {
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
		if (zjlist != null && zjlist.size() != 0 && pjlist != null && pjlist.size() != 0) {
			if (position == 0) {
				viewHolder.salesconsultant.setText("总计");
				viewHolder.Turnover.setText(zjlist.get(0).getTurnover());
				viewHolder.bountiqueSaleNum.setText(zjlist.get(0).getBountiqueSaleNum());

//				viewHolder.bountiqueAddrate.setText("-");
				viewHolder.bountiqueAddrate.setText(pjlist.get(0).getBountiqueAddrate()+"%");
				viewHolder.bountiqueGross.setText(StringUtils.intDivTo0(zjlist.get(0).getBountiqueGross()));

//				viewHolder.bountiqueGrossrate.setText("-");
				viewHolder.bountiqueGrossrate.setText(pjlist.get(0).getBountiqueGrossrate()+"%");
				
//				viewHolder.avgbountiqueGross.setText("-");
				viewHolder.avgbountiqueGross.setText(StringUtils.intDivTo0(pjlist.get(0).getAvgbountiqueGross()));
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
			}else if (position == 1) {
				viewHolder.salesconsultant.setText("平均");
				viewHolder.Turnover.setText(pjlist.get(0).getTurnover());
				viewHolder.bountiqueSaleNum.setText(pjlist.get(0).getBountiqueSaleNum());
				viewHolder.bountiqueAddrate.setText("-");
//				viewHolder.bountiqueAddrate.setText(pjlist.get(0).getBountiqueAddrate()+"%");
				
				viewHolder.bountiqueGross.setText(StringUtils.intDivTo0(pjlist.get(0).getBountiqueGross()));
				viewHolder.bountiqueGrossrate.setText("-");
//              viewHolder.bountiqueGrossrate.setText(pjlist.get(0).getBountiqueGrossrate()+"%");
				viewHolder.avgbountiqueGross.setText("-");
//				viewHolder.avgbountiqueGross.setText(StringUtils.intDivTo0(pjlist.get(0).getAvgbountiqueGross()));
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
