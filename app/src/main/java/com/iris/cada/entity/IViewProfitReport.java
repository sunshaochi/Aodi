package com.iris.cada.entity;

public class IViewProfitReport {

	private static final long serialVersionUID = 1L;

	// 销售顾问
	private String salesconsultant;
	// 车系
	private String models;
	// 总交车数
	private String Turnover;
	// 精品销售数
	private String bountiqueSaleNum;
	// 精品加装率
	private String bountiqueAddrate;
	// 精品总毛利
	private String bountiqueGross;
	// 精品毛利率
	private String bountiqueGrossrate;
	// 平均单车精品毛利
	private String avgbountiqueGross;
	// 车贷销售数
	private String carloanSaleNum;
	// 车贷渗透率
	private String carloanpermeaterate;
	// 车贷销售服务费
	private String carloanfees;
	// 平均单车车贷毛利
	private String avgcarloanGross;
	// 新保销售数
	private String xinInsuranceSaleNum;
	// 新保渗透率
	private String xinInsurancepermeaterate;
	// 新保销售本店收入
	private String xinInsuranceIncome;
	// 平均单车新保毛利
	private String avgxinInsuranceGross;
	// 延保销售数
	private String lastInsuranceSaleNum;
	// 延保渗透率
	private String lastInsurancepermeaterate;
	// 延保销售本店收入
	private String lastInsuranceIncome;
	// 平均单车延保毛利
	private String avglastInsuranceGross;
	// 置换数
	private String permuteSaleNum;
	// 置换率
	private String permuterate;
	// 置换返利
	private String permuterebate;
	// 平均单车置换返利
	private String avgpermuteGross;
	
	//新增上牌
	//上牌数
	private String onCardSaleNum;
	//上牌渗透率
	private String onCardPermeaterate;
	//上牌总毛利
	private String onCardTotalProfit;
	//上牌毛利率
	private String onCardGrossrate;
	//单车上牌毛利
	private String avgOnCardGross;
	
	//车贷页新增毛利率：
	private String carloanGrossrate;
	
	//新保页新增毛利率：
	private String xinInsuranceGrossrate; 
	
	//延保页新增毛利率：
	private String lastInsuranceGrossrate;
	
	
	

	public String getOnCardSaleNum() {
		return onCardSaleNum;
	}

	public void setOnCardSaleNum(String onCardSaleNum) {
		this.onCardSaleNum = onCardSaleNum;
	}

	public String getOnCardPermeaterate() {
		return onCardPermeaterate;
	}

	public void setOnCardPermeaterate(String onCardPermeaterate) {
		this.onCardPermeaterate = onCardPermeaterate;
	}

	public String getOnCardTotalProfit() {
		return onCardTotalProfit;
	}

	public void setOnCardTotalProfit(String onCardTotalProfit) {
		this.onCardTotalProfit = onCardTotalProfit;
	}

	public String getOnCardGrossrate() {
		return onCardGrossrate;
	}

	public void setOnCardGrossrate(String onCardGrossrate) {
		this.onCardGrossrate = onCardGrossrate;
	}

	public String getAvgOnCardGross() {
		return avgOnCardGross;
	}

	public void setAvgOnCardGross(String avgOnCardGross) {
		this.avgOnCardGross = avgOnCardGross;
	}

	public String getCarloanGrossrate() {
		return carloanGrossrate;
	}

	public void setCarloanGrossrate(String carloanGrossrate) {
		this.carloanGrossrate = carloanGrossrate;
	}

	public String getXinInsuranceGrossrate() {
		return xinInsuranceGrossrate;
	}

	public void setXinInsuranceGrossrate(String xinInsuranceGrossrate) {
		this.xinInsuranceGrossrate = xinInsuranceGrossrate;
	}

	public String getLastInsuranceGrossrate() {
		return lastInsuranceGrossrate;
	}

	public void setLastInsuranceGrossrate(String lastInsuranceGrossrate) {
		this.lastInsuranceGrossrate = lastInsuranceGrossrate;
	}

