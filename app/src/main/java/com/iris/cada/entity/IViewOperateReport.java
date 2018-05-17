package com.iris.cada.entity;

import java.io.Serializable;

public class IViewOperateReport implements Serializable{
  
	private static final long serialVersionUID = 1L;
	//销售顾问
	private String salesconsultant;
	//车系
	private String models;
	//新增潜客
	private String xinpotential;
	//首次进店试乘试驾
	private String firstDrive;
	//报价数
	private String quotation;
	//成交数
	private String indent;
	//首次进店成交数
	private String firstindent;
	//总交车数
	private String Turnover;
	//接待总数
	private String receivesum;
	//再次进店
	private String again;
	//当月客户再次进店
	private String month;
	//活跃再次
	private String active;
	//休眠再次
	private String dormant;
	//首次试乘试驾率
	private String firstDriverate;
	//再次进店率
	private String againrate;
	//报价率
	private String offerrate;
	//成交率
	private String closerate;
	
	public IViewOperateReport() {
		super();
	}
	
	public IViewOperateReport(String salesconsultant, String models,
			String xinpotential, String firstDrive, String quotation,
			String indent, String firstindent, String turnover,
			String receivesum, String again, String month, String active,
			String dormant, String firstDriverate, String againrate,
			String offerrate, String closerate) {
		super();
		this.salesconsultant = salesconsultant;
		this.models = models;
		this.xinpotential = xinpotential;
		this.firstDrive = firstDrive;
		this.quotation = quotation;
		this.indent = indent;
		this.firstindent = firstindent;
		Turnover = turnover;
		this.receivesum = receivesum;
		this.again = again;
		this.month = month;
		this.active = active;
		this.dormant = dormant;
		this.firstDriverate = firstDriverate;
		this.againrate = againrate;
		this.offerrate = offerrate;
		this.closerate = closerate;
	}

	public String getSalesconsultant() {
		return salesconsultant;
	}
	public void setSalesconsultant(String salesconsultant) {
		this.salesconsultant = salesconsultant;
	}
	public String getModels() {
		return models;
	}
	public void setModels(String models) {
		this.models = models;
	}
	public String getXinpotential() {
		return xinpotential;
	}
	public void setXinpotential(String newpotential) {
		this.xinpotential = newpotential;
	}
	public String getFirstDrive() {
		return firstDrive;
	}
	public void setFirstDrive(String firstDrive) {
		this.firstDrive = firstDrive;
	}
	public String getQuotation() {
		return quotation;
	}
	public void setQuotation(String quotation) {
		this.quotation = quotation;
	}
	public String getIndent() {
		return indent;
	}
	public void setIndent(String indent) {
		this.indent = indent;
	}
	public String getFirstindent() {
		return firstindent;
	}
	public void setFirstindent(String firstindent) {
		this.firstindent = firstindent;
	}
	public String getTurnover() {
		return Turnover;
	}
	public void setTurnover(String turnover) {
		Turnover = turnover;
	}
	public String getReceivesum() {
		return receivesum;
	}
	public void setReceivesum(String receivesum) {
		this.receivesum = receivesum;
	}
	public String getAgain() {
		return again;
	}
	public void setAgain(String again) {
		this.again = again;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getDormant() {
		return dormant;
	}
	public void setDormant(String dormant) {
		this.dormant = dormant;
	}
	public String getFirstDriverate() {
		return firstDriverate;
	}
	public void setFirstDriverate(String firstDriverate) {
		this.firstDriverate = firstDriverate;
	}
	public String getAgainrate() {
		return againrate;
	}
	public void setAgainrate(String againrate) {
		this.againrate = againrate;
	}
	public String getOfferrate() {
		return offerrate;
	}
	public void setOfferrate(String offerrate) {
		this.offerrate = offerrate;
	}
	public String getCloserate() {
		return closerate;
	}
	public void setCloserate(String closerate) {
		this.closerate = closerate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "IViewOperateReport [Turnover=" + Turnover + ", active="
				+ active + ", again=" + again + ", againrate=" + againrate
				+ ", closerate=" + closerate + ", dormant=" + dormant
				+ ", firstDrive=" + firstDrive + ", firstDriverate="
				+ firstDriverate + ", firstindent=" + firstindent + ", indent="
				+ indent + ", models=" + models + ", month=" + month
				+ ", newpotential=" + xinpotential + ", offerrate=" + offerrate
				+ ", quotation=" + quotation + ", receivesum=" + receivesum
				+ ", salesconsultant=" + salesconsultant + "]";
	}
	
}
