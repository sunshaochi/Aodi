package com.iris.cada.view.ckbar;

import java.util.List;

/**
 * 所有传入的y轴数据应该是已经排序好的,第一位为最小值,最后一位为最大值
 * 
 * @author CK
 *
 */
public class CKChartData {
	// 左边y轴的数据,右边y轴的数据,x轴的数据,bar的数据,line的数据
	List<String> leftAxisDatas, rightAxisDatas, xAxisDatas, barDatas, lineDatas;
	String maxLeft, minLeft, maxRight, minRight;
	public static List<String> touchText;
	public static float YSTART = 0;

	public static List<String> getTouchText() {
		return touchText;
	}

	public static void setTouchText(List<String> touchText) {
		CKChartData.touchText = touchText;
	}

	public CKChartData(List<String> leftAxisDatas, List<String> rightAxisDatas, List<String> xAxisDatas,
			List<String> barDatas, List<String> lineDatas) {
		super();
		this.leftAxisDatas = leftAxisDatas;
		this.rightAxisDatas = rightAxisDatas;
		this.xAxisDatas = xAxisDatas;
		this.barDatas = barDatas;
		this.lineDatas = lineDatas;
		if (leftAxisDatas != null && leftAxisDatas.size() > 0) {
			maxLeft = leftAxisDatas.get(leftAxisDatas.size() - 1);
			minLeft = leftAxisDatas.get(0);
		}
		if (rightAxisDatas != null && rightAxisDatas.size() > 0) {
			maxRight = rightAxisDatas.get(rightAxisDatas.size() - 1);
			minRight = rightAxisDatas.get(0);
		}
	}

	public List<String> getLeftAxisDatas() {
		return leftAxisDatas;
	}

	public void setLeftAxisDatas(List<String> leftAxisDatas) {
		this.leftAxisDatas = leftAxisDatas;
	}

	public List<String> getRightAxisDatas() {
		return rightAxisDatas;
	}

	public void setRightAxisDatas(List<String> rightAxisDatas) {
		this.rightAxisDatas = rightAxisDatas;
	}

	public List<String> getxAxisDatas() {
		return xAxisDatas;
	}

	public void setxAxisDatas(List<String> xAxisDatas) {
		this.xAxisDatas = xAxisDatas;
	}

	public List<String> getBarDatas() {
		return barDatas;
	}

	public void setBarDatas(List<String> barDatas) {
		this.barDatas = barDatas;
	}

	public List<String> getLineDatas() {
		return lineDatas;
	}

	public void setLineDatas(List<String> lineDatas) {
		this.lineDatas = lineDatas;
	}

	public String getMaxLeft() {
		return maxLeft;
	}

	public void setMaxLeft(String maxLeft) {
		this.maxLeft = maxLeft;
	}

	public String getMinLeft() {
		return minLeft;
	}

	public void setMinLeft(String minLeft) {
		this.minLeft = minLeft;
	}

	public String getMaxRight() {
		return maxRight;
	}

	public void setMaxRight(String maxRight) {
		this.maxRight = maxRight;
	}

	public String getMinRight() {
		return minRight;
	}

	public void setMinRight(String minRight) {
		this.minRight = minRight;
	}

}
