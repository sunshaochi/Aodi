package com.iris.cada.entity;

import java.io.Serializable;

public class ShzdBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String 平均单车收入;
	private String 合计收入;
	private String 车型;
	private String 团队平均;
	private String 合计毛利;
	
	private String 服务顾问;
	private String 维修台次;
	

	public String get平均单车收入() {
		return 平均单车收入;
	}

	public void set平均单车收入(String 平均单车收入) {
		this.平均单车收入 = 平均单车收入;
	}

	public String get合计收入() {
		return 合计收入;
	}

	public void set合计收入(String 合计收入) {
		this.合计收入 = 合计收入;
	}

	public String get车型() {
		return 车型;
	}

	public void set车型(String 车型) {
		this.车型 = 车型;
	}

	public String get团队平均() {
		return 团队平均;
	}

	public void set团队平均(String 团队平均) {
		this.团队平均 = 团队平均;
	}

	public String get合计毛利() {
		return 合计毛利;
	}

	public void set合计毛利(String 合计毛利) {
		this.合计毛利 = 合计毛利;
	}

	
	
	public String get服务顾问() {
		return 服务顾问;
	}

	public void set服务顾问(String 服务顾问) {
		this.服务顾问 = 服务顾问;
	}

	public String get维修台次() {
		return 维修台次;
	}

	public void set维修台次(String 维修台次) {
		this.维修台次 = 维修台次;
	}

	
	@Override
	public String toString() {
		return "ShzdBean [平均单车收入=" + 平均单车收入 + ", 合计收入=" + 合计收入 + ", 车型=" + 车型 + ", 团队平均=" + 团队平均 + ", 合计毛利=" + 合计毛利
				+ ", 服务顾问=" + 服务顾问 + ", 维修台次=" + 维修台次 + "]";
	}

	
	
	
	

}
