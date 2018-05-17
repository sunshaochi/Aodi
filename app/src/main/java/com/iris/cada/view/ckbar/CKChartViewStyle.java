package com.iris.cada.view.ckbar;

import java.util.List;

import android.graphics.Typeface;

public class CKChartViewStyle {
	// 左右间隔,左右轴的宽度,上下的间隔,上下的宽度,左右轴文字大小,颜色,样式,标题的文字大小,颜色,样式,底部的文字大小,颜色,样式||x轴每步的宽度
	int leftMargin = 5, rightMargin = 5;
	int leftAxisWidth = 40, rightAxisWidth = 40, topWidth = 40, btmWidth = 20, xAxisheight = 20;
	int leftTextSize = 10, rightTextSize = 10, topTextSize = 20, btmTextSize = 14, xAxisTextSize = 10;
	Typeface typeFace;
	String leftTextColor = "#000000", rightTextColor = "#000000", topTextColor = "#000000", btmTextColor = "#000000",
			xAxisTextColor = "#000000", xAxisColor = "#222222";
	int barStep = 40, barWidth = 20;
	List<String> barColors, lineColors;
	public static Boolean HAD_RIGHT_AXIS = true;

	public CKChartViewStyle(List<String> barColors, List<String> lineColors) {
		this.barColors = barColors;
		this.lineColors = lineColors;
		if (rightAxisWidth == 0) {
			HAD_RIGHT_AXIS = false;
		}else{
			HAD_RIGHT_AXIS = true;
		}
	}

	public CKChartViewStyle(int leftMargin, int rightMargin, int leftAxisWidth, int rightAxisWidth, int topWidth,
			int btmWidth, int xAxisheight, int leftTextSize, int rightTextSize, int topTextSize, int btmTextSize,
			int xAxisTextSize, Typeface typeFace, String leftTextColor, String rightTextColor, String topTextColor,
			String btmTextColor, String xAxisTextColor, String xAxisColor, int barStep, int barWidth,
			List<String> barColors, List<String> lineColors) {
		super();
		this.leftMargin = leftMargin;
		this.rightMargin = rightMargin;
		this.leftAxisWidth = leftAxisWidth;
		this.rightAxisWidth = rightAxisWidth;
		if (rightAxisWidth == 0) {
			HAD_RIGHT_AXIS = false;
		}else{
			HAD_RIGHT_AXIS = true;
		}
		this.topWidth = topWidth;
		this.btmWidth = btmWidth;
		this.xAxisheight = xAxisheight;
		this.leftTextSize = leftTextSize;
		this.rightTextSize = rightTextSize;
		this.topTextSize = topTextSize;
		this.btmTextSize = btmTextSize;
		this.xAxisTextSize = xAxisTextSize;
		this.typeFace = typeFace;
		this.leftTextColor = leftTextColor;
		this.rightTextColor = rightTextColor;
		this.topTextColor = topTextColor;
		this.btmTextColor = btmTextColor;
		this.xAxisTextColor = xAxisTextColor;
		this.xAxisColor = xAxisColor;
		this.barStep = barStep;
		this.barWidth = barWidth;
		this.barColors = barColors;
		this.lineColors = lineColors;
	}

	public int getLeftMargin() {
		return leftMargin;
	}

	public void setLeftMargin(int leftMargin) {
		this.leftMargin = leftMargin;
	}

	public int getRightMargin() {
		return rightMargin;
	}

	public void setRightMargin(int rightMargin) {
		this.rightMargin = rightMargin;
	}

	public int getLeftAxisWidth() {
		return leftAxisWidth;
	}

	public void setLeftAxisWidth(int leftAxisWidth) {
		this.leftAxisWidth = leftAxisWidth;
	}

	public int getRightAxisWidth() {
		return rightAxisWidth;
	}

	public void setRightAxisWidth(int rightAxisWidth) {
		this.rightAxisWidth = rightAxisWidth;
		if (rightAxisWidth == 0) {
			HAD_RIGHT_AXIS = false;
		}else{
			HAD_RIGHT_AXIS = true;
		}
	}

	public int getTopWidth() {
		return topWidth;
	}

