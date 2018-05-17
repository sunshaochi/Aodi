package com.iris.cada.newfragment;

import java.util.ArrayList;
import java.util.List;

import com.iris.cada.adapter.DiagDetailsAdapter;
import com.iris.cada.fragment.BaseFragment;
import com.iris.cada.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * @author ChuanJing
 * @date 2016年10月9日 下午2:25:52
 *诊断页面 fragment
 */
public class DiagDetailsFragment extends BaseFragment {
	private ListView fragment_diag_lv;
	private Context Context;
    private List<String>  datas;
    private TextView newspepolnumber;//新增浅客人数
    private TextView averagelnumber;//团队平均人数
    private TextView  ratelnumber;//成交率
    private TextView  averagel_rate_number;//平均成交率
    private TextView  Profitnumber;//利润贡献
    private TextView  averagel_Profit_number;//利润平均
    private TextView  gp_number;//gp2数值
    private TextView  averagel_gp_number;//gp2平均
    private TextView  replacementnumber;//置换率
    private TextView  averagel_replacement_number;//置换平均
    private ImageView fine_logo,carloan_logo,insurance_logo,extended_logo;
    private TextView icul_name,carloan_name,insurance_name,extended_name;
    private TextView finenumber,fine_profit_number;//精品加装率//平均单车盈利
    private TextView fine_averagel_number,fine_averagel_profit_number;//精品加装率团队平均//盈利平均
    private TextView datas_name1,datas_name2,datas_name3,datas_name4;
    private TextView carloannumber,carloan_profit_number;//车贷渗透率//平均单车盈利
    private TextView carloa_averagel_number,carloa_averagel_profit_number;//精品加装率平均//盈利平均
    private TextView insurancenumber,insurance_profit_number;//保险渗透率//平均单车盈利
    private TextView insurance_averagel_number,insurance_averagel_profit_number;//保险加装率平均//盈利平均
    private TextView extendednumber,extended_profit_number;//延保渗透率//平均单车盈利
    private TextView extended_averagel_number,extended_averagel_profit_number;//延保加装率平均//盈利平均
    private TextView diagdetal_dates;
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_diagdetails, container, false);
		initView(view);
		bindata();
		//datas=new ArrayList<String>();
		//datas.add("111");
		//datas.add("123");
		//fragment_diag_lv.setAdapter(new DiagDetailsAdapter(getActivity(),datas));
		return view;
	}

	private void initView(View view) {
		//新增
		
		newspepolnumber=(TextView) view.findViewById(R.id.fragment_diadetail_datas_linner_add_right_newspepolnumber);//新增潜客
		averagelnumber=(TextView) view.findViewById(R.id.fragment_diadetail_datas_linner_add_right_averagelnumber);
		ratelnumber=(TextView) view.findViewById(R.id.fragment_diadetail_datas_linner_deal_right_ratelnumber);
		averagel_rate_number=(TextView) view.findViewById(R.id.fragment_diadetail_datas_linner_deal_right_averagelnumber);
		Profitnumber=(TextView) view.findViewById(R.id.fragment_diadetail_datas_linner_profit_right_Profitnumber);
		averagel_Profit_number=(TextView) view.findViewById(R.id.fragment_diadetail_datas_linner_profit_right_averagelnumber);
		gp_number=(TextView) view.findViewById(R.id.fragment_diadetail_datas_linner_gp2_right_gpnumber);
		averagel_gp_number=(TextView) view.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_averagelnumber);
		replacementnumber=(TextView) view.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_replacementnumber);
		averagel_replacement_number=(TextView) view.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_averagelnumber);
		
		View diagdetail_Fine_incl= view.findViewById(R.id.fragment_diagdetail_Fine_incl);
		View diagdetail_carloan_incl= view.findViewById(R.id.fragment_diagdetail_carloan_incl);
		View iagdetail_insurance_incl= view.findViewById(R.id.fragment_diagdetail_insurance_incl);
		View diagdetail_extended_incl= view.findViewById(R.id.fragment_diagdetail_extended_incl);
		datas_name1=(TextView) diagdetail_Fine_incl.findViewById(R.id.datas_name);
		datas_name2=(TextView) diagdetail_carloan_incl.findViewById(R.id.datas_name);
		datas_name3=(TextView) iagdetail_insurance_incl.findViewById(R.id.datas_name);
		datas_name4=(TextView) diagdetail_extended_incl.findViewById(R.id.datas_name);
		//延保数据
		extendednumber=(TextView) diagdetail_extended_incl.findViewById(R.id.fragment_diadetail_datas_linner_gp2_right_newspepolnumber);
		extended_profit_number=(TextView) diagdetail_extended_incl.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_newspepolnumber);
		extended_averagel_number=(TextView) diagdetail_extended_incl.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_averagelnumber);
		extended_averagel_profit_number=(TextView) diagdetail_extended_incl.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_averagelnumber);
		//保险数据
		insurancenumber=(TextView) iagdetail_insurance_incl.findViewById(R.id.fragment_diadetail_datas_linner_gp2_right_newspepolnumber);
		insurance_profit_number=(TextView) iagdetail_insurance_incl.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_newspepolnumber);
		insurance_averagel_number=(TextView) iagdetail_insurance_incl.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_averagelnumber);
		insurance_averagel_profit_number=(TextView) iagdetail_insurance_incl.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_averagelnumber);
		//精品 数据		
		finenumber=(TextView) diagdetail_Fine_incl.findViewById(R.id.fragment_diadetail_datas_linner_gp2_right_newspepolnumber);
		carloan_profit_number=(TextView) diagdetail_Fine_incl.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_newspepolnumber);
		fine_averagel_number=(TextView) diagdetail_Fine_incl.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_averagelnumber);
		fine_averagel_profit_number=(TextView) diagdetail_Fine_incl.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_averagelnumber);
		//车贷数据
		carloannumber=(TextView) diagdetail_carloan_incl.findViewById(R.id.fragment_diadetail_datas_linner_gp2_right_newspepolnumber);
		fine_profit_number=(TextView) diagdetail_carloan_incl.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_newspepolnumber);
		carloa_averagel_number=(TextView) diagdetail_carloan_incl.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_averagelnumber);
		carloa_averagel_profit_number=(TextView) diagdetail_carloan_incl.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_averagelnumber);
		//logo图
		fine_logo=(ImageView) diagdetail_Fine_incl.findViewById(R.id.fragment_diadetail_gp2_linnder_profit_image);
		carloan_logo=(ImageView) diagdetail_carloan_incl.findViewById(R.id.fragment_diadetail_gp2_linnder_profit_image);
		insurance_logo=(ImageView) iagdetail_insurance_incl.findViewById(R.id.fragment_diadetail_gp2_linnder_profit_image);
		extended_logo=(ImageView) diagdetail_extended_incl.findViewById(R.id.fragment_diadetail_gp2_linnder_profit_image);
        //数据name
		icul_name=(TextView) diagdetail_Fine_incl.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_name);
		carloan_name=(TextView) diagdetail_carloan_incl.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_name);
		insurance_name=(TextView) iagdetail_insurance_incl.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_name);
		extended_name=(TextView) diagdetail_extended_incl.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_name);
	}
	private void bindata(){
		newspepolnumber.setText("10");
		averagelnumber.setText("20");
		ratelnumber.setText("30");
		averagel_rate_number.setText("40");
		Profitnumber.setText("50");
		averagel_Profit_number.setText("60");
		gp_number.setText("70");
		averagel_gp_number.setText("80");
		replacementnumber.setText("90");
		averagel_replacement_number.setText("11");
	
		//精品，车贷，保险，延保，数据set
		finenumber.setText("11");
		fine_profit_number.setText("11");
		fine_averagel_number.setText("22");
		fine_averagel_profit_number.setText("22");
		carloannumber.setText("11");
		carloan_profit_number.setText("11");
		carloa_averagel_number.setText("22");
		carloa_averagel_profit_number.setText("22");
		insurancenumber.setText("11");
		insurance_profit_number.setText("11");
		insurance_averagel_number.setText("22");
		insurance_averagel_profit_number.setText("22");
		extendednumber.setText("11");
		extended_profit_number.setText("11");
		extended_averagel_number.setText("22");
		extended_averagel_profit_number.setText("22");
		fine_logo.setImageResource(R.drawable.icon_boutique);
		carloan_logo.setImageResource(R.drawable.icon_carloan);
		insurance_logo.setImageResource(R.drawable.icon_xinbao);
		extended_logo.setImageResource(R.drawable.icon_extendedwarranty);
		datas_name1.setText("精品");
		datas_name2.setText("车贷");
		datas_name3.setText("新保");
		datas_name4.setText("保险");
		icul_name.setText("加装率");
		carloan_name.setText("渗透率");
		insurance_name.setText("渗透率");
		extended_name.setText("渗透率");
	}
}
