package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 班级圈头部popupWindow的Adapter
 * 
 * @author 郭家豪
 * @version 2015-08-11 11:33:25
 */
public class GroupAdapter extends BaseAdapter {

	private Context context;
	@SuppressWarnings("unused")
	private List<String> datas;

	private List<String> list;

	public GroupAdapter(Context context, List<String> list) {

		this.context = context;
		this.list = list;

	}

	public void setData(List<String> datas) {
		this.datas = datas;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.group_item_view, null);
			holder = new ViewHolder();

			convertView.setTag(holder);

			holder.groupItem = (TextView) convertView.findViewById(R.id.groupItem);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.groupItem.setText(list.get(position));

		return convertView;
	}

	static class ViewHolder {
		TextView groupItem;
	}
}
