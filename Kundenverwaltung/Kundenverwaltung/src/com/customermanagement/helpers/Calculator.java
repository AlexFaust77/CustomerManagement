package com.customermanagement.helpers;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.customermanagement.database.SQL_Statements;
import com.customermanagement.entities.Obj_Order;
import com.customermanagement.main.Cust_Gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Calculator {

	    private ArrayList<String> lst_month = new ArrayList<String>();				 // vorher monat
	    private ArrayList<Obj_Order> obj_orderlist = new ArrayList<Obj_Order>();	 // obj Bestellliste
	    private ArrayList<Double> lst_rates = new ArrayList<Double>();               // li_ratesPerOrder
	    private Double[] ar_monthly_rates  = new Double[200];						 // monthlyRate
	    private Double[] ar_still_payed = new Double[200];							 // stillPayed
	    private ArrayList<String> str_monthly_rate = new ArrayList<String>();
	   	    
	    private ArrayList<String> monate = new ArrayList<String>();
	    private ArrayList<String> monatsliste = new ArrayList<String>();
	    private ArrayList<String> monatlicheRate = new ArrayList<String>();
	    
	    double completeToPay = 0.0;
	    double allPaid = 0.0;
 
	    int aktTag;
	    int aktMonat;
	    int aktJahr;
	    
	    // fill Month list from now to x rates
	    public void fill_month_lst(Cust_Gui Obj_Cust_Gui,						
	    						   SQL_Statements dataBase_Request,Logger logger ) {
	        
	       
	       Date date = new Date();							
	       int current_month;		
	       int current_year;		
	       
	       // Dateformat for each part of Date
	       DateFormat yearformat = new SimpleDateFormat("yyyy"); 			           
	       DateFormat monthformat = new SimpleDateFormat("MM"); 
	       
	       // parse int into Dateformat
	       current_month = Integer.parseInt(monthformat.format(date));		       
	       current_year = Integer.parseInt(yearformat.format(date));
	       
	       // fill lists for 48 rates
	       for(int month_counter = 0; month_counter < 48;month_counter++) {
	             
	    	   	lst_month.add(current_month + "/" + current_year);
	    	   	logger.debug("Current Month : " + current_month + "\nCurrent Year : " + current_year);  
	         	           
	    	   // year +1 if 12 month reached	  
	           if(current_month == 12) {
	        	  current_month = 0;
	        	  current_year++;
	           } 
	           current_month++;
	       }
	         
	       
	    }
	    // Calculate the Payment Enddate Automaticly
	    public Date paymentEndDate(Date paymentStart,int rateCount, Logger logger) {
	        Date paymentEnds = null;
	                
	        LocalDate payStart = LocalDate.from(Instant.ofEpochMilli(paymentStart.getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
	        
	        int month = payStart.getMonthValue();
	        int year = payStart.getYear();
	        int day = payStart.getDayOfMonth();
	      	       
	        logger.debug("Date Values - Year : " + year + " Month : " + month + " Day : " + day + "\n");
	                   
	        String monthformatter ="0";
	        String dayformatter ="0";
	        
	        // if month 12 reached add one to Year and start month by one
	        for(int x = 0; x < rateCount;x++) {
	            if(month == 12) {    
	              month = 1;        
	              year++;           
	            } else {
	              month++;
	            }
	        }
	        if(month < 10) { 
	           monthformatter = monthformatter + month; 
	        } else {
	           monthformatter = ""+ month;
	        }
	        
	        if(day < 10) {
	           dayformatter = dayformatter + day; 
	        } else {
	            dayformatter = "" + day;
	        }
	        // Convert the paymentEnd String into Date
	        
	        try {
	        	SimpleDateFormat dateformatter = new SimpleDateFormat("dd-MM-yyyy");
	           	String stringInDate = dayformatter + "-" + monthformatter + "-" + year;
	           	logger.debug("Date String created :" + stringInDate + "\n");
	            paymentEnds = dateformatter.parse(stringInDate);
			} catch (ParseException e) {
				logger.debug("Date parse Error : " + paymentEnds.getTime() + "\n" + e);
				
			}
	       
	        logger.debug("paymentEnds parsed : " + paymentEnds.getTime() + "\n" );
	   	     	      	              
	    return paymentEnds;
	      
	    }

	    public void fillMonthlyList() {
	         for(int monats_zaehler = 0; monats_zaehler < monate.size();monats_zaehler++) {  
	             monatsliste.add(monate.get(monats_zaehler));
	         }
	    }
	 
	    
	    
	 public void getAll_Rates(Logger logger, ObservableList<Obj_Order> orders) {     
	    double allreadyPaid = 0;
	    double stillToPay = 0;
	    
	    LocalDate currentDate = LocalDate.now();
       
        for(int y = 0; y < ar_monthly_rates.length; y++) {                  							
   	        ar_monthly_rates[y] = 0.0;
   	    }
        
        logger.debug("Monthly Rate List " + ar_monthly_rates.length + "\nOrder Object List " + orders.size());
        
	    // calculate each order
	    for(int currentOrder = 0; currentOrder < orders.size() ; currentOrder++) {		//orders.size()
	    	// fill Array with values 0
	    	for(int y = 0; y < ar_monthly_rates.length; y++) {      // added 15.01.2017             							
	   	      
	   	    	ar_still_payed[y] = 0.0;
	   	    }
	    	
	    	logger.debug("Current order : " + orders.get(currentOrder).getOrderNo()
	    				 + "\nStartdate : " + orders.get(currentOrder).getPayStart() 
	    				 + "\nEnddate : " + orders.get(currentOrder).getPayEnd() + "\n");
	        
	    	int int_month_to_Pay = 0 ;
	        int int_rate_count = orders.get(currentOrder).getRateCount();	// number of rates
	        double first_rate = orders.get(currentOrder).getFirstRate();	// first rate
	        double follow_rate = orders.get(currentOrder).getRate();		// all other rates
	        
	        logger.debug("Rate counter: " + int_rate_count + "\nFirst Rate " + first_rate + " Rates: " + follow_rate);
	        
	        lst_rates.clear();
	        // fill rate list with values
	        for(int fillRates = 0; fillRates < int_rate_count; fillRates++) {  						    
	            if(fillRates == 0) {																	// first Rate only on the Beginning
	            	lst_rates.add(first_rate);
	            } else {
	            	lst_rates.add(follow_rate);
	            }   
	        }
	        
	        Date date = orders.get(currentOrder).getPayEnd();
	        	        
	        // End Date Year and Month        
	           
	        LocalDate payment_ends = LocalDate.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate()); 
	        // make Date Diff
	        Period date_diff = Period.between(currentDate,payment_ends);                                                                  
	        int diff_in_months = date_diff.getMonths();
	        int diff_in_years = date_diff.getYears();
	        int diff_in_days = date_diff.getDays();
            logger.debug("Date Diff results / Days : " + diff_in_days + "\nDiff in Months : " + diff_in_months + "\nDiff in Years : " + diff_in_years);
            
            // calculate Years to Month
            if(diff_in_years > 0 ) { 																									  
	        	int_month_to_Pay = diff_in_years * 12;
	        } 

            // diff in month
	        if(diff_in_months > 0) { 																									  
	        	int_month_to_Pay = int_month_to_Pay + diff_in_months;  
	        }
	        
	        // opened month =>  month + 1
	        if(diff_in_days > 0 ) { 																									  
	        	int_month_to_Pay = int_month_to_Pay + 1; 
	        }
	        
	        logger.debug("Result Complete Month to Pay : " + int_month_to_Pay + "\n");
	        
	        // Payment start is in future ? Not startet yet ?  	         
	        if(int_month_to_Pay > int_rate_count ) {	
	        	
	        	// diff to pay start
	            int count_diff = int_month_to_Pay - int_rate_count;																		  
	            logger.debug("Payment not startet : " + count_diff);
	            
	            for(int future_rate = 0; future_rate < int_month_to_Pay;future_rate++) {
	            	// fill Array beginning with 0 values	
	                if(future_rate < count_diff) {
	                	//ar_monthly_rates[future_rate] = 0.0;												// Bestehende Bestellung wird nicht berücksichtigt
	                	ar_monthly_rates[future_rate] = ar_monthly_rates[future_rate] + 0.0;
	                	// WErte werden komplett neu gefüllt bereits vorhandene Bestellungen werden überschrieben
	                } else {																						// extension needed for first Rate !!	
	                	// fill normal rates if start Date is reached										
	                	// ar_monthly_rates[future_rate] = lst_rates.get((future_rate - count_diff));
	                	ar_monthly_rates[future_rate] = ar_monthly_rates[future_rate] + lst_rates.get((future_rate - count_diff));
	                	stillToPay = stillToPay + ar_monthly_rates[future_rate];									// added 15.01.2017
	                }
	           }
	        
	           logger.debug("List Size and Rates : " + ar_monthly_rates.length + " Rates : " + int_rate_count); 
	      
	        } else { 	// Paymentstart reached		 																											  
	            
	        	// index adjustment																													
	            int int_backwards  = lst_rates.size() - 1;																				  
	            
	            System.out.println("Zu zahlende Monate : " + int_month_to_Pay);
	            
	            // add rates to Array beginning on the End of list
	            for(int add_rate = int_month_to_Pay; add_rate > 0; add_rate--) {														 
	                
	            	ar_monthly_rates[add_rate-1] =ar_monthly_rates[add_rate-1] + lst_rates.get(int_backwards);
	            	logger.debug("filled in monthly rates : " + ar_monthly_rates[add_rate-1] + " Ratelist : " + lst_rates.get(int_backwards) + "\n");
	                int_backwards--;
	                stillToPay = stillToPay + ar_monthly_rates[add_rate - 1];									   // added 15.01.2017
	            } 
	            
	            // add rates already paid
	            for(int add_still_payed = ((int_rate_count - int_month_to_Pay) - 1); add_still_payed >= 0 ;add_still_payed--) {		      
	             	ar_still_payed[add_still_payed] = ar_still_payed[add_still_payed] + lst_rates.get(add_still_payed);
	             	allreadyPaid = allreadyPaid + ar_still_payed[add_still_payed]; 
	            }
		       
	           orders.get(currentOrder).setAlreadyPaid(allreadyPaid);
		       orders.get(currentOrder).setStillToPay(stillToPay);
		       
		       
	           //logger.debug("Rates to Pay : " + ar_monthly_rates.length + "\nRates payed : " + ar_still_payed.length );
	           logger.debug("Summary to Pay : " + stillToPay + "\nRates payed : " + allreadyPaid + "\n" + "Rate:" + orders.get(currentOrder).getRate() + "\nMonth to Pay:" + int_month_to_Pay ); 
	           
	           
	           completeToPay = completeToPay + stillToPay;
	         
	           allPaid = allPaid + allreadyPaid;
	           
	           allreadyPaid = 0;
	           stillToPay = 0;
	           
	        } 
    	      
	     }
	 // convert doubles to string 
	 logger.debug("\nSummary to Pay : " + completeToPay + "\nRates payed : " + allPaid + "\n"); 
	 logger.debug("Monthly Rate list 5er steps :\n" + ar_monthly_rates[4] + "\n" + ar_monthly_rates[9] + "\n" + ar_monthly_rates[14] + "\n"+ ar_monthly_rates[19] + "\n"); 
	
	 //System.exit(0);
	 convert_double_to_string(ar_monthly_rates);
 
	 }  
	 
	 private void convert_double_to_string(Double[] monthly_rates) {
	     for(int rate_counter = 0; rate_counter < 72; rate_counter++) {
	         str_monthly_rate.add(monthly_rates[rate_counter].toString());
	         System.out.println("hinzugefügte Rate " + monthly_rates[rate_counter] + "\n");
	    
	     }
	 }
    
	    public ArrayList<String> getLstMonth(){ return lst_month; }
	    public void setLstMonth(ArrayList<String> lst_month) { this.lst_month = lst_month; }
	    public ArrayList<String> getMonthlyRate() { return str_monthly_rate; }
        public ArrayList<String> getMonatsliste() { return monatsliste;}
		public ArrayList<Obj_Order> getObj_orderlist() { return obj_orderlist; };
		

        
	
}
