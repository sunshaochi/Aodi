package com.iris.cada.adapter;

import java.util.List;

import com.iris.cada.ProfitApplication;
import com.iris.cada.entity.ILogData;
import com.iris.cada.utils.CommonAdapter;
import com.iris.cada.utils.CommonHolder;
import com.iris.cada.R;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 自定义适配器
 * 
 * @author jiahaoGuo
 * @version 2015-11-9 15:00:45
 */
public class ReportListAdapter extends CommonAdapter<ILogData> {
	private int contentView;
	private List<ILogData> dataCompare;
	private Context context;
	private int number;
	private Boolean isDay;
	private Boolean isProfit;

	public ReportListAdapter(Context context, List<ILogData> datas, List<ILogData> compareData, int number,
			Boolean isDay) {
		super(context, datas);
		this.dataCompare = compareData;
		this.context = context;
		this.number = number;
		this.isDay = isDay;
		switch (number) {
		case 1:
			contentView = R.layout.item_exhibition_listview_number1;
			break;
		case 2:
			contentView = R.layout.item_exhibition_listview_number2;
			break;
		case 3:
			contentView = R.layout.item_exhibition_listview_number3;
			break;
		case 4:
			contentView = R.layout.item_exhibition_listview_number4;
			break;
		case 5:
			contentView = R.layout.item_exhibition_listview_number5;
			break;
		}
	}

	public void setData(List<ILogData> datas, List<ILogData> compareData, Boolean isDay) {
		this.datas = datas;
		this.dataCompare = compareData;
		this.isDay = isDay;
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommonHolder holder = CommonHolder.get(context, convertView, parent, contentView, position);
		switch (number) {
		case 1:
			isProfit = false;
			getView1(holder, position);
			break;
		case 2:
			isProfit = false;
			getView2(holder, position);
			break;
		case 3:
			isProfit = false;
			getView3(holder, position);
			break;
		case 4:
			isProfit = false;
			getView4(holder, position);
			break;
		case 5:
			isProfit = true;
			getProfitView(holder, position);
			break;
		}
		return holder.getConvertView();
	}

	private void getView1(CommonHolder holder, int position) {
		ILogData iLogData = datas.get(position);
		ILogData iLogDataCompare = new ILogData();
		if (!isDay) {
			iLogDataCompare = dataCompare.get(position);
		}
		holder.getTextView(R.id.tv_model).setText(iLogData.getModels());
		if (isDay) {
			toSpanableString(iLogData.getInRoomFir(), "", holder.getTextView(R.id.tv_new_add));
			toSpanableString(iLogData.getInRoomSec(), "", holder.getTextView(R.id.tv_inroom));
			toSpanableString(iLogData.getQuote(), "", holder.getTextView(R.id.tv_quote));
			toSpanableString(iLogData.getOrder(), "", holder.getTextView(R.id.tv_order));
			toSpanableString(iLogData.getRetail(), "", holder.getTextView(R.id.tv_retail));
		} else {
			toSpanableString(iLogData.getInRoomFir(),
					ProfitApplication.unitConversion(iLogData.getInRoomFir(), iLogDataCompare.getInRoomFir()),
					holder.getTextView(R.id.tv_new_add));
			toSpanableString(iLogData.getInRoomSec(),
					ProfitApplication.unitConversion(iLogData.getInRoomSec(), iLogDataCompare.getInRoomSec()),
					holder.getTextView(R.id.tv_inroom));
			toSpanableString(iLogData.getQuote(), "", holder.getTextView(R.id.tv_quote));
			toSpanableString(iLogData.getOrder(),
					ProfitApplication.unitConversion(iLogData.getOrder(), iLogDataCompare.getOrder()),
					holder.getTextView(R.id.tv_order));
			toSpanableString(iLogData.getRetail(), "", holder.getTextView(R.id.tv_retail));
		}
	}

