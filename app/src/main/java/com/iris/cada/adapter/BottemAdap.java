package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.entity.GlkcIfBean;
import com.iris.cada.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BottemAdap extends BaseAdapter{
	private Context context;
	private List<GlkcIfBean>list;
	
	private String ntjd,ntcj,ntkc,ntdc;
	private String nojd,nocj,nokc,nodc;

	public BottemAdap(Context context, List<GlkcIfBean> list) {
		super();
		this.context = context;
		this.list = list;
		setBasedate(list);
	}

	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHodle hodle;
		if(convertView==null){					
		convertView=View.inflate(context, R.layout.item_bottem, null);
		hodle=new ViewHodle();
		hodle.tv_compare=(TextView) convertView.findViewById(R.id.tv_compare);
		hodle.tv_close=(TextView) convertView.findViewById(R.id.tv_close);
		hodle.tv_customer=(TextView) convertView.findViewById(R.id.tv_customer);
		hodle.tv_storage=(TextView) convertView.findViewById(R.id.tv_storage);
		hodle.tv_gp3=(TextView) convertView.findViewById(R.id.tv_gp3);
		hodle.ll_item=(LinearLayout) convertView.findViewById(R.id.ll_item);
		convertView.setTag(hodle);
		}else {
			hodle=(ViewHodle) convertView.getTag();
		}
		if(list!=null&&list.size()>0){
			hodle.tv_compare.setText(list.get(position).getCompare());
			hodle.tv_close.setText(list.get(position).getCustomerNum());//接待			
			hodle.tv_customer.setText(list.get(position).getCloseRate()+"%");//成交
			hodle.tv_storage.setText(list.get(position).getStorageDaysNum());
			hodle.tv_gp3.setText(list.get(position).getGp3());
			if(position%2==0){
				hodle.ll_item.setBackgroundColor(Color.parseColor("#EBEBEB"));
			}else {
				hodle.ll_item.setBackgroundColor(Color.parseColor("#ffffffff"));
			}
			
			if(list.get(position).getCompare().equals("本月至今")){
				setDrawable(hodle.tv_close,list.get(position).getCustomerNum(),nojd);
				setDrawable(hodle.tv_customer,list.get(position).getCloseRate(),nocj);
				setgpDrawable(hodle.tv_storage,list.get(position).getStorageDaysNum(),nokc);	
				setDrawable(hodle.tv_gp3,list.get(position).getGp3(),nodc);
			}else if(list.get(position).getCompare().equals("N-1月环比")){
				setDrawable(hodle.tv_close,list.get(position).getCustomerNum(),ntjd);
				setDrawable(hodle.tv_customer,list.get(position).getCloseRate(),ntcj);
				setgpDrawable(hodle.tv_storage,list.get(position).getStorageDaysNum(),ntkc);		
				setDrawable(hodle.tv_gp3,list.get(position).getGp3(),ntdc);
			}else if(list.get(position).getCompare().equals("N-2月环比")){
				hodle.tv_close.setCompoundDrawables(null, null, null, null);
				hodle.tv_close.setCompoundDrawables(null, null, null, null);
				hodle.tv_close.setCompoundDrawables(null, null, null, null);
				hodle.tv_close.setCompoundDrawables(null, null, null, null);
			}
		}else {
			hodle.tv_compare.setText("暂无数据");
			hodle.tv_close.setText("");//接待			
			hodle.tv_customer.setText("");//成交
			hodle.tv_storage.setText("");
			hodle.tv_gp3.setText("");
			if(position%2==0){
				hodle.ll_item.setBackgroundColor(Color.parseColor("#EBEBEB"));
			}else {
				hodle.ll_item.setBackgroundColor(Color.parseColor("#ffffffff"));
			}
		}
		
		return convertView;
	}
	
	

	



	class ViewHodle {
		private TextView tv_compare,tv_close,tv_customer,tv_storage,tv_gp3;
		private LinearLayout ll_item;
	}
	
	private void setBasedate(List<GlkcIfBean> list2) {
		// TODO Auto-generated method stub
		for(int i=0;i<list2.size();i++){
			if(list2.get(i).getCompare().equals("N-2月环比")){
				ntjd=list2.get(i).getCustomerNum();
				ntcj=list2.get(i).getCloseRate();
				ntkc=list2.get(i).getStorageDaysNum();
				ntdc=list2.get(i).getGp3();
			}else if(list2.get(i).getCompare().equals("N-1月环比")){
				nojd=list2.get(i).getCustomerNum();
				nocj=list2.get(i).getCloseRate();
				nokc=list2.get(i).getStorageDaysNum();
				nodc=list2.get(i).getGp3();
			}
		}
	}
	
	
	private void setDrawable(TextView tv_close, String customerNum, String nojd2) {
		// TODO Auto-generated method stub
		customerNum=customerNum.replace(",", "");
		nojd2=nojd2.replace(",", "");
		
		Integer nubmerInt1 = Integer.parseInt(customerNum);
		Integer numberInt2 = Integer.parseInt(nojd2);
		if (nubmerInt1 < numberInt2) {
			Drawable drawable = context.getResources().getDrawable(R.drawable.down); 
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()) ; 
			tv_close.setCompoundDrawables(null, null, drawable, null)  ;
			tv_close.setCompoundDrawablePadding(5);
		} else {
			Drawable drawable = context.getResources().getDrawable(R.drawable.upgreen); 
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()) ; 
			tv_close.setCompoundDrawables(null, null, drawable, null)  ;
			tv_close.setCompoundDrawablePadding(5);
		}
	}
	
	private void setgpDrawable(TextView tv_gp3, String gp3, String nodc2) {
		gp3=gp3.replace(",", "");
		nodc2=nodc2.replace(",", "");
		
		Integer nubmerInt1 = Integer.parseInt(gp3);
		Integer numberInt2 = Integer.parseInt(nodc2);
		if (nubmerInt1 > numberInt2) {
			Drawable drawable = context.getResources().getDrawable(R.drawable.redup); 
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()) ; 
			tv_gp3.setCompoundDrawables(null, null, drawable, null)  ;
			tv_gp3.setCompoundDrawablePadding(5);
		} else if(nubmerInt1 < numberInt2){
			Drawable drawable = context.getResources().getDrawable(R.drawable.greendown); 
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()) ; 
			tv_gp3.setCompoundDrawables(null, null, drawable, null)  ;
			tv_gp3.setCompoundDrawablePadding(5);
		}else if(nubmerInt1 == numberInt2){
//			Drawable drawable = context.getResources().getDrawable(R.drawable.greendown); 
//			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()) ; 
			tv_gp3.setCompoundDrawables(null, null, null, null)  ;
			tv_gp3.setCompoundDrawablePadding(5);
		}
		
	}

}
