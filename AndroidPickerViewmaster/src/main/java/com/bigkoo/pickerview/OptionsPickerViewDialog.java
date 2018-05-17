package com.bigkoo.pickerview;

import java.util.ArrayList;

import com.bigkoo.pickerview.view.BasePickerView;
import com.bigkoo.pickerview.view.WheelOptions;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Sai on 15/11/22.
 */
public class OptionsPickerViewDialog<T> implements View.OnClickListener {
	WheelOptions wheelOptions;
	private Button btnSubmit, btnCancel;
	private TextView tvTitle;
	private OnOptionsSelectListener optionsSelectListener;
	private static final String TAG_SUBMIT = "submit";
	private static final String TAG_CANCEL = "cancel";
	private RelativeLayout customTitle;
	Dialog dialog;
	public OptionsPickerViewDialog(Activity context) {
		dialog = new Dialog(context);
		View contentView = LayoutInflater.from(context).inflate(R.layout.pickerview_options, null);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(contentView);
		Window window = dialog.getWindow();
		window.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		WindowManager windowManager = context.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = (int) (display.getWidth()); // 设置宽度
		window.setAttributes(lp);
		
		customTitle = (RelativeLayout) contentView.findViewById(R.id.custom_title);
		// -----确定和取消按钮
		btnSubmit = (Button) contentView.findViewById(R.id.btnSubmit);
		btnSubmit.setTag(TAG_SUBMIT);
		btnCancel = (Button) contentView.findViewById(R.id.btnCancel);
		btnCancel.setTag(TAG_CANCEL);
		btnSubmit.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		// 顶部标题
		tvTitle = (TextView) contentView.findViewById(R.id.tvTitle);
		// ----转轮
		final View optionspicker = contentView.findViewById(R.id.optionspicker);
		wheelOptions = new WheelOptions(optionspicker);
	}

	public void setPicker(ArrayList<T> optionsItems) {
		wheelOptions.setPicker(optionsItems, null, null, false);
	}

	public void setPicker(ArrayList<T> options1Items, ArrayList<ArrayList<T>> options2Items, boolean linkage) {
		wheelOptions.setPicker(options1Items, options2Items, null, linkage);
	}

	public void setPicker(ArrayList<T> options1Items, ArrayList<ArrayList<T>> options2Items,
			ArrayList<ArrayList<ArrayList<T>>> options3Items, boolean linkage) {
		wheelOptions.setPicker(options1Items, options2Items, options3Items, linkage);
	}

	/**
	 * 设置选中的item位置
	 * 
	 * @param option1
	 */
	public void setSelectOptions(int option1) {
		wheelOptions.setCurrentItems(option1, 0, 0);
	}

	/**
	 * 设置选中的item位置
	 * 
	 * @param option1
	 * @param option2
	 */
	public void setSelectOptions(int option1, int option2) {
		wheelOptions.setCurrentItems(option1, option2, 0);
	}

	/**
	 * 设置选中的item位置
	 * 
	 * @param option1
	 * @param option2
	 * @param option3
	 */
	public void setSelectOptions(int option1, int option2, int option3) {
		wheelOptions.setCurrentItems(option1, option2, option3);
	}

	/**
	 * 设置选项的单位
	 * 
	 * @param label1
	 */
	public void setLabels(String label1) {
		wheelOptions.setLabels(label1, null, null);
	}

	/**
	 * 设置选项的单位
	 * 
	 * @param label1
	 * @param label2
	 */
	public void setLabels(String label1, String label2) {
		wheelOptions.setLabels(label1, label2, null);
	}

	/**
	 * 设置选项的单位
	 * 
	 * @param label1
	 * @param label2
	 * @param label3
	 */
	public void setLabels(String label1, String label2, String label3) {
		wheelOptions.setLabels(label1, label2, label3);
	}

	/**
	 * 设置是否循环滚动
	 * 
	 * @param cyclic
	 */
	public void setCyclic(boolean cyclic) {
		wheelOptions.setCyclic(cyclic);
	}

	public void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
		wheelOptions.setCyclic(cyclic1, cyclic2, cyclic3);
	}

	@Override
	public void onClick(View v) {
		String tag = (String) v.getTag();
		if (tag.equals(TAG_CANCEL)) {
			dialog.dismiss();
			optionsSelectListener.onCancel();
			return;
		} else {
			if (optionsSelectListener != null) {
				int[] optionsCurrentItems = wheelOptions.getCurrentItems();
				optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1],
						optionsCurrentItems[2]);
			}
			dialog.dismiss();
			return;
		}
	}

	public interface OnOptionsSelectListener {
		public void onOptionsSelect(int options1, int option2, int options3);
		public void onCancel();
	}

	public void setOnoptionsSelectListener(OnOptionsSelectListener optionsSelectListener) {
		this.optionsSelectListener = optionsSelectListener;
	}

	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	public void setTitleColor(String colorStr) {
		tvTitle.setTextColor(Color.parseColor(colorStr));
	}

	public void setButtonLeftText(String str) {
		btnSubmit.setText(str);
	}

	public void setButtonRightText(String str) {
		btnCancel.setText(str);
	}

	public void setButtonLeftBackgroundRes(int res) {
		btnCancel.setBackgroundResource(res);
	}

	public void setButtonRightBackgroundRes(int res) {
		btnSubmit.setBackgroundResource(res);
	}

	public void setTitleBackgroundColor(String colorStr) {
		customTitle.setBackgroundColor(Color.parseColor(colorStr));
	}
}