	private void getView2(CommonHolder holder, int position) {
		ILogData iLogData = datas.get(position);
		ILogData iLogDataCompare = new ILogData();
		if (!isDay) {
			iLogDataCompare = dataCompare.get(position);
		}
		holder.getTextView(R.id.tv_model).setText(iLogData.getModels());
		if (isDay) {
			toSpanableString(iLogData.getCollectible(), "", holder.getTextView(R.id.tv_quoterate));
			toSpanableString(iLogData.getFinance(), "", holder.getTextView(R.id.tv_products));
			toSpanableString(iLogData.getInsurance(), "", holder.getTextView(R.id.tv_transrate));
			toSpanableString(iLogData.getExInsurance(), "", holder.getTextView(R.id.tv_dealrate));
			toSpanableString(iLogData.getReplacement(), "", holder.getTextView(R.id.tv_tdriverate));
		} else {
			toSpanableString(iLogData.getCollectible(),
					ProfitApplication.unitConversion(iLogData.getCollectible(), iLogDataCompare.getCollectible()),
					holder.getTextView(R.id.tv_quoterate));
			toSpanableString(iLogData.getFinance(),
					ProfitApplication.unitConversion(iLogData.getFinance(), iLogDataCompare.getFinance()),
					holder.getTextView(R.id.tv_products));
			toSpanableString(iLogData.getInsurance(),
					ProfitApplication.unitConversion(iLogData.getInsurance(), iLogDataCompare.getInsurance()),
					holder.getTextView(R.id.tv_transrate));
			toSpanableString(iLogData.getExInsurance(),
					ProfitApplication.unitConversion(iLogData.getExInsurance(), iLogDataCompare.getExInsurance()),
					holder.getTextView(R.id.tv_dealrate));
			toSpanableString(iLogData.getReplacement(),
					ProfitApplication.unitConversion(iLogData.getReplacement(), iLogDataCompare.getReplacement()),
					holder.getTextView(R.id.tv_tdriverate));
		}
	}

	private void getView3(CommonHolder holder, int position) {
		ILogData iLogData = datas.get(position);
		ILogData iLogDataCompare = new ILogData();
		// if (!isDay) {
		iLogDataCompare = dataCompare.get(position);
		// }
		holder.getTextView(R.id.tv_model).setText(iLogData.getModels());
		// if (isDay) {
		// toSpanableString(iLogData.getQuoteRate(), "",
		// holder.getTextView(R.id.tv_inroom));
		// toSpanableString(iLogData.getTransRate(), "",
		// holder.getTextView(R.id.tv_bid_order));
		// toSpanableString(iLogData.getDealRate(), "",
		// holder.getTextView(R.id.tv_order_delivery));
		// } else {
		if (position == 0) {
			// 率总计
			String inRoomQuote = ProfitApplication.int2percent(ProfitApplication.stringIsNull(iLogData.getQuote()),
					ProfitApplication.stringIsNull(iLogData.getInRoom()));
			String quoteOrder = ProfitApplication.int2percent(ProfitApplication.stringIsNull(iLogData.getOrder()),
					ProfitApplication.stringIsNull(iLogData.getQuote()));
			String orderRetail = ProfitApplication.int2percent(ProfitApplication.stringIsNull(iLogData.getRetail()),
					ProfitApplication.stringIsNull(iLogData.getOrder()));
			String turnoverRate = ProfitApplication.int2percent(ProfitApplication.stringIsNull(iLogData.getOrder()),
					ProfitApplication.stringIsNull(iLogData.getInRoomFir()));

			toSpanableString(inRoomQuote, "", holder.getTextView(R.id.tv_inroom));
			toSpanableString(quoteOrder, "", holder.getTextView(R.id.tv_bid_order));
			toSpanableString(turnoverRate, ProfitApplication.conversStr(turnoverRate, iLogDataCompare.getTransRateFir()),
					holder.getTextView(R.id.tv_turnover_rate));
			toSpanableString(orderRetail, "", holder.getTextView(R.id.tv_order_delivery));
		} else {
			toSpanableString(iLogData.getQuoteRate(), "", holder.getTextView(R.id.tv_inroom));
			toSpanableString(iLogData.getTransRate(), "", holder.getTextView(R.id.tv_bid_order));
			toSpanableString(iLogData.getTransRateFir(),
					ProfitApplication.conversStr(iLogData.getTransRateFir(), iLogDataCompare.getTransRateFir()),
					holder.getTextView(R.id.tv_turnover_rate));
			toSpanableString(iLogData.getDealRate(), "", holder.getTextView(R.id.tv_order_delivery));
		}
		// }
	}

