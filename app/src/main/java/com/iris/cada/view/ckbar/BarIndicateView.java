package com.iris.cada.view.ckbar;

import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class BarIndicateView extends View {
	Paint paint;
	List<String> titles;
	final int offset = 5;
	float textWidth, textHeight;
	List<String> colors;
	float tempStart = 0;

	public BarIndicateView(Context context, List<String> titles, Paint paint, List<String> colors) {
		super(context);
		this.paint = paint;
		paint.setColor(Color.parseColor("#FF6633"));
		this.titles = titles;
		this.colors = colors;
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		// TODO Auto-generated method stub
		super.onLayout(changed, left, top, right, bottom);
		if (titles.size() > 0)
			tempStart = (getMeasuredWidth() - paint.measureText(titles.get(0)) * titles.size()) / 2;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		textHeight = paint.getTextSize();
		for (int i = 0; i < titles.size(); i++) {
			textWidth = paint.measureText(titles.get(i));
			paint.setColor(Color.parseColor(colors.get(i)));
			String temp = titles.get(i).substring(0, 1);
			canvas.drawText(temp, tempStart, getMeasuredHeight() / 2 + textHeight / 2, paint);
			paint.setColor(Color.BLACK);
			canvas.drawText(titles.get(i).substring(1), tempStart + paint.measureText(temp),
					getMeasuredHeight() / 2 + textHeight / 2, paint);
			tempStart += textWidth + offset;
		}

	}
}
