package com.iris.cada.entity;

import java.io.Serializable;
/*
 * 诊断diagnose  
 */
public class Check implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//销售顾问
	private String SalesConsultant;
	//销售顾问别名 
	private String Alias; 
	//车型
	private String CarType;
	//新增潜客
	private String XinPotentialCustomer;
	//成交率 
	private String LookToBuyRate;
	//衍生业务平均单车盈利--Derivatives business average car profits
	private String DerivativesBusinessAverageCarProfits;
	
	//新增
	//接待总数
	private String receptionTotal ; 
	
	private String inventory;//库存数量（后来没用了用了下面的字段）
	
	private String stockNum;//库存数量

	private String CarTypeCode;//车型编码
	
	
	
	
	
	public String getStockNum() {
		return stockNum;
	}
	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}
	public String getInventory() {
		return inventory;
	}
	public void setInventory(String inventory) {
		this.inventory = inventory;
	}
	public String getReceptionTotal() {
		return receptionTotal;
	}
	public void setReceptionTotal(String receptionTotal) {
		this.receptionTotal = receptionTotal;
	}
	
	
//	public Check(){
//		super();
//	}
//	public Check(String alias,String carType ,String salesConsultant, String xinPotentialCustomer,
//			String lookToBuyRate,String derivativesBusinessAverageCarProfits){
//		super();
//		this.Alias = alias;
//		this.CarType=carType;
//		this.SalesConsultant=salesConsultant;
//		this.XinPotentialCustomer=xinPotentialCustomer;
//		this.LookToBuyRate=lookToBuyRate;
//		this.DerivativesBusinessAverageCarProfits=derivativesBusinessAverageCarProfits;
//	}
//
//	public Check(String carType , String xinPotentialCustomer,
//			String lookToBuyRate,String derivativesBusinessAverageCarProfits){
//		super();
//		this.CarType=carType;
//		this.XinPotentialCustomer=xinPotentialCustomer;
//		this.LookToBuyRate=lookToBuyRate;
//		DerivativesBusinessAverageCarProfits=derivativesBusinessAverageCarProfits;
//	}
	
	public String getSalesConsultant() {
		return SalesConsultant;
	}
	public void setSalesConsultant(String salesConsultant) {
		this.SalesConsultant = salesConsultant;
	}
	public String getAlias() {
		return Alias;
	}
	public void setAlias(String alias) {
		this.Alias = alias;
	}
	public String getCarType() {
		return CarType;
	}
	public void setCarType(String carType) {
		this.CarType = carType;
	}
	public String getXinPotentialCustomer() {
		return XinPotentialCustomer;
	}
	public void setXinPotentialCustomer(String xinPotentialCustomer) {
		this.XinPotentialCustomer = xinPotentialCustomer;
	}
	public String getLookToBuyRate() {
		return LookToBuyRate;
	}
	public void setLookToBuyRate(String lookToBuyRate) {
		this.LookToBuyRate = lookToBuyRate;
	}
	public String getDerivativesBusinessAverageCarProfits() {
		return DerivativesBusinessAverageCarProfits;
	}
	public void setDerivativesBusinessAverageCarProfits(String derivativesBusinessAverageCarProfits) {
		DerivativesBusinessAverageCarProfits = derivativesBusinessAverageCarProfits;
	}

	public String getCarTypeCode() {
		return CarTypeCode;
	}

	public void setCarTypeCode(String carTypeCode) {
		CarTypeCode = carTypeCode;
	}

	@Override
	public String toString() {
		return "Check [SalesConsultant=" + SalesConsultant + ", Alias=" + Alias + ", CarType=" + CarType
				+ ", XinPotentialCustomer=" + XinPotentialCustomer + ", LookToBuyRate=" + LookToBuyRate
				+ ", DerivativesBusinessAverageCarProfits=" + DerivativesBusinessAverageCarProfits + ", receptionTotal="
				+ receptionTotal + ", inventory=" + inventory + ", stockNum=" + stockNum + "]";
	}
	  
	
   




}



 











