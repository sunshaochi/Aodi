package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.entity.Check;
import com.iris.cada.entity.IViewInputHistory;
import com.iris.cada.utils.CommonAdapter;
import com.iris.cada.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ImportRecordAdapter extends CommonAdapter<IViewInputHistory> {
	private List<IViewInputHistory> data;

	public ImportRecordAdapter(Context context, List<IViewInputHistory> data) {
		super(context, data);
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(this.context).inflate(R.layout.item_import_record, null);
			viewHolder.ImportDate = (TextView) convertView.findViewById(R.id.item_extend_name);
			viewHolder.SalesProfit = (TextView) convertView.findViewById(R.id.item_extend_name1);
			viewHolder.PassengerFlow = (TextView) convertView.findViewById(R.id.item_extend_name2);
			viewHolder.tv_kc = (TextView) convertView.findViewById(R.id.item_extend_name3);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.ImportDate.setText(data.get(position).getDate());
		if (data.get(position).getInputProfitState().equals("0")) {
			viewHolder.SalesProfit.setText("x");
			viewHolder.SalesProfit.setTextColor(Color.parseColor("#ff4400"));
		} else {
			viewHolder.SalesProfit.setText("√");
			viewHolder.SalesProfit.setTextColor(Color.parseColor("#79be0c"));
		}
		if (data.get(position).getInputReceptionState().equals("0")) {
			viewHolder.PassengerFlow.setText("x");
			viewHolder.PassengerFlow.setTextColor(Color.parseColor("#ff4400"));
		} else {
			viewHolder.PassengerFlow.setText("√");
			viewHolder.PassengerFlow.setTextColor(Color.parseColor("#79be0c"));
		}
		if (data.get(position).getInputRepertoryState().equals("0")) {//导入
			viewHolder.tv_kc.setText("x");
			viewHolder.tv_kc.setTextColor(Color.parseColor("#ff4400"));
		} else {
			viewHolder.tv_kc.setText("√");
			viewHolder.tv_kc.setTextColor(Color.parseColor("#79be0c"));
		}
		return convertView;
	}

	private static class ViewHolder {
		// 导入日期
		private TextView ImportDate;
		// 销售利润
		private TextView SalesProfit;
		// 展厅客流
		private TextView PassengerFlow;
		
		// 库存
		private TextView tv_kc;
	}
}
