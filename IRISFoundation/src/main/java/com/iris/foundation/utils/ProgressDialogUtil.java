package com.iris.foundation.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 进度对话框工具，显示和关闭对话框
 * 
 * @author yang_qingan
 * 
 */
public class ProgressDialogUtil {
	private ProgressDialogUtil() {
	}

	private static ProgressDialog progress;

	/**
	 * 显示进度对话框
	 * 
	 * @param ctx
	 *            上下文
	 * @param strResId
	 *            提示内容
	 */
	public static void showProgress(Context ctx, int strResId) {
		showProgress(ctx, strResId, true, false);
	}

	/**
	 * 显示进度对话框
	 * 
	 * @param ctx
	 *            上下文
	 * @param strResId
	 *            提示内容
	 * @param cancelable
	 *            用户是否可手动取消
	 * @param hasProgress
	 *            是否显示进度条
	 */
	public static void showProgress(Context ctx, int strResId,
			boolean cancelable, boolean hasProgress) {
		dismissProgress();
		progress = new ProgressDialog(ctx);
		progress.setCancelable(cancelable); // 控制返回按钮无效
//		String msg = ctx.getResources().getString(strResId);
//		progress.setMessage(msg);
		if (hasProgress) {
			progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progress.setProgress(0);
		}
		progress.show();
//		ImageView image = new ImageView(ctx);
//		image.setImageResource(R.drawable.main_home_logo);
//		progress.getWindow().setContentView(image);
	}

	/**
	 * 设置进度
	 * 
	 * @param progress
	 */
	public static void setProgress(int progress) {
		if (ProgressDialogUtil.progress != null
				&& ProgressDialogUtil.progress.isShowing()) {
			ProgressDialogUtil.progress.setProgress(progress);
		}
	}

	/**
	 * 关闭对话框
	 */
	public static void dismissProgress() {
		if (progress != null) {
			progress.dismiss();
			progress = null;
		}
	}
}
