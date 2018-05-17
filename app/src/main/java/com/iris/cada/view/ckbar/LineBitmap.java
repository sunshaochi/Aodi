package com.iris.cada.view.ckbar;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

public class LineBitmap extends BitmapDrawParent {
	List<Path> paths;
	Paint paint, pointPaint;
	int rectWidth = 10;

	public LineBitmap(int width, int height, List<String> datas, int yStart, List<PointF> points, List<String> colors,
			int barWidth) {
		super(width, height, datas, yStart, points, colors);
		rectWidth = barWidth / 2;
		int drawStep = 1;
		if (points.size() > 12) {
			drawStep = 4;
		}
		paths = new ArrayList<Path>();
		for (int i = 0; i < drawStep; i++) {
			Path tempPath = new Path();
			int tempStart = i * (points.size() / drawStep);
			if (tempStart == 0) {
				tempPath.moveTo(points.get(tempStart).x, points.get(tempStart).y);
			} else {
				tempPath.moveTo(points.get(tempStart - 1).x, points.get(tempStart - 1).y);
			}
			int tempSize = tempStart + points.size() / drawStep;
			if (i == drawStep - 1 && tempSize < points.size()) {
				tempSize = points.size();
			}
			for (int j = tempStart; j < tempSize; j++) {
				tempPath.lineTo(points.get(j).x, points.get(j).y);
			}
			paths.add(tempPath);
		}

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStrokeWidth(4);
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.parseColor("#FFD671"));
		pointPaint = new Paint();
		pointPaint.setAntiAlias(true);
	}

	@Override
	protected Bitmap draw(float animationFactor) {
		canvas = new Canvas(bitmap);
		canvas.clipRect(0, 0, width * animationFactor, height);
		draw(canvas);

		return bitmap;
	}

	@Override
	public void draw(Canvas canvas) {
		for (Path path : paths) {
			canvas.drawPath(path, paint);
		}
		// 画点
		for (int i = 0; i < points.size(); i++) {
			pointPaint.setColor(Color.parseColor(colors.get(i)));
			float rectBtm = points.get(i).y + rectWidth / 2 > height ? points.get(i).y
					: points.get(i).y + rectWidth / 2;

			canvas.drawRect(points.get(i).x - rectWidth / 2, points.get(i).y - rectWidth / 2,
					points.get(i).x + rectWidth / 2, rectBtm, pointPaint);
		}
	}
}
