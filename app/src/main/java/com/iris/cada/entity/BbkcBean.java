package com.iris.cada.entity;

import java.io.Serializable;

public class BbkcBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String carType;//总计
	private String ztsl;//在途数量
	private String zksl;//在库数量
	private String kcsl;//库存数量
	private String pjkcts;//平均库存天数
	
	
	
	public BbkcBean() {
		super();
	}
	public BbkcBean(String carType, String ztsl, String zksl, String kcsl, String pjkcts) {
		super();
		this.carType = carType;
		this.ztsl = ztsl;
		this.zksl = zksl;
		this.kcsl = kcsl;
		this.pjkcts = pjkcts;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getZtsl() {
		return ztsl;
	}
	public void setZtsl(String ztsl) {
		this.ztsl = ztsl;
	}
	public String getZksl() {
		return zksl;
	}
	public void setZksl(String zksl) {
		this.zksl = zksl;
	}
	public String getKcsl() {
		return kcsl;
	}
	public void setKcsl(String kcsl) {
		this.kcsl = kcsl;
	}
	public String getPjkcts() {
		return pjkcts;
	}
	public void setPjkcts(String pjkcts) {
		this.pjkcts = pjkcts;
	}
	@Override
	public String toString() {
		return "BbkcBean [carType=" + carType + ", ztsl=" + ztsl + ", zksl=" + zksl + ", kcsl=" + kcsl + ", pjkcts="
				+ pjkcts + "]";
	}
	
	
	

}
