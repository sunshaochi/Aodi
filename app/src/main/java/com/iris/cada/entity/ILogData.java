package com.iris.cada.entity;

import java.io.Serializable;

public class ILogData implements Serializable {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 车型
	 */
	private String Models;
	/**
	 * 线索
	 */
	private String Clue;
	/**
	 * 进店
	 */
	private String InRoom;
	/**
	 * 试驾
	 */
	private String TDrive;
	/**
	 * 报价
	 */
	private String Quote;
	/**
	 * 订单
	 */
	private String Order;
	/**
	 * 新增潜客线索数
	 */
	private String InRoomFir;
	/**
	 * 再次进店线索数
	 */
	private String InRoomSec;

	/**
	 * 交车
	 */
	private String Retail;

	/**
	 * 单车总价
	 */
	private String SingleCar;

	/**
	 * 精品
	 */
	private String Collectible;

	/**
	 * 金融
	 */
	private String Finance;

	/**
	 * 保险
	 */
	private String Insurance;
	/**
	 * 延保
	 */
	private String ExInsurance;
	/**
	 * 置换
	 */
	private String Replacement;
	/**
	 * 报价率
	 */
	private String QuoteRate;
	/**
	 * 订单率
	 */
	private String TransRate;
	/**
	 * 交车率
	 */
	private String DealRate;
	/**
	 * 成交率
	 */
	private String TransRateFir;

	/**
	 * 总计利润
	 */
	private String Profit;

	/**
	 * 精品渗透率
	 */
	private String CollectibleRate;

	/**
	 * 金融渗透率
	 */
	private String FinanceRate;
	/**
	 * 保险渗透率
	 */
	private String InsuranceRate;
	/**
	 * 延保渗透率
	 */
	private String ExInsuranceRate;

	/**
	 * 置换渗透率
	 */
	private String ReplacementRate;

	public ILogData() {
		super();
	}

	/**
	 * 第二页利润
	 */
	public ILogData(String models, String singleCar, String collectible, String finance, String insurance,
			String exInsurance, String replacement, String profit) {
		super();
		Models = models;
		SingleCar = singleCar;
		Collectible = collectible;
		Finance = finance;
		Insurance = insurance;
		ExInsurance = exInsurance;
		Replacement = replacement;
		Profit = profit;
	}

	/**
	 * 首页报表
	 */
	public ILogData(String models, String inRoom, String quote, String order, String retail, String collectible,
			String finance, String insurance, String exInsurance, String replacement, String quoteRate,
			String transRate, String dealRate, String inRoomFir, String inRoomSec) {
		super();
		this.Models = models;
		this.InRoom = inRoom;
		this.Quote = quote;
		this.Order = order;
		this.Retail = retail;
		this.Collectible = collectible;
		this.Finance = finance;
		this.Insurance = insurance;
		this.ExInsurance = exInsurance;
		this.Replacement = replacement;
		this.QuoteRate = quoteRate;
		this.TransRate = transRate;
		this.DealRate = dealRate;
		this.InRoomFir = inRoomFir;
		this.InRoomSec = inRoomSec;
	}

	public ILogData(String models, String inRoom, String tDrive, String quote, String order, String retail,
			String collectible, String finance, String insurance, String exInsurance, String replacement,
			String quoteRate, String transRate, String dealRate, String collectibleRate, String financeRate,
			String insuranceRate, String exInsuranceRate, String replacementRate) {
		super();
		Models = models;
		InRoom = inRoom;
		TDrive = tDrive;
		Quote = quote;
		Order = order;
		Retail = retail;
		Collectible = collectible;
		Finance = finance;
		Insurance = insurance;
		ExInsurance = exInsurance;
		Replacement = replacement;
		QuoteRate = quoteRate;
		TransRate = transRate;
		DealRate = dealRate;
		CollectibleRate = collectibleRate;
		FinanceRate = financeRate;
		InsuranceRate = insuranceRate;
		ExInsuranceRate = exInsuranceRate;
		ReplacementRate = replacementRate;
	}

	public String getClue() {
		return Clue;
	}

	public String getCollectible() {
		return Collectible;
	}

	public String getCollectibleRate() {
		return CollectibleRate;
	}

	public String getDealRate() {
		return DealRate;
	}

