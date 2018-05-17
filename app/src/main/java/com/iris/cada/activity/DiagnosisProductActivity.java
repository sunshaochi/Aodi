package com.iris.cada.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iris.cada.MyHandler;
import com.iris.cada.ProfitApplication;
import com.iris.cada.adapter.MyGridViewAdapter;
import com.iris.cada.comparator.CheckXinPotentialCustomerComparatorAsc;
import com.iris.cada.comparator.DiagnosisProductComparatorAsc;
import com.iris.cada.entity.IViewSCBean;
import com.iris.cada.entity.IviewDiagnosisBean;
import com.iris.cada.entity.IviewDiagnosisProductBean;
import com.iris.cada.utils.CKNumFormat;
import com.iris.cada.utils.DateAndTimeUtils;
import com.iris.cada.utils.StringUtils;
import com.iris.cada.utils.TimeRefreshUtils;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.view.CProgressDialog;
import com.iris.cada.view.pickerviewhelper.AddTodoPickerViewUtils;
import com.iris.cada.R;

public class DiagnosisProductActivity extends Activity implements OnClickListener {
	private Gson gson;
	private String id;
	private String timeFormat = null;
	private Date mCurrentDate = null;
	protected Dialog progressDialog;
	private ListView fragment_diag_lv;
	private Context Context;
	private List<String> datas;
	private TextView tv_newspepol, tv_ratel, tv_Profit, tv_gp2, tv_replacement, tv_fine, tv_fine_averagel, tv_carloan,
			tv_avgcarloan, tv_insurance, tv_avginsurance, tv_extended, tv_avgextended;
	private TextView title_name;
	private TextView newspepolnumber;// 新增浅客人数
	private TextView averagelnumber;// 团队平均人数
	private TextView ratelnumber;// 成交率
	private TextView averagel_rate_number;// 平均成交率
	private TextView Profitnumber;// 利润贡献
	private TextView averagel_Profit_number;// 利润平均
	private TextView gp_number;// gp1数值
	private TextView averagel_gp_number;// gp1平均
	private TextView replacementnumber;// 置换率
	private TextView averagel_replacement_number;// 置换平均
	private ImageView fine_logo, carloan_logo, insurance_logo, extended_logo;
	private TextView icul_name, carloan_name, insurance_name, extended_name;
	private TextView finenumber, fine_profit_number;// 精品加装率//平均单车盈利
	private TextView fine_averagel_number, fine_averagel_profit_number;// 精品加装率团队平均//盈利平均
	private TextView datas_name1, datas_name2, datas_name3, datas_name4;
	private TextView carloannumber, carloan_profit_number;// 车贷渗透率//平均单车盈利
	private TextView carloa_averagel_number, carloa_averagel_profit_number;// 精品加装率平均//盈利平均
	private TextView insurancenumber, insurance_profit_number;// 保险渗透率//平均单车盈利
	private TextView insurance_averagel_number, insurance_averagel_profit_number;// 保险加装率平均//盈利平均
	private TextView extendednumber, extended_profit_number;// 延保渗透率//平均单车盈利
	private TextView extended_averagel_number, extended_averagel_profit_number;// 延保加装率平均//盈利平均
	private TextView diagdetal_dates;
	private ImageView dia_back;
	private List<IviewDiagnosisProductBean> DiagnosisProfductlist;
	private AddTodoPickerViewUtils addTodoPickerViewUtils;
	private Date mStartDate;
	private Date mEndDate;
	private ImageView last_img, next_img;
	private String title_names, productdata;
	private LinearLayout report_awy_linner;
	
	private TextView tv_o,tv_t,tv_three,tv_f,tv_five,tv_s;
	
	private String premission;

	private List<IViewSCBean>carlist=new ArrayList<>();
	
	/**以下是新加的东西**/
	private LinearLayout ll_kcsl;//库存数量那列
	private TextView tv_kcsl,tv_kcslpj,tv_kcts,tv_kctspj,tv_dingbi;//库存数量，平均库存数量,库存天数，平均库存天数,定交比
	private TextView tv_bfgp1,tv_gp1pj,tv_dcgp1,tv_dcgp1pj,tv_bfgp2,tv_gp2pj,tv_dcgp2,tv_dcgp2pj,tv_bfgp3,tv_gp3pj,tv_dcgp3,tv_dcgp3pj;

	private void bindata() {
		dia_back.setOnClickListener(this);
		// diagdetal_dates.setOnClickListener(this);
		// last_img.setOnClickListener(this);
		// next_img.setOnClickListener(this);
		// report_awy_linner.setOnClickListener(this);
		if(!ProfitApplication.isConsultantMode){
			tv_o.setText("车型平均");
			tv_t.setText("车型平均");
			tv_three.setText("车型平均");
			tv_f.setText("车型平均");
			tv_five.setText("车型平均");
			tv_s.setText("车型平均");
		}else {
			tv_o.setText("团队平均");
			tv_t.setText("团队平均");
			tv_three.setText("团队平均");
			tv_f.setText("团队平均");
			tv_five.setText("团队平均");
			tv_s.setText("团队平均");
		}
		
	}

