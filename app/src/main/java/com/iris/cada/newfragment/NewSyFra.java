package com.iris.cada.newfragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;

import com.bigkoo.pickerview.TimePickerViewDialog;
import com.google.gson.Gson;
import com.iris.cada.AppManager;
import com.iris.cada.ProfitApplication;
import com.iris.cada.Statcode;
import com.iris.cada.activity.MymessAct;
import com.iris.cada.activity.NewMainAct;
import com.iris.cada.activity.NewSettingAct;
import com.iris.cada.activity.SeparateAct;
import com.iris.cada.activity.ShmainAct;
import com.iris.cada.entity.IViewFirst;
import com.iris.cada.entity.ShsyBean;
import com.iris.cada.fragment.NewBaseFrag;
import com.iris.cada.utils.DateAndTimeUtils;
import com.iris.cada.utils.StringUtils;
import com.iris.cada.utils.TimeRefreshUtils;
import com.iris.cada.utils.ToastUtils;
import com.iris.cada.utils.WebUtils;
import com.iris.cada.view.KpiDialog;
import com.iris.cada.view.pickerviewhelper.AddTodoPickerViewUtils;
import com.iris.cada.R;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class NewSyFra extends NewBaseFrag {
	@ViewInject(R.id.ll_xs)
	private LinearLayout ll_xs;
	@ViewInject(R.id.ll_sh)
	private LinearLayout ll_sh;
	@ViewInject(R.id.iv_sysetting)
	private ImageView iv_sysetting;
	@ViewInject(R.id.tv_syname)
	private TextView tv_syname;
	@ViewInject(R.id.tv_start)
	private TextView tv_start;
	@ViewInject(R.id.tv_end)
	private TextView tv_end;
	@ViewInject(R.id.tv_xswxtc)
	private TextView tv_xswxtc;
	@ViewInject(R.id.tv_xsdccz)
	private TextView tv_xsdccz;
	@ViewInject(R.id.tv_xszhsr)
	private TextView tv_xszhsr;
	@ViewInject(R.id.tv_xszhml)
	private TextView tv_xszhml;

	@ViewInject(R.id.tv_xstime)
	private TextView tv_xstime;
	@ViewInject(R.id.tv_xstime1)
	private TextView tv_xstime1;
	@ViewInject(R.id.tv_xstime2)
	private TextView tv_xstime2;
	@ViewInject(R.id.tv_xstime3)
	private TextView tv_xstime3;

	@ViewInject(R.id.tv_xscjs)
	private TextView tv_xscjs;
	@ViewInject(R.id.tv_xszjcs)
	private TextView tv_xszjcs;
	@ViewInject(R.id.tv_xsxszhsr)
	private TextView tv_xsxszhsr;
	@ViewInject(R.id.tv_xsxszhml)
	private TextView tv_xsxszhml;

	@ViewInject(R.id.tv_xsruntime)
	private TextView tv_xsruntime;
	@ViewInject(R.id.tv_xsruntime1)
	private TextView tv_xsruntime1;
	@ViewInject(R.id.tv_xsruntime2)
	private TextView tv_xsruntime2;
	@ViewInject(R.id.tv_xsruntime3)
	private TextView tv_xsruntime3;

	@ViewInject(R.id.tv_num)
	private TextView tv_num;

	private String key;
	private Date startdate, enddate;// 开始时间Date格式，结束时间Date格式
	private String stime, etime;// 开始时间，结束时间
	private String mTimeFormat;
	private AddTodoPickerViewUtils addPickerViewUtils;
	private String premission;// 权限

	private Gson gson;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Statcode.SHSUC:// 获取售后数据成功
//				initShdate(msg);
				break;
			case Statcode.XSSUC:// 获取销售数据成功
				initSxdate(msg);
				break;

			case Statcode.SHFAILED:// 获取售后数据失败
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				break;

			case Statcode.XS_FAILED:// 获取销售数据失败
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_data));
				break;

			case ProfitApplication.SERVER_FAILED:// 连接服务器失败
				ToastUtils.showMyToast(getActivity(), getString(R.string.error_net));
				break;
			}
		}

	};

	@Override
	public View initView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_sy, null);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		gson = new Gson();
		tv_syname.setText(ProfitApplication.loginBackInfo.getDealerName());
		addPickerViewUtils = new AddTodoPickerViewUtils(getActivity());
		mTimeFormat = "yyyy-MM-dd";
		tv_start.setText(WebUtils.getStart());// 开始时间
		tv_end.setText(WebUtils.getend());// 结束时间
		startdate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, tv_start.getText().toString());// 赋值后的开始时间锉
		
		 premission = ProfitApplication.loginBackInfo.getPermission();		
			if(TextUtils.isEmpty(premission)){
					premission="总经理";
			}
		if (Statcode.Messnum == 0) {
			tv_num.setVisibility(View.GONE);
		} else {
			tv_num.setVisibility(View.VISIBLE);
			tv_num.setText(Statcode.Messnum + "");
		}

