package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.ProfitApplication;
import com.iris.cada.entity.IViewOperateReport;
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

public class ExhibitionAdapter extends CommonAdapter<IViewOperateReport> {

	private Context context;
	private List<IViewOperateReport> datas;

	public ExhibitionAdapter(Context context, List<IViewOperateReport> datas) {
		super(context, datas);
		this.context = context;
		this.datas = datas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_exhibition_hall, null);
			viewHolder.salesconsultant = (TextView) convertView.findViewById(R.id.item_exhibi_name);
			viewHolder.newpotential = (TextView) convertView.findViewById(R.id.item_exhibi_name2);
			viewHolder.receivesum = (TextView) convertView.findViewById(R.id.item_exhibi_name1);
			viewHolder.again = (TextView) convertView.findViewById(R.id.item_exhibi_name3);
			viewHolder.month = (TextView) convertView.findViewById(R.id.item_exhibi_name4);
			viewHolder.active = (TextView) convertView.findViewById(R.id.item_exhibi_name5);
			viewHolder.dormant = (TextView) convertView.findViewById(R.id.item_exhibi_name6);
			viewHolder.ll_item=(LinearLayout) convertView.findViewById(R.id.item_exhibi_linner);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (!ProfitApplication.isConsultantMode) {
			viewHolder.salesconsultant.setText(datas.get(position).getModels());
		}else{
			viewHolder.salesconsultant.setText(datas.get(position).getSalesconsultant());
		}
		viewHolder.receivesum.setText(datas.get(position).getReceivesum());
		viewHolder.newpotential.setText(datas.get(position).getXinpotential());
		Log.e("recevise", datas.get(position).getReceivesum());
		viewHolder.again.setText(datas.get(position).getAgain());
		viewHolder.month.setText(datas.get(position).getMonth());
		viewHolder.active.setText(datas.get(position).getActive());
		viewHolder.dormant.setText(datas.get(position).getDormant());
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
		// 新增潜客
		private TextView newpotential;
		// 接待总数
		private TextView receivesum;
		// 再次进店
		private TextView again;
		// 当月客户再次进店
		private TextView month;
		// 活跃再次
		private TextView active;
		// 休眠再次
		private TextView dormant;
		
		private LinearLayout ll_item;
	}
}
