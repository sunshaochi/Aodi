package com.iris.cada.adapter;

import java.util.List;

import com.iris.foundation.utils.CommonAdapter;
import com.iris.foundation.utils.CommonHolder;
import com.iris.cada.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ListViewAdapter extends CommonAdapter<String> {

	public int res;
	private List<String> list;

	public ListViewAdapter(Context context, List<String> datas) {
		super(context, datas);
	}

	public ListViewAdapter(Context context, List<String> datas, int res) {
		super(context, datas);
		this.res = res;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommonHolder holder = CommonHolder.get(context, convertView, parent, res, position);
		if (res == R.layout.list_child_item) {
			holder.setText(R.id.name, datas.get(position));
			if (position != 0) {
				holder.setText(R.id.tv_stock_number_count, +position + "台");
			} else {
				holder.setText(R.id.tv_stock_number_count, "没有库存");
			}
		}
		if (res == R.layout.item_stock_listview) {
			holder.setText(R.id.tv_vin, "LSVAM4187C218484" + 6 + position);
			holder.setText(R.id.tv_appearance, "黑色");
			holder.setText(R.id.tv_interior, "米色");
			holder.setText(R.id.tv_state, "到店");
			// holder.setText(R.id.tv_date, "2016/5/2" + position);
		}
		if (res == R.layout.item_customlevel) {
			holder.setText(R.id.tv_name, datas.get(position));
		}
		return holder.getConvertView();
	}
}
