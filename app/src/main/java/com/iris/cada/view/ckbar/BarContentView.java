package com.iris.cada.view.ckbar;

import java.util.ArrayList;
import java.util.List;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class BarContentView extends View {
	// animation
	private float animationFactor = 0f;
	private List<BitmapDrawParent> bitmaps;
	private int width, xAxisHeight, barStep;
	private GestureDetector gestureDetector;
	List<String> xAxisDatas;
	Paint xAxisPaint, xAxisTextPaint,indicateTextPaint;
	ChartClickListener chartClickListener;

	PointF touchPoint;
	int touchRegion = -1;
	ObjectAnimator value;
	float valueFactor;

	List<List<String>> datas;
	Bitmap tempBitmap1;

	// 回调接口
	public interface ChartClickListener {
		void onCharClick(int position);
	}

	/**
	 * @param context
	 * @param width
	 * @param height
	 * @param BAR_WIDTH
	 * @param datas
	 * @param points
	 * @param colors
	 * @param xAxisDatas
	 * @param typeface
	 * @param xAxisPaint
	 * @param xAxisTextPaint
	 * @param xAxisHeight
	 * @param rightAxisWidth
	 *            右边y轴的宽度,如果为0则不添加第二个view
	 */
	@SuppressLint("ClickableViewAccessibility")
	public BarContentView(Context context, int width, int height, int BAR_WIDTH, List<List<String>> datas,
			List<List<PointF>> points, List<List<String>> colors, List<String> xAxisDatas, Typeface typeface,
			Paint xAxisPaint, Paint xAxisTextPaint, int xAxisHeight, int rightAxisWidth,
			ChartClickListener chartClickListener) {
		super(context);
		gestureDetector = new GestureDetector(context, new gesture());
		this.datas = datas;
		this.xAxisDatas = xAxisDatas;
		this.setLongClickable(true);
		this.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		});
		bitmaps = new ArrayList<BitmapDrawParent>();
		bitmaps.add(new BarBitmap(width, height - xAxisHeight, datas.get(0), (int) CKChartData.YSTART, points.get(0),
				colors.get(0), BAR_WIDTH));
		if (rightAxisWidth != 0) {
			bitmaps.add(new LineBitmap(width, height - xAxisHeight, datas.get(1), (int) CKChartData.YSTART,
					points.get(1), colors.get(1), BAR_WIDTH));
		}
		this.width = width;
		this.xAxisHeight = xAxisHeight;
		barStep = width / xAxisDatas.size();
		this.xAxisPaint = xAxisPaint;
		this.xAxisTextPaint = xAxisTextPaint;
		this.indicateTextPaint = new Paint(xAxisTextPaint);
		indicateTextPaint.setTextSize(xAxisTextPaint.getTextSize()*5/4);
		this.chartClickListener = chartClickListener;
		// if (!(xAxisDatas.size() > 50)) {
		// ObjectAnimator animator = ObjectAnimator.ofFloat(this,
		// "animationFactor", 0f, 1f);
		// animator.setDuration(5 * 1000);
		// animator.start();
		// } else {
		setAnimationFactor(1);
		// }
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(width, heightMeasureSpec);
	}

	public float getAnimationFactor() {
		return animationFactor;
	}

	public void setAnimationFactor(float animationFactor) {
		this.animationFactor = animationFactor;
		this.invalidate();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setFilterBitmap(true);
		paint.setAntiAlias(true);
		paint.setColor(Color.parseColor("#DDDDDD"));
		super.onDraw(canvas);
		// if (animationFactor < 1) {
		// for (int i = 0; i < bitmaps.size(); i++) {
		// if (!(xAxisDatas.size() > 50)) {
		// drawBitmapByRect(canvas,
		// bitmaps.get(i).setAnimationFactor(animationFactor), paint);
		// }
		// }
		// }
		// if (animationFactor == 1) {
		for (int i = 0; i < bitmaps.size(); i++) {
			bitmaps.get(i).draw(canvas);
			bitmaps.get(i).recyle();
		}
		// }
		canvas.drawLine(0, CKChartData.YSTART, width, CKChartData.YSTART, xAxisPaint);
		for (int i = 0; i < xAxisDatas.size(); i++) {
			if (xAxisTextPaint.measureText(xAxisDatas.get(i)) > barStep) {
				xAxisDatas.set(i, xAxisDatas.get(i).substring(2));
			}
			float tempOffset = (barStep - xAxisTextPaint.measureText(xAxisDatas.get(i))) / 2;
			canvas.drawText(xAxisDatas.get(i), 0 + barStep * i + tempOffset,
					getMeasuredHeight() - xAxisHeight + xAxisTextPaint.getTextSize(), xAxisTextPaint);
			canvas.drawLine(0 + barStep * i, getMeasuredHeight() - xAxisHeight, 0 + barStep * i,
					getMeasuredHeight() - xAxisHeight - 5, xAxisPaint);
		}
		if (touchRegion >= 0) {
			int lineNum = CKChartData.getTouchText().size() + 1;
			String drawText1 = "", drawText2 = "", drawText3 = "";
			float singleLineHeight = indicateTextPaint.getTextSize();
			float touchLineTextWidth = indicateTextPaint.measureText("哈哈哈哈哈哈哈") + 10;
			float lineHeight = lineNum * singleLineHeight;
			float margin = singleLineHeight / 2;
			float rectLeftStart = touchPoint.x - touchLineTextWidth / 2;
			float rectTopStart = touchPoint.y - lineHeight / 2;
			if (CKChartData.getTouchText().size() == 2) {
				drawText1 = CKChartData.getTouchText().get(0) + ":" + xAxisDatas.get(touchRegion);
				drawText2 = CKChartData.getTouchText().get(1) + ":" + datas.get(0).get(touchRegion);
				canvas.drawRect(rectLeftStart, rectTopStart, rectLeftStart + touchLineTextWidth,
						rectTopStart + 3 * margin + 2 * singleLineHeight, paint);
				canvas.drawText(drawText2, rectLeftStart + 10, rectTopStart + margin + singleLineHeight,
						indicateTextPaint);
				canvas.drawText(drawText1, rectLeftStart + 10, rectTopStart + margin + 2 * singleLineHeight,
						indicateTextPaint);
			} else if (CKChartData.getTouchText().size() == 3) {
				drawText1 = CKChartData.getTouchText().get(0) + ":" + xAxisDatas.get(touchRegion);
				drawText2 = CKChartData.getTouchText().get(1) + ":" + datas.get(1).get(touchRegion);
				drawText3 = CKChartData.getTouchText().get(2) + ":" + datas.get(0).get(touchRegion);
				canvas.drawRect(rectLeftStart, rectTopStart, rectLeftStart + touchLineTextWidth,
						rectTopStart + 4 * margin + 3 * singleLineHeight, paint);
				canvas.drawText(drawText3, rectLeftStart + 10, margin + rectTopStart + singleLineHeight,
						indicateTextPaint);
				canvas.drawText(drawText2, rectLeftStart + 10, margin + rectTopStart + 2 * singleLineHeight,
						indicateTextPaint);
				canvas.drawText(drawText1, rectLeftStart + 10, margin + rectTopStart + 3 * singleLineHeight,
						indicateTextPaint);
			}
		}

	}

	class gesture extends SimpleOnGestureListener {
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			int position = (int) (e.getX() / barStep);
			touchPoint = new PointF(e.getX(), e.getY());
			touchRegion = position;
			if (chartClickListener != null) {
				chartClickListener.onCharClick(position);
			}
			invalidate();
			return super.onSingleTapUp(e);
		}
	}

	void drawBitmapByRect(Canvas canvas, Bitmap bitmap, Paint paint) {
		int drawStep = 1;
		if (xAxisDatas.size() > 16) {
			drawStep = 4;
		}

		for (int i = 0; i < drawStep; i++) {
			if (animationFactor > i * (1.0f / drawStep)) {
				tempBitmap1 = Bitmap.createBitmap(bitmap, i * bitmap.getWidth() / drawStep, 0,
						bitmap.getWidth() / drawStep, bitmap.getHeight());
				canvas.drawBitmap(tempBitmap1, i * bitmap.getWidth() / drawStep, 0, paint);
			}
		}
	}
}
