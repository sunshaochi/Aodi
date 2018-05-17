package com.iris.cada.entity;

public class IViewFirst {

	private static final long serialVersionUID = 1L;
	// 总成交数
	private String turnover;
	// 交车数
	private String retail;
	// 总收入
	private String gross;
	// 总毛利
	private String revenue;
	/**
	 * 总成交数导入时间
	 */
	private String timeTurnover;
	/**
	 * 总交车数导入时间
	 */
	private String timeRetail;
	/**
	 * 总总收入导入时间
	 */
	private String timeGross;
	/**
	 * 总总毛利导入时间
	 */
	private String timeRevenue;

	public String getTimeTurnover() {
		return timeTurnover;
	}

	public void setTimeTurnover(String timeTurnover) {
		this.timeTurnover = timeTurnover;
	}

	public String getTimeRetail() {
		return timeRetail;
	}

	public void setTimeRetail(String timeRetail) {
		this.timeRetail = timeRetail;
	}

	public String getTimeGross() {
		return timeGross;
	}

	public void setTimeGross(String timeGross) {
		this.timeGross = timeGross;
	}

	public String getTimeRevenue() {
		return timeRevenue;
	}

	public void setTimeRevenue(String timeRevenue) {
		this.timeRevenue = timeRevenue;
	}

	public String getTurnover() {
		return turnover;
	}

	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	public String getRetail() {
		return retail;
	}

	public void setRetail(String retail) {
		this.retail = retail;
	}

	public String getGross() {
		return gross;
	}

	public void setGross(String gross) {
		this.gross = gross;
	}

	public String getRevenue() {
		return revenue;
	}

	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}

	public IViewFirst() {
		super();
	}





	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "IViewFirst [Gross=" + gross + ", Retail=" + retail + ", Revenue=" + revenue + ", Turnover=" + turnover
				+ "]";
	}
}
