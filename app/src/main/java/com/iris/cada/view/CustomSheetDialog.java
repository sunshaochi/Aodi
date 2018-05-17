package com.iris.cada.view;

import java.util.List;

import com.iris.cada.adapter.ListViewAdapter;
import com.iris.cada.view.swipeXListView.SwipeMenuListView;
import com.iris.foundation.utils.DensityUtil;
import com.iris.foundation.view.CKTitleView;
import com.iris.foundation.view.CKTitleView.TitleClick;
import com.iris.cada.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

/**
 * CustomInfo页用到
 */
public class CustomSheetDialog {
	private Context context;
	private Dialog dialog;

	private CKTitleView titleView;
	private ListViewAdapter adapter;
	private SwipeMenuListView listView;
	private List<String> datas;
	private OnListViewItemClick itemClick;
	private OnDialogDismissListener dialogDismissListener;

	public interface OnDialogDismissListener {
		void OnDismiss();
	}

	public void setDialogDismissListener(OnDialogDismissListener dialogDismissListener) {
		this.dialogDismissListener = dialogDismissListener;
	}

	public void setItemClick(OnListViewItemClick itemClick) {
		this.itemClick = itemClick;
	}

	public CustomSheetDialog(Context context) {
		this.context = context;
	}

	public CustomSheetDialog(Context context, List<String> datas) {
		this.context = context;
		this.datas = datas;
	}

	public void setDatas(List<String> datas) {
		this.datas = datas;
	}

	public void initView() {

	}

	@SuppressWarnings("deprecation")
	@SuppressLint({ "InflateParams", "RtlHardcoded" })
	public CustomSheetDialog builder(String strTitle) {
		View view = LayoutInflater.from(context).inflate(R.layout.toast_view_actionsheet2, null);
		view.setMinimumWidth(DensityUtil.getScreenSize(context).x);
		listView = (SwipeMenuListView) view.findViewById(R.id.listview);
		titleView = (CKTitleView) view.findViewById(R.id.title);
		titleView.getLeftButoon().setBackgroundResource(R.drawable.dialog_cancel);
		titleView.getRightButoon().setBackgroundResource(R.drawable.confirm);
		titleView.setBackgroundColor(Color.parseColor("#BB0A30"));
		TextView title = new TextView(context);
		title.setText(strTitle);
		title.setTextColor(context.getResources().getColor(R.color.white));
		title.setTextSize(18);
		titleView.getLinearLayout().addView(title);

		titleView.setTitleClick(new TitleClick() {

			@Override
			public void btRightClick(View v) {

			}

			@Override
			public void btLeftClick(View v) {
				dialog.dismiss();
			}
		});

		adapter = new ListViewAdapter(context, datas, R.layout.item_customlevel);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (itemClick != null) {
					itemClick.onClick(datas.get(position - 1), position);
					dialog.dismiss();
				}
			}
		});
		listView.setPullLoadEnable(false);
		listView.setPullRefreshEnable(false);
		dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog.setContentView(view);
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				if (dialogDismissListener != null) {
					dialogDismissListener.OnDismiss();
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

	public CustomSheetDialog setCancelable(boolean cancel) {
		dialog.setCancelable(cancel);
		return this;
	}

	public CustomSheetDialog setCanceledOnTouchOutside(boolean cancel) {
		dialog.setCanceledOnTouchOutside(cancel);
		return this;
	}

	public void show() {
		dialog.show();
	}

	public interface DialogDismissListener {
		void onDismiss(DialogInterface dialog);
	}

	public interface OnListViewItemClick {
		void onClick(String str, int position);
	}
}
