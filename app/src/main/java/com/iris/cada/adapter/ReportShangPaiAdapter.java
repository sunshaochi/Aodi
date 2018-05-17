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

public class ReportShangPaiAdapter extends CommonAdapter<IViewProfitReport>{
	
	private Context context;
	private List<IViewProfitReport> datas;

	public ReportShangPaiAdapter(Context context, List<IViewProfitReport> datas) {
		super(context, datas);
		this.context = context;
		this.datas = datas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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
		if (!ProfitApplication.isConsultantMode) {
			viewHolder.salesconsultant.setText(datas.get(position).getModels());
		}else {
			viewHolder.salesconsultant.setText(datas.get(position).getSalesconsultant());
		}
		viewHolder.Turnover.setText(datas.get(position).getTurnover());
		viewHolder.shangPaiCount.setText(datas.get(position).getOnCardSaleNum());
		viewHolder.shangPaiShenTouRate.setText(datas.get(position).getOnCardPermeaterate()+"%");
		viewHolder.shangPaiTotalMaoLi.setText(StringUtils.intDivTo0(datas.get(position).getOnCardTotalProfit()));
		viewHolder.shangPaiMaoLiRate.setText(StringUtils.nullTo0(datas.get(position).getOnCardGrossrate())+"%");
		viewHolder.oneShangPaiMaoLi.setText(StringUtils.intDivTo0(datas.get(position).getAvgOnCardGross()));
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
