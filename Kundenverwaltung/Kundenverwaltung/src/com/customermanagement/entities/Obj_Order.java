package com.customermanagement.entities;

import java.util.Date;

public class Obj_Order {				// Order Object one - Object for each Order

    private String orderNo;				// order Number
    private Date orderDate;				// order Date
    private Date payStart;				// Date which the Customer starts to pay the order
    private Date payEnd;				// Date which the payment ends
    private int rateCount;				// Number of Rates to pay
    private double firstRate;			// first Rate - is most not the same as the other rates
    private double rate;				// other rates
    private double orderSummary;		// Total Payment for the Order
    private String custNo;				// Customer Number - used as foreign Key
    private double alreadyPaid;			// Summary of all payed Rates
    private double stillToPay;			// Summary of all Rates which the Customer have to pay
    
     public Obj_Order(String orderNo, Date orderDate, Date payStart, Date payEnd, int rateCount, double firstRate,
			double rate, double orderSummary, String custNo) {
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
	
	public Date getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	public Date getPayStart() {
		return payStart;
	}
	
	public void setPayStart(Date payStart) {
		this.payStart = payStart;
	}
	
	public Date getPayEnd() {
		return payEnd;
	}
	
	public void setPayEnd(Date payEnd) {
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
	
	public String getCustNo() {
		return custNo;
	}
	
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public double getAlreadyPaid() {
		return alreadyPaid;
	}

	public void setAlreadyPaid(double alreadyPaid) {
		this.alreadyPaid = alreadyPaid;
	}

	public double getStillToPay() {
		return stillToPay;
	}

	public void setStillToPay(double stillToPay) {
		this.stillToPay = stillToPay;
	}
	
	
	
	
	
}
