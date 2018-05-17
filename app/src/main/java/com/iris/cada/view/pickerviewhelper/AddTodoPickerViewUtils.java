package com.iris.cada.view.pickerviewhelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.TimePickerViewDialog;
import com.iris.foundation.utils.CKNumFormat;
import com.iris.cada.R;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

public class AddTodoPickerViewUtils {

	private ArrayList<String> options1Items = new ArrayList<String>();

	private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();

	private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();

	private TimePickerViewDialog pvTimeDialog;

	@SuppressWarnings("rawtypes")
	private OptionsPickerView pvOptions;

	private Activity context;

	public AddTodoPickerViewUtils(Context context) {
		super();
		this.context = (Activity) context;
	}

	/**
	 * 日期选择器 年月日时分
	 * 
	 * @param dialogTitle
	 * @return
	 */
	public AddTodoPickerViewUtils initAllPickerView(String dialogTitle) {
		pvTimeDialog = new TimePickerViewDialog(context, TimePickerView.Type.ALL);
		pvTimeDialog.setCyclic(false);
		// pvTimeDialog.setButtonLeftText("取消");
		// pvTimeDialog.setButtonRightText("确定");
		pvTimeDialog.setButtonLeftBackgroundRes(R.drawable.dialog_cancel);
		pvTimeDialog.setButtonRightBackgroundRes(R.drawable.confirm);
		pvTimeDialog.setTitle(dialogTitle);
		pvTimeDialog.setTitleColor("#ffffff");
		pvTimeDialog.setTitleBackgroundColor("#BB0A30");
		return this;
	}

	/**
	 * 日期选择器 年月日时分 可以传入开始和结束时间
	 * 
	 * @param dialogTitle
	 * @return
	 */
	public AddTodoPickerViewUtils initAllPickerView(Calendar calendarStart, Calendar calendarEnd, String dialogTitle) {
		pvTimeDialog = new TimePickerViewDialog(context, TimePickerView.Type.ALL);
		pvTimeDialog.setCyclic(false);
		// pvTimeDialog.setButtonLeftText("取消");
		// pvTimeDialog.setButtonRightText("确定");
		pvTimeDialog.setRange(calendarStart, calendarEnd);// 开始时间和结束时间
		pvTimeDialog.setTime(calendarStart);// 默认值
		pvTimeDialog.setButtonLeftBackgroundRes(R.drawable.dialog_cancel);
		pvTimeDialog.setButtonRightBackgroundRes(R.drawable.confirm);
		pvTimeDialog.setTitle(dialogTitle);
		pvTimeDialog.setTitleColor("#ffffff");
		pvTimeDialog.setTitleBackgroundColor("#BB0A30");
		return this;
	}

	/**
	 * 日期选择器 年月日
	 * 
	 * @param dialogTitle
	 * @return
	 */
	public AddTodoPickerViewUtils initDatePickerView(String dialogTitle) {
		pvTimeDialog = new TimePickerViewDialog(context, TimePickerView.Type.YEAR_MONTH_DAY);
		pvTimeDialog.setCyclic(false);
		// pvTimeDialog.setButtonLeftText("取消");
		// pvTimeDialog.setButtonRightText("确定");
		pvTimeDialog.setButtonLeftBackgroundRes(R.drawable.dialog_cancel);
		pvTimeDialog.setButtonRightBackgroundRes(R.drawable.confirm);
		pvTimeDialog.setTitle(dialogTitle);
		pvTimeDialog.setTitleColor("#ffffff");
		pvTimeDialog.setTitleBackgroundColor("#BB0A30");
		return this;
	}

	/**
	 * 日期选择器 年月日
	 * 
	 * @param dialogTitle
	 * @return
	 */
	public AddTodoPickerViewUtils initDatePickerView(Calendar calendarStart, Calendar calendarEnd, String dialogTitle) {
		pvTimeDialog = new TimePickerViewDialog(context, TimePickerView.Type.YEAR_MONTH_DAY);
		pvTimeDialog.setCyclic(false);
		// pvTimeDialog.setButtonLeftText("取消");
		// pvTimeDialog.setButtonRightText("确定");
		pvTimeDialog.setRange(calendarStart, calendarEnd);
		pvTimeDialog.setTime(calendarStart);// 默认值
		pvTimeDialog.setButtonLeftBackgroundRes(R.drawable.dialog_cancel);
		pvTimeDialog.setButtonRightBackgroundRes(R.drawable.confirm);
		pvTimeDialog.setTitle(dialogTitle);
		pvTimeDialog.setTitleColor("#ffffff");
		pvTimeDialog.setTitleBackgroundColor("#BB0A30");
		return this;
	}

