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

public class XbAdapter  extends BaseAdapter{
	private Context context;
	private List<IViewProfitReport> zjlist;// 总计集合
	private List<IViewProfitReport> pjlist;// 平均集合
	
	
	public XbAdapter(Context context, List<IViewProfitReport> zjlist, List<IViewProfitReport> pjlist) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_report_new_insurance, null);
			viewHolder.salesconsultant = (TextView) convertView.findViewById(R.id.item_new_insu_name);
			viewHolder.Turnover = (TextView) convertView.findViewById(R.id.item_new_insu_name1);
			viewHolder.xinInsuranceSaleNum = (TextView) convertView.findViewById(R.id.item_new_insu_name2);
			viewHolder.xinInsurancepermeaterate = (TextView) convertView.findViewById(R.id.item_new_insu_name3);//渗透率
			viewHolder.xinInsuranceIncome = (TextView) convertView.findViewById(R.id.item_new_insu_name4);
			viewHolder.avgnewInsuranceGross = (TextView) convertView.findViewById(R.id.item_new_insu_name6);//单车新保毛利率
			viewHolder.xinInsuranceMaoLiRate = (TextView) convertView.findViewById(R.id.item_new_insu_name5);//毛利率
			viewHolder.ll_item=(LinearLayout) convertView.findViewById(R.id.item_new_insu_linner);
			convertView.setTag(viewHolder);
		} else {
             viewHolder=(ViewHolder) convertView.getTag();
		}
		if (zjlist != null && zjlist.size() != 0 && pjlist != null && pjlist.size() != 0) {
			if (position == 0) {
				viewHolder.salesconsultant.setText("总计");
				viewHolder.Turnover.setText(zjlist.get(0).getTurnover());
				viewHolder.xinInsuranceSaleNum.setText(zjlist.get(0).getXinInsuranceSaleNum());
//				viewHolder.xinInsurancepermeaterate.setText(zjlist.get(0).getXinInsurancepermeaterate()+"%");
//				viewHolder.xinInsurancepermeaterate.setText("-");
				viewHolder.xinInsurancepermeaterate.setText(pjlist.get(0).getXinInsurancepermeaterate()+"%");
				viewHolder.xinInsuranceIncome.setText(StringUtils.intDivTo0(zjlist.get(0).getXinInsuranceIncome()));
//				viewHolder.avgnewInsuranceGross.setText(StringUtils.intDivTo0(zjlist.get(0).getAvgxinInsuranceGross()));
//				viewHolder.avgnewInsuranceGross.setText("-");
				viewHolder.avgnewInsuranceGross.setText(StringUtils.intDivTo0(pjlist.get(0).getAvgxinInsuranceGross()));
//				viewHolder.xinInsuranceMaoLiRate.setText(StringUtils.nullTo0(zjlist.get(0).getXinInsuranceGrossrate())+"%");
//				viewHolder.xinInsuranceMaoLiRate.setText("-");
				viewHolder.xinInsuranceMaoLiRate.setText(StringUtils.nullTo0(pjlist.get(0).getXinInsuranceGrossrate())+"%");
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
			}else if (position == 1) {
				viewHolder.salesconsultant.setText("平均");
				viewHolder.Turnover.setText(pjlist.get(0).getTurnover());
				viewHolder.xinInsuranceSaleNum.setText(pjlist.get(0).getXinInsuranceSaleNum());
			    viewHolder.xinInsurancepermeaterate.setText("-");
//				viewHolder.xinInsurancepermeaterate.setText(pjlist.get(0).getXinInsurancepermeaterate()+"%");
				viewHolder.xinInsuranceIncome.setText(StringUtils.intDivTo0(pjlist.get(0).getXinInsuranceIncome()));
				viewHolder.avgnewInsuranceGross.setText("-");
//				viewHolder.avgnewInsuranceGross.setText(StringUtils.intDivTo0(pjlist.get(0).getAvgxinInsuranceGross()));
				viewHolder.xinInsuranceMaoLiRate.setText("-");
//				viewHolder.xinInsuranceMaoLiRate.setText(StringUtils.nullTo0(pjlist.get(0).getXinInsuranceGrossrate())+"%");
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

