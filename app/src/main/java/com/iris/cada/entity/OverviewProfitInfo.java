package com.iris.cada.entity;

/**
 * 
 * @author LFJ
 *
 */
public class OverviewProfitInfo {
	/** 新增 */
	private String gp3;
	private String gp3rate;
	private String onCardTotalIncome; // 上牌总收入
	private String onCardTotalProfit;// 上牌总毛利
	private String onCardPermeaterate; // 上牌渗透率
	private String onCardGrossrate; // 上牌毛利率
	private String otherTotalIncome; // 其他总收入
	private String otherTotalProfit;// 其他总毛利
	private String otherGrossrate;// 其他毛利率
	
	private String carloanProfit;//车贷总毛利
	private String carloanGrossrate;//车贷毛利率
	private String xinInsuranceProfit;//新保总毛利
	private String xinInsurancePermeaterate;//新保毛利率
	private String lastInsuranceProfit;//延保总毛利
	private String lastInsurancePermeaterate;//延保毛利率

	// 新增


	public String getOnCardTotalIncome() {
		return onCardTotalIncome;
	}

	public void setOnCardTotalIncome(String onCardTotalIncome) {
		this.onCardTotalIncome = onCardTotalIncome;
	}

	public String getOnCardTotalProfit() {
		return onCardTotalProfit;
	}

	public void setOnCardTotalProfit(String onCardTotalProfit) {
		this.onCardTotalProfit = onCardTotalProfit;
	}

	public String getOnCardPermeaterate() {
		return onCardPermeaterate;
	}

	public void setOnCardPermeaterate(String onCardPermeaterate) {
		this.onCardPermeaterate = onCardPermeaterate;
	}

	public String getOnCardGrossrate() {
		return onCardGrossrate;
	}

	public void setOnCardGrossrate(String onCardGrossrate) {
		this.onCardGrossrate = onCardGrossrate;
	}

	public String getOtherTotalIncome() {
		return otherTotalIncome;
	}

	public void setOtherTotalIncome(String otherTotalIncome) {
		this.otherTotalIncome = otherTotalIncome;
	}

	public String getOtherTotalProfit() {
		return otherTotalProfit;
	}

	public void setOtherTotalProfit(String otherTotalProfit) {
		this.otherTotalProfit = otherTotalProfit;
	}

	public String getOtherGrossrate() {
		return otherGrossrate;
	}

	public void setOtherGrossrate(String otherGrossrate) {
		this.otherGrossrate = otherGrossrate;
	}
	
	

	public String getCarloanProfit() {
		return carloanProfit;
	}

	public void setCarloanProfit(String carloanProfit) {
		this.carloanProfit = carloanProfit;
	}

	public String getCarloanGrossrate() {
		return carloanGrossrate;
	}

	public void setCarloanGrossrate(String carloanGrossrate) {
		this.carloanGrossrate = carloanGrossrate;
	}

	public String getXinInsuranceProfit() {
		return xinInsuranceProfit;
	}

	public void setXinInsuranceProfit(String xinInsuranceProfit) {
		this.xinInsuranceProfit = xinInsuranceProfit;
	}

	public String getXinInsurancePermeaterate() {
		return xinInsurancePermeaterate;
	}

	public void setXinInsurancePermeaterate(String xinInsurancePermeaterate) {
		this.xinInsurancePermeaterate = xinInsurancePermeaterate;
	}

	public String getLastInsuranceProfit() {
		return lastInsuranceProfit;
	}

	public void setLastInsuranceProfit(String lastInsuranceProfit) {
		this.lastInsuranceProfit = lastInsuranceProfit;
	}

	public String getLastInsurancePermeaterate() {
		return lastInsurancePermeaterate;
	}

	public void setLastInsurancePermeaterate(String lastInsurancePermeaterate) {
		this.lastInsurancePermeaterate = lastInsurancePermeaterate;
	}



	// 新车GP1
	private String gp1 = null;
	// 新车GP1%
	private String gp1rate = null;
	// 新车GP2
	private String gp2 = null;
	// 新车GP2%
	private String gp2rate = null;
	// 精品总收入
	private String bountiqueIncome = null;
	// 精品总毛利
	private String bountiqueGross = null;
	// 精品加装率
	private String bountiqueAddrate = null;
	// 精品毛利率
	private String bountiqueGrossrate = null;
	// 车贷手续费 car loan fees
	private String carloanfees = null;
	