	private void initView() {
		
		//新增加库存数量和定交比新车gp1哪些
		ll_kcsl=(LinearLayout) findViewById(R.id.ll_diakcsl);//库存数量那列
		if(!ProfitApplication.isConsultantMode){
			ll_kcsl.setVisibility(View.VISIBLE);
		}else {
			ll_kcsl.setVisibility(View.GONE);
		}
		
		tv_o=(TextView) findViewById(R.id.tv_o);
		tv_t=(TextView) findViewById(R.id.tv_t);
		tv_three=(TextView) findViewById(R.id.tv_three);
		tv_f=(TextView) findViewById(R.id.tv_f);
		tv_five=(TextView) findViewById(R.id.tv_five);
		tv_s=(TextView) findViewById(R.id.tv_s);
		
		tv_kcsl=(TextView) findViewById(R.id.tv_kcsl);
		tv_kcslpj=(TextView) findViewById(R.id.tv_kcslpj);
		tv_kcts=(TextView) findViewById(R.id.tv_kcts);
		tv_kctspj=(TextView) findViewById(R.id.tv_kctspj);
		tv_dingbi=(TextView) findViewById(R.id.tv_dingbi);
		
		tv_bfgp1=(TextView) findViewById(R.id.tv_bfgp1);
		tv_gp1pj=(TextView) findViewById(R.id.tv_gp1pj);
		tv_dcgp1=(TextView) findViewById(R.id.tv_dcgp1);
		tv_dcgp1pj=(TextView) findViewById(R.id.tv_dcgp1pj);
		
		tv_bfgp2=(TextView) findViewById(R.id.tv_bfgp2);
		tv_gp2pj=(TextView) findViewById(R.id.tv_gp2pj);
		tv_dcgp2=(TextView) findViewById(R.id.tv_dcgp2);
		tv_dcgp2pj=(TextView) findViewById(R.id.tv_dcgp2pj);
		
		tv_bfgp3=(TextView) findViewById(R.id.tv_bfgp3);
		tv_gp3pj=(TextView) findViewById(R.id.tv_gp3pj);
		tv_dcgp3=(TextView) findViewById(R.id.tv_dcgp3);
		tv_dcgp3pj=(TextView) findViewById(R.id.tv_dcgp3pj);
		
//		mPremission = ProfitApplication.loginBackInfo.getPermission();
		// 新增
		layoutStartTime = (RelativeLayout) findViewById(R.id.fragment_diagno_star_adc_rela);// 开始日期
		layoutStartTime.setOnClickListener(this);
		txtStartTime = (TextView) findViewById(R.id.fragment_diagno_adc_start_time);
		layoutEndTime = (RelativeLayout) findViewById(R.id.fragment_diagno_end_adc_rela);// 结束日期
		layoutEndTime.setOnClickListener(this);
		txtEndTime = (TextView) findViewById(R.id.fragment_diagno_adc_end_time);

		title_name = (TextView) findViewById(R.id.home_title_name);
		dia_back = (ImageView) findViewById(R.id.overview_title_back);// 返回箭头

		jiedai_linner = (TextView) findViewById(R.id.jiedai_linner);// 接待总数
		jiedai_averagelnumber = (TextView) findViewById(R.id.jiedai_linner_add_right_averagelnumber);// 团队平均

		newspepolnumber = (TextView) findViewById(R.id.fragment_diadetail_datas_linner_add_right_newspepolnumber);// 新增潜客
		averagelnumber = (TextView) findViewById(R.id.fragment_diadetail_datas_linner_add_right_averagelnumber);// 团队平均

		ratelnumber = (TextView) findViewById(R.id.fragment_diadetail_datas_linner_deal_right_ratelnumber);// 成交率
		averagel_rate_number = (TextView) findViewById(R.id.fragment_diadetail_datas_linner_deal_right_averagelnumber);// 团队平均

		Profitnumber = (TextView) findViewById(R.id.fragment_diadetail_datas_linner_profit_right_Profitnumber);// 交车数
		averagel_Profit_number = (TextView) findViewById(
				R.id.fragment_diadetail_datas_linner_profit_right_averagelnumber);// 团队平均
//--------------------------------------------------------------------------------------------------------------------
		gp_number = (TextView) findViewById(R.id.fragment_diadetail_datas_linner_gp1_right_gpnumber);// GP1
		averagel_gp_number = (TextView) findViewById(R.id.fragment_diadetail_gp1_linner_profit_right_averagelnumber);// 团队平均

		gp3_number = (TextView) findViewById(R.id.fragment_diadetail_datas_linner_gp3_right_gpnumber);
		average3_gp_number = (TextView) findViewById(R.id.fragment_diadetail_gp3_linner_profit_right_averagelnumber);

		View diagdetail_Fine_incl = findViewById(R.id.fragment_diagdetail_Fine_incl);
		View diagdetail_carloan_incl = findViewById(R.id.fragment_diagdetail_carloan_incl);
		View iagdetail_insurance_incl = findViewById(R.id.fragment_diagdetail_insurance_incl);
		View diagdetail_extended_incl = findViewById(R.id.fragment_diagdetail_extended_incl);
		datas_name1 = (TextView) diagdetail_Fine_incl.findViewById(R.id.datas_name);
		datas_name2 = (TextView) diagdetail_carloan_incl.findViewById(R.id.datas_name);
		datas_name3 = (TextView) iagdetail_insurance_incl.findViewById(R.id.datas_name);
		datas_name4 = (TextView) diagdetail_extended_incl.findViewById(R.id.datas_name);
		// 新增
		View diagdetail_shangPai_incl = findViewById(R.id.fragment_diagdetail_shangPai_incl);// 上牌
		View diagdetail_zhiHuan_incl = findViewById(R.id.fragment_diagdetail_zhiHuan_incl);// 置换
		datas_name5 = (TextView) diagdetail_shangPai_incl.findViewById(R.id.datas_name);
		datas_name6 = (TextView) diagdetail_zhiHuan_incl.findViewById(R.id.datas_name);
		TextView tv_shangPai = (TextView) diagdetail_shangPai_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_names);
		TextView tv_shangPai_averagel = (TextView) diagdetail_shangPai_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_names);
		TextView tv_zhiHuan = (TextView) diagdetail_zhiHuan_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_names);
		TextView tv_zhiHuan_averagel = (TextView) diagdetail_zhiHuan_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_names);

		// 上牌 数据
		shangPaiNumber = (TextView) diagdetail_shangPai_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_gp2_right_newspepolnumber);
		shangPai_averagel_number = (TextView) diagdetail_shangPai_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_averagelnumber);// 团队平均

		shangPai_profit_number = (TextView) diagdetail_shangPai_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_newspepolnumber);// 毛利
		shangPai_averagel_profit_number = (TextView) diagdetail_shangPai_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_averagelnumber);

		// 置換数据
		zhiHuanNumber = (TextView) diagdetail_zhiHuan_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_gp2_right_newspepolnumber);// 置换渗透率
		zhiHuan_averagel_number = (TextView) diagdetail_zhiHuan_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_averagelnumber);// 团队平均

		zhiHuan_profit_number = (TextView) diagdetail_zhiHuan_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_newspepolnumber);
		zhiHuan_averagel_profit_number = (TextView) diagdetail_zhiHuan_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_averagelnumber);

		TextView zhiHuan_maoli_name = (TextView) diagdetail_zhiHuan_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_name);
		zhiHuan_maoli_name.setText("单车其它毛利");

		// logo图
		ImageView jieDai_logo = (ImageView)findViewById(R.id.fragment_diadetail_datas_jiedai_linnder_added_image);//接待数
		ImageView jiaoChe_logo = (ImageView)findViewById(R.id.fragment_diadetail_datas_linnder_profit_image);//交车数
		ImageView shangPai_logo = (ImageView) diagdetail_shangPai_incl
				.findViewById(R.id.fragment_diadetail_gp2_linnder_profit_image);//上牌
		ImageView zhiHuan_logo = (ImageView) diagdetail_zhiHuan_incl
				.findViewById(R.id.fragment_diadetail_gp2_linnder_profit_image);//置换

		// 数据name
		TextView shangPai_name = (TextView) diagdetail_shangPai_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_name);
		TextView zhiHuan_name = (TextView) diagdetail_zhiHuan_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_name);
		
		tv_jiedai = (TextView) findViewById(R.id.jiedai_linner_add_right_names);
		//gp1---团队平均
		tv_gp1 = (TextView) findViewById(R.id.fragment_diadetail_gp1_linner_profit_right_names);
		//gp3---团队平均
		tv_gp3 = (TextView) findViewById(R.id.fragment_diadetail_gp3_linner_profit_right_names);
		
		tv_newspepol = (TextView) findViewById(R.id.fragment_diadetail_datas_linner_add_right_names);//新增潜客---团队平均
		tv_ratel = (TextView) findViewById(R.id.fragment_diadetail_datas_linner_deal_right_names);
		tv_Profit = (TextView) findViewById(R.id.fragment_diadetail_datas_linner_profit_right_names);//交车数---团队平均

		tv_fine = (TextView) diagdetail_Fine_incl.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_names);
		tv_fine_averagel = (TextView) diagdetail_Fine_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_names);
		tv_carloan = (TextView) diagdetail_carloan_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_names);
		tv_avgcarloan = (TextView) diagdetail_carloan_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_names);
		tv_insurance = (TextView) iagdetail_insurance_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_names);
		tv_avginsurance = (TextView) iagdetail_insurance_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_names);
		tv_extended = (TextView) diagdetail_extended_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_names);
		tv_avgextended = (TextView) diagdetail_extended_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_names);

		// 延保数据
		extendednumber = (TextView) diagdetail_extended_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_gp2_right_newspepolnumber);
		extended_profit_number = (TextView) diagdetail_extended_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_newspepolnumber);
		extended_averagel_number = (TextView) diagdetail_extended_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_averagelnumber);
		extended_averagel_profit_number = (TextView) diagdetail_extended_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_averagelnumber);
		// 保险数据
		insurancenumber = (TextView) iagdetail_insurance_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_gp2_right_newspepolnumber);
		insurance_profit_number = (TextView) iagdetail_insurance_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_newspepolnumber);
		insurance_averagel_number = (TextView) iagdetail_insurance_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_averagelnumber);
		insurance_averagel_profit_number = (TextView) iagdetail_insurance_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_averagelnumber);
		// 精品 数据
		finenumber = (TextView) diagdetail_Fine_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_gp2_right_newspepolnumber);// 加装率
		fine_averagel_number = (TextView) diagdetail_Fine_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_averagelnumber);// 团队平均

		fine_profit_number = (TextView) diagdetail_Fine_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_newspepolnumber);// 单车毛利
		fine_averagel_profit_number = (TextView) diagdetail_Fine_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_averagelnumber);
		// 车贷数据
		carloannumber = (TextView) diagdetail_carloan_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_gp2_right_newspepolnumber);
		carloan_profit_number = (TextView) diagdetail_carloan_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_newspepolnumber);
		carloa_averagel_number = (TextView) diagdetail_carloan_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_averagelnumber);
		carloa_averagel_profit_number = (TextView) diagdetail_carloan_incl
				.findViewById(R.id.fragment_diadetail_datas_linner_replacement_right_averagelnumber);
		// logo图
		fine_logo = (ImageView) diagdetail_Fine_incl.findViewById(R.id.fragment_diadetail_gp2_linnder_profit_image);
		carloan_logo = (ImageView) diagdetail_carloan_incl
				.findViewById(R.id.fragment_diadetail_gp2_linnder_profit_image);
		insurance_logo = (ImageView) iagdetail_insurance_incl
				.findViewById(R.id.fragment_diadetail_gp2_linnder_profit_image);
		extended_logo = (ImageView) diagdetail_extended_incl
				.findViewById(R.id.fragment_diadetail_gp2_linnder_profit_image);
		// 数据name
		icul_name = (TextView) diagdetail_Fine_incl.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_name);
		carloan_name = (TextView) diagdetail_carloan_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_name);
		insurance_name = (TextView) iagdetail_insurance_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_name);
		extended_name = (TextView) diagdetail_extended_incl
				.findViewById(R.id.fragment_diadetail_gp2_linner_profit_right_name);
		fine_logo.setImageResource(R.drawable.icon_boutique);
		carloan_logo.setImageResource(R.drawable.icon_carloan);
		insurance_logo.setImageResource(R.drawable.icon_xinbao);
		extended_logo.setImageResource(R.drawable.icon_extendedwarranty);
		
		

		jieDai_logo.setImageResource(R.drawable.diagnose_jiedai);
		jiaoChe_logo.setImageResource(R.drawable.diagnose_jiaoche);
		shangPai_logo.setImageResource(R.drawable.diagnose_shangpai);
		zhiHuan_logo.setImageResource(R.drawable.diagnose_zhihuan);

		datas_name1.setText("精品");
		datas_name2.setText("车贷");
		datas_name3.setText("新保");
		datas_name4.setText("延保");

		datas_name5.setText("上牌");
		datas_name6.setText("置换");

		icul_name.setText("加装率");
		carloan_name.setText("渗透率");
		insurance_name.setText("渗透率");
		extended_name.setText("渗透率");

		shangPai_name.setText("渗透率");
		zhiHuan_name.setText("置换率");

		if (!ProfitApplication.isConsultantMode) {
			tv_newspepol.setText("车型平均");
			tv_ratel.setText("车型平均");
			tv_Profit.setText("车型平均");
			tv_fine.setText("车型平均");
			tv_fine_averagel.setText("车型平均");
			
			tv_carloan.setText("车型平均");
			tv_avgcarloan.setText("车型平均");
			
			tv_insurance.setText("车型平均");
			tv_avginsurance.setText("车型平均");
			
			tv_extended.setText("车型平均");
			tv_avgextended.setText("车型平均");

			tv_shangPai.setText("车型平均");
			tv_shangPai_averagel.setText("车型平均");
			tv_zhiHuan.setText("车型平均");
			tv_zhiHuan_averagel.setText("车型平均");
			
			tv_jiedai.setText("车型平均");
			tv_gp1.setText("车型平均");
			tv_gp3.setText("车型平均");
		}

	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.fragment_diagdetails2);
		
		if (!ProfitApplication.isConsultantMode) {//汽车模式
			
			Bundle bundle = new Bundle();
			bundle = this.getIntent().getExtras();
			title_names = bundle.getString("name");
			id = bundle.getString("id");
			productdata = bundle.getString("datas");
			check_car = bundle.getString("CHECK_CAR");
			
		}else{//顾问模式
			
			Bundle bundle = new Bundle();
			bundle = this.getIntent().getExtras();
			title_names = bundle.getString("name");
			id = bundle.getString("id");
			productdata = bundle.getString("datas");
			mConsultantList = (ArrayList<IViewSCBean>) bundle.getSerializable("CHECK_UESR");//销售顾问
		}
		premission = ProfitApplication.loginBackInfo.getPermission();
		if(TextUtils.isEmpty(premission)){
			premission="总经理";
		}
		initView();//初始化控件
		bindata();
		initData();//初始化时间然后在里面调取接口
		
		
	}

	private void getData() {
		progressDialog = CProgressDialog.createLoadingDialog(this);
		progressDialog.show();
		
		if(!ProfitApplication.isConsultantMode){//车型
			
			ProfitApplication.profitNetService.getDiagnosisProductData2(id, txtStartTime.getText().toString().trim(),
					txtEndTime.getText().toString().trim(), handler,check_car,carlist);
		}else{
			
			ProfitApplication.profitNetService.getDiagnosisProductData(id, txtStartTime.getText().toString().trim(),
					txtEndTime.getText().toString().trim(), handler,mConsultantList);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.overview_title_back:
			finish();
			break;
		case R.id.fragment_diagno_star_adc_rela:
			showDateDialog(txtStartTime, true);
			break;
		case R.id.fragment_diagno_end_adc_rela:
			showDateDialog(txtEndTime, false);
			break;
		case R.id.fragment_report_awy_linner:
			break;
		}
	}

	Handler handler = new MyHandler() {
		public void handleMessage(android.os.Message msg) {
			progressDialog.dismiss();
			switch (msg.what) {
			case ProfitApplication.DATA_FAILED:
				ToastUtils.showMyToast(DiagnosisProductActivity.this, getString(R.string.error_data));

				case ProfitApplication.DATA_SUC:
				dealChannelInfo(msg);
				break;
			case ProfitApplication.SERVER_FAILED:
				ToastUtils.showMyToast(DiagnosisProductActivity.this, getString(R.string.error_net));
				break;
			}
		}

		private void dealChannelInfo(Message msg) {
			try {
				String strMsg = (String) msg.obj;
				Log.e("详情", strMsg);
				DiagnosisProfductlist = new Gson().fromJson(strMsg,
						new TypeToken<ArrayList<IviewDiagnosisProductBean>>() {
				}.getType());
				Log.e("list", DiagnosisProfductlist.toString());
				showOperativeInfo(DiagnosisProfductlist);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		private void showOperativeInfo(List<IviewDiagnosisProductBean> diagnosisProfductlist) {
			System.out.println(diagnosisProfductlist.toString());
			final int Posation = 0;
			if (!ProfitApplication.isConsultantMode) {
				title_name.setText(title_names);
			} else {
				title_name.setText(title_names);
			}

			jiedai_linner.setText(diagnosisProfductlist.get(Posation).getReception());// 接待总数
			jiedai_averagelnumber.setText(diagnosisProfductlist.get(Posation).getAvg_reception());// 团队平均
			setTextColors(jiedai_linner, jiedai_averagelnumber);

			newspepolnumber.setText(diagnosisProfductlist.get(Posation).getXinPotentialCustomer());// 新增潜客
			averagelnumber.setText(diagnosisProfductlist.get(Posation).getAvg_XinPotentialCustomer());// 团队平均
			setTextColors(newspepolnumber, averagelnumber);

			ratelnumber.setText(diagnosisProfductlist.get(Posation).getLooktoBuyRate());// 成交率
			averagel_rate_number.setText(diagnosisProfductlist.get(Posation).getAvg_LooktoBuyRate());
			setTextColors(ratelnumber, averagel_rate_number);

			Profitnumber.setText(diagnosisProfductlist.get(Posation).getTurnover());// 交车数
			averagel_Profit_number.setText(diagnosisProfductlist.get(Posation).getAvg_turnover());
			setTextColors(Profitnumber, averagel_Profit_number);
			
			//---------------------------------------(新加的库存)
			tv_dingbi.setText(diagnosisProfductlist.get(Posation).getOrderTurnover()+"");
			tv_kcsl.setText(diagnosisProfductlist.get(Posation).getStockNum()+"");
			tv_kcslpj.setText(diagnosisProfductlist.get(Posation).getAvgStockNum()+"");
			setoterTextColors(tv_kcsl, tv_kcslpj);
			
			tv_kcts.setText(diagnosisProfductlist.get(Posation).getAvgStockDays()+"");
			tv_kctspj.setText(diagnosisProfductlist.get(Posation).getAvgAvgStockDays()+"");
			setoterTextColors(tv_kcts, tv_kctspj);
			
			if("仅运营权限".equals(premission)||"销售半权限".equals(premission)){
			
				
				tv_bfgp1.setText("-");
				tv_gp1pj.setText("-");
				setTextColors(tv_bfgp1, tv_gp1pj);
				
				tv_bfgp2.setText("-");
				tv_gp2pj.setText("-");
				setTextColors(tv_bfgp2, tv_gp2pj);
				
				tv_bfgp3.setText("-");
				tv_gp3pj.setText("-");
				setTextColors(tv_bfgp3, tv_gp3pj);
				
				tv_dcgp1.setText("-");
				tv_dcgp1pj.setText("-");
				setTextColors(tv_dcgp1, tv_dcgp1pj);
				
				tv_dcgp2.setText("-");
				tv_dcgp2pj.setText("-");
				setTextColors(tv_dcgp2, tv_dcgp2pj);
				
				tv_dcgp3.setText("-");
				tv_dcgp3pj.setText("-");
				setTextColors(tv_dcgp3, tv_dcgp3pj);
			}else {

				
				tv_bfgp1.setText(diagnosisProfductlist.get(Posation).getGp1Percent()+"");
				tv_gp1pj.setText(diagnosisProfductlist.get(Posation).getAvgGp1Percent()+"");
				setTextColors(tv_bfgp1, tv_gp1pj);
				
				tv_bfgp2.setText(diagnosisProfductlist.get(Posation).getGp2Percent()+"");
				tv_gp2pj.setText(diagnosisProfductlist.get(Posation).getAvgGp2Percent()+"");
				setTextColors(tv_bfgp2, tv_gp2pj);
				
				tv_bfgp3.setText(diagnosisProfductlist.get(Posation).getGp3Percent()+"");
				tv_gp3pj.setText(diagnosisProfductlist.get(Posation).getAvgGp3Percent()+"");
				setTextColors(tv_bfgp3, tv_gp3pj);
				
				tv_dcgp1.setText(diagnosisProfductlist.get(Posation).getOneCarGp1()+"");
				tv_dcgp1pj.setText(diagnosisProfductlist.get(Posation).getAvgOneCarGp1()+"");
				setTextColors(tv_dcgp1, tv_dcgp1pj);
				
				tv_dcgp2.setText(diagnosisProfductlist.get(Posation).getOneCarGp2()+"");
				tv_dcgp2pj.setText(diagnosisProfductlist.get(Posation).getAvgOneCarGp2()+"");
				setTextColors(tv_dcgp2, tv_dcgp2pj);
				
				tv_dcgp3.setText(diagnosisProfductlist.get(Posation).getOneCarGp3()+"");
				tv_dcgp3pj.setText(diagnosisProfductlist.get(Posation).getAvgOneCarGp3()+"");
				setTextColors(tv_dcgp3, tv_dcgp3pj);
			}
						
//---------------------------------------------------------
			if("仅运营权限".equals(premission)||"销售半权限".equals(premission)){
				gp_number.setText("-");// GP1
				averagel_gp_number.setText("-");
				setTextColors(gp_number, averagel_gp_number);
				
				gp3_number.setText("-");// GP3
				average3_gp_number.setText("-");
				setTextColors(gp3_number, average3_gp_number);
				
				// 精品，车贷，保险，延保，数据set
				finenumber.setText("-");// 精品
				fine_averagel_number.setText("-");
				setTextColors(finenumber, fine_averagel_number);
				
				fine_profit_number.setText("-");// 单车毛利
				fine_averagel_profit_number.setText("-");
				setTextColors(fine_profit_number, fine_averagel_profit_number);

				carloannumber.setText("-");// 车贷渗透率
				carloa_averagel_number.setText("-");
				setTextColors(carloannumber, carloa_averagel_number);
				
				carloan_profit_number.setText("-");// 毛利
				carloa_averagel_profit_number
						.setText("-");
				setTextColors(carloan_profit_number, carloa_averagel_profit_number);

				insurancenumber.setText("-");// 新保渗透率
				insurance_averagel_number.setText("-");// 团队平均
				setTextColors(insurancenumber, insurance_averagel_number);
				insurance_profit_number.setText("-");// 单车毛利
				insurance_averagel_profit_number.setText("-");
				setTextColors(insurance_profit_number, insurance_averagel_profit_number);

				extendednumber.setText("-");// 延保渗透率
				extended_averagel_number.setText("-");
				setTextColors(extendednumber, extended_averagel_number);
				extended_profit_number.setText("-");
				extended_averagel_profit_number.setText("-");
				setTextColors(extended_profit_number, extended_averagel_profit_number);

				// 上牌
				shangPaiNumber.setText("-");// 上牌渗透率
				shangPai_averagel_number.setText("-");
				setTextColors(shangPaiNumber, shangPai_averagel_number);
				shangPai_profit_number.setText("-");// 毛利
				shangPai_averagel_profit_number.setText("-");
				setTextColors(shangPai_profit_number, shangPai_averagel_profit_number);
				
				// 置换
				zhiHuanNumber.setText("-");//  置换率
				zhiHuan_averagel_number.setText("-");
				setTextColors(zhiHuanNumber, zhiHuan_averagel_number);
				zhiHuan_profit_number.setText("-");// 毛利
				zhiHuan_averagel_profit_number.setText("-");
				setTextColors(zhiHuan_profit_number, zhiHuan_averagel_profit_number);
			}else{
			gp_number.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getGp1()));// GP1
			averagel_gp_number.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getAvg_GP1()));
			setTextColors(gp_number, averagel_gp_number);
			
			gp3_number.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getGp3()));// GP3
			average3_gp_number.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getAvg_GP3()));
			setTextColors(gp3_number, average3_gp_number);
			
			// 精品，车贷，保险，延保，数据set
			finenumber.setText(diagnosisProfductlist.get(Posation).getAddingRate() + "");// 精品加装率
			fine_averagel_number.setText(diagnosisProfductlist.get(Posation).getAvg_AddingRate());//团队平均
			setTextColors(finenumber, fine_averagel_number);
			fine_profit_number.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getAddingRateAvgProfits()));// 单车毛利
			fine_averagel_profit_number.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getAvg_AddingRateAvgProfits()));//团队平均
			setTextColors(fine_profit_number, fine_averagel_profit_number);

			carloannumber.setText(diagnosisProfductlist.get(Posation).getCarLoanPerRate());// 车贷渗透率
			carloa_averagel_number.setText(diagnosisProfductlist.get(Posation).getAvg_CarLoanPerRate() + "");//团队平均
			setTextColors(carloannumber, carloa_averagel_number);
			
			carloan_profit_number.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getCarLoanPerRateAvgProfits()));// 毛利
			carloa_averagel_profit_number
					.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getAvg_CarLoanPerRateAvgProfits()));
			setTextColors(carloan_profit_number, carloa_averagel_profit_number);

			insurancenumber.setText(diagnosisProfductlist.get(Posation).getXinInsurPenRate());// 新保渗透率
			insurance_averagel_number.setText(diagnosisProfductlist.get(Posation).getAvg_XinInsurPenRate());// 团队平均
			setTextColors(insurancenumber, insurance_averagel_number);
			insurance_profit_number.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getXinInsurPenRateAvgProfits()));// 单车毛利
			insurance_averagel_profit_number
					.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getAvg_XinInsurPenRateAvgProfits()));//团队平均
			setTextColors(insurance_profit_number, insurance_averagel_profit_number);

			extendednumber.setText(diagnosisProfductlist.get(Posation).getExtendWarRate());// 延保渗透率
			extended_averagel_number.setText(diagnosisProfductlist.get(Posation).getAvg_ExtendWarRate());//团队平均
			setTextColors(extendednumber, extended_averagel_number);
			extended_profit_number.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getExtendWarRateAvgProfits()));
			extended_averagel_profit_number
					.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getAvg_ExtendWarRateAvgProfits()));//团队平均
			setTextColors(extended_profit_number, extended_averagel_profit_number);

			// 上牌
			shangPaiNumber.setText(diagnosisProfductlist.get(Posation).getOnCardPerRate());// 上牌渗透率
			shangPai_averagel_number.setText(diagnosisProfductlist.get(Posation).getAvg_onCardPerRate());
			setTextColors(shangPaiNumber, shangPai_averagel_number);
			shangPai_profit_number.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getOnCardPenRateAvgProfits()));// 毛利
			shangPai_averagel_profit_number
					.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getAvg_onCardPenRateAvgProfits()));
			setTextColors(shangPai_profit_number, shangPai_averagel_profit_number);
			
			// 置换
			zhiHuanNumber.setText(diagnosisProfductlist.get(Posation).getReplacementRate());//  置换率
			zhiHuan_averagel_number.setText(diagnosisProfductlist.get(Posation).getAvg_ReplacementRate());
			setTextColors(zhiHuanNumber, zhiHuan_averagel_number);
			zhiHuan_profit_number.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getOtherPenRateAvgProfits()));// 毛利
			zhiHuan_averagel_profit_number
					.setText(StringUtils.intDivTo0(diagnosisProfductlist.get(Posation).getAvg_otherPenRateAvgProfits()));
			setTextColors(zhiHuan_profit_number, zhiHuan_averagel_profit_number);
			}
		}
	};
	private RelativeLayout layoutStartTime;
	private TextView txtStartTime;
	private RelativeLayout layoutEndTime;
	private TextView txtEndTime;
	private TextView datas_name5;
	private TextView datas_name6;
	private TextView jiedai_linner;
	private TextView jiedai_averagelnumber;
	private TextView gp3_number;
	private TextView average3_gp_number;
	private TextView shangPaiNumber;
	private TextView shangPai_profit_number;
	private TextView shangPai_averagel_number;
	private TextView shangPai_averagel_profit_number;
	private TextView zhiHuanNumber;
	private TextView zhiHuan_profit_number;
	private TextView zhiHuan_averagel_number;
	private TextView zhiHuan_averagel_profit_number;
	private TextView tv_jiedai;
	private TextView tv_gp1;
	private TextView tv_gp3;
	/**权限*/
