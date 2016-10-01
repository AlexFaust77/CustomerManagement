package com.customermanagement.entities;


public class Obj_Order {				// Order Object one - Object for each Order

    private String orderNo;
    private String orderDate;
    private String payStart;
    private String payEnd;
    private int rateCount;
    private double firstRate;
    private double rate;
    private double orderSummary;
    private double custNo;
    
     public Obj_Order(String orderNo, String orderDate, String payStart, String payEnd, int rateCount, double firstRate,
			double rate, double orderSummary, double custNo) {
		super();
		this.orderNo = orderNo;
		this.orderDate = orderDate;
		this.payStart = payStart;
		this.payEnd = payEnd;
		this.rateCount = rateCount;
		this.firstRate = firstRate;
		this.rate = rate;
		this.orderSummary = orderSummary;
		this.custNo = custNo;
	}

	public String getOrderNo() {
		return orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	public String getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
	public String getPayStart() {
		return payStart;
	}
	
	public void setPayStart(String payStart) {
		this.payStart = payStart;
	}
	
	public String getPayEnd() {
		return payEnd;
	}
	
	public void setPayEnd(String payEnd) {
		this.payEnd = payEnd;
	}
	
	public int getRateCount() {
		return rateCount;
	}
	
	public void setRateCount(int rateCount) {
		this.rateCount = rateCount;
	}
	
	public double getFirstRate() {
		return firstRate;
	}
	
	public void setFirstRate(double firstRate) {
		this.firstRate = firstRate;
	}
	
	public double getRate() {
		return rate;
	}
	
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	public double getOrderSummary() {
		return orderSummary;
	}
	
	public void setOrderSummary(double orderSummary) {
		this.orderSummary = orderSummary;
	}
	
	public double getCustNo() {
		return custNo;
	}
	
	public void setCustNo(double custNo) {
		this.custNo = custNo;
	}
	
}
