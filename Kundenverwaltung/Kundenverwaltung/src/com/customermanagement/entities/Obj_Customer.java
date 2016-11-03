package com.customermanagement.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@Entity															// Hibernate Annotations for transfer in Database with Hibernate
@Table(name = "Customer")
public class Obj_Customer {          							// Customer Object Stores Customer Information - Getter and Setter

	    @Id @GeneratedValue
	    @Column(name="id")
		private int id;
	    
	    @Column(name="customerNo")
	    private String custNo;
	    
	    @Column(name="lastname")
	    private String lastname;
	    
	    @Column(name="name")
	    private String firstname;
	    
	    @Column(name="street")
	    private String street;
	    
	    @Column(name="houseNo")
	    private int houseNo;
	    
	    @Column(name="postcode")
	    private int postcode;
	    
	    @Column(name="residenz")
	    private String residenz;
	    
	    @Transient
	    private int orderCount;
	    
	    @Transient
	    private double custTotal;
	    
	    @Transient
	    private double custBalance;
	    
	    @Transient
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
