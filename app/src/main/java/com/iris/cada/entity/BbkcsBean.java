package com.iris.cada.entity;

public class BbkcsBean {
	
	
	private String carType;//总计
	private String coty030;//在途数量
	private String coty3160;//在库数量
	private String coty6190;//库存数量
	private String coty91180;//平均库存天数
	private String coty181;//平均库存天数
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCoty030() {
		return coty030;
	}
	public void setCoty030(String coty030) {
		this.coty030 = coty030;
	}
	public String getCoty3160() {
		return coty3160;
	}
	public void setCoty3160(String coty3160) {
		this.coty3160 = coty3160;
	}
	public String getCoty6190() {
		return coty6190;
	}
	public void setCoty6190(String coty6190) {
		this.coty6190 = coty6190;
	}
	public String getCoty91180() {
		return coty91180;
	}
	public void setCoty91180(String coty91180) {
		this.coty91180 = coty91180;
	}
	public String getCoty181() {
		return coty181;
	}
	public void setCoty181(String coty181) {
		this.coty181 = coty181;
	}
	@Override
	public String toString() {
		return "BbkcsBean [carType=" + carType + ", coty030=" + coty030 + ", coty3160=" + coty3160 + ", coty6190="
				+ coty6190 + ", coty91180=" + coty91180 + ", coty181=" + coty181 + "]";
	}
	
	
	
    
	
}
