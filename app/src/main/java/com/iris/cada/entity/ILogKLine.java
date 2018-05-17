package com.iris.cada.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ILogKLine implements Serializable{

	/**
	 * 进店
	 * 订单
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> InRooms;
	private ArrayList<String> Orders;
	public ILogKLine() {
		super();
	}
	public ILogKLine(ArrayList<String> inRooms, ArrayList<String> orders) {
		super();
		InRooms = inRooms;
		Orders = orders;
	}
	public ArrayList<String> getInRooms() {
		return InRooms;
	}
	public void setInRooms(ArrayList<String> inRooms) {
		InRooms = inRooms;
	}
	public ArrayList<String> getOrders() {
		return Orders;
	}
	public void setOrders(ArrayList<String> orders) {
		Orders = orders;
	}
	@Override
	public String toString() {
		return "ILogKLine [InRooms=" + InRooms + ", Orders=" + Orders + "]";
	}
	
}