	public void setTopWidth(int topWidth) {
		this.topWidth = topWidth;
	}

	public int getBtmWidth() {
		return btmWidth;
	}

	public void setBtmWidth(int btmWidth) {
		this.btmWidth = btmWidth;
	}

	public int getxAxisheight() {
		return xAxisheight;
	}

	public void setxAxisheight(int xAxisheight) {
		this.xAxisheight = xAxisheight;
	}

	public int getLeftTextSize() {
		return leftTextSize;
	}

	public void setLeftTextSize(int leftTextSize) {
		this.leftTextSize = leftTextSize;
	}

	public int getRightTextSize() {
		return rightTextSize;
	}

	public void setRightTextSize(int rightTextSize) {
		this.rightTextSize = rightTextSize;
	}

	public int getTopTextSize() {
		return topTextSize;
	}

	public void setTopTextSize(int topTextSize) {
		this.topTextSize = topTextSize;
	}

	public int getBtmTextSize() {
		return btmTextSize;
	}

	public void setBtmTextSize(int btmTextSize) {
		this.btmTextSize = btmTextSize;
	}

	public int getxAxisTextSize() {
		return xAxisTextSize;
	}

	public void setxAxisTextSize(int xAxisTextSize) {
		this.xAxisTextSize = xAxisTextSize;
	}

	public Typeface getTypeFace() {
		return typeFace;
	}

	public void setTypeFace(Typeface typeFace) {
		this.typeFace = typeFace;
	}

	public String getLeftTextColor() {
		return leftTextColor;
	}

	public void setLeftTextColor(String leftTextColor) {
		this.leftTextColor = leftTextColor;
	}

	public String getRightTextColor() {
		return rightTextColor;
	}

	public void setRightTextColor(String rightTextColor) {
		this.rightTextColor = rightTextColor;
	}

	public String getTopTextColor() {
		return topTextColor;
	}

	public void setTopTextColor(String topTextColor) {
		this.topTextColor = topTextColor;
	}

	public String getBtmTextColor() {
		return btmTextColor;
	}

	public void setBtmTextColor(String btmTextColor) {
		this.btmTextColor = btmTextColor;
	}

	public String getxAxisTextColor() {
		return xAxisTextColor;
	}

	public void setxAxisTextColor(String xAxisTextColor) {
		this.xAxisTextColor = xAxisTextColor;
	}

	public String getxAxisColor() {
		return xAxisColor;
	}

	public void setxAxisColor(String xAxisColor) {
		this.xAxisColor = xAxisColor;
	}

	public int getBarStep() {
		return barStep;
	}

	public void setBarStep(int barStep) {
		this.barStep = barStep;
	}

	public int getBarWidth() {
		return barWidth;
	}

	public void setBarWidth(int barWidth) {
		this.barWidth = barWidth;
	}

	public List<String> getBarColors() {
		return barColors;
	}

	public void setBarColors(List<String> barColors) {
		this.barColors = barColors;
	}

	public List<String> getLineColors() {
		return lineColors;
	}

	public void setLineColors(List<String> lineColors) {
		this.lineColors = lineColors;
	}

	@Override
	public String toString() {
		return "CKChartViewStyle [leftMargin=" + leftMargin + ", rightMargin=" + rightMargin + ", leftAxisWidth="
				+ leftAxisWidth + ", rightAxisWidth=" + rightAxisWidth + ", topWidth=" + topWidth + ", btmWidth="
				+ btmWidth + ", xAxisheight=" + xAxisheight + ", leftTextSize=" + leftTextSize + ", rightTextSize="
				+ rightTextSize + ", topTextSize=" + topTextSize + ", btmTextSize=" + btmTextSize + ", xAxisTextSize="
				+ xAxisTextSize + ", typeFace=" + typeFace + ", leftTextColor=" + leftTextColor + ", rightTextColor="
				+ rightTextColor + ", topTextColor=" + topTextColor + ", btmTextColor=" + btmTextColor
				+ ", xAxisTextColor=" + xAxisTextColor + ", xAxisColor=" + xAxisColor + ", barStep=" + barStep
				+ ", barWidth=" + barWidth + ", barColors=" + barColors + ", lineColors=" + lineColors + "]";
	}

}
