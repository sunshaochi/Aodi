package com.iris.cada.entity;

import java.io.Serializable;

public class ILogProfit  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 车型
	 */
	private String Models;
	/**
	 * 利润
	 */
	private String Profit;
	/**
	 * 渗透率
	 */
	private String Rate;
	
	
	public ILogProfit() {
		super();
	}
	/**
	 * 衍生业务
	 */
	public ILogProfit(String models, String profit, String rate) {
		super();
		Models = models;
		Profit = profit;
		Rate = rate;
	}
	/**
	 * 部门利润
	 */
	public ILogProfit(String models, String profit) {
		super();
		Models = models;
		Profit = profit;
	}
	public String getModels() {
		return Models;
	}
	public void setModels(String models) {
		Models = models;
	}
	public String getProfit() {
		return Profit;
	}
	public void setProfit(String profit) {
		Profit = profit;
	}
	public String getRate() {
		return Rate;
	}
	public void setRate(String rate) {
		Rate = rate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "ILogProfit [Models=" + Models + ", Profit=" + Profit
				+ ", Rate=" + Rate + "]";
	}
}
