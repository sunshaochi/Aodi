package com.iris.cada.entity;

public class ChannelInfo {
	// 渠道效果
	private String name;
	// 目标
	private String goal;
	// 实际
	private String reality;
	// 达成进度
	private String completePro;

	private String dormantGuest;
	private String activeGuest;
	private String adcpotentialGuest;
	private String potentialGuest;

	public ChannelInfo() {
	}

	public ChannelInfo(String name, String goal, String reality, String completePro, String dormantGuest, String activeGuest, String adcpotentialGuest, String potentialGuest) {
		this.name = name;
		this.goal = goal;
		this.reality = reality;
		this.completePro = completePro;
		this.dormantGuest = dormantGuest;
		this.activeGuest = activeGuest;
		this.adcpotentialGuest = adcpotentialGuest;
		this.potentialGuest = potentialGuest;
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

	public String getDormantGuest() {
		return dormantGuest;
	}

	public void setDormantGuest(String dormantGuest) {
		this.dormantGuest = dormantGuest;
	}

	public String getActiveGuest() {
		return activeGuest;
	}

	public void setActiveGuest(String activeGuest) {
		this.activeGuest = activeGuest;
	}

	public String getAdcpotentialGuest() {
		return adcpotentialGuest;
	}

	public void setAdcpotentialGuest(String adcpotentialGuest) {
		this.adcpotentialGuest = adcpotentialGuest;
	}

	public String getPotentialGuest() {
		return potentialGuest;
	}

	public void setPotentialGuest(String potentialGuest) {
		this.potentialGuest = potentialGuest;
	}

	@Override
	public String toString() {
		return "ChannelInfo{" +
				"name='" + name + '\'' +
				", goal='" + goal + '\'' +
				", reality='" + reality + '\'' +
				", completePro='" + completePro + '\'' +
				", dormantGuest='" + dormantGuest + '\'' +
				", activeGuest='" + activeGuest + '\'' +
				", adcpotentialGuest='" + adcpotentialGuest + '\'' +
				", potentialGuest='" + potentialGuest + '\'' +
				'}';
	}
}