	// 车贷渗透率 car loan permeate rate
	private String carloanpermeaterate = null;
	// 新保收入 new Insurance Income
	private String xinInsuranceIncome = null;
	// 新保渗透率 new Insurance permeate rate
	private String xinInsurancepermeaterate = null;
	// 延保收入 last Insurance Income
	private String lastInsuranceIncome = null;
	// 延保渗透率 last Insurance permeate rate
	private String lastInsurancepermeaterate = null;
	// 置换返利
	private String permuterebate = null;
	// 置换率
	private String permuterate = null;

	public String getGp3() {
		return gp3;
	}

	public void setGp3(String gp3) {
		this.gp3 = gp3;
	}

	public String getGp3rate() {
		return gp3rate;
	}

	public void setGp3rate(String gp3rate) {
		this.gp3rate = gp3rate;
	}

	public String getGp1() {
		return gp1;
	}

	public void setGp1(String gp1) {
		this.gp1 = gp1;
	}

	public String getGp1rate() {
		return gp1rate;
	}

	public void setGp1rate(String gp1rate) {
		this.gp1rate = gp1rate;
	}

	public String getGp2() {
		return gp2;
	}

	public void setGp2(String gp2) {
		this.gp2 = gp2;
	}

	public String getGp2rate() {
		return gp2rate;
	}

	public void setGp2rate(String gp2rate) {
		this.gp2rate = gp2rate;
	}

	public String getBountiqueIncome() {
		return bountiqueIncome;
	}

	public void setBountiqueIncome(String bountiqueIncome) {
		this.bountiqueIncome = bountiqueIncome;
	}

	public String getBountiqueGross() {
		return bountiqueGross;
	}

	public void setBountiqueGross(String bountiqueGross) {
		this.bountiqueGross = bountiqueGross;
	}

	public String getBountiqueAddrate() {
		return bountiqueAddrate;
	}

	public void setBountiqueAddrate(String bountiqueAddrate) {
		this.bountiqueAddrate = bountiqueAddrate;
	}

	public String getBountiqueGrossrate() {
		return bountiqueGrossrate;
	}

	public void setBountiqueGrossrate(String bountiqueGrossrate) {
		this.bountiqueGrossrate = bountiqueGrossrate;
	}

	public String getCarloanfees() {
		return carloanfees;
	}

	public void setCarloanfees(String carloanfees) {
		this.carloanfees = carloanfees;
	}

	public String getCarloanpermeaterate() {
		return carloanpermeaterate;
	}

	public void setCarloanpermeaterate(String carloanpermeaterate) {
		this.carloanpermeaterate = carloanpermeaterate;
	}

	public String getXinInsuranceIncome() {
		return xinInsuranceIncome;
	}

	public void setXinInsuranceIncome(String xinInsuranceIncome) {
		this.xinInsuranceIncome = xinInsuranceIncome;
	}

	public String getXinInsurancepermeaterate() {
		return xinInsurancepermeaterate;
	}

	public void setXinInsurancepermeaterate(String xinInsurancepermeaterate) {
		this.xinInsurancepermeaterate = xinInsurancepermeaterate;
	}

	public String getLastInsuranceIncome() {
		return lastInsuranceIncome;
	}

	public void setLastInsuranceIncome(String lastInsuranceIncome) {
		this.lastInsuranceIncome = lastInsuranceIncome;
	}

	public String getLastInsurancepermeaterate() {
		return lastInsurancepermeaterate;
	}

	public void setLastInsurancepermeaterate(String lastInsurancepermeaterate) {
		this.lastInsurancepermeaterate = lastInsurancepermeaterate;
	}

	public String getPermuterebate() {
		return permuterebate;
	}

	public void setPermuterebate(String permuterebate) {
		this.permuterebate = permuterebate;
	}

	public String getPermuterate() {
		return permuterate;
	}

	public void setPermuterate(String permuterate) {
		this.permuterate = permuterate;
	}

}
