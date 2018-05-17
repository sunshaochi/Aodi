package com.iris.cada.view;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.iris.cada.view.EditDialog.OnSaveClick;
import com.iris.cada.view.SelectBrandDialog.OnListViewItemClick;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author ChuanJing
 * @date 2016年8月11日 下午3:33:16
 */
public class EditDialogUtils {

	/** 弹出输入框 */
	public static EditDialog showEditDialog(Context context, final View v, String textTitle) {
		EditDialog editDialog = new EditDialog(context, textTitle);
		editDialog.show();
		editDialog.setSaveClick(new OnSaveClick() {

			@Override
			public void onClick(View view) {
				String str = ((EditText) view).getText().toString();
				((TextView) v).setText(str);
			}
		});
		return editDialog;
	}

	/** 弹出输入 数字 框 */
	public static EditDialog showInputNumberEditDialog(Context context, final View v, String textTitle) {
		EditDialog editDialog = new EditDialog(context, textTitle, true);
		editDialog.show();
		editDialog.setSaveClick(new OnSaveClick() {
			@Override
			public void onClick(View view) {
				String str = ((EditText) view).getText().toString().trim();
				((TextView) v).setText(str);
			}
		});
		return editDialog;
	}

	/** 弹出输入 金融 框，内部已做千位分隔符操作 */
	public static EditDialog showInputMoneyEditDialog(Context context, final View v, String textTitle) {
		EditDialog editDialog = new EditDialog(context, textTitle, true);
		editDialog.show();
		editDialog.setSaveClick(new OnSaveClick() {
			@Override
			public void onClick(View view) {
				String str = ((EditText) view).getText().toString().trim();
				str = intDiv(str);
				((TextView) v).setText(str);
			}
		});
		return editDialog;
	}

	public static String intDiv(String str) {
		String moneyStr = "";
		// 不足三位不截取，不然会出现,100
		if (str.length() > 3) {
			// 倒置字符串从个位判断，从前往后会出现100,0
			str = new StringBuilder(str).reverse().toString();
			moneyStr = str.replaceAll("[\\w]{3}", "$0,");
			moneyStr = new StringBuilder(moneyStr).reverse().toString();
			// 如果第一个是 , 则去掉
			if (moneyStr.startsWith(",")) {
				moneyStr = moneyStr.substring(1);
			}
			return moneyStr;
		} else {
			return str;
		}
	}

	/**
	 * 弹出输入 电话号码 框，内部已做正则校验
	 */
	public static EditDialog showInputPhoneNumberEditDialog(final Context context, final View v, String textTitle) {
		EditDialog editDialog = new EditDialog(context, textTitle, true);
		editDialog.show();
		editDialog.setSaveClick(new OnSaveClick() {
			@Override
			public void onClick(View view) {
				String phoneNumber = ((EditText) view).getText().toString().trim();

				String regExp = "^(((13[0-9])|(15([0-3]|[5-9]))|(17[0-9])|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$";
				Pattern p = Pattern.compile(regExp);
				Matcher m = p.matcher(phoneNumber);
				if (m.find()) {
					// String phone =
					// LshItaskSEApplication.phoneNumDector(phoneNumber);
					// ((TextView) v).setText(phone);
					((TextView) v).setText(phoneNumber);
				} else {
					Toast.makeText(context, "请输入正确格式的手机号码！", 0).show();
				}
			}
		});
		return editDialog;
	}

	/** 弹出选择框 */
	public static CustomSheetDialog showSelectDialog(Context context, final View v, String title, List<String> datas) {
		CustomSheetDialog customSheetDialog = new CustomSheetDialog(context, datas);
		customSheetDialog.builder(title).show();
		customSheetDialog.setItemClick(new CustomSheetDialog.OnListViewItemClick() {

			@Override
			public void onClick(String str, int position) {
				((TextView) v).setText(str);
			}
		});
		return customSheetDialog;
	}

	/** 弹出选择车型的dialog */
	public static SelectBrandDialog showIntentionCarModelDialog(Context context, final View v) {
		SelectBrandDialog dialog4 = new SelectBrandDialog(context);
		dialog4.show();
		dialog4.setItemClick(new OnListViewItemClick() {
			@Override
			public void onClick(String str) {
				((TextView) v).setText(str);
			}
		});
		return dialog4;
	}

}