	public IViewProfitReport(String salesconsultant, String models, String turnover, String bountiqueSaleNum,
			String bountiqueAddrate, String bountiqueGross, String bountiqueGrossrate, String avgbountiqueGross,
			String carloanSaleNum, String carloanpermeaterate, String carloanfees, String avgcarloanGross,
			String newInsuranceSaleNum, String newInsurancepermeaterate, String newInsuranceIncome,
			String avgnewInsuranceGross, String lastInsuranceSaleNum, String lastInsurancepermeaterate,
			String lastInsuranceIncome, String avglastInsuranceGross, String permuteSaleNum, String permuterate,
			String permuterebate, String avgpermuteGross) {
		super();
		this.salesconsultant = salesconsultant;
		this.models = models;
		Turnover = turnover;
		this.bountiqueSaleNum = bountiqueSaleNum;
		this.bountiqueAddrate = bountiqueAddrate;
		this.bountiqueGross = bountiqueGross;
		this.bountiqueGrossrate = bountiqueGrossrate;
		this.avgbountiqueGross = avgbountiqueGross;
		this.carloanSaleNum = carloanSaleNum;
		this.carloanpermeaterate = carloanpermeaterate;
		this.carloanfees = carloanfees;
		this.avgcarloanGross = avgcarloanGross;
		this.xinInsuranceSaleNum = newInsuranceSaleNum;
		this.xinInsurancepermeaterate = newInsurancepermeaterate;
		this.xinInsuranceIncome = newInsuranceIncome;
		this.avgxinInsuranceGross = avgnewInsuranceGross;
		this.lastInsuranceSaleNum = lastInsuranceSaleNum;
		this.lastInsurancepermeaterate = lastInsurancepermeaterate;
		this.lastInsuranceIncome = lastInsuranceIncome;
		this.avglastInsuranceGross = avglastInsuranceGross;
		this.permuteSaleNum = permuteSaleNum;
		this.permuterate = permuterate;
		this.permuterebate = permuterebate;
		this.avgpermuteGross = avgpermuteGross;
	}
	
	//上牌和精品公用一个构造，只是传的参数不同而已
	
	// 精品
	public IViewProfitReport(String salesconsultant, String models, String turnover, String bountiqueSaleNum,
			String bountiqueAddrate, String bountiqueGross, String bountiqueGrossrate, String avgbountiqueGross) {
		super();
		this.salesconsultant = salesconsultant;
		this.models = models;
		Turnover = turnover;
		this.bountiqueSaleNum = bountiqueSaleNum;
		this.bountiqueAddrate = bountiqueAddrate;
		this.bountiqueGross = bountiqueGross;
		this.bountiqueGrossrate = bountiqueGrossrate;
		this.avgbountiqueGross = avgbountiqueGross;
	}

	// 车贷
	public IViewProfitReport(String salesconsultant, String models, String turnover, String carloanSaleNum,
			String carloanpermeaterate, String carloanfees, String avgcarloanGross) {
		super();
		this.salesconsultant = salesconsultant;
		this.models = models;
		Turnover = turnover;
		this.carloanSaleNum = carloanSaleNum;
		this.carloanpermeaterate = carloanpermeaterate;
		this.carloanfees = carloanfees;
		this.avgcarloanGross = avgcarloanGross;
	}

	public void setSalesconsultant(String salesconsultant) {
		this.salesconsultant = salesconsultant;
	}

	public String getSalesconsultant() {
		return salesconsultant;
	}

	public String getModels() {
		return models;
	}

	public void setModels(String models) {
		this.models = models;
	}

	public String getTurnover() {
		return Turnover;
	}

	public void setTurnover(String turnover) {
		Turnover = turnover;
	}

	public String getBountiqueSaleNum() {
		return bountiqueSaleNum;
	}

	public void setBountiqueSaleNum(String bountiqueSaleNum) {
		this.bountiqueSaleNum = bountiqueSaleNum;
	}

	public String getBountiqueAddrate() {
		return bountiqueAddrate;
	}

	public void setBountiqueAddrate(String bountiqueAddrate) {
		this.bountiqueAddrate = bountiqueAddrate;
	}

	public String getBountiqueGross() {
		return bountiqueGross;
	}

	public void setBountiqueGross(String bountiqueGross) {
		this.bountiqueGross = bountiqueGross;
	}

	public String getBountiqueGrossrate() {
		return bountiqueGrossrate;
	}

	public void setBountiqueGrossrate(String bountiqueGrossrate) {
		this.bountiqueGrossrate = bountiqueGrossrate;
	}

	public String getAvgbountiqueGross() {
		return avgbountiqueGross;
	}

	public void setAvgbountiqueGross(String avgbountiqueGross) {
		this.avgbountiqueGross = avgbountiqueGross;
	}

	public String getCarloanSaleNum() {
		return carloanSaleNum;
	}

	public void setCarloanSaleNum(String carloanSaleNum) {
		this.carloanSaleNum = carloanSaleNum;
	}

	public String getCarloanpermeaterate() {
		return carloanpermeaterate;
	}

	public void setCarloanpermeaterate(String carloanpermeaterate) {
		this.carloanpermeaterate = carloanpermeaterate;
	}

