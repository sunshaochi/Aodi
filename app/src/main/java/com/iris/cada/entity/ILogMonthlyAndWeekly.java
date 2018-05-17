package com.iris.cada.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ILogMonthlyAndWeekly implements Serializable{

//	/**
//	 * 散客线索
//	 * 呼入线索
//	 * 网络线索
//	 * 主动集客线索
//	 * 推荐线索
//	 * 再购线索
//	 * 活跃线索
//	 * 休眠线索
//	 */
	private static final long serialVersionUID = 1L;
//	private String FITClue;
//	private String CallClue;
//	private String NetworkClue;
//	private String SetOffClue;
//	private String RecommendationClue;
//	private String RepurchaseClue;
//	private String ActiveClue;
//	private String DormancyClue;
//	/**
//	 * 线索数
//	 * 进店数
//	 * 报价数
//	 * 订单数
//	 * 交车数
//	 * 试乘试驾数
//	 */
//	private String ClueNum;
//	private String IndoorNum;
//	private String QuoteNum;
//	private String OrderNum;
//	private String RetailNum;
//	private String TestDriveNum;
//	/**
//	 * 散客留档率
//	 * 呼入线索进店率
//	 * 网络线索进店率
//	 * 主动集客线索进店率
//	 * 推荐线索进店率
//	 * 再购线索进店率
//	 * 活跃线索进店率
//	 * 休眠线索进店率
//	 */
//	private String FITIndoorRate;
//	private String CallIndoorRate;
//	private String NetworkIndoorRate;
//	private String SetOffIndoorRate;
//	private String RecommendationIndoorRate;
//	private String RepurchaseIndoorRate;
//	private String ActiveIndoorRate;
//	private String DormancyIndoorRate;
//	/**
//	 * 线索进店率
//	 * 进店报价率
//	 * 报价订单率
//	 * 订单交车率
//	 * 线索订单率
//	 * 线索试乘试驾率
//	 */
//	private String ClueInDoorRate;
//	private String IndoorQuoteRate;
//	private String QuoteOrderRate;
//	private String OrderRetailRate;
//	private String ClueOrderRate;
//	private String ClueTestDriveRate;
//	
//	
//	//实际
//	private String FITClueA;
//	private String CallClueA;
//	private String NetworkClueA;
//	private String SetOffClueA;
//	private String RecommendationClueA;
//	private String RepurchaseClueA;
//	private String ActiveClueA;
//	private String DormancyClueA;
//
//	private String ClueNumA;
//	private String IndoorNumA;
//	private String QuoteNumA;
//	private String OrderNumA;
//	private String RetailNumA;
//	private String TestDriveNumA;
//
//	private String FITIndoorRateA;
//	private String CallIndoorRateA;
//	private String NetworkIndoorRateA;
//	private String SetOffIndoorRateA;
//	private String RecommendationIndoorRateA;
//	private String RepurchaseIndoorRateA;
//	private String ActiveIndoorRateA;
//	private String DormancyIndoorRateA;
//
//	private String ClueInDoorRateA;
//	private String IndoorQuoteRateA;
//	private String QuoteOrderRateA;
//	private String OrderRetailRateA;
//	private String ClueOrderRateA;
//	private String ClueTestDriveRateA;
	
	
	
	
	
	/**
	 * KPI目标
	 * KPI实际
	 */
	private ArrayList<String> KPITarget;
	private ArrayList<String> KPIActual;
	/**
	 * 店名
	 * 接待
	 * 主动集客
	 * 呼入
	 * 网络
	 * 推荐
	 * 再购
	 * 活跃休眠
	 */
	private String StoreName;
	private String DailyUse;
	private String FITClue;
	private String CallClue;
	private String NetworkClue;
	private String RecommendationClue;
	private String RepurchaseClue;
	private String ActiveClue;
	private String RetainCustome;

	public ILogMonthlyAndWeekly() {
		super();
	}
	

	public ILogMonthlyAndWeekly(ArrayList<String> kPITarget,
			ArrayList<String> kPIActual) {
		super();
		KPITarget = kPITarget;
		KPIActual = kPIActual;
	}
	


	public ILogMonthlyAndWeekly(String storeName, String dailyUse,
			String fITClue, String callClue, String networkClue,
			String recommendationClue, String repurchaseClue,
			String activeClue, String retainCustome) {
		super();
		StoreName = storeName;
		DailyUse = dailyUse;
		FITClue = fITClue;
		CallClue = callClue;
		NetworkClue = networkClue;
		RecommendationClue = recommendationClue;
		RepurchaseClue = repurchaseClue;
		ActiveClue = activeClue;
		RetainCustome = retainCustome;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public ArrayList<String> getKPITarget() {
		return KPITarget;
	}

	public void setKPITarget(ArrayList<String> kPITarget) {
		KPITarget = kPITarget;
	}

	public ArrayList<String> getKPIActual() {
		return KPIActual;
	}

	public void setKPIActual(ArrayList<String> kPIActual) {
		KPIActual = kPIActual;
	}

	
	public String getStoreName() {
		return StoreName;
	}


	public void setStoreName(String storeName) {
		StoreName = storeName;
	}


	public String getDailyUse() {
		return DailyUse;
	}


	public void setDailyUse(String dailyUse) {
		DailyUse = dailyUse;
	}


	public String getFITClue() {
		return FITClue;
	}


	public void setFITClue(String fITClue) {
		FITClue = fITClue;
	}


	public String getCallClue() {
		return CallClue;
	}


	public void setCallClue(String callClue) {
		CallClue = callClue;
	}


	public String getNetworkClue() {
		return NetworkClue;
	}


	public void setNetworkClue(String networkClue) {
		NetworkClue = networkClue;
	}


	public String getRecommendationClue() {
		return RecommendationClue;
	}


	public void setRecommendationClue(String recommendationClue) {
		RecommendationClue = recommendationClue;
	}


	public String getRepurchaseClue() {
		return RepurchaseClue;
	}


	public void setRepurchaseClue(String repurchaseClue) {
		RepurchaseClue = repurchaseClue;
	}


	public String getActiveClue() {
		return ActiveClue;
	}


	public void setActiveClue(String activeClue) {
		ActiveClue = activeClue;
	}


	public String getRetainCustome() {
		return RetainCustome;
	}


	public void setRetainCustome(String retainCustome) {
		RetainCustome = retainCustome;
	}


	@Override
	public String toString() {
		return "ILogMonthlyAndWeekly [KPITarget=" + KPITarget + ", KPIActual="
				+ KPIActual + ", StoreName=" + StoreName + ", DailyUse="
				+ DailyUse + ", FITClue=" + FITClue + ", CallClue=" + CallClue
				+ ", NetworkClue=" + NetworkClue + ", RecommendationClue="
				+ RecommendationClue + ", RepurchaseClue=" + RepurchaseClue
				+ ", ActiveClue=" + ActiveClue + ", RetainCustome="
				+ RetainCustome + "]";
	}
}
