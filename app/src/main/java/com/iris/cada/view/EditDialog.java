package com.iris.cada.view;

import com.iris.foundation.utils.DensityUtil;
import com.iris.foundation.view.CKTitleView;
import com.iris.foundation.view.CKTitleView.TitleClick;
import com.iris.cada.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class EditDialog {
	private Context context;
	private Dialog dialog;

	private CKTitleView titleView;
	private EditText carModelColor;
	private DialogDismissListener dismissListenrt;
	private String titleText;
	private OnSaveClick saveClick;

	public void setSaveClick(OnSaveClick saveClick) {
		this.saveClick = saveClick;
	}

	public void setDismissListenrt(DialogDismissListener dismissListenrt) {
		this.dismissListenrt = dismissListenrt;
	}

	public EditDialog(Context context) {
		this.context = context;
		builder();
	}

	public EditDialog(Context context, String title) {
		this.context = context;
		this.titleText = title;
		builder();
	}

	/**
	 * @param context
	 * @param title
	 * @param inputIsNumber
	 *            true表示 只能输入数字
	 */
	public EditDialog(Context context, String title, boolean inputIsNumber) {
		this.context = context;
		this.titleText = title;
		builderInputNumber(inputIsNumber);
	}

	public EditDialog(String title, Context context, boolean inputIsNumber) {
		this.context = context;
		this.titleText = title;
		builderInputNumber1(inputIsNumber);
	}

	public void initView() {

	}

	@SuppressLint({ "InflateParams", "RtlHardcoded" })
	public EditDialog builder() {
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null);
		view.setMinimumWidth(DensityUtil.getScreenSize(context).x);
		carModelColor = (EditText) view.findViewById(R.id.et_mail_content);
		titleView = (CKTitleView) view.findViewById(R.id.title);
		titleView.getLeftButoon().setBackgroundResource(R.drawable.dialog_cancel);
		titleView.getRightButoon().setBackgroundResource(R.drawable.confirm);
		TextView title = new TextView(context);
		title.setText(titleText);
		title.setTextColor(context.getResources().getColor(R.color.white));
		title.setTextSize(18);
		titleView.getLinearLayout().addView(title);

		titleView.setTitleClick(new TitleClick() {

			@Override
			public void btRightClick(View v) {
				saveClick.onClick(carModelColor);
				dialog.dismiss();
			}

			@Override
			public void btLeftClick(View v) {
				dialog.dismiss();
			}
		});

		dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog.setContentView(view);

		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.x = 0;
		lp.y = 0;
		dialogWindow.setAttributes(lp);
		return this;
	}

	/** 在builder()之前调用有效 */
	public EditDialog setTitle(String titleText) {
		this.titleText = titleText;
		return this;
	}

	public EditDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public EditDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	public void show() {
		dialog.show();
	}

	public void dismiss() {
		dialog.dismiss();
	}

	public interface DialogDismissListener {
		void onDismiss(View v);
	}

	public interface OnListViewItemClick {
		void onClick(String str);
	}

	public interface OnSaveClick {
		void onClick(View v);
	}

	@SuppressLint({ "InflateParams", "RtlHardcoded" })
	public EditDialog builderInputNumber(boolean inputIsNumber) {
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null);
		view.setMinimumWidth(DensityUtil.getScreenSize(context).x);
		carModelColor = (EditText) view.findViewById(R.id.et_mail_content);

		if (inputIsNumber) {
			carModelColor.setInputType(InputType.TYPE_CLASS_NUMBER);
		}

		titleView = (CKTitleView) view.findViewById(R.id.title);
		titleView.getLeftButoon().setBackgroundResource(R.drawable.dialog_cancel);
		titleView.getRightButoon().setBackgroundResource(R.drawable.confirm);
		TextView title = new TextView(context);
		title.setText(titleText);
		title.setTextColor(context.getResources().getColor(R.color.white));
		title.setTextSize(18);
		titleView.getLinearLayout().addView(title);

		titleView.setTitleClick(new TitleClick() {

			@Override
			public void btRightClick(View v) {
				if (saveClick != null) {
					saveClick.onClick(carModelColor);
					dialog.dismiss();
				}
			}

			@Override
			public void btLeftClick(View v) {
				dialog.dismiss();
			}
		});

		dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog.setContentView(view);
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				if (dismissListenrt != null) {
					dismissListenrt.onDismiss(carModelColor);
				}
			}
		});
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.x = 0;
		lp.y = 0;
		dialogWindow.setAttributes(lp);
		return this;
	}

	public EditDialog builderInputNumber1(boolean inputIsNumber) {
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_edit, null);
		view.setMinimumWidth(DensityUtil.getScreenSize(context).x);
		carModelColor = (EditText) view.findViewById(R.id.et_mail_content);

		if (inputIsNumber) {
			carModelColor.setInputType(InputType.TYPE_CLASS_NUMBER);
		}

		titleView = (CKTitleView) view.findViewById(R.id.title);
		titleView.getLeftButoon().setBackgroundResource(R.drawable.dialog_cancel);
		titleView.getRightButoon().setBackgroundResource(R.drawable.confirm);
		TextView title = new TextView(context);
		title.setText(titleText);
		title.setTextColor(context.getResources().getColor(R.color.white));
		title.setTextSize(18);
		titleView.getLinearLayout().addView(title);

		titleView.setTitleClick(new TitleClick() {

			@Override
			public void btRightClick(View v) {
				if (saveClick != null) {
					saveClick.onClick(carModelColor);
				}
			}

			@Override
			public void btLeftClick(View v) {
				dialog.dismiss();
			}
		});

		dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog.setContentView(view);
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				if (dismissListenrt != null) {
					dismissListenrt.onDismiss(carModelColor);
				}
			}
		});
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.x = 0;
		lp.y = 0;
		dialogWindow.setAttributes(lp);
		return this;
	}
}