package com.iris.cada.entity;

import java.io.Serializable;

public class IViewSCBean implements Serializable{	
	String scName;
	String scCode;
	public String getScName() {
		return scName;
	}
	public void setScName(String scName) {
		this.scName = scName;
	}
	public String getScCode() {
		return scCode;
	}
	public void setScCode(String scCode) {
		this.scCode = scCode;
	}
	@Override
	public String toString() {
		return String.format("IViewSCBean [scName=%s, scCode=%s]", scName,
				scCode);
	}
	
}