	public String getCarloanfees() {
		return carloanfees;
	}

	public void setCarloanfees(String carloanfees) {
		this.carloanfees = carloanfees;
	}

	public String getAvgcarloanGross() {
		return avgcarloanGross;
	}

	public void setAvgcarloanGross(String avgcarloanGross) {
		this.avgcarloanGross = avgcarloanGross;
	}

	public String getXinInsuranceSaleNum() {
		return xinInsuranceSaleNum;
	}

	public void setXinInsuranceSaleNum(String newInsuranceSaleNum) {
		this.xinInsuranceSaleNum = newInsuranceSaleNum;
	}

	public String getXinInsurancepermeaterate() {
		return xinInsurancepermeaterate;
	}

	public void setXinInsurancepermeaterate(String newInsurancepermeaterate) {
		this.xinInsurancepermeaterate = newInsurancepermeaterate;
	}

	public String getXinInsuranceIncome() {
		return xinInsuranceIncome;
	}

	public void setXinInsuranceIncome(String newInsuranceIncome) {
		this.xinInsuranceIncome = newInsuranceIncome;
	}

	public String getAvgxinInsuranceGross() {
		return avgxinInsuranceGross;
	}

	public void setAvgxinInsuranceGross(String avgnewInsuranceGross) {
		this.avgxinInsuranceGross = avgnewInsuranceGross;
	}

	public String getLastInsuranceSaleNum() {
		return lastInsuranceSaleNum;
	}

	public void setLastInsuranceSaleNum(String lastInsuranceSaleNum) {
		this.lastInsuranceSaleNum = lastInsuranceSaleNum;
	}

	public String getLastInsurancepermeaterate() {
		return lastInsurancepermeaterate;
	}

	public void setLastInsurancepermeaterate(String lastInsurancepermeaterate) {
		this.lastInsurancepermeaterate = lastInsurancepermeaterate;
	}

	public String getLastInsuranceIncome() {
		return lastInsuranceIncome;
	}

	public void setLastInsuranceIncome(String lastInsuranceIncome) {
		this.lastInsuranceIncome = lastInsuranceIncome;
	}

	public String getAvglastInsuranceGross() {
		return avglastInsuranceGross;
	}

	public void setAvglastInsuranceGross(String avglastInsuranceGross) {
		this.avglastInsuranceGross = avglastInsuranceGross;
	}

	public String getPermuteSaleNum() {
		return permuteSaleNum;
	}

	public void setPermuteSaleNum(String permuteSaleNum) {
		this.permuteSaleNum = permuteSaleNum;
	}

	public String getPermuterate() {
		return permuterate;
	}

	public void setPermuterate(String permuterate) {
		this.permuterate = permuterate;
	}

	public String getPermuterebate() {
		return permuterebate;
	}

	public void setPermuterebate(String permuterebate) {
		this.permuterebate = permuterebate;
	}

	public String getAvgpermuteGross() {
		return avgpermuteGross;
	}

	public void setAvgpermuteGross(String avgpermuteGross) {
		this.avgpermuteGross = avgpermuteGross;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "IViewProfitReport [Turnover=" + Turnover + ", avgbountiqueGross=" + avgbountiqueGross
				+ ", avgcarloanGross=" + avgcarloanGross + ", avglastInsuranceGross=" + avglastInsuranceGross
				+ ", avgnewInsuranceGross=" + avgxinInsuranceGross + ", avgpermuteGross=" + avgpermuteGross
				+ ", bountiqueAddrate=" + bountiqueAddrate + ", bountiqueGross=" + bountiqueGross
				+ ", bountiqueGrossrate=" + bountiqueGrossrate + ", bountiqueSaleNum=" + bountiqueSaleNum
				+ ", carloanSaleNum=" + carloanSaleNum + ", carloanfees=" + carloanfees + ", carloanpermeaterate="
				+ carloanpermeaterate + ", lastInsuranceIncome=" + lastInsuranceIncome + ", lastInsuranceSaleNum="
				+ lastInsuranceSaleNum + ", lastInsurancepermeaterate=" + lastInsurancepermeaterate + ", models="
				+ models + ", newInsuranceIncome=" + xinInsuranceIncome + ", newInsuranceSaleNum=" + xinInsuranceSaleNum
				+ ", newInsurancepermeaterate=" + xinInsurancepermeaterate + ", permuteSaleNum=" + permuteSaleNum
				+ ", permuterate=" + permuterate + ", permuterebate=" + permuterebate + ", salesconsultant="
				+ salesconsultant + "]";
	}

}
