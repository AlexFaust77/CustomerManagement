package com.customermanagement.inputchecks;

import com.customermanagement.entities.Obj_Order;
import com.customermanagement.helpers.Calculator;
import com.customermanagement.main.Cust_Gui;
import com.customermanagement.main.OrderGui;
import com.customermanagement.messages.HintMessages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

public class InputChecks {

	private boolean check_ok = false;
	private String modifiedString = "";
	private double modifiedDouble = 0.0;
	private int modifiedInteger = 0;
	private HintMessages message = new HintMessages();
	Calculator calculate = new Calculator();	
	
	// check if input is Date and the correct Special Characters are use
	public void checkAllDates(OrderGui orderGui,Logger logger, Obj_Order objOrder) {   
	
		
		// Check all Input Dates from user - Order Date and Start Payment Date
		checkDates(orderGui.getOrderDate(),logger);
		
		// if check Dates ok set into object - experimental design
		if(check_ok) {
		   	
		   Date newOrderDate;
		   try {
			   newOrderDate = new SimpleDateFormat("dd-MM-yyyy").parse(modifiedString);				
	
			   objOrder.setOrderDate(newOrderDate);	// => Datentyp Date muss übertragen werden nicht String
		        
			   logger.debug("OrderDate Ok " + newOrderDate );
		   
		   } catch (ParseException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
		   		   
    	} else {
		   message.inputFailMessage("Datumseingaben", "Fehlerhafte Eingabe", "Das Bestelldatum entspricht nicht dem geforderten Format (dd.mm.yyyy)");
		   check_ok = false;
      	
    	}
    	
		
	     checkDates(orderGui.getPayStart(),logger);
		
		// if check Dates ok set into object
		if(check_ok) {
		   Date newPayStartDate;
		 
		 	try {
		 		newPayStartDate = new SimpleDateFormat("dd-MM-yyyy").parse(modifiedString);
		 		
		 		Date paymentEnds = calculate.paymentEndDate(newPayStartDate,objOrder.getRateCount(),logger);
		        
		 		objOrder.setPayEnd(paymentEnds);
		 		objOrder.setPayStart(newPayStartDate);
		 		logger.debug("Start Payment Date " + newPayStartDate + "\nCaculated End Date" + paymentEnds );	
		 
		 	} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 	}	
			 
		} else {
		   message.inputFailMessage("Datumseingaben", "Fehlerhafte Eingabe", "Das Zahlstartdatum entspricht nicht dem geforderten Format (dd.mm.yyyy)");
		   check_ok = false;
		}
		
		
		
	
	}
	
	public void checkAllDouble(OrderGui orderGui,Logger logger, Obj_Order objOrder) {
		
		// Check all Input Values from user - First Rate and Rate - If ok set into Object
		checkDoubles(orderGui.getFirstRate(),logger);
		
		if(check_ok) {
		   objOrder.setFirstRate(modifiedDouble);	
		   logger.debug("inputCheck - Double First Rate " + modifiedDouble );	
		} else {
		   message.inputFailMessage("Erste Rate", "Fehlerhafte Eingabe", "Die Erste Rate entspricht nicht dem geforderten Format (dd.dd)");
		   check_ok = false;
		}
		
		checkDoubles(orderGui.getRate(),logger);
		
		if(check_ok) {
		   objOrder.setRate(modifiedDouble);
		   double summary = objOrder.getFirstRate() + ((objOrder.getRateCount() - 1 ) * objOrder.getRate());
		   objOrder.setOrderSummary(summary);
		   orderGui.setOrderSummary(summary);
		   
		   logger.debug("inputCheck - Double Rate " + modifiedDouble );	
		} else {
		   message.inputFailMessage("Folge Rate", "Fehlerhafte Eingabe", "Die Folge Rate entspricht nicht dem geforderten Format (dd.dd)");
		   check_ok = false;
		}

	}
	public void checkAllIntegers(OrderGui orderGui,Logger logger, Obj_Order objOrder, Calculator calculate) {
		// Check all Input Values from user - RateCount
		checkIntegers(orderGui.getRateCount(),logger);
		
		if(check_ok) {
		   objOrder.setRateCount(modifiedInteger);	
		   Date paymentEnddate = calculate.paymentEndDate(objOrder.getPayStart(), modifiedInteger,logger);
		   objOrder.setPayEnd(paymentEnddate);
		   logger.debug("inputCheck - Integer Rate Count " + modifiedInteger );
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
		    check_ok = true;
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
			logger.error("com.customermanagement.inputchecks - CheckDates => Input: " + orderDate + "\n" + e);
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
