package com.iris.cada.view.ckbar;

import java.util.List;

import com.iris.cada.utils.CKNumFormat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class YAxis extends View {

	int yStart = 0, yEnd = 0;
	Paint paint, paintLine;
	Boolean IS_LEFT;
	float xStart = 0;
	List<String> datas;

	public YAxis(Context context, List<String> datas, Paint paint, int yStart, int yEnd, Boolean IS_LEFT) {
		super(context);
		this.yStart = yStart;
		this.yEnd = yEnd;
		this.paint = paint;
		paint.setStrokeWidth(1);
		// 设置字体颜色
		String axisColor = "#444444";
		if (CKChartViewStyle.HAD_RIGHT_AXIS) {// 如果有两个坐标轴,那么左轴颜色为CC0033,右轴为FBB300
			if (IS_LEFT) {
				axisColor = "#CC0033";
			} else {
				axisColor = "#FBB300";
			}
		}
		paint.setColor(Color.parseColor(axisColor));
		paintLine = new Paint(paint);
		paintLine.setColor(Color.parseColor("#444444"));
		this.IS_LEFT = IS_LEFT;
		this.datas = datas;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		xStart = IS_LEFT ? getMeasuredWidth() - paint.getStrokeWidth() : paint.getStrokeWidth();
		canvas.drawLine(xStart, yStart, xStart, yEnd, paintLine);
		float unit = 0;
		if (datas.size() - 1 != 0) {
			unit = (yEnd - yStart) / (datas.size() - 1);
		}

		if (IS_LEFT) {
			for (int i = 0; i < datas.size(); i++) {
				String temp = datas.get(i);
				if (CKNumFormat.stringNotNull2Float(temp) >= 1000 || CKNumFormat.stringNotNull2Float(temp) <= -1000) {
					temp = CKNumFormat.decimalRound(CKNumFormat.stringNotNull2Float(temp) / 1000) + "K";
				}else{
					temp = (int)CKNumFormat.decimalRound(datas.get(i))+"";
				}
				canvas.drawText(temp, xStart - paint.measureText(temp) - 3, yStart + paint.getTextSize() / 2 + i * unit,
						paint);
			}
		} else {
			for (int i = 0; i < datas.size(); i++) {
				String temp = datas.get(i);
				canvas.drawText(temp, xStart + 3, yStart + paint.getTextSize() / 2 + i * unit, paint);
			}
		}
	}
}