	private void getView4(CommonHolder holder, int position) {
		ILogData iLogData = datas.get(position);
		ILogData iLogDataCompare = new ILogData();
		// if (!isDay) {
		iLogDataCompare = dataCompare.get(position);
		// }
		holder.getTextView(R.id.tv_model).setText(iLogData.getModels());
		// if (isDay) {
		toSpanableString(iLogData.getFinanceRate(), "", holder.getTextView(R.id.tv_finance));
		toSpanableString(iLogData.getInsuranceRate(), "", holder.getTextView(R.id.tv_insurance));
		toSpanableString(iLogData.getExInsuranceRate(), "", holder.getTextView(R.id.tv_yanbao));
		toSpanableString(iLogData.getReplacementRate(), "", holder.getTextView(R.id.tv_disp));
		// } else {
		if (position == 0) {
			// 率总计
			String collectible = ProfitApplication.int2percent(ProfitApplication.stringIsNull(iLogData.getCollectible()),
					ProfitApplication.stringIsNull(iLogData.getRetail()));
			String financeRate = ProfitApplication.int2percent(ProfitApplication.stringIsNull(iLogData.getFinanceRate()),
					ProfitApplication.stringIsNull(iLogData.getRetail()));
			String insuranceRate = ProfitApplication.int2percent(ProfitApplication.stringIsNull(iLogData.getFinance()),
					ProfitApplication.stringIsNull(iLogData.getRetail()));
			String exInsuranceRate = ProfitApplication.int2percent(ProfitApplication.stringIsNull(iLogData.getInsurance()),
					ProfitApplication.stringIsNull(iLogData.getRetail()));
			String replacementRate = ProfitApplication.int2percent(
					ProfitApplication.stringIsNull(iLogData.getExInsurance()),
					ProfitApplication.stringIsNull(iLogData.getRetail()));
			toSpanableString(collectible, ProfitApplication.conversStr(collectible, iLogDataCompare.getCollectibleRate()),
					holder.getTextView(R.id.tv_collectible));
			toSpanableString(financeRate, ProfitApplication.conversStr(financeRate, iLogDataCompare.getFinanceRate()),
					holder.getTextView(R.id.tv_finance));
			toSpanableString(insuranceRate,
					ProfitApplication.conversStr(insuranceRate, iLogDataCompare.getInsuranceRate()),
					holder.getTextView(R.id.tv_insurance));
			toSpanableString(exInsuranceRate,
					ProfitApplication.conversStr(exInsuranceRate, iLogDataCompare.getExInsuranceRate()),
					holder.getTextView(R.id.tv_yanbao));
			toSpanableString(replacementRate,
					ProfitApplication.conversStr(replacementRate, iLogDataCompare.getReplacementRate()),
					holder.getTextView(R.id.tv_disp));
		} else {
			toSpanableString(iLogData.getCollectibleRate(),
					ProfitApplication.conversStr(iLogData.getCollectibleRate(), iLogDataCompare.getCollectibleRate()),
					holder.getTextView(R.id.tv_collectible));
			toSpanableString(iLogData.getFinanceRate(),
					ProfitApplication.conversStr(iLogData.getFinanceRate(), iLogDataCompare.getFinanceRate()),
					holder.getTextView(R.id.tv_finance));
			toSpanableString(iLogData.getInsuranceRate(),
					ProfitApplication.conversStr(iLogData.getInsuranceRate(), iLogDataCompare.getInsuranceRate()),
					holder.getTextView(R.id.tv_insurance));
			toSpanableString(iLogData.getExInsuranceRate(),
					ProfitApplication.conversStr(iLogData.getExInsuranceRate(), iLogDataCompare.getExInsuranceRate()),
					holder.getTextView(R.id.tv_yanbao));
			toSpanableString(iLogData.getReplacementRate(),
					ProfitApplication.conversStr(iLogData.getReplacementRate(), iLogDataCompare.getReplacementRate()),
					holder.getTextView(R.id.tv_disp));
		}
		// }
	}

