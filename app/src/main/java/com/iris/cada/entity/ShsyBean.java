package com.iris.cada.entity;

import java.io.Serializable;



public class ShsyBean implements Serializable{
	private static final long serialVersionUID = 1L;
		
	
	private String maintenanceTimes;//维修台次  
	private String outputValue;//单车产值
	private String income;//综合收入
	private String profit;//综合毛利
	private String returntime;//更新时间
	
	public String getMaintenanceTimes() {
		return maintenanceTimes;
	}
	public void setMaintenanceTimes(String maintenanceTimes) {
		this.maintenanceTimes = maintenanceTimes;
	}
	public String getOutputValue() {
		return outputValue;
	}
	public void setOutputValue(String outputValue) {
		this.outputValue = outputValue;
	}
	public String getIncome() {
		return income;
	}
	public void setIncome(String income) {
		this.income = income;
	}
	public String getProfit() {
		return profit;
	}
	public void setProfit(String profit) {
		this.profit = profit;
	}
	public String getReturntime() {
		return returntime;
	}
	public void setReturntime(String returntime) {
		this.returntime = returntime;
	}
	@Override
	public String toString() {
		return "ShsyBean [maintenanceTimes=" + maintenanceTimes + ", outputValue=" + outputValue + ", income=" + income
				+ ", profit=" + profit + ", returntime=" + returntime + "]";
	}
	
	
	

}
