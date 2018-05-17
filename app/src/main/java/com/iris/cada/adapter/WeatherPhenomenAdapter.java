package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.entity.IViewProfitReport;
import com.iris.cada.entity.SortModel;
import com.iris.cada.utils.CommonAdapter;
import com.iris.cada.R;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherPhenomenAdapter extends CommonAdapter<SortModel> {
	private Context context;
	private List<SortModel> list;

	public WeatherPhenomenAdapter(Context context, List<SortModel> list) {
		super(context, list);
		this.context = context;
		this.list = list;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_weather_pheno, null);
			viewHolder.name = (TextView) convertView.findViewById(R.id.item_weather_name);
			viewHolder.product = (TextView) convertView.findViewById(R.id.item_weather_peoduct);
			viewHolder.ll_item=(LinearLayout) convertView.findViewById(R.id.ll_item);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.name.setText(list.get(position).getName());
		viewHolder.product.setText(list.get(position).getLetters());
		if(position%2==0){
			viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
			viewHolder.name.setTextColor(Color.parseColor("#666666"));
			viewHolder.product.setTextColor(Color.parseColor("#666666"));
		}else {
			viewHolder.ll_item.setBackgroundColor(Color.parseColor("#EBEBEB"));
			viewHolder.name.setTextColor(Color.parseColor("#666666"));
			viewHolder.product.setTextColor(Color.parseColor("#666666"));
		}
		return convertView;
	}

	public static class ViewHolder {
		private TextView name;
		private TextView product;
		private LinearLayout ll_item;

	}
}
