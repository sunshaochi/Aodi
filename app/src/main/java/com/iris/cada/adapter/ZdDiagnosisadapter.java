package com.iris.cada.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.DiagnosisProductActivity;
import com.iris.cada.activity.H5webAct;
import com.iris.cada.entity.Check;
import com.iris.cada.entity.IViewSCBean;
import com.iris.cada.entity.ShzdBean;
import com.iris.cada.utils.CKNumFormat;
import com.iris.cada.utils.CommonAdapter;
import com.iris.cada.utils.StringUtils;
import com.iris.cada.view.pickerviewhelper.AddTodoPickerViewUtils;
import com.iris.cada.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ZdDiagnosisadapter extends CommonAdapter<ShzdBean> {
	
//	ArrayList<String> checkSalesConsultant;
	private ArrayList<ShzdBean> mConsultantList;
	String value="";
	
	private Context context;
	private List<ShzdBean> datas;
	private String id;
	private String name;
	private String acXinPotentialCustomer, acLookToBuyRate, acDerivativesBusinessAverageCarProfits;
	private String receptionTotal;//平均接待总数
	private String stocknum;//平均库存数量
	private AddTodoPickerViewUtils addPickerViewUtils = new AddTodoPickerViewUtils(context);
	private List<ShzdBean> dianotade;
	
	private String startDate;//开始时间
	private String endDate;//结束时间
	String premission ;
	
	/**
	 * @param paramContext
	 * @param diagnosislist 除掉第一条的数据
	 * @param mCurrentDate
	 * @param dianotade 返回来的数据
	 */
	public ZdDiagnosisadapter(Context paramContext, List<ShzdBean> diagnosislist, List<ShzdBean> dianotade,String startDate,String endDate) {
		super(paramContext, diagnosislist);
		this.context = paramContext;
		this.datas = diagnosislist;//除掉第一条的数据
		this.startDate=startDate;
		this.endDate=endDate;
		// Log.e("diaz", datas.size() + "");
		setAvgData(dianotade);//后台返回的数据通过这个方法获得平均值，然后通过list的值和平均值比较在显示字体颜色
		premission= ProfitApplication.loginBackInfo.getPermission();
		if(TextUtils.isEmpty(premission)){
			premission="总经理";
		}
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	public View getView(final int posation, View paramView, ViewGroup paramViewGroup) {
		ViewHolder viewHolder = null;
		if (paramView == null) {
			viewHolder = new ViewHolder();
			paramView = LayoutInflater.from(this.context).inflate(R.layout.item_fragment_diagnosisa, null);
			viewHolder.SalesConsultant = (TextView) paramView.findViewById(R.id.item_diadeal_name);//服务顾问
			viewHolder.receptionCount = (TextView) paramView.findViewById(R.id.item_diadeal_name0);//维修台次
			viewHolder.XinPotentialCustomer = (TextView) paramView.findViewById(R.id.item_diadeal_name1);//收入
			viewHolder.LookToBuyRate = (TextView) paramView.findViewById(R.id.item_diadeal_name2);//毛利
			viewHolder.DerivativesBusinessAverageCarProfits = (TextView) paramView
					.findViewById(R.id.item_diadeal_name3);//平均单车
			viewHolder.product = (TextView) paramView.findViewById(R.id.item_diadeal_name4);//详情			
			viewHolder.tv_itemkcsl=(TextView) paramView.findViewById(R.id.tv_itemkcsl);//销售部分有用这里隐藏掉
			viewHolder.tv_itemkcsl.setVisibility(View.GONE);
			
			paramView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) paramView.getTag();
		}
		
		if (!ProfitApplication.isConsultantMode) {//车型模式
			viewHolder.SalesConsultant.setText(datas.get(posation).get车型());			
		
		} else {
			viewHolder.SalesConsultant.setText(datas.get(posation).get服务顾问());
						
		}
		
	
		viewHolder.receptionCount.setText(datas.get(posation).get维修台次());		
		setTextColors(receptionTotal, datas.get(posation).get维修台次(),
				viewHolder.receptionCount);
		
		
		if("售后半权限".equals(premission)){
			viewHolder.XinPotentialCustomer.setText("-");
			viewHolder.LookToBuyRate.setText("-");	
			viewHolder.DerivativesBusinessAverageCarProfits.setText("-");
		}else {
			viewHolder.XinPotentialCustomer.setText(datas.get(posation).get合计收入());		
			setTextColors(acXinPotentialCustomer, datas.get(posation).get合计收入(), viewHolder.XinPotentialCustomer);
		
		
			viewHolder.LookToBuyRate.setText(datas.get(posation).get合计毛利());		
			setTextColors(acLookToBuyRate,datas.get(posation).get合计毛利(),
					viewHolder.LookToBuyRate);
		    
			viewHolder.DerivativesBusinessAverageCarProfits.setText(datas.get(posation).get平均单车收入());
			
			setTextColors(acDerivativesBusinessAverageCarProfits,
					datas.get(posation).get平均单车收入(),
					viewHolder.DerivativesBusinessAverageCarProfits);
		}
		
		
				
		viewHolder.product.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(context, H5webAct.class);
				if(ProfitApplication.isConsultantMode){//顾问模式
					String url=ProfitApplication.H5_INFO+"?dealerCode="
				               +ProfitApplication.loginBackInfo.getLicense()
				               +"&checkType="+"0"
				               +"&startDate="+startDate
				               +"&endDate="+endDate
				               +"&customName="+datas.get(posation).get服务顾问();
				   int datasSize = datas != null ? datas.size() : 0;
				   for(int a=0;a<datasSize;a++){
					   url = url + "&dataDuringList="+datas.get(a).get服务顾问();
				   }
				   if("总经理".equals(premission)||"售后全权限".equals(premission)){
					   url=url+"&premission="+"1";
				   }else {
					   url=url+"&premission="+"0";
				   }
				   intent.putExtra("url", url);
				   intent.putExtra("name", datas.get(posation).get服务顾问());
				   context.startActivity(intent);
				}else {//车型模式
					String url=ProfitApplication.H5_INFO+"?dealerCode="
				               +ProfitApplication.loginBackInfo.getLicense()
				               +"&checkType="+"1"				              
				               +"&startDate="+startDate
				               +"&endDate="+endDate
				               +"&customName="+datas.get(posation).get车型();
					   int datasSize = datas != null ? datas.size() : 0;
					   for(int a=0;a<datasSize;a++){
						   url = url + "&dataDuringList="+datas.get(a).get车型();
					   }
					   
					   if("总经理".equals(premission)||"售后全权限".equals(premission)){
						   url=url+"&premission="+"1";//可以看
					   }else {
						   url=url+"&premission="+"0";//不能看
					   }
				   intent.putExtra("url", url);
				   intent.putExtra("name", datas.get(posation).get车型());
				   context.startActivity(intent);
				}
				}
			
		});
				
		return paramView;
	}

	public void setAvgData(List<ShzdBean>dat) {
		this.acXinPotentialCustomer = dat.get(0).get合计收入();
		this.acLookToBuyRate = dat.get(0).get合计毛利();
		this.acDerivativesBusinessAverageCarProfits = dat.get(0).get平均单车收入();
		this.receptionTotal = dat.get(0).get维修台次();//维修台次
		
	}

	private static class ViewHolder {
		// 销售顾问
		private TextView SalesConsultant;
		// 新增潜客
		private TextView XinPotentialCustomer;
		// 成交率
		private TextView LookToBuyRate;
		// 平均单车赢利
		private TextView DerivativesBusinessAverageCarProfits;
		// 详细
		private TextView product;
		//接待总数
		private TextView receptionCount;
		//库存数量
		private TextView tv_itemkcsl;
	}

	public void setTextColors(String number1, String number2, TextView tv) {
		number1=number1.replace(",", "");
		number2=number2.replace(",", "");
		if (number1 != null && number2 != null) {
			CKNumFormat ckNumFormat = new CKNumFormat();
			if (ckNumFormat.isNumeric(number1) && ckNumFormat.isNumeric(number2)) {
				Integer nubmerInt1 = Integer.parseInt(number1);
				Integer numberInt2 = Integer.parseInt(number2);
				if (nubmerInt1 > numberInt2) {
					tv.setTextColor(Color.parseColor("#ff4400"));
				} else {
					tv.setTextColor(Color.parseColor("#79be0c"));
				}
			}
		}
	}
}

