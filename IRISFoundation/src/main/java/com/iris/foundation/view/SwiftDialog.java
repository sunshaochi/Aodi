package com.iris.foundation.view;

import com.iris.foundation.utils.DensityUtil;
import com.iris.irisfoundation.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class SwiftDialog {
	private Dialog dialog;
	private BtnOnClick btnOnClick;
	private String strStext;
	private TextView tv_title,tv_left, tv;
	private View view;
	public interface BtnOnClick {
		void openClick();

		void cancelClick();
	}

	public void setBtnOnClick(BtnOnClick btnOnClick) {
		this.btnOnClick = btnOnClick;
	}
	public SwiftDialog(Activity context, String strText) {
		this(context, strText, null);
	}
	@SuppressLint("NewApi")
	public SwiftDialog(Activity context, String strText, Drawable icon) {
		this(context, strText, icon, 0);
	}
	
	@SuppressLint("NewApi")
	public SwiftDialog(Activity context, String strText, Drawable icon, int themeResId ) {
		this.strStext = strText;
		view = LayoutInflater.from(context).inflate(R.layout.swift_dialog, null);
		
		dialog = new Dialog(context, themeResId);
		SwiftDialog.dialogDownDectorCenter(dialog, context);
		
		dialog.setContentView(view);
		
		Button open = (Button) view.findViewById(R.id.open);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		tv = (TextView) view.findViewById(R.id.tv_content);
		tv.setText(strStext);
		tv_left = (TextView) view.findViewById(R.id.tv_left);
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		if (icon != null) {
			tv_left.setBackground(icon);
		} else {
			tv_left.setVisibility(View.GONE);
		}
		
		// 打开
		open.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btnOnClick.openClick();
			}
		});
		// 取消
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btnOnClick.cancelClick();
			}
		});
	}

	public TextView getTextViewLeft() {
		return tv_left;
	}

	public TextView getTextViewRight() {
		return tv;
	}
	
	/**
	 * 设置标题
	 * @param title
	 */
	public void setDialogTitle(String title) {
		if (title != null && title.length() > 0) {
			tv_title.setText(title);
			tv_title.setVisibility(View.VISIBLE);
		} else {
			tv_title.setVisibility(View.GONE);
		}
	}

	public void show() {
		dialog.show();
	}

	public void dismiss() {
		dialog.dismiss();
	}

	public Dialog getDialog() {
		return dialog;
	}
	public View getContentView(){
		return view;
	}

	/**
	 * 让dialog显示的位置在某个view的下部
	 * 
	 * @param d
	 *            屏幕的分辨率
	 * @param dg
	 *            dialog
	 * @param anchorView
	 *            锚view
	 * @param context
	 * @param layout
	 *            dialog的内容布局
	 * @param backgroundLight
	 *            true则dialog背景高亮,否则false
	 * @param scaleHeight
	 *            dialog的高度
	 */
	@SuppressWarnings("deprecation")
	public static void dialogDownDector(final Dialog dg, View anchorView, Activity context, View layout,
			Boolean backgroundLight, float scaleHeight) {
		Display d = context.getWindowManager().getDefaultDisplay();
		dg.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dg.setContentView(layout);
		Window dialogWindow = dg.getWindow();
		dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		if (backgroundLight) {
			dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		}
		dialogWindow.setGravity(Gravity.CENTER | Gravity.TOP);
		// 显示的坐标
		lp.y = anchorView.getTop() + anchorView.getMeasuredHeight() + DensityUtil.dip2px(context, 5)
				+ getStatusBarHeight(context);// dialog的offset
		int width = (int) (d.getWidth() * 1);
		int height = (int) ((d.getHeight() - lp.y) * scaleHeight);
		// dialog的大小
		lp.width = width;
		lp.height = height;
		dialogWindow.setAttributes(lp);
	}

	/**
	 * 让dialog显示的位置在某个view的下部,有阴影的情况下使用
	 * 
	 * @param d
	 *            屏幕的分辨率
	 * @param dg
	 *            dialog
	 * @param anchorView
	 *            锚view
	 * @param context
	 * @param layout
	 *            dialog的内容布局
	 * @param scaleHeight
	 *            dialog的高度
	 * @param id
	 *            点击消失对话框的区域(即阴影部分的ID)
	 */
	@SuppressWarnings("deprecation")
	public static void dialogDownDector(final Dialog dg, View anchorView, Activity context, View layout,
			float scaleHeight, int id) {
		Display d = context.getWindowManager().getDefaultDisplay();
		dg.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dg.setContentView(layout);
		Window dialogWindow = dg.getWindow();
		dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		dialogWindow.setGravity(Gravity.CENTER | Gravity.TOP);
		// 显示的坐标
		lp.y = anchorView.getTop() + anchorView.getMeasuredHeight() + DensityUtil.dip2px(context, 5)
				+ getStatusBarHeight(context);// dialog的offset
		int width = (int) (d.getWidth() * 1);
		int height = (int) ((d.getHeight() - lp.y) * scaleHeight);
		if (id > 0) {
			dg.findViewById(id).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dg.dismiss();
				}
			});
		}
		// dialog的大小
		lp.width = width;
		lp.height = height;
		dialogWindow.setAttributes(lp);
	}

	/**
	 * 让dialog显示在屏幕的中间
	 * 
	 * @param dg
	 * @param context
	 */
	public static void dialogDownDectorCenter(Dialog dg, Activity context) {
		dg.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window dialogWindow = dg.getWindow();
		dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		// dialogWindow.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}

	public static void dialogBtmDector(Dialog dialog, Activity activity, View layout) {
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(layout);
		Window window = dialog.getWindow();
		window.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		WindowManager windowManager = activity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = (int) (display.getWidth()); // 设置宽度
		window.setAttributes(lp);
	}

	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
}
