package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.ProfitApplication;
import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdcZjAdpter extends BaseAdapter {

	private Context context;
	private List<IViewOperateReport> zjlist;// 总计集合
	private List<IViewOperateReport> pjlist;// 平均集合

	public AdcZjAdpter(Context context, List<IViewOperateReport> zjlist, List<IViewOperateReport> pjlist) {
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
		if (zjlist != null && zjlist.size() != 0 && pjlist != null && pjlist.size() != 0) {
			if (position == 0) {
//				viewHolder.salesconsultant.setText("总计");
//				viewHolder.firstDriverate.setText(zjlist.get(0).getFirstDriverate() + "%");
//				viewHolder.againrate.setText(zjlist.get(0).getAgainrate() + "%");
//				viewHolder.offerrate.setText(zjlist.get(0).getOfferrate() + "%");
//				viewHolder.closerate.setText(zjlist.get(0).getCloserate() + "%");
				viewHolder.salesconsultant.setText("总计");
//				viewHolder.firstDriverate.setText("-");
//				viewHolder.againrate.setText("-");
//				viewHolder.offerrate.setText("-");
//				viewHolder.closerate.setText("-");
				
				viewHolder.firstDriverate.setText(pjlist.get(0).getFirstDriverate() + "%");
				viewHolder.againrate.setText(pjlist.get(0).getAgainrate() + "%");
				viewHolder.offerrate.setText(pjlist.get(0).getOfferrate() + "%");
				viewHolder.closerate.setText(pjlist.get(0).getCloserate() + "%");
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
			}else if (position == 1) {
				viewHolder.salesconsultant.setText("平均");
//				viewHolder.firstDriverate.setText(pjlist.get(0).getFirstDriverate() + "%");
//				viewHolder.againrate.setText(pjlist.get(0).getAgainrate() + "%");
//				viewHolder.offerrate.setText(pjlist.get(0).getOfferrate() + "%");
//				viewHolder.closerate.setText(pjlist.get(0).getCloserate() + "%");
				viewHolder.firstDriverate.setText("-");
				viewHolder.againrate.setText("-");
				viewHolder.offerrate.setText("-");
				viewHolder.closerate.setText("-");
				
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ebebeb"));
			}
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
