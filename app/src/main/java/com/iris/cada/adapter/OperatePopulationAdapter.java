package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.utils.CommonAdapter;
import com.iris.foundation.utils.CommonHolder;
import com.iris.cada.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OperatePopulationAdapter extends CommonAdapter<String> {
	private Context context;
	private List<String> datas;

	public OperatePopulationAdapter(Context context, List<String> datas) {
		super(context, datas);
		this.context = context;
		this.datas = datas;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommonHolder commonHolder = CommonHolder.get(context, convertView,parent,R.layout.item_fragment_diagdetail_listview, position);
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_operate_population, null);
		    convertView.setTag(convertView);	
		}else{
			commonHolder=(CommonHolder) convertView.getTag();
			
		}
		return convertView;
	}

}
