package com.iris.cada.view.ckbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class BarTitleView extends View {
	Paint paint;
	String title;

	public BarTitleView(Context context, String title, Paint paint) {
		super(context);
		this.paint = paint;
		this.title = title;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		float textHeight = paint.getTextSize();
		float textWidth = paint.measureText(title);
		canvas.drawText(title, (getMeasuredWidth() - textWidth) / 2, getMeasuredHeight() / 2 + textHeight/2, paint);
	}
}
