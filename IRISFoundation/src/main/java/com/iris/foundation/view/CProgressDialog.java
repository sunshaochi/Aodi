package com.iris.foundation.view;

import com.iris.foundation.utils.DensityUtil;
import com.iris.irisfoundation.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CProgressDialog {
	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog createLoadingDialog(Context context) {
		// ImageView
		ImageView spaceshipImage = new ImageView(context);
		spaceshipImage.setBackgroundResource(R.drawable.progress);
		// 加载动画
		RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setRepeatCount(RotateAnimation.INFINITE);
		rotateAnimation.setDuration(1500);
		// 使用ImageView显示动画loading_dialog.xml
		spaceshipImage.startAnimation(rotateAnimation);

		Dialog loadingDialog = new Dialog(context);// 创建自定义样式dialog
		loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window dialogWindow = loadingDialog.getWindow();
		dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		loadingDialog.setCancelable(false);// 不可以用“返回键”取消

		loadingDialog.setContentView(spaceshipImage,
				new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 60), DensityUtil.dip2px(context, 60)));// 设置布局

		return loadingDialog;

	}
}