	/**
	 * 日期选择器 年月日 选择后直接把日期填在 TextView上
	 * 
	 * @param tvTime
	 * @param dialogTitle
	 * @return
	 */
	public AddTodoPickerViewUtils initDatePickerView(String dialogTitle, final TextView tvTime) {
		pvTimeDialog = new TimePickerViewDialog(context, TimePickerView.Type.YEAR_MONTH_DAY);
		pvTimeDialog.setCyclic(false);
		pvTimeDialog.setButtonLeftBackgroundRes(R.drawable.dialog_cancel);
		pvTimeDialog.setButtonRightBackgroundRes(R.drawable.confirm);
		pvTimeDialog.setTitleColor("#ffffff");
		pvTimeDialog.setTitleBackgroundColor("#BB0A30");

		// 时间选择后回调
		pvTimeDialog.setOnTimeSelectListener(new TimePickerViewDialog.OnTimeSelectListener() {

			@Override
			public void onTimeSelect(Date date) {
				tvTime.setText(getDate(date));
			}

			@Override
			public void onCancel() {

			}
		});
		return this;
	}

	/**
	 * 日期选择器，年 — 月
	 * 
	 * @param dialogTitle
	 * @return
	 */
	public AddTodoPickerViewUtils initDateNoDayPickerView(String dialogTitle) {
		pvTimeDialog = new TimePickerViewDialog(context, TimePickerView.Type.YEAR_MONTH);
		pvTimeDialog.setCyclic(false);
		pvTimeDialog.setButtonLeftBackgroundRes(R.drawable.dialog_cancel);
		pvTimeDialog.setButtonRightBackgroundRes(R.drawable.confirm);
		pvTimeDialog.setTitle(dialogTitle);
		pvTimeDialog.setTitleColor("#ffffff");
		pvTimeDialog.setTitleBackgroundColor("#BB0A30");
		return this;
	}

	/**
	 * 时间选择器 时分
	 * 
	 * @param tvTime
	 * @param dialogTitle
	 * @return
	 */
	public AddTodoPickerViewUtils initTimePickerView(String dialogTitle) {
		pvTimeDialog = new TimePickerViewDialog(context, TimePickerView.Type.HOURS_MINS);
		pvTimeDialog.setCyclic(false);
		pvTimeDialog.setButtonLeftBackgroundRes(R.drawable.dialog_cancel);
		pvTimeDialog.setButtonRightBackgroundRes(R.drawable.confirm);
		// pvTimeDialog.setButtonLeftText("取消");
		// pvTimeDialog.setButtonRightText("确定");
		pvTimeDialog.setTitle(dialogTitle);
		pvTimeDialog.setTitleColor("#ffffff");
		pvTimeDialog.setTitleBackgroundColor("#BB0A30");
		return this;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AddTodoPickerViewUtils initCusPickerView(final TextView tvOptions) {
		// 选项选择器
		pvOptions = new OptionsPickerView(context);
		// 选项1
		options1Items.add("上午");
		options1Items.add("下午");

		// 选项2
		ArrayList<String> options2Items_01 = new ArrayList<String>();
		for (int i = 1; i < 13; i++) {
			options2Items_01.add("" + i);
		}
		options2Items.add(options2Items_01);

		// 选项3
		ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
		ArrayList<String> options3Items_01_01 = new ArrayList<String>();
		for (int i = 1; i < 61; i++) {
			if (i <= 9) {
				options3Items_01_01.add("0" + i);
			} else {
				options3Items_01_01.add(i + "");
			}
		}
		options3Items_01.add(options3Items_01_01);
		options3Items.add(options3Items_01);

		// 三级联动效果
		pvOptions.setPicker(options1Items, options2Items, options3Items, false);
		// 设置默认选中的三级项目
		// pvOptions.setSelectOptions(1, 1, 1);
		// 设置选择的三级单位
		// pwOptions.setLabels("省", "市", "区");
		setSytle(pvOptions, "选择时间");
		// 监听确定选择按钮
		pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

			@Override
			public void onOptionsSelect(int options1, int option2, int options3) {
				// 返回的分别是三个级别的选中位置
				int tempAm = 0;
				if ("下午".equals(options1Items.get(options1))) {
					tempAm = 12;
				}
				String tx = (tempAm + CKNumFormat.stringNotNull2Int(options2Items.get(0).get(option2))) + ":"
						+ options3Items.get(0).get(0).get(options3);
				tvOptions.setText(tx);
			}
		});
		return this;
	}

	public String getTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(date);
	}

	public String getDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	public String getDateNoDay(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
		return format.format(date);
	}

	public String getDateAll(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(date);
	}

	public String getDateAwlays(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return format.format(date);
	}

	@SuppressWarnings("rawtypes")
	public OptionsPickerView getPvOptions() {
		return pvOptions;
	}

	public TimePickerViewDialog getPvTime() {
		return pvTimeDialog;
	}

	@SuppressWarnings("rawtypes")
	public void setSytle(OptionsPickerView pvOptions, String titleType) {
		// 设置样式
		pvOptions.setTitle(titleType);
		pvOptions.setCyclic(false, true, true);
		pvOptions.setButtonLeftBackgroundRes(R.drawable.dialog_cancel);
		pvOptions.setButtonRightBackgroundRes(R.drawable.confirm);
		pvOptions.setTitleColor("#ffffff");
		pvOptions.setTitleBackgroundColor("#BB0A30");
	}
}