	/**
	 * 利润
	 * 
	 * @param holder
	 * @param position
	 */
	private void getProfitView(CommonHolder holder, int position) {
		ILogData iLogData = datas.get(position);
		ILogData iLogDataCompare = new ILogData();
		if (!isDay) {
			iLogDataCompare = dataCompare.get(position);
		}
		holder.getTextView(R.id.tv_model).setText(iLogData.getModels());
		double count = ProfitApplication.unitConversion(iLogData.getSingleCar())
				+ ProfitApplication.unitConversion(iLogData.getCollectible())
				+ ProfitApplication.unitConversion(iLogData.getFinance())
				+ ProfitApplication.unitConversion(iLogData.getInsurance())
				+ ProfitApplication.unitConversion(iLogData.getExInsurance())
				+ ProfitApplication.unitConversion(iLogData.getReplacement());
		if (isDay) {
			toSpanableString(ProfitApplication.unitConversion(iLogData.getSingleCar()) + "", "",
					holder.getTextView(R.id.tv_singlecar));
			toSpanableString(ProfitApplication.unitConversion(iLogData.getCollectible()) + "", "",
					holder.getTextView(R.id.tv_products));
			toSpanableString(ProfitApplication.unitConversion(iLogData.getFinance()) + "", "",
					holder.getTextView(R.id.tv_finance));
			toSpanableString(ProfitApplication.unitConversion(iLogData.getInsurance()) + "", "",
					holder.getTextView(R.id.tv_insurance));
			toSpanableString(ProfitApplication.unitConversion(iLogData.getExInsurance()) + "", "",
					holder.getTextView(R.id.tv_exinsurance));
			toSpanableString(ProfitApplication.unitConversion(iLogData.getReplacement()) + "", "",
					holder.getTextView(R.id.tv_replacement));
			toSpanableString(count + "", "", holder.getTextView(R.id.tv_count));
		} else {
			toSpanableString(ProfitApplication.unitConversion(iLogData.getSingleCar()) + "",
					ProfitApplication.conversion(iLogData.getSingleCar(), iLogDataCompare.getSingleCar()) + "",
					holder.getTextView(R.id.tv_singlecar));
			toSpanableString(ProfitApplication.unitConversion(iLogData.getCollectible()) + "",
					ProfitApplication.conversion(iLogData.getCollectible(), iLogDataCompare.getCollectible()) + "",
					holder.getTextView(R.id.tv_products));
			toSpanableString(ProfitApplication.unitConversion(iLogData.getFinance()) + "",
					ProfitApplication.conversion(iLogData.getFinance(), iLogDataCompare.getFinance()) + "",
					holder.getTextView(R.id.tv_finance));
			toSpanableString(ProfitApplication.unitConversion(iLogData.getInsurance()) + "",
					ProfitApplication.conversion(iLogData.getInsurance(), iLogDataCompare.getInsurance()) + "",
					holder.getTextView(R.id.tv_insurance));
			toSpanableString(ProfitApplication.unitConversion(iLogData.getExInsurance()) + "",
					ProfitApplication.conversion(iLogData.getExInsurance(), iLogDataCompare.getExInsurance()) + "",
					holder.getTextView(R.id.tv_exinsurance));
			toSpanableString(ProfitApplication.unitConversion(iLogData.getReplacement()) + "",
					ProfitApplication.conversion(iLogData.getReplacement(), iLogDataCompare.getReplacement()) + "",
					holder.getTextView(R.id.tv_replacement));
			toSpanableString(ProfitApplication.conStr(count) + "",
					ProfitApplication.conversion(
							ProfitApplication.conStr(ProfitApplication.stringIsFloat(count + "") * 1000) + "",
							iLogDataCompare.getProfit() + ""),
					holder.getTextView(R.id.tv_count));
		}
	}

	private void toSpanableString(String first, String second, TextView tv) {
		String firstk = "K";
		String secondk = "K";
		int color = R.color.text_grey;
		// 为第二页利润时
		if (isProfit) {
			if (!TextUtils.isEmpty(second) && second.equals("N/A")) {
				secondk = "";
			}
			if (ProfitApplication.convers(second) < 0) {
				color = R.color.text_red;
			}
		} else {
			firstk = "";
			secondk = "";
			if (ProfitApplication.conversStr(second) < 0) {
				color = R.color.text_red;
			}
		}
		if (!isDay) {
			SpannableString result = null;
			if (!TextUtils.isEmpty(second) && !"".equals(second)) {
				result = new SpannableString("");
				first = first + firstk;
				second = second + secondk;
				result = new SpannableString(first + "\n" + second);
				result.setSpan(new ForegroundColorSpan(context.getResources().getColor(color)),
						first.length() + "\n".length(), first.length() + "\n".length() + second.length(),
						Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
				tv.setText(result);
			} else {
				tv.setText(first);
			}
		} else {
			tv.setText(first + firstk);
		}
	}
}
