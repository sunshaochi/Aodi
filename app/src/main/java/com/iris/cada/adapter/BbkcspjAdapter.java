package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.adapter.BbpjzjAdapter.ViewHolder;
import com.iris.cada.entity.BbkcBean;
import com.iris.cada.entity.BbkcsBean;
import com.iris.cada.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BbkcspjAdapter extends BaseAdapter{
	private Context context;
	private List<BbkcsBean> zjlist;// 总计集合
	private List<BbkcsBean> pjlist;// 平均集合
	
	
	

	public BbkcspjAdapter(Context context, List<BbkcsBean> zjlist, List<BbkcsBean> pjlist) {
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
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (arg1 == null) {
			viewHolder = new ViewHolder();
			arg1 = LayoutInflater.from(context).inflate(R.layout.item_operoter, null);
			viewHolder.salesconsultant = (TextView) arg1.findViewById(R.id.item_oper_name);
			viewHolder.newpotential = (TextView) arg1.findViewById(R.id.item_oper_name1);
			viewHolder.firstDrive = (TextView) arg1.findViewById(R.id.item_oper_name2);
			viewHolder.quotation = (TextView) arg1.findViewById(R.id.item_oper_name3);
			viewHolder.indent = (TextView) arg1.findViewById(R.id.item_oper_name4);
			viewHolder.firstindent = (TextView) arg1.findViewById(R.id.item_oper_name5);
			viewHolder.Turnover = (TextView) arg1.findViewById(R.id.item_oper_name6);
//			viewHolder.firstindent.setVisibility(View.GONE);
			viewHolder.Turnover.setVisibility(View.GONE);
			viewHolder.ll_item=(LinearLayout) arg1.findViewById(R.id.item_oper_linner);
			arg1.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) arg1.getTag();
		}
		if (zjlist != null && zjlist.size() != 0 && pjlist != null && pjlist.size() != 0) {
			if (arg0 == 0) {
				viewHolder.salesconsultant.setText("总计");
				viewHolder.newpotential.setText(zjlist.get(0).getCoty030());
				viewHolder.firstDrive.setText(zjlist.get(0).getCoty3160());
				viewHolder.quotation.setText(zjlist.get(0).getCoty6190());
				viewHolder.indent.setText(zjlist.get(0).getCoty91180());
				viewHolder.firstindent.setText(zjlist.get(0).getCoty181());
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ffffff"));
			} else if (arg0 == 1) {
				viewHolder.salesconsultant.setText("平均");
				viewHolder.newpotential.setText(pjlist.get(0).getCoty030());
				viewHolder.firstDrive.setText(pjlist.get(0).getCoty3160());
				viewHolder.quotation.setText(pjlist.get(0).getCoty6190());
				viewHolder.indent.setText(pjlist.get(0).getCoty91180());
				viewHolder.firstindent.setText(pjlist.get(0).getCoty181());
				viewHolder.ll_item.setBackgroundColor(Color.parseColor("#ebebeb"));
			}
		} 
		return arg1;
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
		
		private LinearLayout ll_item;
	}

}

