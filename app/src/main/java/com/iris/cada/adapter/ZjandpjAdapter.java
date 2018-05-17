package com.iris.cada.adapter;

import java.util.List;

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

/**
 * @author bitch-1
 *
 */
/**
 * @author bitch-1
 *
 */
public class ZjandpjAdapter extends BaseAdapter {
	private Context context;
	private List<IViewOperateReport> zjlist;// 总计集合
	private List<IViewOperateReport> pjlist;// 平均集合

	public ZjandpjAdapter(Context context, List<IViewOperateReport> zjlist, List<IViewOperateReport> pjlist) {
		super();
		this.context = context;
		this.zjlist = zjlist;
		this.pjlist = pjlist;
	}
	
   public void notity(List<IViewOperateReport> zjlist,List<IViewOperateReport> pjlist) {
	this.zjlist=zjlist;
	this.pjlist=pjlist;
	notifyDataSetChanged();
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
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.item_operoter, null);
			viewHolder.salesconsultant = (TextView) convertView.findViewById(R.id.item_oper_name);
			viewHolder.newpotential = (TextView) convertView.findViewById(R.id.item_oper_name1);
			viewHolder.firstDrive = (TextView) convertView.findViewById(R.id.item_oper_name2);
			viewHolder.quotation = (TextView) convertView.findViewById(R.id.item_oper_name3);
			viewHolder.indent = (TextView) convertView.findViewById(R.id.item_oper_name4);
			viewHolder.firstindent = (TextView) convertView.findViewById(R.id.item_oper_name5);
			viewHolder.Turnover = (TextView) convertView.findViewById(R.id.item_oper_name6);
			viewHolder.ll_item=(LinearLayout) convertView.findViewById(R.id.item_oper_linner);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (zjlist != null && zjlist.size() != 0 && pjlist != null && pjlist.size() != 0) {
			if (position == 0) {
				viewHolder.salesconsultant.setText("总计");
				viewHolder.newpotential.setText(zjlist.get(0).getXinpotential());
				viewHolder.firstDrive.setText(zjlist.get(0).getFirstDrive());
				viewHolder.quotation.setText(zjlist.get(0).getQuotation());
				viewHolder.indent.setText(zjlist.get(0).getIndent());
				viewHolder.firstindent.setText(zjlist.get(0).getFirstindent());
				viewHolder.Turnover.setText(zjlist.get(0).getTurnover());
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
			} else if (position == 1) {
				viewHolder.salesconsultant.setText("平均");
				viewHolder.newpotential.setText(pjlist.get(0).getXinpotential());
				viewHolder.firstDrive.setText(pjlist.get(0).getFirstDrive());
				viewHolder.quotation.setText(pjlist.get(0).getQuotation());
				viewHolder.indent.setText(pjlist.get(0).getIndent());
				viewHolder.firstindent.setText(pjlist.get(0).getFirstindent());
				viewHolder.Turnover.setText(pjlist.get(0).getTurnover());
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#EBEBEB"));
				
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
		// 首次进店试乘试驾
		private TextView firstDrive;
		// 报价数
		private TextView quotation;
		// 成交数
		private TextView indent;
		// 首次进店成交数
		private TextView firstindent;
		// 总交车数
		private TextView Turnover;
		//总的布局
		private LinearLayout ll_item;
	}

}
