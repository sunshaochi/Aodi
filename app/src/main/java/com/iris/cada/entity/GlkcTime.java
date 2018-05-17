package com.iris.cada.entity;

public class GlkcTime {
	
	private String repertoryUpdateTime;//库存更新时间
	
	private String receptionUpdateTime;//潜客成交数更新时间
	private String avgStorageDays;//平均库存天数
	private String storageDaysNum;//库存数量
	private String profitUpdateTime;//利润跟新时间
	public String getRepertoryUpdateTime() {
		return repertoryUpdateTime;
	}
	public void setRepertoryUpdateTime(String repertoryUpdateTime) {
		this.repertoryUpdateTime = repertoryUpdateTime;
	}
	public String getReceptionUpdateTime() {
		return receptionUpdateTime;
	}
	public void setReceptionUpdateTime(String receptionUpdateTime) {
		this.receptionUpdateTime = receptionUpdateTime;
	}
	public String getAvgStorageDays() {
		return avgStorageDays;
	}
	public void setAvgStorageDays(String avgStorageDays) {
		this.avgStorageDays = avgStorageDays;
	}
	public String getStorageDaysNum() {
		return storageDaysNum;
	}
	public void setStorageDaysNum(String storageDaysNum) {
		this.storageDaysNum = storageDaysNum;
	}
	public String getProfitUpdateTime() {
		return profitUpdateTime;
	}
	public void setProfitUpdateTime(String profitUpdateTime) {
		this.profitUpdateTime = profitUpdateTime;
	}
	@Override
	public String toString() {
		return "GlkcTime [repertoryUpdateTime=" + repertoryUpdateTime + ", receptionUpdateTime=" + receptionUpdateTime
				+ ", avgStorageDays=" + avgStorageDays + ", storageDaysNum=" + storageDaysNum + ", profitUpdateTime="
				+ profitUpdateTime + "]";
	}
	
	
	
}
