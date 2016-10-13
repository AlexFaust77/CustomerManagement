package com.customermanagement.inputchecks;

import com.customermanagement.entities.Obj_Order;
import com.customermanagement.helpers.Calculator;
import com.customermanagement.main.Cust_Gui;
import com.customermanagement.messages.HintMessages;

import org.apache.log4j.Logger;

public class InputChecks {

	private boolean check_ok = false;
	private String modifiedString = "";
	private double modifiedDouble = 0.0;
	private int modifiedInteger = 0;
	private HintMessages message = new HintMessages();
	
	// check if input is Date and the correct Special Characters are use
	public void checkAllDates(Cust_Gui Obj_Cust_Gui,Logger logger, Obj_Order obj_Order) {   
	
		
		// Check all Input Dates from user - Order Date
		checkDates(Obj_Cust_Gui.getOrderDate(),logger);
		
		// if check Dates ok set into object
		if(check_ok) {
		   obj_Order.setOrderDate(modifiedString);	
		   System.out.println("Date Values : " + modifiedString);
		} else {
		   message.inputFailMessage("Datumseingaben", "Fehlerhafte Eingabe", "Das Bestelldatum entspricht nicht dem geforderten Format (dd.mm.yyyy)");
		   check_ok = false;
		}
		
		// Check Input Dates from user - Start Payment Date
		checkDates(Obj_Cust_Gui.getPayStart(),logger);
		
		// if check Dates ok set into object
		if(check_ok) {
		   obj_Order.setPayStart(modifiedString);	
		   System.out.println("Date Values : " + modifiedString);
		} else {
		   message.inputFailMessage("Datumseingaben", "Fehlerhafte Eingabe", "Das Zahlstartdatum entspricht nicht dem geforderten Format (dd.mm.yyyy)");
		   check_ok = false;
		}
	
	}
	
	public void checkAllDouble(Cust_Gui Obj_Cust_Gui,Logger logger, Obj_Order obj_Order) {
		
		// Check all Input Values from user - First Rate
		checkDoubles(Obj_Cust_Gui.getFirstRate(),logger);
		
		// if check Value ok - set into object
		if(check_ok) {
		   obj_Order.setFirstRate(modifiedDouble);	
		   System.out.println("Double Values : " + modifiedDouble);
		} else {
		   message.inputFailMessage("Erste Rate", "Fehlerhafte Eingabe", "Die Erste Rate entspricht nicht dem geforderten Format (dd.dd)");
		   check_ok = false;
		}
		
		// Check all Input Values from user - Following Rates
		checkDoubles(Obj_Cust_Gui.getRate(),logger);
		
		// if check Value ok - set into object and calculate the Order Summary
		if(check_ok) {
		   obj_Order.setRate(modifiedDouble);
		   double summary = obj_Order.getFirstRate() + ((obj_Order.getRateCount() - 1 ) * obj_Order.getRate());
		   obj_Order.setOrderSummary(summary);
		   Obj_Cust_Gui.setOrderSummary(summary);
		   
		   System.out.println("Double Values : " + modifiedDouble);
		} else {
		   message.inputFailMessage("Folge Rate", "Fehlerhafte Eingabe", "Die Folge Rate entspricht nicht dem geforderten Format (dd.dd)");
		   check_ok = false;
		}

	}
	public void checkAllIntegers(Cust_Gui Obj_Cust_Gui,Logger logger, Obj_Order obj_Order, Calculator calculate) {
		// Check all Input Values from user - OrderSummary
		checkIntegers(Obj_Cust_Gui.getRateCount(),logger);
		
		// if check Value - if ok set into object and into GUI
		if(check_ok) {
		   obj_Order.setRateCount(modifiedInteger);	
		   String paymentEnddate = calculate.paymentEndDate(obj_Order.getPayStart(), modifiedInteger,logger);
		   obj_Order.setPayEnd(paymentEnddate);
		   Obj_Cust_Gui.setPayEnd(paymentEnddate);
		} else {
		   message.inputFailMessage("Ratenanzahl", "Fehlerhafte Eingabe", "Die Ratenanzahl entspricht nicht dem geforderten Format (dd)");
		   check_ok = false;
		}
		
	}
	
    // All Date inputs from users can be checked - before writing into Database
	private boolean checkDates(String orderDate,Logger logger) {
		
		// looking what kind of special characters are used
		// and replaced into the correct one for Database
		if (orderDate.matches("\\d{2}-\\d{2}-\\d{4}")) {
		    modifiedString = orderDate;
		    check_ok=true;
		} else {
		    logger.debug("Input is not matching : " + orderDate);
		    check_ok = false;
		}
		
		if (orderDate.matches("\\d{2}.\\d{2}.\\d{4}")) {
		    modifiedString = orderDate.replace(".", "-");
		    check_ok = true;
		}
		
		if (orderDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
		    modifiedString = orderDate.replace("/", "-");
		    check_ok = true;
		}
		

				
		// Date splitet and check the input values for all Parts of Date
		String[] dateSplit = modifiedString.split("-");
		int days=0;
		int months=0;
		int years=0;
		
		try { 
				 days = Integer.parseInt(dateSplit[0]);
				 months = Integer.parseInt(dateSplit[1]);
				 years = Integer.parseInt(dateSplit[2]);
		         logger.debug("Values for Parts of Date - Days : " + days + "\nMonths : " + months + "\nYear : " + years);		 
		} catch (NumberFormatException e) {
			logger.error("Parser Error String to int : " + e);
			check_ok = false;
		}
	
		if(dateSplit.length < 2 ) {
			logger.debug("Array Size Date : " + dateSplit.length);
			check_ok = false;
		} else { 
				if(days > 31 ) {
					logger.debug("Day value : " + days);
					check_ok = false;
				} 
		
				if(months > 12 ) {
					logger.debug("Month value : " + months);
					check_ok = false;
				}
		}
		
	return check_ok;	
	}
	
	private boolean checkDoubles(String orderValues, Logger logger) {
		
		// looking what kind of special characters are used
		// and replaced into the correct one for Database
		if (orderValues.matches("\\d+.\\d{2}")) {
			// parse string in Double if right format
			try { 
				modifiedDouble = Double.parseDouble(orderValues);
				check_ok = true;
			} catch (NumberFormatException e) {
				logger.error("Parser Error String to Double : " + e);
				check_ok = false;
			}
		    
		} else {
		    logger.debug("Input is not matching : " + orderValues);
		    check_ok = false;
		}
		
		if (orderValues.matches("\\d+,\\d{2}")) {
		    modifiedString = orderValues.replace(",", ".");
		    check_ok = true;
		}
		
		if (orderValues.matches("\\d+;\\d{2}")) {
		    modifiedString = orderValues.replace(";", ".");
		    check_ok = true;
		}
		

		
	return check_ok;	
	}
	
	private boolean checkIntegers(String rateCount, Logger logger){
		
		// looking what kind of special characters are used
		// and replaced into the correct one for Database
		if (rateCount.matches("\\d+")) {
			
			// parse string in Double if right format
			try { 
				modifiedInteger = Integer.parseInt(rateCount);
				check_ok=true;
			} catch (NumberFormatException e) {
				logger.error("Parser Error String to Integer : " + e);
				check_ok = false;
			}
		} else {
		    logger.debug("Input is not matching : " + rateCount);
		    check_ok = false;
		}
	
	return check_ok;	
	}
	
	public boolean getCheck_ok() {
		return check_ok;
	}
	
	

	
	
}
