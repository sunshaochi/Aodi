package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.ProfitApplication;
import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.utils.CommonAdapter;
import com.iris.cada.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdclistDataAdapter extends CommonAdapter<IViewOperateReport> {

	private Context context;
	private List<IViewOperateReport> datas;

	public AdclistDataAdapter(Context context, List<IViewOperateReport> datas) {
		super(context, datas);
		this.context = context;
		this.datas = datas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_fragment_adc, null);
			viewHolder.salesconsultant = (TextView) convertView.findViewById(R.id.item_adc_name);
			viewHolder.firstDriverate = (TextView) convertView.findViewById(R.id.item_adc_name1);
			viewHolder.againrate = (TextView) convertView.findViewById(R.id.item_adc_name2);
			viewHolder.offerrate = (TextView) convertView.findViewById(R.id.item_adc_name3);
			viewHolder.closerate = (TextView) convertView.findViewById(R.id.item_adc_name4);
			viewHolder.ll_item=(LinearLayout) convertView.findViewById(R.id.item_adc_linner);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (!ProfitApplication.isConsultantMode) {
			viewHolder.salesconsultant.setText(datas.get(position).getModels());
		}else{
			viewHolder.salesconsultant.setText(datas.get(position).getSalesconsultant());
		}
		viewHolder.firstDriverate.setText(datas.get(position).getFirstDriverate()+"%");
		viewHolder.againrate.setText(datas.get(position).getAgainrate()+"%");
		viewHolder.offerrate.setText(datas.get(position).getOfferrate()+"%");
		viewHolder.closerate.setText(datas.get(position).getCloserate()+"%");
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
		// 首次试乘试驾率
		private TextView firstDriverate;
		// 再次进店率
		private TextView againrate;
		// 报价率
		private TextView offerrate;
		// 成交率
		private TextView closerate;
		
		private LinearLayout ll_item;
	}
}
