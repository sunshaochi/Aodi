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

public class SpAdapter extends BaseAdapter{
	private Context context;
	private List<IViewProfitReport> zjlist;// 总计集合
	private List<IViewProfitReport> pjlist;// 平均集合
	
	
	public SpAdapter(Context context, List<IViewProfitReport> zjlist, List<IViewProfitReport> pjlist) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_report_shang_pai, null);
			viewHolder.salesconsultant = (TextView) convertView.findViewById(R.id.item_shang_pai_name);
			viewHolder.Turnover = (TextView) convertView.findViewById(R.id.item_shang_pai_name1);
			viewHolder.shangPaiCount = (TextView) convertView.findViewById(R.id.item_shang_pai_name2);//上牌数
			viewHolder.shangPaiShenTouRate = (TextView) convertView.findViewById(R.id.item_shang_pai_name3);//渗透率
			viewHolder.shangPaiTotalMaoLi = (TextView) convertView.findViewById(R.id.item_shang_pai_name4);//总毛利
			viewHolder.shangPaiMaoLiRate = (TextView) convertView.findViewById(R.id.item_shang_pai_name5);//毛利率
			viewHolder.oneShangPaiMaoLi = (TextView) convertView.findViewById(R.id.item_shang_pai_name6);//单车上牌毛利
			viewHolder.ll_item=(LinearLayout) convertView.findViewById(R.id.item_shang_pai_linner);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (zjlist != null && zjlist.size() != 0 && pjlist != null && pjlist.size() != 0) {
			if (position == 0) {
				viewHolder.salesconsultant.setText("总计");
				viewHolder.Turnover.setText(zjlist.get(0).getTurnover());
				viewHolder.shangPaiCount.setText(zjlist.get(0).getOnCardSaleNum());
//				viewHolder.shangPaiShenTouRate.setText(zjlist.get(0).getOnCardPermeaterate()+"%");
//				viewHolder.shangPaiShenTouRate.setText("-");
				viewHolder.shangPaiShenTouRate.setText(pjlist.get(0).getOnCardPermeaterate()+"%");
				viewHolder.shangPaiTotalMaoLi.setText(StringUtils.intDivTo0(zjlist.get(0).getOnCardTotalProfit()));
//				viewHolder.shangPaiMaoLiRate.setText(StringUtils.nullTo0(zjlist.get(0).getOnCardGrossrate())+"%");
//				viewHolder.shangPaiMaoLiRate.setText("-");
				viewHolder.shangPaiMaoLiRate.setText(StringUtils.nullTo0(pjlist.get(0).getOnCardGrossrate())+"%");
//				viewHolder.oneShangPaiMaoLi.setText(StringUtils.intDivTo0(zjlist.get(0).getAvgOnCardGross()));
//				viewHolder.oneShangPaiMaoLi.setText("-");
				viewHolder.oneShangPaiMaoLi.setText(pjlist.get(0).getAvgOnCardGross());
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
			}else if (position == 1) {
				viewHolder.salesconsultant.setText("平均");
				viewHolder.Turnover.setText(pjlist.get(0).getTurnover());
				viewHolder.shangPaiCount.setText(pjlist.get(0).getOnCardSaleNum());
				viewHolder.shangPaiShenTouRate.setText(pjlist.get(0).getOnCardPermeaterate()+"%");
//				viewHolder.shangPaiTotalMaoLi.setText(StringUtils.intDivTo0(pjlist.get(0).getOnCardTotalProfit()));
				viewHolder.shangPaiTotalMaoLi.setText(pjlist.get(0).getOnCardTotalProfit());
				viewHolder.shangPaiMaoLiRate.setText("-");
//				viewHolder.shangPaiMaoLiRate.setText(StringUtils.nullTo0(pjlist.get(0).getOnCardGrossrate())+"%");
//				viewHolder.oneShangPaiMaoLi.setText(StringUtils.intDivTo0(pjlist.get(0).getAvgOnCardGross()));
				viewHolder.oneShangPaiMaoLi.setText("-");
//				viewHolder.oneShangPaiMaoLi.setText(pjlist.get(0).getAvgOnCardGross());
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
		//上牌数
		private TextView shangPaiCount;
		//上牌渗透率
		private TextView shangPaiShenTouRate;
		//上牌总毛利
		private TextView shangPaiTotalMaoLi;
		//上牌毛利率
		private TextView shangPaiMaoLiRate;
		//单车上牌毛利
		private TextView oneShangPaiMaoLi;
		
		private LinearLayout ll_item;
	}
}