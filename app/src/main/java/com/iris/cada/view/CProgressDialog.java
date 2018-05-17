package com.iris.cada.view;

import com.iris.cada.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CProgressDialog {
	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	@SuppressLint("InflateParams")
	public static Dialog createLoadingDialog(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		// 得到加载view
		View v = inflater.inflate(R.layout.loading_dialog, null);
		// 加载布局
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		// 提示文字
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		// 设置加载信息
		tipTextView.setVisibility(View.GONE);
		// 创建自定义样式dialog
		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
		// loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		loadingDialog.setCancelable(false);// 不可以用“返回键”取消
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
		return loadingDialog;
	}
}
