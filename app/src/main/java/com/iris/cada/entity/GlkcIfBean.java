package com.iris.cada.entity;

public class GlkcIfBean {
	private String compare;//本月至今
	private String closeRate;//成交率
	private String customerNum;//潜客数
	private String storageDaysNum;//库存数量
	private String gp3;//单车gp3
	public String getCompare() {
		return compare;
	}
	public void setCompare(String compare) {
		this.compare = compare;
	}
	public String getCloseRate() {
		return closeRate;
	}
	public void setCloseRate(String closeRate) {
		this.closeRate = closeRate;
	}
	public String getCustomerNum() {
		return customerNum;
	}
	public void setCustomerNum(String customerNum) {
		this.customerNum = customerNum;
	}
	public String getStorageDaysNum() {
		return storageDaysNum;
	}
	public void setStorageDaysNum(String storageDaysNum) {
		this.storageDaysNum = storageDaysNum;
	}
	public String getGp3() {
		return gp3;
	}
	public void setGp3(String gp3) {
		this.gp3 = gp3;
	}
	@Override
	public String toString() {
		return "GlkcIfBean [compare=" + compare + ", closeRate=" + closeRate + ", customerNum=" + customerNum
				+ ", storageDaysNum=" + storageDaysNum + ", gp3=" + gp3 + "]";
	}
	
	
}
