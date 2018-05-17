package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.adapter.ZjandpjAdapter.ViewHolder;
import com.iris.cada.entity.IViewOperateReport;
import com.iris.cada.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 报表运营总体里面第二个适配器
 * **/
public class ExhiZpAdapter extends BaseAdapter{
	private Context context;
	private List<IViewOperateReport> zjlist;// 总计集合
	private List<IViewOperateReport> pjlist;// 平均集合
	
	

	public ExhiZpAdapter(Context context, List<IViewOperateReport> zjlist, List<IViewOperateReport> pjlist) {
		super();
		this.context = context;
		this.zjlist = zjlist;
		this.pjlist = pjlist;
	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return zjlist.size()+pjlist.size();
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
		if (zjlist != null && zjlist.size() != 0 && pjlist != null && pjlist.size() != 0) {
			if (position == 0) {
				viewHolder.salesconsultant.setText("总计");
				viewHolder.receivesum.setText(zjlist.get(0).getReceivesum());
				viewHolder.newpotential.setText(zjlist.get(0).getXinpotential());
				Log.e("recevise", zjlist.get(0).getReceivesum());
				viewHolder.again.setText(zjlist.get(0).getAgain());
				viewHolder.month.setText(zjlist.get(0).getMonth());
				viewHolder.active.setText(zjlist.get(0).getActive());
				viewHolder.dormant.setText(zjlist.get(0).getDormant());
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
			} else if (position == 1) {
				viewHolder.salesconsultant.setText("平均");
				viewHolder.receivesum.setText(pjlist.get(0).getReceivesum());
				viewHolder.newpotential.setText(pjlist.get(0).getXinpotential());
				Log.e("recevise", pjlist.get(0).getReceivesum());
				viewHolder.again.setText(pjlist.get(0).getAgain());
				viewHolder.month.setText(pjlist.get(0).getMonth());
				viewHolder.active.setText(pjlist.get(0).getActive());
				viewHolder.dormant.setText(pjlist.get(0).getDormant());
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ebebeb"));
			}
		} 
		         return convertView;
	}
	
	class ViewHolder {
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
