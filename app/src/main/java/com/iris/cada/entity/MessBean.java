package com.iris.cada.entity;

public class MessBean {
	
	private String messageText;
	private String sendDateTime;
	private String dealerCode;
	private String id;
	private String state;
	private String type;
	private String listType;
	
	
	
	
	public MessBean(String messageText, String sendDateTime, String dealerCode, String id, String state, String type,
			String listType) {
		super();
		this.messageText = messageText;
		this.sendDateTime = sendDateTime;
		this.dealerCode = dealerCode;
		this.id = id;
		this.state = state;
		this.type = type;
		this.listType = listType;
	}
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	public String getSendDateTime() {
		return sendDateTime;
	}
	public void setSendDateTime(String sendDateTime) {
		this.sendDateTime = sendDateTime;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	

}
