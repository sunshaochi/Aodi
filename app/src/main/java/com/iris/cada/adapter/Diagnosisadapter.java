package com.iris.cada.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnContextClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iris.cada.ProfitApplication;
import com.iris.cada.activity.DiagnosisProductActivity;
import com.iris.cada.entity.Check;
import com.iris.cada.entity.ConsultantInfo;
import com.iris.cada.entity.IViewSCBean;
import com.iris.cada.entity.IviewDiagnosisBean;
import com.iris.cada.utils.CKNumFormat;
import com.iris.cada.utils.CommonAdapter;
import com.iris.cada.utils.StringUtils;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.view.pickerviewhelper.AddTodoPickerViewUtils;
import com.iris.foundation.utils.CommonHolder;
import com.iris.cada.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Diagnosisadapter extends CommonAdapter<Check> {
	
//	ArrayList<String> checkSalesConsultant;
	private ArrayList<IViewSCBean> mConsultantList;
	String value="";
	
	private Context context;
	private List<Check> datas;
	private Date mCurrentDate = null;
	private String id;
	private String name;
	private String acXinPotentialCustomer, acLookToBuyRate, acDerivativesBusinessAverageCarProfits;
	private String receptionTotal;//平均接待总数
	private String stocknum;//平均库存数量
	private AddTodoPickerViewUtils addPickerViewUtils = new AddTodoPickerViewUtils(context);
	private List<Check> dianotade;
	/**
	 * @param paramContext
	 * @param diagnosislist 除掉第一条的数据
	 * @param mCurrentDate
	 * @param dianotade 返回来的数据
	 */
	public Diagnosisadapter(Context paramContext, List<Check> diagnosislist, Date mCurrentDate,List<Check> dianotade) {
		super(paramContext, diagnosislist);
		this.context = paramContext;
		this.datas = diagnosislist;//除掉第一条的数据
		this.mCurrentDate = mCurrentDate;
		// Log.e("diaz", datas.size() + "");
		setAvgData(dianotade);//后台返回的数据通过这个方法获得平均值，然后通过list的值和平均值比较在显示字体颜色
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
			viewHolder.SalesConsultant = (TextView) paramView.findViewById(R.id.item_diadeal_name);//车型
			viewHolder.XinPotentialCustomer = (TextView) paramView.findViewById(R.id.item_diadeal_name1);//新增
			viewHolder.LookToBuyRate = (TextView) paramView.findViewById(R.id.item_diadeal_name2);//成交率
			viewHolder.DerivativesBusinessAverageCarProfits = (TextView) paramView
					.findViewById(R.id.item_diadeal_name3);//衍生业务毛利单车gp3
			viewHolder.product = (TextView) paramView.findViewById(R.id.item_diadeal_name4);
			viewHolder.receptionCount = (TextView) paramView.findViewById(R.id.item_diadeal_name0);//接待总数
			viewHolder.tv_itemkcsl=(TextView) paramView.findViewById(R.id.tv_itemkcsl);//库存数量
			if(ProfitApplication.isConsultantMode){
				viewHolder.tv_itemkcsl.setVisibility(View.GONE);
			}else {
				viewHolder.tv_itemkcsl.setVisibility(View.VISIBLE);
			}
			paramView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) paramView.getTag();
		}
		if (!ProfitApplication.isConsultantMode) {
			viewHolder.SalesConsultant.setText(datas.get(posation).getCarType());
		} else {
			viewHolder.SalesConsultant.setText(datas.get(posation).getSalesConsultant());
		}
		viewHolder.XinPotentialCustomer.setText(datas.get(posation).getXinPotentialCustomer());
		setTextColors(acXinPotentialCustomer, datas.get(posation).getXinPotentialCustomer(),
				viewHolder.XinPotentialCustomer);
		viewHolder.LookToBuyRate.setText(datas.get(posation).getLookToBuyRate() + "%");
		setTextColors(acLookToBuyRate, datas.get(posation).getLookToBuyRate(), viewHolder.LookToBuyRate);
		//权限
		String premission = ProfitApplication.loginBackInfo.getPermission();
		if(TextUtils.isEmpty(premission)){
			premission="总经理";
		}
		if("仅运营权限".equals(premission)||"销售半权限".equals(premission)){
			viewHolder.DerivativesBusinessAverageCarProfits.setText("-");//衍生
		}else{
			viewHolder.DerivativesBusinessAverageCarProfits
			.setText(StringUtils.intDivTo0(datas.get(posation).getDerivativesBusinessAverageCarProfits()));//衍生
		}
		setTextColors(acDerivativesBusinessAverageCarProfits,
				datas.get(posation).getDerivativesBusinessAverageCarProfits(),
				viewHolder.DerivativesBusinessAverageCarProfits);
		viewHolder.product.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String dataput = addPickerViewUtils.getDateNoDay(mCurrentDate);
				if (!ProfitApplication.isConsultantMode) {
					value = "";
					id = datas.get(posation).getCarTypeCode();
					name = datas.get(posation).getCarType();
					
					for (int i = 0; i < datas.size(); i++) {
						if(i == datas.size()-1){
							//如果是最后一个元素
							value += datas.get(i).getCarTypeCode();
						}else{
							value += datas.get(i).getCarTypeCode()+",";
						}
					}
					
					Intent intent = new Intent(context, DiagnosisProductActivity.class);
					intent.putExtra("name", name);
					intent.putExtra("id", id);
					intent.putExtra("datas", dataput);
					intent.putExtra("CHECK_CAR", value);
					context.startActivity(intent);
					
				} else {//销售顾问
					
					id = datas.get(posation).getAlias();
					name = datas.get(posation).getSalesConsultant();
					
					mConsultantList = new ArrayList<IViewSCBean>();
					for (int i = 0; i < datas.size(); i++) {
						IViewSCBean iViewSCBean = new IViewSCBean();
						iViewSCBean.setScName(datas.get(i).getSalesConsultant());
						iViewSCBean.setScCode(datas.get(i).getAlias());
						mConsultantList.add(iViewSCBean);
					}
					Intent intent = new Intent(context, DiagnosisProductActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("CHECK_UESR", (Serializable)mConsultantList);
					bundle.putString("name", name);
					bundle.putString("id", id);
					bundle.putString("datas", dataput);
					intent.putExtras(bundle);
					context.startActivity(intent);
				}
			}
		});
		
		viewHolder.receptionCount.setText(datas.get(posation).getReceptionTotal());//接待总数
		setTextColors(receptionTotal, datas.get(posation).getReceptionTotal(),
				viewHolder.receptionCount);
		viewHolder.tv_itemkcsl.setText(datas.get(posation).getStockNum());//库存数量
		setTextColors(stocknum, datas.get(posation).getStockNum(), viewHolder.tv_itemkcsl);
		return paramView;
	}

	public void setAvgData(List<Check>dat) {
		this.acXinPotentialCustomer = dat.get(0).getXinPotentialCustomer();
		this.acLookToBuyRate = dat.get(0).getLookToBuyRate();
		this.acDerivativesBusinessAverageCarProfits = dat.get(0).getDerivativesBusinessAverageCarProfits();
		this.receptionTotal = dat.get(0).getReceptionTotal();//接待总数
		this.stocknum=dat.get(0).getStockNum();
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
