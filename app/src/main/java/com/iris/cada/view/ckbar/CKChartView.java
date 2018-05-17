package com.iris.cada.view.ckbar;

import java.util.ArrayList;
import java.util.List;

import com.iris.cada.view.ckbar.BarContentView.ChartClickListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CKChartView extends RelativeLayout {
	Boolean onLayout = true;
	Context context;
	CKChartViewStyle style;
	CKChartData datas;
	String title;
	List<String> indicates, indicateColors;
	RelativeLayout.LayoutParams leftMarginViewParams, leftParams, rightParams, centerScrollParams, centerParams,
			titleParams, btmParams;
	final int leftMarginViewTag = 1218, leftViewTag = 1219, centerScrollTag = 1223, centerTag = 1224,
			titleViewTag = 1220, btmViewTag = 1221, rightViewTag = 1222;
	RelativeLayout.LayoutParams params;
	TextView tv;
	ChartClickListener chartClickListener;

	public CKChartView(Context context, CKChartViewStyle style, CKChartData datas, String title, List<String> indicates,
			List<String> indicateColors, ChartClickListener chartClickListener) {
		super(context);
		this.context = context;
		this.style = style;
		this.datas = datas;
		this.title = title;
		this.indicates = indicates;
		this.indicateColors = indicateColors;
		this.chartClickListener = chartClickListener;

	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// if (getMeasuredHeight() > 0 && getMeasuredWidth() > 0) {
		if (onLayout) {
			// -------------------view init start--------------------
			int leftMarginViewWidth = CKBarViewHelper.dp2px(style.getLeftMargin(), context);
			int leftViewWidth = CKBarViewHelper.dp2px(style.getLeftAxisWidth(), context);
			int titleViewHeight = CKBarViewHelper.dp2px(style.getTopWidth(), context);
			int btmViewHeight = CKBarViewHelper.dp2px(style.getBtmWidth(), context);
			int rightViewWidth = CKBarViewHelper.dp2px(style.getRightAxisWidth(), context);
			int rightMarginWidth = CKBarViewHelper.dp2px(style.getRightMargin(), context);
			int centerScrollViewWidth = getMeasuredWidth() - leftMarginViewWidth - leftViewWidth - rightMarginWidth
					- rightViewWidth;
			int yAxisStart = getMeasuredHeight() - btmViewHeight
					- CKBarViewHelper.dp2px(style.getxAxisheight(), context);
			int yAxisEnd = titleViewHeight;

			View leftMarginView = new View(context);
			leftMarginView.setId(leftMarginViewTag);

			Paint leftPaint = getPaint(style.getLeftTextSize(), style.getLeftTextColor(), style.getTypeFace());
			YAxis leftView = new YAxis(context, datas.getLeftAxisDatas(), leftPaint, yAxisStart, yAxisEnd, true);
			leftView.setId(leftViewTag);

			Paint rightPaint = getPaint(style.getRightTextSize(), style.getRightTextColor(), style.getTypeFace());
			YAxis rightView = new YAxis(context, datas.getRightAxisDatas(), rightPaint, yAxisStart, yAxisEnd, false);
			rightView.setId(rightViewTag);

			Paint titlePaint = getPaint(style.getTopTextSize(), style.getTopTextColor(), style.getTypeFace());
			BarTitleView titleView = new BarTitleView(context, title, titlePaint);
			titleView.setId(titleViewTag);

			Paint btmPaint = getPaint(style.getBtmTextSize(), style.getBtmTextColor(), style.getTypeFace());
			BarIndicateView btmView = new BarIndicateView(context, indicates, btmPaint, indicateColors);
			btmView.setId(btmViewTag);

			Paint xAxisPaint = getPaint(style.getBtmTextSize(), style.getxAxisColor(), style.getTypeFace());
			Paint xAxisTextPaint = getPaint(style.getxAxisTextSize(), style.getxAxisTextColor(), style.getTypeFace());

			HorizontalScrollView centerScroll = new HorizontalScrollView(context);
			centerScroll.setId(centerScrollTag);

			// ------------view init end and view add start-------------

			leftMarginViewParams = new RelativeLayout.LayoutParams(leftMarginViewWidth,
					RelativeLayout.LayoutParams.MATCH_PARENT);
			leftMarginViewParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			addView(leftMarginView, leftMarginViewParams);

			leftParams = new RelativeLayout.LayoutParams(leftViewWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
			leftParams.addRule(RelativeLayout.RIGHT_OF, leftMarginView.getId());
			addView(leftView, leftParams);

			titleParams = new RelativeLayout.LayoutParams(centerScrollViewWidth, titleViewHeight);
			titleParams.addRule(RelativeLayout.RIGHT_OF, leftView.getId());
			titleParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			addView(titleView, titleParams);

			HorizontalScrollView centerViewContainer = new HorizontalScrollView(context);
			centerViewContainer.setId(centerScrollTag);
			centerViewContainer.setHorizontalScrollBarEnabled(false);
			centerScrollParams = new RelativeLayout.LayoutParams(centerScrollViewWidth,
					getMeasuredHeight() - titleViewHeight - btmViewHeight);
			centerScrollParams.addRule(RelativeLayout.RIGHT_OF, leftView.getId());
			centerScrollParams.addRule(RelativeLayout.BELOW, titleView.getId());
			// 再添加中心内容之前需要准备中心的数据,包括data,color,point
			// data
			List<List<String>> centerDatas = new ArrayList<List<String>>();
			centerDatas.add(datas.getBarDatas());
			centerDatas.add(datas.getLineDatas());
			// color
			List<List<String>> centerColors = new ArrayList<List<String>>();
			centerColors.add(style.getBarColors());
			centerColors.add(style.getLineColors());

			// pointF
			int height = getMeasuredHeight() - titleViewHeight - btmViewHeight;
			int width = datas.getxAxisDatas().size()
					* CKBarViewHelper.dp2px(style.barStep, context) > centerScrollViewWidth
							? datas.getxAxisDatas().size() * CKBarViewHelper.dp2px(style.barStep, context)
							: centerScrollViewWidth;
			List<List<PointF>> points = new ArrayList<List<PointF>>();
			if (datas.getMinLeft().contains("-")) {//如果y轴有负值
				CKChartData.YSTART = (height - CKBarViewHelper.dp2px(style.getxAxisheight(), context)) / 2;
			} else {
				CKChartData.YSTART = (height - CKBarViewHelper.dp2px(style.getxAxisheight(), context));
			}
			points.add(getPoint(datas.getMaxLeft(), datas.getMinLeft(),
					height - CKBarViewHelper.dp2px(style.getxAxisheight(), context), width, datas.getBarDatas(),
					false));
			points.add(getPoint(datas.getMaxRight(), datas.getMinRight(),
					height - CKBarViewHelper.dp2px(style.getxAxisheight(), context), width, datas.getLineDatas(),
					true));
			// new contentView
			BarContentView contentView = new BarContentView(context, width, height,
					CKBarViewHelper.dp2px(style.barWidth, context), centerDatas, points, centerColors,
					datas.getxAxisDatas(), style.typeFace, xAxisPaint, xAxisTextPaint,
					CKBarViewHelper.dp2px(style.getxAxisheight(), context), rightViewWidth, chartClickListener);
			FrameLayout.LayoutParams contentParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
					FrameLayout.LayoutParams.WRAP_CONTENT);
			centerViewContainer.addView(contentView, contentParams);
			addView(centerViewContainer, centerScrollParams);

			btmParams = new RelativeLayout.LayoutParams(centerScrollViewWidth, btmViewHeight);
			btmParams.addRule(RelativeLayout.RIGHT_OF, leftView.getId());
			btmParams.addRule(RelativeLayout.BELOW, centerViewContainer.getId());
			addView(btmView, btmParams);

			if (rightMarginWidth != 0) {
				rightParams = new RelativeLayout.LayoutParams(rightViewWidth, RelativeLayout.LayoutParams.MATCH_PARENT);
				rightParams.addRule(RelativeLayout.RIGHT_OF, centerViewContainer.getId());
				addView(rightView, rightParams);
			}
			// -------------view add end-------------------
			onLayout = false;
			// }
		}
	}

	// 获取坐标
	List<PointF> getPoint(String max, String min, int height, int width, List<String> datas, Boolean ISPERCENT) {
		// 如果是百分比的,那么所有的值都要处理
		List<PointF> result = new ArrayList<PointF>();
		float maxFloat = 0, minFloat = 0;
		float unitWidth = 0;
		if (datas.size() > 0) {
			unitWidth = width / datas.size();
		} else {
			return null;
		}
		if (ISPERCENT) {
			maxFloat = CKBarViewHelper.percent2float(max);
			minFloat = CKBarViewHelper.percent2float(min);
		} else {
			maxFloat = Float.parseFloat(max);
			minFloat = Float.parseFloat(min);
		}
		if (maxFloat == minFloat) {
			for (int i = 0; i < datas.size(); i++) {
				PointF temp = new PointF(i * unitWidth, height);
				result.add(temp);
			}
		} else {
			for (int i = 0; i < datas.size(); i++) {
				float tempData = 0;
				if (ISPERCENT) {
					tempData = CKBarViewHelper.percent2float(datas.get(i));
				} else {
					tempData = Float.parseFloat(datas.get(i));
				}
				PointF temp = new PointF();
				if (minFloat < 0) {//如果有负值
					if (tempData < 0) {
						temp = new PointF(i * unitWidth + unitWidth / 2,
								CKChartData.YSTART + Math.abs(tempData / (maxFloat)) * height / 2);
					} else {
						temp = new PointF(i * unitWidth + unitWidth / 2,
								CKChartData.YSTART - Math.abs(tempData / (maxFloat)) * height / 2);
					}
				} else {//如果没有负值
					temp = new PointF(i * unitWidth + unitWidth / 2,
							height - Math.abs(tempData / (maxFloat - minFloat)) * height);
				}
				result.add(temp);
			}
		}
		return result;

	}

	Paint getPaint(int textSize, String color, Typeface typeface) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextSize(CKBarViewHelper.sp2px(textSize, context));
		paint.setColor(Color.parseColor(color));
		if (typeface != null) {
			paint.setTypeface(typeface);
		}
		return paint;
	}
}
