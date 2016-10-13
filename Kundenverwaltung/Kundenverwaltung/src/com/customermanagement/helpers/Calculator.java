package com.customermanagement.helpers;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.customermanagement.database.SQL_Statements;
import com.customermanagement.entities.Obj_Order;
import com.customermanagement.main.Cust_Gui;

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
	    
	    
	  //  private ArrayList<String> rateHashMap = new ArrayList<String>();           // removed 01.10.2016
	  //  private ArrayList<String> monateHashMap = new ArrayList<String>();
	  //  private ArrayList<Integer> monatHashMap = new ArrayList<Integer>();
	  //  private ArrayList<Integer> jahrHashMap = new ArrayList<Integer>();
 
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
	            //  monatHashMap.add(current_month);  
	            //  jahrHashMap.add(current_year);
	           
	    	   // year +1 if 12 month reached	  
	           if(current_month == 12) {
	        	  current_month = 0;
	        	  current_year++;
	           } 
	           current_month++;
	       }
	         
	       
	    }
	    // Calculate the Payment Enddate Automaticly
	    public String paymentEndDate(String paymentStart,int rateCount, Logger logger) {
	        String paymentEnds="";
	        
	        // Split Date in 3 parts
	        String arr_Date[] = paymentStart.split("-"); 
	        
	        int day = Integer.parseInt(arr_Date[0]);     // Days
	        int month = Integer.parseInt(arr_Date[1]);   // Month
	        int year = Integer.parseInt(arr_Date[2]);    // Years
	        
	        String month_formatter ="0";
	        String day_formatter ="0";
	        
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
	           month_formatter = month_formatter + month; 
	        } else {
	           month_formatter = ""+ month;
	        }
	        
	        if(day < 10) {
	           day_formatter = day_formatter + day; 
	        } else {
	            day_formatter = "" + day;
	        }
	        // Build the paymentEnd String
	        paymentEnds = day_formatter + "-" + month_formatter + "-" + year; 
	        logger.debug("PaymentEndDate Calculator\nDay : " + day_formatter + "\nmonth : " + month_formatter + "\nYear : " + year );
	    return paymentEnds;   
	    }
	    
	    // Convert from Ordernumberlist to Order Objectlist
	    public ArrayList<Obj_Order> getOrder_Objects(Cust_Gui obj_Cust_Gui,Obj_Order obj_Order, SQL_Statements dataBase_Request,Logger logger) {			
	    	// get Ordernumbers from Gui
	        List orderlist = obj_Cust_Gui.getBestellNummernListe();
	        logger.debug("Result Orderlist : " + orderlist);
	            // convert into fully Order Object List with all ordervalues             
	            for(int objektzaehler = 0; objektzaehler < orderlist.size();objektzaehler++){	
	            	
	            	Obj_Order temp_order = new Obj_Order("","","","",0,0.0,0.0,0.0,0.0); 
	            	obj_Order = dataBase_Request.getOne_Order(temp_order,orderlist.get(objektzaehler).toString(), 
	            											  obj_Cust_Gui.getCustNr(), obj_Cust_Gui.getActiveDB(),logger);
	                // add order Object
	            	obj_orderlist .add(obj_Order);
	            	logger.debug("\nAdded Order Object" + obj_Order.getCustNo() );
	            }
	    return obj_orderlist ;       
	    }
	    
	    public void fillMonthlyList() {
	         for(int monats_zaehler = 0; monats_zaehler < monate.size();monats_zaehler++) {  
	             monatsliste.add(monate.get(monats_zaehler));
	         }
	    }
	 
	 public void getAll_Rates(Logger logger) {     
	   
	    LocalDate currentDate = LocalDate.now();
	    // fill Array with values 0
	    for(int y = 0; y < ar_monthly_rates.length; y++) {                   							
	    	ar_monthly_rates[y] = 0.0;
	    	ar_still_payed[y] = 0.0;
	    }
	    // calculate each order
	    for(int current_order = 0; current_order < obj_orderlist.size(); current_order++) {				
	
	        int int_month_to_Pay = 0 ;
	        int int_rate_count = obj_orderlist.get(current_order).getRateCount();						// number of rates
	        double first_rate = obj_orderlist.get(current_order).getFirstRate();						// first rate
	        double follow_rate = obj_orderlist.get(current_order).getRate();							// all other rates
	        // split the Date for Pay End
	        String[] split_end_date = obj_orderlist.get(current_order).getPayEnd().split("-");			
	        lst_rates.clear();
	        // fill rate list with values
	        for(int fillRates = 0; fillRates < int_rate_count; fillRates++) {  						    
	            if(fillRates == 0) {																	// first Rate only on the Beginning
	            	lst_rates.add(first_rate);
	            } else {
	            	lst_rates.add(follow_rate);
	            }   
	        }
	        // End Date Year and Month        
	        LocalDate payment_ends = LocalDate.of(Integer.parseInt(split_end_date[2]), Integer.parseInt(split_end_date[1]), 01);          
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
	        
	        // Payment start is in future ? Not startet yet ?  	         
	        if(int_month_to_Pay > int_rate_count ) {	
	        	
	        	// diff to pay start
	            int count_diff = int_month_to_Pay - int_rate_count;																		  
	            logger.debug("Payment not startet : " + count_diff);
	            
	            for(int future_rate = 0; future_rate < int_month_to_Pay;future_rate++) {
	            	// fill Array beginning with 0 values	
	                if(future_rate < count_diff) {
	                	ar_monthly_rates[future_rate] = 0.0;																			  		
	                } else {																						// extension needed for first Rate !!	
	                	// fill normal rates if start Date is reached										
	                	ar_monthly_rates[future_rate] = lst_rates.get((future_rate - count_diff));										  
	                }
	           }
	           logger.debug("List Size and Rates : " + ar_monthly_rates.length + " Rates : " + int_rate_count); 
	        } else { 	// Paymentstart reached		 																											  
	            
	        	// index adjustment																													
	            int int_backwards  = lst_rates.size() - 1;																				  
	            
	            // add rates to Array beginning on the End of list
	            for(int add_rate = int_month_to_Pay; add_rate > 0; add_rate--) {														 
	                
	            	ar_monthly_rates[add_rate-1] =ar_monthly_rates[add_rate-1] + lst_rates.get(int_backwards);
	                int_backwards--;
	            } 
	            
	            // add rates already paid
	            for(int add_still_payed = ((int_rate_count - int_month_to_Pay) - 1); add_still_payed >= 0 ;add_still_payed--) {		      
	             	ar_still_payed[add_still_payed] = ar_still_payed[add_still_payed] + lst_rates.get(add_still_payed);
	            }
	        
	           logger.debug("Rates to Pay : " + ar_monthly_rates.length + "\nRates payed : " + ar_still_payed.length ); 
	        } 
	        
	           
       
	    double payed = 0;
	    double to_pay = 0;
	    
	    // add up rates to pay
	    for(int x = 0; x < ar_monthly_rates.length; x++) { 																				 
	    	to_pay = to_pay + ar_monthly_rates[x];
	    }
	    
	    // add up rates still payed   
	    for(int x = 0; x < ar_still_payed.length; x++) { 																				 
	        payed = payed + ar_still_payed[x];
	    }
	        
         logger.debug("Summary to Pay : " + to_pay + "\nSummary payed : " + payed );     
	    }
	 // convert doubles to string  
	 convert_double_to_string(ar_monthly_rates);																						
	 }  
	 
	 private void convert_double_to_string(Double[] monthly_rates) {
	     for(int rate_counter = 0; rate_counter < 72; rate_counter++) {
	         str_monthly_rate.add(monthly_rates[rate_counter].toString());
	    
	     }
	 }
    
	    public ArrayList<String> getLstMonth(){ return lst_month; }
	    public void setLstMonth(ArrayList<String> lst_month) { this.lst_month = lst_month; }
	    public ArrayList<String> getMonthlyRate() { return str_monthly_rate; }
        public ArrayList<String> getMonatsliste() { return monatsliste;}
	
}