	public String getExInsurance() {
		return ExInsurance;
	}

	public String getExInsuranceRate() {
		return ExInsuranceRate;
	}

	public String getFinance() {
		return Finance;
	}

	public String getFinanceRate() {
		return FinanceRate;
	}

	public String getInRoom() {
		return InRoom;
	}

	public String getInRoomFir() {
		return InRoomFir;
	}

	public String getInRoomSec() {
		return InRoomSec;
	}

	public String getInsurance() {
		return Insurance;
	}

	public String getInsuranceRate() {
		return InsuranceRate;
	}

	public String getModels() {
		return Models;
	}

	public String getOrder() {
		return Order;
	}

	public String getProfit() {
		return Profit;
	}

	public String getQuote() {
		return Quote;
	}

	public String getQuoteRate() {
		return QuoteRate;
	}

	public String getReplacement() {
		return Replacement;
	}

	public String getReplacementRate() {
		return ReplacementRate;
	}

	public String getRetail() {
		return Retail;
	}

	public String getSingleCar() {
		return SingleCar;
	}

	public String getTDrive() {
		return TDrive;
	}

	public String getTransRate() {
		return TransRate;
	}

	public String getTransRateFir() {
		return TransRateFir;
	}

	public void setClue(String clue) {
		Clue = clue;
	}

	public void setCollectible(String collectible) {
		Collectible = collectible;
	}

	public void setCollectibleRate(String collectibleRate) {
		CollectibleRate = collectibleRate;
	}

	public void setDealRate(String dealRate) {
		DealRate = dealRate;
	}

	public void setExInsurance(String exInsurance) {
		ExInsurance = exInsurance;
	}

	public void setExInsuranceRate(String exInsuranceRate) {
		ExInsuranceRate = exInsuranceRate;
	}

	public void setFinance(String finance) {
		Finance = finance;
	}

	public void setFinanceRate(String financeRate) {
		FinanceRate = financeRate;
	}

	public void setInRoom(String inRoom) {
		InRoom = inRoom;
	}

	public void setInRoomFir(String inRoomFir) {
		InRoomFir = inRoomFir;
	}

	public void setInRoomSec(String inRoomSec) {
		InRoomSec = inRoomSec;
	}

	public void setInsurance(String insurance) {
		Insurance = insurance;
	}

	public void setInsuranceRate(String insuranceRate) {
		InsuranceRate = insuranceRate;
	}

	public void setModels(String models) {
		Models = models;
	}

	public void setOrder(String order) {
		Order = order;
	}

	public void setProfit(String profit) {
		Profit = profit;
	}

	public void setQuote(String quote) {
		Quote = quote;
	}

	public void setQuoteRate(String quoteRate) {
		QuoteRate = quoteRate;
	}

	public void setReplacement(String replacement) {
		Replacement = replacement;
	}

	public void setReplacementRate(String replacementRate) {
		ReplacementRate = replacementRate;
	}

	public void setRetail(String retail) {
		Retail = retail;
	}

	public void setSingleCar(String singleCar) {
		SingleCar = singleCar;
	}

	public void setTDrive(String tDrive) {
		TDrive = tDrive;
	}

	public void setTransRate(String transRate) {
		TransRate = transRate;
	}

	public void setTransRateFir(String transRateFir) {
		TransRateFir = transRateFir;
	}

	@Override
	public String toString() {
		return "ILogData [Models=" + Models + ", Clue=" + Clue + ", InRoom=" + InRoom + ", TDrive=" + TDrive
				+ ", Quote=" + Quote + ", Order=" + Order + ", Retail=" + Retail + ", SingleCar=" + SingleCar
				+ ", Collectible=" + Collectible + ", Finance=" + Finance + ", Insurance=" + Insurance
				+ ", ExInsurance=" + ExInsurance + ", Replacement=" + Replacement + ", QuoteRate=" + QuoteRate
				+ ", TransRate=" + TransRate + ", DealRate=" + DealRate + ", CollectibleRate=" + CollectibleRate
				+ ", FinanceRate=" + FinanceRate + ", InsuranceRate=" + InsuranceRate + ", ExInsuranceRate="
				+ ExInsuranceRate + ", ReplacementRate=" + ReplacementRate + "]";
	}
}