//		getShinfo(tv_start.getText().toString(), tv_end.getText().toString());// 售后数据请求
		getProfitInfo(tv_start.getText().toString(), tv_end.getText().toString());// 销售数据请求

	}

	/*
	 * 获取售后数据
	 **/
	private void getShinfo(String startDate, String endDate) {
		// TODO Auto-generated method stub
		ProfitApplication.profitNetService.getShsydate(startDate, endDate, handler);
	}

	/*
	 * 获取销售数据
	 **/
	private void getProfitInfo(String startDate, String endDate) {
		ProfitApplication.profitNetService.getiviewFirstHomeServlet(startDate, endDate, handler);
	}

	private void initSxdate(Message msg) {
		// TODO Auto-generated method stub
		String mes = (String) msg.obj;
		try {
			String strMsg = (String) msg.obj;
			JSONObject object = new JSONObject(mes);
			IViewFirst iViewFirstdata = new Gson().fromJson(object.toString(), IViewFirst.class);

			if ("售后半权限".equals(premission)) {
				tv_xscjs.setText("-");
				tv_xszjcs.setText("-");
				tv_xsxszhsr.setText("-");
				tv_xsxszhml.setText("-");
			} else {
				if ("仅运营权限".equals(premission) || "销售半权限".equals(premission)) {
					tv_xsxszhsr.setText("-");
					tv_xsxszhml.setText("-");
				} else {
					tv_xsxszhsr.setText(StringUtils.intDivTo0(iViewFirstdata.getGross()));// 销售综合收入
					tv_xsxszhml.setText(StringUtils.intDivTo0(iViewFirstdata.getRevenue()));// 销售综合毛利
				}
				tv_xscjs.setText(iViewFirstdata.getTurnover());
				tv_xszjcs.setText(iViewFirstdata.getRetail());
			}
			setShowview(tv_xsruntime, iViewFirstdata.getTimeTurnover());
			setShowview(tv_xsruntime1, iViewFirstdata.getTimeRetail());
			setShowview(tv_xsruntime2, iViewFirstdata.getTimeGross());
			setShowview(tv_xsruntime3, iViewFirstdata.getTimeRevenue());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 设置字体显示内容
	private void setShowview(TextView tv, String timeTurnover) {
		// TODO Auto-generated method stub
		if (!TextUtils.isEmpty(timeTurnover)) {
			tv.setText("更新于" + timeTurnover);// 成交数更新时间
		} else {
			tv.setText("更新于" + "--");// 成交数更新时间
		}
	}

	private void initShdate(Message msg) {
		// TODO Auto-generated method stub
		try {
			String mes = (String) msg.obj;
			JSONObject object = new JSONObject(mes).getJSONObject("data");
			// String data=object.getString("data");
			ShsyBean bean = gson.fromJson(object.toString(), ShsyBean.class);
			if (bean != null) {
				// tv_xswxtc.setText(bean.getMaintenanceTimes() + "");
				// tv_xsdccz.setText(bean.getOutputValue() + "");
				// tv_xszhsr.setText(bean.getIncome() + "");
				// tv_xszhml.setText(bean.getProfit() + "");

				if ("销售半权限".equals(premission) || "仅运营权限".equals(premission)) {
					tv_xswxtc.setText("-");
					tv_xsdccz.setText("-");
					tv_xszhsr.setText("-");
					tv_xszhml.setText("-");
				} else {
					if ("售后半权限".equals(premission)) {
						tv_xsdccz.setText("-");
						tv_xszhsr.setText("-");
						tv_xszhml.setText("-");
					} else {
						tv_xsdccz.setText(bean.getOutputValue() + "");
						tv_xszhsr.setText(bean.getIncome() + "");
						tv_xszhml.setText(bean.getProfit() + "");
					}
					tv_xswxtc.setText(bean.getMaintenanceTimes() + "");
				}

				setShowview(tv_xstime, bean.getReturntime());
				setShowview(tv_xstime1, bean.getReturntime());
				setShowview(tv_xstime2, bean.getReturntime());
				setShowview(tv_xstime3, bean.getReturntime());

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnClick({ R.id.ll_xs, R.id.ll_sh, R.id.iv_sysetting, R.id.tv_start, R.id.tv_end, R.id.ll_kpi, R.id.ll_kpish,
			R.id.iv_xx })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.ll_xs:
			if ("售后全权限".equals(premission) || "售后半权限".equals(premission)) {
				ToastUtils.showMyToast(getActivity(), "暂无权限");
			} else {
				Intent intent2 = new Intent(getActivity(), NewMainAct.class);
				startActivity(intent2);
				getActivity().finish();
			}

			break;

		case R.id.ll_sh:
			if ("销售全权限".equals(premission) || "销售半权限".equals(premission)) {
				ToastUtils.showMyToast(getActivity(), "暂无权限");
			} else {
				Intent intent1 = new Intent(getActivity(), ShmainAct.class);
				startActivity(intent1);
				getActivity().finish();
			}
			break;

		case R.id.iv_sysetting:

			Intent intent = new Intent(getActivity(), NewSettingAct.class);
			startActivity(intent);

			break;
		case R.id.tv_start:
			showDateDialog(true);// 选择开始时间
			break;

		case R.id.tv_end:
			showDateDialog(false);// 选择开始时间
			break;

		case R.id.ll_kpi:
			/*
			 * Toast.makeText(SeparateAct.this, "点击了销售的kpi",
			 * Toast.LENGTH_SHORT).show();
			 */
			showKpiDialog(0);
			break;

		case R.id.ll_kpish:
			showKpiDialog(1);
			/*
			 * Toast.makeText(SeparateAct.this, "点击了售后的kpi",
			 * Toast.LENGTH_SHORT).show();
			 */
			break;

		case R.id.iv_xx:
			tv_num.setVisibility(View.GONE);
			openActivity(MymessAct.class);
			break;

		}

	}

	private void showKpiDialog(int type) {
		// TODO Auto-generated method stub
		List<String> list = new ArrayList<String>();
		if (type == 0) {
			list.add("成交数&当期新增订单量");
			list.add("总交车数&当期交车总数");
			list.add("销售综合收入&当期开票价格总额+衍生业务收入总计");
			list.add("销售综合毛利&进销差（GP1）+销售返利总计+衍生业务毛利总计");
		} else if (type == 1) {
			list.add("维修台次&按日去重的进厂车辆数");
			list.add("单车产值&维修收入除以维修台次");
			list.add("售后综合收入&售后主营+精品附件+续保延保的收入合计");
			list.add("售后综合毛利&售后主营+精品附件+续保延保的毛利合计");
		}
		KpiDialog kpidialog = new KpiDialog(getActivity(), list).builder();
		kpidialog.show();
		kpidialog.setCanceledOnTouchOutside(true);
	}

	/****
	 * 
	 * @param b
	 */
	private void showDateDialog(final boolean b) {
		// TODO Auto-generated method stub
		TimePickerViewDialog pvTimeDialog = addPickerViewUtils.initDatePickerView(b ? "请选择开始时间" : "请选择结束时间")
				.getPvTime();
		pvTimeDialog.getDialog().show();
		pvTimeDialog.setOnTimeSelectListener(new TimePickerViewDialog.OnTimeSelectListener() {

			@Override
			public void onTimeSelect(Date date) {
				// TODO Auto-generated method stub
				if (b) {
					if (!DateAndTimeUtils.isBefore(date, DateAndTimeUtils.getCurrentDate(mTimeFormat))) {// 开始时间比当前时间大
						ToastUtils.showMyToast(getActivity(), "开始时间不可大于当前时间");
						return;
					} else {// 开始时间比当前时间小
						stime = addPickerViewUtils.getDate(date);// 开始时间
						tv_start.setText(stime);

						startdate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, stime);// 开始时间锉
						ProfitApplication.mStartDate = startdate;
						etime = DateAndTimeUtils.getLastDateOfMonth(startdate);
						enddate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, etime);

						if (!DateAndTimeUtils.isBefore(enddate, DateAndTimeUtils.getCurrentDate(mTimeFormat))) {
							tv_end.setText(DateAndTimeUtils.getCurrentTime(mTimeFormat));// 当前时间
							ProfitApplication.mEndDate = DateAndTimeUtils.getCurrentDate(mTimeFormat);
						} else {
							tv_end.setText(etime);
							ProfitApplication.mEndDate = enddate;
						}
//						getShinfo(tv_start.getText().toString(), tv_end.getText().toString());// 售后数据请求
						getProfitInfo(tv_start.getText().toString(), tv_end.getText().toString());// 销售数据请求
						TimeRefreshUtils.sendTimeRefreshMsg(getContext(), startdate, enddate);
					}

				} else {
					if (!DateAndTimeUtils.isBefore(date, DateAndTimeUtils.getCurrentDate(mTimeFormat))) {// 结束时间比当前时间大
						ToastUtils.showMyToast(getActivity(), "结束时间不可大于当前时间");
						return;
					} else {
						etime = addPickerViewUtils.getDate(date);// 结束时间
						enddate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, etime);// 结束时间锉
						if (DateAndTimeUtils.isEqual(startdate, enddate)) {// 结束时间等于开始时间
							tv_end.setText(etime);
							ProfitApplication.mEndDate = enddate;
//							getShinfo(tv_start.getText().toString(), tv_end.getText().toString());// 售后数据请求
							getProfitInfo(tv_start.getText().toString(), tv_end.getText().toString());// 销售数据请求
							TimeRefreshUtils.sendTimeRefreshMsg(getContext(), startdate, enddate);
						} else if (!DateAndTimeUtils.isBefore(startdate, enddate)) {// 结束时间小于开始时间
							ToastUtils.showMyToast(getActivity(), "“结束时间”不可小于“开始时间”");
							return;
						} else {// 结束时间大于开始时间小于当前时间
							tv_end.setText(etime);
							ProfitApplication.mEndDate = enddate;
//							getShinfo(tv_start.getText().toString(), tv_end.getText().toString());// 售后数据请求
							getProfitInfo(tv_start.getText().toString(), tv_end.getText().toString());// 销售数据请求
							TimeRefreshUtils.sendTimeRefreshMsg(getContext(), startdate, enddate);
						}
					}

				}

			}

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (hidden) {// 被影藏

		} else {// 可见
			if (tv_start.getText().toString().equals(WebUtils.getStart())
					&& tv_end.getText().equals(WebUtils.getStart())) {

			} else {
				tv_start.setText(WebUtils.getStart());// 开始时间
				tv_end.setText(WebUtils.getend());// 结束时间
				startdate = DateAndTimeUtils.getDateTimeForStr(mTimeFormat, tv_start.getText().toString());// 赋值后的开始时间锉

//				getShinfo(tv_start.getText().toString(), tv_end.getText().toString());// 售后数据请求
				getProfitInfo(tv_start.getText().toString(), tv_end.getText().toString());// 销售数据请求

			}
		}
	}

	/** 给首页去调用的方法 **/
	public void upNum() {
		// TODO Auto-generated method stub
		if (Statcode.Messnum == 0) {
			tv_num.setVisibility(View.GONE);
		} else {
			tv_num.setVisibility(View.VISIBLE);
			tv_num.setText(Statcode.Messnum + "");
		}
	}

}