//	private String mPremission;
	private ArrayList<String> checkSalesConsultant;
	private String check_car;
	private ArrayList<IViewSCBean> mConsultantList;

	private void initData() {
		timeFormat = "yyyy-MM-dd";
		addTodoPickerViewUtils = new AddTodoPickerViewUtils(DiagnosisProductActivity.this);
		gson = new Gson();
		// 日期默认设置为本月日期
		// DateAndTimeUtils dateAndTimeUtils = new DateAndTimeUtils();
		// if (productdata != null) {
		// mCurrentDate = dateAndTimeUtils.getDateTimeForStr("yyyy-MM",
		// productdata);
		// diagdetal_dates.setText(productdata + "");
		// getData();
		// }

		if (null == ProfitApplication.mStartDate) {
			// 默认显示当前月
			mCurrentDate = DateAndTimeUtils.getCurrentDate(timeFormat);
		} else {
			mCurrentDate = ProfitApplication.mStartDate;
		}
		initDate();
		getData();
	}

	

	private void initDate() {
		if (null == ProfitApplication.mStartDate) {
			mStartDate = DateAndTimeUtils.getCurrentMonthOfFirstDay();
		} else {
			mStartDate = ProfitApplication.mStartDate;
		}
		txtStartTime.setText(DateAndTimeUtils.getTimeForDate(timeFormat, mStartDate));

		if (null == ProfitApplication.mEndDate) {
			mEndDate = DateAndTimeUtils.getCurrentDate(timeFormat);
			txtEndTime.setText(DateAndTimeUtils.getCurrentTime(timeFormat));
		} else {
			mEndDate = ProfitApplication.mEndDate;
			txtEndTime.setText(DateAndTimeUtils.getTimeForDate(timeFormat, mEndDate));
		}
	}

	/** 显示日期选择器 */
	private void showDateDialog(final TextView tvTime, final boolean isStart) {
		TimePickerViewDialog pvTimeDialog = addTodoPickerViewUtils
				.initDatePickerView(isStart ? "请选择开始时间" : "请选择结束时间").getPvTime();
		pvTimeDialog.getDialog().show();

		pvTimeDialog.setOnTimeSelectListener(new TimePickerViewDialog.OnTimeSelectListener() {

			@Override
			public void onTimeSelect(Date date) {
				String strDate = null;
				if (isStart) {
//					strDate = addTodoPickerViewUtils.getDateNoDay(date) + "-01";
					strDate = addTodoPickerViewUtils.getDate(date);//开始日期
					mStartDate = DateAndTimeUtils.getDateTimeForStr(timeFormat, strDate);
				} else {
//					strDate = DateAndTimeUtils.getLastDateOfMonth(date);
					strDate = addTodoPickerViewUtils.getDate(date);//结束日期
					mEndDate = DateAndTimeUtils.getDateTimeForStr(timeFormat, strDate);
				}

				if (isStart) {
					tvTime.setText(strDate);
					String strEndDate = DateAndTimeUtils.getLastDateOfMonth(mStartDate);
					mEndDate = DateAndTimeUtils.getDateTimeForStr(timeFormat, strEndDate);

					txtEndTime.setText(strEndDate);
				} else {
					if(DateAndTimeUtils.isEqual(mStartDate, mEndDate)){
						tvTime.setText(strDate);
					}else if (!DateAndTimeUtils.isBefore(mStartDate, mEndDate)) {
						ToastUtils.showMyToast(DiagnosisProductActivity.this, "“结束时间”不可小于“开始时间”");
						return;
					} else {
						tvTime.setText(strDate);
					}
				}
				// 时间刷新
				TimeRefreshUtils.sendTimeRefreshMsg(DiagnosisProductActivity.this, mStartDate, mEndDate);

				getData();
			}

			@Override
			public void onCancel() {

			}
		});
	}


	private void refreshDate(boolean isLeft) {
		String strCurrentDate = DateAndTimeUtils.getTimeForDate(timeFormat, mCurrentDate);
		long currentLongTime;

		if (isLeft) {
			currentLongTime = DateAndTimeUtils
					.lastMonth(DateAndTimeUtils.getLongTimeForStr(timeFormat, strCurrentDate));
			Date lastDate = DateAndTimeUtils.getDateTimeForStr(timeFormat,
					DateAndTimeUtils.getTimeForLongTime(timeFormat, currentLongTime));

			mCurrentDate = lastDate;

		} else {
			boolean isCurrentMonth = DateAndTimeUtils
					.orCurrentMonth(DateAndTimeUtils.getLongTimeForStr(timeFormat, strCurrentDate));
			if (isCurrentMonth) {
				return;
			}

			currentLongTime = DateAndTimeUtils
					.nextMonth(DateAndTimeUtils.getLongTimeForStr(timeFormat, strCurrentDate));
			Date nextDate = DateAndTimeUtils.getDateTimeForStr(timeFormat,
					DateAndTimeUtils.getTimeForLongTime(timeFormat, currentLongTime));

			if (!DateAndTimeUtils.isAfter(nextDate, DateAndTimeUtils.getCurrentDate(timeFormat))) {
				mCurrentDate = nextDate;
			}
		}

		String strDate = DateAndTimeUtils.getFirstAndLastDayOfMonth(mCurrentDate);
		// diagdetal_dates.setText(addTodoPickerViewUtils.getDateNoDay(mCurrentDate));
		getData();
	}

	public void setTextColors(TextView numbers1, TextView numbers2) {
		String number1;
		String number2;
		if (numbers1 != null && numbers2 != null) {
			number1 = numbers1.getText().toString();
			number2 = numbers2.getText().toString();
			number1 = number1.replace(",", "");
			number2 = number2.replace(",", "");
			number1 = number1.replace(".", "");
			number2 = number2.replace(".", "");
			CKNumFormat ckNumFormat = new CKNumFormat();
			if (ckNumFormat.isNumeric(number1) && ckNumFormat.isNumeric(number2)) {
				int nubmerInt1 = Integer.parseInt(number1);
				int numberInt2 = Integer.parseInt(number2);
				if (nubmerInt1 > numberInt2) {
					numbers1.setTextColor(Color.parseColor("#79be0c"));
				} else if(nubmerInt1 == numberInt2){
					numbers1.setTextColor(Color.parseColor("#79be0c"));
				}
				else {
					numbers1.setTextColor(Color.parseColor("#ff4400"));
				}
			}
		}
	}
	
	public void setoterTextColors(TextView tv_kcsl2, TextView tv_kcslpj2) {
		// TODO Auto-generated method stub
		String number1;
		String number2;
		if (tv_kcsl2 != null && tv_kcslpj2 != null) {
			number1 = tv_kcsl2.getText().toString();
			number2 = tv_kcslpj2.getText().toString();
			number1 = number1.replace(",", "");
			number2 = number2.replace(",", "");
			number1 = number1.replace(".", "");
			number2 = number2.replace(".", "");
			CKNumFormat ckNumFormat = new CKNumFormat();
			if (ckNumFormat.isNumeric(number1) && ckNumFormat.isNumeric(number2)) {
				int nubmerInt1 = Integer.parseInt(number1);
				int numberInt2 = Integer.parseInt(number2);
				if (nubmerInt1 > numberInt2) {
					tv_kcsl2.setTextColor(Color.parseColor("#ff4400"));
				} else if(nubmerInt1 == numberInt2){
					tv_kcsl2.setTextColor(Color.parseColor("#79be0c"));
				}
				else {
					tv_kcsl2.setTextColor(Color.parseColor("#79be0c"));
				}
			}
		}
	}
}
