package com.iris.cada.entity;

public class IViewInputHistory {
	/**
	 * 日期:2016年9月9日
	 */
	String date;
	/**
	 * 0表示未导入,1表示导入
	 */
	String inputReceptionState;
	/**
	 * 0表示未导入,1表示导入
	 */
	String inputProfitState;
	
	String inputRepertoryState;//导入状态
	
	
	public IViewInputHistory() {
		this.inputReceptionState = "0";
		this.inputProfitState = "0";
	}
	
	

	

	public String getInputRepertoryState() {
		return inputRepertoryState;
	}





	public void setInputRepertoryState(String inputRepertoryState) {
		this.inputRepertoryState = inputRepertoryState;
	}





	public String getInputReceptionState() {
		return inputReceptionState;
	}

	public void setInputReceptionState(String inputReceptionState) {
		this.inputReceptionState = inputReceptionState;
	}

	public String getInputProfitState() {
		return inputProfitState;
	}

	public void setInputProfitState(String inputProfitState) {
		this.inputProfitState = inputProfitState;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	
	@Override
	public String toString() {
		return "IViewInputHistory [date=" + date + ", inputReceptionState=" + inputReceptionState
				+ ", inputProfitState=" + inputProfitState + ", inputRepertoryState=" + inputRepertoryState + "]";
	}

}
