package com.customermanagement.entities;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class Obj_Customer {          							// Customer Object Stores Customer Information - Getter and Setter

	    private String custNo;
	    private String lastname;
	    private String firstname;
	    private String street;
	    private int houseNo;
	    private int postcode;
	    private String residenz;
	    private int orderCount;
	    private double custTotal;
	    private double custBalance;
	    private ObservableList<String> orderlist = FXCollections.observableArrayList();
	    
	    
		public String getCustNo() {
			return custNo;
		}
		
		public void setCustNo(String custNo) {
			this.custNo = custNo;
		}
		
		public String getLastname() {
			return lastname;
		}
		
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
		
		public String getFirstname() {
			return firstname;
		}
		
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		
		public String getStreet() {
			return street;
		}
		
		public void setStreet(String street) {
			this.street = street;
		}
		
		public int getHouseNo() {
			return houseNo;
		}
		
		public void setHouseNo(int houseNo) {
			this.houseNo = houseNo;
		}
		
		public int getPostcode() {
			return postcode;
		}
		
		public void setPostcode(int postcode) {
			this.postcode = postcode;
		}
		
		public String getResidenz() {
			return residenz;
		}
		
		public void setResidenz(String residenz) {
			this.residenz = residenz;
		}
		public int getOrderCount() {
			return orderCount;
		}
		
		public void setOrderCount(int orderCount) {
			this.orderCount = orderCount;
		}
		
		public double getCustTotal() {
			return custTotal;
		}
		
		public void setCustTotal(double custTotal) {
			this.custTotal = custTotal;
		}
		
		public double getCustBalance() {
			return custBalance;
		}
		
		public void setCustBalance(double custBalance) {
			this.custBalance = custBalance;
		}
		
		public ObservableList<String> getOrderlist() {
			return orderlist;
		}
		
		public void setOrderlist(ObservableList<String> bestellItems) {
			this.orderlist = bestellItems;
		}
	    
}
