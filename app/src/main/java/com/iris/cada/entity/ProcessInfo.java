package com.iris.cada.entity;

public class ProcessInfo {
	/**
	 * name : 成交率
	 * goal : 0
	 * reality : 0
	 * completePro : 0
	 * fixNum : 0
	 * quoNum : 0
	 * fixR : 0
	 * addGuestModel : 0
	 * quoR : 0
	 * firstIntoShopTDModel : 0
	 * nextIntoShopModel : 0
	 * firstIntoShopTDR : 0
	 * nextIntoShopTDR : 0
	 */

	private String name;
	private String goal;
	private String reality;
	private String completePro;
	private String fixNum;
	private String quoNum;
	private String fixR;
	private String addGuestModel;
	private String quoR;
	private String firstIntoShopTDModel;
	private String nextIntoShopModel;
	private String firstIntoShopTDR;
	private String nextIntoShopTDR;


	public ProcessInfo() {
	}

	public ProcessInfo(String name, String goal, String reality, String completePro, String fixNum, String quoNum, String fixR, String addGuestModel, String quoR, String firstIntoShopTDModel, String nextIntoShopModel, String firstIntoShopTDR, String nextIntoShopTDR) {
		this.name = name;
		this.goal = goal;
		this.reality = reality;
		this.completePro = completePro;
		this.fixNum = fixNum;
		this.quoNum = quoNum;
		this.fixR = fixR;
		this.addGuestModel = addGuestModel;
		this.quoR = quoR;
		this.firstIntoShopTDModel = firstIntoShopTDModel;
		this.nextIntoShopModel = nextIntoShopModel;
		this.firstIntoShopTDR = firstIntoShopTDR;
		this.nextIntoShopTDR = nextIntoShopTDR;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getReality() {
		return reality;
	}

	public void setReality(String reality) {
		this.reality = reality;
	}

	public String getCompletePro() {
		return completePro;
	}

	public void setCompletePro(String completePro) {
		this.completePro = completePro;
	}

	public String getFixNum() {
		return fixNum;
	}

	public void setFixNum(String fixNum) {
		this.fixNum = fixNum;
	}

	public String getQuoNum() {
		return quoNum;
	}

	public void setQuoNum(String quoNum) {
		this.quoNum = quoNum;
	}

	public String getFixR() {
		return fixR;
	}

	public void setFixR(String fixR) {
		this.fixR = fixR;
	}

	public String getAddGuestModel() {
		return addGuestModel;
	}

	public void setAddGuestModel(String addGuestModel) {
		this.addGuestModel = addGuestModel;
	}

	public String getQuoR() {
		return quoR;
	}

	public void setQuoR(String quoR) {
		this.quoR = quoR;
	}

	public String getFirstIntoShopTDModel() {
		return firstIntoShopTDModel;
	}

	public void setFirstIntoShopTDModel(String firstIntoShopTDModel) {
		this.firstIntoShopTDModel = firstIntoShopTDModel;
	}

	public String getNextIntoShopModel() {
		return nextIntoShopModel;
	}

	public void setNextIntoShopModel(String nextIntoShopModel) {
		this.nextIntoShopModel = nextIntoShopModel;
	}

	public String getFirstIntoShopTDR() {
		return firstIntoShopTDR;
	}

	public void setFirstIntoShopTDR(String firstIntoShopTDR) {
		this.firstIntoShopTDR = firstIntoShopTDR;
	}

	public String getNextIntoShopTDR() {
		return nextIntoShopTDR;
	}

	public void setNextIntoShopTDR(String nextIntoShopTDR) {
		this.nextIntoShopTDR = nextIntoShopTDR;
	}

	@Override
	public String toString() {
		return "ProcessInfo{" +
				"name='" + name + '\'' +
				", goal='" + goal + '\'' +
				", reality='" + reality + '\'' +
				", completePro='" + completePro + '\'' +
				", fixNum='" + fixNum + '\'' +
				", quoNum='" + quoNum + '\'' +
				", fixR='" + fixR + '\'' +
				", addGuestModel='" + addGuestModel + '\'' +
				", quoR='" + quoR + '\'' +
				", firstIntoShopTDModel='" + firstIntoShopTDModel + '\'' +
				", nextIntoShopModel='" + nextIntoShopModel + '\'' +
				", firstIntoShopTDR='" + firstIntoShopTDR + '\'' +
				", nextIntoShopTDR='" + nextIntoShopTDR + '\'' +
				'}';
	}
}
